#region XD World Recipe V 2.8
// FileName: editarticle.cs
// Author: Dexter Zafra
// Date Created: 2/29/2009
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

public partial class editarticle : BasePage
{
    private Utility Util
    {
        get { return new Utility(); }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            ProviderArticleDetails Article = new ProviderArticleDetails();

            int ArticleID = (int)Util.Val(Request.QueryString["aid"]);

            Article.Approved = constant.Approved;
            Article.FillUp(ArticleID);

            if (Article.UID == UserIdentity.UserID)
            {
                HideContentIfNotLogin.Visible = true;
            }
            else
            {
                lblyouarenotlogin.Visible = true;
                lblyouarenotlogin.Text = "<div style='margin-top: 12px; margin-bottom: 7px;'><img src='images/lock.gif' align='absmiddle'> You are not authorize to edit this article.</div>";
            }

            LoadDropDownListCategory.LoadDropDownCategory("Article Category", ddlarticlecategory, "Select a Category");

            lblauthorname.Text = UserIdentity.UserName.ToString();

            Title.Value = Article.Title;
            Summary.Value = Article.Summary;
            Content.Value = Article.Content;
            Keyword.Value = Article.Keyword;

            Article = null;
        }
        else
        {
            lblyouarenotlogin.Visible = true;
            lblyouarenotlogin.Text = "<div style='margin-top: 12px; margin-bottom: 7px;'><img src='images/lock.gif' align='absmiddle'> You are not authorize to edit an article. Please login to edit your article.</div>";
        }
    }

    public void Update_Article(Object s, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            ArticleRepository Article = new ArticleRepository();

            Article.UID = UserIdentity.UserID;
            Article.ID = (int)Util.Val(Request.QueryString["aid"]);
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

            //Refresh cache
            Caching.PurgeCacheItems("Newest_Articles");
            Caching.PurgeCacheItems("ArticleCategory_SideMenu");

            if (Article.Update(Article) != 0)
            {
                JSLiteral.Text = "Error occured while processing your article submission.";
                return;
            }

            Article = null;

            Response.Redirect("confirmsubmitarticle.aspx?mode=Updated");
        }
    }
}
