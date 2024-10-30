#region XD World Recipe V 3
// FileName: LoadDropDownListCategory.cs
// Author: Dexter Zafra
// Date Created: 5/15/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using XDRecipe.Common;
using System.Web.UI.WebControls;
using System.Collections;
using XDRecipe.BL.Providers.Article;
using XDRecipe.BL.Providers.Recipes;

namespace XDRecipe.BL
{
    /// <summary>
    /// Object in this class load the category dropdownlist.
    /// </summary>
    public static class LoadDropDownListCategory
    {
        public static void LoadDropDownCategory(string SectionName, DropDownList ddlname, string defaultvalue)
        {
            if (SectionName == "Article Category")
            {
                ProviderArticleCategoryDropdown LoadArticleCategoryData = new ProviderArticleCategoryDropdown();
                DropdownlistHelper.FillUpDropDown(ddlname, LoadArticleCategoryData.categoryListArticle, defaultvalue);
                LoadArticleCategoryData = null;
            }
            else if (SectionName == "Recipe Category")
            {
                ProviderRecipeCategoryDropdown LoadRecipeCategoryData = new ProviderRecipeCategoryDropdown();
                DropdownlistHelper.FillUpDropDown(ddlname, LoadRecipeCategoryData.categoryListRecipe, defaultvalue);
                LoadRecipeCategoryData = null;
            }
        }
    }
}
