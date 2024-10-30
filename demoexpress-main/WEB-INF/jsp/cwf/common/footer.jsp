<%-- Each tiles jsp needs the taglibs definition in it. --%>
<%@ include file="/WEB-INF/jsp/cwf/common/include.jsp" %>

<div>


<script language="JavaScript1.2">


		var CONTACT_NDX = 1;
		var LEGALINFO_NDX = 2;
		var HELP_NDX = 3;

		var wnd_Contact = 1;
		var wnd_LegalInfo = 2;
		var wnd_Help = 3;

		var CONTACT_LINK   = '<bean:message key="footer.contactus.rellink"/>';
		var LEGALINFO_LINK = '<bean:message key="footer.legalinfo.rellink"/>';
		var HELP_LINK      = '<bean:message key="footer.help.rellink"/>';



</script>


<table class="pgClrBtNv" align="right" border="0" cellspacing="0" cellpadding="0" width='100%'>
	<tr valign="center" align="right">
		<td align="left" height="22">&nbsp;</td>
		<td align="left" height="22" width="123">
<a href="#" OnClick="return popWindow(wnd_Contact,CONTACT_NDX,CONTACT_LINK,'dependent,scrollbars,width=650,height=400,screenX=0,screenY=0,left=0,top=0');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1','','<c:out value='${initParam.cwfImagesLoc}'/>contactUsRoll.gif',1)"><img src="<c:out value='${initParam.cwfImagesLoc}'/>contactUs.gif" border="0" name="Image1" width="82" height="16"></a></td>
		<td width="225" align="left">
<a href="#" OnClick="return popWindow(wnd_LegalInfo,LEGALINFO_NDX,LEGALINFO_LINK,'dependent,scrollbars,width=650,height=400,screenX=0,screenY=0,left=0,top=0');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2','','<c:out value='${initParam.cwfImagesLoc}'/>legalInfoRoll.gif',1)"><img src="<c:out value='${initParam.cwfImagesLoc}'/>legalInfo.gif" border="0" name="Image2" width="185" height="16"></a></td>
		<td width="225" align="left">
<a href="#" OnClick="return popWindow(wnd_Help,HELP_NDX,HELP_LINK,'dependent,scrollbars,width=650,height=400,screenX=0,screenY=0,left=0,top=0');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image3','','<c:out value='${initParam.cwfImagesLoc}'/>helpRoll.gif',1)"><img src="<c:out value='${initParam.cwfImagesLoc}'/>help.gif" border="0" name="Image3" width="48" height="16"></a></td>
	</tr>
</table>
</div>

