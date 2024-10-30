<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>

<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/primary.css" />
<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/displaytag.css" />
<script language="JavaScript" src="/mcx/static/js/dealerinfo_popup.js"></script>
	<script language="JavaScript" src="/mcx/static/js/alerts.js"></script>

<html:form action="/adminViewAlert" method="post">
	<bean:define id="subject">
		<bean:message key="alert.subject" />
	</bean:define>
	<bean:define id="sentby">
		<bean:message key="alert.sentby" />
	</bean:define>
	<bean:define id="senton">
		<bean:message key="alert.senton" />
	</bean:define>

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
			<td width="69%" colspan="4" class="con1 padL20a"><bean:message
				key="alert.alerts" /></td>
		</tr>
		<tr>
			<td height="6" colspan="4"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>

		<tr>
			<td colspan="4" class="padL20a"><%%> <display:table name="alertlist" id="row" class="border_alert"
				cellpadding="0" cellspacing="0">
				<display:column title="<%=subject%>" headerClass="blue_bg2_alert"
					class="con9_alert">
					<a href="/mcx/action/adminViewAlertDetail?alertid=<c:out value='${row.alertid}'/>">
					<c:out value="${row.subject}" /> </a>
				</display:column>
				<display:column title="<%=sentby%>"
					headerClass="blue_bg2_no_pad_alert" class="con9_alert2">
					<a class="linkDealer"
						href="javascript:userDetailsPopUp('<c:out value='${row.userid}'/>');">
					<c:out value="${row.userName}" /> </a>
				</display:column>
				<display:column title="<%=senton%>"
					headerClass="blue_bg2_no_pad_alert2" class="con9_alert3">
					<c:out value="${row.updatetimestamp}" />
				</display:column>
			</display:table></td>
		</tr>
		</logic:present>
		
		<tr>
			<td height="10px" colspan="4"><img
				src="/mcx/static/images/spacer.gif" /></td>
		</tr>

		<tr>
			<td width="69%" colspan="4" class="con1 padL20a">&nbsp;</td>
		</tr>

	</table>

</html:form>
