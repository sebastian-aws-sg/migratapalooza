<%@ Page Language="C#" AutoEventWireup="true" ValidateRequest="false" CodeFile="pmsend.aspx.cs" Inherits="pmsend" %>
<%@ Register TagPrefix="ucl" TagName="Login" Src="Control/Login.ascx" %>
<%@ Register TagPrefix="ucl" TagName="pmmenu" Src="Control/pmmenu.ascx" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head id="Head1" runat="server">
    <title>Sending Private Message</title>
    <link href="CSS/cssreciaspx.css" rel="stylesheet" type="text/css" />
    <!-- TinyMCE -->
<script type="text/javascript" src="tinymce_editor/tiny_mce.js"></script>
<script type="text/javascript">
	tinyMCE.init({
		// General options
		editor_deselector : "mceNoEditor",
		mode : "textareas",
		theme : "advanced",
		plugins : "safari,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,inlinepopups",

		// Theme options
		theme_advanced_buttons1 : "forecolor,backcolor,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,fontselect,fontsizeselect",
		theme_advanced_buttons2 : "emotions,undo,redo,|,link,unlink,image,code,|,insertdate,inserttime,preview",
		theme_advanced_buttons3 : "",
		theme_advanced_buttons4 : "",
		theme_advanced_toolbar_location : "top",
		theme_advanced_toolbar_align : "left",
		theme_advanced_statusbar_location : "bottom",
		theme_advanced_resizing : false,
        height: "250px",
        width: "530px",

		// Example word content CSS (should be your site CSS) this one removes paragraph margins
		content_css : "CSS/word.css",

		// Drop lists for link/image/media/template dialogs
		template_external_list_url : "lists/template_list.js",
		external_link_list_url : "lists/link_list.js",
		external_image_list_url : "lists/image_list.js",
		media_external_list_url : "lists/media_list.js",

		// Replace values for the template plugin
		template_replace_values : {
			username : "Some User",
			staffid : "991234"
		}
	});
</script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">
   $(document).ready(function(){
	  
	  $('#MyFriendsListCont').hide();
   
   			$('#click').click(function(){
			
			$('#MyFriendsListCont').show('slow');
   
   });
   
   $('a#close').click(function(){
   		$('#MyFriendsListCont').hide('slow');
		})
   
 	  });
</script>
<!-- /TinyMCE -->
<!--[if gte IE 5]>
<style>
.textboxsearch {height: 17px;}
.contrememberme {margin-top: 0px;}
#b2 ul a {height: 1.2em;}
#b2 li {float: left; clear: both; width: 100%;}
fieldset {background: #ffffff;}
#sddm {margin-top: 1px;}
</style>
<![endif]-->
</head>
<body>
    <form id="form1" runat="server">
    <div id="header">
        <!--Begin Header-->
        <a name="Top"></a>
        <div class="headerwrap">
        <div class="headerlogoleft"><a title="Myasp-net.com" href="default.aspx"><img src="images/recipelogo.gif" width="357" height="70" border="0" alt="Myasp-net.com" /></a></div>
        <div class="headerright">
        <div class="headerrightdatetime">
        <ucl:Login id="loginbox" runat="server"></ucl:Login>
        </div>
        </div>
        </div>
        <div style="clear:both;"></div>
        <!--End Header-->   
    </div>
<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
 <tr>
    <td width="15%" valign="top" align="left">
    <ucl:pmmenu id="pm_menu" runat="server"></ucl:pmmenu>
    </td>
<td valign="top" width="85%">
<!--End Insert Recipe Form-->
<div style="margin-left: 15px; margin-top: 52px;">
<asp:Label ID="lbvalenght" runat="server" Font-Bold="True" ForeColor="#C00000" Font-Names="Verdana" Visible="false" /> 
<fieldset><legend>Sending Private Message Form</legend>
<asp:Label runat="server" id="lblyouarenotlogin" Visible="false" CssClass="content12" EnableViewState="false" />
<asp:Panel ID="HideContentIfNotLogin" Visible="false" runat="server">
<div style="padding: 2px; margin-top: 12px; margin-left: 1px; margin-right: 26px;">
<span class="content2">Fields marked with red <span class="cred2">*</span> are required.</span> 
</div>
<div style="margin-top: 8px;">
<table border="0" cellpadding="2" cellspacing="2" width="60%">
<asp:Label runat="server" id="lblreplyto" Visible="false" CssClass="content12" EnableViewState="false" />
<asp:Label runat="server" id="lblsubject" Visible="false" CssClass="content12" EnableViewState="false" />
<asp:Panel ID="PanelShowHideToFieldIfReply" Visible="false" runat="server">
    <tr>
    <td width="18%" valign="top"><span class="content12">Send To:</span><span class="cred2">*</span></td>
    <td width="82%" valign="top"><input type="text" id="PMSendTo" name="PMSendTo" class="textbox" size="25" runat="server" onFocus="this.style.backgroundColor='#FFFBE1'" onBlur="this.style.backgroundColor='#ffffff'" onmouseover="Tip('(Make sure the username exist and only 1 recipient allowed.)', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />&nbsp;
    <img src="images/friendlisticon_smll2.gif" />&nbsp;<a href="javascript:void(0)" class="content2" id="click" onmouseover="Tip('Look up from my Friends List.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()"><b>Friends List</b></a>&nbsp;&nbsp;&nbsp;<img src="images/user-icon.gif" />&nbsp;<a class="content2" href="members.aspx" onmouseover="Tip('Cannot find someone? Look up in the members list.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()"><b>Members List</b></a>  
   <div id="MyFriendsListCont" style="margin-top: 2px; height: 130px; width: 155px; margin: 4px 0px 4px 0px; padding: 5px; border: solid 1px #acc6db; overflow:auto; display: none;">
   <asp:Label runat="server" id="lblcountfriends" Text="You no friends saved.<br>" Visible="false" CssClass="content12" EnableViewState="false" />
   <asp:Repeater id="MyFriendsList" runat="server">     
    <ItemTemplate>
        <div class="dcnt2" style="margin-top: 2px;">
         <img src="images/user-icon.gif" />&nbsp;<a href="javascript:void(0)" class="content2" onmouseover="Tip('Send PM to <b><%# Eval("Username") %></b>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" OnClick="document.getElementById('PMSendTo').value='<%# Eval("Username") %>'"><%# Eval("Username") %></a>
        </div>         
   </ItemTemplate>
  </asp:Repeater>
  <img src="images/icon_delete.gif" />&nbsp;<a title="close" href="javascript:void(0)" class="content2" id="close">Close</a>
  </div>
</td>
  </tr>
 </asp:Panel>
<asp:Panel ID="PanelShowHideSubjectIfReply" Visible="false" runat="server">  
  <tr>
    <td width="18%" valign="top"><span class="content12">Subject:</span><span class="cred2">*</span></td>
    <td width="82%" valign="top">
    <div style="margin-bottom: 6px;">
<input type="text" id="PMSubject" name="PMSubject" class="textbox" size="50" runat="server" onFocus="this.style.backgroundColor='#FFFBE1'" onBlur="this.style.backgroundColor='#ffffff'" onmouseover="Tip('50 characters maximum for the <b>Subject.</b>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />
  </div>
</td>
  </tr>
 </asp:Panel>
    <tr>
    <td width="18%" valign="top"><span class="content12">Message:</span><span class="cred2">*</span></td>
    <td width="82%">
<textarea runat="server" id="Content" name="Content" class="textbox" cols="80" style="width: 80%" rows="50" onFocus="this.style.backgroundColor='#FFFBE1'" onBlur="this.style.backgroundColor='#ffffff'" />
</td>
  </tr>
  <tr>
    <td width="18%"></td>
    <td width="82%">
<input type="text" class="textbox" ID="hd" name="hd" runat="server" style="visibility:hidden;">
<br />
<asp:Button runat="server" Text="Send" id="AddArticle" cssClass="submitadmin" OnClick="SendPM_Click" onmouseover="Tip('Before submitting, you can <b>preview</b> your article by clicking the preview button in the editor menu.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />
</td>
  </tr>
</table>
 </div>
 </asp:Panel>
</fieldset>
</div>
</td>
  </tr>
</table>
<div style="margin-top: 135px;"></div>
<div id="footer">
        <!--Begin Footer-->
        <div class="footerwrap">
        <br />
        <img src="images/returntop.gif" alt="return to top" align="absmiddle" border="0" /><a class="dt2" title="Return to top of the page" href="#Top">Return to top</a>
        <br />
        <span class="content2">
        Copyright © 2000 - 2009 Ex-designz.net. All rights reserved. Developed By <a class="dt2" title="Website" href="http://www.ex-designz.net">Dexter Zafra - Ex-designz.net</a></span>
        <br />
         <asp:HyperLink id="Powered" cssClass="dt2" ToolTip="Visit our portal website" NavigateURL="http://www.ex-designz.net" runat="server">Powered By Ex-designz.net World Recipe .NET version</asp:HyperLink>
         <br /><br />
         </div>
        <!--End Footer-->  
  </div>
<asp:Literal id="JSLiteral" runat="server"></asp:Literal>
<!--End Insert Article Form-->
    </form>
    <script type="text/javascript" src="js/wz_tooltip.js"></script>
</body>
</html>
