#region XD World Recipe V 2.8
// FileName: Category.cs
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
using XDRecipe.Security;
using XDRecipe.BL.Providers.User;

public partial class recipecategory : BasePage
{
    private int CatId;
    private int OrderBy;
    private int SortBy;
    private int iPage;

    public int psCatId;
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

    protected void Page_Load(Object sender, EventArgs e)
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

        iPage = 1;

        if (!string.IsNullOrEmpty(this.Request.QueryString["page"]))
            iPage = (int)Util.Val(Request.QueryString["page"]);

        int GetPage = (int)Util.Val(Request.QueryString["page"]);

        int Layout = (int)Util.Val(Request.QueryString["layout"]);

        BindList(iPage, GetPage, Layout);

        GetMetaTitleTagKeywords(lblcatname2.Text, iPage);
    }

    private void BindList(int iPage, int GetPage, int Layout)
    {
        CatId = (int)Util.Val(Request.QueryString["catid"]);
        OrderBy = (int)Util.Val(Request.QueryString["ob"]);
        SortBy = (int)Util.Val(Request.QueryString["sb"]);

        psPageSize = PagerLinks.DefaultPageSize;

        string ParamURL = Request.CurrentExecutionFilePath + "?catid=" + CatId;

        psCatId = CatId;
        psOrderBy = OrderBy;
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
                RecipeCat.RepeatColumns = UserFeaturesConfiguration.GetUserPreferredLayout;
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
                RecipeCat.RepeatColumns = pLayout;
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
            RecipeCat.RepeatColumns = pLayout;
        }

        RecipeCategoryProvider GetCategory = RecipeCategoryProvider.GetInstance();
        GetCategory.CategoryParam(CatId, OrderBy, SortBy, PageIndex, PageSize);

        PagerLinks Pager = PagerLinks.GetInstance();
        Pager.PagerLinksParam(PageIndex, PageSize, GetCategory.RecordCount);

        lblcatname2.Text = GetCategory.Category;
        lblcount.Text = "(" + GetCategory.RecordCount.ToString() + ")";

        lbPagerLink.Text = Pager.DisplayNumericPagerLink(ParamURL, OrderBy, SortBy, GetPage, pLayout);

        lblRecpagetop.Text = Pager.GetTopRightPagerCounterCustomPaging;

        lblRecpage.Text = Pager.GetBottomPagerCounterCustomPaging;

        RecipeCat.DataSource = GetCategory.GetCategories();
        RecipeCat.DataBind();
    }

    private void GetMetaTitleTagKeywords(string strPageTitle, int iPage)
    {
        string GetCategoryName;
        GetCategoryName = strPageTitle + " Recipes ";

        Page.Header.Title = PageTitle.Title(GetCategoryName, iPage);
        HtmlMeta metaTag = new HtmlMeta();
        metaTag.Name = "Keywords";

        metaTag.Content = strPageTitle + DynamicKeywords.Keywords(constant.intRecipeDynamicKeywordCategory);
        this.Header.Controls.Add(metaTag);
    }

    public void RecipeCatItemDataBound(Object s, DataListItemEventArgs e)
    {
        Utility.GetIdentifyItemNewPopular(Convert.ToDateTime(DataBinder.Eval(e.Item.DataItem, "Date")), e,
                                            (int)DataBinder.Eval(e.Item.DataItem, "Hits"));
    }
}
