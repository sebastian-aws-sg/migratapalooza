<%@ Page Language="C#" MasterPageFile="~/SiteTemplate2.master" AutoEventWireup="true" CodeFile="testcal.aspx.cs" Inherits="testcal" Title="Untitled Page" %>
<%@ Register TagPrefix="ucl" TagName="sidemenu" Src="Control/sidemenu.ascx" %>
<%@ Register TagPrefix="ucl" TagName="Upcomingevents" Src="Control/Upcomingevents.ascx" %>

<asp:Content ID="Content1" ContentPlaceHolderID="LeftPanel" Runat="Server">
<br />
<ucl:sidemenu id="menu1" runat="server"></ucl:sidemenu>
<ucl:Upcomingevents id="event1" runat="server"></ucl:Upcomingevents>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
<div style="margin-top: 20px; margin-left: 15px; margin-right: 75px;"> 
    <fieldset><legend>Events Week View</legend>
     <div style="margin: 20px 24px 35px 16px;">
     <div style="margin-bottom: 16px;">
     <img src="images/calendarview_icon.gif" title="Calendar view" />&nbsp;<a class="content2" href="events.aspx" onmouseover="Tip('View in <b>Calendar View</b> mode.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">Calendar View</a>&nbsp;&nbsp;&nbsp;<img src="images/calendarview_icon.gif" title="List view" />&nbsp;<a class="content2" href="events.aspx?view=upcoming" onmouseover="Tip('View in all upcoming and recent events.</b> mode.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">Upcoming &amp; Recent</a>
     </div>
     <div style="margin: 0px 0px 8px 0px; float: left;"">
        <asp:ImageButton ImageUrl="images/prevdaybtn.gif" style="vertical-align:middle;" ID="btnprevday" runat="server" OnClick="PreviousWeek" />&nbsp;<asp:ImageButton ImageUrl="images/btnCurrentWeek.gif" style="vertical-align:middle;" ID="btnCurrentWeek" runat="server" OnClick="ResetToCurrentWeek" />&nbsp;<asp:ImageButton ImageUrl="images/nxtdaybtn.gif" style="vertical-align:middle;" ID="btnnxtday" runat="server" OnClick="NextWeek" />
        &nbsp;<asp:Label ID="lblbeginenddate" runat="server" CssClass="content2" /><asp:Label ID="lblEventType" runat="server" CssClass="content2" /><span class="content2">&nbsp;Week&nbsp;</span><asp:Label ID="lbWeekCounter" runat="server" CssClass="content2" />
     </div>
     <div style="margin-bottom: 8px; float: right;">
         <asp:Dropdownlist runat="server" ID="ddleventtype" AutoPostBack="True" OnSelectedIndexChanged="btnSubmit_Click" cssClass="cselect" width="150px">
        <asp:Listitem Value="All" selected>All Events</asp:Listitem>
        <asp:Listitem Value="Appointment">Appointment</asp:Listitem>
        <asp:Listitem Value="Meeting">Meeting</asp:Listitem>
        <asp:Listitem Value="Anniversary">Anniversary</asp:Listitem>
        <asp:Listitem Value="USHoliday">Holiday</asp:Listitem>
        <asp:Listitem Value="BirthDay">BirthDay</asp:Listitem>
        <asp:Listitem Value="Info">Information</asp:Listitem>
    </asp:Dropdownlist>
    <asp:DropDownList id="ddlweeknumber" runat="server" cssClass="cselect" Width="85">          
    </asp:DropDownList>
    <asp:DropDownList id="ddlyear" runat="server" cssClass="cselect" Width="60">
             <asp:ListItem>2009</asp:ListItem>
             <asp:ListItem>2010</asp:ListItem>
             <asp:ListItem>2011</asp:ListItem>
             <asp:ListItem>2012</asp:ListItem>
             <asp:ListItem>2013</asp:ListItem>
             <asp:ListItem>2014</asp:ListItem>
             <asp:ListItem>2015</asp:ListItem>
     </asp:DropDownList>
     <asp:Button CssClass="submitadmin" ID="btnSubmit" Text="Submit" OnClick="btnSubmit_Click" runat=server />
     </div>
     <div style="clear:both;"></div>
       <%=BuildCalendar%>
     </div>
     </fieldset>
<div>
</asp:Content>
