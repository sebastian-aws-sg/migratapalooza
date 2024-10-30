<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/WEB-INF/jsp/common/error.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld"  prefix="bean"  %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld"  prefix="html"  %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld"  prefix="bean"  %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/tlds/taglibs-datetime.tld"  prefix="dt"    %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	<title><bean:message key="mcx.title"/></title>
	<link rel="stylesheet" type="text/css" href="/mcx/static/css/primary.css" />
	<script language="JavaScript" src="/mcx/static/js/primary.js"></script>
	<script language="JavaScript" src="/mcx/static/js/submenu.js"></script>
	
	<tiles:insert attribute="session" />
	
</head>
<body>
<iframe id="menuframe" src="javascript:'';"  scrolling="no" frameborder="0" 
  style="position:absolute;width:0px;height:0px;top:0px;left:0px;border:none;display:block;z-index:0"></iframe>
<iframe id="submenuframe" src="javascript:'';"  scrolling="no" frameborder="0" 
  style="position:absolute;width:0px;height:0px;top:0px;left:0px;border:none;display:block;z-index:0"></iframe>
<iframe id="sidemenuframe" src="javascript:'';"  scrolling="no" frameborder="0" 
  style="position:absolute;width:0px;height:0px;top:0px;left:0px;border:none;display:block;z-index:0"></iframe>
<div class="menu" id="menu"  onmouseover="dropdownmenu_mouseover()" onmouseout="dropdownmenu_mouseout()" ></div>
<div class="sub_menu" style="overflow:visible" id="sub_menu"></div>
<div class="sub_menu" id="sub_menu2"></div>
<table width="100%"   cellspacing="0" cellpadding="0" >
<tr>
	<td>
		<tiles:insert attribute="header" />
		<tiles:insert attribute="menu" />
		<tiles:insert attribute="submenu" />
		<tiles:insert attribute="menu_highlight" />
	</td>
</tr>
      <tiles:insert attribute="errors" />
	  <tiles:insert attribute="messages" />
<tr>
	<td>
		<tiles:insert attribute="form1" />
	</td>
</tr>
<tr>
	<td>
		<tiles:insert attribute="footer" />
	</td>
</tr>
</table>
</body>
</html>