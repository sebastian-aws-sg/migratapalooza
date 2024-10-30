#region XD World Recipe V 2.8
// FileName: myaccount.cs
// Author: Dexter Zafra
// Date Created: 5/29/2008
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
using XDRecipe.Common;
using XDRecipe.Security;
using XDRecipe.Common.Utilities;
using XDRecipe.BL.Providers.CookBooks;
using XDRecipe.BL.Providers.FriendList;
using XDRecipe.BL.Providers.User;

public partial class myaccount : BasePage
{
    private Utility Util
    {
        get { return new Utility(); }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            HideContentIfNotLogin.Visible = true;

            int UserID = UserIdentity.UserID;
            string Username = UserIdentity.UserName;

            Blogic.UpdateUserLastLogin(UserID);

            lblusernameheader.Text = Username + "'s My Account";

            ProviderUserDetails user = new ProviderUserDetails();

            user.FillUp(UserID);

            lbllastactivitymsg.Text = " Your last activity was on: (<span style='color: #800000'>" + string.Format("{0:g}", user.LastLogin) + "</span>)";
            lblmyprofilelink.Text = "<img src='images/user1_icon.gif'>&nbsp;<a href=userprofile.aspx?uid=" + UserID + ">My Profile</a>";
            lbleditmyprofile.Text = "<img src='images/editsmall.gif'>&nbsp;<a href='editprofile.aspx'>Edit My Profile</a>";
            lblmycookbooklink.Text = "<img src='images/cookbookicon_smll.gif'>&nbsp;<a href='mycookbook.aspx'>My Cook Book</a>";
            lblmyfriendslistlink.Text = "<img src='images/friendlisticon_smll.gif'>&nbsp;<a href='myfriendslist.aspx'>My Friends List</a>";
            lblmypminbox.Text = "<img src='images/newmsg_icon_smll2.gif'>&nbsp;<a href='pmview.aspx'>My PM Inbox</a>";
            lblmyprofilelink.Attributes.Add("onmouseover", "Tip('View my profile page.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblmyprofilelink.Attributes.Add("onmouseout", "UnTip()");
            lbleditmyprofile.Attributes.Add("onmouseover", "Tip('Update information, change password, email and photo.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lbleditmyprofile.Attributes.Add("onmouseout", "UnTip()");
            lblmycookbooklink.Attributes.Add("onmouseover", "Tip('Vew My Cook Book page.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblmycookbooklink.Attributes.Add("onmouseout", "UnTip()");
            lblmyfriendslistlink.Attributes.Add("onmouseover", "Tip('Vew My Friends List page.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblmyfriendslistlink.Attributes.Add("onmouseout", "UnTip()");
            lblmypminbox.Attributes.Add("onmouseover", "Tip('View my Inbox.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblmypminbox.Attributes.Add("onmouseout", "UnTip()");

            GetMetaTitleTagKeywords(Username);
            GetMyStatisticsCounter(user);
            GetCounters(user);
            GetUserWhoAddMeInFriendsList(UserID);
            GetLast5UsersWhoSavedMyRecipeInCookBook(UserID);
            GetDDLSelectedValue();
            GetStatisticsCounters();
            GetReturnFromUpdateMsg();

            user = null;
        }
        else
        {
            GetMetaTitleTagKeywords("Denied View");
            HideContentIfNotLogin.Visible = false;
            lblyouarenotlogin.Visible = true;
            lblusernameheader.Text = "Denied My Account View";
            lblyouarenotlogin.Text = "<div style='margin-top: 12px; margin-bottom: 7px;'><img src='images/lock.gif' align='absmiddle'> You are not authorize to My Account page. Please login to view your Account Page.</div>";
        }
    }

    private void GetStatisticsCounters()
    {
        ProviderSiteStatistics Statistics = new ProviderSiteStatistics();
        Statistics.fillup();

        lbltotalrecipe.Text = "Recipes: " + string.Format("{0:#,###}", Statistics.TotalRecipe);
        lbltotalarticle.Text = "Articles: " + Statistics.TotalArticle;
        lbltotalrecipecomments.Text = "Recipe Comments: " + Statistics.TotalRecipeComments;
        lbltotalarticlecomments.Text = "Article Comments: " + Statistics.TotalArticleComments;
        lbltotalsavedrecipeincookbook.Text = "Recipes in CookBook: " + Statistics.TotalSavedRecipesInCookBook;
        lbltotaluserswhousecookbook.Text = "Users in CookBook: " + Statistics.TotalUsersWhoUseCookBook;
        lbltotaluserswhousefriendslist.Text = "Users in FriendsList: " + Statistics.TotalUsersWhoUseFriendsList;
        lbltotalprivatemessage.Text = "Private Messages: " + Statistics.TotalPrivateMessage;
        lbltotalRegisteredUsers.Text = "Total Members: <a href='members.aspx'>" + Statistics.TotalMembers + "</a>";
        lbltotalRegisteredUsers.Attributes.Add("onmouseover", "Tip('Browse members page.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        lbltotalRegisteredUsers.Attributes.Add("onmouseout", "UnTip()");
        lbltotaluserjoinedtoday.Text = "Joined Today: " + Statistics.TotalUsersJoinedToday;
        lbltotaluserjoininaweek.Text = "Joined Last 7 Days: " + Statistics.TotalUsersJoinedInAWeek;
        lbltotaluserjoinedinamonth.Text = "Joined Last 31 Days: " + Statistics.TotalUsersJoinedInAMonth;

        Statistics = null;
    }

    private void GetMyStatisticsCounter(ProviderUserDetails user)
    {
        lblcountmysavedrecipe.Text = "Saved Recipe: " + user.SavedrecipeCount.ToString();
        lblcountmyfriends.Text = "Saved Friends: " + user.FriendsCount.ToString();
        lblcommentedrecipe.Text = "Comments Recipe: " + user.CommentRecipeCount.ToString();
        lblpostedrecipecount.Text = "Published Recipe: " + user.PostedRecipeCount.ToString();
        lblpostedarticlecount.Text = "Published Article: " + user.PostedArticleCount.ToString();
        lblcommentarticle.Text = "Comments Article: " + user.CommentArticleCount.ToString();

        if (user.SavedrecipeCount != 0)
        {
            lblcountmysavedrecipe.Text = "Saved Recipe:&nbsp;<a href='mycookbook.aspx'>" + user.SavedrecipeCount + "</a>";
            lblcountmysavedrecipe.Attributes.Add("onmouseover", "Tip('Browse My Cook Book.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblcountmysavedrecipe.Attributes.Add("onmouseout", "UnTip()");
        }

        if (user.FriendsCount != 0)
        {
            lblcountmyfriends.Text = "Saved Recipe:&nbsp;<a href='myfriendslist.aspx'>" + user.FriendsCount + "</a>";
            lblcountmyfriends.Attributes.Add("onmouseover", "Tip('Browse My Friends List.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblcountmyfriends.Attributes.Add("onmouseout", "UnTip()");
        }

        if (user.PostedRecipeCount != 0)
        {
            lblpostedrecipecount.Text = "Published Recipe:&nbsp;<a href=" + "findallrecipebyauthor.aspx?author=" + user.Username + ">" + user.PostedRecipeCount + "</a>";
            lblpostedrecipecount.Attributes.Add("onmouseover", "Tip('Browse all my published recipe.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblpostedrecipecount.Attributes.Add("onmouseout", "UnTip()");
        }

        if (user.PostedArticleCount != 0)
        {
            lblpostedarticlecount.Text = "Published Article:&nbsp;<a href=" + "findallarticlebyauthor.aspx?author=" + user.Username + ">" + user.PostedArticleCount + "</a>";
            lblpostedarticlecount.Attributes.Add("onmouseover", "Tip('Browse all my published article.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblpostedarticlecount.Attributes.Add("onmouseout", "UnTip()");
        }

        if (user.CommentRecipeCount != 0)
        {
            lblcommentedrecipe.Text = "Comments Recipe:&nbsp;<a href=" + "findallrecipecommentedbyuser.aspx?commentauthor=" + user.Username + ">" + user.CommentRecipeCount + "</a>";
            lblcommentedrecipe.Attributes.Add("onmouseover", "Tip('Browse all commented recipe.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblcommentedrecipe.Attributes.Add("onmouseout", "UnTip()");
        }

        if (user.CommentArticleCount != 0)
        {
            lblcommentarticle.Text = "Comments Article:&nbsp;<a href=" + "findallarticlecommentbyuser.aspx?author=" + user.Username + ">" + user.CommentArticleCount + "</a>";
            lblcommentarticle.Attributes.Add("onmouseover", "Tip('Browse all my commented article.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblcommentarticle.Attributes.Add("onmouseout", "UnTip()");
        }
    }

    private void GetUserWhoAddMeInFriendsList(int UserID)
    {
        //Get all users who add me in their Friends List.
        //This gives user some info who are the user added him/her in Friends list.
        ProvidersListOfUserWhoAddedMe Who = new ProvidersListOfUserWhoAddedMe(UserID);

        WhoAddsMe.DataSource = Who.GetUsers();
        WhoAddsMe.DataBind();

        int CountUsers = Blogic.GetCountUserWhoAddMeInFriendsList(UserID);

        if (CountUsers != 0)
        {
            lblcountwhoaddmeinfriendslist.Text = "There are (" + CountUsers + ") users.";
        }
        else
        {
            lblcountwhoaddmeinfriendslist.Text = "No users have added you in the Friends List.";
        }

        Who = null;
    }

    private void GetLast5UsersWhoSavedMyRecipeInCookBook(int UserID)
    {
        ProviderLast5UsersWhoSavedMyRecipe WhoSavedMyRecipe = new ProviderLast5UsersWhoSavedMyRecipe(UserID);

        Last5UsersWhoSavedMyRecipe.DataSource = WhoSavedMyRecipe.GetUsers();
        Last5UsersWhoSavedMyRecipe.DataBind();

        int CountUsers = Blogic.GetCountUsersWhoSavedMyRecipeInCookBook(UserID);

        if (CountUsers != 0)
        {
            lblcountuserswhosavedmyrecipe.Text = "There are (" + CountUsers + ") users.";
        }
        else
        {
            lblcountuserswhosavedmyrecipe.Text = "No users have saved your recipe in Cook Book.";
        }

        WhoSavedMyRecipe = null;
    }

    private void GetDDLSelectedValue()
    {
        UserFeaturesConfiguration.Fetch(UserIdentity.UserID);

        int layout = UserFeaturesConfiguration.GetUserPreferredLayout;
        int pagesize = UserFeaturesConfiguration.GetUserPreferredPageSize;

        if ((UserFeaturesConfiguration.IsUserChoosePreferredLayoutPageSize))
        {
            uconfigturnonofflayoutpagesize.Items.Insert(0, new ListItem("Turn On", "1"));
        }
        else
        {
            uconfigturnonofflayoutpagesize.Items.Insert(0, new ListItem("Turn Off", "0"));
        }

        switch (layout)
        {
            case 1:
                uconfigpreflayout.Items.Insert(0, new ListItem("Rows", layout.ToString()));
                break;

            case 2:
                uconfigpreflayout.Items.Insert(0, new ListItem("Grid - 2 Columns", layout.ToString()));
                break;

            case 3:
                uconfigpreflayout.Items.Insert(0, new ListItem("Grid - 3 Columns", layout.ToString()));
                break;
        }

        if (UserFeaturesConfiguration.IsUserChooseToReceivePM)
        {
            uconfigreceivepm.Items.Insert(0, new ListItem("Receive PM - Yes", "1"));
        }
        else
        {
            uconfigreceivepm.Items.Insert(0, new ListItem("Receive PM - No", "0"));
        }

        if (UserFeaturesConfiguration.IsUserChooseToReceiveEmailAlertReceivePM)
        {
            uconfigreceivepmemailalert.Items.Insert(0, new ListItem("Receive PM Email - Yes", "1"));
        }
        else
        {
            uconfigreceivepmemailalert.Items.Insert(0, new ListItem("Receive PM Email - No", "0"));
        }

        uconfigprefpagesize.Items.Insert(0, new ListItem(pagesize + " Records Per Page", pagesize.ToString()));
    }

    private void GetCounters(ProviderUserDetails user)
    {
        if (user.PostedRecipeCount != 0)
        {
            lblviewallmysubmittedrecipe.Text = "<img src='images/editsmall.gif'>&nbsp;<a href=" + "findallrecipebyauthor.aspx?author=" + user.Username + ">Edit My Published Recipe</a>";
            lblviewallmysubmittedrecipe.Attributes.Add("onmouseover", "Tip('You have published (" + user.PostedRecipeCount + ") recipes.<br>Click to view or edit all of your published recipe.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblviewallmysubmittedrecipe.Attributes.Add("onmouseout", "UnTip()");
        }
        else
        {
            lblviewallmysubmittedrecipe.Text = "<img src='images/editsmall.gif'>&nbsp;<a href='javascript:void(0)'>Edit My Published Recipe</a>";
            lblviewallmysubmittedrecipe.Attributes.Add("onmouseover", "Tip('You have not published any recipe.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblviewallmysubmittedrecipe.Attributes.Add("onmouseout", "UnTip()");
        }

        if (user.PostedArticleCount != 0)
        {
            lblviewallmypublishedarticle.Text = "<img src='images/editsmall.gif'>&nbsp;<a href=" + "findallarticlebyauthor.aspx?author=" + user.Username + ">Edit My Published Article</a>";
            lblviewallmypublishedarticle.Attributes.Add("onmouseover", "Tip('You have published (" + user.PostedArticleCount + ") article.<br>Click to view or edit all of your published article.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblviewallmypublishedarticle.Attributes.Add("onmouseout", "UnTip()");
        }
        else
        {
            lblviewallmypublishedarticle.Text = "<img src='images/editsmall.gif'>&nbsp;<a href='javascript:void(0)'>Edit My Published Article</a>";
            lblviewallmypublishedarticle.Attributes.Add("onmouseover", "Tip('You have not published any article.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblviewallmypublishedarticle.Attributes.Add("onmouseout", "UnTip()");
        }
    }

    private void GetReturnFromUpdateMsg()
    {
        string item = "";
        int status = 0;

        //After updating settings in Myaccount.aspx
        //Make sure parameter is not empty. The passed in parameter mode is "Success"
        if (!string.IsNullOrEmpty(Request.QueryString["item"]) && !string.IsNullOrEmpty(Request.QueryString["status"]))
        {
            item = Request.QueryString["item"];
            status = (int)Util.Val(Request.QueryString["status"]);

            lblupdatesettingsmsg.Visible = true;

            //Public/Private redirection return message
            if (item == "fl")
            {
                switch (status)
                {
                    case 0:
                        lblupdatesettingsmsg.Text = "<div style='width: 450px; margin-left: 20px; margin-top: 15px; color: #800000;'><img src='images/availuname.gif'> Update Success - Your Friends List is now Private.</div>";
                        break;
                    case 1:
                        lblupdatesettingsmsg.Text = "<div style='width: 450px; margin-left: 20px; margin-top: 15px; color: #800000;'><img src='images/availuname.gif'> Update Success - Your Friends List is now Public.</div>";
                        break;
                }
            }
            else if (item == "cb")
            {
                switch (status)
                {
                    case 0:
                        lblupdatesettingsmsg.Text = "<div style='width: 450px; margin-left: 20px; margin-top: 15px; color: #800000;'><img src='images/availuname.gif'> Update Success - Your Cook Book is now Private.</div>";
                        break;
                    case 1:
                        lblupdatesettingsmsg.Text = "<div style='width: 450px; margin-left: 20px; margin-top: 15px; color: #800000;'><img src='images/availuname.gif'> Update Success - Your Cook Book is now Public.</div>";
                        break;
                }
            }
        }

        if (!string.IsNullOrEmpty(Request.QueryString["item"]) && !string.IsNullOrEmpty(Request.QueryString["numrecord"]))
        {
            item = Request.QueryString["item"];
            int numrecord = (int)Util.Val(Request.QueryString["numrecord"]);

            lblupdatesettingsmsg.Visible = true;

            if (item == "fln")
            {
                lblupdatesettingsmsg.Text = "<div style='width: 450px; margin-left: 20px; margin-top: 15px; color: #800000;'><img src='images/availuname.gif'> Update Success - Friends List in your profile page will now show " + numrecord + " friends.</div>";
            }
            else if (item == "cbn")
            {
                lblupdatesettingsmsg.Text = "<div style='width: 450px; margin-left: 20px; margin-top: 15px; color: #800000;'><img src='images/availuname.gif'> Update Success - Cook Book in your profile page will now show " + numrecord + " recipes.</div>";
            }
        }

        if (!string.IsNullOrEmpty(Request.QueryString["save"]))
        {
            lblupdatesettingsmsg.Visible = true;
            lblupdatesettingsmsg.Text = "<div style='width: 450px; margin-left: 20px; margin-top: 15px; color: #800000;'><img src='images/availuname.gif'> Update Success - Preferred layout and pagesize has been updated.</div>";
        }

        if (!string.IsNullOrEmpty(Request.QueryString["method"]) && !string.IsNullOrEmpty(Request.QueryString["setting"]))
        {
            string method = Request.QueryString["method"];
            int settingval = (int)Util.Val(Request.QueryString["setting"]);

            lblupdatesettingsmsg.Visible = true;

            switch (method)
            {
                case "receivepm":
                    if (settingval == 1)
                        lblupdatesettingsmsg.Text = "<div style='width: 450px; margin-left: 20px; margin-top: 15px; color: #800000;'><img src='images/availuname.gif'> Update Success - Your opted to receive a Private Message from other members.</div>";
                    else
                        lblupdatesettingsmsg.Text = "<div style='width: 450px; margin-left: 20px; margin-top: 15px; color: #800000;'><img src='images/availuname.gif'> Update Success - You opted not to receive any Private Message fropm other members.</div>";
                    break;

                case "pmemailalert":
                    if (settingval == 1)
                        lblupdatesettingsmsg.Text = "<div style='width: 450px; margin-left: 20px; margin-top: 15px; color: #800000;'><img src='images/availuname.gif'> Update Success - You opted to receive an email alert when someone send you a PM.</div>";
                    else
                        lblupdatesettingsmsg.Text = "<div style='width: 450px; margin-left: 20px; margin-top: 15px; color: #800000;'><img src='images/availuname.gif'> Update Success - You opted not to receive an email alert when someone send you a PM.</div>";
                    break;
            }
        }
    }

    public void UpdatePreferredLayoutPageSize_Click(object sender, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            Blogic.ConfigurePreferredLayoutPagesize(UserIdentity.UserID, Int32.Parse(Request.Form[uconfigturnonofflayoutpagesize.UniqueID]),
                Int32.Parse(Request.Form[uconfigpreflayout.UniqueID]), Int32.Parse(Request.Form[uconfigprefpagesize.UniqueID]));

            Response.Redirect("confirmlayoutchange.aspx?ReturnURL=" + this.Request.Url.PathAndQuery + "&flag=1");
        }
    }

    public void UpdateReceivePM_Click(object sender, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            Blogic.UpdateReceivePM(UserIdentity.UserID, Int32.Parse(Request.Form[uconfigreceivepm.UniqueID]));

            Response.Redirect("confirmupdatepmconfig.aspx?mode=receivepm&val=" + Int32.Parse(Request.Form[uconfigreceivepm.UniqueID]));
        }
    }

    public void UpdateReceivePMEmailAlert_Click(object sender, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            Blogic.UpdateReceivePMEmailAlert(UserIdentity.UserID, Int32.Parse(Request.Form[uconfigreceivepmemailalert.UniqueID]));

            Response.Redirect("confirmupdatepmconfig.aspx?mode=pmemailalert&val=" + Int32.Parse(Request.Form[uconfigreceivepmemailalert.UniqueID]));
        }
    }

    private void GetMetaTitleTagKeywords(string UserName)
    {
        Page.Header.Title = UserName + " Account Seetings Page";
        HtmlMeta metaTag = new HtmlMeta();
        metaTag.Name = "Keywords";
        metaTag.Content = UserName + " Account Seetings Page.";
        this.Header.Controls.Add(metaTag);
    }
}
