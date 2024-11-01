#region XD World Recipe V 2.8
// FileName: editing.cs
// Author: Dexter Zafra
// Date Created: 6/13/2008
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
using System.Data.SqlClient;
using XDRecipe.UI;
using XDRecipe.BL;
using XDRecipe.BL.Providers.Recipes;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.Common.Utilities;
using XDRecipe.BL.Providers.User;

public partial class admin_editing : BasePageAdmin
{
    private Utility Util
    {
        get { return new Utility(); }
    }

    public string strRecipeImage;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            LoadDropDownListCategory.LoadDropDownCategory("Recipe Category", CategoryID, "Select a Category");

            lblusername.Text = "Welcome Admin:&nbsp;" + UserIdentity.AdminUsername;

            RecipeDetails Recipe = new RecipeDetails();

            int RecipeID = (int)Util.Val(Request.QueryString["id"]);

            strRecipeImage = GetRecipeImage.GetImage(RecipeID);
            Recipe.Approved = constant.UnApprovedRecipe;
            Recipe.FillUp(RecipeID);

            lblauthorname.Text = Recipe.Author;
            Userid.Value = Recipe.UID.ToString();
            Name.Text = Recipe.RecipeName;
            Author.Value = Recipe.Author;
            Hits.Text = Recipe.Hits.ToString();
            Ingredients.Text = Recipe.Ingredients;
            Instructions.Text = Recipe.Instructions;

            Recipe = null;
        }
    }

    public void Update_Recipe(object sender, EventArgs e)
    {
        RecipeRepository Recipe = new RecipeRepository();

        Recipe.UID = int.Parse(Request.Form["Userid"]);
        Recipe.ID = (int)Util.Val(Request.QueryString["id"]);
        Recipe.RecipeName = Request.Form["Name"];
        Recipe.Author = Request.Form["Author"];
        Recipe.CatID = int.Parse(Request.Form["CategoryID"]);
        Recipe.Ingredients = Request.Form["Ingredients"];
        Recipe.Instructions = Request.Form["Instructions"];
        Recipe.Hits = int.Parse(Request.Form["Hits"]);

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

        ImageUploadManager.UploadRecipeImage(Recipe, PlaceHolder1, GetRecipeImage.ImagePath, constant.RecipeImageMaxSize, true);

        if (Recipe.Update(Recipe) != 0)
        {
            JSLiteral.Text = Util.JSProcessingErrorAlert;
            return;
        }

        string strURLRedirect;
        strURLRedirect = "confirmdel.aspx?catname=" + Recipe.RecipeName + "&mode=update";

        Recipe = null;

        Response.Redirect(strURLRedirect);

    }

    public void Cancel_Update(object sender, EventArgs e)
    {
        Response.Redirect("recipemanager.aspx");
    }
}
