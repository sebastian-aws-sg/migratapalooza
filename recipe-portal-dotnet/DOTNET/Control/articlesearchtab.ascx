<%@ Control Language="C#" AutoEventWireup="true" CodeFile="articlesearchtab.ascx.cs" Inherits="articlesearchtab" EnableViewState="false"%>
<!--Begin Search-->
<div style="margin-left: 10px; margin-right: 12px;">
<div class="nifty" style="width: 120px;">
<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
<div class="dcntstab"><span class="contwhite">Article Search</span></div>
</div>
<div id="basic" class="tbcont">
<div style="padding-top: 5px; color: #000;">
<img src="images/search.gif" border="0" alt="Search recipe" align="absmiddle">
<input type="text" name="find" id="find" class="textboxsearch" size="20" value="Find..." onfocus="if(this.value=='Find...')value='';" onblur="if(this.value=='')value='Find...';" runat="server"> in 
<asp:dropdownlist id="SDropName" runat="server" cssClass="ddl" AutoPostBack="false"></asp:dropdownlist>
<asp:Button ID="Sbuton" runat="server" cssClass="submitadmin" OnClick="SearchButton_Click" Text="Search" />
</div>
</div>
</div>
<!--End Search-->
