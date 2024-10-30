#region XD World Recipe V 3
// FileName: ProviderRecipeDetail.cs
// Author: Dexter Zafra
// Date Created: 5/29/2008
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Model;
using XDRecipe.Common.Utilities;

namespace XDRecipe.BL.Providers.Recipes
{
    /// <summary>
    /// Objects in this class manages recipe detail, update and approval database field
    /// </summary>
    public sealed class RecipeDetails : BaseRecipeObj
    {
        public RecipeDetails()
        {
        }

        /// <summary>
        /// Get recipe name, author, date, hits, rating, ingredients, instructions and other field from the DB matching the Recipe ID provided.
        /// </summary>
        public override void FillUp(int ID)
        {
            Utility Util = new Utility();

            try
            {
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetRecipeDetail(ID, Approved);

                dr.Read();

                if (dr["ID"] != DBNull.Value)
                {
                    this._ID = (int)dr["ID"];
                }
                if (dr["Name"] != DBNull.Value)
                {
                    this._RecipeName = (string)dr["Name"];
                }
                if (dr["Author"] != DBNull.Value)
                {
                    this._Author = (string)dr["Author"];
                }
                if (dr["CAT_ID"] != DBNull.Value)
                {
                    this._CatID = (int)dr["CAT_ID"];
                }
                if (dr["NO_RATES"] != DBNull.Value)
                {
                    this._NoRates = dr["NO_RATES"].ToString();
                }
                if (dr["HITS"] != DBNull.Value)
                {
                    this._Hits = (int)dr["HITS"];
                }
                if (dr["Rates"] != DBNull.Value)
                {
                    this._Rating = dr["Rates"].ToString();
                }
                if (dr["Category"] != DBNull.Value)
                {
                    this._Category = (string)dr["Category"];
                }
                if (dr["Ingredients"] != DBNull.Value)
                {
                    this._Ingredients = (string)dr["Ingredients"];
                }
                if (dr["Instructions"] != DBNull.Value)
                {
                    this._Instructions = (string)dr["Instructions"];
                }
                if (dr["Date"] != DBNull.Value)
                {
                    this._Date = (DateTime)(dr["Date"]);
                }
                if (dr["TOTAL_COMMENTS"] != DBNull.Value)
                {
                    this._CountComments = (int)dr["TOTAL_COMMENTS"];
                }
                if (dr["LINK_APPROVED"] != DBNull.Value)
                {
                    this._Approved = (int)dr["LINK_APPROVED"];
                }
                if (dr["RecipeImage"] != DBNull.Value)
                {
                    this._RecipeImage = (string)dr["RecipeImage"];
                }
                if (dr["UID"] != DBNull.Value)
                {
                    this._UID = (int)dr["UID"];
                }
                if (dr["HIT_DATE"] != DBNull.Value)
                {
                    this._HitDate = (DateTime)dr["HIT_DATE"];
                }

                dr.Close();
                dr = null;
            }
            catch
            {
                Util.PageRedirect(1);
            }
        }
    }
}