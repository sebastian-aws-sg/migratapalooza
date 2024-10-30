#region XD World Recipe V 2.8
// FileName: findallrecipebyauthor.cs
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
using XDRecipe.Security;
using XDRecipe.BL.Providers.User;
using XDRecipe.Common.Utilities;

public partial class findallrecipebyauthor : BasePage
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

        strAuthorName = Request.QueryString["author"].ToString();
        string strmetaTag = "Recipe submitted by " + strAuthorName;

        GetMetaTitleTagKeywords(strmetaTag);

        BindList();
    }

    private void BindList()
    {
        strAuthorName = Request.QueryString["author"].ToString();
        OrderBy = (int)Util.Val(Request.QueryString["ob"]);

        string ParamURL = Request.CurrentExecutionFilePath + "?author=" + strAuthorName;

        int GetPage = (int)Util.Val(Request.QueryString["page"]);

        int iPage = 1;

        if (!string.IsNullOrEmpty(this.Request.QueryString["page"]))
            iPage = (int)Util.Val(Request.QueryString["page"]);

        int PageSize = PagerLinks.DefaultPageSize;
        int PageIndex = iPage;

        ProvidersGetAllRecipebyAuthor GetRecipe = ProvidersGetAllRecipebyAuthor.GetInstance();
        GetRecipe.Param(strAuthorName, OrderBy, 0, PageIndex, PageSize);

        PagerLinks Pager = PagerLinks.GetInstance();
        Pager.PagerLinksParam(PageIndex, PageSize, GetRecipe.RecordCount);

        lblcount.Text = "(" + GetRecipe.RecordCount.ToString() + ") recipes found submitted by (<b>" + strAuthorName + "</b>)";

        lbPagerLink.Text = Pager.DisplayNumericPagerLink(ParamURL, OrderBy, 0, GetPage);

        lblRecpagetop.Text = Pager.GetTopRightPagerCounterCustomPaging;

        lblRecpage.Text = Pager.GetBottomPagerCounterCustomPaging;

        RecipeCat.DataSource = GetRecipe.GetAllRecipeByAuthorResult();
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

        if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
        {
            HyperLink editrecipelink = (HyperLink)(e.Item.FindControl("editrecipelink"));

            int UserID = (int)DataBinder.Eval(e.Item.DataItem, "UID");

            if (Authentication.IsUserAuthenticated && UserID == UserIdentity.UserID)
            {
                editrecipelink.Visible = true;
                editrecipelink.Text = "<img src='images/icon_pencil.gif' alt='Edit' border='0'> Edit";
                editrecipelink.NavigateUrl = "editrecipe.aspx?id=" + (int)DataBinder.Eval(e.Item.DataItem, "ID");
                editrecipelink.Attributes.Add("onmouseover", "Tip('Edit my submitted <b>" + DataBinder.Eval(e.Item.DataItem, "RecipeName") + "</b> recipe.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
                editrecipelink.Attributes.Add("onmouseout", "UnTip()");
            }
        }

    }
}
