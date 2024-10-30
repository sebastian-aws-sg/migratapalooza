#region XD World Recipe V 3
// FileName: findallarticlecommentbyuser.cs
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
using XDRecipe.Security;
using XDRecipe.BL.Providers.User;
using XDRecipe.Common.Utilities;

public partial class findallarticlecommentbyuser : BasePage
{
    private Utility Util
    {
        get { return new Utility(); }
    }

    private string strAuthor;
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

        strAuthor = Request.QueryString["author"].ToString();

        GetMetaTitleTagKeywords(strAuthor);

        BindList();
    }

    private void BindList()
    {
        strAuthor = Request.QueryString["author"].ToString();
        OrderBy = (int)Util.Val(Request.QueryString["ob"]);
        SortBy = (int)Util.Val(Request.QueryString["sb"]);

        string ParamURL = Request.CurrentExecutionFilePath + "?author=" + strAuthor;

        int GetPage = (int)Util.Val(Request.QueryString["page"]);

        int iPage = 1;

        if (!string.IsNullOrEmpty(this.Request.QueryString["page"]))
            iPage = (int)Util.Val(Request.QueryString["page"]);

        int PageSize = PagerLinks.DefaultPageSize;
        int PageIndex = iPage;

        ProviderGetAllArticleCommentByUser GetArticle = ProviderGetAllArticleCommentByUser.GetInstance();
        GetArticle.Param(strAuthor, OrderBy, SortBy, PageIndex, PageSize);

        PagerLinks Pager = PagerLinks.GetInstance();
        Pager.PagerLinksParam(PageIndex, PageSize, GetArticle.RecordCount);

        lbcount.Text = "(" + GetArticle.RecordCount.ToString() + ") article commented by (<b>" + strAuthor + "</b>)";

        lbPagerLink.Text = Pager.DisplayNumericPagerLink(ParamURL, OrderBy, SortBy, GetPage);

        lblRecpagetop.Text = Pager.GetTopRightPagerCounterCustomPaging;

        lblRecpage.Text = Pager.GetBottomPagerCounterCustomPaging;

        ArticleCat.DataSource = GetArticle.GetArticle();
        ArticleCat.DataBind();
    }

    private void GetMetaTitleTagKeywords(string AuthorName)
    {
        Page.Header.Title = "Article commented by " + AuthorName;
        HtmlMeta metaTag = new HtmlMeta();
        metaTag.Name = "Keywords";
        metaTag.Content = "article, cooking article";
        this.Header.Controls.Add(metaTag);
    }

    public void ArticleCat_ItemDataBound(Object s, RepeaterItemEventArgs e)
    {
        Utility.GetIdentifyItemNewPopular(Convert.ToDateTime(DataBinder.Eval(e.Item.DataItem, "Date")), e,
                                            (int)DataBinder.Eval(e.Item.DataItem, "Hits"));
    }
}
