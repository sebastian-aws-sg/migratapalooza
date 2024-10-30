<%@ Page Language="C#" MasterPageFile="~/SiteTemplate2.master" AutoEventWireup="true" CodeFile="addevent.aspx.cs" Inherits="addevent" Title="Adding an event" %>
<%@ Register TagPrefix="ucl" TagName="sidemenu" Src="Control/sidemenu.ascx" %>

<asp:Content ID="Content1" ContentPlaceHolderID="LeftPanel" Runat="Server">
<br />
<ucl:sidemenu id="menu1" runat="server"></ucl:sidemenu>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
<script type="text/javascript">
jQuery(function() {
$("#ctl00_MainContent_txtstartTime").timePicker();
$("#ctl00_MainContent_txtendTime").timePicker();
$('#ctl00_MainContent_txtstartDate').datepick();
$('#ctl00_MainContent_txtendDate').datepick();
});
</script>
<div style="margin-top: 20px; margin-left: 15px; margin-right: 75px;"> 
    <fieldset><legend>Add Event</legend>
     <div style="margin: 20px 24px 35px 16px;">
 <table border="0" cellpadding="6" cellspacing="3">
  <tr>
    <td width="7%">Title</td>
    <td width="93%"><asp:TextBox ID="eventTitle" runat="server" Width="350" CssClass="textboxsearch" /></td>
  </tr>
  <tr>
    <td width="7%">When</td>
    <td width="93%">
    <asp:TextBox ID="txtstartDate" runat="server" Width="80" CssClass="textboxsearch" />&nbsp;&nbsp;<asp:TextBox ID="txtstartTime" runat="server" Width="70" CssClass="textboxsearch" />&nbsp;&nbsp;To&nbsp;&nbsp;<asp:TextBox ID="txtendTime" runat="server" Width="70" CssClass="textboxsearch" />&nbsp;&nbsp;<asp:TextBox ID="txtendDate" runat="server" Width="80" CssClass="textboxsearch" />
    </td>
  </tr>
  <tr>
    <td width="7%">Privacy</td>
    <td width="93%">
    Public<input type="radio" id="eventprivacy" name="eventprivacy" value="0" checked "/>&nbsp;&nbsp;Private<input type="radio" id="Radio1" name="eventprivacy" value="1" />
    </td>
  </tr>
  <tr>
    <td width="7%">Type</td>
    <td width="93%">
      <asp:Dropdownlist runat="server" ID="ddleventtype" cssClass="cselect" width="130px">
        <asp:Listitem Value="Appointment">Appointment</asp:Listitem>
        <asp:Listitem Value="Meeting">Meeting</asp:Listitem>
        <asp:Listitem Value="Anniversary">Anniversary</asp:Listitem>
        <asp:Listitem Value="USHoliday">Holiday</asp:Listitem>
        <asp:Listitem Value="BirthDay">BirthDay</asp:Listitem>
        <asp:Listitem Value="Info">Information</asp:Listitem>
    </asp:Dropdownlist>
    </td>
  </tr>
  <tr>
    <td width="7%" valign="top">Description</td>
    <td width="93%">
    <textarea id="eventDesc" name="eventDesc" Class="textbox" cols="55" rows="7" onKeyDown="textCounter(350);" onKeyUp="textCounter(350);" onFocus="this.style.backgroundColor='#FFF9EC'" onBlur="this.style.backgroundColor='#ffffff'"  runat="server" />
    </td>
  </tr>
  <tr>
    <td width="7%">&nbsp;</td>
    <td width="93%"><asp:Button CssClass="submitadmin" ID="btnSubmit" Text="Submit" runat=server /></td>
  </tr>
</table>
     </div>
     </fieldset>
<div>
</asp:Content>