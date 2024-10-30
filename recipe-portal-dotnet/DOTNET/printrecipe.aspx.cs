#region XD World Recipe V 2.8
// FileName: printrecipe.cs
// Author: Dexter Zafra
// Date Created: 5/24/2008
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

public partial class printrecipe : System.Web.UI.Page
{
    public string strRName;

    protected void Page_Load(object sender, EventArgs e)
    {
        Utility Util = new Utility();

        RecipeDetails Recipe = new RecipeDetails();

        int RecipeID = (int)Util.Val(Request.QueryString["id"]);
        Recipe.Approved = constant.ApprovedRecipe;
        Recipe.FillUp(RecipeID);

        lblingredientsdis.Text = "Ingredients:";
        lblinstructionsdis.Text = "Instructions:";
        lblname.Text = Recipe.RecipeName;
        lblIngredients.Text = Util.FormatText(Recipe.Ingredients);
        lblInstructions.Text = Util.FormatText(Recipe.Instructions);

        strRName = "Printing" + Recipe.RecipeName + " Recipe";

        Util = null;
        Recipe = null;
    }
}
