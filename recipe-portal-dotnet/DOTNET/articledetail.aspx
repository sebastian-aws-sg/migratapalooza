<%@ Page Language="C#" MasterPageFile="~/SiteTemplate2.master" EnableViewState="false" AutoEventWireup="true" CodeFile="articledetail.aspx.cs" Inherits="articledetail" Title="Untitled Page" %>
<%@ Register TagPrefix="ucl" TagName="rsssidemenu" Src="Control/rsssidemenu.ascx" %>
<%@ Register TagPrefix="ucl" TagName="sidemenu" Src="Control/sidemenu.ascx" %>
<%@ Register TagPrefix="ucl" TagName="newestarticle" Src="Control/newestarticle.ascx" %>
<%@ Register TagPrefix="ucl" TagName="articategorylistsidemenu" Src="Control/articategorylistsidemenu.ascx" %>
<%@ Register TagPrefix="ucl" TagName="articlesearchtab" Src="Control/articlesearchtab.ascx" %>

<asp:Content ID="Content1" ContentPlaceHolderID="LeftPanel" Runat="Server">
    <ucl:sidemenu id="menu1" runat="server"></ucl:sidemenu>
    <div style="clear: both;"></div>
    <ucl:newestarticle id="nart" runat="server"></ucl:newestarticle>
    <br />
    <ucl:articategorylistsidemenu id="artcat" runat="server"></ucl:articategorylistsidemenu>
    <br />
    <ucl:rsssidemenu id="rsscont" runat="server"></ucl:rsssidemenu>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <ucl:articlesearchtab id="searchcont" runat="server"></ucl:articlesearchtab>
    <div style="text-align: left; margin-left: 10px; margin-right: 12px; background-color: #FFF9EC; margin-bottom: 10px;">&nbsp;
                            <a class="dsort" title="Back to recipe homepage" href="default.aspx">Home</a>
                           <span class="bluearrow">»</span>
                         <a class="dsort" href="articlecategory.aspx?&catid=<%=ArtCatId%>" title="Browse <%=strCatName%> cooking article category"><%=strCatName%></a>
                      </div>                
    <asp:PlaceHolder id="PlaceHolder2" runat="server">
    <div style="margin-left: 25px; margin-right: 25px; margin-top: 25px;">
        <h1><asp:Label ID="lbtitle" runat="server"></asp:Label></h1>
        <div style="margin-bottom: 2px; margin-top: 3px;">
        <span class="content2">By&nbsp;<img src="images/user-icon.gif" />&nbsp;<asp:Label ID="lbauthor" CssClass="content2" runat="server" />&nbsp;&nbsp;Date Posted:&nbsp;<asp:Label runat="server" id="lbldate" cssClass="cyel" />&nbsp;&nbsp;Hits:&nbsp;<asp:Label ID="lbhits" CssClass="cmaron3" runat="server" />
        &nbsp;&nbsp;Rating:&nbsp;<asp:Image id="starimage" runat="server" />&nbsp;(<asp:Label cssClass="cgr" runat="server" id="lblrating" />) votes <asp:Label cssClass="cyel" runat="server" id="lblvotescount" />&nbsp;&nbsp;Word Count:&nbsp;<asp:Label ID="lblwordcount" CssClass="cmaron3" runat="server" /></span>
        </div>
        <div>
        <img src="images/save_icon.gif" align="absmiddle" alt="Save/Add <%=strArtTitle%> recipe to your favorite"> 
    <a class="dt" title="Save/Add <%=strArtTitle%> article to favorite" href="javascript:bookmark('<%=strArtTitle%> Article', '<%=strBookmarkURL%>')">Save to favorite</a>&nbsp;&nbsp;<asp:Image ID="CommentImg" ImageUrl="images/discuss_icon.gif" AlternateText="Discuss <%=strArtTitle%> article" ImageAlign="AbsMiddle" runat="server" />&nbsp;<asp:HyperLink ID="CommentLink" NavigateUrl="#DIS" ToolTip="Comment this article" runat="server" cssClass="dt" />&nbsp;&nbsp;<img src="images/print_icon.gif" align="absmiddle" alt="Print <%=strArtTitle%> recipe"> 
    <a class="thickbox dt" title="Print <%=strArtTitle%> article" href="printarticle.aspx?aid=<%=Request.QueryString["aid"]%>&keepThis=true&TB_iframe=true&height=600&width=750">Print this article</a>&nbsp;&nbsp;
    <img src="images/email_icon.gif" align="absmiddle" alt="Email <%=strArtTitle%> article to a friend"> 
    <a class="thickbox dt" title="Email <%=strArtTitle%> recipe to friend" href="emailarticle.aspx?aid=<%=Request.QueryString["aid"]%>&amp;n=<%=strArtTitle%>&keepThis=true&TB_iframe=true&height=220&width=400">Email this article</a>&nbsp;&nbsp;&nbsp;<asp:HyperLink CssClass="dt" ID="editarticlelink" Visible="false" runat="server" EnableViewState="false" />
        </div>
        <div style="margin-bottom: 7px;">
    <asp:Panel ID="Panel2" runat="server" Height="50px" Width="220px">
    &nbsp;&nbsp;<b><span id="link<%=Request.QueryString["aid"]%>" class="cgr">Rate this article</span></b>
    <ul class="srating">
    <li><a href="#" onmouseover="document.getElementById('link<%=Request.QueryString["aid"]%>').innerHTML='Poor - 1 star'"  onmouseout="document.getElementById('link<%=Request.QueryString["aid"]%>').innerHTML='Rate this article'" onclick="javascript:top.document.location.href='rate.aspx?id=<%=Request.QueryString["aid"]%>&amp;rateval=1&amp;wp=<%=ArticleSection%>';" title='Rate article: Not sure - 1 star' class='onestar'>1</a></li>
    <li><a href="#" onmouseover="document.getElementById('link<%=Request.QueryString["aid"]%>').innerHTML='Fair - 2 stars'" onmouseout="document.getElementById('link<%=Request.QueryString["aid"]%>').innerHTML='Rate this article'" onclick="javascript:top.document.location.href='rate.aspx?id=<%=Request.QueryString["aid"]%>&amp;rateval=2&amp;wp=<%=ArticleSection%>';" title='Rate article: Fair - 2 stars' class='twostars'>2</a></li>
    <li><a href="#" onmouseover="document.getElementById('link<%=Request.QueryString["aid"]%>').innerHTML='Interesting - 3 stars'" onmouseout="document.getElementById('link<%=Request.QueryString["aid"]%>').innerHTML='Rate this article'" onclick="javascript:top.document.location.href='rate.aspx?id=<%=Request.QueryString["aid"]%>&amp;rateval=3&amp;wp=<%=ArticleSection%>';" title='Rate article: Interesting - 3 stars' class='threestars'>3</a></li>
    <li><a href="#" onmouseover="document.getElementById('link<%=Request.QueryString["aid"]%>').innerHTML='Good - 4 stars'" onmouseout="document.getElementById('link<%=Request.QueryString["aid"]%>').innerHTML='Rate this article'" onclick="javascript:top.document.location.href='rate.aspx?id=<%=Request.QueryString["aid"]%>&amp;rateval=4&amp;wp=<%=ArticleSection%>';" title='Rate article: Very good - 4 stars' class='fourstars'>4</a></li>
    <li><a href="#" onmouseover="document.getElementById('link<%=Request.QueryString["aid"]%>').innerHTML='Excellent - 5 stars'" onmouseout="document.getElementById('link<%=Request.QueryString["aid"]%>').innerHTML='Rate this article'" onclick="javascript:top.document.location.href='rate.aspx?id=<%=Request.QueryString["aid"]%>&amp;rateval=5&amp;wp=<%=ArticleSection%>';" title='Rate article: Excellent - 5 stars' class='fivestars'>5</a></li>
    </ul>
    </asp:Panel>
     </div>
        <div style="margin-top: 8px; padding-top: 12px; margin-right: 50px;">
            <asp:Label ID="lbcontent" runat="server"></asp:Label>
        </div>
       <div style="clear:both"><br /></div>
        <div style="width: 750px;">
                 <div style="width: 350px; float:left;">
                 <asp:Panel ID="PanelOtherArticleByAuthor" Visible="false" runat="server">               
                      <fieldset><legend><span class="sortcat">Other Article by <a href="findallarticlebyauthor.aspx?author=<%=strAuthor%>" onmouseover="Tip('View all article publish by <b><%=strAuthor%></b>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()"><%=strAuthor%></a></span></legend>
                        <div style="padding-right: 8px; padding-left: 5px; padding-top: 6px; line-height: 18px;">
                    <asp:Repeater id="OtherArticleByThisAuthor" runat="server">     
                    <ItemTemplate>
                        <div class="dcnt2" style="margin-top: 2px;">
                         <span class="cyel">&raquo;</span>&nbsp;<a class="content12" href="articledetail.aspx?aid=<%# Eval("ID")%>" onmouseover="Tip('<b>Category:</b> <%# Eval("Category")%><br><b>Hits:</b><%# Eval("Hits")%><br>Read more...', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()"><%# Eval("Title")%></a>
                        </div>         
                   </ItemTemplate>
                </asp:Repeater>
                   <div class="dcnt2" style="margin-top: 2px;">
                    <img src="images/arrow7.gif" />&nbsp;<a class="content12" href="findallarticlebyauthor.aspx?author=<%=strAuthor%>" onmouseover="Tip('View all article published by <b><%=strAuthor%></b>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">More...</a>
                    </div>
                        </div>
                   </fieldset>
                   </asp:Panel>          
                 </div>
                 
                    <div style="width: 350px; float:right;">
                    <asp:Panel ID="PanelRelatedArticle" Visible="false" runat="server"> 
                        <fieldset><legend><span class="sortcat">Related Article</span></legend>
                            <div style="padding-right: 8px; padding-left: 5px; padding-top: 6px; line-height: 18px;">
                        <asp:Repeater id="RelatedArticle" runat="server">     
                        <ItemTemplate>
                            <div class="dcnt2" style="margin-top: 2px;">
                             <span class="cyel">&raquo;</span>&nbsp;<a class="content12" href="articledetail.aspx?aid=<%# Eval("ID")%>" onmouseover="Tip('<b>Category:</b> <%# Eval("Category")%><br><b>Hits:</b><%# Eval("Hits")%><br>Read more...', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()"><%# Eval("Title")%></a>
                            </div>         
                       </ItemTemplate>
                    </asp:Repeater>
                            </div>
                       </fieldset>
                       </asp:Panel>
                 </div>
        </div>
       <div style="clear:both"><br /></div>
    </asp:PlaceHolder>  
    <asp:Panel ID="Panel1" runat="server">
    <!--Begin Display Comments-->
    <div style="margin-left: 5px; margin-right: 260px; margin-top: 20px;">
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
    <div style="margin-left: 5px; margin-right: 260px;">
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
    <br /><input class="textbox" type="text" name="remLen" id="remLem" size="3" maxlength="3" value="350" readonly> <span class="catcntsml">Char count</span>
    <br />
    <asp:Label cssClass="cred2" runat="server" id="lblcomcharlimit" visible="false" />
    <input type="hidden" value="<%=Request.QueryString["aid"]%>" ID="ID" name="ID">
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
    </div>
</asp:Content>

