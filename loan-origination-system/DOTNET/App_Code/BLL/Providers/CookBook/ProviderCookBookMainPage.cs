#region XD World Recipe V 3
// FileName: ProviderCookBookMainPage.cs
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
    public sealed class ProviderCookBookMainPage : CookBook
    {
        private int _OrderBy;
        private int _SortBy;
        private int _PageIndex;
        private int _PageSize;

        private int OrderBy
        {
            get { return _OrderBy; }
            set { _OrderBy = value; }
        }
        private int SortBy
        {
            get { return _SortBy; }
            set { _SortBy = value; }
        }
        public int PageIndex
        {
            get { return _PageIndex; }
            set { _PageIndex = value; }
        }
        public int PageSize
        {
            get { return _PageSize; }
            set { _PageSize = value; }
        }

        public ProviderCookBookMainPage(int UserID, int OrderBy, int SortBy, int PageIndex, int PageSize)
        {
            this._UID = UserID;
            this._OrderBy = OrderBy;
            this._SortBy = SortBy;
            this._PageIndex = PageIndex;
            this._PageSize = PageSize;

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
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetUsersCookBookMain(UID, OrderBy, SortBy, PageIndex, PageSize);
                return dr;
            }
        }

        public ExtendedCollection<CookBook> GetUserRecipeInCookBookMain()
        {
            return ProviderDataFieldsCookBook.GetDataFields(GetData);  
        }
    }
}