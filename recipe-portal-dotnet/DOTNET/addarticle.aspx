<%@ Page Language="C#" AutoEventWireup="true" EnableViewState="false" ValidateRequest="false" CodeFile="addarticle.aspx.cs" Inherits="addarticle" %>
<%@ Register TagPrefix="ucl" TagName="Login" Src="Control/Login.ascx" %>
<%@ Register TagPrefix="ucl" TagName="sidemenu" Src="Control/sidemenu.ascx" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head id="Head1" runat="server">
    <title>Article Submission Page</title>
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
		theme_advanced_buttons1 : "save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect",
		theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
		theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",
		theme_advanced_buttons4 : "insertlayer,moveforward,movebackward,absolute,|,styleprops,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,pagebreak",
		theme_advanced_toolbar_location : "top",
		theme_advanced_toolbar_align : "left",
		theme_advanced_statusbar_location : "bottom",
		theme_advanced_resizing : true,

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
<!-- /TinyMCE -->
<!--[if gte IE 5]>
<style>
.textboxsearch {height: 17px;}
.contrememberme {margin-top: 0px;}
#b2 ul a {height: 1.2em;}
#b2 li {float: left; clear: both; width: 100%;}
fieldset {background: #ffffff;}
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
    <!--End Insert Recipe Form-->
<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
 <tr>
    <td width="15%" valign="top" align="left">
<ucl:sidemenu id="menu1" runat="server"></ucl:sidemenu>
    </td>
<td valign="top" width="85%">
<div style="margin-left: 15px;">
<asp:Label ID="lbvalenght" runat="server" Font-Bold="True" ForeColor="#C00000" Font-Names="Verdana" Visible="false" /> 
<fieldset><legend>Article Submission Form</legend>
<asp:Label runat="server" id="lblyouarenotlogin" Visible="false" CssClass="content12" EnableViewState="false" />
<asp:Panel ID="HideContentIfNotLogin" Visible="false" runat="server">
<div style="padding: 2px; margin-top: 12px; margin-left: 1px; margin-right: 26px;">
<span class="cred2">All fields are required</span> 
</div>
<div style="margin-top: 8px;">
<table border="0" cellpadding="2" cellspacing="2" width="60%">
  <tr>
    <td width="26%"><span class="content12">Category:</span></td>
    <td width="74%">
<asp:dropdownlist id="ddlarticlecategory" runat="server" cssClass="ddl" AutoPostBack="false"></asp:dropdownlist>
          <asp:RequiredFieldValidator runat="server"
          id="RequiredFieldCategory" ControlToValidate="ddlarticlecategory" SetFocusOnError="true"
          cssClass="cred2"
          InitialValue="0"
          ErrorMessage = "Category is blank"
          display="Dynamic"> </asp:RequiredFieldValidator>
</td>
  </tr>
    <tr>
    <td width="26%"><span class="content12">Author:</span></td>
    <td width="74%">
<span class="cmaron"><asp:Label ID="lblauthorname" cssClass="cmaron" runat="server" /></span>
</td>
  </tr>  
  <tr>
    <td width="26%"><span class="content12">Title:</span><span class="cred2">*</span></td>
    <td width="74%">
<input type="text" id="Title" name="Title" class="textbox" size="60" runat="server" onFocus="this.style.backgroundColor='#FFFBE1'" onBlur="this.style.backgroundColor='#ffffff'" onmouseover="Tip('65 characters maximum for the <b>Title</b>', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />
      <asp:RequiredFieldValidator runat="server"
        id="artname" ControlToValidate="Title"
        cssClass="cred2" errormessage="* Article Title:<br />"
        display="Dynamic" />
</td>
  </tr>
        <tr>
    <td width="26%" valign="top"><span class="content12">Summary:</span><span class="cred2">*</span></td>
    <td width="74%">
<textarea runat="server" id="Summary" class="mceNoEditor textbox" cols="80" rows="3" onmouseover="Tip('350 characters maximum for the <b>Summary</b>, and should not contain HTML tag.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />
      <asp:RequiredFieldValidator runat="server"
        id="summa" ControlToValidate="Summary"
        cssClass="cred2" errormessage="* Summary:<br />"
        display="Dynamic" />
</td>
  </tr>
    <tr>
    <td width="26%" valign="top"><span class="content12">Content:</span><span class="cred2">*</span></td>
    <td width="74%">
<textarea runat="server" id="Content" name="Content" class="textbox" cols="80" style="width: 80%" rows="50" onFocus="this.style.backgroundColor='#FFFBE1'" onBlur="this.style.backgroundColor='#ffffff'" />
</td>
  </tr>
    <tr>
    <td width="26%"><span class="content12">Keyword:</span><span class="cred2">*</span></td>
    <td width="74%">
<input type="text" id="Keyword" name="Keyword" class="textbox" size="65" runat="server" onFocus="this.style.backgroundColor='#FFFBE1'" onBlur="this.style.backgroundColor='#ffffff'" onmouseover="Tip('60 characters maximum for the <b>Summary</b>, and should not contain HTML tag.<br>Multiple keywords must be separated with comma.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />
      <asp:RequiredFieldValidator runat="server"
        id="keyw" ControlToValidate="Keyword"
        cssClass="cred2" errormessage="* Keyword:<br />"
        display="Dynamic" />
</td>
  </tr>
  <tr>
    <td width="26%"></td>
    <td width="74%">
<input type="text" class="textbox" ID="hd" name="hd" runat="server" style="visibility:hidden;">
<br />
<asp:Button runat="server" Text="Submit" id="AddArticle" cssClass="submitadmin" OnClick="Add_Article" onmouseover="Tip('Before submitting, you can <b>preview</b> your article by clicking the preview button in the editor menu.', BGCOLOR, '#FFFBE1', BORDERCOLOR, '#acc6db')" onmouseout="UnTip()" />
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
<div style="margin-top: 45px;"></div>
<div id="footer">
        <!--Begin Footer-->
        <div class="footerwrap">
        <br />
        <img src="images/returntop.gif" alt="return to top" align="absmiddle" border="0" /><a class="dt2" title="Return to top of the page" href="#Top">Return to top</a>
        <br />
        <span class="content2">
        Copyright © 2000 - 2008 Ex-designz.net. All rights reserved. Developed By <a class="dt2" title="Website" href="http://www.ex-designz.net">Dexter Zafra - Ex-designz.net</a></span>
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

