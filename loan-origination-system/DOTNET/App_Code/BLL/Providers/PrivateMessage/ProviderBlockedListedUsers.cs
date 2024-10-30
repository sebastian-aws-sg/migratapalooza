#region XD World Recipe V 3
// FileName: ProviderBlockedListedUsers.cs
// Author: Dexter Zafra
// Date Created: 2/29/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using XDRecipe.Model;
using XDRecipe.Common;

namespace XDRecipe.BL.Providers.PrivateMessages
{
    /// <summary>
    /// Object in this class returns PM blocklisted users.
    /// </summary>
    public static class ProviderBlockedListedUsers
    {
        private static int _UID;

        private static int UID
        {
            get { return _UID; }
            set { _UID = value; }
        }

        public static void Param(int UserID)
        {
            _UID = UserID;
        }

        /// <summary>
        /// Return Data
        /// </summary>
        /// <returns></returns>
        private static IDataReader GetData
        {
            get
            {
                IDataReader dr = Blogic.ActionProcedureDataProvider.ShowAllPMBlockedUsersByUserID(UID);
                return dr;
            }
        }

        public static ExtendedCollection<PrivateMessage> GetBlockListedUsers()
        {
            ExtendedCollection<PrivateMessage> list = new ExtendedCollection<PrivateMessage>();

            IDataReader dr = GetData;

            while (dr.Read())
            {
                PrivateMessage item = new PrivateMessage();

                if (dr["ID"] != DBNull.Value)
                {
                    item.ID = (int)dr["ID"];
                }
                if (dr["UID"] != DBNull.Value)
                {
                    item.SenderUserID = (int)dr["UID"];
                }
                if (dr["UserName"] != DBNull.Value)
                {
                    item.SenderUserName = (string)dr["UserName"];
                }
                if (dr["Date"] != DBNull.Value)
                {
                    item.DateCreated = (DateTime)(dr["Date"]);
                }

                list.Add(item);
            }

            dr.Close();

            return list;
        }
    }
}

