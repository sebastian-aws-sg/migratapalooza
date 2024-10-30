#region XD World Recipe V 3
// FileName: ProviderRecipeAlphaLetter.cs
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
    /// object in this class manages recipe category object List collection
    /// </summary>
    public sealed class RecipeAlphaLetterProvider : Recipe
    {
        private static readonly RecipeAlphaLetterProvider Instance = new RecipeAlphaLetterProvider();
 
        static RecipeAlphaLetterProvider() 
        {
        }

        RecipeAlphaLetterProvider() 
        { 
        }

        public static RecipeAlphaLetterProvider GetInstance()
        {
          return Instance;
        }

        public void RecipeAlphaLetterParam(string letter, int OrderBy, int SortBy, int PageIndex, int PageSize)
        {
            this._Letter = letter;
            this._OrderBy = OrderBy;
            this._SortBy = SortBy;
            this._Index = PageIndex;
            this._PageSize = PageSize;

            IDataReader dr = GetData;

            dr.Read();

            this._RecordCount = (int)dr["RCount"];

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
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetRecipeAlphaLetterWithPaging(Letter, OrderBy, SortBy, Index, PageSize);
                return dr;
            }
        }

        public ExtendedCollection<Recipe> GetRecipeAlphabetLetter()
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
