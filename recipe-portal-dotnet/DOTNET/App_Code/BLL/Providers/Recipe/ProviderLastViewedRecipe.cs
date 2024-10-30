#region XD World Recipe V 3
// FileName: ProviderLastViewedRecipe.cs
// Author: Dexter Zafra
// Date Created: 7/15/2008
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
    /// Objects in this class manages last viewed recipe
    /// </summary>
    public static class LastViewedRecipeProvider
    {
        /// <summary>
        /// Returns the number of hours
        /// </summary>
        public static int GetMinuteSpan
        {
            get
            {
                int MinuteSpan = 0;

                try
                {
                    IDataReader dr = GetData;

                    dr.Read();

                    //Get the Minutes/hours span
                    MinuteSpan = (int)dr["MinuteSpan"];

                    dr.Close();
                }
                catch
                {
                }

                return MinuteSpan;
            }
        }

        /// <summary>
        /// Return Last Viewed Recipe Data
        /// </summary>
        /// <returns></returns>
        public static IDataReader GetData
        {
            get
            {
                //Get data
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetLastViewedRecipe;

                return dr;
            }
        }

        public static ExtendedCollection<Recipe> GetLastViewedRecipe()
        {
            ExtendedCollection<Recipe> list = new ExtendedCollection<Recipe>();

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
                    if (dr["Hits"] != DBNull.Value)
                    {
                        item.Hits = (int)(dr["Hits"]);
                    }
                    if (dr["Hours"] != DBNull.Value)
                    {
                        item.Hours = (int)(dr["Hours"]);
                    }
                    if (dr["Minutes"] != DBNull.Value)
                    {
                        item.Minutes = (int)(dr["Minutes"]);
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
