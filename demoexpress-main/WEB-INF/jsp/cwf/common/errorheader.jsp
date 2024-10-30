<%-- Each tiles jsp needs the taglibs definition in it. --%>
<%@ include file="/WEB-INF/jsp/cwf/common/include.jsp" %>

<script language="JavaScript">
function closeWindow() {
   if (confirm("<bean:message key='nav.loggout.message'/>")) { 
      window.close();
   }
}

</script>
<div>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="top" valign="left">
   <tr class="tbl2" >
      <td>
		 <table width="100%" border="0" cellspacing="0" cellpadding="0" align="top" valign="left">
		    <tr bgcolor="FFFFFF">

<td width="30%" align="right"><a href="http://www.dtcc.com" target="_blank"><img src="<c:out value='${initParam.cwfImagesLoc}'/>DTCCLogo.gif" width="121" height="46" border="0"></a></td>
		    </tr>	
         </table>	      
      </td>   
   </tr>     
<tr>
</tr>     
    <tr >
       <td  background="<c:out value='${initParam.cwfImagesLoc}'/>nav_filler.gif" valign="left" nowrap>		
          <img src="<c:out value='${initParam.cwfImagesLoc}'/>nav_separator.gif">
          <a height="18" href='<bean:message key="nav.home.link"/>' onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('home','','<c:out value='${initParam.cwfImagesLoc}'/>Home_roll.gif',1)"><img src="<c:out value='${initParam.cwfImagesLoc}'/>Home.gif" name="home" border="0"></a>
          <img src="<c:out value='${initParam.cwfImagesLoc}'/>nav_separator.gif">
          <a href='<bean:message key="nav.changepassword.link"/>' target="_blank" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Change Password','','<c:out value='${initParam.cwfImagesLoc}'/>changePassword_roll.gif',1)"><img src="<c:out value='${initParam.cwfImagesLoc}'/>changePassword.gif" name="Change Password" border="0" ></a>
          <img src="<c:out value='${initParam.cwfImagesLoc}'/>nav_separator.gif">
          <a href="#" onClick="closeWindow();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Logout','','<c:out value='${initParam.cwfImagesLoc}'/>Logout_roll.gif',1)"><img src="<c:out value='${initParam.cwfImagesLoc}'/>Logout.gif" name="Logout" border="0" ></a>
          <img src="<c:out value='${initParam.cwfImagesLoc}'/>nav_separator.gif">
       </td>
    </tr>
</table>
</div>