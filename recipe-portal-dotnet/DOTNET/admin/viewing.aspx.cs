#region XD World Recipe V 2.8
// FileName: viewing.cs
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
using System.Data.SqlClient;
using XDRecipe.UI;
using XDRecipe.BL;
using XDRecipe.BL.Providers.Recipes;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.Common.Utilities;

public partial class admin_viewing : BasePageAdmin
{
    Utility Util = new Utility();

    private int ID;
    public string strRecipename;
    public string strRecipeImage;
    public int UserID;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            RecipeDetails Recipe = new RecipeDetails();

            int RecipeID = (int)Util.Val(Request.QueryString["id"]);
            Recipe.Approved = constant.UnApprovedRecipe;
            Recipe.FillUp(RecipeID);

            UserID = Recipe.UID;

            strRecipeImage = GetRecipeImage.GetImage(RecipeID);

            if (Recipe.HitDate.ToString() == "1/1/0001 12:00:00 AM")
            {
                lblastviewed.Text = "This Recipe Has not been view by a user lately.";
            }
            else
            {
                lblastviewed.Text = Recipe.HitDate.ToString();
            }

            lblname.Text = Recipe.RecipeName;
            lblauthor.Text = Recipe.Author;
            lbldate.Text = Recipe.Date.ToShortDateString();
            lblCatName.Text = Recipe.Category;
            Ingredients.Text = Recipe.Ingredients;
            Instructions.Text = Recipe.Instructions;

            if (Recipe.Approved == 1)
            {
                approvebutton.Visible = false;
                lblapprovalstatus.Text = "Viewing Recipe";
            }
            else
            {
                lblapprovalstatus.Text = "Unapprove - This recipe is waiting for approval";
            }

            if (Recipe.Hits == 0)
            {
                lblhits.Text = "0";
            }
            else
            {
                lblhits.Text = string.Format("{0:#,###}", Recipe.Hits);
            }

            strRecipename = Recipe.RecipeName;

            Util = null;
            Recipe = null;
        }
    }

    public void Approve_Recipe(object sender, EventArgs e)
    {
        ID = (int)Util.Val(Request.QueryString["id"]);

        Caching.PurgeCacheItems("MainCourse_RecipeCategory");
        Caching.PurgeCacheItems("Ethnic_RecipeCategory");
        Caching.PurgeCacheItems("RecipeCategory_SideMenu");
        Caching.PurgeCacheItems("Newest_RecipesSideMenu_");
        Caching.PurgeCacheItems("Last5_RecipePublishedByUser_" + UserID);

        Blogic.ActionProcedureDataProvider.AdminApproveRecipe(ID);

        Util.PageRedirect(8);

        Util = null;
    }
}
