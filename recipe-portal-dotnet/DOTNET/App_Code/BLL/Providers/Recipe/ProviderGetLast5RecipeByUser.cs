#region XD World Recipe V 3
// FileName: ProviderGetLast5RecipeByUser.cs
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
    /// object in this class returns a list of the last 5 recipe published by user.
    /// </summary>
    public sealed class ProviderGetLast5RecipeByUser : Recipe
    {
        private static readonly ProviderGetLast5RecipeByUser Instance = new ProviderGetLast5RecipeByUser();

        static ProviderGetLast5RecipeByUser()
        {
        }

        ProviderGetLast5RecipeByUser()
        {
        }

        public static ProviderGetLast5RecipeByUser GetInstance()
        {
            return Instance;
        }

        public void Param(int UserID)
        {
            this._UID = UserID;
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
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetLast5RecipeByUser(UID);
                return dr;
            }
        }

        public ExtendedCollection<Recipe> GetRecipe()
        {
           ExtendedCollection<Recipe> list = new ExtendedCollection<Recipe>();

           string Key = "Last5_RecipePublishedByUser_" + UID;

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

