<%@ Control Language="C#" AutoEventWireup="true" CodeFile="newesttoprecipes.ascx.cs" Inherits="newesttoprecipes" EnableViewState="false"%>

<div id="MyCookBookSideMenuContainer" runat="server" style="margin-bottom: 20px;">
<div class="toproundbluesidemenu">
<div class="toproundbluesidemenuheader"><span class="content3">My CookBook Quick View</span></div>
</div>
<div class="contentdisplay">
<div class="contentdis5">
<asp:Label runat="server" id="lblcounter" CssClass="content2" EnableViewState="false" />
<br />
<asp:Repeater id="MyCookBookSideMenuRepeater" runat="server" EnableViewState="false">
   <ItemTemplate>
<div style="margin-top: 3px; margin-bottom: 3px;">
<span class="cyel">&raquo;</span>&nbsp;<a class="content12" title="View <%# Eval("RecipeName") %> recipe" onmouseover="Tip('<b>Recipe Name:</b> <%# Eval("RecipeName") %><br><b>Author:</b> <%# Eval("Author") %><br><b>Category:</b> <%# Eval("Category") %><br><b>Hits:</b> (<%# Eval("Hits") %>)<br><b>Rating:</b> (<%# Eval("Rating") %>)<br><b>Comments:</b> (<%# Eval("Comments") %>)<br>Added to the CookBook on: <%# Eval("Date", "{0:M/d/yyyy}")%><br><b>Photo:</b><br><img src=&quot;RecipeImageUpload/<%# Eval("RecipeImage")%>&quot; width=&quot;150&quot; height=&quot;120&quot;>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" href='<%# Eval("RecipeID", "recipedetail.aspx?id={0}") %>'><%# Eval("RecipeName")%></a>
</div>
   </ItemTemplate>
  </asp:Repeater>
</div>
</div>
</div>
<!--Begin Random Recipe-->
<div class="toproundbluesidemenu">
<div class="toproundbluesidemenuheader"><span class="content3">Featured <asp:Label ID="lblrancatname" cssClass="content3" runat="server" EnableViewState="false" />Recipe</span></div>
</div>
<div class="contentdisplayblue">
<div class="contentdis5">
<span class="bluearrow">&raquo;</span>
<asp:HyperLink id="LinkRanName" cssClass="dt" runat="server" EnableViewState="false" />
<br />
<span class="content8">Category:</span> <asp:HyperLink id="LinkRanCat" cssClass="dt2" runat="server" EnableViewState="false" />
<br />
<span class="content8">Rating:&nbsp;<asp:Image id="ranrateimage" runat="server" ImageAlign="absmiddle" EnableViewState="false" />&nbsp;votes&nbsp;<asp:Label cssClass="cgr" runat="server" id="lblvotes" EnableViewState="false" /></span>
<br />
<span class="content8">Hits:</span> <asp:Label cssClass="cmaron2" runat="server" id="lblranhits" EnableViewState="false" />
</div>
</div>	
<!--End Random Recipe-->
<br />
<!--Begin 15 Newest Recipes-->
<div class="toproundgreen">
<div class="toproundgreenheader"><span class="content3"><asp:Label ID="lbTopCnt" cssClass="content3" runat="server" EnableViewState="false" />&nbsp;<asp:Label ID="lblcatname" cssClass="content3" runat="server" EnableViewState="false" />Newest Recipes</span></div>
</div>
<div class="contentdisplaygreen">
<div class="contentbggreen">
<asp:Repeater id="RecipeNew" runat="server" OnItemDataBound="RecipeNew_ItemDataBound" EnableViewState="false">
   <ItemTemplate>
<div class="dcnt2">
<span class="arrowgr">&raquo;</span>
<a class="dt" onmouseover="Tip('<b>Category: </b><%# Eval("Category") %><br><b>Hits: </b><%# Eval("Hits") %><b><br>Date Added: </b><%# Eval("Date", "{0:M/d/yyyy}")%><br><b>Photo:</b><br><img src=&quot;RecipeImageUpload/<%# Eval("RecipeImage")%>&quot; width=&quot;150&quot; height=&quot;120&quot;>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" href='<%# Eval("ID", "recipedetail.aspx?id={0}") %>'>
<%# Eval("RecipeName") %></a>
<br />
<span class="content2"><asp:Label cssClass="content2" ID="lblgetdate" runat="server" EnableViewState="false" /></span>
</div>
      </ItemTemplate>
  </asp:Repeater>
</div>
</div>
<!--End 15 Newest Recipes-->
<br />
<!--Begin 15 Most Popular-->
<div class="toproundgreen">
<div class="toproundgreenheader"><span class="content3"><asp:Label ID="lblcatnamepop" cssClass="content3" runat="server" EnableViewState="false" /><asp:Label ID="lblpopcounter" cssClass="content3" runat="server" EnableViewState="false" /></span></div>
</div>
<div class="contentdisplaygreen">
<div class="contentbggreen">
<asp:Repeater id="TopRecipe" runat="server" OnItemDataBound="TopRecipe_ItemDataBound" EnableViewState="false">
   <ItemTemplate>
<div class="dcnt2">
<asp:Label ID="lbseqnumber" cssClass="cyel2" runat="server" EnableViewState="false" /> <a class="dt" onmouseover="Tip('<b>Category: </b><%# Eval("Category") %><br><b>Hits: </b><%# Eval("Hits") %><br><b>Photo:</b><br><img src=&quot;RecipeImageUpload/<%# Eval("RecipeImage")%>&quot; width=&quot;150&quot; height=&quot;120&quot;>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" href='<%# Eval("ID", "recipedetail.aspx?id={0}") %>'>
<%# Eval("RecipeName")%></a>
</div>
      </ItemTemplate>
  </asp:Repeater>
</div>
</div>
<!--End 15 Most Popular-->
