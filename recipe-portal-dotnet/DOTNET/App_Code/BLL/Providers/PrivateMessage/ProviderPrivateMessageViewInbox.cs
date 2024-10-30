#region XD World Recipe V 3
// FileName: ProviderPrivateMessageViewInbox.cs
// Author: Dexter Zafra
// Date Created: 3/8/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Model;

namespace XDRecipe.BL.Providers.PrivateMessages
{
    /// <summary>
    /// Object in this class provides data for Private Message View Inbox.
    /// </summary>
    public sealed class ProviderPrivateMessageViewInbox : BasePMObj
    {
        private int _OrderBy;
        private int _SortBy;
        private int _Index;
        private int _PageSize;
        private string _Folder;

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
        private int Index
        {
            get { return _Index; }
            set { _Index = value; }
        }
        private int PageSize
        {
            get { return _PageSize; }
            set { _PageSize = value; }
        }
        private string Folder
        {
            get { return _Folder; }
            set { _Folder = value; }
        }

        public ProviderPrivateMessageViewInbox()
        {
        }

        public void Param(string Folder, int UserID, int OrderBy, int SortBy, int PageIndex, int PageSize)
        {
            this._SenderUserID = UserID;
            this._OrderBy = OrderBy;
            this._SortBy = SortBy;
            this._Index = PageIndex;
            this._PageSize = PageSize;
            this._Folder = Folder;

            IDataReader dr = GetData;

            while (dr.Read())
            {
                this._TotalCount = (int)dr["TotalCount"];
                this._UnreadCount = (int)dr["UnreadCount"];
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
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetUsersPrivateMessages(Folder, SenderUserID, OrderBy, SortBy, Index, PageSize);
                return dr;
            }
        }

        public ExtendedCollection<PrivateMessage> GetMessages()
        {
            ExtendedCollection<PrivateMessage> list = new ExtendedCollection<PrivateMessage>();

            IDataReader dr = GetData;

            while (dr.Read())
            {
                PrivateMessage item = new PrivateMessage();

                if (dr["M_ID"] != DBNull.Value)
                {
                    item.ID = (int)dr["M_ID"];
                }
                if (dr["M_Subject"] != DBNull.Value)
                {
                    item.Subject = (string)dr["M_Subject"];
                }
                if (dr["M_From"] != DBNull.Value)
                {
                    item.SenderUserID = (int)dr["M_From"];
                }
                if (dr["M_To"] != DBNull.Value)
                {
                    item.RecipientUserID = (int)dr["M_To"];
                }
                if (dr["UserName"] != DBNull.Value)
                {
                    item.SenderUserName = (string)dr["UserName"];
                }
                if (dr["M_Sent"] != DBNull.Value)
                {
                    item.DateSent = (DateTime)(dr["M_Sent"]);
                }
                if (dr["M_Read"] != DBNull.Value)
                {
                    item.Read = (int)(dr["M_Read"]);
                }
                if (dr["PM_Message"] != DBNull.Value)
                {
                    item.Message = (string)(dr["PM_Message"]);
                }

                list.Add(item);
            }

            dr.Close();

            return list;
        }
    }
}
