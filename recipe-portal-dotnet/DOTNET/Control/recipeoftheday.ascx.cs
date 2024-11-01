#region XD World Recipe V 2.8
// FileName: recipeoftheday.ascx.cs
// Author: Dexter Zafra
// Date Created: 7/16/2008
// Website: www.ex-designz.net
#endregion
using System;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using XDRecipe.BL;
using XDRecipe.BL.Providers.Recipes;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.Common.Utilities;

public partial class recipeoftheday : System.Web.UI.UserControl
{
    protected void Page_Load(object sender, EventArgs e)
    {
            //Instantiate utility object
            Utility Util = new Utility();

            RecipeoftheDay Recipe = RecipeoftheDay.GetInstance();

            Recipe.FillUp();

            RanCat.NavigateUrl = "~/category.aspx?catid=" + Recipe.CatID;
            RanCat.Text = Recipe.Category;
            RanCat.ToolTip = "Browse " + Recipe.Category + " category";

            rdetails.NavigateUrl = "~/recipedetail.aspx?id=" + Recipe.ID;
            rdetails.Text = "Read more...";
            rdetails.ToolTip = "Read full details of " + Recipe.RecipeName + " recipe";

            lbrecname.Text = Recipe.RecipeName;
            lbingred.Text = Util.FormatText(Recipe.Ingredients);
            lbinstruct.Text = Util.FormatText(Recipe.Instructions);
            lbhits.Text = Recipe.Hits.ToString();
            lblrating.Text = Recipe.Rating;
            lbvotes.Text = Recipe.NoRates.ToString();

            rateimage.ImageUrl = Utility.GetStarImage(Recipe.Rating);

            Util = null;
   }
}
