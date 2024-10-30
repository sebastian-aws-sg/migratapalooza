<%@ Control Language="C#" AutoEventWireup="true" CodeFile="pmmenu.ascx.cs" Inherits="pmmenu" %>
<asp:Panel ID="PanelShowPMMenuIfLogin" Visible="false" runat="server">
<div style="margin-left: 8px; margin-top: 56px;">
<div style="background: #DBEEFF url(images/bbg.gif) repeat-x; padding: 10px; border: 1px solid #DCEEFF; margin-top: 16px;">
<div style="background:#FEFEF5; border:1px solid #D7DFDF;">
<div style="background:#FFE58B url(images/cpbgyel1.gif) repeat-x; padding-top: 1px; padding-left: 3px; padding-bottom: 1px; text-align: left;">
<img src="images/arrow_orange.gif" alt="">&nbsp;<span style="font-family : tahoma,arial,helvetica,sans-serif; font-size: 11px;"><b>PM Menu</b></span></div>
<div style="padding-left: 4px; padding-top: 7px; padding-bottom: 4px;">
<img border="0" src="images/home_icon.gif" align="absmiddle" width="15" height="15">&nbsp;<a class="content12" href="default.aspx" onmouseover="Tip('Back to homepage.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">Home</a>
<br />
<img border="0" src="images/compose_icon1.gif" align="absmiddle" width="15" height="15">&nbsp;<a class="content12" href="pmsend.aspx?method=newmsg" onmouseover="Tip('Compose new message.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">Compose</a>
<br>
<img border="0" src="images/inbox_icon1.gif" align="absmiddle" width="15" height="15">&nbsp;<a class="content12" href="pmview.aspx" onmouseover="Tip('View Inbox', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">Inbox</a>&nbsp;<span class="content2">(<span style="color: #800000;"><%=CountUserUnreadMsg %></span>)</span>
<br>
<img border="0" src="images/sent_icon1.gif" align="absmiddle" width="15" height="15">&nbsp;<a class="content12" href="pmview.aspx?folder=sentitems" onmouseover="Tip('View All Sent Messages', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">Sent Items</a>&nbsp;<span class="content2">(<span style="color: #800000;"><%=UserSentPMCount %></span>)</span>
<br>
<img border="0" src="images/trash_icon1.gif" align="absmiddle" width="15" height="15">&nbsp;<a class="content12" href="pmview.aspx?folder=trash" onmouseover="Tip('View all messaged in the trash can.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">Trash</a>&nbsp;<span class="content2">(<span style="color: #800000;"><%=CountPMinTrash %></span>)&nbsp;&nbsp;&nbsp;&nbsp;<asp:Label ID="lbemptytrash" runat="server" EnableViewState="false" /></span>
<br>
<img border="0" src="images/PMblockeduser_icon.png" align="absmiddle" width="15" height="15">&nbsp;<a class="content12" href="pmblockedusers.aspx" onmouseover="Tip('Manage blocked users - remove users from my block list.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()">Blocked Users</a>&nbsp;<span class="content2">(<span style="color: #800000;"><%=CountBlockedUsers %></span>)</span>
</div>
</div>
</div>
<div style="background: #DBEEFF url(images/bbg.gif) repeat-x; padding: 10px; border: 1px solid #DCEEFF; margin-top: 16px;">
<div style="background:#FEFEF5; border:1px solid #D7DFDF;">
<div style="background:url(images/cpbggreen6.gif) repeat-x; height: 18px; padding-top: 3px; padding-left: 3px; padding-bottom: 3px; text-align: left;"><img src="images/arrow_orange.gif" alt=""> <span style="font-family : tahoma,arial,helvetica,sans-serif; font-size: 11px;"><b>PM Statistics</b></span></div>
<div class="content2" style="padding: 5px;">
<b><span style="color: #800000;"><%=CountAllPMInTheSystem %></span></b> PM's in the system.<br>
<b><span style="color: #800000;"><%=CountPMSentToday %></span></b> PM's sent and received within last 24 hours.
</div>
</div>
</div>
</div>

<div id="confirmModalEmptyTrash" style="display:none;">
    <div class="confirm">
        <div class="message">
         <img src="images/icon_confirm.gif" alt="confirm icon" style="float: left; margin-right: 10px;" /> Are you sure you want to delete all messages in trash permanently?
        </div>
        <div class="commands" style="text-align: center;">
            <input type="button" value="Yes" class="submitpopupmodal" onclick="sendRequestEmptyTrash()">&nbsp;&nbsp;<input type="button" value="No" class="submitpopupmodal" onclick="tb_remove(); return false;">
        </div>
    </div>
</div>
        
</asp:Panel>
