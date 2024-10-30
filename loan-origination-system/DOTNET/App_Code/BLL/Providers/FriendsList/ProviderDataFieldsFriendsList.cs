#region XD World Recipe V 3
// FileName: ProviderDataFieldsFriendsList.cs
// Author: Dexter Zafra
// Date Created: 3/29/2009
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
    /// Object in this class provides data fields list for Friends List.
    /// </summary>
    public static class ProviderDataFieldsFriendsList
    {
        public static ExtendedCollection<FriendsList> GetDataFields(IDataReader dr)
        {
            ExtendedCollection<FriendsList> list = new ExtendedCollection<FriendsList>();

            while (dr.Read())
            {
                FriendsList item = new FriendsList();

                if (dr["ID"] != DBNull.Value)
                {
                    item.ID = (int)dr["ID"];
                }
                if (dr["FriendID"] != DBNull.Value)
                {
                    item.FriendID = (int)dr["FriendID"];
                }
                if (dr["UserName"] != DBNull.Value)
                {
                    item.Username = (string)dr["UserName"];
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
