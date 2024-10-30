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
using XDRecipe.BL.Providers.CookBooks;
using XDRecipe.Security;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Common.Utilities;

public partial class userconfigfetaures : System.Web.UI.UserControl
{
    protected void Page_Load(object sender, EventArgs e)
    {
        int UserID = UserIdentity.UserID;

        UserFeaturesConfiguration.Fetch(UserID);

        int CurentRecordsShowCookBook = UserFeaturesConfiguration.GetNumRecordsCookBookShow;
        int CurentRecordsShowFriendsList = UserFeaturesConfiguration.GetNumRecordsFriendsListShow;

        uconfigcookbookddl.Items.Insert(0, new ListItem(CurentRecordsShowCookBook + " Recipes", CurentRecordsShowCookBook.ToString()));
        uconfigcookbookddl.Items.Insert(1, new ListItem("5 Recipes", "5"));
        uconfigcookbookddl.Items.Insert(2, new ListItem("10 Recipes", "10"));
        uconfigcookbookddl.Items.Insert(3, new ListItem("15 Recipes", "15"));
        uconfigcookbookddl.Items.Insert(4, new ListItem("20 Recipes", "20"));

        uconfigfriendslistddl.Items.Insert(0, new ListItem(CurentRecordsShowFriendsList + " Friends", CurentRecordsShowFriendsList.ToString()));
        uconfigfriendslistddl.Items.Insert(1, new ListItem("5 Friends", "5"));
        uconfigfriendslistddl.Items.Insert(2, new ListItem("10 Friends", "10"));
        uconfigfriendslistddl.Items.Insert(3, new ListItem("15 Friends", "15"));
        uconfigfriendslistddl.Items.Insert(4, new ListItem("20 Friends", "20"));

        if (UserFeaturesConfiguration.IsPublicFriendsListQuickView(UserID))
            uconfigshowhidefriendslistddl.Items.Insert(0, new ListItem("Public FriendsList", "1"));
        else
            uconfigshowhidefriendslistddl.Items.Insert(0, new ListItem("Private FriendsList", "0"));

        if (UserFeaturesConfiguration.IsPublicCookBookQuickView(UserID))
            uconfigshowhidecookbookddl.Items.Insert(0, new ListItem("Public CookBook", "1"));
        else
            uconfigshowhidecookbookddl.Items.Insert(0, new ListItem("Private CookBook", "0"));
    }

    public void UpdateShowHideFriendsList_Click(object sender, EventArgs e)
    {
        Blogic.UpdateShowHideFriendsListInProfile(UserIdentity.UserID, Int32.Parse(Request.Form[uconfigshowhidefriendslistddl.UniqueID]));

        Response.Redirect("myaccount.aspx?item=fl&status=" + Int32.Parse(Request.Form[uconfigshowhidefriendslistddl.UniqueID]));

    }

    public void UpdateShowHideCookBook_Click(object sender, EventArgs e)
    {
        Blogic.UpdateShowHideCookBookInProfile(UserIdentity.UserID, Int32.Parse(Request.Form[uconfigshowhidecookbookddl.UniqueID]));

        Response.Redirect("myaccount.aspx?item=cb&status=" + Int32.Parse(Request.Form[uconfigshowhidecookbookddl.UniqueID]));
    }

    public void UpdateNumRecordsCookBook_Click(object sender, EventArgs e)
    {
        //Referesh cached
        Caching.PurgeCacheItems("MyCookBook_SideMenu_" + UserIdentity.UserID);

        Blogic.UpdateNumberRecordsCookBookProfile(UserIdentity.UserID, Int32.Parse(Request.Form[uconfigcookbookddl.UniqueID]));

        Response.Redirect("myaccount.aspx?item=cbn&numrecord=" + Int32.Parse(Request.Form[uconfigcookbookddl.UniqueID]));
    }

    public void UpdateNumRecordsFriendsList_Click(object sender, EventArgs e)
    {
        Blogic.UpdateNumberRecordsFriendsListProfile(UserIdentity.UserID, Int32.Parse(Request.Form[uconfigfriendslistddl.UniqueID]));

        Response.Redirect("myaccount.aspx?item=fln&numrecord=" + Int32.Parse(Request.Form[uconfigfriendslistddl.UniqueID]));
    }
}
