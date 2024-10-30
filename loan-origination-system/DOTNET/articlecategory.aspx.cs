#region XD World Recipe V 2.8
// FileName: articlecategory.cs
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

public partial class articlecategory : BasePage
{
    private int CatId;
    private int OrderBy;
    private int SortBy;

    private Utility Util
    {
        get { return new Utility(); }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!string.IsNullOrEmpty(this.Request.QueryString["ob"]) || !string.IsNullOrEmpty(this.Request.QueryString["sb"]))
        {
            OrderBy = (int)Util.Val(Request.QueryString["ob"]);
            SortBy = (int)Util.Val(Request.QueryString["sb"]);
            lblsortname.Text = Util.GetSortOptionName(OrderBy) + Util.GetSortOptionOrderBy(SortBy);
        }
        else
        {
            lblsortname.Text = Util.GetSortOptionName(OrderBy);
        }

        int GetPage = (int)Util.Val(Request.QueryString["page"]);

        int iPage = 1;

        if (!string.IsNullOrEmpty(this.Request.QueryString["page"]))
            iPage = (int)Util.Val(Request.QueryString["page"]);

        BindList(iPage, GetPage);

        GetMetaTitleTagKeywords(lbcatname.Text.ToString());
    }

    private void BindList(int iPage, int GetPage)
    {
        CatId = (int)Util.Val(Request.QueryString["catid"]);
        OrderBy = (int)Util.Val(Request.QueryString["ob"]);
        SortBy = (int)Util.Val(Request.QueryString["sb"]);

        string ParamURL = Request.CurrentExecutionFilePath + "?&catid=" + CatId;

        int PageSize = PagerLinks.DefaultPageSize;
        int PageIndex = iPage;

        ArticleCategoryProvider GetCategory = ArticleCategoryProvider.GetInstance();
        GetCategory.ArticleCategoryParam(CatId, OrderBy, SortBy, PageIndex, PageSize);

        PagerLinks Pager = PagerLinks.GetInstance();
        Pager.PagerLinksParam(PageIndex, PageSize, GetCategory.RecordCount);

        lbcatname.Text = GetCategory.Category;
        lbcount.Text = "(" + GetCategory.RecordCount.ToString() + ")";

        lbPagerLink.Text = Pager.DisplayNumericPagerLink(ParamURL, OrderBy, SortBy, GetPage);

        lblRecpagetop.Text = Pager.GetTopRightPagerCounterCustomPaging;

        lblRecpage.Text = Pager.GetBottomPagerCounterCustomPaging;

        ArticleCat.DataSource = GetCategory.GetCategories();
        ArticleCat.DataBind();
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
