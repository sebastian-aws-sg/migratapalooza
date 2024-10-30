<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/mca-grid.tld"  prefix="grid" %>
<link rel="stylesheet" type="text/css" href="/mcx/static/css/primary.css" />
<script language="JavaScript" src="/mcx/static/js/enrollment.js"></script>
<title><bean:message key="mcx.enroll.selectmca.selecttype" /></title>
<html:form action="/selectMCA" method="post">
<body onload="javascript:window.scrollTo(0,0);">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>

		<td>
		<table width="100%" class="border1" cellpadding="0" cellspacing="0">
			<tr>
				<td height="10px"><img src="/mcx/static/images/spacer.gif" /></td>
			</tr>
		  <tr>
		    <td  height="117" valign="top" class="blue_pop_bg1 padL20 border2"><strong style="word-break: break-all; width: 95%" id="<bean:write name="SelectMCAForm" property="dealerCode" />"><bean:message key="mcx.enroll.selectmca.selecttype" />:&nbsp;</strong><br />
		      <div class="blue_pop_bg1_small"><bean:message key="mcx.enroll.selectmca.clickbox" /></div>
			  <div class="blue_pop_bg2" valign="top"><strong><bean:message key="mcx.enroll.selectmca.legend" /></strong><br />
		      <img src="/mcx/static/images/red_shade.gif" alt="red shade" />&nbsp;<bean:message key="mcx.enroll.selectmca.openmca" /><br /><img src="/mcx/static/images/green_shade.gif" alt="green shade" />&nbsp;<bean:message key="mcx.enroll.selectmca.execmca" /> </div>
  		    </td>
		  </tr>
			<tr>
				<td colspan="3" class="blue_con_bg">
					<grid:ProductMCAGrid productList='<%=(ArrayList)request.getAttribute("productList")%>'  regionList='<%=(ArrayList)request.getAttribute("regionList")%>' productRegionMap='<%=(HashMap)request.getAttribute("productRegionMap")%>' >
					</grid:ProductMCAGrid>

				</td>
			</tr>
			<tr>
				<td colspan="3">&nbsp;</td>
			</tr>
			<tr>
				<td align="right" class="padR5" colspan="3"><input class="save"
							type="button" name="saveChoices" onclick="saveMCAChoices(document.forms['SelectMCAForm'],'<bean:write name="SelectMCAForm" property="dealerCode" />')" value="SAVE CHOICES" /></td>
			</tr>
			<tr>
				<td height="10px"><img src="/mcx/static/images/spacer.gif" /></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<script>
	showSelectedMCAs('<bean:write name="SelectMCAForm" property="dealerCode" />');
</script>
</body>
</html:form>