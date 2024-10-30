<%@ Page Language="C#" MasterPageFile="~/SiteTemplate2.master" AutoEventWireup="true" CodeFile="events.aspx.cs" Inherits="events" Title="Events Calendar" %>
<%@ Register TagPrefix="ucl" TagName="sidemenu" Src="Control/sidemenu.ascx" %>
<%@ Register TagPrefix="ucl" TagName="Upcomingevents" Src="Control/Upcomingevents.ascx" %>
<% @Register Namespace="XDRecipe.Common.EventCalendar" TagPrefix="ec" %>

<asp:Content ID="Content1" ContentPlaceHolderID="LeftPanel" Runat="Server">
<br />
<ucl:sidemenu id="menu1" runat="server"></ucl:sidemenu>
<ucl:Upcomingevents id="event1" runat="server"></ucl:Upcomingevents>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
<script type="text/javascript">
function AddEventCalendar(sdate)
{
 window.location = "addevent.aspx?sdate=" + sdate;
}
</script>
<div style="margin-top: 20px; margin-left: 15px; margin-right: 75px;"> 
    <fieldset><legend>Events Calendar</legend>
     <div style="margin: 20px 6px 35px 16px;">
     <div style="margin-bottom: 16px;">
     <img src="images/calendarview_icon.gif" title="Calendar view" />&nbsp;<a class="content2" href="events.aspx" onmouseover="Tip('View in <b>Calendar View</b> format.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">Month View</a>&nbsp;&nbsp;&nbsp;<img src="images/calendarview_icon.gif" title="Calendar view" />&nbsp;<asp:LinkButton ID="btnListView" runat="server" CssClass="content2" Text="List View" OnClick="Format_Click" onmouseover="Tip('View in <b>List View</b> format.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />&nbsp;&nbsp;&nbsp;<img src="images/calendarview_icon.gif" title="Calendar view" />&nbsp;<a class="content2" href="eventweekview.aspx" onmouseover="Tip('View in <b>Week View</b> mode.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">Week View</a>&nbsp;&nbsp;&nbsp;<img src="images/calendarview_icon.gif" title="List view" />&nbsp;<a class="content2" href="events.aspx?view=upcoming" onmouseover="Tip('View in all upcoming and recent events.</b> mode.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">Upcoming &amp; Recent</a>
     </div>
     <asp:Panel ID="PanelListViewTitle" runat="server" style="margin: 0px 0px 8px 0px; float: left;">
       <asp:ImageButton ImageUrl="images/prevdaybtn.gif" style="vertical-align:middle;" ID="btnlvprevmonth" runat="server" OnClick="btnlvprevmonth_Click" />&nbsp;&nbsp;<asp:Label runat="server" id="lblListViewTitle" style="font-family: arial; font-size: 16px; color: #000;" />&nbsp;&nbsp;<asp:ImageButton ImageUrl="images/nxtdaybtn.gif" style="vertical-align:middle;" ID="btnlvnxtmonth" OnClick="btnlvnxtmonth_Click" runat="server" />
       <asp:Label runat="server" id="lblListViewVisibleDate" CssClass="content2" style="visibility:hidden;" />
     </asp:Panel>
     <asp:Panel ID="PanelSelectDate" runat="server" style="margin: 0px 0px 8px 0px; float: left;">
        <asp:TextBox ID="txtDate" CssClass="txtday" Width="74" runat="server" />&nbsp;<asp:ImageButton ImageUrl="images/btndaygo.gif" style="vertical-align:middle;" ID="btndaygo" runat="server" OnClick="SelectedDateChange" onmouseover="Tip('Get Selected Date', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()"  />
        <asp:ImageButton ImageUrl="images/prevdaybtn.gif" style="vertical-align:middle;" ID="btnprevday" runat="server" OnClick="SelectedDatePreviousDay" />&nbsp;<asp:ImageButton ImageUrl="images/nxtdaybtn.gif" style="vertical-align:middle;" ID="btnnxtday" runat="server" OnClick="SelectedDateNextDay" />
     </asp:Panel>
    <div style="margin: 0px 20px 8px 0px; float: right;">
     <span class="content2">Format:</span>
    <asp:Dropdownlist runat="server" ID="ddlFormat" AutoPostBack="True" OnSelectedIndexChanged="ddlFormat_OnChange" cssClass="cselect" width="120px">
        <asp:Listitem Value="Calendar" selected>Calendar View</asp:Listitem>
        <asp:Listitem Value="ListView">List View</asp:Listitem>
    </asp:Dropdownlist>
    <span class="content2">Filter:</span>
    <asp:Dropdownlist runat="server" ID="ddleventtype" AutoPostBack="True" OnSelectedIndexChanged="ddleventtype_OnChange" cssClass="cselect" width="150px">
        <asp:Listitem Value="All" selected>All Events</asp:Listitem>
        <asp:Listitem Value="Appointment">Appointment</asp:Listitem>
        <asp:Listitem Value="Meeting">Meeting</asp:Listitem>
        <asp:Listitem Value="Anniversary">Anniversary</asp:Listitem>
        <asp:Listitem Value="USHoliday">Holiday</asp:Listitem>
        <asp:Listitem Value="BirthDay">BirthDay</asp:Listitem>
        <asp:Listitem Value="Info">Information</asp:Listitem>
    </asp:Dropdownlist>
    <asp:DropDownList id="ddlmonth" runat="server" cssClass="cselect" Width="50">
         <asp:ListItem Value="1">Jan</asp:ListItem>
         <asp:ListItem Value="2">Feb</asp:ListItem>
         <asp:ListItem Value="3">Mar</asp:ListItem>
         <asp:ListItem Value="4">Apr</asp:ListItem>
         <asp:ListItem Value="5">May</asp:ListItem>
         <asp:ListItem Value="6">Jun</asp:ListItem>
         <asp:ListItem Value="7">Jul</asp:ListItem>
         <asp:ListItem Value="8">Aug</asp:ListItem>
         <asp:ListItem Value="9">Sep</asp:ListItem>
         <asp:ListItem Value="10">Oct</asp:ListItem>
         <asp:ListItem Value="11">Nov</asp:ListItem>
         <asp:ListItem Value="12">Dec</asp:ListItem>
    </asp:DropDownList>
    <asp:DropDownList id="ddlyear" runat="server" cssClass="cselect" Width="60">
             <asp:ListItem>2010</asp:ListItem>
             <asp:ListItem>2011</asp:ListItem>
             <asp:ListItem>2012</asp:ListItem>
             <asp:ListItem>2013</asp:ListItem>
             <asp:ListItem>2014</asp:ListItem>
             <asp:ListItem>2015</asp:ListItem>
     </asp:DropDownList>
      <asp:Button CssClass="submitadmin" ID="btnjumpMY" Text="Submit" OnClick="FilterEvent" runat=server />
     </div>
     <div style="clear:both"></div>
     <asp:Panel ID="PanelListView" runat="server" Visible="false">
     <asp:Label runat="server" id="lblcountupcomingevent" Visible="false" CssClass="content2" EnableViewState="false" />
     <asp:Repeater id="UpComingEvents" runat="server" Visible="false">     
        <ItemTemplate>
            <div class="dcnt2" style="margin-top: 2px; width: 150px;" id="upcomingeventID<%# Eval("EventsID") %>">
             <img src='images/<%# Eval("EventType") %>.gif' height="12" width="12" border="0" onmouseover="Tip('<b>Type:</b> <%# Eval("EventType") %>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />&nbsp;<a class="content2" href="javascript:void(0);" onclick="javascript: showUpcomingEvent(<%# Eval("EventsID") %>,'top','right')" onmouseover="Tip('<b>Type:</b> <%# Eval("EventType") %> on <%# CustomDateFormat(Eval("StartDate"))%> <%# Eval("AppMeetingStartTime") %> - <%# Eval("AppMeetingEndTime") %><br>View <%# Eval("EventTitle") %> detail.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()"><%# Eval("EventTitle") %></a>
            </div>         
       </ItemTemplate>
    </asp:Repeater>
    <br />
    <asp:Label runat="server" id="lblpastpublicevents" Visible="false" CssClass="content2" EnableViewState="false" />
     <asp:Repeater id="PastPublicEvents" runat="server" Visible="false">     
        <ItemTemplate>
            <div class="dcnt2" style="margin-top: 2px; width: 150px;" id="recenteventID<%# Eval("EventsID") %>">
             <img src='images/<%# Eval("EventType") %>.gif' height="12" width="12" border="0" onmouseover="Tip('<b>Type:</b> <%# Eval("EventType") %>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />&nbsp;<a class="content2" href="javascript:void(0);" onclick="javascript: showRecentEvent(<%# Eval("EventsID") %>,'top','right')" onmouseover="Tip('<b>Type:</b> <%# Eval("EventType") %> on <%# CustomDateFormat(Eval("StartDate"))%> <%# Eval("AppMeetingStartTime") %> - <%# Eval("AppMeetingEndTime") %><br>View <%# Eval("EventTitle") %> detail.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()"><%# Eval("EventTitle") %></a>
            </div>         
       </ItemTemplate>
    </asp:Repeater>
    </asp:Panel>
           <ec:EventCalendar id="cal1" runat="server" width="98%"
                                         DayField="START_DATE"
                                         OnVisibleMonthChanged="MonthChange"
                                         DayNameFormat="Full"
                                         FirstDayOfWeek="Sunday"  
                                         CssClass="eventmonthtable">
                         <NextPrevStyle CssClass="content2" ForeColor="#007AF4" />
                         <SelectedDayStyle CssClass="selecteddate" BackColor="#FFFDDD" ForeColor="#797979" />
                         <TodayDayStyle BackColor="#FFFDDD" />
                         <USHolidayWithEventsStyle BackColor="#FFDFD9" />
                        <DayStyle HorizontalAlign="Left" VerticalAlign="Top" Font-Size="10" ForeColor="#797979" Font-Bold="false" Font-Name="Arial" BackColor="#ffffff" />                          
                        <OtherMonthDayStyle BackColor="#f7f7f7" ForeColor="DarkGray" />
                        <ItemTemplate>
                            <div class="eventItemDiv" style="<%=DynmicStyleItem %>" id="eventID<%# Container.DataItem["EVENT_ID"] %>">
                            <img src='images/<%# Container.DataItem["CATEGORY"] %>.gif' height="12" width="12" align="absmiddle" border="0"/>                   
                            <a class="content2" onclick="showEvent(<%# Container.DataItem["EVENT_ID"] %>,'top','<%# FloatLeftOrRight(Container.DataItem["START_DATE"]) %>')" href='javascript:void(0);' onmouseover="Tip('<b><%# Container.DataItem["CATEGORY"] %></b> <%# NumberOfDays(Container.DataItem["START_DATE"], Container.DataItem["END_DATE"], Container.DataItem["APPMEETING_STARTTIME"], Container.DataItem["APPMEETING_ENDTIME"]) %><br>View <%# Container.DataItem["EVENT_TITLE"] %>.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">                    
                                    <%# Container.DataItem["EVENT_TITLE"] %>
                            </a>
                             <span style="font-family: verdana; font-size: 9px; color: #868686;"><%# DisplayAppMeetingTime(Container.DataItem["APPMEETING_STARTTIME"], Container.DataItem["APPMEETING_ENDTIME"]) %> </span>               
                            </div>           
                        </ItemTemplate>                    
                        <NoEventsTemplate>
                         <%=NoEventSpacer %>
                        </NoEventsTemplate>            
            </ec:EventCalendar>
          </div>
    </fieldset>
<div>
</asp:Content>

