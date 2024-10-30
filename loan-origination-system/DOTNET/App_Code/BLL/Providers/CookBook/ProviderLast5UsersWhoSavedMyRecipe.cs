#region XD World Recipe V 3
// FileName: ProviderLast5UsersWhoSavedMyRecipe.cs
// Author: Dexter Zafra
// Date Created: 3/29/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using XDRecipe.Model;
using XDRecipe.BL;
using XDRecipe.Common;

namespace XDRecipe.BL.Providers.CookBooks
{
    /// <summary>
    /// Object in this class returns all users who added me in Friends List
    /// </summary>
    public class ProviderLast5UsersWhoSavedMyRecipe : CookBook
    {
        public ProviderLast5UsersWhoSavedMyRecipe(int UserID)
        {
            this._UID = UserID;
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
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetLast5UsersWhoSavedMyRecipeInCookBook(UID);
                return dr;
            }
        }

        public ExtendedCollection<CookBook> GetUsers()
        {
            ExtendedCollection<CookBook> list = new ExtendedCollection<CookBook>();

            IDataReader dr = GetData;

            while (dr.Read())
            {
                CookBook item = new CookBook();

                if (dr["UID"] != DBNull.Value)
                {
                    item.UID = (int)dr["UID"];
                }
                if (dr["UserName"] != DBNull.Value)
                {
                    item.UserName = (string)dr["UserName"];
                }
                if (dr["Date"] != DBNull.Value)
                {
                    item.Date = (DateTime)(dr["Date"]);
                }

                list.Add(item);
            }

            dr.Close();

            return list;
        }

    }
}
