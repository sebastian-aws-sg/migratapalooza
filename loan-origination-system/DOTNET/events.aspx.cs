#region XD World Recipe V 3
// FileName: events.cs
// Author: Dexter Zafra
// Date Created: 4/20/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using XDRecipe.BL.Providers.Events;
using XDRecipe.Common.EventCalendar;
using XDRecipe.Common.Utilities;
using XDRecipe.BL;
using XDRecipe.UI;
using System.Text;

public partial class events : BasePage
{
    public string DynmicStyleItem;
    public string NoEventSpacer;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            cal1.VisibleDate = DateTime.Now.Date;
            txtDate.Text = DateTime.Now.Date.ToShortDateString();
            lblListViewTitle.Text = "<b>" + Utility.GetMonthNameOrAbbrev(DateTime.Now.Month, true) + " " + DateTime.Now.Year.ToString() + "</b>";
            lblListViewVisibleDate.Text = DateTime.Now.Month + "/1/" + DateTime.Now.Year;
            LoadButtonsToolTip();
            LoadCalendarViewEvents();
            GetDDLCurrentMonth();
            GetDDLCurrentYear();
        }

        HideControlsIfUpComingRecentEventsView();
        LoadUpComingEvents();
        LoadRecentEvents();
    }

    private bool IsUpComingAndRecentEventsView
    {
        get
        {
            if (!string.IsNullOrEmpty(Request.QueryString["view"]) && Request.QueryString["view"] == "upcoming")
                return true;
            else
                return false;
        }
    }

    private void LoadButtonsToolTip()
    {
        btnlvnxtmonth.Attributes.Add("onmouseover", "Tip('<b>" + Utility.GetMonthNameOrAbbrev(Convert.ToDateTime(lblListViewVisibleDate.Text).AddMonths(1).Month, true) + "</b> " + Convert.ToDateTime(lblListViewVisibleDate.Text).Year.ToString() + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnlvnxtmonth.Attributes.Add("onmouseout", "UnTip()");
        btnlvprevmonth.Attributes.Add("onmouseover", "Tip('<b>" + Utility.GetMonthNameOrAbbrev(Convert.ToDateTime(lblListViewVisibleDate.Text).AddMonths(-1).Month, true) + "</b> " + Convert.ToDateTime(lblListViewVisibleDate.Text).Year.ToString() + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnlvprevmonth.Attributes.Add("onmouseout", "UnTip()");
        btnnxtday.Attributes.Add("onmouseover", "Tip('Select " + Utility.GetMonthNameOrAbbrev(Convert.ToDateTime(txtDate.Text).Month, true) + ", " + Convert.ToDateTime(txtDate.Text).AddDays(1).Day + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnnxtday.Attributes.Add("onmouseout", "UnTip()");
        btnprevday.Attributes.Add("onmouseover", "Tip('Select " + Utility.GetMonthNameOrAbbrev(Convert.ToDateTime(txtDate.Text).Month, true) + ", " + Convert.ToDateTime(txtDate.Text).AddDays(-1).Day + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnprevday.Attributes.Add("onmouseout", "UnTip()");
    }

    private void HideControlsIfUpComingRecentEventsView()
    {
        if (IsUpComingAndRecentEventsView)
        {
            PanelSelectDate.Visible = false;
            cal1.Visible = false;
            ddlmonth.Visible = false;
            ddlyear.Visible = false;
            btnjumpMY.Visible = false;
            PanelListView.Visible = true;
            ddlFormat.Visible = false;
        }
    }

    private void LoadUpComingEvents()
    {
        if (IsUpComingAndRecentEventsView)
        {
            ProviderGetUpComingPublicEvents.Param(ddleventtype.SelectedItem.Value);

            int Count = ProviderGetUpComingPublicEvents.RecordCount;
            lblcountupcomingevent.Visible = true;

            if (Count == 0)
            {
                lblcountupcomingevent.Text = "There are No Upcoming Events";
            }
            else
            {
                lblcountupcomingevent.Text = "There are (" + Count + ") Upcoming Events";
                UpComingEvents.Visible = true;
                UpComingEvents.DataSource = ProviderGetUpComingPublicEvents.UpComingPublicEvents();
                UpComingEvents.DataBind();
            }
        }
    }

    private void LoadRecentEvents()
    {
        if (IsUpComingAndRecentEventsView)
        {
            ProviderGetPast30DaysPublicEvents.Param(ddleventtype.SelectedItem.Value);

            int Count = ProviderGetPast30DaysPublicEvents.RecordCount;
            lblpastpublicevents.Visible = true;

            if (Count == 0)
            {
                lblpastpublicevents.Text = "There are No Events Recent Events";
            }
            else
            {
                lblpastpublicevents.Text = "There are (" + Count + ") Recent Events";
                PastPublicEvents.Visible = true;
                PastPublicEvents.DataSource = ProviderGetPast30DaysPublicEvents.Past30DaysPublicEvents();
                PastPublicEvents.DataBind();
            }
        }
    }

    private void LoadCalendarViewEvents()
    {
        string SelectedFormat = ddlFormat.SelectedItem.Value;

        if (SelectedFormat == "ListView")
        {
            PanelListViewTitle.Visible = true;
            cal1.PrevMonthText = "&nbsp;&laquo; Previous";
            cal1.NextMonthText = "Next &raquo;&nbsp;";
            PanelSelectDate.Visible = false;
            cal1.ListView = "ListView";
            cal1.TitleStyle.Font.Bold = true;
            cal1.TitleStyle.CssClass = "DayTitleStyleListView";
            cal1.DayHeaderStyle.CssClass = "DayTitleStyleListView";
            cal1.ShowDayHeader = false;
            DynmicStyleItem = "padding: 2px; margin: 3px 3px 3px 4px; width: 120px;";
            cal1.DayNumberStyle.CssClass = "dayNumberListView";
            NoEventSpacer = "<br /><br />";
            cal1.DayNumberStyle.Font.Bold = true;
        }
        else
        {
            PanelListViewTitle.Visible = false;
            cal1.PrevMonthText = "&nbsp;&laquo; Previous Month";
            cal1.NextMonthText = "Next Month &raquo;&nbsp;";
            cal1.TitleStyle.CssClass = "TitleStyle";
            cal1.DayHeaderStyle.CssClass = "DayHeaderStyle";
            PanelSelectDate.Visible = true;
            DynmicStyleItem = "border: 1px solid #88B5FF; padding: 2px; margin: 2px 3px 3px 4px;";
            NoEventSpacer = "<br /><br /><br /><br /><br />";
            cal1.DayNumberStyle.CssClass = "dayNumber";
        }

        if (IsUpComingAndRecentEventsView)
        {
            PanelSelectDate.Visible = false;
        }

        ddleventtype.AutoPostBack = true;
        cal1.UseAccessibleHeader = false;
        DateTime startDate = new DateTime(cal1.VisibleDate.Year, cal1.VisibleDate.Month, 1).AddDays(-7);
        DateTime endDate = new DateTime(cal1.VisibleDate.Date.AddMonths(1).Year, cal1.VisibleDate.Date.AddMonths(1).Month, 1).AddDays(7);
        cal1.DataSource = Blogic.ActionProcedureDataProvider.GetAllPublicEventsCalendar(startDate, endDate, ddleventtype.SelectedItem.Value);
    }

    public void MonthChange(Object o, MonthChangedEventArgs e)
    {
        string SelectedFormat = ddlFormat.SelectedItem.Value;

        if (SelectedFormat == "ListView")
        {
            lblListViewTitle.Text = "<b>" + Utility.GetMonthNameOrAbbrev(e.NewDate.Month, true) + " " + e.NewDate.Year.ToString() + "</b>";
            lblListViewVisibleDate.Text = e.NewDate.Date.ToShortDateString();
            PanelListViewTitle.Visible = true;
            PanelSelectDate.Visible = false;
            cal1.ListView = "ListView";
            cal1.TitleStyle.Font.Bold = true;
            cal1.TitleStyle.CssClass = "DayTitleStyleListView";
            cal1.DayHeaderStyle.CssClass = "DayTitleStyleListView";
            cal1.ShowDayHeader = false;
            DynmicStyleItem = "padding: 2px; margin: 3px 3px 3px 4px; width: 120px;";
            cal1.DayNumberStyle.CssClass = "dayNumberListView";
            NoEventSpacer = "<br /><br />";
            cal1.DayNumberStyle.Font.Bold = true;

            btnlvnxtmonth.Attributes.Add("onmouseover", "Tip('<b>" + Utility.GetMonthNameOrAbbrev(e.NewDate.AddMonths(1).Month, true) + "</b> " + e.NewDate.Year.ToString() + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            btnlvnxtmonth.Attributes.Add("onmouseout", "UnTip()");
            btnlvprevmonth.Attributes.Add("onmouseover", "Tip('<b>" + Utility.GetMonthNameOrAbbrev(e.NewDate.AddMonths(-1).Month, true) + "</b> " + e.NewDate.Year.ToString() + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
            btnlvprevmonth.Attributes.Add("onmouseout", "UnTip()");
        }
        else
        {
            PanelListViewTitle.Visible = false;
            cal1.TitleStyle.CssClass = "TitleStyle";
            cal1.DayHeaderStyle.CssClass = "DayHeaderStyle";
            PanelSelectDate.Visible = true;
            DynmicStyleItem = "border: 1px solid #88B5FF; padding: 2px; margin: 2px 3px 3px 4px;";
            NoEventSpacer = "<br /><br /><br /><br /><br />";
            cal1.DayNumberStyle.CssClass = "dayNumber";
        }

        if (IsUpComingAndRecentEventsView)
        {
            PanelSelectDate.Visible = false;
        }

        DateTime startDate = e.NewDate.AddDays(-7);
        DateTime endDate = e.NewDate.AddMonths(1).AddDays(7);
        cal1.DataSource = Blogic.ActionProcedureDataProvider.GetAllPublicEventsCalendar(startDate, endDate, ddleventtype.SelectedItem.Value);
    }

    public void Format_Click(object sender, EventArgs e)
    {
        if (!IsUpComingAndRecentEventsView)
        {
            ddlFormat.SelectedItem.Value = "ListView";
            ddlFormat.SelectedIndex = 1;
            LoadCalendarViewEvents();
        }
        else
        {
            Response.Redirect("events.aspx");
        }
    }

    public void ddlFormat_OnChange(object sender, EventArgs e)
    {
        if (!IsUpComingAndRecentEventsView)
        {
            LoadCalendarViewEvents();
        }
        else
        {
            Response.Redirect("events.aspx");
        }
    }

    public void ddleventtype_OnChange(object sender, EventArgs e)
    {
        LoadCalendarViewEvents();
    }

    public void btnlvnxtmonth_Click(object sender, EventArgs e)
    {
        DateTime startDate = Convert.ToDateTime(lblListViewVisibleDate.Text);
        cal1.VisibleDate = startDate.AddMonths(1);
        lblListViewVisibleDate.Text = startDate.AddMonths(1).ToShortDateString();
        lblListViewTitle.Text = "<b>" + Utility.GetMonthNameOrAbbrev(Convert.ToDateTime(lblListViewVisibleDate.Text).Month, true) + " " + Convert.ToDateTime(lblListViewVisibleDate.Text).Year.ToString() + "</b>";
        btnlvnxtmonth.Attributes.Add("onmouseover", "Tip('<b>" + Utility.GetMonthNameOrAbbrev(Convert.ToDateTime(lblListViewVisibleDate.Text).AddMonths(1).Month, true) + "</b> " + Convert.ToDateTime(lblListViewVisibleDate.Text).Year.ToString() + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnlvnxtmonth.Attributes.Add("onmouseout", "UnTip()");
        btnlvprevmonth.Attributes.Add("onmouseover", "Tip('<b>" + Utility.GetMonthNameOrAbbrev(Convert.ToDateTime(lblListViewVisibleDate.Text).AddMonths(-1).Month, true) + "</b> " + Convert.ToDateTime(lblListViewVisibleDate.Text).Year.ToString() + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnlvprevmonth.Attributes.Add("onmouseout", "UnTip()");
        LoadCalendarViewEvents();
    }

    public void btnlvprevmonth_Click(object sender, EventArgs e)
    {
        DateTime startDate = Convert.ToDateTime(lblListViewVisibleDate.Text);
        cal1.VisibleDate = startDate.AddMonths(-1);
        lblListViewVisibleDate.Text = startDate.AddMonths(-1).ToShortDateString();
        lblListViewTitle.Text = "<b>" + Utility.GetMonthNameOrAbbrev(Convert.ToDateTime(lblListViewVisibleDate.Text).Month, true) + " " + Convert.ToDateTime(lblListViewVisibleDate.Text).Year.ToString() + "</b>";
        btnlvprevmonth.Attributes.Add("onmouseover", "Tip('<b>" + Utility.GetMonthNameOrAbbrev(Convert.ToDateTime(lblListViewVisibleDate.Text).AddMonths(-1).Month, true) + "</b> " + Convert.ToDateTime(lblListViewVisibleDate.Text).Year.ToString() + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnlvprevmonth.Attributes.Add("onmouseout", "UnTip()");
        btnlvnxtmonth.Attributes.Add("onmouseover", "Tip('<b>" + Utility.GetMonthNameOrAbbrev(Convert.ToDateTime(lblListViewVisibleDate.Text).AddMonths(1).Month, true) + "</b> " + Convert.ToDateTime(lblListViewVisibleDate.Text).Year.ToString() + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnlvnxtmonth.Attributes.Add("onmouseout", "UnTip()");
        LoadCalendarViewEvents();
    }

    public void SelectedDateChange(object sender, EventArgs e)
    {
        txtDateDefaultValue();
        DateTime startDate = Convert.ToDateTime(txtDate.Text);
        cal1.SelectedDate = startDate;
        cal1.VisibleDate = startDate;
        LoadCalendarViewEvents();
    }

    public void SelectedDateNextDay(object sender, EventArgs e)
    {
        txtDateDefaultValue();
        DateTime startDate = Convert.ToDateTime(txtDate.Text);
        cal1.SelectedDate = startDate.AddDays(1);
        txtDate.Text = startDate.AddDays(1).ToShortDateString();
        btnnxtday.Attributes.Add("onmouseover", "Tip('Select " + Utility.GetMonthNameOrAbbrev(Convert.ToDateTime(txtDate.Text).Month, true) + ", " + Convert.ToDateTime(txtDate.Text).AddDays(1).Day + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnnxtday.Attributes.Add("onmouseout", "UnTip()");
        btnprevday.Attributes.Add("onmouseover", "Tip('Select " + Utility.GetMonthNameOrAbbrev(Convert.ToDateTime(txtDate.Text).Month, true) + ", " + Convert.ToDateTime(txtDate.Text).AddDays(-1).Day + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnprevday.Attributes.Add("onmouseout", "UnTip()");
        LoadCalendarViewEvents();
    }

    public void SelectedDatePreviousDay(object sender, EventArgs e)
    {
        txtDateDefaultValue();
        DateTime startDate = Convert.ToDateTime(txtDate.Text);
        cal1.SelectedDate = startDate.AddDays(-1);
        txtDate.Text = startDate.AddDays(-1).ToShortDateString();
        btnnxtday.Attributes.Add("onmouseover", "Tip('Select " + Utility.GetMonthNameOrAbbrev(Convert.ToDateTime(txtDate.Text).Month, true) + ", " + Convert.ToDateTime(txtDate.Text).AddDays(1).Day + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnnxtday.Attributes.Add("onmouseout", "UnTip()");
        btnprevday.Attributes.Add("onmouseover", "Tip('Select " + Utility.GetMonthNameOrAbbrev(Convert.ToDateTime(txtDate.Text).Month, true) + ", " + Convert.ToDateTime(txtDate.Text).AddDays(-1).Day + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnprevday.Attributes.Add("onmouseout", "UnTip()");
        LoadCalendarViewEvents();
    }

    public void FilterEvent(Object sender, EventArgs e)
    {
        cal1.VisibleDate = new DateTime(int.Parse(ddlyear.SelectedValue), int.Parse(ddlmonth.SelectedValue), 1);
        lblListViewVisibleDate.Text = cal1.VisibleDate.ToShortDateString();
        lblListViewTitle.Text = "<b>" + Utility.GetMonthNameOrAbbrev(Convert.ToDateTime(lblListViewVisibleDate.Text).Month, true) + " " + Convert.ToDateTime(lblListViewVisibleDate.Text).Year.ToString() + "</b>";
        btnlvnxtmonth.Attributes.Add("onmouseover", "Tip('<b>" + Utility.GetMonthNameOrAbbrev(Convert.ToDateTime(lblListViewVisibleDate.Text).AddMonths(1).Month, true) + "</b> " + Convert.ToDateTime(lblListViewVisibleDate.Text).Year.ToString() + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnlvnxtmonth.Attributes.Add("onmouseout", "UnTip()");
        btnlvprevmonth.Attributes.Add("onmouseover", "Tip('<b>" + Utility.GetMonthNameOrAbbrev(Convert.ToDateTime(lblListViewVisibleDate.Text).AddMonths(-1).Month, true) + "</b> " + Convert.ToDateTime(lblListViewVisibleDate.Text).Year.ToString() + "', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')");
        btnlvprevmonth.Attributes.Add("onmouseout", "UnTip()");
        LoadCalendarViewEvents();
    }

    private void txtDateDefaultValue()
    {
        if (string.IsNullOrEmpty(txtDate.Text))
            txtDate.Text = DateTime.Now.Date.ToShortDateString();
    }

    public string NumberOfDays(object s, object e, object st, object et)
    {
        int numdays = Utility.DateDiff(Convert.ToDateTime(s), Convert.ToDateTime(e));

        DateTime startDate = Convert.ToDateTime(s);
        DateTime endDate = Convert.ToDateTime(e);
        StringBuilder mySB = new StringBuilder();

        if (numdays > 0)
        {
            mySB.Append(numdays + " days  ");
            mySB.Append(Utility.GetDayNameAbbrev(startDate.DayOfWeek.ToString()));
            mySB.Append(", " + Utility.GetMonthNameOrAbbrev(int.Parse(startDate.Month.ToString()), true));
            mySB.Append(" " + int.Parse(startDate.Day.ToString()));
            mySB.Append(" - " + Utility.GetDayNameAbbrev(endDate.DayOfWeek.ToString()));
            mySB.Append(", " + Utility.GetMonthNameOrAbbrev(int.Parse(endDate.Month.ToString()), true));
            mySB.Append(" " + int.Parse(endDate.Day.ToString()));
        }
        else
        {
            if (!string.IsNullOrEmpty(st.ToString()) && !string.IsNullOrEmpty(et.ToString()))
            {
                mySB.Append(st.ToString() + " - " + et.ToString());
            }
            else if (!string.IsNullOrEmpty(st.ToString()) && string.IsNullOrEmpty(et.ToString()))
            {
                mySB.Append(st.ToString());
            }
            else
            {
                mySB.Append("All Day");
            }
        }

        return mySB.ToString();
    }

    public string DisplayAppMeetingTime(object st, object et)
    {
        StringBuilder mySB = new StringBuilder();

        if (!string.IsNullOrEmpty(st.ToString()) && !string.IsNullOrEmpty(et.ToString()))
        {
            mySB.Append("<br>" + st.ToString() + " - " + et.ToString());
        }
        else if (!string.IsNullOrEmpty(st.ToString()) && string.IsNullOrEmpty(et.ToString()))
        {
            mySB.Append("<br>" + st.ToString());
        }

        return mySB.ToString();
    }

    private void GetDDLCurrentMonth()
    {
        ddlmonth.Items.Insert(0, new ListItem(Utility.GetMonthNameOrAbbrev(DateTime.Now.Month, true), DateTime.Now.Month.ToString()));
    }

    private void GetDDLCurrentYear()
    {
        ddlyear.Items.Insert(0, new ListItem(DateTime.Now.Year.ToString(), DateTime.Now.Year.ToString()));
    }

    //Determine whether AJAX pop-up details float to the right or left.
    public string FloatLeftOrRight(object o)
    {
        string SelectedFormat = ddlFormat.SelectedItem.Value;
        string lr = string.Empty;

        if (Utility.WeekDayName(Convert.ToDateTime(o)) == "Sunday" || Utility.WeekDayName(Convert.ToDateTime(o)) == "Monday")
            lr = "right";
        else
            lr = "left";

        if (SelectedFormat == "ListView")
        {
            lr = "right";
        }

        return lr;
    }
}
