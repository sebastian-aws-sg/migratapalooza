<%@ Control Language="C#" AutoEventWireup="true" CodeFile="categorylistsidemenu.ascx.cs" Inherits="categorylistsidemenu" EnableViewState="false"%>
<!--Begin Display Category List Side Menu-->
<div class="toproundorange">
<div class="toproundorangeheader"><span class="content3">Browse Recipe Categories</span></div>
</div>
<div class="contentdisplayora">
<div class="contentdis5">
<asp:Repeater id="CategoryList" runat="server" EnableViewState="false">
   <ItemTemplate>
<div class="dcnt2">
<span class="cyel">&raquo;</span> <a class="dt" title="Browse <%# Eval("Category") %> category" href='<%# Eval("CatID", "category.aspx?catid={0}") %>'><%# Eval("Category")%></a> <span class="content15">(<%# Eval("RecordCount")%>)</span>
</div>
   </ItemTemplate>
  </asp:Repeater>
</div>
</div>
<!--End Display Category List Side Menu-->