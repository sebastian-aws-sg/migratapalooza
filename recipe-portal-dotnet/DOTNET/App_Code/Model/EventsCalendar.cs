#region XD World Recipe V 3
// FileName: EventsCalendar.cs
// Author: Dexter Zafra
// Date Created: 4/20/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;

namespace XDRecipe.Model
{
    /// <summary>
    /// This class manages events calendar object
    /// </summary>
    public class EventsCalendar
    {
        public EventsCalendar()
        {
        }

        protected int _EventsID;
        protected DateTime _CreatedDate;
        protected DateTime _StartDate;
        protected DateTime _EndDate;
        protected string _EventTitle;
        protected string _AppMeetingStartTime;
        protected string _AppMeetingEndTime;
        protected string _EventType;
        protected string _EventDetails;
        protected int _UID;
        protected string _UserName;
        protected int _Private;
        protected int _RecordCount;

        protected string _Monday;
        protected string _Tuesday;
        protected string _Wednesday;
        protected string _Thursday;
        protected string _Friday;
        protected string _Saturday;
        protected string _Sunday;
        protected DateTime _WeekBegindate;
        protected DateTime _WeekEnddate;


        public int EventsID
        {
            get { return _EventsID; }
            set { _EventsID = value; }
        }

        public DateTime CreatedDate
        {
            get { return _CreatedDate; }
            set { _CreatedDate = value; }
        }

        public DateTime StartDate
        {
            get { return _StartDate; }
            set { _StartDate = value; }
        }

        public DateTime EndDate
        {
            get { return _EndDate; }
            set { _EndDate = value; }
        }

        public string EventTitle
        {
            get { return _EventTitle; }
            set { _EventTitle = value; }
        }

        public string AppMeetingStartTime
        {
            get { return _AppMeetingStartTime; }
            set { _AppMeetingStartTime = value; }
        }

        public string AppMeetingEndTime
        {
            get { return _AppMeetingEndTime; }
            set { _AppMeetingEndTime = value; }
        }

        public string EventType
        {
            get { return _EventType; }
            set { _EventType = value; }
        }

        public string EventDetails
        {
            get { return _EventDetails; }
            set { _EventDetails = value; }
        }

        public int UID
        {
            get { return _UID; }
            set { _UID = value; }
        }

        public string UserName
        {
            get { return _UserName; }
            set { _UserName = value; }
        }

        public int Private
        {
            get { return _Private; }
            set { _Private = value; }
        }

        public int RecordCount
        {
            get { return _RecordCount; }
            set { _RecordCount = value; }
        }

        public string Monday
        {
            get { return _Monday; }
            set { _Monday = value; }
        }
        public string Tuesday
        {
            get { return _Tuesday; }
            set { _Tuesday = value; }
        }
        public string Wednesday
        {
            get { return _Wednesday; }
            set { _Wednesday = value; }
        }
        public string Thursday
        {
            get { return _Thursday; }
            set { _Thursday = value; }
        }
        public string Friday
        {
            get { return _Friday; }
            set { _Friday = value; }
        }
        public string Saturday
        {
            get { return _Saturday; }
            set { _Saturday = value; }
        }
        public string Sunday
        {
            get { return _Sunday; }
            set { _Sunday = value; }
        }
        public DateTime WeekBegindate
        {
            get { return _WeekBegindate; }
            set { _WeekBegindate = value; }
        }
        public DateTime WeekEnddate
        {
            get { return _WeekEnddate; }
            set { _WeekEnddate = value; }
        }
    }
}
