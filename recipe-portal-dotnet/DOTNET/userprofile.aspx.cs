#region XD World Recipe V 2.8
// FileName: userprofile.cs
// Author: Dexter Zafra
// Date Created: 2/20/2009
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
using XDRecipe.BL.Providers.Article;
using XDRecipe.BL.Providers.Recipes;

public partial class userprofile : BasePage
{
    private Utility Util
    {
        get { return new Utility(); }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        CheckUserIDQueryStringParameter();
        ShowProfileContentPublicOrPrivate();
        GetReturnMsgAfterAddingAFriend();

        try
        {
            int userID = (int)Util.Val(Request.QueryString["uid"]);
            int user_ID = UserIdentity.UserID;

            UserFeaturesConfiguration.Fetch(userID);

            ProviderUserDetails user = new ProviderUserDetails();

            user.FillUp(userID);

            userimage.ImageUrl = GetUserImage.GetImage(user.Photo).ToString();

            lblusernameheader.Text = user.Username + "'s Profile";
            lblusername.Text = user.Username;
            lblfullname.Text = user.FirstName + "&nbsp;" + user.LastName;
            lblprofileviews.Text = user.Hits.ToString();
            lbllastupdate.Text = Utility.FormatDate(user.LastUpdated);
            lbljoined.Text = Utility.FormatDate(user.DateJoined);
            lbldob.Text = Utility.FormatDate(user.DOB);
            lblcommentedrecipe.Text = user.CommentRecipeCount.ToString();
            lblaboutme.Text = Util.FormatText(user.AboutMe);
            lblpostedrecipecount.Text = user.PostedRecipeCount.ToString();
            lblpostedarticlecount.Text = user.PostedArticleCount.ToString();
            lblcommentarticle.Text = user.CommentArticleCount.ToString();
            lblfavfood1.Text = user.FavoriteFoods1;
            lblfavfood2.Text = user.FavoriteFoods2;
            lblfavfood3.Text = user.FavoriteFoods3;
            lblnosavedrecipe.Visible = true;
            lblcity.Text = "NA";
            lblstate.Text = "NA";
            lblnosavedrecipe.Text = "No saved recipe.";
            lblnofriends.Text = "No friends added.";
            lbllastlogin.Text = "NA";

            GetUserProfileInformationWithLink(user);
            ShowAddToFriendsListLinkButton(user, userID);
            ShowSendPrivateMessageLink(user);
            ShowProfileFriendsListQuickView(user, userID);
            ShowProfileCookBookQuickView(user, userID);
            GetMetaTitleTagKeywords(user.Username);
            GetUserLast5Recipe(userID, user.PostedRecipeCount, user.Username);
            GetUserLast5Article(userID, user.PostedArticleCount, user.Username);
            UpdateUserLastVisit(user);

            user = null;
        }
        catch
        {
            Server.Transfer("userdoesnotexists.aspx");
        }
    }

    private void CheckUserIDQueryStringParameter()
    {
        if (string.IsNullOrEmpty(Request.QueryString["uid"]) || !Utility.IsNumeric(Request.QueryString["uid"]))
        {
            Response.Redirect("userdoesnotexists.aspx");
        }
    }

    private void GetReturnMsgAfterAddingAFriend()
    {
        string mode = "notaddingafriend";

        //After adding a friend redirect back to this page.
        //Make sure parameter is not empty. The passed in parameter mode is "Success"
        if (!string.IsNullOrEmpty(Request.QueryString["mode"]))
        {
            //Validate to make sure it does not contain illegal characters i.e. HTML tags.
            Util.SecureQueryString(Request.QueryString["mode"]);

            mode = Request.QueryString["mode"];

            lbladdfriendsuccessmsg.Visible = true;
            lbladdfriendsuccessmsg.Text = "<div style='text-align: center; margin-top: 15px; color: #800000;'>Add Friend Success. Waiting for user approval.</div>";
        }
    }

    private void ShowProfileContentPublicOrPrivate()
    {
        //Check whether user profile is set to public viewing. Anyone inlcuding non-member can view the profile.
        if (Blogic.IsProfilePagePublic)
        {
            PanelIsProfilePublicOrPrivate.Visible = true;
        }
        else
        {   //If is not set to public. Only member can view the page. We also add the admin session.
            //Admin access this page from the Admin Membership manager.
            if (Authentication.IsUserAuthenticated || CookieLoginHelper.IsLoginAdminSessionExists)
            {
                PanelIsProfilePublicOrPrivate.Visible = true;
            }
            else
            {
                PanelIsProfilePublicOrPrivate.Visible = false;
                lblyouarenotauthorizedtoview.Visible = true;
                lblyouarenotauthorizedtoview.Text = "<div style='margin-top: 12px; margin-bottom: 7px;'><img src='images/lock.gif' align='absmiddle'> You are not authorize to view this profile. Please login to view this profile.</div>";
            }
        }
    }

    private void UpdateUserLastVisit(ProviderUserDetails user)
    {
        //Show last visit to registered users only.
        if (Authentication.IsUserAuthenticated)
        {
            //Update user last visit date.
            Blogic.UpdateUserLastLogin(UserIdentity.UserID);

            lbllastlogin.Text = Utility.FormatDate(user.LastLogin);
        }
    }

    private void GetUserProfileInformationWithLink(ProviderUserDetails user)
    {
        lblcountry.Text = "<a title href=searchuser.aspx?input=" + user.Country.Replace(" ", "+") + "&condition=6>" + user.Country + "</a>";
        lblcountry.Attributes.Add("onmouseover", "Tip('Browse all users who lives in the country of <b>" + user.Country + "</b>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        lblcountry.Attributes.Add("onmouseout", "UnTip()");

        if (user.City != "NA")
        {
            lblcity.Text = "<a title href=searchuser.aspx?input=" + user.City.Replace(" ", "+") + "&condition=4>" + user.City + "</a>";
            lblcity.Attributes.Add("onmouseover", "Tip('Browse all users who live in the city of <b>" + user.City + "</b>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblcity.Attributes.Add("onmouseout", "UnTip()");
        }

        if (user.State != "NA")
        {
            lblstate.Text = "<a title href=searchuser.aspx?input=" + user.State.Replace(" ", "+") + "&condition=5>" + user.State + "</a>";
            lblstate.Attributes.Add("onmouseover", "Tip('Browse all users who lives in the state of <b>" + user.State + "</b>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblstate.Attributes.Add("onmouseout", "UnTip()");
        }

        if (user.FavoriteFoods1 != "NA")
        {
            lblfavfood1.Text = "<a title='Search' href=" + "searchrecipe.aspx?find=" + user.FavoriteFoods1.Replace(" ", "+") + "&catid=0>" + user.FavoriteFoods1 + "</a>";
            lblfavfood1.Attributes.Add("onmouseover", "Tip('Search (<b>" + user.FavoriteFoods1.Replace(" ", "+") + "</b>) recipe.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblfavfood1.Attributes.Add("onmouseout", "UnTip()");
        }

        if (user.FavoriteFoods2 != "NA")
        {
            lblfavfood2.Text = "<a title='Search' href=" + "searchrecipe.aspx?find=" + user.FavoriteFoods2.Replace(" ", "+") + "&catid=0>" + user.FavoriteFoods2 + "</a>";
            lblfavfood2.Attributes.Add("onmouseover", "Tip('Search (<b>" + user.FavoriteFoods1.Replace(" ", "+") + "</b>) recipe.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblfavfood2.Attributes.Add("onmouseout", "UnTip()");
        }

        if (user.FavoriteFoods3 != "NA")
        {
            lblfavfood3.Text = "<a title='Search' href=" + "searchrecipe.aspx?find=" + user.FavoriteFoods3.Replace(" ", "+") + "&catid=0>" + user.FavoriteFoods3 + "</a>";
            lblfavfood3.Attributes.Add("onmouseover", "Tip('Search (<b>" + user.FavoriteFoods1.Replace(" ", "+") + "</b>) recipe.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblfavfood3.Attributes.Add("onmouseout", "UnTip()");
        }

        if (user.PostedRecipeCount != 0)
        {
            lblpostedrecipecount.Text = "<a href=" + "findallrecipebyauthor.aspx?author=" + user.Username + ">" + user.PostedRecipeCount + "</a>";
            lblpostedrecipecount.Attributes.Add("onmouseover", "Tip('Browse all recipe submitted by <b>" + user.Username + "</b>.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblpostedrecipecount.Attributes.Add("onmouseout", "UnTip()");
        }

        if (user.PostedArticleCount != 0)
        {
            lblpostedarticlecount.Text = "<a href=" + "findallarticlebyauthor.aspx?author=" + user.Username + ">" + user.PostedArticleCount + "</a>";
            lblpostedarticlecount.Attributes.Add("onmouseover", "Tip('Browse all article submitted by <b>" + user.Username + "</b>.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblpostedarticlecount.Attributes.Add("onmouseout", "UnTip()");
        }

        if (user.CommentRecipeCount != 0)
        {
            lblcommentedrecipe.Text = "<a href=" + "findallrecipecommentedbyuser.aspx?commentauthor=" + user.Username + ">" + user.CommentRecipeCount + "</a>";
            lblcommentedrecipe.Attributes.Add("onmouseover", "Tip('Browse all recipe commented by <b>" + user.Username + "</b>.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblcommentedrecipe.Attributes.Add("onmouseout", "UnTip()");
        }

        if (user.CommentArticleCount != 0)
        {
            lblcommentarticle.Text = "<a href=" + "findallarticlecommentbyuser.aspx?author=" + user.Username + ">" + user.CommentArticleCount + "</a>";
            lblcommentarticle.Attributes.Add("onmouseover", "Tip('Browse all article commented by <b>" + user.Username + "</b>.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblcommentarticle.Attributes.Add("onmouseout", "UnTip()");
        }

        if (user.SavedrecipeCount != 0)
            lblnosavedrecipe.Visible = false;

        if (user.FriendsCount != 0)
            lblnofriends.Visible = false;

        if (user.Website.IndexOf("http://") == -1)
            lblwebsite.Text = "<a title='Visit website' target='_blank' href=" + "http://" + user.Website + ">" + user.Website + "</a>";

        if (user.Website.IndexOf("http://") != -1)
            lblwebsite.Text = "<a title='Visit website' target='_blank' href=" + user.Website + ">" + user.Website + "</a>";

        if (user.Website == "NA")
            lblwebsite.Text = user.Website;
    }

    private void ShowSendPrivateMessageLink(ProviderUserDetails user)
    {
        if (Authentication.IsUserAuthenticated)
        {
            int userID = (int)Util.Val(Request.QueryString["uid"]);

            UserFeaturesConfiguration.Fetch(userID);

            if (UserFeaturesConfiguration.IsUserChooseToReceivePM)
            {
                lblsendpm.Text = "<a href=pmsend.aspx?method=newmsg&sendto=" + user.Username + ">Send Private Message</a>";
                lblsendpm.Attributes.Add("onmouseover", "Tip('Send a PM to (<b>" + user.Username + "</b>).', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                lblsendpm.Attributes.Add("onmouseout", "UnTip()");
            }
            else
            {
                lblsendpm.Text = "<a href='javascript:void(0)'>Send Private Message</a>";
                lblsendpm.Attributes.Add("onclick", "csscody.alert('<b>Denied Send PM</b><br>Sorry! <b>" + user.Username + "</b> opted not to receive a private message.');return false;");
                lblsendpm.Attributes.Add("onmouseover", "Tip('Sorry! <b>" + user.Username + "</b> opted not to receive a private message.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                lblsendpm.Attributes.Add("onmouseout", "UnTip()");
            }
        }
        else
        {
            lblsendpm.Text = "<a href='javascript:void(0)'>Send Private Message</a>";
            lblsendpm.Attributes.Add("onclick", "csscody.alert('<b>Denied Send PM</b><br>Sorry! You must login to send a PM to (<b>" + user.Username + "</b>).');return false;");
            lblsendpm.Attributes.Add("onmouseover", "Tip('Sorry! You must login to send a PM to (<b>" + user.Username + "</b>).', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblsendpm.Attributes.Add("onmouseout", "UnTip()");
        }
    }

    private void ShowAddToFriendsListLinkButton(ProviderUserDetails user, int userID)
    {
        string mode = "";

        if (!string.IsNullOrEmpty(Request.QueryString["mode"]))
        {
            mode = Request.QueryString["mode"];
        }

        //Prevent adding himself/herself to the Friends List.
        if (Authentication.IsUserAuthenticated && (UserIdentity.UserID == userID))
        {
            lbladdtofriendslist.Visible = true;
            lbladdtofriendslist.Attributes.Add("onclick", "csscody.alert('<b>Denied Save</b><br>Sorry! You cannot add yourself.');return false;");
            lbladdtofriendslist.Attributes.Add("onmouseover", "Tip('Sorry! You cannot add<br>yourself to your Friends List.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lbladdtofriendslist.Attributes.Add("onmouseout", "UnTip()");
            lbladdtofriendslist.Text = "<a title='Sorry! You cannot add yourself.' href='javascript:void(0)'>Add to Friends List</a>";
        }
        else if (Authentication.IsUserAuthenticated && (UserIdentity.UserID != userID))
        {
            //Check mode - adding a friend.
            //This prevent successive clicking after adding a friend
            if (mode == "Success")
            {
                LinkButtonAddfriendLogin.Visible = false;
                lbladdtofriendslist.Visible = true;
                lbladdtofriendslist.Attributes.Add("onclick", "csscody.alert('<b>Denied Save</b><br>Sorry! (<b>" + user.Username + "</b>) is already in your Friends List.');return false;");
                lbladdtofriendslist.Attributes.Add("onmouseover", "Tip('Sorry! (<b>" + user.Username + "</b>) is already in your Friends List.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                lbladdtofriendslist.Attributes.Add("onmouseout", "UnTip()");
                lbladdtofriendslist.Text = "<a title='Sorry! You already added this user to your Friends List.' href='javascript:void(0)'>Add to Friends List</a>";
            }
            //Check if the user is already in friends List. If exists, hide linkbutton and show the label link.
            else if (Blogic.IsFriendExists(UserIdentity.UserID, userID))
            {
                LinkButtonAddfriendLogin.Visible = false;
                lbladdtofriendslist.Visible = true;
                lbladdtofriendslist.Attributes.Add("onclick", "csscody.alert('<b>Denied Add a Friend</b><br>Sorry! (<b>" + user.Username + "</b>) is already in your Friends List.');return false;");
                lbladdtofriendslist.Attributes.Add("onmouseover", "Tip('Sorry! (<b>" + user.Username + "</b>) is already in your Friends List.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                lbladdtofriendslist.Attributes.Add("onmouseout", "UnTip()");
                lbladdtofriendslist.Text = "<a title='This user already in your Friends List.' href='javascript:void(0)'>Add to Friends List</a>";
            }
            else
            {
                int NumrecordsAllowedInFriendsList = SiteConfiguration.GetConfiguration.NumberOfFriendsInFriendsList;

                if (user.FriendsCount > NumrecordsAllowedInFriendsList)
                {
                    LinkButtonAddfriendLogin.Visible = true;
                    LinkButtonAddfriendLogin.Text = "Add to Friends List";
                    LinkButtonAddfriendLogin.Attributes.Add("onclick", "csscody.alert('<b>Denied Add a Friend</b><br>You cannot add anymore friend. You have reached the maximum number of friends allowed to add in your Friends List.');return false;");
                    LinkButtonAddfriendLogin.Attributes.Add("onmouseover", "Tip('Cannot add a friend. You have reached the maximum allowed.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                    LinkButtonAddfriendLogin.Attributes.Add("onmouseout", "UnTip()");
                }
                else
                {
                    LinkButtonAddfriendLogin.Visible = true;
                    LinkButtonAddfriendLogin.Text = "Add to Friends List";
                    LinkButtonAddfriendLogin.Attributes.Add("onmouseover", "Tip('Add (<b>" + user.Username + "</b>) to My Friends List.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                    LinkButtonAddfriendLogin.Attributes.Add("onmouseout", "UnTip()");
                }
            }
        }
        else
        {
            lbladdtofriendslist.Visible = true;
            lbladdtofriendslist.Attributes.Add("onclick", "csscody.alert('<b>Denied Save</b><br>You must login to add (<b>" + user.Username + "</b>) into your Friends List.');return false;");
            lbladdtofriendslist.Attributes.Add("onmouseover", "Tip('You must login to add (<b>" + user.Username + "</b>) into your Friends List.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lbladdtofriendslist.Attributes.Add("onmouseout", "UnTip()");
            lbladdtofriendslist.Text = "<a title='You must login to add this user into your Friends List.' href='javascript:void(0)'>Add to Friends List</a>";
        }
    }

    private void ShowProfileFriendsListQuickView(ProviderUserDetails user, int userID)
    {
        ProviderFriendsList MyFriends = new ProviderFriendsList(userID, UserFeaturesConfiguration.GetNumRecordsFriendsListShow);

        //Check whether the user wants to make Friends List quick view public in profile page.
        //Public view - everyone who access the profile including non-member will be able to see the quick view.
        //Quick view only show up to 20 items maximun. The main Friends List page can show up to 50 or more...
        if (UserFeaturesConfiguration.IsPublicFriendsListQuickView(userID))
        {
            MyFriendsListPanel.Visible = true;

            MyFriendsList.DataSource = MyFriends.GetFriendsList();
            MyFriendsList.DataBind();

            lblmyfriendscount.Text = "(" + user.FriendsCount.ToString() + ")";
        }
        else
        {
            //Private only - only the owner will see the quick view.
            if (Authentication.IsUserAuthenticated && (UserIdentity.UserID == userID))
            {
                MyFriendsListPanel.Visible = true;

                MyFriendsList.DataSource = MyFriends.GetFriendsList();
                MyFriendsList.DataBind();

                lblmyfriendscount.Text = "(" + user.FriendsCount.ToString() + ")";
            }
        }

        MyFriends = null;
    }

    private void ShowProfileCookBookQuickView(ProviderUserDetails user, int userID)
    {
        UsersCookBook CookBook = new UsersCookBook(userID, UserFeaturesConfiguration.GetNumRecordsCookBookShow);

        //Check whether the user wants to make the CookBook quick view public in profile page.
        //Public view - everyone who access the profile including non-member will be able to see the quick view.
        //Quick view only show up to 20 items maximun. The main Cook Book page can show up to 50 or more...
        if (UserFeaturesConfiguration.IsPublicCookBookQuickView(userID))
        {
            MyCookBookPanel.Visible = true;

            SavedUserCookBookProfile.DataSource = CookBook.GetUserRecipeInCookBook();
            SavedUserCookBookProfile.DataBind();

            lblmycookbookcount.Text = "(" + user.SavedrecipeCount.ToString() + ")";
        }
        else
        {
            //Private only - only the owner will see the quick view.
            if (Authentication.IsUserAuthenticated && (UserIdentity.UserID == userID))
            {
                MyCookBookPanel.Visible = true;

                SavedUserCookBookProfile.DataSource = CookBook.GetUserRecipeInCookBook();
                SavedUserCookBookProfile.DataBind();

                lblmycookbookcount.Text = "(" + user.SavedrecipeCount.ToString() + ")";

            }
        }

        CookBook = null;
    }

    private void GetUserLast5Recipe(int UserID, int Counter, string UserName)
    {
        if (Counter != 0)
        {
            PanelLast5recipeByUser.Visible = true;

            ProviderGetLast5RecipeByUser GetLast5Recipe = ProviderGetLast5RecipeByUser.GetInstance();
            GetLast5Recipe.Param(UserID);

            Last5RecipeByUser.DataSource = GetLast5Recipe.GetRecipe();
            Last5RecipeByUser.DataBind();
        }

        if (Counter > 5)
        {
            lblmorerecipebyuser.Visible = true;
            lblmorerecipebyuser.Text = "<img src='images/arrow7.gif'>&nbsp;<a href=findallrecipebyauthor.aspx?author=" + UserName + ">More...</a>";
            lblmorerecipebyuser.Attributes.Add("onmouseover", "Tip('Browse all recipe published by <b>" + UserName + "</b>.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblmorerecipebyuser.Attributes.Add("onmouseout", "UnTip()");
        }
    }

    private void GetUserLast5Article(int UserID, int Counter, string UserName)
    {
        if (Counter != 0)
        {
            PanelLast5ArticleByUser.Visible = true;

            ProviderLast5ArticlePublishedByUser GetLast5Article = ProviderLast5ArticlePublishedByUser.GetInstance();
            GetLast5Article.Param(UserID);

            Last5ArticleByUser.DataSource = GetLast5Article.GetArticle();
            Last5ArticleByUser.DataBind();
        }

        if (Counter > 5)
        {
            lbllast5articlebyuser.Visible = true;
            lbllast5articlebyuser.Text = "<img src='images/arrow7.gif'>&nbsp;<a href=findallarticlebyauthor.aspx?author=" + UserName + ">More...</a>";
            lbllast5articlebyuser.Attributes.Add("onmouseover", "Tip('Browse all article published by <b>" + UserName + "</b>.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lbllast5articlebyuser.Attributes.Add("onmouseout", "UnTip()");
        }
    }

    public void Add_Friend(Object s, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            int FriendID = int.Parse(Request.QueryString["uid"]);

            Blogic.AddNewFriend(UserIdentity.UserID, FriendID);

            AddAFriendSendEmailNotification();

            Response.Redirect("userprofile.aspx?uid=" + FriendID + "&mode=Success");
        }
    }

    private void AddAFriendSendEmailNotification()
    {
        int FriendID = int.Parse(Request.QueryString["uid"]);

        ProviderUserDetails user = new ProviderUserDetails();

        user.FillUp(FriendID);

        EmailTemplate Email = new EmailTemplate();

        Email.AddAFriendEmailNotification(user.Email, user.Username, UserIdentity.UserName);

        Email = null;
        user = null;
    }

    private void GetMetaTitleTagKeywords(string UserName)
    {
        Page.Header.Title = UserName + "' Member Profile";
        HtmlMeta metaTag = new HtmlMeta();
        metaTag.Name = "Keywords";
        metaTag.Content = UserName + " member profile.";
        this.Header.Controls.Add(metaTag);
    }
}
