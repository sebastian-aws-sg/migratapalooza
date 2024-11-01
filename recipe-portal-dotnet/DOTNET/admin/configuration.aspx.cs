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
using XDRecipe.BL.Providers.Recipes;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.Common.Utilities;
using XDRecipe.BL.Providers.User;

public partial class configuration : BasePageAdmin
{
    protected void Page_Load(object sender, EventArgs e)
    {
        //Get admin username from the sessioan variable and place it in the label.
        lblusername.Text = "Welcome Admin:&nbsp;" + UserIdentity.AdminUsername;

        GetAdminCurrentEmailSetting();
        GetRecipeCommentCurrentSetting();
        GetArticleCommentCurrentSetting();
        GetLastViewedCurrentSetting();
        GetProfilePageCurrentSetting();
        GetCookBookNumberRecordsShowCurrentSetting();
        GetFriendsListNumberRecordsShowCurrentSetting();
    }

    private void GetAdminCurrentEmailSetting()
    {
        adminFromEmail.Text = SiteConfiguration.GetConfiguration.AdminFromEmail;
        adminToEmail.Text = SiteConfiguration.GetConfiguration.AdminToEmail;
    }

    public void UpdateAdminEmailSettings_Click(object sender, EventArgs e)
    {
        string stradminFromEmail;
        string stradminToEmail;
        stradminFromEmail = Request.Form["adminFromEmail"];
        stradminFromEmail = Request.Form["adminToEmail"];

        Blogic.ActionProcedureDataProvider.AdminUpdateEmailAndSMTPAddress(stradminFromEmail, stradminFromEmail);

        Response.Redirect("confirmemailupdate.aspx");
    }

    private void GetRecipeCommentCurrentSetting()
    {
        int getshowhidecomval = SiteConfiguration.GetConfiguration.ShowHideRecipeComment;

        if (getshowhidecomval == 1)
            ddlshowhide.Items.Insert(0, new ListItem("Recipe Comment is Enabled", "1"));
        else
            ddlshowhide.Items.Insert(0, new ListItem("Recipe Comment is Disabled", "0"));
    }

    private void GetArticleCommentCurrentSetting()
    {
        int getshowhidecomval = SiteConfiguration.GetConfiguration.ShowHideArticleComment;

        if (getshowhidecomval == 1)
            ddlshowhidearticlecomment.Items.Insert(0, new ListItem("Article Comment is Enabled", "1"));
        else
            ddlshowhidearticlecomment.Items.Insert(0, new ListItem("Article Comment is Disabled", "0"));
    }

    private void GetProfilePageCurrentSetting()
    {
        if (Blogic.IsProfilePagePublic)
            ddlconfigprofilepage.Items.Insert(0, new ListItem("Profile Page is Public", "1"));
        else
            ddlconfigprofilepage.Items.Insert(0, new ListItem("Profile Page is Private", "0"));
    }

    private void GetCookBookNumberRecordsShowCurrentSetting()
    {
        int GetCurrentNumRecords = SiteConfiguration.GetConfiguration.NumberOfrecipeInCookBook;

        ddlconfignumberofrecipeincookbook.Items.Insert(0, new ListItem("Current Setting is " + GetCurrentNumRecords + " Recipe per user", GetCurrentNumRecords.ToString()));
    }

    private void GetFriendsListNumberRecordsShowCurrentSetting()
    {
        int GetCurrentNumRecords = SiteConfiguration.GetConfiguration.NumberOfFriendsInFriendsList;

        ddlnumberoffriendsallowed.Items.Insert(0, new ListItem("Current Setting is " + GetCurrentNumRecords + " Friends per user", GetCurrentNumRecords.ToString()));
    }

    public void EnabledDisbledRecipeComment_Click(object sender, EventArgs e)
    {
        Blogic.ActionProcedureDataProvider.AdminUpdateShowHideComment(int.Parse(Request.Form["ddlshowhide"]));

        Response.Redirect("confirmcommentenabled.aspx");
    }


    public void EnabledDisableArticleComment_Click(object sender, EventArgs e)
    {
        Blogic.UpdateArticleCommentConfiguration(int.Parse(Request.Form["ddlshowhidearticlecomment"]));

        Response.Redirect("confirmcommentenabled.aspx");
    }

    public void ConfigProfilePage_Click(object sender, EventArgs e)
    {
        Blogic.ConfigureProfilePage(int.Parse(Request.Form["ddlconfigprofilepage"]));

        Response.Redirect("confirmconfigprofilepage.aspx");
    }

    public void CookBookSetting_Click(object sender, EventArgs e)
    {
        Blogic.UpdateConfigNumberOfRecordsInCookBookAdmin(int.Parse(Request.Form["ddlconfignumberofrecipeincookbook"]));

        Response.Redirect("confirmconfignumberofrecordsinCookBook.aspx");
    }

    public void FriendsListSetting_Click(object sender, EventArgs e)
    {
        Blogic.UpdateConfigNumberOfRecordsInFriendsListAdmin(int.Parse(Request.Form["ddlnumberoffriendsallowed"]));

        Response.Redirect("confirmconfignumberrecordsshowinFriendsList.aspx");
    }

    //Handles last viewed set hours dropdownlist selected index
    private void GetLastViewedCurrentSetting()
    {
        int GetLastViewedMin = SiteConfiguration.GetConfiguration.LastViewedNumberOfHoursSpan;

        switch (GetLastViewedMin)
        {
            case 1:
                ddllastviewedhours.Items.Insert(0, new ListItem("1 hour span", "60"));
                break;
            case 2:
                ddllastviewedhours.Items.Insert(0, new ListItem("2 hours span", "120"));
                break;
            case 3:
                ddllastviewedhours.Items.Insert(0, new ListItem("3 hours span", "180"));
                break;
            case 4:
                ddllastviewedhours.Items.Insert(0, new ListItem("4 hours span", "240"));
                break;
            case 5:
                ddllastviewedhours.Items.Insert(0, new ListItem("5 hours span", "300"));
                break;
            case 6:
                ddllastviewedhours.Items.Insert(0, new ListItem("6 hours span", "360"));
                break;
            case 7:
                ddllastviewedhours.Items.Insert(0, new ListItem("7 hours span", "420"));
                break;
            case 8:
                ddllastviewedhours.Items.Insert(0, new ListItem("8 hours span", "480"));
                break;
            case 16:
                ddllastviewedhours.Items.Insert(0, new ListItem("16 hours span", "960"));
                break;
            case 23:
                ddllastviewedhours.Items.Insert(0, new ListItem("23 hours span", "1860"));
                break;
        }
    }

    //Handles update last viewed hours
    public void AdminUpdateLastViewedHours_Click(object sender, EventArgs e)
    {
        Blogic.ActionProcedureDataProvider.AdminUpdateLastViewedHours(int.Parse(Request.Form["ddllastviewedhours"]));

        Response.Redirect("confirmupdatelastviewedhrs.aspx");
    }
}
