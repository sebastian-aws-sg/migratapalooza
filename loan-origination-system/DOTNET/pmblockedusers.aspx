<%@ Page Language="C#" MasterPageFile="~/SiteTemplate2.master" AutoEventWireup="true" CodeFile="pmblockedusers.aspx.cs" Inherits="pmblockedusers" Title="Managed PM Blocked Users" %>
<%@ Register TagPrefix="ucl" TagName="pmmenu" Src="Control/pmmenu.ascx" %>

<asp:Content ID="Content1" ContentPlaceHolderID="LeftPanel" Runat="Server">
    <ucl:pmmenu id="pm_menu" runat="server"></ucl:pmmenu>
    <br />
    <div class="content2" style="padding-left: 20px; line-height: 20px;">
              <img alt="New Message" src="images/newmsg_icon.gif">&nbsp;New Message<br>
          <img alt="Old Message" src="images/oldmsg_icon2.gif">&nbsp;Old Message
    </div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
<div style="margin-left: 15px; margin-left: 15px; margin-top: 50px; margin-right: 90px;">
<fieldset><legend>Block Listed Users</legend>
<asp:Label runat="server" id="lblyouarenotlogin" Visible="false" CssClass="content12" EnableViewState="false" />
<asp:Panel ID="HideContentIfNotLogin" Visible="false" runat="server">
<div style="background:#D9F3B0 url(images/cpbggreen6.gif) repeat-x; border: 1px solid #D9F3B0;padding-top: 2px; padding-bottom: 2px; padding-left: 6px; text-align: left; margin-top: 25px; margin-right: 10px;">
<span style="font-family : tahoma,arial,helvetica,sans-serif; font-size: 14px; color: #274E74;"><b>Hi, <%=HiUserName%></b></span>
</div>
<div style="margin-top: 15px; margin-bottom: 15px;">
<asp:Label runat="server" id="lblcountblocklistedusers" CssClass="content2" EnableViewState="false" />
</div>
<asp:Repeater id="PMBlockedUsers" runat="server" OnItemDataBound="PMBlockedUsers_ItemDataBound" OnItemCommand="PMBlockedUsers_Command">    
<ItemTemplate>
       <div class="dcnt2" style="margin-top: 6px;">
        <asp:HyperLink ID="btnDelete" runat="server" CssClass="thickbox" Text="<img border='0' src='images/icon_delete.gif'>" /><asp:Button ID="deleteCommand" runat="server" CausesValidation="false" CommandName='Remove' CommandArgument='<%# Eval("ID") + "," + Eval("SenderUserName")%>' style="display:none" />&nbsp;&nbsp;<a class="content12" onmouseover="Tip('<b>Block listed on: </b> <%# Eval("DateCreated", "{0:M/d/yyyy}")%><br>Click to view complete profile.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" href='userprofile.aspx?uid=<%# Eval("SenderUserID") %>'><%# Eval("SenderUserName")%></a>
        </div>
        
        <asp:Panel ID="deleteConfirm" runat="server" style="display:none;">
            <div class="confirm">
            
                <div class="message">
                 <img src="images/icon_confirm.gif" alt="confirm icon" style="float: left; margin-right: 10px;" /> Are you sure you want to remove <span style="color:#266B91"><b><%# Eval("SenderUserName")%></b></span> from your PM block list.?
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
</fieldset>
</div>
<div style="height: 150px;"></div>
</asp:Content>

