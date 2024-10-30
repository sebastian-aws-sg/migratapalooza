#region XD World Recipe V 2.8
// FileName: searcharticle.cs
// Author: Dexter Zafra
// Date Created: 5/25/2008
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
using XDRecipe.BL.Providers.Article;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.Common.Utilities;

public partial class searcharticle : BasePage
{
    private Utility Util
    {
        get { return new Utility(); }
    }

    private string strKeyword;
    private int CatId;
    private int OrderBy;
    private int SortBy;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!string.IsNullOrEmpty(this.Request.QueryString["ob"]) || !string.IsNullOrEmpty(this.Request.QueryString["sb"]))
        {
            OrderBy = (int)Util.Val(Request.QueryString["ob"]);
            int SortBy = (int)Util.Val(Request.QueryString["sb"]);
            lblsortname.Text = Util.GetSortOptionName(OrderBy) + Util.GetSortOptionOrderBy(SortBy);
        }
        else
        {
            lblsortname.Text = Util.GetSortOptionName(OrderBy);
        }

        strKeyword = Request.QueryString["find"].ToString();
        string strmetaTag = "Search result for " + strKeyword;

        GetMetaTitleTagKeywords(strmetaTag);

        BindList();
    }

    private void BindList()
    {
        CatId = (int)Util.Val(Request.QueryString["catid"]);
        strKeyword = Request.QueryString["find"].ToString();
        OrderBy = (int)Util.Val(Request.QueryString["ob"]);
        SortBy = (int)Util.Val(Request.QueryString["sb"]);

        string ParamURL = Request.CurrentExecutionFilePath + "?find=" + strKeyword + "&catid=" + CatId;

        int GetPage = (int)Util.Val(Request.QueryString["page"]);

        int iPage;

        if (string.IsNullOrEmpty(this.Request.QueryString["page"]))
            iPage = 1; 
        else
            iPage = (int)Util.Val(Request.QueryString["page"]); 

        int PageSize = PagerLinks.DefaultPageSize;
        int PageIndex = iPage;

        try
        {
            ArticleSearchProvider GetArticle = ArticleSearchProvider.GetInstance();
            GetArticle.ArticleSearchParam(strKeyword, CatId, OrderBy, SortBy, PageIndex, PageSize);

            PagerLinks Pager = PagerLinks.GetInstance();
            Pager.PagerLinksParam(PageIndex, PageSize, GetArticle.RecordCount);

            string strSearchIn;

            if (CatId == 0)
                strSearchIn = "in all category.";
            else
                strSearchIn = "in <b>" + GetArticle.Category + "</b> category.";

            lbcount.Text = "(" + GetArticle.RecordCount.ToString() + ") article found for keyword (<b>" + strKeyword + "</b>) " + strSearchIn;

            lbPagerLink.Text = Pager.DisplayNumericPagerLink(ParamURL, OrderBy, SortBy, GetPage);

            lblRecpagetop.Text = Pager.GetTopRightPagerCounterCustomPaging;

            lblRecpage.Text = Pager.GetBottomPagerCounterCustomPaging;

            ArticleCat.DataSource = GetArticle.GetArticleSearchResult();
            ArticleCat.DataBind();
        }
        catch
        {
            lblNorecordFound.Visible = true;
            lblNorecordFound.Text = "&nbsp;&nbsp;&nbsp;No Article Found for the keyword (" + strKeyword + "). Please try again.";
        }
    }

    private void GetMetaTitleTagKeywords(string CategoryName)
    {
        Page.Header.Title = CategoryName + " article";
        HtmlMeta metaTag = new HtmlMeta();
        metaTag.Name = "Keywords";
        metaTag.Content = CategoryName + " article, cooking article";
        this.Header.Controls.Add(metaTag);
    }

    public void ArticleCat_ItemDataBound(Object s, RepeaterItemEventArgs e)
    {
        Utility.GetIdentifyItemNewPopular(Convert.ToDateTime(DataBinder.Eval(e.Item.DataItem, "Date")), e,
                                            (int)DataBinder.Eval(e.Item.DataItem, "Hits"));
    }
}
