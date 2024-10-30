#region XD World Recipe V 2.8
// FileName: sort.cs
// Author: Dexter Zafra
// Date Created: 5/22/2008
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
using XDRecipe.Security;
using XDRecipe.BL.Providers.User;

public partial class recipesorting : BasePage
{
    private string strPageTtitle;
    private string strKeywords;
    private int SortBy;
    private int Sort_By;
    private int iPage;

    public int psOrderBy;
    public int psSortBy;
    public int psPageSize;
    public int psPageIndex;
    public int pLayout;
    public string ReturnURL;
    public string SelectedValuePrefLayout;

    private Utility Util
    {
        get { return new Utility(); }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!string.IsNullOrEmpty(this.Request.QueryString["sid"]) || !string.IsNullOrEmpty(this.Request.QueryString["sb"]))
        {
            SortBy = (int)Util.Val(Request.QueryString["sid"]);
            Sort_By = (int)Util.Val(Request.QueryString["sb"]);
            lblsortname.Text = Util.GetSortOptionName(SortBy) + Util.GetSortOptionOrderBy(Sort_By);
        }

        lblcount.Text = "(100) <b>Recipes</b> - ";

        iPage = 1;

        if (!string.IsNullOrEmpty(this.Request.QueryString["page"]))
            iPage = (int)Util.Val(Request.QueryString["page"]);

        int GetPage = (int)Util.Val(Request.QueryString["page"]);

        int Layout = (int)Util.Val(Request.QueryString["layout"]);

        BindList(iPage, GetPage, Layout);

        GetMetaTitleTagKeywords(iPage);
    }

    private void BindList(int iPage, int GetPage, int Layout)
    {
        SortBy = (int)Util.Val(Request.QueryString["sid"]);
        Sort_By = (int)Util.Val(Request.QueryString["sb"]);

        psPageSize = PagerLinks.DefaultPageSize;

        string ParamURL = Request.CurrentExecutionFilePath + "?sid=" + SortBy;

        psOrderBy = Sort_By;
        psSortBy = SortBy;
        psPageIndex = iPage;

        int PageIndex = iPage;
        int PageSize;

        if (Authentication.IsUserAuthenticated)
        {
            UserFeaturesConfiguration.Fetch(UserIdentity.UserID);

            if (UserFeaturesConfiguration.IsUserChoosePreferredLayoutPageSize)
            {
                PanelDropdownOptionTopPreferred.Visible = true;
                PanelDropdownOptionBottomPreferred.Visible = true;
                psPageSize = UserFeaturesConfiguration.GetUserPreferredPageSize;
                PageSize = UserFeaturesConfiguration.GetUserPreferredPageSize;
                RecipeSort.RepeatColumns = UserFeaturesConfiguration.GetUserPreferredLayout;
                lbps.Text = " - Displaying <b>" + psPageSize + "</b> records per page";

                ReturnURL = this.Request.Url.PathAndQuery;

                SelectedValuePrefLayout = Utility.PreferredLayoutSelectedValue(UserFeaturesConfiguration.GetUserPreferredLayout);
                pLayout = Utility.PreferredLayout(UserFeaturesConfiguration.GetUserPreferredLayout);
            }
            else
            {
                PanelDropdownOptionTopNotPreferred.Visible = true;
                PanelDropdownOptionBottomNotPreferred.Visible = true;

                lbps.Text = " - Displaying <b>10</b> records per page";

                if (!string.IsNullOrEmpty(this.Request.QueryString["ps"]))
                {
                    psPageSize = (int)Util.Val(Request.QueryString["ps"]);
                    lbps.Text = " - Displaying <b>" + psPageSize + "</b> records per page";
                }

                if (psPageSize > 50)
                    psPageSize = 10;

                SelectedValuePrefLayout = Utility.PreferredLayoutSelectedValue(Layout);
                pLayout = Utility.PreferredLayout(Layout);

                PageSize = psPageSize;
                RecipeSort.RepeatColumns = pLayout;
            }
        }
        else
        {
            PanelDropdownOptionTopNotPreferred.Visible = true;
            PanelDropdownOptionBottomNotPreferred.Visible = true;

            lbps.Text = " - Displaying <b>10</b> records per page";

            if (!string.IsNullOrEmpty(this.Request.QueryString["ps"]))
            {
                psPageSize = (int)Util.Val(Request.QueryString["ps"]);
                lbps.Text = " - Displaying <b>" + psPageSize + "</b> records per page";
            }

            if (psPageSize > 50)
                psPageSize = 10;

            SelectedValuePrefLayout = Utility.PreferredLayoutSelectedValue(Layout);
            pLayout = Utility.PreferredLayout(Layout);

            PageSize = psPageSize;
            RecipeSort.RepeatColumns = pLayout;
        }

        int TotalRecords = 100;
        int MaxSortPageSize = 5;

        lblcount.Text = "(" + TotalRecords + ")";

        RecipeSortProvider GetRecipe = RecipeSortProvider.GetInstance();
        GetRecipe.RecipeSortParam(SortBy, Sort_By, PageIndex, PageSize);

        PagerLinks Pager = PagerLinks.GetInstance();
        Pager.PagerLinksParam(PageIndex, PageSize, TotalRecords, MaxSortPageSize);

        lbPagerLink.Text = Pager.DisplayNumericPagerLink(ParamURL, SortBy, Sort_By, GetPage, pLayout);

        lblRecpagetop.Text = Pager.GetTopRightPagerCounterCustomPaging;

        lblRecpage.Text = Pager.GetBottomPagerCounterCustomPaging;

        RecipeSort.DataSource = GetRecipe.GetRecipeSort();
        RecipeSort.DataBind();
    }

    private void GetMetaTitleTagKeywords(int iPage)
    {
        SortBy = (int)Util.Val(Request.QueryString["sid"]);

        string strPageTitleAndKeywords = DynamicKeywords.Keywords(constant.intRecipeDynamicKeywordSortPage, SortBy);
        string[] arrayPagetitleAndKeywords = strPageTitleAndKeywords.Split(',');

        GetMetaTitleTagKeyword(arrayPagetitleAndKeywords[0], arrayPagetitleAndKeywords[1], iPage);
    }

    private void GetMetaTitleTagKeyword(string strPageTitle, string strKeywords, int iPage)
    {
        Page.Header.Title = PageTitle.Title(strPageTitle, iPage);
        HtmlMeta metaTag = new HtmlMeta();
        metaTag.Name = "Keywords";
        metaTag.Content = strKeywords;
        this.Header.Controls.Add(metaTag);
    }

    public void RecipeSortItemDataBound(Object s, DataListItemEventArgs e)
    {
        Utility.GetIdentifyItemNewPopular(Convert.ToDateTime(DataBinder.Eval(e.Item.DataItem, "Date")), e,
                                            (int)DataBinder.Eval(e.Item.DataItem, "Hits"));
    }
}
