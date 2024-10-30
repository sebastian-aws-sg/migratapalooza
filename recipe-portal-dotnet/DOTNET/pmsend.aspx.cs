#region XD World Recipe V 3
// FileName: pmsend.cs
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
using XDRecipe.BL.Providers.FriendList;
using XDRecipe.BL.Providers.PrivateMessages;

public partial class pmsend : BasePage
{
    private Utility Util
    {
        get { return new Utility(); }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        EnsureParameterIsCorrect();

        if (Authentication.IsUserAuthenticated)
        {
            Blogic.UpdateUserLastLogin(UserIdentity.UserID);

            HideContentIfNotLogin.Visible = true;

            GetMethodMode();

            ProviderFriendsListMain MyFriends = new ProviderFriendsListMain(UserIdentity.UserID);

            MyFriendsList.DataSource = MyFriends.GetFriendsList();
            MyFriendsList.DataBind();

            if (MyFriends.TotalCount == 0)
                lblcountfriends.Visible = true;

            MyFriends = null;
        }
        else
        {
            HideContentIfNotLogin.Visible = false;
            lblyouarenotlogin.Visible = true;
            lblyouarenotlogin.Text = "<div style='margin-top: 12px; margin-bottom: 7px;'><img src='images/lock.gif' align='absmiddle'> You are not authorize to view this page. Please login to to send a private message to other members.</div>";
        }
    }

    private void EnsureParameterIsCorrect()
    {
        if (string.IsNullOrEmpty(Request.QueryString["method"]))
            Response.Redirect("pmview.aspx");

        string method = Request.QueryString["method"];

        if (method == "newmsg" || method == "reply" || method == "forward")
        {
        }
        else
        {
            Response.Redirect("pmview.aspx");
        }
    }

    private void GetMethodMode()
    {
        PMSendTo.Value = Request.QueryString["sendto"];

        string mode = Request.QueryString["method"];

        if (mode == "newmsg")
        {
            PanelShowHideToFieldIfReply.Visible = true;
            PanelShowHideSubjectIfReply.Visible = true;
        }

        switch (mode)
        {
            case "reply":
                lblreplyto.Visible = true;
                lblsubject.Visible = true;
                lblreplyto.Text = "<div style='margin-left: 3px; margin-bottom: 6px;'>ReplyTo:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + Server.UrlDecode(Request.QueryString["replyto"]).Trim() +"</div>";
                lblsubject.Text = "<div style='margin-left: 3px; margin-bottom: 6px;'>Subject:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RE: " + Server.UrlDecode(Request.QueryString["subject"]).Trim() + "</div>";
                break;

            case "forward":
                PanelShowHideToFieldIfReply.Visible = true;
                lblsubject.Visible = true;
                lblsubject.Text = "<div style='margin-left: 3px; margin-bottom: 6px;'>Subject:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FW: " + Server.UrlDecode(Request.QueryString["subject"]).Trim() + "</div>";
                Content.Value = Blogic.GetPMMessage(int.Parse(Request.QueryString["pmid"]));
                break;
        }
    }

    public void SendPM_Click(Object s, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            if (Page.IsValid)
            {
                PrivateMessageRepository Message = new PrivateMessageRepository();

                string mode = Request.QueryString["method"];

                if (mode == "newmsg")
                {
                    Message.Subject = Util.FormatTextForInput(Request.Form["PMSubject"]);
                    Message.RecipientUserName = Util.FormatTextForInput(Request.Form["PMSendTo"]).Trim();
                }
                if (mode == "reply")
                {
                    Message.Subject = "RE: " + Server.UrlDecode(Request.QueryString["subject"]).Trim();
                    Message.RecipientUserName = Server.UrlDecode(Request.QueryString["replyto"]).Trim();
                }
                if (mode == "forward")
                {
                    Message.Subject = "FW: " + Server.UrlDecode(Request.QueryString["subject"]).Trim();
                    Message.RecipientUserName = Util.FormatTextForInput(Request.Form["PMSendTo"]).Trim();
                }

                Message.SenderUserID = UserIdentity.UserID;
                Message.Message = Request.Form["Content"];

                #region Input Validation

                if (Blogic.IsUsernameAvailable(Message.RecipientUserName))
                {
                    lbvalenght.Text = "<br>Error: The recipient username " + Message.RecipientUserName + " does not exist.";
                    lbvalenght.Visible = true;
                    return;
                }

                //Check if sender is blocked by the recipient from sending a PM.
                if (Blogic.IsUserBlockedByRecipient(Message.RecipientUserName, UserIdentity.UserID))
                {
                    lbvalenght.Text = "<br>Error: " + Message.RecipientUserName + " Had Blocked You From Sending a PM.";
                    lbvalenght.Visible = true;
                    return;
                }

                //Get the userID
                int GetUserID = Blogic.GetUserIDByUsername(Message.RecipientUserName);
                UserFeaturesConfiguration.Fetch(GetUserID);

                if (!UserFeaturesConfiguration.IsUserChooseToReceivePM)
                {
                    lbvalenght.Text = "<br>Error: " + Message.RecipientUserName + " opted not to receive a private message.";
                    lbvalenght.Visible = true;
                    return;
                }
                if (Message.Subject.Length == 0)
                {
                    lbvalenght.Text = "<br>Error: The subject is empty.";
                    lbvalenght.Visible = true;
                    return;
                }
                if (Message.Subject.Length > 65)
                {
                    lbvalenght.Text = "<br>Error: The subject is too long. Maximum of 65 characters.";
                    lbvalenght.Visible = true;
                    return;
                }
                if (Message.Message.Length == 0)
                {
                    lbvalenght.Text = "<br>Error: Message is empty.";
                    lbvalenght.Visible = true;
                    return;
                }
                if (Message.Message.Length > 5000)
                {
                    lbvalenght.Text = "<br>Error: The message is too long. Maximum of 5000 characters including HTML formatting.";
                    lbvalenght.Visible = true;
                    return;
                }

                #endregion

                if (Message.Add(Message) != 0)
                {
                    JSLiteral.Text = "An error occured while processing your request.";
                    return;
                }

                if (UserFeaturesConfiguration.IsUserChooseToReceiveEmailAlertReceivePM)
                {
                    SendPMEmailNotification(GetUserID, Message.Subject);
                }

                Response.Redirect("confirmpmsent.aspx?to=" + Message.RecipientUserName);

                Message = null;
            }
        }
    }

    private void SendPMEmailNotification(int UserID, string PMSubject)
    {
        ProviderUserDetails user = new ProviderUserDetails();

        user.FillUp(UserID);

        EmailTemplate Email = new EmailTemplate();

        Email.SendEmailPrivateMessageAlert(UserIdentity.UserName, user.Username, user.Email, PMSubject);

        Email = null;
        user = null;
    }
}
