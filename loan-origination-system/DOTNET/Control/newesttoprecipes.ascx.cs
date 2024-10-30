#region XD World Recipe V 2.8
// FileName: newesttoprecipes.ascx.cs
// Author: Dexter Zafra
// Date Created: 5/19/2008
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
using System.Data.SqlClient;
using XDRecipe.BL;
using XDRecipe.BL.Providers.Recipes;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.Common.Utilities;
using XDRecipe.Security;
using XDRecipe.BL.Providers.CookBooks;
using XDRecipe.BL.Providers.User;

public partial class newesttoprecipes : System.Web.UI.UserControl
{

    protected void Page_Load(object sender, EventArgs e)
    {
        //Instantiate object
        Utility Util = new Utility();

        int CatId = (int)Util.Val(Request.QueryString["catid"]);

        //Display random recipe
        GetRandomrecipe(CatId);

        MyCookBookSideMenu();

        //Get the newest 10 recipes. Change the value to 5 if you want to display only 5 newest recipes.
        int TopNewest = 10;

        //Get the 15 popular recipes. Change the value to 5 or 10 if you want to display only 5/10 most popular recipes.
        int TopPopular = 15;

        NewestRecipeSideMenuProvider GetNewestRecipe = NewestRecipeSideMenuProvider.GetInstance();
        GetNewestRecipe.NewestRecipeParam(CatId, TopNewest);

        PopularRecipeSideMenuProvider GetPopularRecipe = PopularRecipeSideMenuProvider.GetInstance();
        GetPopularRecipe.PopularRecipeParam(CatId, TopPopular);

        string CategoryName = GetNewestRecipe.Category;

        if (!string.IsNullOrEmpty(this.Request.QueryString["catid"]))
        {
            lblcatname.Text = CategoryName.ToString() + "&nbsp;";
            lblcatnamepop.Text = CategoryName.ToString() + "&nbsp;";
            lblrancatname.Text = CategoryName.ToString() + "&nbsp;";
            lbTopCnt.Text = TopNewest.ToString();
        }
        else
        {
            lblcatname.Text = "";
            lbTopCnt.Text = TopNewest.ToString();
        }

        RecipeNew.DataSource = GetNewestRecipe.GetNewestRecipe();
        RecipeNew.DataBind();

        TopRecipe.DataSource = GetPopularRecipe.GetPopularRecipe();
        TopRecipe.DataBind();

        if (!string.IsNullOrEmpty(this.Request.QueryString["catid"]))
        {
            lblpopcounter.Text = "Top Recipes";
        }
        else
        {
            lblpopcounter.Text = "Top 15 Recipes";
        }

        //Release allocated memory
        Util = null;
    }

    private void MyCookBookSideMenu()
    {
        MyCookBookSideMenuContainer.Visible = false;
        MyCookBookSideMenuRepeater.Visible = false;

        if (Authentication.IsUserAuthenticated)
        {
            MyCookBookSideMenuContainer.Visible = true;
            MyCookBookSideMenuRepeater.Visible = true;

            UsersCookBook GetUserRecipe = new UsersCookBook(UserIdentity.UserID, 10);
            MyCookBookSideMenuRepeater.DataSource = GetUserRecipe.GetUserRecipeInCookBook();
            MyCookBookSideMenuRepeater.DataBind();

            int RemainingRecipe = 50 - GetUserRecipe.TotalCount;

            lblcounter.Text = UserIdentity.UserName + "'s CookBook. You have (" + GetUserRecipe.TotalCount.ToString() + ") saved recipe and " + RemainingRecipe + " to go.";

            GetUserRecipe = null;
        }
    }

    //Handles random recipe
    private void GetRandomrecipe(int CatId)
    {
        RandomRecipe Recipe = RandomRecipe.GetInstance();

        Recipe.CatID = CatId;
        Recipe.FillUp();

        LinkRanName.NavigateUrl = "~/recipedetail.aspx?id=" + Recipe.ID;
        LinkRanName.Text = Recipe.RecipeName;
        LinkRanName.ToolTip = "View " + Recipe.RecipeName + " recipe";

        LinkRanCat.NavigateUrl = "~/category.aspx?catid=" + Recipe.CatID;
        LinkRanCat.Text = Recipe.Category;
        LinkRanCat.ToolTip = "Browse " + Recipe.Category + " category";

        lblranhits.Text = Recipe.Hits.ToString();

        ranrateimage.ImageUrl = Utility.GetStarImage(Recipe.Rating);
        lblvotes.Text = Recipe.NoRates;
    }

    public void TopRecipe_ItemDataBound(Object s, RepeaterItemEventArgs e)
    {
        //Get sequential number counter
        Utility.GetSeqCounter(TopRecipe, e);       
    }

    public void RecipeNew_ItemDataBound(Object s, RepeaterItemEventArgs e)
    {
        //Get the number of days
        //parameter 1 = we are dealing with the newest recipe.
        Utility.GetCounterDay(Convert.ToDateTime(DataBinder.Eval(e.Item.DataItem, "Date")), e, 1);
    }
}
