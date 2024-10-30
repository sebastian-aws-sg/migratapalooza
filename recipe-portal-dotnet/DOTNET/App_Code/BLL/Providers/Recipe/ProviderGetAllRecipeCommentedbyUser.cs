#region XD World Recipe V 3
// FileName: ProviderGetAllRecipeCommentedbyUser.cs
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
    /// object in this class returns all recipe by author
    /// </summary>
    public sealed class ProvidersGetAllRecipeCommentedbyUser : Recipe
    {
        private static readonly ProvidersGetAllRecipeCommentedbyUser Instance = new ProvidersGetAllRecipeCommentedbyUser();

        static ProvidersGetAllRecipeCommentedbyUser()
        {
        }

        ProvidersGetAllRecipeCommentedbyUser()
        {
        }

        public static ProvidersGetAllRecipeCommentedbyUser GetInstance()
        {
            return Instance;
        }

        public void Param(string AuthorName, int OrderBy, int SortBy, int PageIndex, int PageSize)
        {
            this._AuthorName = AuthorName;
            this._OrderBy = OrderBy;
            this._SortBy = SortBy;
            this._Index = PageIndex;
            this._PageSize = PageSize;

            IDataReader dr = GetData;

            while (dr.Read())
            {
                //Get category name and record count
                this._RecordCount = (int)dr["RCount"];
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
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetAllRecipeCommentedByUser(AuthorName, OrderBy, Index, PageSize);
                return dr;
            }
        }

        public ExtendedCollection<Recipe> GetAllRecipeCommentedByAuthorResult()
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
                if (dr["UID"] != DBNull.Value)
                {
                    item.UID = (int)dr["UID"];
                }

                list.Add(item);
            }

            dr.Close();

            return list;
        }
    }
}


