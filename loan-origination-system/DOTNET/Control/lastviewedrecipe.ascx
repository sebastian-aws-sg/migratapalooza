<%@ Control Language="C#" AutoEventWireup="true" CodeFile="lastviewedrecipe.ascx.cs" Inherits="Controllastviewedrecipe" EnableViewState="false"%>
    <div class="hpright">
    <span class="hdgr">Most Viewed in the Last <asp:Label cssClass="hdgr" runat="server" id="lbgethour" /> Hours</span>
    <br />
    <asp:Repeater id="lastview" runat="server" OnItemDataBound="lastview_ItemDataBound" EnableViewState="false">
   <ItemTemplate>
<div class="dcnt2">
<asp:Label ID="lbseqnumber" cssClass="cgr2" runat="server"></asp:Label> <a class="dt" onmouseover="Tip('<b>Category: </b><%# Eval("Category") %><br><b>Hits: </b><%# Eval("Hits") %><br><b>Last Viewed:</b><br>(<%# Eval("Hours") %> hr(s)., <%# Eval("Minutes") %> min.) ago.<br><b>Photo:</b><br><img src=&quot;RecipeImageUpload/<%# Eval("RecipeImage")%>&quot; width=&quot;150&quot; height=&quot;120&quot;>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" href='<%# Eval("ID", "recipedetail.aspx?id={0}") %>'>
<%# Eval("RecipeName") %></a>
<br />
</div>
      </ItemTemplate>
  </asp:Repeater>
  </div>
