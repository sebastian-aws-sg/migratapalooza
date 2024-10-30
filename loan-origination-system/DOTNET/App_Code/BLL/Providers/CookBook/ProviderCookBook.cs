#region XD World Recipe V 3
// FileName: UsersCookBook.cs
// Author: Dexter Zafra
// Date Created: 2/29/2009
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
    /// Object in this class provides data to user Cookbook
    /// </summary>
    public sealed class UsersCookBook : CookBook
    {
        public UsersCookBook(int UserID, int NumRecords)
        {
            this._UID = UserID;
            this._NumRecords = NumRecords;

            IDataReader dr = GetData;

            while (dr.Read())
            {
                //Get total record count
                this._TotalCount = (int)dr["TotalCount"];
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
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetUsersRecipeInCookBook(UID, NumRecords);
                return dr;
            }
        }

        public ExtendedCollection<CookBook> GetUserRecipeInCookBook()
        {
            ExtendedCollection<CookBook> list = new ExtendedCollection<CookBook>();

            string Key = "MyCookBook_SideMenu_" + UID;

            if (Caching.Cache[Key] != null)
            {
                list = (ExtendedCollection<CookBook>)Caching.Cache[Key];
            }
            else
            {
                list = ProviderDataFieldsCookBook.GetDataFields(GetData);        
            }

            return list;
        }
    }
}
