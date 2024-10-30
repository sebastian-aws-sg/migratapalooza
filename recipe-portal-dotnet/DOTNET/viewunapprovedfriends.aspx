<%@ Page Language="C#" MasterPageFile="~/SiteTemplate2.master" AutoEventWireup="true" CodeFile="viewunapprovedfriends.aspx.cs" Inherits="viewunapprovedfriends" Title="Viewing UnApproved New Friends" %>
<%@ Register TagPrefix="ucl" TagName="sidemenu" Src="Control/sidemenu.ascx" %>
<%@ Register TagPrefix="ucl" TagName="usersearchtab" Src="Control/usersearchtab.ascx" %>

<asp:Content ID="Content1" ContentPlaceHolderID="LeftPanel" Runat="Server">
<ucl:sidemenu id="menu1" runat="server"></ucl:sidemenu>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
<ucl:usersearchtab id="searchusercont" runat="server"></ucl:usersearchtab>
    <div style="margin-left: 10px; margin-bottom: 12px; margin-right: 12px; background-color: #FFF9EC; margin-top: 0px;">
    &nbsp;&nbsp;<a href="default.aspx" class="dsort" title="Back to recipe homepage">Home</a>&nbsp;<span class="bluearrow">»</span>&nbsp; <span class="content2">You are here: My Friends List</span>
    </div>
    <div style="margin-left: 15px;">   
    <table border="0" cellpadding="2" align="left" cellspacing="2" width="75%">
      <tr>
    <td width="68%">
    <fieldset><legend><asp:Label runat="server" id="lblusernameheader" EnableViewState="false" /></legend>
     <div style="padding-top: 8px;">
     <asp:Label runat="server" id="lblyouarenotlogin" Visible="false" CssClass="content12" EnableViewState="false" />
     <asp:Panel ID="HideContentIfNotLogin" runat="server">
     <div style="margin-top: 5px; margin-bottom: 20px;">
        <asp:Label runat="server" id="lblcountunpprovednewfriends" CssClass="content12" EnableViewState="false" />
     </div>
        <asp:Label runat="server" CssClass="content2" id="lblnofriends" EnableViewState="false" />
         <asp:Repeater id="MyFriendsList" runat="server" OnItemDataBound="MyFriendsList_ItemDataBound" OnItemCommand="Delete_Friend">     
        <ItemTemplate>
        <div class="dcnt2" style="margin-top: 6px;">
        <asp:HyperLink ID="btnDelete" runat="server" CssClass="thickbox" Text="<img border='0' src='images/icon_delete.gif'>" /><asp:Button ID="deleteCommand" runat="server" CausesValidation="false" CommandName='Declined' CommandArgument='<%# Eval("ID") + "," + Eval("Username") + "," + Eval("Email") %>' style="display:none" />&nbsp;&nbsp;&nbsp;<asp:ImageButton ID="btnapproved" ImageUrl="images/inc_grencheck.gif" runat="server" CausesValidation="false" CommandName='Approved' CommandArgument='<%# Eval("ID") + "," + Eval("Email") + "," + Eval("Username") %>' OnClientClick="return confirm('Are you sure you want to approved this user?');" />&nbsp;&nbsp;&nbsp;&nbsp;<img src="images/user-icon.gif" />&nbsp;<a class="content12" onmouseover="Tip('<%# Eval("Email") %><br><b>User name:</b> <%# Eval("Username") %><br><b>Full name:</b> <%# Eval("FirstName") %> <%# Eval("LastName") %><br><b>Country:</b> <%# Eval("Country") %><br><b>Profile views:</b> (<%# Eval("Hits") %>)<br><b>Date joined:</b> <%# Eval("DateJoined", "{0:M/d/yyyy}")%><br><b>Last visit:</b> (<%# Eval("LastVisit") %>)<br>Added to Friends List on: <%# Eval("Date", "{0:M/d/yyyy}")%><br><b>Photo:</b><br><img src=&quot;UserImages/<%# Eval("Photo")%>&quot; width=&quot;160&quot; height=&quot;140&quot;>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" href='userprofile.aspx?uid=<%# Eval("UID") %>'><%# Eval("Username")%></a>
        </div>
        
        <asp:Panel ID="deleteConfirm" runat="server" style="display:none;">
            <div class="confirm">
            
                <div class="message">
                 <img src="images/icon_confirm.gif" alt="confirm icon" style="float: left; margin-right: 10px;" /> Are you sure you want to declined <span style="color:#266B91"><b><%# Eval("Username")%></b></span>?
                </div>
                
                <div class="commands" style="text-align: center;">
                  <asp:Button ID="yes" runat="server" Width="60" Height="20"
                  CausesValidation="false" CssClass="submitpopupmodal"
                  Text="OK" ToolTip="Delete" />&nbsp;
                  
                  <asp:Button ID="no" runat="server" Width="60" Height="20"
                  CausesValidation="false" CssClass="submitpopupmodal" ToolTip="Cancel and Close" 
                  Text="Cancel" OnClientClick="tb_remove(); return false;" />
                </div>
                
            </div>
        </asp:Panel>        
       </ItemTemplate>
 
      </asp:Repeater>
      </asp:Panel>
     </div>
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

