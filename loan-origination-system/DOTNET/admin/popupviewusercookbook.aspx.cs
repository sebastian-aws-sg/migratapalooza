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
using XDRecipe.BL.Providers.CookBooks;

public partial class admin_popupviewusercookbook : BasePageAdmin
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!string.IsNullOrEmpty(Request.QueryString["uid"]) && !string.IsNullOrEmpty(Request.QueryString["uname"]))
        {
            int UserID = int.Parse(Request.QueryString["uid"]);
            string UserName = Request.QueryString["uname"];

            lblusernameheader.Text = UserName + "'s Cook Book";
            UsersCookBookMain GetUserRecipe = new UsersCookBookMain(UserID);
            SavedRecipeInCookBook.DataSource = GetUserRecipe.GetUserRecipeInCookBookMain();
            SavedRecipeInCookBook.DataBind();

            int RemainingRecipe = 50 - GetUserRecipe.TotalCount;

            lblcounter.Text = "<img src='../images/cookbookicon.gif' align='absmiddle'>&nbsp;&nbsp;" + GetUserRecipe.TotalCount.ToString() + " saved recipe and " + RemainingRecipe + " to go.";

            GetUserRecipe = null;
        }
    }
}
