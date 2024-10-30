#region XD World Recipe V 2.8
// FileName: default.cs
// Author: Dexter Zafra
// Date Created: 5/19/2008
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;
using XDRecipe.UI;
using XDRecipe.BL;
using XDRecipe.BL.Providers.Recipes;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.Common.Utilities;

public partial class _default : BasePage
{
    protected void Page_Load(Object sender, EventArgs e)
    {
        GetMetaKeywords();

        lbltotalRecipe.Text = "There are " + string.Format("{0:#,###}",
        Blogic.ActionProcedureDataProvider.GetHomepageTotalRecipeCount) + " recipes in " + Blogic.ActionProcedureDataProvider.GetHomepageTotalCategoryCount + " categories";

        Myranimage.ImageUrl = Utility.GetRandomImage;

        BindList();
    }

    private void BindList()
    {
        MainCourseCategory.DataSource = MainCourseRecipeCategoryProvider.GetMainCourseCategory();
        MainCourseCategory.DataBind();

        EthnicRegionalCat.DataSource = EthnicRecipeCategoryProvider.GetEthnicCategory();
        EthnicRegionalCat.DataBind();
    }

    private void GetMetaKeywords()
    {
        Page.Header.Title = "XD Portal";
        HtmlMeta metaTag = new HtmlMeta();
        metaTag.Name = "Keywords";
        metaTag.Content = "chinese recipe, barbeque recipe, seafoods recipes, salad recipe, mexican recipe";
        this.Header.Controls.Add(metaTag);
    }
}
