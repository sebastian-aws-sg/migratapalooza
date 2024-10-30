#region XD World Recipe V 3
// FileName: ProviderDataFieldsComment.cs
// Author: Dexter Zafra
// Date Created: 3/26/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Web;
using System.Data;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Model;

namespace XDRecipe.BL.Providers.Comments
{
    /// <summary>
    /// object in this class manages comments data fields.
    /// </summary>
    public static class ProviderDataFieldsComment
    {
        /// <summary>
        /// Return collection
        /// </summary>
        public static ExtendedCollection<Comment> GetDataFields(IDataReader dr)
        {
            ExtendedCollection<Comment> list = new ExtendedCollection<Comment>();

            while (dr.Read())
            {
                Comment item = new Comment();

                if (dr["Author"] != DBNull.Value)
                {
                    item.Author = (string)dr["Author"];
                }
                if (dr["Email"] != DBNull.Value)
                {
                    item.Email = (string)dr["Email"];
                }
                if (dr["Date"] != DBNull.Value)
                {
                    item.Date = (DateTime)(dr["Date"]);
                }
                if (dr["Comments"] != DBNull.Value)
                {
                    item.Comments = (string)dr["Comments"];
                }
                if (dr["UID"] != DBNull.Value)
                {
                    item.UID = (int)dr["UID"];
                }

                list.Add(item);
            }

            dr.Close();

            return list;
        }
    }
}
