#region XD World Recipe V 3
// FileName: ProviderRecipeCategorySideMenu.cs
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
    /// object in this class manages recipe category side menu object List collection
    /// </summary>
    public static class RecipeCategoryMenuProvider
    {
        /// <summary>
        /// Return Recipe Category
        /// </summary>
        public static ExtendedCollection<Recipe> GetRecipeCategoryMenu()
        {
            ExtendedCollection<Recipe> list = new ExtendedCollection<Recipe>();

            string Key = "RecipeCategory_SideMenu";

            if (Caching.Cache[Key] != null)
            {
                list = (ExtendedCollection<Recipe>)Caching.Cache[Key];
            }
            else
            {
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetRecipeCategoryList_SideMenu;

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