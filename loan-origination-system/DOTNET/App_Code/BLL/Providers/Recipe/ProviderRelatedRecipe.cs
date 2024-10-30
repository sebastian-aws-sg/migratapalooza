#region XD World Recipe V 2.8
// FileName: ProviderRelatedRecipe.cs
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
    /// object in this class manages related recipe object List collection
    /// </summary>
    public static class RelatedRecipeProvider
    {
        /// <summary>
        /// Returns related recipe
        /// </summary>
        public static ExtendedCollection<Recipe> GetRelatedRecipes(int CatId)
        {
            ExtendedCollection<Recipe> list = new ExtendedCollection<Recipe>();

            IDataReader dr = Blogic.ActionProcedureDataProvider.GetRelatedRecipe(CatId);

            while (dr.Read())
            {
                Recipe item = new Recipe();

                item.ID = (int)dr["ID"];

                if (dr["Name"] != DBNull.Value)
                {
                    item.RecipeName = (string)dr["Name"];
                }
                if (dr["Category"] != DBNull.Value)
                {
                    item.Category = (string)dr["Category"];
                }
                if (dr["Hits"] != DBNull.Value)
                {
                    item.Hits = (int)(dr["Hits"]);
                }
                if (dr["RecipeImage"] != DBNull.Value)
                {
                    item.RecipeImage = (string)dr["RecipeImage"];
                }

                list.Add(item);
            }

            dr.Close();

            return list;
        }
    }
}