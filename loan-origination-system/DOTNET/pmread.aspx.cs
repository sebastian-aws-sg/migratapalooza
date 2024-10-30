#region XD World Recipe V 3
// FileName: pmread.cs
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

public partial class pmread : BasePage
{
    private Utility Util
    {
        get { return new Utility(); }
    }

    public string HiUserName;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!Page.IsPostBack)
        {
            if (Authentication.IsUserAuthenticated)
            {
                HideContentIfNotLogin.Visible = true;

                int PMID = (int)Util.Val(Request.QueryString["pmid"]);

                if (!Blogic.IsPMOwner(PMID, UserIdentity.UserID))
                    Response.Redirect("pmview.aspx");
                
                LoadMessage(PMID);

                Blogic.UpdateUserLastLogin(UserIdentity.UserID);

                HiUserName = UserIdentity.UserName;
            }
            else
            {
                HideContentIfNotLogin.Visible = false;
                lblyouarenotlogin.Visible = true;
                lblyouarenotlogin.Text = "<div style='margin-top: 12px; margin-bottom: 7px;'><img src='images/lock.gif' align='absmiddle'> You are not authorize to view this page. Please login to view your Private Message InBox.</div>";
            }
        }
    }

    private void LoadMessage(int PMID)
    {
        ProviderReadMessage GetMessage = new ProviderReadMessage();
        GetMessage.FillUp(PMID, UserIdentity.UserID);

        lblsubject.Text = "<b>From&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Subject: " + GetMessage.Subject + "</b>";
        lblsenderinfo.Text = "<a href=userprofile.aspx?uid=" + GetMessage.SenderUserID + ">" + GetMessage.SenderUserName + "</a>";
        lblsenderinfo.Attributes.Add("onmouseover", "Tip('View <b>" + GetMessage.SenderUserName + "</b> profile.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        lblsenderinfo.Attributes.Add("onmouseout", "UnTip()");
        lbldate.Text = Utility.FormatDate(GetMessage.DateSent);
        lblemailsender.Text = "<a href=emailuser.aspx?uid=" + GetMessage.SenderUserID + "><img src='images/email_icon.gif' align='absmiddle' border='0'></a>";
        lblemailsender.Attributes.Add("onmouseover", "Tip('Send <b>" + GetMessage.SenderUserName + "</b> an email.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        lblemailsender.Attributes.Add("onmouseout", "UnTip()");
        lblmessage.Text = GetMessage.Message;

        btnReply.Attributes.Add("onmouseover", "Tip('Reply to <b>" + GetMessage.Subject + "</b> message.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnReply.Attributes.Add("onmouseout", "UnTip()");
        btnForward.Attributes.Add("onmouseover", "Tip('Forward <b>" + GetMessage.Subject + "</b> message.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnForward.Attributes.Add("onmouseout", "UnTip()");
        btnDelete.Attributes.Add("onmouseover", "Tip('Delete <b>" + GetMessage.Subject + "</b> message.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnDelete.Attributes.Add("onmouseout", "UnTip()");

        GetMessage = null;
    }

    public void Reply_Click(object sender, EventArgs e)
    {
        int PMID = (int)Util.Val(Request.QueryString["pmid"]);

        ProviderReadMessage GetMessage = new ProviderReadMessage();
        GetMessage.FillUp(PMID, UserIdentity.UserID);

        string UserNameToReply = GetMessage.SenderUserName;
        string ReplySubject = GetMessage.Subject;

        GetMessage = null;

        Response.Redirect("pmsend.aspx?method=reply&replyto=" + UserNameToReply + "&pmid=" + PMID + "&subject=" + Server.UrlEncode(ReplySubject));
    }

    public void Forward_Click(object sender, EventArgs e)
    {
        int PMID = (int)Util.Val(Request.QueryString["pmid"]);

        ProviderReadMessage GetMessage = new ProviderReadMessage();
        GetMessage.FillUp(PMID, UserIdentity.UserID);

        string ReplySubject = GetMessage.Subject;

        GetMessage = null;

        Response.Redirect("pmsend.aspx?method=forward&pmid=" + PMID + "&subject=" + Server.UrlEncode(ReplySubject));
    }

    public void Delete_Click(object sender, EventArgs e)
    {
        int PMID = (int)Util.Val(Request.QueryString["pmid"]);

        PrivateMessageRepository Message = new PrivateMessageRepository();
        Message.RecipientUserID = UserIdentity.UserID;
        Message.ID = PMID;

        Message.Delete(Message);

        Response.Redirect("confirmupdateprivatemsg.aspx?mode=delete&pid=" + PMID);
    }
}
