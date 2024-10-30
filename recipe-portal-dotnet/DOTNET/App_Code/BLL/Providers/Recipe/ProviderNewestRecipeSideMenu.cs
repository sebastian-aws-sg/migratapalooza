#region XD World Recipe V 3
// FileName: ProviderNewestRecipeSideMenu.cs
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
    /// object in this class manages newest recipe sidemenu object List collection
    /// </summary>
    public sealed class NewestRecipeSideMenuProvider : Recipe
    {
        private static readonly NewestRecipeSideMenuProvider Instance = new NewestRecipeSideMenuProvider();
 
        static NewestRecipeSideMenuProvider() 
        {
        }

        NewestRecipeSideMenuProvider() 
        { 
        }

        public static NewestRecipeSideMenuProvider GetInstance()
        {
          return Instance;
        }

        public void NewestRecipeParam(int CatId, int Top)
        {
            if (CatId != null)
            {
                this._CatID = CatId;
            }

            this._Top = Top;

            IDataReader dr = GetData;

            dr.Read();

            //Get the category name
            this._Category = (string)dr["Category"];

            dr.Close();
        }

        /// <summary>
        /// Return Recipe Category Data
        /// </summary>
        /// <returns></returns>
        private IDataReader GetData
        {
            get
            {
                //Get data
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetNewestRecipesSideMenu(CatID, Top);

                return dr;
            }
        }

        public ExtendedCollection<Recipe> GetNewestRecipe()
        {
            string Key = "Newest_RecipesSideMenu_" + CatID.ToString();

            ExtendedCollection<Recipe> list = new ExtendedCollection<Recipe>();

            if (Caching.Cache[Key] != null)
            {
                list = (ExtendedCollection<Recipe>)Caching.Cache[Key];
            }
            else
            {
                IDataReader dr = GetData;

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
                    if (dr["Date"] != DBNull.Value)
                    {
                        item.Date = (DateTime)(dr["Date"]);
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
