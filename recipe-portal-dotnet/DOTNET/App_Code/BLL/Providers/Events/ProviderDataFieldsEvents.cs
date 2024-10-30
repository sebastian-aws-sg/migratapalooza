#region XD World Recipe V 3
// FileName: ProviderDataFieldsEvents.cs
// Author: Dexter Zafra
// Date Created: 4/22/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Web;
using System.Data;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Model;

namespace XDRecipe.BL.Providers.Events
{
    /// <summary>
    /// object in this class manages upcoming and past events data fields.
    /// </summary>
    public static class ProviderDataFieldsEvents
    {
        /// <summary>
        /// Return collection
        /// </summary>
        public static ExtendedCollection<EventsCalendar> GetDataFields(IDataReader dr)
        {
            ExtendedCollection<EventsCalendar> list = new ExtendedCollection<EventsCalendar>();

            while (dr.Read())
            {
                EventsCalendar item = new EventsCalendar();

                if (dr["EVENT_ID"] != DBNull.Value)
                {
                    item.EventsID = (int)dr["EVENT_ID"];
                }
                if (dr["EVENT_TITLE"] != DBNull.Value)
                {
                    item.EventTitle = (string)dr["EVENT_TITLE"];
                }
                if (dr["CATEGORY"] != DBNull.Value)
                {
                    item.EventType = (string)dr["CATEGORY"];
                }
                if (dr["APPMEETING_STARTTIME"] != DBNull.Value)
                {
                    item.AppMeetingStartTime = (string)dr["APPMEETING_STARTTIME"];
                }
                if (dr["APPMEETING_ENDTIME"] != DBNull.Value)
                {
                    item.AppMeetingEndTime = (string)dr["APPMEETING_ENDTIME"];
                }
                if (dr["START_DATE"] != DBNull.Value)
                {
                    item.StartDate = (DateTime)dr["START_DATE"];
                }

                list.Add(item);
            }

            dr.Close();

            return list;
        }
    }
}

