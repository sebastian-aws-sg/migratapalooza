#region XD World Recipe V 2.8
// FileName: printarticle.cs
// Author: Dexter Zafra
// Date Created: 7/24/2008
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

public partial class printarticle : BasePage
{
    public string strArtTitle;

    protected void Page_Load(object sender, EventArgs e)
    {
        //Instantiate utility object
        Utility Util = new Utility();

        ProviderArticleDetails Article = new ProviderArticleDetails();

        int ArticleID = (int)Util.Val(Request.QueryString["aid"]);

        Article.Approved = constant.Approved;
        Article.FillUp(ArticleID);

        lbtitle.Text = Article.Title;
        lbcontent.Text = Article.Content;
        strArtTitle = "Printing " + Article.Title + " article";

        //Release allocated memory         
        Util = null;
        Article = null;
    }
}