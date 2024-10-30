#region XD World Recipe V 2.8
// FileName: addrecipe.cs
// Author: Dexter Zafra
// Date Created: 5/28/2008
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
using XDRecipe.BL.Providers;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.BL.Providers.Recipes;
using XDRecipe.Common.Utilities;
using XDRecipe.Security;
using XDRecipe.BL.Providers.User;

public partial class addrecipe : BasePage
{
    protected void Page_Load(Object sender, EventArgs e)
    {
        Panel3.Visible = false;
        lblwarning.Visible = true;
        lblwarning.Text = "<img src='images/lock.gif' align='absmiddle'> You must login to submit a recipe. " + "<a href='registration.aspx'>Register here</a>";

        if (Authentication.IsUserAuthenticated)
        {
            Panel3.Visible = true;
            lblwarning.Visible = false;
            lblusername.Text = UserIdentity.UserName;
            Author.Value = UserIdentity.UserName;

            LoadDropDownListCategory.LoadDropDownCategory("Recipe Category", CategoryID, "Select a Category");
        }
    }

    public void Add_Recipe(Object s, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            if (Page.IsValid)
            {
                Utility Util = new Utility();

                RecipeRepository Recipe = new RecipeRepository();

                //Filters harmful scripts from input string.
                Recipe.RecipeName = Util.FormatTextForInput(Request.Form[Name.UniqueID]);
                Recipe.Author = Util.FormatTextForInput(Request.Form[Author.UniqueID]);
                Recipe.CatID = int.Parse(Request.Form[CategoryID.UniqueID]);
                Recipe.Ingredients = Util.FormatTextForInput(Request.Form[Ingredients.UniqueID]);
                Recipe.Instructions = Util.FormatTextForInput(Request.Form[Instructions.UniqueID]);

                Recipe.UID = UserIdentity.UserID;

                #region Form Input Validator
                //Validate for empty recipe name
                if (Recipe.RecipeName.Length == 0)
                {
                    lbvalenght.Text = "<br>Error: Recipe Name is empty, please enter a recipe name.";
                    lbvalenght.Visible = true;
                    return;
                }
                if (Recipe.CatID == 0)
                {
                    lbvalenght.Text = "<br>Error: You must select a category where you want your recipe to show.";
                    lbvalenght.Visible = true;
                    return;
                }
                //Validate for empty author name
                if (Recipe.Author.Length == 0)
                {
                    lbvalenght.Text = "<br>Error: Author Name is empty, please enter the author name";
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
                    Name.Value = "";
                    return;
                }
                //Author name maximum of 25 char allowed
                if (Recipe.Author.Length > 25)
                {
                    lbvalenght.Text = "<br>Error: Author Name is too long. Max of 25 characters.";
                    lbvalenght.Visible = true;
                    Author.Value = "";
                    return;
                }
                //Ingredients maximum of 1000 char allowed - can be increase to max of 1000 char.
                if (Recipe.Ingredients.Length > 500)
                {
                    lbvalenght.Text = "<br>Error: Ingredients is too long. Max of 500 characters.";
                    lbvalenght.Visible = true;
                    return;
                }
                //Instruction maximum of 750 char allowed - can be increase to max of 2000 char
                if (Recipe.Instructions.Length > 750)
                {
                    lbvalenght.Text = "<br>Error: Instructions is too long. Max of 700 characters.";
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

                ImageUploadManager.UploadRecipeImage(Recipe, PlaceHolder1, GetRecipeImage.ImagePathDetail, constant.RecipeImageMaxSize, false);

                if (Recipe.Add(Recipe) != 0)
                {
                    JSLiteral.Text = "Error occured while processing your submit.";
                    return;
                }

                EmailRecipeSubmissionNotificationToAdministrator(Recipe.RecipeName);

                Recipe = null;

                Response.Redirect("confirmaddeditrecipe.aspx?mode=Added");

                Util = null;
            }
        }
    }

    private void ValidateFileUploadContentType(FileUpload ImageUpload, int maxFileSize)
    {
  
    }

    private void EmailRecipeSubmissionNotificationToAdministrator(string RecipeName)
    {
        EmailTemplate SendEMail = new EmailTemplate();
        SendEMail.ItemName = RecipeName;
        SendEMail.SendEmailAddRecipeNotify();
        SendEMail = null;
    }
}
