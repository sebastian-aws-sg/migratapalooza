<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/primary.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />
	<script language="JavaScript" src="/mcx/static/js/alerts.js"></script>


<html:form action="/userViewAlert" method="post">
       <bean:define id="subject"><bean:message key="alert.subject"/></bean:define>
        <bean:define id="sentby"><bean:message key="alert.sentby"/></bean:define>
        <bean:define id="receivedon"><bean:message key="alert.receivedon"/></bean:define>
		<table width="94%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td height="14" colspan="4"><img src="/mcx/static/images/spacer.gif" /></td>
			</tr>
			<logic:notPresent name="alertlist" scope="request">
			<tr>
				<td width="69%" colspan="4" class="con1 padL20a"><bean:message key="alert.norecord"/></td>
			</tr>
			</logic:notPresent>
			<logic:present name="alertlist" scope="request">
			<tr>
				<td width="69%" colspan="4" class="con1 padL20a"><bean:message key="alert.myalerts"/></td>
			</tr>
			<tr>
				<td height="6" colspan="4"><img src="/mcx/static/images/spacer.gif" /></td>
			</tr>
		
			<tr>
				<td colspan="4" class="padL20a">
				<%
				
				 %>
				<display:table name="alertlist" id="row" class="border_useralert" cellpadding="0" cellspacing="0" >
	 				<display:column title="<%=subject%>" headerClass="blue_bg2_alert" class="con9_alert">
						<a href="/mcx/action/userViewAlertDetail?alertid=<c:out value='${row.alertid}'/>">
						<c:out value="${row.subject}"/>
						</a>
					</display:column>
	 				<display:column title="<%=receivedon%>" headerClass="blue_bg2_no_pad_alert2" class="con9_alert3">
						<c:out value="${row.updatetimestamp}"/>
					</display:column>
				</display:table>				
				</td>
			</tr>
			</logic:present>
			<tr>
				<td height="10px" colspan="4"><img src="/mcx/static/images/spacer.gif" /></td>
			</tr>

<tr>
		<td width="69%" colspan="4" class="con1 padL20a">&nbsp;</td>
</tr>

		</table>

</html:form>