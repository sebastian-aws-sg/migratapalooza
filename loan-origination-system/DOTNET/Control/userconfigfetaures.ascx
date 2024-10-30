<%@ Control Language="C#" AutoEventWireup="true" CodeFile="userconfigfetaures.ascx.cs" Inherits="userconfigfetaures" %>
<div class="containeruprofilepanel">
<div style="padding-left: 3px; padding-top: 8px; padding-bottom: 3px;">
<img src="images/tools_icon.gif" align="absmiddle" />&nbsp;<span class="content3">Current Profile Page Settings</span>
</div>
<div style="padding: 8px;">
<asp:dropdownlist id="uconfigshowhidecookbookddl" runat="server" cssClass="ddl" onmouseover="Tip('<b>Public</b> - everyone can see your<br>CookBook Quick View in your profile including non-members.<br><b>Private</b> - only you can can see your<br>CookBook Quick View in your profile.<br><b>Note:</b>Quick View only show up to 20 recipe,<br>whereas in your main CookBook page over 50.<br>You can configure how many to show below.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" Width="140" AutoPostBack="false">
<asp:Listitem Value="1">Public CookBook</asp:Listitem>
<asp:Listitem Value="0">Private CookBook</asp:Listitem>
</asp:dropdownlist>
<asp:Button ID="Button2" runat="server" cssClass="submitadmin" OnClick="UpdateShowHideCookBook_Click" Text="Save" onmouseover="Tip('<b>Public</b> - everyone can see your<br>CookBook Quick View in your profile including non-members.<br><b>Private</b> - only you can can see your<br>CookBook Quick View in your profile.<br><b>Note:</b>Quick View only show up to 20 recipe,<br>whereas in your main CookBook page over 50.<br>You can configure how many to show below.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />
</div>
<div style="padding: 8px;">
<asp:dropdownlist id="uconfigshowhidefriendslistddl" runat="server" cssClass="ddl" Width="140" onmouseover="Tip('<b>Public</b> - everyone can see your<br>FriendsList Quick View in your profile including non-members.<br><b>Private</b> - only you can can see your<br>FriendsList Quick View in your profile.<br><b>Note:</b>Quick View only show up to 20 friends,<br>whereas in your main FriendsList page over 50.<br>You can configure how many to show below.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" AutoPostBack="false">
<asp:Listitem Value="1">Public FriendsList</asp:Listitem>
<asp:Listitem Value="0">Private FriendsList</asp:Listitem>
</asp:dropdownlist>
<asp:Button ID="Button1" runat="server" cssClass="submitadmin" OnClick="UpdateShowHideFriendsList_Click" Text="Save" onmouseover="Tip('<b>Public</b> - everyone can see your<br>FriendsList Quick View in your profile including non-members.<br><b>Private</b> - only you can can see your<br>FriendsList Quick View in your profile.<br><b>Note:</b>Quick View only show up to 20 friends,<br>whereas in your main FriendsList page over 50..<br>You can configure how many to show below.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />
</div>
<div style="padding: 8px;">
<asp:dropdownlist id="uconfigfriendslistddl" runat="server" cssClass="ddl" onmouseover="Tip('Configure how many friends to show in my <b>Friens List</b><br>Quick View in my profile page.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" Width="100" AutoPostBack="false">
</asp:dropdownlist>
<asp:Button ID="Sconfigbuton1" runat="server" cssClass="submitadmin" OnClick="UpdateNumRecordsFriendsList_Click" Text="Save" onmouseover="Tip('Configure how many friends to show in my <b>Friens List</b><br>Quick View in my profile page.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />
</div>
<div style="padding: 8px;">
<asp:dropdownlist id="uconfigcookbookddl" runat="server" cssClass="ddl" onmouseover="Tip('Configure how many recipes to show in <b>My CookBook</b><br>Quick View in my profile page.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" Width="100" AutoPostBack="false">
</asp:dropdownlist>
<asp:Button ID="Sconfigbuton2" runat="server" cssClass="submitadmin" OnClick="UpdateNumRecordsCookBook_Click" Text="Save" onmouseover="Tip('Configure how many recipes to show in <b>My CookBook</b><br>Quick View in my profile page.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />
</div>
</div>
