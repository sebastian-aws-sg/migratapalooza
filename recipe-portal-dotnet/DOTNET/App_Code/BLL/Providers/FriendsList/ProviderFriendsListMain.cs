#region XD World Recipe V 3
// FileName: ProviderFriendsListMain.cs
// Author: Dexter Zafra
// Date Created: 5/29/2008
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using XDRecipe.Model;
using XDRecipe.Common;

namespace XDRecipe.BL.Providers.FriendList
{
    /// <summary>
    /// Object in this class provides data for Friends List main page.
    /// </summary>
    public class ProviderFriendsListMain : FriendsList
    {

        public ProviderFriendsListMain(int UserID)
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
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetUsersFriendsListMain(UID);
                return dr;
            }
        }

        public ExtendedCollection<FriendsList> GetFriendsList()
        {
            return ProviderDataFieldsFriendsList.GetDataFields(GetData);
        }
    }
}