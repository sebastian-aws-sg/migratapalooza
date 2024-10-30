#region XD World Recipe V 3
// FileName: ProviderUnApprovedFriends.cs
// Author: Dexter Zafra
// Date Created: 2/29/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using XDRecipe.Model;
using XDRecipe.Common;

namespace XDRecipe.BL.Providers.FriendList
{
    /// <summary>
    /// Object in this class provides data for Friends List.
    /// </summary>
    public class ProviderUnApprovedFriends : FriendsList
    {

        public ProviderUnApprovedFriends(int UserID)
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
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetNewFriendsWaitingForApproval(UID);
                return dr;
            }
        }

        public ExtendedCollection<FriendsList> GetFriendsList()
        {
            ExtendedCollection<FriendsList> list = new ExtendedCollection<FriendsList>();

            IDataReader dr = GetData;

            while (dr.Read())
            {
                FriendsList item = new FriendsList();

                if (dr["ID"] != DBNull.Value)
                {
                    item.ID = (int)dr["ID"];
                }
                if (dr["UID"] != DBNull.Value)
                {
                    item.UID = (int)dr["UID"];
                }
                if (dr["FriendID"] != DBNull.Value)
                {
                    item.FriendID = (int)dr["FriendID"];
                }
                if (dr["UserName"] != DBNull.Value)
                {
                    item.Username = (string)dr["UserName"];
                }
                if (dr["Email"] != DBNull.Value)
                {
                    item.Email = (string)dr["Email"];
                }
                if (dr["Date"] != DBNull.Value)
                {
                    item.Date = (DateTime)(dr["Date"]);
                }
                if (dr["FirstName"] != DBNull.Value)
                {
                    item.FirstName = (string)dr["FirstName"];
                }
                if (dr["LastName"] != DBNull.Value)
                {
                    item.LastName = (string)dr["LastName"];
                }
                if (dr["Country"] != DBNull.Value)
                {
                    item.Country = (string)dr["Country"];
                }
                if (dr["Photo"] != DBNull.Value)
                {
                    item.Photo = (string)dr["Photo"];
                }
                if (dr["Hits"] != DBNull.Value)
                {
                    item.Hits = (int)dr["Hits"];
                }
                if (dr["LastVisit"] != DBNull.Value)
                {
                    item.LastVisit = (DateTime)(dr["LastVisit"]);
                }
                if (dr["DateJoined"] != DBNull.Value)
                {
                    item.DateJoined = (DateTime)(dr["DateJoined"]);
                }

                list.Add(item);
            }

            dr.Close();

            return list;
        }
    }
}
