#region XD World Recipe V 2.8
// FileName: editrecipe.cs
// Author: Dexter Zafra
// Date Created: 2/13/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.IO;
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
using XDRecipe.Security;
using XDRecipe.BL.Providers.User;

public partial class editrecipe : BasePage
{
    private Utility Util
    {
        get { return new Utility(); }
    }

    public string strRecipeImage;

    protected void Page_Load(Object sender, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            lblusername.Text = UserIdentity.UserName;

            LoadDropDownListCategory.LoadDropDownCategory("Recipe Category", CategoryID, "Select a Category");

            try
            {
                RecipeDetails Recipe = new RecipeDetails();

                int RecipeID = (int)Util.Val(Request.QueryString["id"]);

                strRecipeImage = GetRecipeImage.GetRecipeImageUserEdit(RecipeID);
                Recipe.Approved = constant.ApprovedRecipe;
                Recipe.FillUp(RecipeID);

                Name.Value = Recipe.RecipeName;
                Hits.Value = Recipe.Hits.ToString();
                Ingredients.Value = Recipe.Ingredients;
                Instructions.Value = Recipe.Instructions;

                if (Recipe.UID == UserIdentity.UserID)
                {
                    HideContentIfNotLogin.Visible = true;
                }
                else
                {
                    lblyouarenotlogin.Visible = true;
                    lblyouarenotlogin.Text = "<div style='margin-top: 12px; margin-bottom: 7px;'><img src='images/lock.gif' align='absmiddle'> You are not authorize to edit this recipe.</div>";
                }

                Recipe = null;
            }
            catch
            {

            }
        }        
        else
        {
            lblyouarenotlogin.Visible = true;
            lblyouarenotlogin.Text = "<div style='margin-top: 12px; margin-bottom: 7px;'><img src='images/lock.gif' align='absmiddle'> You are not authorize to edit a recipe. Please login to edit your recipe.</div>";
        }
    }

    public void Update_Recipe(Object s, EventArgs e)
    {
        if (Page.IsValid && Authentication.IsUserAuthenticated)
        {
            RecipeRepository Recipe = new RecipeRepository();

            Recipe.UID = UserIdentity.UserID;
            Recipe.ID = (int)Util.Val(Request.QueryString["id"]);
            Recipe.RecipeName = Util.FormatTextForInput(Request.Form[Name.UniqueID]);
            Recipe.CatID = int.Parse(Request.Form[CategoryID.UniqueID]);
            Recipe.Ingredients = Util.FormatTextForInput(Request.Form[Ingredients.UniqueID]);
            Recipe.Instructions = Util.FormatTextForInput(Request.Form[Instructions.UniqueID]);
            Recipe.Hits = int.Parse(Request.Form[Hits.UniqueID]);

            #region Form Input Validator
            //Validate for empty recipe name
            if (Recipe.RecipeName.Length == 0)
            {
                lbvalenght.Text = "<br>Error: Recipe Name is empty, please enter a recipe name.";
                lbvalenght.Visible = true;
                return;
            }
            //Validate for empty ingredients
            if (Recipe.Ingredients.Length == 0)
            {
                lbvalenght.Text = "<br>Error: Ingredients is empty, please enter an ingredients.";
                lbvalenght.Visible = true;
                return;
            }
            //Validate for empty instruction
            if (Recipe.Instructions.Length == 0)
            {
                lbvalenght.Text = "<br>Error: Instructions is empty, please enter an instruction.";
                lbvalenght.Visible = true;
                return;
            }

            //Recipe name maximum of 50 char allowed
            if (Recipe.RecipeName.Length > 50)
            {
                lbvalenght.Text = "<br>Error: Recipe Name is too long. Max of 50 characters.";
                lbvalenght.Visible = true;
                return;
            }
            //Ingredients maximum of 500 char allowed - can be increase to max 1000 characters.
            if (Recipe.Ingredients.Length > 1500)
            {
                lbvalenght.Text = "<br>Error: Ingredients is too long. Max of 1000 characters.";
                lbvalenght.Visible = true;
                return;
            }
            //Instruction maximum of 750 char allowed - can be increase to max 2000 characters.
            if (Recipe.Instructions.Length > 2000)
            {
                lbvalenght.Text = "<br>Error: Instructions is too long. Max of 2000 characters.";
                lbvalenght.Visible = true;
                return;
            }
            #endregion

            if (RecipeImageFileUpload.HasFile)
            {
                int FileSize = RecipeImageFileUpload.PostedFile.ContentLength;
                string contentType = RecipeImageFileUpload.PostedFile.ContentType;

                //File type validation
                if (!contentType.Equals("image/gif") &&
                    !contentType.Equals("image/jpeg") &&
                    !contentType.Equals("image/jpg") &&
                    !contentType.Equals("image/png"))
                {
                    lbvalenght.Text = "<br>File format is invalid. Only gif, jpg, jpeg or png files are allowed.";
                    lbvalenght.Visible = true;
                    return;
                }
                // File size validation
                if (FileSize > constant.RecipeImageMaxSize)
                {
                    lbvalenght.Text = "<br>File size exceed the maximun allowed 30000 bytes";
                    lbvalenght.Visible = true;
                    return;
                }
            }

            ImageUploadManager.UploadRecipeImage(Recipe, PlaceHolder1, GetRecipeImage.ImagePathDetail, constant.RecipeImageMaxSize, true);

            //Refresh cache
            Caching.PurgeCacheItems("MainCourse_RecipeCategory");
            Caching.PurgeCacheItems("Ethnic_RecipeCategory");
            Caching.PurgeCacheItems("RecipeCategory_SideMenu");
            Caching.PurgeCacheItems("Newest_RecipesSideMenu_");

            if (Recipe.Update(Recipe) != 0)
            {
                JSLiteral.Text = Util.JSProcessingErrorAlert;
                return;
            }

            Recipe = null;

            Response.Redirect("confirmaddeditrecipe.aspx?mode=Updated&recipename=" + Request.Form[Name.UniqueID] + "&id=" + int.Parse(Request.QueryString["id"]));
        }
    }
}
