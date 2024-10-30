#region XD World Recipe V 2.8
// FileName: members.cs
// Author: Dexter Zafra
// Date Created: 2/20/2009
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
using XDRecipe.BL.Providers.User;
using XDRecipe.UI;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Security;
using XDRecipe.Common.Utilities;
using XDRecipe.BL.Providers.CookBooks;
using XDRecipe.BL.Providers.FriendList;
using XDRecipe.Security;
using XDRecipe.BL.Providers.User;

public partial class members : BasePage
{
    private Utility Util
    {
        get { return new Utility(); }
    }

    protected void Page_Load(object sender, EventArgs e)
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

        ProviderShowAllUsers Members = new ProviderShowAllUsers(OrderBy, SortBy, PageIndex, PageSize);

        lblcounter.Text = "<img src='images/community-users-icon.jpg' align='absmiddle'>&nbsp;&nbsp;Total Registered Users: " + Members.TotalCount;

        PagerLinks Pager = PagerLinks.GetInstance();
        Pager.PagerLinksParam(PageIndex, PageSize, Members.TotalCount);

        lbPagerLink.Text = Pager.DisplayNumericPagerLink(ParamURL, OrderBy, SortBy, iPage);

        lblRecpage.Text = Pager.GetBottomPagerCounterCustomPaging;

        MembersRep.DataSource = Members.GetAllUsers();
        MembersRep.DataBind();

        lblAplhaLetterLinks.Text = AlphabetLink.BuildLinkSearchMembers("searchuser.aspx?input=", "content12", "Browse all username starting with letter", "&nbsp;&nbsp;&nbsp;");

        SortOptionLinks(OrderBy, SortBy, iPage);

        Members = null;
    }

    private void SortOptionLinks(int OrderBy, int Sort_By, int iPage)
    {
        if (OrderBy == 1)
        {
            SortLinkHits.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=2&page=" + iPage;
            SortLinkHits.Text = "Most Popular";
            SortLinkHits.ToolTip = "Sort by Most Popular Users ASC Order";
            ArrowImage1.ImageUrl = Util.SortOptionArrowImage;
            ArrowImage1.Visible = true;

            if (Sort_By == 2)
            {
                SortLinkHits.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=1&page=" + iPage;
                SortLinkHits.Text = "Most Popular";
                SortLinkHits.ToolTip = "Sort by Most Popular Users DESC Order";
                ArrowImage1.ImageUrl = Util.SortOptionArrowUpImage;
                ArrowImage1.Visible = true;
            }
        }
        else
        {
            SortLinkHits.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=1" + "&sb=1&page=" + iPage;
            SortLinkHits.Text = "Most Popular";
            SortLinkHits.ToolTip = "Sort by Most Popular Users";
            ArrowImage1.Visible = false;
        }

        if (OrderBy == 2)
        {
            SortLinkLastVisit.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=2&page=" + iPage;
            SortLinkLastVisit.Text = "Last Visit";
            SortLinkLastVisit.ToolTip = "Sort by Last Visit Users ASC Order";
            ArrowImage5.ImageUrl = Util.SortOptionArrowImage;
            ArrowImage5.Visible = true;

            if (Sort_By == 2)
            {
                SortLinkLastVisit.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=1&page=" + iPage;
                SortLinkLastVisit.Text = "Last Visit";
                SortLinkLastVisit.ToolTip = "Sort by Last Visit Users DESC Order";
                ArrowImage5.ImageUrl = Util.SortOptionArrowUpImage;
                ArrowImage5.Visible = true;
            }
        }
        else
        {
            SortLinkLastVisit.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=2" + "&sb=1&page=" + iPage;
            SortLinkLastVisit.Text = "Last Visit";
            SortLinkLastVisit.ToolTip = "Sort by Last Visit Users";
            ArrowImage5.Visible = false;
        }

        if (OrderBy == 3)
        {
            SortLinkUsername.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=2&page=" + iPage;
            SortLinkUsername.Text = "Username";
            SortLinkUsername.ToolTip = "Sort by username ASC order";
            ArrowImage2.ImageUrl = Util.SortOptionArrowImage;
            ArrowImage2.Visible = true;

            if (Sort_By == 2)
            {
                SortLinkUsername.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=1&page=" + iPage;
                SortLinkUsername.Text = "Username";
                SortLinkUsername.ToolTip = "Sort by username DESC order";
                ArrowImage2.ImageUrl = Util.SortOptionArrowUpImage;
                ArrowImage2.Visible = true;
            }
            if (Sort_By == 0)
            {
                SortLinkUsername.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=1&page=" + iPage;
                SortLinkUsername.Text = "Username";
                SortLinkUsername.ToolTip = "Sort by username DESC order";
                ArrowImage2.ImageUrl = Util.SortOptionArrowUpImage;
                ArrowImage2.Visible = true;
            }
        }
        else
        {
            SortLinkUsername.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=3" + "&sb=1&page=" + iPage;
            SortLinkUsername.Text = "Username";
            SortLinkUsername.ToolTip = "Sort by username";
            ArrowImage2.Visible = false;
        }

        if (OrderBy == 4)
        {
            SortLinkDateJoined.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=2&page=" + iPage;
            SortLinkDateJoined.Text = "Date Joined";
            SortLinkDateJoined.ToolTip = "Sort by Date Joined ASC order";
            ArrowImage4.ImageUrl = Util.SortOptionArrowImage;
            ArrowImage4.Visible = true;

            if (Sort_By == 2)
            {
                SortLinkDateJoined.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=" + OrderBy + "&sb=1&page=" + iPage;
                SortLinkDateJoined.Text = "Date Joined";
                SortLinkDateJoined.ToolTip = "Sort by Date Joined DESC order";
                ArrowImage4.ImageUrl = Util.SortOptionArrowUpImage;
                ArrowImage4.Visible = true;
            }
        }
        else
        {
            SortLinkDateJoined.NavigateUrl = Request.CurrentExecutionFilePath + "?ob=4&sb=" + Sort_By + "&page=" + iPage;
            SortLinkDateJoined.Text = "Date Joined";
            SortLinkDateJoined.ToolTip = "Sort by Date Joined";
            ArrowImage4.Visible = false;

        }
    }
}

