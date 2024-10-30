<%@ Control Language="C#" AutoEventWireup="true" CodeFile="usersearchtab.ascx.cs" Inherits="usersearchtab" %>
<!--Begin Search-->
<div style="margin-left: 10px; margin-right: 12px;">
<div class="toproundbluesearchtab">
<div class="toproundbluesearchtabheader"><span class="content3" style="color: #fff;">Search for Users</span></div>
</div>
<div id="basic" class="tbcont">
<div style="padding-top: 5px;">
<img src="images/search.gif" border="0" alt="Search recipe" align="absmiddle"> 
<asp:dropdownlist id="ddlSearchUser" runat="server" Width="100" cssClass="ddlsearch" AutoPostBack="false">
<asp:Listitem Value="1">User name</asp:Listitem>
<asp:Listitem Value="2">First name</asp:Listitem>
<asp:Listitem Value="3">Last name</asp:Listitem>
<asp:Listitem Value="4">City</asp:Listitem>
<asp:Listitem Value="5">State</asp:Listitem>
<asp:Listitem Value="6">Country</asp:Listitem>
</asp:dropdownlist>
<input type="text" name="usersearcbasicinput" id="usersearcbasicinput" class="textboxsearch" size="20" value="Administrator" onfocus="if(this.value=='Administrator')value='';" onblur="if(this.value=='')value='Administrator';" runat="server">
<asp:Button ID="Sbuton" runat="server" cssClass="submitadmin" OnClick="SearchUserbasic_Click" Text="Search" />
</div>
</div>
</div>
<!--End Search-->