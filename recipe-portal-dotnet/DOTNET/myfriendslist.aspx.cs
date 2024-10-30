#region XD World Recipe V 3
// FileName: myfriendslist.cs
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

public partial class myfriendslist : BasePage
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            HideContentIfNotLogin.Visible = true;

            lblusernameheader.Text = UserIdentity.UserName + "'s Friends List";

            Blogic.UpdateUserLastLogin(UserIdentity.UserID);

            GetMetaTitleTagKeywords(UserIdentity.UserName);
            GetCountUnApprovedNewFriendsLink();
            LoadData();
        }
        else
        {
            GetMetaTitleTagKeywords("Denied View");
            HideContentIfNotLogin.Visible = false;
            lblyouarenotlogin.Visible = true;
            lblusernameheader.Text = "Denied Friends List View";
            lblyouarenotlogin.Text = "<div style='margin-top: 12px; margin-bottom: 7px;'><img src='images/lock.gif' align='absmiddle'> You are not authorize to view this Friends List. Please login to view your Friends List.</div>";
        }
    }

    private void LoadData()
    {
        ProviderFriendsListMain MyFriends = new ProviderFriendsListMain(UserIdentity.UserID);

        MyFriendsList.DataSource = MyFriends.GetFriendsList();
        MyFriendsList.DataBind();

        int NumRecordsAllowed = SiteConfiguration.GetConfiguration.NumberOfFriendsInFriendsList;

        int Remaining = NumRecordsAllowed - MyFriends.TotalCount;

        lblcounter.Text = "<img src='images/friendlisticon.gif' align='absmiddle'>&nbsp;&nbsp;You have (" + MyFriends.TotalCount + ") friends saved. The maximum allowed of friends is " + NumRecordsAllowed + ". You have a remaining " + Remaining + " friends to save.";

        MyFriends = null;
    }

    private void GetCountUnApprovedNewFriendsLink()
    {
        int CountUnApprovedNewFriends = Blogic.GetCountUnApprovedFriends(UserIdentity.UserID);

        if (CountUnApprovedNewFriends != 0)
        {
            PanelUnApprovedFriends.Visible = true;
            lblcountunpprovednewfriends.Text = "You have <a href='viewunapprovedfriends.aspx'><b>" + CountUnApprovedNewFriends + "</b> users</a> added you as a friend and waiting for your approval.";
            lblcountunpprovednewfriends.Attributes.Add("onmouseover", "Tip('Click the link to approved or declined users request.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblcountunpprovednewfriends.Attributes.Add("onmouseout", "UnTip()");
        }
    }

    public void MyFriendsList_ItemDataBound(Object s, RepeaterItemEventArgs e)
    {
        if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
        {
            Label lblDelete = (Label)(e.Item.FindControl("lblDelete"));

            int ID = (int)DataBinder.Eval(e.Item.DataItem, "ID");

            lblDelete.Text = "<a class='thickbox' href='#TB_inline?height=75&width=350&inlineId=confirmModal" + ID + "&modal=true'><img border='0' src='images/icon_delete.gif'></a>";
            lblDelete.Attributes.Add("onmouseover", "Tip('Remove (<b>" + DataBinder.Eval(e.Item.DataItem, "Username") + "</b>) from my Friends List.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblDelete.Attributes.Add("onmouseout", "UnTip()");
        }
    }

    private void GetMetaTitleTagKeywords(string UserName)
    {
        Page.Header.Title = UserName + "'s Friends List";
        HtmlMeta metaTag = new HtmlMeta();
        metaTag.Name = "Keywords";
        metaTag.Content = UserName + " Friends List.";
        this.Header.Controls.Add(metaTag);
    }
}
