#region XD World Recipe V 3
// FileName: viewunapprovedfriends.cs
// Author: Dexter Zafra
// Date Created: 2/25/2009
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
using XDRecipe.BL.Providers.User;
using XDRecipe.UI;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Security;
using XDRecipe.Common.Utilities;
using XDRecipe.BL.Providers.CookBooks;
using XDRecipe.BL.Providers.FriendList;

public partial class viewunapprovedfriends : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!Page.IsPostBack)
        {
            if (Authentication.IsUserAuthenticated)
            {
                HideContentIfNotLogin.Visible = true;

                lblusernameheader.Text = UserIdentity.UserName + "'s New Friends Waiting For Approval";

                Blogic.UpdateUserLastLogin(UserIdentity.UserID);

                GetCountUnApprovedNewFriendsLink();
                LoadData();
            }
            else
            {
                HideContentIfNotLogin.Visible = false;
                lblyouarenotlogin.Visible = true;
                lblusernameheader.Text = "Denied Friends List View";
                lblyouarenotlogin.Text = "<div style='margin-top: 12px; margin-bottom: 7px;'><img src='images/lock.gif' align='absmiddle'> You are not authorize to view this Friends List. Please login to view your Friends List.</div>";
            }
        }
    }

    private void LoadData()
    {
        ProviderUnApprovedFriends UnApprovedNewFriends = new ProviderUnApprovedFriends(UserIdentity.UserID);

        MyFriendsList.DataSource = UnApprovedNewFriends.GetFriendsList();
        MyFriendsList.DataBind();

        UnApprovedNewFriends = null;
    }

    private void GetCountUnApprovedNewFriendsLink()
    {
        int CountUnApprovedNewFriends = Blogic.GetCountUnApprovedFriends(UserIdentity.UserID);

        if (CountUnApprovedNewFriends != 0)
            lblcountunpprovednewfriends.Text = "There are <b>(" + CountUnApprovedNewFriends + ")</b> users added you as a friend and waiting for your approval.";
        else
            lblcountunpprovednewfriends.Text = "There are <b>(" + CountUnApprovedNewFriends + ")</b> users added you as a friend..";
    }

    public void MyFriendsList_ItemDataBound(Object s, RepeaterItemEventArgs e)
    {
        if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
        {
            HyperLink deleteLink = (HyperLink)(e.Item.FindControl("btnDelete"));
            Button deleteCommand = (Button)(e.Item.FindControl("deleteCommand"));
            ImageButton btnapproved = (ImageButton)(e.Item.FindControl("btnapproved"));
            Button yes = (Button)(e.Item.FindControl("yes"));
            Panel deleteConfirm = (Panel)(e.Item.FindControl("deleteConfirm"));

            const string thickBoxOptionsFormat = "#TB_inline?height=75&width=350&inlineId={0}&modal=true";

            btnapproved.Attributes.Add("onmouseover", "Tip('Click to approved (<b>" + DataBinder.Eval(e.Item.DataItem, "Username") + "</b>).', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            btnapproved.Attributes.Add("onmouseout", "UnTip()");

            deleteLink.Attributes.Add("onmouseover", "Tip('Declined (<b>" + DataBinder.Eval(e.Item.DataItem, "Username") + "</b>).', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            deleteLink.Attributes.Add("onmouseout", "UnTip()");
            deleteLink.NavigateUrl = string.Format(thickBoxOptionsFormat, deleteConfirm.ClientID);

            yes.Attributes.Add("onmouseover", "Tip('Click to declined (<b>" + DataBinder.Eval(e.Item.DataItem, "Username") + "</b>).', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            yes.Attributes.Add("onmouseout", "UnTip()");

            const string deleteFormat = "$('#{0}').click(); return false;";
            yes.OnClientClick = string.Format(deleteFormat, deleteCommand.ClientID);
        }
    }

    public void Delete_Friend(Object sender, RepeaterCommandEventArgs e)
    {
        int ID;
        string UserName;
        string Email;

        if ((e.CommandName == "Declined"))
        {
            string[] commandArgsDelete = e.CommandArgument.ToString().Split(new char[] { ',' });
            ID = int.Parse(commandArgsDelete[0].ToString());
            UserName = commandArgsDelete[1].ToString();
            Email = commandArgsDelete[2].ToString();

            Blogic.DeleteUnApprovedFriends(UserIdentity.UserID, ID);

            SendEmailApprovedOrDeclinedNotification(Email, UserName, false);

            Response.Redirect("confirmfrienddelete.aspx?un=" + Server.HtmlEncode(UserName));
        }

        if ((e.CommandName == "Approved"))
        {
            string[] commandArgsApproved = e.CommandArgument.ToString().Split(new char[] { ',' });
            ID = int.Parse(commandArgsApproved[0].ToString());
            Email = commandArgsApproved[1].ToString();
            UserName = commandArgsApproved[2].ToString();

            Blogic.ApprovedAFriend(UserIdentity.UserID, ID);

            SendEmailApprovedOrDeclinedNotification(Email, UserName, true);

            Response.Redirect("myfriendslist.aspx");
        }
    }

    private void SendEmailApprovedOrDeclinedNotification(string ToEmail, string ToUserName, bool IsApproved)
    {
        EmailTemplate Email = new EmailTemplate();

        Email.ApprovedOrDeclineAddAFriendEmailNotification(ToEmail, ToUserName, UserIdentity.UserName, IsApproved);

        Email = null;
    }
}
