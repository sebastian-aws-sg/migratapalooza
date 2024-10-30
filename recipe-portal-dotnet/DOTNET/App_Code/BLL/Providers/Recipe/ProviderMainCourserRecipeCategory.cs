#region XD World Recipe V 3
// FileName: ProviderMainCourserRecipeCategory.cs
// Author: Dexter Zafra
// Date Created: 8/26/2008
// Website: www.ex-designz.net
#endregion
using System;
using System.Web;
using System.Data;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Model;

namespace XDRecipe.BL.Providers.Recipes
{
    /// <summary>
    /// object in this class manages main course recipe category object List collection
    /// </summary>
    public static class MainCourseRecipeCategoryProvider
    {
        /// <summary>
        /// Return Recipe Main Course Category
        /// </summary>
        /// <returns></returns>
        public static ExtendedCollection<Recipe> GetMainCourseCategory()
        {
            ExtendedCollection<Recipe> list = new ExtendedCollection<Recipe>();

            string Key = "MainCourse_RecipeCategory";
         
            if (Caching.Cache[Key] != null)
            {
                list = (ExtendedCollection<Recipe>)Caching.Cache[Key];
            }
            else
            {
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetHomepageMainCourseCategory;

                while (dr.Read())
                {
                    Recipe item = new Recipe();

                    item.CatID = (int)dr["CAT_ID"];

                    if (dr["CAT_TYPE"] != DBNull.Value)
                    {
                        item.Category = (string)dr["CAT_TYPE"];
                    }
                    if (dr["REC_COUNT"] != DBNull.Value)
                    {
                        item.RecordCount = (int)(dr["REC_COUNT"]);
                    }

                    list.Add(item);

                    Caching.CahceData(Key, list);
                }

                dr.Close();
            }

            return list;
        }
    }
}