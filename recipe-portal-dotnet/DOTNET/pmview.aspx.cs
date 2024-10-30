#region XD World Recipe V 2.8
// FileName: pmview.cs
// Author: Dexter Zafra
// Date Created: 3/19/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using XDRecipe.UI;
using XDRecipe.BL;
using XDRecipe.Security;
using XDRecipe.Common.Utilities;
using XDRecipe.BL.Providers.User;
using XDRecipe.BL.Providers.PrivateMessages;

public partial class pmview : BasePage
{
    private Utility Util
    {
        get { return new Utility(); }
    }

    public string HiUserName;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            Blogic.UpdateUserLastLogin(UserIdentity.UserID);

            HideContentIfNotLogin.Visible = true;

            int OrderBy = (int)Util.Val(Request.QueryString["ob"]);
            int SortBy = (int)Util.Val(Request.QueryString["sb"]);

            string ParamURL = Request.CurrentExecutionFilePath + "?";

            int iPage = 1;

            if (!string.IsNullOrEmpty(this.Request.QueryString["page"]))
                iPage = (int)Util.Val(Request.QueryString["page"]);

            int PageSize = constant.PageSize20;
            int PageIndex = iPage;

            string Folder = "inbox";

            lblPMHeader.Text = "Viewing Inbox";

            if (!string.IsNullOrEmpty(Request.QueryString["folder"]) && Request.QueryString["folder"] == "sentitems")
            {
                Folder = Request.QueryString["folder"];
                lblPMHeader.Text = "Viewing Inbox Sent Items";
            }

            if (!string.IsNullOrEmpty(Request.QueryString["folder"]) && Request.QueryString["folder"] == "trash")
            {
                Folder = Request.QueryString["folder"];
                lblPMHeader.Text = "Viewing Trash Items";
            }

            LoadMessage(Folder, UserIdentity.UserID, OrderBy, SortBy, PageIndex, PageSize);

            SortOptionLinks(OrderBy, SortBy, iPage, Folder);

            HiUserName = UserIdentity.UserName;
        }
        else
        {
            HideContentIfNotLogin.Visible = false;
            lblyouarenotlogin.Visible = true;
            lblyouarenotlogin.Text = "<div style='margin-top: 12px; margin-bottom: 7px;'><img src='images/lock.gif' align='absmiddle'> You are not authorize to view this page. Please login to view your Private Message InBox.</div>";
        }
    }

    private void LoadMessage(string Folder, int UserID, int OrderBy, int SortBy, int PageIndex, int PageSize)
    {
        ProviderPrivateMessageViewInbox GetMessage = new ProviderPrivateMessageViewInbox();
        GetMessage.Param(Folder, UserID, OrderBy, SortBy, PageIndex, PageSize);

        PagerLinks Pager = PagerLinks.GetInstance();
        Pager.PagerLinksParam(PageIndex, PageSize, GetMessage.TotalCount);

        string ParamURL = Request.CurrentExecutionFilePath + "?";

        lbPagerLink.Text = Pager.DisplayNumericPagerLinkPrivateMessage(ParamURL, OrderBy, SortBy, PageIndex, Folder);

        lblRecpage.Text = Pager.GetBottomPagerCounterCustomPaging;

        GetTopImageReadUnreadTopAndLabel(GetMessage, Folder);

        PMViewInbox.DataSource = GetMessage.GetMessages();
        PMViewInbox.DataBind();

        GetMessage = null;
    }

    private void GetTopImageReadUnreadTopAndLabel(ProviderPrivateMessageViewInbox GetMessage, string Folder)
    {
        if (string.IsNullOrEmpty(Request.QueryString["folder"]) || Folder == "inbox")
        {
            if (GetMessage.UnreadCount == 0)
            {
                IsNewImageTop.ImageUrl = "images/oldmsg_icon2.gif";
                lblcountunreadmsg.Text = "You have 0 new messages";
            }
            else
            {
                IsNewImageTop.ImageUrl = "images/newmsg_icon.gif";
                lblcountunreadmsg.Text = "You have " + GetMessage.UnreadCount + " new messages";
            }
        }
        else if (Folder == "sentitems")
        {
            if (GetMessage.UnreadCount == 0)
            {
                IsNewImageTop.ImageUrl = "images/oldmsg_icon2.gif";
                lblcountunreadmsg.Text = "You have 0 sent messages";
            }
            else
            {
                IsNewImageTop.ImageUrl = "images/newmsg_icon.gif";
                lblcountunreadmsg.Text = "You have " + GetMessage.UnreadCount + " unread sent messages";
            }
        }
        else if (Folder == "trash")
        {
            lblcountunreadmsg.Text = "You have " + GetMessage.TotalCount + " trash messages";
        }
    }

    public void PMViewInbox_ItemDataBound(Object s, RepeaterItemEventArgs e)
    {
        if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
        {
            Label MarkUnreadOrRead = (Label)(e.Item.FindControl("MarkUnreadOrRead"));
            Label lbDeleteInboxTrashPM = (Label)(e.Item.FindControl("lbDeleteInboxTrashPM"));
            Label lblDeleteSentPM = (Label)(e.Item.FindControl("lblDeleteSentPM"));
            LinkButton btnblockuser = (LinkButton)(e.Item.FindControl("btnblockuser"));
            Image ImgReadUnread = (Image)(e.Item.FindControl("ImgReadUnread"));
            Label lblreply = (Label)(e.Item.FindControl("lblreply"));
            Label lblforward = (Label)(e.Item.FindControl("lblforward"));
            Label lbusername = (Label)(e.Item.FindControl("lbusername"));
            Label linksubject = (Label)(e.Item.FindControl("linksubject"));
            Label lblhidemsg = (Label)(e.Item.FindControl("lblhidemsg"));
            Label lblmovepmtotrash = (Label)(e.Item.FindControl("lblmovepmtotrash"));
            Label lblmovebacktoinbox = (Label)(e.Item.FindControl("lblmovebacktoinbox"));

            int ReadMsg = (int)DataBinder.Eval(e.Item.DataItem, "Read");

            int UserID = UserIdentity.UserID;
            int PMID = (int)DataBinder.Eval(e.Item.DataItem, "ID");
            string PM_Subject = (string)DataBinder.Eval(e.Item.DataItem, "Subject");
            string UserNameOfSenderOrRecipient = (string)DataBinder.Eval(e.Item.DataItem, "SenderUserName");
            int UserIDOfRecipient = (int)DataBinder.Eval(e.Item.DataItem, "RecipientUserID");
            int UserIDOfSender = (int)DataBinder.Eval(e.Item.DataItem, "SenderUserID");
            int UnRead = 0;
            int Read = 1;

            lblreply.Attributes.Add("onmouseover", "Tip('Reply to <b>" + PM_Subject + "</b> message.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblreply.Attributes.Add("onmouseout", "UnTip()");
            lblforward.Attributes.Add("onmouseover", "Tip('Forward <b>" + PM_Subject + "</b> message.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblforward.Attributes.Add("onmouseout", "UnTip()");
            lbusername.Attributes.Add("onmouseover", "Tip('View <b>" + UserNameOfSenderOrRecipient + "</b> user profile.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lbusername.Attributes.Add("onmouseout", "UnTip()");
            linksubject.Attributes.Add("onmouseover", "Tip('Read <b>" + PM_Subject + "</b> full message.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            linksubject.Attributes.Add("onmouseout", "UnTip()");
            lblhidemsg.Attributes.Add("onmouseover", "Tip('Close message.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblhidemsg.Attributes.Add("onmouseout", "UnTip()");

            string NewMsgIcon = "<img src='images/newmsg_icon.gif' border='0'>";
            string OldMsgIcon = "<img src='images/oldmsg_icon2.gif' border='0'>";

            if (string.IsNullOrEmpty(Request.QueryString["folder"]) || Request.QueryString["folder"] == "inbox")
            {
                lblreply.Text = "<a href=pmsend.aspx?method=reply&replyto=" + UserNameOfSenderOrRecipient + "&pmid=" + PMID + "&subject=" + Server.UrlEncode(PM_Subject) + ">Reply</a>";
                lblforward.Text = "<a href=pmsend.aspx?method=forward&pmid=" + PMID + "&subject=" + Server.UrlEncode(PM_Subject) + ">Forward</a>";
                lbusername.Text = "<a href=userprofile.aspx?uid=" + UserIDOfSender + ">" + UserNameOfSenderOrRecipient + "</a>";

                lblmovepmtotrash.Visible = true;
                lblmovepmtotrash.Text = "<a class='thickbox' href='#TB_inline?height=75&width=350&inlineId=confirmModalMoveToTrash" + PMID + "&modal=true'><img border='0' src='images/icon_trashcan.gif'></a>";
                lblmovepmtotrash.Attributes.Add("onmouseover", "Tip('Move message (" + DataBinder.Eval(e.Item.DataItem, "Subject") + ") to trash.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                lblmovepmtotrash.Attributes.Add("onmouseout", "UnTip()");

                btnblockuser.Visible = true;
                btnblockuser.Attributes.Add("onmouseout", "UnTip()");

                if (!Blogic.IsUserBlocked(UserIDOfRecipient, UserIDOfSender))
                {
                    btnblockuser.Attributes.Add("onmouseover", "Tip('Block <b>" + UserNameOfSenderOrRecipient + "</b> from sending you a PM.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                }
                else
                {
                    btnblockuser.Attributes.Add("onmouseover", "Tip('<b>" + UserNameOfSenderOrRecipient + "</b> is already in your block list.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                }

                ImgReadUnread.Visible = false;

                if (ReadMsg == 1)
                {
                    MarkUnreadOrRead.Text = "<a href='javascript:void(0)' OnClick='sendRequestMarkOldMsgOnClickIconImage(" + UserIDOfRecipient + "," + PMID + "," + UnRead + ")'>" + OldMsgIcon + "</a>";
                    MarkUnreadOrRead.Attributes.Add("onmouseover", "Tip('Mark as <b>Unread</b> Message.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                    MarkUnreadOrRead.Attributes.Add("onmouseout", "UnTip()");
                    linksubject.Text = "<a href='javascript:void(0)' OnClick='ShowMessageNoAjaxCall(" + PMID + ")'>" + PM_Subject + "</a>";
                    lblhidemsg.Text = "<a href='javascript:void(0)' OnClick='HideMessageNoReload(" + PMID + ")'>Close</a>";
                }
                else
                {
                    MarkUnreadOrRead.Text = "<a href='javascript:void(0)' OnClick='sendRequestMarkOldMsgOnClickIconImage(" + UserIDOfRecipient + "," + PMID + "," + Read + ")'>" + NewMsgIcon + "</a>";
                    MarkUnreadOrRead.Attributes.Add("onmouseover", "Tip('Mark as <b>Read</b> Message.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                    MarkUnreadOrRead.Attributes.Add("onmouseout", "UnTip()");
                    linksubject.Text = "<a href='javascript:void(0)' OnClick='ShowMessage(" + PMID + ")'>" + PM_Subject + "</a>";
                    lblhidemsg.Text = "<a href='javascript:void(0)' OnClick='HideMessage(" + PMID + ")'>Close</a>";
                }
            }
            else if (Request.QueryString["folder"] == "sentitems")
            {
                lbusername.Text = "<a href=userprofile.aspx?uid=" + UserIDOfRecipient + ">" + UserNameOfSenderOrRecipient + "</a>";
                linksubject.Text = "<a href='javascript:void(0)' OnClick='ShowMessageNoAjaxCall(" + PMID + ")'>" + PM_Subject + "</a>";
                lblhidemsg.Text = "<a href='javascript:void(0)' OnClick='HideMessageNoReload(" + PMID + ")'>Close</a>";

                lblDeleteSentPM.Visible = true;
                lblDeleteSentPM.Text = "<a class='thickbox' href='#TB_inline?height=75&width=350&inlineId=confirmModalDelSentPM" + PMID + "&modal=true'><img border='0' src='images/icon_delete.gif'></a>";
                lblDeleteSentPM.Attributes.Add("onmouseover", "Tip('Delete sent message (" + DataBinder.Eval(e.Item.DataItem, "Subject") + ") permanently.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                lblDeleteSentPM.Attributes.Add("onmouseout", "UnTip()");

                MarkUnreadOrRead.Visible = false;

                if (ReadMsg == 1)
                {
                    ImgReadUnread.ImageUrl = "images/oldmsg_icon2.gif";
                    ImgReadUnread.Attributes.Add("onmouseover", "Tip('This message had been already read by the recipient.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                    ImgReadUnread.Attributes.Add("onmouseout", "UnTip()");
                }
                else
                {
                    ImgReadUnread.ImageUrl = "images/newmsg_icon.gif";
                    ImgReadUnread.Attributes.Add("onmouseover", "Tip('This message has not been read by the recipient.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                    ImgReadUnread.Attributes.Add("onmouseout", "UnTip()");
                }
            }
            else if (Request.QueryString["folder"] == "trash")
            {
                lblreply.Visible = false;
                lblforward.Visible = false;
                lbusername.Text = "<a href=userprofile.aspx?uid=" + UserIDOfRecipient + ">" + UserNameOfSenderOrRecipient + "</a>";

                lblmovebacktoinbox.Visible = true;
                lblmovebacktoinbox.Text = "<a href='javascript:void(0)' OnClick='sendRequestMovePMBackToInbox(" + PMID + ")'><img border='0' src='images/movetoinboxicon.gif'></a>&nbsp;&nbsp;";
                lblmovebacktoinbox.Attributes.Add("onmouseover", "Tip('Move message (" + DataBinder.Eval(e.Item.DataItem, "Subject") + ") back to inbox.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                lblmovebacktoinbox.Attributes.Add("onmouseout", "UnTip()");

                lbDeleteInboxTrashPM.Visible = true;
                lbDeleteInboxTrashPM.Text = "<a class='thickbox' href='#TB_inline?height=75&width=350&inlineId=confirmModalDelTrashInboxPM" + PMID + "&modal=true'><img border='0' src='images/icon_delete.gif'></a>";
                lbDeleteInboxTrashPM.Attributes.Add("onmouseover", "Tip('Delete message (" + DataBinder.Eval(e.Item.DataItem, "Subject") + ") permanently.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                lbDeleteInboxTrashPM.Attributes.Add("onmouseout", "UnTip()");

                if (ReadMsg == 1)
                {
                    MarkUnreadOrRead.Text = OldMsgIcon.ToString();
                    linksubject.Text = "<a href='javascript:void(0)' OnClick='ShowMessageNoAjaxCall(" + PMID + ")'>" + PM_Subject + "</a>";
                    lblhidemsg.Text = "<a href='javascript:void(0)' OnClick='HideMessageNoReload(" + PMID + ")'>Close</a>";
                }
                else
                {
                    MarkUnreadOrRead.Text = NewMsgIcon.ToString();
                    linksubject.Text = "<a href='javascript:void(0)' OnClick='ShowMessageNoAjaxCall(" + PMID + ")'>" + PM_Subject + "</a>";
                    lblhidemsg.Text = "<a href='javascript:void(0)' OnClick='HideMessageNoReload(" + PMID + ")'>Close</a>";
                }
            }
        }
    }

    public void PMViewInBox_Command(Object sender, RepeaterCommandEventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            int UserID;

            if ((e.CommandName == "BlockUser"))
            {
                string[] commandArgsBlockUser = e.CommandArgument.ToString().Split(new char[] { ',' });
                UserID = int.Parse(commandArgsBlockUser[0].ToString());
                int FromUID = int.Parse(commandArgsBlockUser[1].ToString());
                string FromUName = commandArgsBlockUser[2].ToString();

                if (!Blogic.IsUserBlocked(UserID, FromUID))
                {
                    Blogic.InsertPMBlockedUser(UserID, FromUID);
                    Response.Redirect("confirmupdateprivatemsg.aspx?mode=block&username=" + FromUName);
                }
            }
        }
    }

    private void SortOptionLinks(int OrderBy, int Sort_By, int iPage, string Folder)
    {
        string LinkName = "From";

        if (!string.IsNullOrEmpty(Folder) && Folder == "sentitems")
            LinkName = "To";

        if (OrderBy == 1)
        {
            SortLinkSubject.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=2&page=" + iPage + "&folder=" + Folder;
            SortLinkSubject.Text = "Subject";
            SortLinkSubject.Attributes.Add("onmouseover", "Tip('Sort by Subject ASC Order', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            SortLinkSubject.Attributes.Add("onmouseout", "UnTip()");
            ArrowImageSubject.ImageUrl = Util.SortOptionArrowImage;
            ArrowImageSubject.Visible = true;

            if (Sort_By == 2)
            {
                SortLinkSubject.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=1&page=" + iPage + "&folder=" + Folder;
                SortLinkSubject.Text = "Subject";
                SortLinkSubject.Attributes.Add("onmouseover", "Tip('Sort by Subject DESC Order', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                SortLinkSubject.Attributes.Add("onmouseout", "UnTip()");
                ArrowImageSubject.ImageUrl = Util.SortOptionArrowUpImage;
                ArrowImageSubject.Visible = true;
            }
        }
        else
        {
            SortLinkSubject.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=1" + "&sb=" + Sort_By + "&page=" + iPage + "&folder=" + Folder;
            SortLinkSubject.Text = "Subject";
            SortLinkSubject.Attributes.Add("onmouseover", "Tip('Sort by Subject', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            SortLinkSubject.Attributes.Add("onmouseout", "UnTip()");
            ArrowImageSubject.Visible = false;
        }

        if (OrderBy == 2)
        {
            SortLinkSender.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=2&page=" + iPage + "&folder=" + Folder;
            SortLinkSender.Text = LinkName;
            SortLinkSender.Attributes.Add("onmouseover", "Tip('Sort by Sender ASC order', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            SortLinkSender.Attributes.Add("onmouseout", "UnTip()");
            ArrowImageSender.ImageUrl = Util.SortOptionArrowImage;
            ArrowImageSender.Visible = true;

            if (Sort_By == 2)
            {
                SortLinkSender.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=1&page=" + iPage + "&folder=" + Folder;
                SortLinkSender.Text = LinkName;
                SortLinkSender.Attributes.Add("onmouseover", "Tip('Sort by Sender DESC order', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                SortLinkSender.Attributes.Add("onmouseout", "UnTip()");
                ArrowImageSender.ImageUrl = Util.SortOptionArrowUpImage;
                ArrowImageSender.Visible = true;
            }
            if (Sort_By == 0)
            {
                SortLinkSender.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=1&page=" + iPage + "&folder=" + Folder;
                SortLinkSender.Text = LinkName;
                SortLinkSender.Attributes.Add("onmouseover", "Tip('Sort by Sender DESC order', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                SortLinkSender.Attributes.Add("onmouseout", "UnTip()");
                ArrowImageSender.ImageUrl = Util.SortOptionArrowUpImage;
                ArrowImageSender.Visible = true;
            }
        }
        else
        {
            SortLinkSender.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=2" + "&sb=" + Sort_By + "&page=" + iPage + "&folder=" + Folder;
            SortLinkSender.Text = LinkName;
            SortLinkSender.Attributes.Add("onmouseover", "Tip('Sort by Sender', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            SortLinkSender.Attributes.Add("onmouseout", "UnTip()");
            ArrowImageSender.Visible = false;
        }

        if (OrderBy == 3)
        {
            SortLinkDateSent.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=2&page=" + iPage + "&folder=" + Folder;
            SortLinkDateSent.Text = "Date";
            SortLinkDateSent.Attributes.Add("onmouseover", "Tip('Sort by Date Sent ASC order', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            SortLinkDateSent.Attributes.Add("onmouseout", "UnTip()");
            ArrowImageDateSent.ImageUrl = Util.SortOptionArrowImage;
            ArrowImageDateSent.Visible = true;

            if (Sort_By == 2)
            {
                SortLinkDateSent.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=1&page=" + iPage + "&folder=" + Folder;
                SortLinkDateSent.Text = "Date";
                SortLinkDateSent.Attributes.Add("onmouseover", "Tip('Sort by Date Sent DESC order', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                SortLinkDateSent.Attributes.Add("onmouseout", "UnTip()");
                ArrowImageDateSent.ImageUrl = Util.SortOptionArrowUpImage;
                ArrowImageDateSent.Visible = true;
            }
        }
        else
        {
            SortLinkDateSent.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=3&sb=" + Sort_By + "&page=" + iPage + "&folder=" + Folder;
            SortLinkDateSent.Text = "Date";
            SortLinkDateSent.Attributes.Add("onmouseover", "Tip('Sort by Date Sent', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            SortLinkDateSent.Attributes.Add("onmouseout", "UnTip()");
            ArrowImageDateSent.Visible = false;
        }
    }
}
