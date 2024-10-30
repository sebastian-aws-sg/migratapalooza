#region XD World Recipe V 2.8
// FileName: Recipedetail.cs
// Author: Dexter Zafra
// Date Created: 5/23/2008
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
using XDRecipe.BL.Providers.Recipes;
using XDRecipe.BL.Providers.Comments;
using XDRecipe.BL.Providers.User;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.BL.Providers.CookBooks;
using XDRecipe.Common.Utilities;
using XDRecipe.Security;

public partial class recipedetail : BasePage
{
    public string strRName;
    public string strCName;
    public int RecCatId;
    public string strBookmarkURL;
    public string strRecipeImage;
    public int RecipeSection;
    public string strAddCookBookURL;
    public string strAddCookbookLinkTooltip;
    public string strAddCookBookURLWithJavaScriptCall;
    public DateTime dateposted;

    private Utility Util
    {
        get { return new Utility(); }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        RecipeDetails Recipe = new RecipeDetails();

        int RecipeID = (int)Util.Val(Request.QueryString["id"]);
        Recipe.Approved = constant.ApprovedRecipe;
        Recipe.FillUp(RecipeID);

        RecCatId = Recipe.CatID;
        strRName = Recipe.RecipeName;
        strCName = Recipe.Category;
        RecipeSection = constant.intRecipe;
        dateposted = Recipe.Date;

        CommentLink.Text = "Comments (" + Recipe.CountComments + ")";
        CommentLink.Attributes.Add("onmouseover", "Tip('Read or write comments.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        CommentLink.Attributes.Add("onmouseout", "UnTip()");
        lbcountcomment.Text = Recipe.CountComments.ToString();
        lblname.Text = Recipe.RecipeName;
        lblauthor.Text = "<a href=" + "userprofile.aspx?uid=" + Recipe.UID + ">" + Recipe.Author + "</a>";
        lblhits.Text = string.Format("{0:#,###}", Recipe.Hits);
        lblrating.Text = Recipe.Rating;
        lblvotescount.Text = Recipe.NoRates;
        lblcategorytop.Text = Recipe.Category;
        lbldate.Text = Utility.FormatDate(Recipe.Date);
        lblIngredients.Text = Util.FormatText(Recipe.Ingredients);
        lblInstructions.Text = Util.FormatText(Recipe.Instructions);
        starimage.ImageUrl = Utility.GetStarImage(Recipe.Rating);
        lblauthor.Attributes.Add("onmouseover", "Tip('View all recipe submitted by <b>" + Recipe.Author + "</b>.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        lblauthor.Attributes.Add("onmouseout", "UnTip()");
        lblauthor.Attributes.Add("onclick", "Popup('AjaxRequest/popUpAjax.aspx?uid=" + Recipe.UID + "&author=" + Recipe.Author + "',this);return false");

        strBookmarkURL = Bookmark.URL;

        GetMetaKeywords(DynamicKeywords.Keywords(constant.intRecipeDynamicKeywordDetails, Recipe.RecipeName.ToString() + " recipe, " + Recipe.Category.ToString() + " recipe"));

        ShowRecipeImage(RecipeID);
        AddToCookBookLinkButton(Recipe.RecipeName);
        ShowEditLink(Recipe);
        ShowNewPopularImage(RecipeID);
        GetRelatedrecipes(Recipe.CatID);
        GetUserRecipeCookieRating(RecipeID);
        GetComments(RecipeID);
        ShowCommentFormIfLogin();

        Recipe = null;
    }

    private void AddToCookBookLinkButton(string RecipeName)
    {
        if (Authentication.IsUserAuthenticated)
        {
            UsersCookBookMain UserCookBook = new UsersCookBookMain(UserIdentity.UserID);

            int NumRecordsCookBookAllowed = SiteConfiguration.GetConfiguration.NumberOfrecipeInCookBook;

            addtoCookBook.Visible = false;
            LinkButtonAddtoCookBookLogin.Visible = true;

            if (UserCookBook.TotalCount > NumRecordsCookBookAllowed)
            {
                LinkButtonAddtoCookBookLogin.Text = "Add to my Cookbook";
                LinkButtonAddtoCookBookLogin.Attributes.Add("onclick", "csscody.alert('<b>Denied Save Recipe</b><br>You cannot save anymore recipe. You have reached the maximum number of recipe allowed to save into your Cook Book.');return false;");
                LinkButtonAddtoCookBookLogin.Attributes.Add("onmouseover", "Tip('Cannot save recipe. You have reached the maximum allowed.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                LinkButtonAddtoCookBookLogin.Attributes.Add("onmouseout", "UnTip()");
            }
            else
            {
                LinkButtonAddtoCookBookLogin.Text = "Add to my Cookbook";
                LinkButtonAddtoCookBookLogin.Attributes.Add("onmouseover", "Tip('Add (<b>" + RecipeName + "</b>) recipe to My CookBook.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                LinkButtonAddtoCookBookLogin.Attributes.Add("onmouseout", "UnTip()");
            }

            UserCookBook = null;
        }
        else
        {
            addtoCookBook.Visible = true;
            saveicon.AlternateText = "Add to my Cookbook";
            addtoCookBook.Text = "<a href='javascript:void(0)'>Add to my CookBook</a>";
            addtoCookBook.Attributes.Add("onclick", "csscody.alert('<b>Denied Save Recipe</b><br>Login to add (<b>" + RecipeName + "</b>) recipe into your Cookbook.');return false;");
            addtoCookBook.Attributes.Add("onmouseover", "Tip('Login to add this recipe into your Cokkbook.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            addtoCookBook.Attributes.Add("onmouseout", "UnTip()");
        }
    }

    private void ShowRecipeImage(int RecipeID)
    {
        recipeimage.Visible = false;

        string strRecipeImage = GetRecipeImage.GetImageForRecipeDetails(RecipeID);

        if (!string.IsNullOrEmpty(strRecipeImage))
        {
            recipeimage.Visible = true;
            recipeimage.ImageUrl = strRecipeImage.ToString();
        }
    }

    private void ShowCommentFormIfLogin()
    {
        if (Authentication.IsUserAuthenticated)
        {
            Panel3.Visible = true;
            lbllogintocomment.Visible = false;

            AUTHOR.Value = UserIdentity.UserName;
            EMAIL.Value = UserIdentity.UserEmail;
            lblUsernameComment.Text = UserIdentity.UserName;
            lbluserCommentEmail.Text = UserIdentity.UserEmail;
        }
        else
        {
            Panel3.Visible = false;
            lbllogintocomment.Visible = true;
            lbllogintocomment.Text = "To be able to leave a comment please login at the top right corner or " + "<a href='registration.aspx'>Register</a>.";
        }
    }

    private void ShowEditLink(RecipeDetails Recipe)
    {
        if (Authentication.IsUserAuthenticated && Recipe.UID == UserIdentity.UserID)
        {
            editrecipelink.Visible = true;
            editrecipelink.Text = "<img src='images/icon_pencil.gif' alt='Edit' border='0'> Edit";
            editrecipelink.NavigateUrl = "editrecipe.aspx?id=" + Recipe.ID;
            editrecipelink.Attributes.Add("onmouseover", "Tip('Edit my published <b>" + Recipe.RecipeName + "</b> recipe.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            editrecipelink.Attributes.Add("onmouseout", "UnTip()");
        }
    }

    public void Add_CookBook(Object s, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            int RecipeID = (int)Util.Val(Request.QueryString["id"]);
            Caching.PurgeCacheItems("MyCookBook_SideMenu_" + UserIdentity.UserID);
            Blogic.InsertRecipeToCookBook(UserIdentity.UserID, RecipeID);
            Response.Redirect("mycookbook.aspx");
        }
    }

    private void GetUserRecipeCookieRating(int RecipeID)
    {
        CookieRating GetCookie = new CookieRating(constant.intRecipe, RecipeID, PlaceHolder1);
        GetCookie.GetUserCookieRating();
        GetCookie = null;
    }

    private void ShowNewPopularImage(int RecipeID)
    {
        Utility.GetIdentifyItemNewPopular(dateposted, PlaceHolder1, RecipeID);
    }

    private void GetRelatedrecipes(int CatID)
    {
        RelatedRecipes.DataSource = RelatedRecipeProvider.GetRelatedRecipes(CatID);
        RelatedRecipes.DataBind();
    }

    private void GetComments(int RecipeID)
    {
        RecipeComments Comment = new RecipeComments(RecipeID, RecComments, PlaceHolder1);
        Comment.FillUp();
        Comment = null;
    }

    private void GetMetaKeywords(string strPageTitleAndKeywords)
    {
        Page.Header.Title = strPageTitleAndKeywords;
        HtmlMeta metaTag = new HtmlMeta();
        metaTag.Name = "Keywords";
        metaTag.Content = strPageTitleAndKeywords;
        this.Header.Controls.Add(metaTag);
    }

    public void Add_Comment(Object s, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            if (Page.IsValid && (txtsecfield.Text.ToString() == Session["randomStr"].ToString()))
            {
                RecipeCommentsRepository comment = new RecipeCommentsRepository();

                comment.ID = (int)Util.Val(Request.QueryString["id"]);
                comment.UID = UserIdentity.UserID;

                comment.Author = Util.FormatTextForInput(Request.Form[AUTHOR.UniqueID]);
                comment.Email = Util.FormatTextForInput(Request.Form[EMAIL.UniqueID]);
                comment.Comments = Util.FormatTextForInput(Request.Form[COMMENTS.UniqueID]);

                if (comment.Comments.Length == 0)
                {
                    lbvalenght.Text = "<br>Error: Comment is empty, please enter your comment.";
                    lbvalenght.Visible = true;
                    txtsecfield.Text = "";
                    return;
                }
                if (comment.Comments.Length > 350)
                {
                    lbvalenght.Text = "<br>Error: Comments is too long. Max of 350 characters.";
                    lbvalenght.Visible = true;
                    txtsecfield.Text = "";
                    return;
                }

                if (comment.Add(comment) != 0)
                {
                    lbvalenght.Text = "A database error occured while processing your comment.";
                    return;
                }

                EmailCommentNotificationToAdministrator(comment.ID, strRName);

                comment = null;

                Response.Redirect("commentpostconfirmation.aspx?ReturnURL=" + this.Request.Url.PathAndQuery);
            }
            else
            {
                lbvalenght.Text = "<br>Invalid security code. Make sure you type it correctly.";
                lbvalenght.Visible = true;
                txtsecfield.Text = "";

                lblinvalidsecode.Text = "Invalid security code. Make sure you type it correctly.";
                lblinvalidsecode.Visible = true;
            }
        }
    }

    private void EmailCommentNotificationToAdministrator(int ID, string RecipeName)
    {
        EmailTemplate SendEmail = new EmailTemplate();

        SendEmail.ItemID = ID;
        SendEmail.ItemName = RecipeName;

        SendEmail.SendEmailRecipeCommentNotify();

        SendEmail = null;
    }
}
