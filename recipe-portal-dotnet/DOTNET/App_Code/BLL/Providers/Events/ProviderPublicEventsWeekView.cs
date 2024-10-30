#region XD World Recipe V 3
// FileName: ProviderPublicEventsWeekView.cs
// Author: Dexter Zafra
// Date Created: 4/21/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Model;

namespace XDRecipe.BL.Providers.Events
{
    public sealed class ProviderPublicEventsWeekView
    {
        private string _EventType;
        private DateTime _StartDate;

        private DateTime StartDate
        {
            get { return _StartDate; }
            set { _StartDate = value; }
        }

        private string EventType
        {
            get { return _EventType; }
            set { _EventType = value; }
        }

        public ProviderPublicEventsWeekView()
        {
        }

        public ProviderPublicEventsWeekView(DateTime StartDate, string EventType)
        {
            this._StartDate = StartDate;
            this._EventType = EventType;
        }

        private IDataReader GetData
        {
            get
            {
                IDataReader dr = Blogic.ActionProcedureDataProvider.PublicEventWeekView(StartDate, EventType);
                return dr;
            }
        }

        public ExtendedCollection<EventsCalendar> EventsWeekView()
        {
            return ProviderDataFieldsEvents.GetDataFields(GetData);
        }
    }
}

