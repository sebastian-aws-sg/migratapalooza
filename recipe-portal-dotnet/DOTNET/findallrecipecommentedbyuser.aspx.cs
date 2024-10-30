#region XD World Recipe V 2.8
// FileName: findallrecipecommentedbyuser.cs
// Author: Dexter Zafra
// Date Created: 5/19/2008
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
using XDRecipe.BL.Providers.Recipes;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.Common.Utilities;

public partial class findallrecipecommentedbyuser : BasePage
{
    private Utility Util
    {
        get { return new Utility(); }
    }

    private string strAuthorName;
    private int OrderBy;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!string.IsNullOrEmpty(this.Request.QueryString["ob"]))
        {
            OrderBy = (int)Util.Val(Request.QueryString["ob"]);
            lblsortname.Text = Util.GetSortOptionName(OrderBy);
        }
        else
        {
            lblsortname.Text = "";
        }

        strAuthorName = Request.QueryString["commentauthor"].ToString();
        string strmetaTag = "Recipe(s) commented by: " + strAuthorName;

        GetMetaTitleTagKeywords(strmetaTag);

        BindList();
    }

    private void BindList()
    {
        strAuthorName = Request.QueryString["commentauthor"].ToString();
        OrderBy = (int)Util.Val(Request.QueryString["ob"]);

        string ParamURL = Request.CurrentExecutionFilePath + "?commentauthor=" + strAuthorName;

        int GetPage = (int)Util.Val(Request.QueryString["page"]);

        int iPage = 1;

        if (!string.IsNullOrEmpty(this.Request.QueryString["page"]))
            iPage = (int)Util.Val(Request.QueryString["page"]);

        int PageSize = PagerLinks.DefaultPageSize;
        int PageIndex = iPage;

        ProvidersGetAllRecipeCommentedbyUser GetRecipe = ProvidersGetAllRecipeCommentedbyUser.GetInstance();
        GetRecipe.Param(strAuthorName, OrderBy, 0, PageIndex, PageSize);

        PagerLinks Pager = PagerLinks.GetInstance();
        Pager.PagerLinksParam(PageIndex, PageSize, GetRecipe.RecordCount);

        lblcount.Text = "(" + GetRecipe.RecordCount.ToString() + ") recipes found commented by (<b>" + strAuthorName + "</b>)";

        lbPagerLink.Text = Pager.DisplayNumericPagerLink(ParamURL, OrderBy, 0, GetPage);

        lblRecpagetop.Text = Pager.GetTopRightPagerCounterCustomPaging;

        lblRecpage.Text = Pager.GetBottomPagerCounterCustomPaging;

        RecipeCat.DataSource = GetRecipe.GetAllRecipeCommentedByAuthorResult();
        RecipeCat.DataBind();
    }

    private void GetMetaTitleTagKeywords(string AlphaLetterName)
    {
        Page.Header.Title = AlphaLetterName;
        HtmlMeta metaTag = new HtmlMeta();
        metaTag.Name = "Keywords";
        metaTag.Content = AlphaLetterName;
        this.Header.Controls.Add(metaTag);
    }

    public void RecipeCat_ItemDataBound(Object s, RepeaterItemEventArgs e)
    {
        Utility.GetIdentifyItemNewPopular(Convert.ToDateTime(DataBinder.Eval(e.Item.DataItem, "Date")), e,
                                            (int)DataBinder.Eval(e.Item.DataItem, "Hits"));
    }
}
