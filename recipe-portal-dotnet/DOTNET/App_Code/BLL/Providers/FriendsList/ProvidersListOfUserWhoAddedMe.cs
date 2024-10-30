#region XD World Recipe V 3
// FileName: ProvidersListOfUserWhoAddedMe.cs
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
    /// Object in this class returns all users who added me in Friends List
    /// </summary>
    public class ProvidersListOfUserWhoAddedMe : FriendsList
    {
        public ProvidersListOfUserWhoAddedMe(int UserID)
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
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetUsersWhoAddMeInFriendsList(UID);
                return dr;
            }
        }

        public ExtendedCollection<FriendsList> GetUsers()
        {
            ExtendedCollection<FriendsList> list = new ExtendedCollection<FriendsList>();

            IDataReader dr = GetData;

            while (dr.Read())
            {
                FriendsList item = new FriendsList();

                if (dr["UID"] != DBNull.Value)
                {
                    item.FriendID = (int)dr["UID"];
                }
                if (dr["UserName"] != DBNull.Value)
                {
                    item.Username = (string)dr["UserName"];
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
