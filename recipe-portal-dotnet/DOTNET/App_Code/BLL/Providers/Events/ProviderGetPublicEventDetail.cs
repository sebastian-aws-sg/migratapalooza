#region XD World Recipe V 3
// FileName: ProviderGetPublicEventDetail.cs
// Author: Dexter Zafra
// Date Created: 4/20/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Model;

namespace XDRecipe.BL.Providers.Events
{
    /// <summary>
    /// object in this class manages publis events details
    /// </summary>
    public class ProviderGetPublicEventDetail : EventsCalendar
    {
        public ProviderGetPublicEventDetail()
        {
        }

        public void FillUp(int ID)
        {
            this._EventsID = ID;

            IDataReader dr = Blogic.ActionProcedureDataProvider.GetPublicEventDetails(ID);

            while (dr.Read())
            {
                if (dr["EVENT_TITLE"] != DBNull.Value)
                {
                    this._EventTitle = (string)dr["EVENT_TITLE"];
                }
                if (dr["EVENT_DETAILS"] != DBNull.Value)
                {
                    this._EventDetails = (string)dr["EVENT_DETAILS"];
                }
                if (dr["CATEGORY"] != DBNull.Value)
                {
                    this._EventType = (string)dr["CATEGORY"];
                }
                if (dr["START_DATE"] != DBNull.Value)
                {
                    this._StartDate = (DateTime)(dr["START_DATE"]);
                }
                if (dr["END_DATE"] != DBNull.Value)
                {
                    this._EndDate = (DateTime)(dr["END_DATE"]);
                }
                if (dr["UID"] != DBNull.Value)
                {
                    this._UID = (int)dr["UID"];
                }
                if (dr["UserName"] != DBNull.Value)
                {
                    this._UserName = (string)dr["UserName"];
                }
                if (dr["APPMEETING_STARTTIME"] != DBNull.Value)
                {
                    this._AppMeetingStartTime = (string)dr["APPMEETING_STARTTIME"];
                }
                if (dr["APPMEETING_ENDTIME"] != DBNull.Value)
                {
                    this._AppMeetingEndTime = (string)dr["APPMEETING_ENDTIME"];
                }
            }

            dr.Close();
            dr = null;
        }
    }
}
