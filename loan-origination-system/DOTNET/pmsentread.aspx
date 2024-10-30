<%@ Page Language="C#" MasterPageFile="~/SiteTemplate2.master" EnableViewState="false" AutoEventWireup="true" CodeFile="pmsentread.aspx.cs" Inherits="pmsentread" Title="Reading Sent Message" %>
<%@ Register TagPrefix="ucl" TagName="pmmenu" Src="Control/pmmenu.ascx" %>

<asp:Content ID="Content1" ContentPlaceHolderID="LeftPanel" Runat="Server">
    <ucl:pmmenu id="pm_menu" runat="server"></ucl:pmmenu>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
<div style="margin-left: 15px; margin-left: 15px; margin-top: 50px; margin-right: 200px;">
<fieldset><legend>Reading Sent Message</legend>
<asp:Label runat="server" id="lblyouarenotlogin" Visible="false" CssClass="content12" EnableViewState="false" />
<asp:Panel ID="HideContentIfNotLogin" Visible="false" runat="server">
<div style="background:#D9F3B0 url(images/cpbggreen6.gif) repeat-x; border: 1px solid #D9F3B0;padding-top: 2px; padding-bottom: 2px; padding-left: 6px; text-align: left; margin-top: 25px; margin-right: 10px; margin-bottom: 20px;">
<span style="font-family : tahoma,arial,helvetica,sans-serif; font-size: 14px; color: #274E74;"><b>Hi, <%=HiUserName%></b></span></div>
<table border="0" cellpadding="0" cellspacing="0" width="99%" style="border: 1px solid #E1EDFF;">
  <tr>
    <td width="100%" class="PMHeader"><div style="padding-top: 7px; padding-bottom: 7px; margin-left: 4px;"><asp:Label ID="lblsubject" CssClass="content12" style="color: #0C0260;" runat="server" /></div></td>
  </tr>
    <tr>
    <td width="100%">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td width="14%" height="91" rowspan="2" valign="top" style="border-right: 1px solid #E1EDFF;"><div style="margin: 6px;"><img src="images/user-icon.gif" />&nbsp;<asp:Label ID="lblsenderinfo" CssClass="content2" runat="server" /></div></td>
    <td width="86%" height="27" valign="top" style="border-bottom: 1px solid #E1EDFF;"><div style="margin: 6px;"><asp:Label ID="lbldate" CssClass="content2" runat="server" />&nbsp;&nbsp;&nbsp;<asp:Label ID="lblemailsender" CssClass="content2" runat="server" /></div></td>
  </tr>
  <tr>
    <td width="86%" height="64" valign="top"><div style="margin-top: 8px; margin-left: 8px; margin-right: 25px; margin-bottom: 20px;"><asp:Label ID="lblmessage" CssClass="content2" runat="server" /></div></td>
  </tr>
</table>
    </td>
  </tr>
</table>
</asp:Panel>
<br />
    </fieldset>
</div>
<div style="height: 150px;"></div>
</asp:Content>
