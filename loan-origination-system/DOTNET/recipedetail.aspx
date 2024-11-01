<%@ Page Language="C#" MasterPageFile="~/SiteTemplate.master" EnableViewState="false" AutoEventWireup="true" CodeFile="recipedetail.aspx.cs" Inherits="recipedetail" Title="Untitled Page" %>
<%@ Register TagPrefix="ucl" TagName="alphaletter" Src="Control/alphaletter.ascx" %>
<%@ Register TagPrefix="ucl" TagName="categorylistsidemenu" Src="Control/categorylistsidemenu.ascx" %>
<%@ Register TagPrefix="ucl" TagName="sidemenu" Src="Control/sidemenu.ascx" %>
<%@ Register TagPrefix="ucl" TagName="searchtab" Src="Control/searchtab.ascx" %>

<asp:Content ID="Content1" ContentPlaceHolderID="LeftPanel" Runat="Server">
    <ucl:sidemenu id="menu1" runat="server"></ucl:sidemenu>
    <div style="clear: both;"></div>
    <ucl:categorylistsidemenu id="catlistcont" runat="server"></ucl:categorylistsidemenu>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <ucl:searchtab id="searchcont" runat="server"></ucl:searchtab>
    <div style="text-align: left; margin-left: 10px; margin-right: 12px; background-color: #FFF9EC; margin-bottom: 10px;">&nbsp;
                            <a class="dsort" title="Back to recipe homepage" href="default.aspx">Home</a>
                           <span class="bluearrow">�</span>
                         <a class="dsort" href="category.aspx?catid=<%=RecCatId%>" title="Browse <%=strCName%> category"><%=strCName%></a>
                       <span class="bluearrow">�</span>&nbsp;<span class="content10"><%=strRName%></span>
                      </div>
    <div style="padding: 2px; margin-bottom: 14px; margin-top: 12px; margin-left: 16px; margin-right: 6px;">
    <ucl:alphaletter id="alpha1" runat="server"></ucl:alphaletter>
    </div>
    <!--Begin header User option-->
    <asp:PlaceHolder id="PlaceHolder1" runat="server">
    <div style="margin-left: 10px; margin-right: 10px;">
    <div class="divheaddetail">
    <img src="images/tlcorner.gif" alt="" align="top">
    <asp:Image ImageUrl="images/save_icon.gif" ID="saveicon" ImageAlign="AbsMiddle" runat="server" />&nbsp;<asp:Label runat="server" CssClass="content12" id="addtoCookBook" EnableViewState="false" /><asp:LinkButton id="LinkButtonAddtoCookBookLogin" CausesValidation="false" runat="server" CssClass="content12" Visible="false" OnClick="Add_CookBook" EnableViewState="false" />&nbsp;&nbsp;
    <asp:Image ID="CommentImg" ImageUrl="images/discuss_icon.gif" AlternateText="Discuss <%=strRName%> recipe" ImageAlign="AbsMiddle" runat="server" />&nbsp;<asp:HyperLink ID="CommentLink" NavigateUrl="#DIS" ToolTip="Comment this recipe" runat="server" cssClass="dt" />&nbsp;&nbsp;
    <img src="images/print_icon.gif" align="absmiddle" alt="Print <%=strRName%> recipe"> 
    <a class="thickbox dt" title="Print <%=strRName%> recipe" href="printrecipe.aspx?id=<%=Request.QueryString["id"]%>&keepThis=true&TB_iframe=true&height=600&width=750" onmouseover="Tip('Print <%=strRName%> recipe.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">Print this recipe</a>&nbsp;&nbsp;
    <img src="images/email_icon.gif" align="absmiddle" alt="Email <%=strRName%> recipe to friend"> 
    <a class="thickbox dt" title="Email <%=strRName%> recipe to friend" href="emailrecipe.aspx?id=<%=Request.QueryString["id"]%>&amp;n=<%=strRName%>&c=<%=strCName%>&keepThis=true&TB_iframe=true&height=220&width=400" onmouseover="Tip('Email <%=strRName%> recipe to a friend.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">Email this recipe</a>&nbsp;&nbsp;
    <img src="images/save_icon.gif" align="absmiddle" alt="Save/Add <%=strRName%> recipe to your favorite">
    <a class="dt" title="Bookmark <%=strRName%> recipe to the browser" href="javascript:bookmark('<%=strRName%> Recipe', '<%=strBookmarkURL%>')" onmouseover="Tip('Bookmark <%=strRName%> recipe into your browser.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">Bookmark to browser</a>
    </div>
    <!--End header User option-->
    <table cellpadding="0" cellspacing="0" width="100%">
      <tr>
        <td valign="top" align="left" width="35%">
    <div style="background: #fff url(images/rbg1.gif) repeat-x;">
    <div style="padding-top: 8px;">
    &nbsp;&nbsp;<asp:Label cssClass="cmaron4" runat="server" id="lblname" EnableViewState="false" /> <asp:Label runat="server" id="lblpopular" cssClass="hot" EnableViewState="false" /> <asp:Image id="thumbsup" runat="server" AlternateText = "Thumsb up" EnableViewState="false" /><asp:Image id="newimg" runat="server" AlternateText = "New image" EnableViewState="false" />
    </div>
    <div>
    &nbsp;&nbsp;<span class="content2">Category:</span>
    <a class="dt" href="category.aspx?catid=<%=RecCatId%>" title="Browse <%=strCName%> category" onmouseover="Tip('Browse <%=strCName%> recipe.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()"><%=strCName%></a>&nbsp;&nbsp;<asp:Label cssClass="content2" runat="server" id="lbluid" EnableViewState="false" />
    </div>
    <div>
    &nbsp;&nbsp;<span class="content2">By:</span>
    <img src="images/user-icon.gif" />&nbsp;<asp:Label runat="server" id="lblauthor" cssClass="content2" EnableViewState="false" />&nbsp;&nbsp;<asp:HyperLink CssClass="content2" ID="editrecipelink" Visible="false" runat="server" EnableViewState="false" />
    </div>
    <div>
    &nbsp;&nbsp;<span class="content2">Date Posted:</span>
    <asp:Label runat="server" id="lbldate" cssClass="cyel" EnableViewState="false" />
    </div>
    <div>
    &nbsp;&nbsp;<span class="content2">Hits:</span>
    <asp:Label ID="lblhits" runat="server" cssClass="cmaron3" EnableViewState="false" />
    </div>
    <div style="margin-bottom: 1px;">
    &nbsp;&nbsp;<span class="content2">Rating:&nbsp;<asp:Image id="starimage" runat="server" />&nbsp;(<asp:Label cssClass="cgr" runat="server" id="lblrating" EnableViewState="false" />) votes <asp:Label cssClass="cyel" runat="server" id="lblvotescount" EnableViewState="false" /></span> 
    </div>
    <div style="margin-bottom: 16px;">
    <asp:Panel ID="Panel2" runat="server" Height="50px" Width="220px">
    &nbsp;&nbsp;<b><span id="link<%=Request.QueryString["id"]%>" class="cgr">Rate this recipe</span></b>
    <ul class="srating">
    <li><a href="#" onmouseover="document.getElementById('link<%=Request.QueryString["id"]%>').innerHTML='Poor - 1 star'"  onmouseout="document.getElementById('link<%=Request.QueryString["id"]%>').innerHTML='Rate this recipe'" onclick="javascript:top.document.location.href='rate.aspx?id=<%=Request.QueryString["id"]%>&amp;rateval=1&amp;wp=<%=RecipeSection%>';" title='Rate recipe: Not sure - 1 star' class='onestar'>1</a></li>
    <li><a href="#" onmouseover="document.getElementById('link<%=Request.QueryString["id"]%>').innerHTML='Fair - 2 stars'" onmouseout="document.getElementById('link<%=Request.QueryString["id"]%>').innerHTML='Rate this recipe'" onclick="javascript:top.document.location.href='rate.aspx?id=<%=Request.QueryString["id"]%>&amp;rateval=2&amp;wp=<%=RecipeSection%>';" title='Rate recipe: Fair - 2 stars' class='twostars'>2</a></li>
    <li><a href="#" onmouseover="document.getElementById('link<%=Request.QueryString["id"]%>').innerHTML='Interesting - 3 stars'" onmouseout="document.getElementById('link<%=Request.QueryString["id"]%>').innerHTML='Rate this recipe'" onclick="javascript:top.document.location.href='rate.aspx?id=<%=Request.QueryString["id"]%>&amp;rateval=3&amp;wp=<%=RecipeSection%>';" title='Rate recipe: Interesting - 3 stars' class='threestars'>3</a></li>
    <li><a href="#" onmouseover="document.getElementById('link<%=Request.QueryString["id"]%>').innerHTML='Good - 4 stars'" onmouseout="document.getElementById('link<%=Request.QueryString["id"]%>').innerHTML='Rate this recipe'" onclick="javascript:top.document.location.href='rate.aspx?id=<%=Request.QueryString["id"]%>&amp;rateval=4&amp;wp=<%=RecipeSection%>';" title='Rate recipe: Very good - 4 stars' class='fourstars'>4</a></li>
    <li><a href="#" onmouseover="document.getElementById('link<%=Request.QueryString["id"]%>').innerHTML='Excellent - 5 stars'" onmouseout="document.getElementById('link<%=Request.QueryString["id"]%>').innerHTML='Rate this recipe'" onclick="javascript:top.document.location.href='rate.aspx?id=<%=Request.QueryString["id"]%>&amp;rateval=5&amp;wp=<%=RecipeSection%>';" title='Rate recipe: Excellent - 5 stars' class='fivestars'>5</a></li>
    </ul>
    </asp:Panel>
     </div>
    </div>
    </td>
        <td valign="top" align="left" width="65%" style="background: #fff url(images/rbg1.gif) repeat-x;">
     <div style="background: #fff url(images/rbg1.gif) repeat-x;">
     <asp:Image ID="recipeimage" CssClass="recipeimage" BorderColor="#A0BEE2" BorderWidth="1" Width="150" Height="120" runat="server"/>
     </div>
    </td>
      </tr>
    </table>
    <div style="margin: 6px;">
     <fieldset><legend>Ingredients</legend>
     <div style="padding-top: 12px; padding-right: 12px; line-height: 20px;">
      <asp:Label cssClass="drecipe" ID="lblIngredients" runat="server" EnableViewState="false" />
     </div>
    </fieldset>
    </div>
    <div style="margin: 6px;">
     <fieldset><legend>Instructions</legend>
      <div style="padding-top: 12px; padding-right: 12px; line-height: 20px;">
      <asp:Label cssClass="drecipe" ID="lblInstructions" runat="server" EnableViewState="false" />
     </div>
    </fieldset>
    </div>
    <div style="margin-left: 6px; margin-right: 6px;  margin-bottom: 22px;">
    <fieldset><legend>Other&nbsp;<asp:Label runat="server" id="lblcategorytop" EnableViewState="false" />&nbsp;recipes you might be interested</legend>
    <div style="margin-top: 6px;">
       <asp:Repeater id="RelatedRecipes" runat="server" EnableViewState="false">
       <ItemTemplate>
    <span class="ora2">&raquo;</span>
    <a class="dt" title="Category (<%# Eval("Category")%>) - Hits (<%# Eval("Hits")%>)" onmouseover="Tip('<b>Category: </b><%# Eval("Category") %><br><b>Hits: </b><%# Eval("Hits") %><br><b>Photo:</b><br><img src=&quot;RecipeImageUpload/<%# Eval("RecipeImage")%>&quot; width=&quot;150&quot; height=&quot;120&quot;>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" href='<%# Eval("ID", "recipedetail.aspx?id={0}") %>'>
    <%# Eval("RecipeName") %></a>
    <br />
          </ItemTemplate>
      </asp:Repeater>
    </div>
    </fieldset>
    </div>
    </div>
    </asp:PlaceHolder>
    <asp:Panel ID="Panel1" runat="server">
    <!--Begin Display Comments-->
    <div style="margin-left: 40px; margin-right: 40px; margin-top: 20px;">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
      <tr>
        <td width="100%" height="18" BgColor="#F4F9FF" style="-moz-border-radius: 8px; border-radius: px;"><span class="content6">There are&nbsp;(<asp:Label id="lbcountcomment" cssClass="content6" runat="server" EnableViewState="false" />)&nbsp;comments</span></td>
      </tr>
      <tr>
        <td width="100%">
    <asp:Repeater id="RecComments" runat="server" EnableViewState="false">
          <ItemTemplate>
        <div class="divwrap2" style="-moz-border-radius: 8px; border-radius: px;">
    <div class="divbd6" style="padding: 6px;">
    Comment on <%# CustomDateFormat(Eval("Date"))%> by <img src="images/user-icon.gif" />&nbsp;<a class="content2" title="View <%# Eval("Author") %> profile." href='<%# Eval("UID", "userprofile.aspx?uid={0}") %>'><%# Eval("Author") %></a>
    <div style="margin-top: 6px;">
    <%# Eval("Comments")%>
         </div>
        </div>
       </div>
     </ItemTemplate>
    </asp:Repeater>
    </td>
      </tr>
    </table>
    </div>
    <!--End Display Comments-->
    <!--Begin Comment Field-->
    <div style="margin-left: 40px; margin-right: 40px;">
    <fieldset><legend>Write a Comment</legend>
    <table border="0" cellpadding="2" cellspacing="2" width="60%">
      <tr>
        <td width="100%" colspan="2"><a style="text-decoration: none; color: #336699;" name="DIS"></a>
        <asp:Label ID="lbllogintocomment" runat="server" cssClass="content12" Visible="false" EnableViewState="false" />
        <asp:Panel ID="Panel3" runat="server">
    <asp:Label ID="lbvalenght" runat="server" Font-Bold="True" ForeColor="#C00000" Font-Names="Verdana" Visible="false" />
    <br />
    <span class="content12">Field mark with red asterisk(<span class="cred3">*</span>) is required.</span></td>
      </tr>
      <tr>
        <td width="21%" class="content2"><span class="content12">Username:</span></td>
        <td width="79%">
    <input type="hidden" id="AUTHOR" name="AUTHOR" Class="textbox" runat="server" />
    <asp:Label ID="lblUsernameComment" runat="server" cssClass="content12" EnableViewState="false" />
    </td>
      </tr>
      <tr>
        <td width="21%" class="content2"><span class="content12">Email:</span></td>
        <td width="79%">
    <input type="hidden" id="EMAIL" name="EMAIL" Class="textbox" runat="server" />
    <asp:Label ID="lbluserCommentEmail" runat="server" cssClass="content12" EnableViewState="false" />
    </td>
      </tr>
      <tr>
        <td width="21%" valign="top" class="content2"><span class="content12">Comment:</span><span class="cred2">*</span>
    <br />
    <br />
    <span class="catcntsml">Only 350 char allowed</span>
    </td>
        <td width="79%">
          <asp:RequiredFieldValidator runat="server"
          id="RequiredFieldComment" ControlToValidate="COMMENTS" SetFocusOnError="true"
          cssClass="cred2"
          ValidationGroup="CommentGroup"
          ErrorMessage = "Comment is blank"
          display="Dynamic"> </asp:RequiredFieldValidator>
        <textarea id="COMMENTS" name="COMMENTS" Class="textbox" cols="55" rows="7" onKeyDown="textCounter(350);" onKeyUp="textCounter(350);" onFocus="this.style.backgroundColor='#FFF9EC'" onBlur="this.style.backgroundColor='#ffffff'"  runat="server" /> 
    <input class="textbox" type="text" name="remLen" id="remLem" size="3" maxlength="3" value="350" readonly> <span class="catcntsml">Char count</span>
    <br />

    <asp:Label cssClass="cred2" runat="server" id="lblcomcharlimit" visible="false" />
    <input type="hidden" value="<%=Request.QueryString["id"]%>" ID="ID" name="ID">
    <input type="text" class="textbox" ID="hd" name="hd" runat="server" style="visibility:hidden;">
    <br />
    <span class="content2">Security Code:</span>&nbsp;<asp:RequiredFieldValidator runat="server"
          id="RequiredFieldSecCode" ControlToValidate="txtsecfield" SetFocusOnError="true"
          cssClass="cred2"
          ValidationGroup="CommentGroup"
          ErrorMessage = "Security Code is blank"
          display="Dynamic"> </asp:RequiredFieldValidator>
    <br />
    <img height="30" alt="" src="imgsecuritycode.aspx" width="80"> 
    <br />
      <asp:Label ID="lblinvalidsecode" cssClass="cred2" runat="server" visible="false" />
     <asp:TextBox ID="txtsecfield" CssClass="textbox" runat="server" Width="70"></asp:TextBox>
    <br />
<asp:ValidationSummary
ID="ValidationSummary1"
runat="server"
EnableClientScript="true"
ValidationGroup="CommentGroup"
ShowMessageBox="true"
ShowSummary="false"
HeaderText="The following fields need attention:" />
<br />
    <asp:Button runat="server" Text="Submit" id="AddComments" cssClass="submitadmin" OnClick="Add_Comment" ValidationGroup="CommentGroup" />
    </asp:Panel>
          </td>
        </tr>
       </table>
    </fieldset>
    </div>
    <asp:Literal id="JSLiteral" runat="server"></asp:Literal>
    <!--End Comment Field-->
    </asp:Panel>
</asp:Content>

