<%@ Page Language="C#" MasterPageFile="~/SiteTemplate2.master" EnableViewState="false" AutoEventWireup="true" CodeFile="pmview.aspx.cs" Inherits="pmview" Title="View Private Message Inbox" %>
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
<fieldset><legend>Private Message Inbox</legend>
<asp:Label runat="server" id="lblyouarenotlogin" Visible="false" CssClass="content12" EnableViewState="false" />
<asp:Panel ID="HideContentIfNotLogin" Visible="false" runat="server">
<div style="background:#D9F3B0 url(images/cpbggreen6.gif) repeat-x; border: 1px solid #D9F3B0;padding-top: 2px; padding-bottom: 2px; padding-left: 6px; text-align: left; margin-top: 25px; margin-right: 10px;">
<span style="font-family : tahoma,arial,helvetica,sans-serif; font-size: 14px; color: #274E74;"><b>Hi, <%=HiUserName%></b></span></div>
    <div style="margin-top: 20px; margin-bottom: 15px;">
        <span class="content2" style="color: #000000;"><b><asp:Label ID="lblPMHeader" runat="server" /></b>&nbsp;<asp:Image ID="IsNewImageTop" ImageAlign="absmiddle" runat="server" />&nbsp;<asp:Label ID="lblcountunreadmsg" runat="server" /></span>
    </div>
<table border="0" cellpadding="0" cellspacing="0" width="99%" style="border: 1px solid #E1EDFF;">
  <tr>
    <td width="2%" class="PMHeader"><div style="padding-top: 7px; padding-bottom: 7px;">&nbsp;</div></td>
    <td width="30%" class="PMHeader"><asp:HyperLink id="SortLinkSubject" style="color: #0C0260;" runat="server" />&nbsp;<asp:Image id="ArrowImageSubject" runat="server" /></td>
    <td width="8%" class="PMHeader"><asp:HyperLink id="SortLinkSender" style="color: #0C0260;" runat="server" />&nbsp;<asp:Image id="ArrowImageSender" runat="server" /></td>
    <td width="6%" class="PMHeader"><asp:HyperLink id="SortLinkDateSent" style="color: #0C0260;" runat="server" />&nbsp;<asp:Image id="ArrowImageDateSent" runat="server" /></td>
    <td width="3%" class="PMHeader">Delete</td>
  </tr>
<asp:Repeater id="PMViewInbox" runat="server" OnItemDataBound="PMViewInbox_ItemDataBound" OnItemCommand="PMViewInBox_Command">    
<ItemTemplate>
  <tr onmouseover="this.className='rowhoover';" onmouseout="this.className='row<%# Container.ItemIndex % 2 %>';" class="row<%# Container.ItemIndex % 2 %>">
    <td width="2%" class="tdgrid" valign="top"><div style="padding-left: 3px; padding-top: 7px; padding-bottom: 7px;"><asp:Label ID="MarkUnreadOrRead" runat="server" /><asp:Image ID="ImgReadUnread" runat="server" /></div></td>
    <td width="30%" class="tdgrid" valign="top">
    <div style="padding-top: 7px;"><asp:Label runat="server" CssClass="content" id="linksubject" /></div>
        <div id="msgcont<%# Eval("ID")%>" style="background: #fff; margin: 8px 10px 8px 1px; padding: 6px; border: solid 1px #FFD8B0; display:none;"><div style="padding-top: 6px;"><%# Eval("Message")%></div>
            <div style="margin-top: 15px; margin-bottom: 5px;">
            <asp:Label ID="lblhidemsg" CssClass="content01" runat="server" />&nbsp;&nbsp;&nbsp;&nbsp;<asp:Label ID="lblreply" CssClass="content01" runat="server" />&nbsp;&nbsp;&nbsp;&nbsp;<asp:Label ID="lblforward" CssClass="content01" runat="server" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<asp:LinkButton ID="btnblockuser" Visible="false" Text="Block User" CausesValidation="false" CommandName='BlockUser' CommandArgument='<%# Eval("RecipientUserID") + "," + Eval("SenderUserID") + "," + Eval("SenderUserName")%>' CssClass="content01" runat="server" OnClientClick="return confirm('Are you sure you want to this user?');" />
            </div>
        </div>
    </td>
    <td width="8%" class="tdgrid" valign="top"><div style="padding-top: 7px;"><img src="images/user-icon.gif" />&nbsp;<asp:Label ID="lbusername" CssClass="content2" runat="server" /></div></td>
    <td width="6%" class="tdgrid content2" valign="top"><div style="padding-top: 7px;"><%# CustomDateFormat(Eval("DateSent"))%></div></td>
    <td width="3%" class="tdgrid" valign="top"><div style="padding-top: 7px;">&nbsp;&nbsp;&nbsp;<asp:Label ID="lblmovebacktoinbox" Visible="false" CssClass="content01" runat="server" /><asp:Label ID="lblmovepmtotrash" Visible="false" CssClass="content01" runat="server" /><asp:Label ID="lbDeleteInboxTrashPM" runat="server" Visible="false" EnableViewState="false" /><asp:Label ID="lblDeleteSentPM" runat="server" Visible="false" EnableViewState="false" /></div></td>
  </tr>
      
        <div id="confirmModalMoveToTrash<%# Eval("ID")%>" style="display:none;">
            <div class="confirm">
                 <div class="message">
                     <img src="images/icon_confirm.gif" alt="confirm icon" style="float: left; margin-right: 10px;" /> Are you sure you want to move message<br /><b><%# Eval("Subject")%></b> to trash?
                 </div>
                <div class="commands" style="text-align: center;">
                    <input type="button" value="Yes" class="submitpopupmodal" onclick="sendRequestMovePMToTrash('<%# Eval("ID")%>')">&nbsp;&nbsp;<input type="button" value="No" class="submitpopupmodal" onclick="tb_remove(); return false;">
                </div>
            </div>
        </div>
        
        <div id="confirmModalDelTrashInboxPM<%# Eval("ID")%>" style="display:none;">
            <div class="confirm">
                <div class="message">
                 <img src="images/icon_confirm.gif" alt="confirm icon" style="float: left; margin-right: 10px;" /> Are you sure you want to permanently delete message (<b><%# Eval("Subject")%></b>) from trash?
                </div>
                <div class="commands" style="text-align: center;">
                    <input type="button" value="Yes" class="submitpopupmodal" onclick="sendRequestDeleteTrashInboxPM('<%# Eval("ID")%>')">&nbsp;&nbsp;<input type="button" value="No" class="submitpopupmodal" onclick="tb_remove(); return false;">
                </div>
            </div>
        </div>
        
        <div id="confirmModalDelSentPM<%# Eval("ID")%>" style="display:none;">
            <div class="confirm">
                <div class="message">
                 <img src="images/icon_confirm.gif" alt="confirm icon" style="float: left; margin-right: 10px;" /> Are you sure you want to delete message (<b><%# Eval("Subject")%></b>)?
                </div>
                <div class="commands" style="text-align: center;">
                    <input type="button" value="Yes" class="submitpopupmodal" onclick="sendRequestDeleteSentPM('<%# Eval("ID")%>')">&nbsp;&nbsp;<input type="button" value="No" class="submitpopupmodal" onclick="tb_remove(); return false;">
                </div>
            </div>
        </div>

</ItemTemplate>
</asp:Repeater>
</table>
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
    </asp:Panel>
    </fieldset>
</div>
<div style="height: 150px;"></div>
</asp:Content>