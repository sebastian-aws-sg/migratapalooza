#region XD World Recipe V 3
// FileName: ProviderGetPast30DaysPublicEvents.cs
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
    /// object in this returns collection of past 30 days public events
    /// </summary>
    public static class ProviderGetPast30DaysPublicEvents
    {
        private static int _TotalCount;
        private static string _EventType;

        private static int TotalCount
        {
            get { return _TotalCount; }
            set { _TotalCount = value; }
        }

        private static string EventType
        {
            get { return _EventType; }
            set { _EventType = value; }
        }

        public static void Param(string EventType)
        {
            _EventType = EventType;
        }

        private static IDataReader GetData
        {
            get
            {
                IDataReader dr = Blogic.ActionProcedureDataProvider.GetPastPublicEvents(EventType);
                return dr;
            }
        }

        /// <summary>
        /// Return events
        /// </summary>
        public static ExtendedCollection<EventsCalendar> Past30DaysPublicEvents()
        {
            return ProviderDataFieldsEvents.GetDataFields(GetData);
        }

        public static int RecordCount
        {
            get
            {
                IDataReader dr = GetData;
                while (dr.Read())
                {
                    _TotalCount = (int)dr["RCount"];
                }
                dr.Close();
                return TotalCount;
            }
        }
    }
}
