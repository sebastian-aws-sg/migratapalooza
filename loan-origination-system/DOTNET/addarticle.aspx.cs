#region XD World Recipe V 2.8
// FileName: addarticle.cs
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
using XDRecipe.UI;
using XDRecipe.BL;
using XDRecipe.BL.Providers;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.Security;
using XDRecipe.Common.Utilities;
using XDRecipe.BL.Providers.User;
using XDRecipe.BL.Providers.Article;

public partial class addarticle : BasePage
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            HideContentIfNotLogin.Visible = true;
            lblauthorname.Text = UserIdentity.UserName.ToString();

            LoadDropDownListCategory.LoadDropDownCategory("Article Category", ddlarticlecategory, "Select a Category"); 
        }
        else
        {
            lblyouarenotlogin.Visible = true;
            lblyouarenotlogin.Text = "<div style='margin-top: 12px; margin-bottom: 7px;'><img src='images/lock.gif' align='absmiddle'> You are not authorize to add an article. Please login to add an article.</div>";
        }
    }

    public void Add_Article(Object s, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            Utility Util = new Utility();

            ArticleRepository Article = new ArticleRepository();

            Article.UID = UserIdentity.UserID;
            Article.Author = UserIdentity.UserName;
            Article.Title = Util.FormatTextForInput(Request.Form["Title"]);
            Article.Summary = Util.FormatTextForInput(Request.Form["Summary"]);
            Article.Content = Request.Form["Content"];
            Article.CatID = Int32.Parse(Request.Form["ddlarticlecategory"]);
            Article.Keyword = Util.FormatTextForInput(Request.Form["Keyword"]);

            #region Form Input Validator
            if (Article.Title.Length == 0)
            {
                lbvalenght.Text = "<br>Error: Title is blank, please enter a title.";
                lbvalenght.Visible = true;
                return;
            }
            if (Article.CatID == 0)
            {
                lbvalenght.Text = "<br>Error: You must select a category where you want your article to show.";
                lbvalenght.Visible = true;
                return;
            }
            if (Article.Summary.Length == 0)
            {
                lbvalenght.Text = "<br>Error: Summary is blank, please enter a summary.";
                lbvalenght.Visible = true;
                return;
            }
            if (Article.Content.Length == 0)
            {
                lbvalenght.Text = "<br>Error: Content is blank, please enter a content.";
                lbvalenght.Visible = true;
                return;
            }
            if (Article.Keyword.Length == 0)
            {
                lbvalenght.Text = "<br>Error: Keyword is blank, please enter a keyword.";
                lbvalenght.Visible = true;
                return;
            }
            if (Article.Content.Length > 8000)
            {
                lbvalenght.Text = "<br>Error: Content is too long, max of 8000 characters including HTML formatting.";
                lbvalenght.Visible = true;
                return;
            }
            if (Article.Title.Length > 65)
            {
                lbvalenght.Text = "<br>Error: Title is too long, should not exceed 65 characters.";
                lbvalenght.Visible = true;
                return;
            }
            if (Article.Summary.Length > 350)
            {
                lbvalenght.Text = "<br>Error: Summary is too long, max of 350 characters.";
                lbvalenght.Visible = true;
                return;
            }
            if (Article.Keyword.Length > 80)
            {
                lbvalenght.Text = "<br>Error: Keyword is too long, max of 80 characters.";
                lbvalenght.Visible = true;
                return;
            }

            #endregion

            if (Article.Add(Article) != 0)
            {
                JSLiteral.Text = "Error occured while processing your article submission.";
                return;
            }

            Article = null;
            Util = null;

            Response.Redirect("confirmsubmitarticle.aspx?mode=Added");
        }
    }
}
