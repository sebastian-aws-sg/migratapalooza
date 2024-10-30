<%@ Page Language="C#" MasterPageFile="~/SiteTemplate2.master" EnableViewState="false" AutoEventWireup="true" CodeFile="mycookbook.aspx.cs" Inherits="MyCookBook" Title="Untitled Page" %>
<%@ Register TagPrefix="ucl" TagName="sidemenu" Src="Control/sidemenu.ascx" %>
<%@ Register TagPrefix="ucl" TagName="searchtab" Src="Control/searchtab.ascx" %>

<asp:Content ID="Content1" ContentPlaceHolderID="LeftPanel" Runat="Server">
<ucl:sidemenu id="menu1" runat="server"></ucl:sidemenu>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
<ucl:searchtab id="searchcont" runat="server"></ucl:searchtab>
    <div style="margin-left: 10px; margin-bottom: 12px; margin-right: 12px; background-color: #FFF9EC; margin-top: 0px;">
    &nbsp;&nbsp;<a href="default.aspx" class="dsort" title="Back to recipe homepage">Home</a>&nbsp;<span class="bluearrow">»</span>&nbsp; <span class="content2">You are here: My CookBook</span>
    </div> 
    <div style="margin-left: 15px;">   
    <table border="0" cellpadding="2" align="left" cellspacing="2" width="75%">
      <tr>
    <td width="68%">
    <fieldset><legend><span class="sortcat">Sort Option:</span>&nbsp;<span class="content2">
<asp:HyperLink id="SortLinkRecipeName" cssClass="dsort" runat="server" />&nbsp;<asp:Image id="ArrowImage2" runat="server" />&nbsp;|&nbsp;
<asp:HyperLink id="SortLinkHits" cssClass="dsort" runat="server" />&nbsp;<asp:Image id="ArrowImage1" runat="server" />&nbsp;|&nbsp;
<asp:HyperLink id="SortLinkRating" cssClass="dsort" runat="server" />&nbsp;<asp:Image id="ArrowImage4" runat="server" />&nbsp;|&nbsp;
<asp:HyperLink id="SortLinkDateAdded" cssClass="dsort" runat="server" />&nbsp;<asp:Image id="ArrowImage5" runat="server" />
</span></legend>
     <div style="padding-top: 8px;">
     <asp:Label runat="server" id="lblyouarenotlogin" Visible="false" CssClass="content12" EnableViewState="false" />
     <asp:Panel ID="HideContentIfNotLogin" runat="server">
     <div style="margin-top: 5px; margin-bottom: 16px;">
         <div style="margin-left: 5px; margin-bottom: 12px;">
         <asp:Label runat="server" style="font-family:Verdana, Arial; color: #336699; font-size: 14px; font-weight:bold;" id="lblusernameheader" EnableViewState="false" /><br />
         </div>
         <div>
         <asp:Label runat="server" id="lblcounter" CssClass="content12" EnableViewState="false" />
         </div>
     </div>
        <asp:Label runat="server" CssClass="content2" id="lblnosavedrecipe" EnableViewState="false" />
        <asp:Repeater id="SavedRecipeInCookBook" runat="server" OnItemDataBound="SavedRecipeInCookBook_ItemDataBound">
       <ItemTemplate>
        <div class="dcnt2" style="margin-top: 6px;">
        <asp:Label ID="lblDelete" runat="server" CssClass="thickbox" EnableViewState="false" />&nbsp;&nbsp;<a href="emailrecipe.aspx?id=<%# Eval("itemID") %>&n=<%# Eval("RecipeName") %>&c=<%# Eval("Category") %>&keepThis=true&TB_iframe=true&height=220&width=400" class="thickbox" title="Sharing recipe" onmouseover="Tip('Share <b><%# Eval("RecipeName")%></b> recipe to a friend.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()"><img src="images/send_icon.gif" alt="Email recipe" border="0" align="absmiddle" /></a>&nbsp;&nbsp;
        <a href='<%# Eval("RecipeID", "recipedetail.aspx?id={0}") %>' onmouseover="Tip('View <b><%# Eval("RecipeName")%></b> recipe in details page.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()"><img src="images/detail_icon.gif" alt="view detail page" align="absmiddle" border="0" /></a>&nbsp;&nbsp;<a class="thickbox content12" title="Viewing recipe" onmouseover="Tip('<b>Recipe Name:</b> <%# Eval("RecipeName") %><br><b>Author:</b> <%# Eval("Author") %><br><b>Category:</b> <%# Eval("Category") %><br><b>Hits:</b> (<%# Eval("Hits") %>)<br><b>Rating:</b> (<%# Eval("Rating") %>)<br><b>Comments:</b> (<%# Eval("Comments") %>)<br>Added to the CookBook on: <%# Eval("Date", "{0:M/d/yyyy}")%><br><b>Photo:</b><br><img src=&quot;RecipeImageUpload/<%# Eval("RecipeImage")%>&quot; width=&quot;150&quot; height=&quot;120&quot;>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" href='printrecipe.aspx?id=<%# Eval("RecipeID") %>&keepThis=true&TB_iframe=true&height=600&width=750'><%# Eval("RecipeName")%></a>
        </div>
         
          <div id="confirmModal<%# Eval("itemID")%>" style="display:none;">
            <div class="confirm">
                <div class="message">
                 <img src="images/icon_confirm.gif" alt="confirm icon" style="float: left; margin-right: 10px;" /> Are you sure you want to remove <span style="color:#266B91"><b><%# Eval("RecipeName") %></b></span> recipe from your CookBook?
                </div>
                <div class="commands" style="text-align: center;">
                    <input type="button" value="Yes" class="submitpopupmodal" onclick="sendRequestDeleteUserRecipeInCookBook('<%# Eval("itemID")%>')">&nbsp;&nbsp;<input type="button" value="No" class="submitpopupmodal" onclick="tb_remove(); return false;">
                </div>
            </div>
        </div>
        
       </ItemTemplate>
      </asp:Repeater>
      </asp:Panel>
     </div>
    <!--Begin Record count,page count and paging link-->
    <div style="margin-left: 4px; margin-top: 22px;">
    <asp:label ID="lblRecpage"
      Runat="server"
      cssClass="content2" EnableViewState="false" />
    <div style="margin-top: 10px;">
    <asp:Label cssClass="content2" id="lbPagerLink" runat="server" Font-Bold="True" EnableViewState="false" />
    </div>
    </div>
    <!--End Record count,page count and paging link-->
    </fieldset>
    </td>
      </tr>
    </table>
    </div>
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
        <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
</asp:Content>

