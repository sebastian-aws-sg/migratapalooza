<%@ Page Language="C#" MasterPageFile="~/SiteTemplate2.master" EnableViewState="false" AutoEventWireup="true" CodeFile="myfriendslist.aspx.cs" Inherits="myfriendslist" Title="My friends list" %>
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
     <div style="margin-top: 5px; margin-bottom: 17px;">
     <asp:Label runat="server" id="lblcounter" CssClass="content12" EnableViewState="false" />
     </div>
     <asp:Panel ID="PanelUnApprovedFriends" Visible="false" runat="server" style="margin-top: 5px; margin-bottom: 20px;">
        <asp:Label runat="server" id="lblcountunpprovednewfriends" CssClass="content12" EnableViewState="false" />
     </asp:Panel>
        <asp:Label runat="server" CssClass="content2" id="lblnofriends" EnableViewState="false" />
         <asp:Repeater id="MyFriendsList" runat="server" OnItemDataBound="MyFriendsList_ItemDataBound">     
        <ItemTemplate>
        <div class="dcnt2" style="margin-top: 6px;">
         <asp:Label ID="lblDelete" runat="server" CssClass="thickbox" EnableViewState="false" />&nbsp;&nbsp;&nbsp;<img src="images/user-icon.gif" />&nbsp;<a class="content12" title="View <%# Eval("Username") %> friend" onmouseover="Tip('<b>User name:</b> <%# Eval("Username") %><br><b>Full name:</b> <%# Eval("FirstName") %> <%# Eval("LastName") %><br><b>Country:</b> <%# Eval("Country") %><br><b>Profile views:</b> (<%# Eval("Hits") %>)<br><b>Date joined:</b> <%# Eval("DateJoined", "{0:M/d/yyyy}")%><br><b>Last visit:</b> (<%# Eval("LastVisit") %>)<br>Added to Friends List on: <%# Eval("Date", "{0:M/d/yyyy}")%><br><b>Photo:</b><br><img src=&quot;UserImages/<%# Eval("Photo")%>&quot; width=&quot;160&quot; height=&quot;140&quot;>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" href='userprofile.aspx?uid=<%# Eval("FriendID") %>'><%# Eval("Username")%></a>
        </div>
        
          <div id="confirmModal<%# Eval("ID")%>" style="display:none;">
            <div class="confirm">
                <div class="message">
                 <img src="images/icon_confirm.gif" alt="confirm icon" style="float: left; margin-right: 10px;" /> Are you sure you want to remove <span style="color:#266B91"><b><%# Eval("Username")%></b></span> from your Friends List?
                </div>
                <div class="commands" style="text-align: center;">
                    <input type="button" value="Yes" class="submitpopupmodal" onclick="sendRequestDeleteAFriend('<%# Eval("FriendID")%>')">&nbsp;&nbsp;<input type="button" value="No" class="submitpopupmodal" onclick="tb_remove(); return false;">
                </div>
            </div>
        </div>
        
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
