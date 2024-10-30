#region XD World Recipe V 3
// FileName: ProviderRecipeSearch.cs
// Author: Dexter Zafra
// Date Created: 8/19/2008
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using System.Collections.Generic;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Model;

namespace XDRecipe.BL.Providers.Recipes
{
    /// <summary>
    /// object in this class manages recipe category object List collection
    /// </summary>
    public sealed class RecipeSearchProvider : Recipe
    {
        private static readonly RecipeSearchProvider Instance = new RecipeSearchProvider();
 
        static RecipeSearchProvider() 
        {
        }

        RecipeSearchProvider() 
        { 
        }

        public static RecipeSearchProvider GetInstance()
        {
          return Instance;
        }

        public void RecipeSearchParam(string Keyword, int CatId, int OrderBy, int SortBy, int PageIndex, int PageSize)
        {
            this._CatID = CatId;
            this._Keyword = Keyword;
            this._OrderBy = OrderBy;
            this._SortBy = SortBy;
            this._Index = PageIndex;
            this._PageSize = PageSize;

            IDataReader dr = GetData;

            while (dr.Read())
            {
                //Get category name and record count
                this._RecordCount = (int)dr["RCount"];
                this._Category = (string)dr["Category"];
            }

            dr.Close();
        }

        /// <summary>
        /// Return Data
        /// </summary>
        /// <returns></returns>
        private IDataReader GetData
        {
            get
            {
                //Get data
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetSearchResult(Keyword, CatID, OrderBy, Index, PageSize);
                return dr;
            }
        }

        public ExtendedCollection<Recipe> GetRecipeSearchResult()
        {
            ExtendedCollection<Recipe> list = new ExtendedCollection<Recipe>();

            IDataReader dr = GetData;

            while (dr.Read())
            {
                Recipe item = new Recipe();

                item.ID = (int)dr["ID"];

                item.CatID = (int)dr["CAT_ID"];

                if (dr["Name"] != DBNull.Value)
                {
                    item.RecipeName = (string)dr["Name"];
                }
                if (dr["Category"] != DBNull.Value)
                {
                    item.Category = (string)dr["Category"];
                }
                if (dr["Author"] != DBNull.Value)
                {
                    item.Author = (string)dr["Author"];
                }
                if (dr["Rates"] != DBNull.Value)
                {
                    item.Rating = dr["Rates"].ToString();
                }
                if (dr["NO_RATES"] != DBNull.Value)
                {
                    item.NoRates = dr["NO_RATES"].ToString();
                }
                if (dr["Date"] != DBNull.Value)
                {
                    item.Date = (DateTime)(dr["Date"]);
                }
                if (dr["HITS"] != DBNull.Value)
                {
                    item.Hits = (int)dr["HITS"];
                }

                list.Add(item);
            }

            dr.Close();

            return list;
        }
    }
}
