#region XD World Recipe V 3
// FileName: ProviderPopularRecipeSideMenu.cs
// Author: Dexter Zafra
// Date Created: 8/26/2008
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
    /// object in this class manages popular recipe sidemenu object List collection
    /// </summary>
    public sealed class PopularRecipeSideMenuProvider : Recipe
    {
        private static readonly PopularRecipeSideMenuProvider Instance = new PopularRecipeSideMenuProvider();
 
        static PopularRecipeSideMenuProvider() 
        {
        }

        PopularRecipeSideMenuProvider() 
        { 
        }

        public static PopularRecipeSideMenuProvider GetInstance()
        {
          return Instance;
        }

        public void PopularRecipeParam(int CatId, int Top)
        {
            if (CatId != null)
            {
                this._CatID = CatId;
            }

            this._Top = Top;
        }

        /// <summary>
        /// Returns Most Popular Recipes Side Menu
        /// </summary>
        public ExtendedCollection<Recipe> GetPopularRecipe()
        {
            ExtendedCollection<Recipe> list = new ExtendedCollection<Recipe>();

            string Key = "MostPopular_RecipesSideMenu_" + CatID.ToString();

            if (Caching.Cache[Key] != null)
            {
                list = (ExtendedCollection<Recipe>)Caching.Cache[Key];
            }
            else
            {
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetMostpopularRecipesSideMenu(CatID, Top);

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
                    if (dr["HITS"] != DBNull.Value)
                    {
                        item.Hits = (int)dr["HITS"];
                    }
                    if (dr["RecipeImage"] != DBNull.Value)
                    {
                        item.RecipeImage = (string)dr["RecipeImage"];
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
