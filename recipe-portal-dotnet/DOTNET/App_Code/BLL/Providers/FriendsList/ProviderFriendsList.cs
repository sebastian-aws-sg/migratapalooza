#region XD World Recipe V 3
// FileName: ProviderFriendsList.cs
// Author: Dexter Zafra
// Date Created: 5/29/2008
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using XDRecipe.Model;
using XDRecipe.Common;

namespace XDRecipe.BL.Providers.FriendList
{
    /// <summary>
    /// Object in this class provides data for Friends List in profile page.
    /// </summary>
    public class ProviderFriendsList : FriendsList
    {

        public ProviderFriendsList(int UserID, int NumRecords)
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
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetUsersFriendsList(UID, NumRecords);
                return dr;
            }
        }

        public ExtendedCollection<FriendsList> GetFriendsList()
        {
            return ProviderDataFieldsFriendsList.GetDataFields(GetData);
        }
    }
}
