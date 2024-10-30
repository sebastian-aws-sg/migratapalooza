#region XD World Recipe V 2.8
// FileName: pmblockedusers.cs
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

public partial class pmblockedusers : BasePage
{
    public string HiUserName;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!Page.IsPostBack)
        {
            if (Authentication.IsUserAuthenticated)
            {
                HideContentIfNotLogin.Visible = true;

                HiUserName = UserIdentity.UserName;

                Blogic.UpdateUserLastLogin(UserIdentity.UserID);

                lblcountblocklistedusers.Text = "You Have (" + Blogic.CountPMBlocklistedUsers(UserIdentity.UserID) + ") Block Listed Users.";

                LoadData();
            }
            else
            {
                HideContentIfNotLogin.Visible = false;
                lblyouarenotlogin.Visible = true;
                lblyouarenotlogin.Text = "<div style='margin-top: 12px; margin-bottom: 7px;'><img src='images/lock.gif' align='absmiddle'> You are not authorize to view page. Please login to view this page.</div>";
            }
        }
    }

    private void LoadData()
    {
        ProviderBlockedListedUsers.Param(UserIdentity.UserID);
        PMBlockedUsers.DataSource = ProviderBlockedListedUsers.GetBlockListedUsers();
        PMBlockedUsers.DataBind();
    }

    public void PMBlockedUsers_ItemDataBound(Object s, RepeaterItemEventArgs e)
    {
        if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
        {
            HyperLink deleteLink = (HyperLink)(e.Item.FindControl("btnDelete"));
            Button deleteCommand = (Button)(e.Item.FindControl("deleteCommand"));
            Button yes = (Button)(e.Item.FindControl("yes"));
            Panel deleteConfirm = (Panel)(e.Item.FindControl("deleteConfirm"));

            const string thickBoxOptionsFormat = "#TB_inline?height=75&width=350&inlineId={0}&modal=true";

            deleteLink.Attributes.Add("onmouseover", "Tip('Removed (<b>" + DataBinder.Eval(e.Item.DataItem, "SenderUserName") + "</b>) from my PM block list.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            deleteLink.Attributes.Add("onmouseout", "UnTip()");
            deleteLink.NavigateUrl = string.Format(thickBoxOptionsFormat, deleteConfirm.ClientID);

            yes.Attributes.Add("onmouseover", "Tip('Click to remove (<b>" + DataBinder.Eval(e.Item.DataItem, "SenderUserName") + "</b>) from your PM block list.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            yes.Attributes.Add("onmouseout", "UnTip()");

            const string deleteFormat = "$('#{0}').click(); return false;";
            yes.OnClientClick = string.Format(deleteFormat, deleteCommand.ClientID);
        }
    }

    public void PMBlockedUsers_Command(Object sender, RepeaterCommandEventArgs e)
    {
        if ((e.CommandName == "Remove"))
        {
            string[] commandArgsRemove = e.CommandArgument.ToString().Split(new char[] { ',' });
            int ID = int.Parse(commandArgsRemove[0].ToString());
            string UserName = commandArgsRemove[1].ToString();

            Blogic.RemovedUserFromPMBlockedList(ID, UserIdentity.UserID);

            Response.Redirect("confirmupdateprivatemsg.aspx?mode=remove&username=" + UserName);
        }
    }
}
