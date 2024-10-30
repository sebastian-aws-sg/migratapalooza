#region XD World Recipe V 3
// FileName: ProviderAdminRecipeCategory.cs
// Author: Dexter Zafra
// Date Created: 8/19/2008
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Model;

namespace XDRecipe.BL.Providers.Recipes
{
    /// <summary>
    /// object in this class manages admin recipe category object List collection
    /// </summary>
    public static class AdminRecipeCategoryProvider
    {
        /// <summary>
        /// Return Recipe Category Data
        /// </summary>
        public static ExtendedCollection<Recipe> GetCategories()
        {
            ExtendedCollection<Recipe> list = new ExtendedCollection<Recipe>();

            IDataReader dr = Blogic.ActionProcedureDataProvider.AdminGetRecipeCategoryManager;

            while (dr.Read())
            {
                Recipe item = new Recipe();

                item.CatID = (int)dr["CAT_ID"];

                if (dr["CAT_TYPE"] != DBNull.Value)
                {
                    item.Category = (string)dr["CAT_TYPE"];
                }
                if (dr["GROUPCAT"] != DBNull.Value)
                {
                    item.Group = (string)dr["GROUPCAT"];
                }
                if (dr["REC_COUNT"] != DBNull.Value)
                {
                    item.RecordCount = (int)(dr["REC_COUNT"]);
                }

                list.Add(item);
            }

            dr.Close();

            return list;
        }
    }
}
