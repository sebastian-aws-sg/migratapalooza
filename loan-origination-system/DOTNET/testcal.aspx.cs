using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Globalization;
using System.Web;
using System.Text;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using System.Text.RegularExpressions;
using XDRecipe.Common;
using XDRecipe.Common.Utilities;
using XDRecipe.BL.Providers.Events;
using XDRecipe.Model;
using XDRecipe.BL;
using XDRecipe.UI;

public partial class testcal : BasePage
{
    public DateTime week1;
    public DateTime week2;
    public DateTime week3;
    public DateTime week4;
    public DateTime week5;
    public DateTime week6;

    private int iMonth;
    private int iYear;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            PopulateDDLNumWeek();
            ShowBeginAndEndDate();
            InitializeValue();

            iMonth = 5;
            iYear = 2009;
        }
    }

    private DateTime GetWeekStartDate(int year, int weeknumber)
    {
        return Utility.GetWeekStartDate(year, weeknumber);
    }

    private DateTime GetWeekEndDate(int year, int weeknumber)
    {
        return Utility.GetWeekEndDate(year, weeknumber);
    }

    private int GetWeekNumber(DateTime date)
    {
        return Utility.GetWeekNumber(date);
    }

    private void ShowBeginAndEndDate()
    {
        lbWeekCounter.Text = ddlweeknumber.SelectedItem.Value;
        int Year = int.Parse(ddlyear.SelectedItem.Value);
        int WeekNumber = int.Parse(lbWeekCounter.Text);
        int CurrentWeekNumber = GetWeekNumber(DateTime.Now.Date);

        ValidateNumWeek(WeekNumber);

        DateTime WeekStartDate = GetWeekStartDate(Year, WeekNumber);
        DateTime WeekEndDate = GetWeekEndDate(Year, WeekNumber);

        lblbeginenddate.Text = "<span style='color: #000; font-size: 12px;'><b>" + Utility.GetMonthNameOrAbbrev(WeekEndDate.Month, true) + "</b></span> - " + WeekStartDate.ToShortDateString() + " - " + WeekEndDate.ToShortDateString();
        GetDay(WeekEndDate);

        if (CurrentWeekNumber != WeekNumber)
        {
            btnCurrentWeek.Enabled = true;
            btnCurrentWeek.Attributes.Add("onmouseover", "Tip('Back to current week " + GetWeekNumber(DateTime.Now.Date) + " - " + Utility.GetMonthNameOrAbbrev(DateTime.Now.Date.AddDays(-7).Month, true) + " - " + DateTime.Now.Date.AddDays(-6).ToShortDateString() + " - " + DateTime.Now.Date.ToShortDateString() + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            btnCurrentWeek.Attributes.Add("onmouseout", "UnTip()");
        }
        else
        {
            btnCurrentWeek.Enabled = false;
        }

        btnnxtday.Attributes.Add("onmouseover", "Tip('Week " + (WeekNumber + 1) + " - " + Utility.GetMonthNameOrAbbrev(WeekEndDate.AddDays(1).Month, true) + " - " + WeekEndDate.AddDays(1).ToShortDateString() + " - " + WeekEndDate.AddDays(7).ToShortDateString() + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnnxtday.Attributes.Add("onmouseout", "UnTip()");
        btnprevday.Attributes.Add("onmouseover", "Tip('Week " + (WeekNumber - 1) + " - " + Utility.GetMonthNameOrAbbrev(WeekEndDate.AddDays(-7).Month, true) + " - " + WeekEndDate.AddDays(-7).ToShortDateString() + " - " + WeekEndDate.AddDays(-1).ToShortDateString() + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnprevday.Attributes.Add("onmouseout", "UnTip()");
    }

    private void InitializeValue()
    {
        lblEventType.Text = " - " + ddleventtype.SelectedItem.Value.ToString() + " - ";
        lbWeekCounter.Text = ddlweeknumber.SelectedItem.Value;
        int Year = int.Parse(ddlyear.SelectedItem.Value);
        int WeekNumber = int.Parse(lbWeekCounter.Text);
        ValidateNumWeek(WeekNumber);
    }

    private void GetDay(DateTime sdate)
    {
        DateTime dt = sdate;
        int difference = -((int)dt.DayOfWeek);
        week1 = dt.AddDays(difference).Date;
        week2 = week1.AddDays(7).Date;
        week3 = week2.AddDays(7).Date;
        week4 = week3.AddDays(7).Date;
        week5 = week4.AddDays(7).Date;
        week6 = week5.AddDays(7).Date;
        DateTime end = week1.AddDays(7).Date.AddMilliseconds(-1);
    }

    public void btnSubmit_Click(object sender, EventArgs e)
    {
        ShowBeginAndEndDate();
        InitializeValue();
    }

    public void NextWeek(object sender, EventArgs e)
    {
        int NxtWeek = int.Parse(ddlweeknumber.SelectedItem.Value) + 1;
        ddlweeknumber.SelectedItem.Value = NxtWeek.ToString();
        lbWeekCounter.Text = NxtWeek.ToString();
        ShowBeginAndEndDate();
        InitializeValue();
    }

    public void PreviousWeek(object sender, EventArgs e)
    {
        int PrvWeek = int.Parse(ddlweeknumber.SelectedItem.Value) - 1;
        ddlweeknumber.SelectedItem.Value = PrvWeek.ToString();
        lbWeekCounter.Text = PrvWeek.ToString();
        ShowBeginAndEndDate();
        InitializeValue();
    }

    public void ResetToCurrentWeek(object sender, EventArgs e)
    {
        Response.Redirect("eventweekview.aspx");
    }

    private void ValidateNumWeek(int WeekNumber)
    {
        if (WeekNumber == 0)
        {
            Response.Redirect("eventweekview.aspx");
        }
        else if (WeekNumber > 53)
        {
            Response.Redirect("eventweekview.aspx");
        }
    }

    private void PopulateDDLNumWeek()
    {
        for (int i = 0; i < 54; i++)
        {
            if (i == GetWeekNumber(DateTime.Now.Date))
                ddlweeknumber.Items.Insert(0, new ListItem("Week " + i, i.ToString()));

            ddlweeknumber.Items.Insert(i, new ListItem("Week " + i, i.ToString()));
        }
    }

    public string DisplayAppMeetingTime(string startTime, string endTime)
    {
        StringBuilder mySB = new StringBuilder();

        if (!string.IsNullOrEmpty(startTime) && !string.IsNullOrEmpty(endTime))
        {
            mySB.Append("<span style='font-family: verdana; font-size: 9px; color: #868686;'>");
            mySB.Append("<br>" + startTime + " - " + endTime);
        }
        else if (!string.IsNullOrEmpty(startTime) && string.IsNullOrEmpty(endTime))
        {
            mySB.Append("<br>" + startTime);
            mySB.Append("</span>");
        }

        return mySB.ToString();
    }


    public string BuildCalendar
    {
        get
        {
            string EventType = ddleventtype.SelectedItem.Value;

            DateTime VisibleDate = DateTime.Now.Date;
            DateTime CalMonth = new DateTime(iYear, iMonth, 1);

            string[] days = new string[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

            int numDays = DateTime.DaysInMonth(iYear, iMonth);
            int offset = (Int32)Enum.Parse(typeof(DayOfWeek), CalMonth.DayOfWeek.ToString());
            int position = 0;

            StringBuilder HTMLOutput = new StringBuilder();

            DateTime PrevMonth = CalMonth.AddDays(-(offset + 1));
            DateTime NextMonth = CalMonth.AddMonths(1).AddDays(-1).Date;

            HTMLOutput.Append("<table border='0' cellpadding='0' cellspacing='0' style='border-left: 1px solid #E1EDFF; border-top: 1px solid #E1EDFF;'>");
            HTMLOutput.Append("<tr>");
            HTMLOutput.Append("<td colspan='7' align='center' style='border-right: 1px solid #E1EDFF; background-color: #84B3FF; height: 32px;'><b>" + Utility.GetMonthNameOrAbbrev(CalMonth.Month, true) + " " + CalMonth.Year + "</b></td>");
            HTMLOutput.Append("</tr>");

            foreach (string day in days)
            {
                HTMLOutput.Append("<td width='140' style='background-color: #83B6E0;'>&nbsp;<span class='content12' style='color: #fff; font-weight: bold;'>" + day + "</span></td>");
            }

            HTMLOutput.Append("</tr>");

            for (int i = 1 - offset; i <= numDays; i++)
            {
                if (position % 7 == 0)
                {
                    HTMLOutput.Append("<tr>");
                }
                if (i < 1)
                {
                    HTMLOutput.Append("<td align='center'><div style='text-align: right; padding-right: 3px; border-right: 1px Solid #E1EDFF; background-color: #ECF3FF;'>" + PrevMonth.AddDays(i + offset).Day.ToString() + "</div>");
                    HTMLOutput.Append("<div style='height: 100px; background-color: #f7f7f7; border-right: 1px Solid #E1EDFF; border-bottom: 1px Solid #E1EDFF;''>");
                    HTMLOutput.Append(GetEventDataTable(PrevMonth.AddDays(i + offset).Date, EventType));
                    HTMLOutput.Append("</div>");
                    HTMLOutput.Append("</td>");
                }
                else if (i == VisibleDate.Day && iYear == VisibleDate.Year && iMonth == VisibleDate.Month)
                {
                    HTMLOutput.Append("<td align='center'><div style='text-align: right; padding-right: 3px; border-right: 1px Solid #E1EDFF; background-color: #ECF3FF;'><b>" + i.ToString() + "</b></div>");
                    HTMLOutput.Append("<div style='height: 100px; border-right: 1px Solid #E1EDFF; border-bottom: 1px Solid #E1EDFF;'#E1EDFF;'>");
                    HTMLOutput.Append(GetEventDataTable(DateTime.Now.Date, EventType));
                    HTMLOutput.Append("</div>");
                    HTMLOutput.Append("</td>");
                }
                else
                {
                    HTMLOutput.Append("<td align='center'><div style='text-align: right; padding-right: 3px; border-right: 1px Solid #E1EDFF; background-color: #ECF3FF;'>" + i.ToString() + "</div>");
                    HTMLOutput.Append("<div style='height: 100px; border-right: 1px Solid #E1EDFF; border-bottom: 1px Solid #E1EDFF;''>");
                    HTMLOutput.Append(GetEventDataTable(PrevMonth.AddDays(i + offset).Date, EventType));
                    HTMLOutput.Append("</div>");
                    HTMLOutput.Append("</td>");
                }

                position += 1;

                if (position % 7 == 0)
                    HTMLOutput.Append("</tr>");
            }

            if (position % 7 != 0)
            {
                for (int j = 1; j <= 7 - (position % 7); j++)
                {
                    HTMLOutput.Append("<td align='center'><div style='text-align: right; padding-right: 3px; border-right: 1px Solid #E1EDFF; background-color: #ECF3FF;'>" + NextMonth.AddDays(j).Day.ToString() + "</div>");
                    HTMLOutput.Append("<div style='height: 100px; background-color: #f7f7f7; border-right: 1px Solid #E1EDFF; border-bottom: 1px Solid #E1EDFF;'>");
                    HTMLOutput.Append(GetEventDataTable(NextMonth.AddDays(j).Date, EventType));
                    HTMLOutput.Append("</div>");
                    HTMLOutput.Append("</td>");
                }

                HTMLOutput.Append("</tr>");
            }

            HTMLOutput.Append("</table>");

            return HTMLOutput.ToString();
        }
    }

    public string BuildTable
    {
        get
        {
            StringBuilder HTMLOutPut = new StringBuilder("");

            DateTime startDate = new DateTime(DateTime.Now.Year, DateTime.Now.Month, 1).AddDays(-5);
            DateTime endDate = new DateTime(DateTime.Now.Year, startDate.Date.AddMonths(1).Month, 1).AddDays(5);

            string EventType = ddleventtype.SelectedItem.Value;

            HTMLOutPut.Append(startDate.ToShortDateString() + " - " + endDate.ToShortDateString());

            for (int j = 0; j < 6; j++)
            {
                HTMLOutPut.Append("<tr>");
                for (int i = 0; i < 7; i++)
                {
                    HTMLOutPut.Append("<td style='border-right: 1px Solid #E1EDFF;' valign='top'><div style='text-align: right; padding-right: 3px; border-right: 1px Solid #E1EDFF; background-color: #ECF3FF;'><span class='content2'>" + startDate.AddDays(i).Day + "</span></div>");
                    HTMLOutPut.Append("<div style='padding: 3px; height: 90px;'>");
                    HTMLOutPut.Append("</div></td>");
                }
                HTMLOutPut.Append("</tr>");
            }

            return HTMLOutPut.ToString();
        }
    }

    //Option 1 - Object DataSource Using IDataReader
    private string GetEventDataReader(DateTime StartDate, string EventType)
    {
        StringBuilder DataOutPut = new StringBuilder("");

        ProviderPublicEventsWeekView Event = new ProviderPublicEventsWeekView(StartDate, EventType);

        string imgsrc = string.Empty;
        string EventURL = string.Empty;
        string strDayOfWeek = string.Empty;
        string ToolTipMouseOver = string.Empty;
        string ToolTipMouseOut = string.Empty;

        foreach (EventsCalendar ev in Event.EventsWeekView())
        {
            imgsrc = "images/" + ev.EventType + ".gif";
            strDayOfWeek = ev.StartDate.DayOfWeek.ToString();
            ToolTipMouseOver = "\"Tip('<b>Type:</b> " + ev.EventType + " on " + CustomDateFormat(StartDate) + " " + ev.AppMeetingStartTime + " - " + ev.AppMeetingEndTime + "<br> View detail.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')\"";
            ToolTipMouseOut = "\"UnTip()\"";

            //For Sunday and Monday we float the event detail pop-up modal to the right.
            if (strDayOfWeek == "Sunday" || strDayOfWeek == "Monday")
                EventURL = "\"showEvent(" + ev.EventsID + ",'top','right')\"";
            else
                EventURL = "\"showEvent(" + ev.EventsID + ",'top','left')\"";

            DataOutPut.Append("<div id='eventID" + ev.EventsID + "'" + " class='eventItemDiv' style='margin: 3px 3px 3px 3px; border: 1px solid #88B5FF; padding: 2px 3px 2px 3px;'><img src=" + imgsrc + ">&nbsp;<a class='content2' href='javascript:void(0)'" + " onclick=" + EventURL + "onmouseover=" + ToolTipMouseOver + " " + "onmouseout=" + ToolTipMouseOut + ">" + ev.EventTitle + "</a>" + DisplayAppMeetingTime(ev.AppMeetingStartTime, ev.AppMeetingEndTime) + "</div>");
        }

        return DataOutPut.ToString();

        Event = null;
    }

    //DatatTable DataSource
    private DataTable dt(string EventType)
    {
        DateTime startDate = new DateTime(DateTime.Now.Year, DateTime.Now.Month, 1).AddDays(-5);
        DateTime endDate = new DateTime(DateTime.Now.Year, startDate.Date.AddMonths(1).Month, 1).AddDays(5);

        return Blogic.ActionProcedureDataProvider.GetAllPublicEventsCalendar(startDate, endDate, EventType);
    }

    //Option 2 - DataTable - DataView
    private string GetEventDataTable(DateTime StartDate, string EventType)
    {
        StringBuilder DataOutPut = new StringBuilder("");

        DataView dv = new DataView(dt(EventType));

        dv.RowFilter = string.Format("{0} = #{1}# and {0} < #{2}#", "START_DATE",
            StartDate.Date.ToString("MM/dd/yyyy"),
            StartDate.Date.AddDays(1).ToString("MM/dd/yyyy"));

        string imgsrc = string.Empty;
        string EventURL = string.Empty;
        string strDayOfWeek = string.Empty;
        string ToolTipMouseOver = string.Empty;
        string ToolTipMouseOut = string.Empty;

        int EventsID;
        string EventTitle;
        string EventTypeImage;
        string AppMeetingStartTime;
        string AppMeetingEndTime;

        if (dv.Count > 0)
        {
            foreach (DataRowView drv in dv)
            {
                EventsID = (int)drv["EVENT_ID"];
                EventTitle = drv["EVENT_TITLE"].ToString();
                EventTypeImage = drv["CATEGORY"].ToString();
                AppMeetingStartTime = drv["APPMEETING_STARTTIME"].ToString();
                AppMeetingEndTime = drv["APPMEETING_ENDTIME"].ToString();

                imgsrc = "images/" + EventTypeImage + ".gif";
                strDayOfWeek = StartDate.DayOfWeek.ToString();
                ToolTipMouseOver = "\"Tip('<b>Type:</b> " + EventType + " on " + CustomDateFormat(StartDate) + " " + AppMeetingStartTime + " - " + AppMeetingEndTime + "<br> View detail.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')\"";
                ToolTipMouseOut = "\"UnTip()\"";

                //For Sunday and Monday we float the event detail pop-up modal to the right.
                if (strDayOfWeek == "Sunday" || strDayOfWeek == "Monday")
                    EventURL = "\"showEvent(" + EventsID + ",'top','right')\"";
                else
                    EventURL = "\"showEvent(" + EventsID + ",'top','left')\"";

                DataOutPut.Append("<div id='eventID" + EventsID + "'" + " class='eventItemDiv' style='margin: 3px 3px 3px 3px; border: 1px solid #88B5FF; padding: 2px 3px 2px 3px;'><img src=" + imgsrc + ">&nbsp;<a class='content2' href='javascript:void(0)'" + " onclick=" + EventURL + "onmouseover=" + ToolTipMouseOver + " " + "onmouseout=" + ToolTipMouseOut + ">" + EventTitle + "</a>" + DisplayAppMeetingTime(AppMeetingStartTime, AppMeetingEndTime) + "</div>");
            }
        }
        else
        {
            DataOutPut.Append("<br /><br /><br /><br />");
        }

        return DataOutPut.ToString();

        dv = null;
    }
}