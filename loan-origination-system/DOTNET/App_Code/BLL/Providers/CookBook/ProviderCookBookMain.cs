#region XD World Recipe V 3
// FileName: UsersCookBookMain.cs
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
    /// Object in this class provides data to user Cookbook Main Page
    /// </summary>
    public sealed class UsersCookBookMain : CookBook
    {
        public UsersCookBookMain(int UserID)
        {
            this._UID = UserID;

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
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetUsersCookBook(UID);
                return dr;
            }
        }

        public ExtendedCollection<CookBook> GetUserRecipeInCookBookMain()
        {
            return ProviderDataFieldsCookBook.GetDataFields(GetData);  
        }
    }
}