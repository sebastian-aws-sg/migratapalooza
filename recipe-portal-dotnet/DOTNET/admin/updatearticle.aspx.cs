#region XD World Recipe V 2.8
// FileName: updatearticle.cs
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
using XDRecipe.BL.Providers.Article;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.Common.Utilities;
using XDRecipe.BL.Providers.User;

public partial class admin_updatearticle : BasePageAdmin
{
    Utility Util = new Utility();

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            lblusername.Text = "Welcome Admin:&nbsp;" + UserIdentity.AdminUsername;

            LoadDropDownListCategory.LoadDropDownCategory("Article Category", ddlarticlecategory, "Select a Category");

            ProviderArticleDetails Article = new ProviderArticleDetails();

            int ArticleID = (int)Util.Val(Request.QueryString["aid"]);

            Article.Approved = constant.UnApproved;
            Article.FillUp(ArticleID);

            string categoryname;
            categoryname = Article.Category;

            lblauthorname.Text = Article.Author;
            lbtitle.Text = Article.Title;
            Userid.Value = Article.UID.ToString();
            Title.Value = Article.Title;
            Content.Value = (string)Article.Content;
            Summary.Value = Util.FormatText(Article.Summary);
            Keyword.Value = Article.Keyword;

            Util = null;
        }
    }

    public void Update_Article(Object s, EventArgs e)
    {
        ArticleRepository Article = new ArticleRepository();

        Article.ID = (int)Util.Val(Request.QueryString["aid"]);
        Article.UID = int.Parse(Request.Form["Userid"]);
        Article.Title = Request.Form["Title"];
        Article.Content = Request.Form["Content"];
        Article.CatID = int.Parse(Request.Form["ddlarticlecategory"]);
        Article.Keyword = Request.Form["Keyword"];
        Article.Summary = Request.Form["Summary"];

        //Refresh cache
        Caching.PurgeCacheItems("Newest_Articles");
        Caching.PurgeCacheItems("ArticleCategory_SideMenu");

        //Notify user if error occured.
        if (Article.Update(Article) != 0)
        {
            JSLiteral.Text = Util.JSProcessingErrorAlert;
            return;
        }

        //Release allocated memory
        Article = null;

        //If success, redirect to article update confirmation page.
        Util.PageRedirect(7);

        Util = null;
    }
}
