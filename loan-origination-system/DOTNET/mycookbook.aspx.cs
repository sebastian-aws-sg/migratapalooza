#region XD World Recipe V 3
// FileName: MyCookBook.cs
// Author: Dexter Zafra
// Date Created: 2/25/2009
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
using XDRecipe.Common;
using XDRecipe.Security;
using XDRecipe.Common.Utilities;
using XDRecipe.BL.Providers.CookBooks;
using XDRecipe.BL.Providers.FriendList;
using XDRecipe.BL.Providers.User;

public partial class MyCookBook : BasePage
{
    private Utility Util
    {
        get { return new Utility(); }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Authentication.IsUserAuthenticated)
        {
            HideContentIfNotLogin.Visible = true;

            int UserID = UserIdentity.UserID;
            string UserName = UserIdentity.UserName;

            lblusernameheader.Text = UserName + "'s CookBook";

            Blogic.UpdateUserLastLogin(UserID);
            GetMetaTitleTagKeywords(UserName);

            LoadData(UserID);
        }
        else
        {
            GetMetaTitleTagKeywords("Denied View");
            HideContentIfNotLogin.Visible = false;
            lblyouarenotlogin.Visible = true;
            lblyouarenotlogin.Text = "<div style='margin-top: 12px; margin-bottom: 7px;'><img src='images/lock.gif' align='absmiddle'> You are not authorize to view this CookBook. Please login to view your CookBook.</div>";
        }
    }

    private void LoadData(int UserID)
    {
        int OrderBy = (int)Util.Val(Request.QueryString["ob"]);
        int SortBy = (int)Util.Val(Request.QueryString["sb"]);

        string ParamURL = Request.CurrentExecutionFilePath + "?";

        int iPage;

        if (string.IsNullOrEmpty(this.Request.QueryString["page"]))
            iPage = 1;
        else
            iPage = (int)Util.Val(Request.QueryString["page"]);

        int PageSize = 20;
        int PageIndex = iPage;

        ProviderCookBookMainPage CookBook = new ProviderCookBookMainPage(UserID, OrderBy, SortBy, PageIndex, PageSize);

        PagerLinks Pager = PagerLinks.GetInstance();
        Pager.PagerLinksParam(PageIndex, PageSize, CookBook.TotalCount);

        lbPagerLink.Text = Pager.DisplayNumericPagerLink(ParamURL, OrderBy, SortBy, iPage);

        lblRecpage.Text = Pager.GetBottomPagerCounterCustomPaging;

        SavedRecipeInCookBook.DataSource = CookBook.GetUserRecipeInCookBookMain();
        SavedRecipeInCookBook.DataBind();

        int NumRecordsAllowed = SiteConfiguration.GetConfiguration.NumberOfrecipeInCookBook;

        int Remaining = NumRecordsAllowed - CookBook.TotalCount;

        lblcounter.Text = "<img src='images/cookbookicon.gif' align='absmiddle'>&nbsp;&nbsp;You have (" + CookBook.TotalCount + ") saved recipe. The maximun allowed to save is " + NumRecordsAllowed + ". You have a remaining of " + Remaining + " recipe to save.";

        SortOptionLinks(OrderBy, SortBy, iPage);

        CookBook = null;
    }

    public void SavedRecipeInCookBook_ItemDataBound(Object s, RepeaterItemEventArgs e)
    {
        if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
        {
            Label lblDelete = (Label)(e.Item.FindControl("lblDelete"));

            int ID = (int)DataBinder.Eval(e.Item.DataItem, "itemID");

            lblDelete.Text = "<a class='thickbox' href='#TB_inline?height=75&width=350&inlineId=confirmModal" + ID + "&modal=true'><img border='0' src='images/icon_delete.gif'></a>";
            lblDelete.Attributes.Add("onmouseover", "Tip('Remove (<b>" + DataBinder.Eval(e.Item.DataItem, "RecipeName") + "</b>) recipe from my CookBook.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            lblDelete.Attributes.Add("onmouseout", "UnTip()");
        }
    }

    private void GetMetaTitleTagKeywords(string UserName)
    {
        Page.Header.Title = UserName + " CookBook";
        HtmlMeta metaTag = new HtmlMeta();
        metaTag.Name = "Keywords";
        metaTag.Content = UserName + " CookBook.";
        this.Header.Controls.Add(metaTag);
    }

    private void SortOptionLinks(int OrderBy, int Sort_By, int iPage)
    {
        if (OrderBy == 1)
        {
            SortLinkHits.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=2&page=" + iPage;
            SortLinkHits.Text = "Most Popular";
            SortLinkHits.ToolTip = "Sort by Most Popular Recipe ASC Order";
            ArrowImage1.ImageUrl = Util.SortOptionArrowImage;
            ArrowImage1.Visible = true;

            if (Sort_By == 2)
            {
                SortLinkHits.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=1&page=" + iPage;
                SortLinkHits.Text = "Most Popular";
                SortLinkHits.ToolTip = "Sort by Most Popular Recipe DESC Order";
                ArrowImage1.ImageUrl = Util.SortOptionArrowUpImage;
                ArrowImage1.Visible = true;
            }
        }
        else
        {
            SortLinkHits.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=1" + "&sb=1&page=" + iPage;
            SortLinkHits.Text = "Most Popular";
            SortLinkHits.ToolTip = "Sort by Most Popular Recipe";
            ArrowImage1.Visible = false;
        }

        if (OrderBy == 2)
        {
            SortLinkRating.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=2&page=" + iPage;
            SortLinkRating.Text = "Most Rated";
            SortLinkRating.ToolTip = "Sort by Most Rated Recipe ASC Order";
            ArrowImage4.ImageUrl = Util.SortOptionArrowImage;
            ArrowImage4.Visible = true;

            if (Sort_By == 2)
            {
                SortLinkRating.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=1&page=" + iPage;
                SortLinkRating.Text = "Most Rated";
                SortLinkRating.ToolTip = "Sort by Most Rated Recipe DESC Order";
                ArrowImage4.ImageUrl = Util.SortOptionArrowUpImage;
                ArrowImage4.Visible = true;
            }
        }
        else
        {
            SortLinkRating.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=2" + "&sb=1&page=" + iPage;
            SortLinkRating.Text = "Most Rated";
            SortLinkRating.ToolTip = "Sort by Most Rated Recipe";
            ArrowImage4.Visible = false;
        }

        if (OrderBy == 3)
        {
            SortLinkRecipeName.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=2&page=" + iPage;
            SortLinkRecipeName.Text = "Recipe Name";
            SortLinkRecipeName.ToolTip = "Sort by Recipe Name ASC order";
            ArrowImage2.ImageUrl = Util.SortOptionArrowImage;
            ArrowImage2.Visible = true;

            if (Sort_By == 2)
            {
                SortLinkRecipeName.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=1&page=" + iPage;
                SortLinkRecipeName.Text = "Recipe Name";
                SortLinkRecipeName.ToolTip = "Sort by Recipe Name DESC order";
                ArrowImage2.ImageUrl = Util.SortOptionArrowUpImage;
                ArrowImage2.Visible = true;
            }
            if (Sort_By == 0)
            {
                SortLinkRecipeName.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=1&page=" + iPage;
                SortLinkRecipeName.Text = "Recipe Name";
                SortLinkRecipeName.ToolTip = "Sort by Recipe Name DESC order";
                ArrowImage2.ImageUrl = Util.SortOptionArrowUpImage;
                ArrowImage2.Visible = true;
            }
        }
        else
        {
            SortLinkRecipeName.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=3" + "&sb=1&page=" + iPage;
            SortLinkRecipeName.Text = "Recipe Name";
            SortLinkRecipeName.ToolTip = "Sort by Recipe Name";
            ArrowImage2.Visible = false;
        }

        if (OrderBy == 4)
        {
            SortLinkDateAdded.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=2&page=" + iPage;
            SortLinkDateAdded.Text = "Date Added";
            SortLinkDateAdded.ToolTip = "Sort by Date Added to CookBook ASC order";
            ArrowImage5.ImageUrl = Util.SortOptionArrowImage;
            ArrowImage5.Visible = true;

            if (Sort_By == 2)
            {
                SortLinkDateAdded.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=1&page=" + iPage;
                SortLinkDateAdded.Text = "Date Added";
                SortLinkDateAdded.ToolTip = "Sort by Date Added to CookBook DESC order";
                ArrowImage5.ImageUrl = Util.SortOptionArrowUpImage;
                ArrowImage5.Visible = true;
            }
        }
        else
        {
            SortLinkDateAdded.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=4&sb=" + Sort_By + "&page=" + iPage;
            SortLinkDateAdded.Text = "Date Added";
            SortLinkDateAdded.ToolTip = "Sort by Date Added to CookBook";
            ArrowImage5.Visible = false;
        }
    }
}
