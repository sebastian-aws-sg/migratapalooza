<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<%@ taglib uri="/WEB-INF/derivServ.tld" prefix="derivserv"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<head>
<title><bean:message key="mcx.homePage.popup.head" /></title>
</head>
<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/primary.css" />
<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/displaytag.css" />
<script language="JavaScript" src="/mcx/static/js/pendingEnrollments.js"></script>


<html:form styleId="approve_form" action="/dealerEnrollApproval" method="post">
	<table width="492" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="6" class="blue_header border_comment"><bean:message
				key="mcx.homePage.dealer_cp_approve_confirm.head" /></td>
		</tr>
		<tr>
			<td colspan="6">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="6" class="attach_txt padL20"><bean:message
						key="mcx.homePage.dealer_cp_approve_confirm.confirmation" /></td>
				</tr>
				<tr>
					<td width="13">&nbsp;</td>
					<td width="80">&nbsp;</td>
					<td width="70" class="attach_txt"><input type="radio"
						name="radioYes" value="yes"  checked="checked"/><bean:message
						key="mcx.homePage.dealer_cp_approve_confirm.yes" /></td>
					<td width="9">&nbsp;</td>
					<td width="204" class="attach_txt"><input type="radio"
						name="radioYes" value="no" onclick="javascript:window.close();"/><bean:message
						key="mcx.homePage.dealer_cp_approve_confirm.no" /></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td height="10" colspan="6"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td class="comment_head_txt"><bean:message
				key="mcx.homePage.dealer_cp_approve_confirm.MCA" /><br />
			<bean:message key="mcx.homePage.dealer_cp_approve_confirm.Type" /></td>
		</tr>
		<tr>
			<td height="10" colspan="6"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td class="padL20" colspan="6">
			<table width="100%" class="border" cellpadding="0" cellspacing="0">
				<tr>
					<td height="20" colspan="3" class="blue_bg2"><bean:message
						key="mcx.homePage.dealerdenyConfirm.head" /></td>
				</tr>
				<logic:greaterThan value="1" property="listSize"
					name="PendingApprovalForm">
					<tr class="grey_bg">
						<td width="5%" class="con9"><html:checkbox property="all"
							name="PendingApprovalForm" onclick="CheckAll(this)" /></td>
						<td width="95%" class="con9">ALL</td>
					</tr>
				</logic:greaterThan>
				<%int i = 0;%>
				<logic:iterate id="mcaList" name="PendingApprovalForm"
					property="mcaDetailsList">
					<%int rem = i % 2;
            if (rem == 0)
            {

            %>
					<tr class="normal">
						<%} else
            {%>
					<tr class="grey_bg">
						<%}

            %>
						<%++i;%>
						<td width="5%" class="con9"><html:multibox
							property="transaction.selectedMCAs" onclick="unCheck()">
							<bean:write name="mcaList" property="tmpltId"></bean:write>
						</html:multibox></td>
						<td width="95%" class="con9"><bean:write property="tmpltNm"
							name="mcaList" /></td>
					</tr>
				</logic:iterate>



				<tr>
					<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
				</tr>
			</table>
			</td>

		</tr>
		<tr>
			<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			</table>
			</td>
		</tr>
		<tr>
			<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="6" class="attach_txt padL60"></td>
				</tr>
				<tr>
					<td width="15">&nbsp;<html:hidden name="PendingApprovalForm"
						property="transaction.dealerCode" /></td>
					<td width="34">&nbsp;<html:hidden name="PendingApprovalForm"
						property="transaction.userNm" /></td>
					<td width="58">&nbsp;<html:hidden name="PendingApprovalForm"
						property="transaction.upLoadTime" /></td>
					<td width="58">&nbsp;<html:hidden name="PendingApprovalForm"
						property="transaction.pendingEnrollmentSuccess" /></td>
					<td width="81">&nbsp;</td>
					<td width="160">&nbsp;</td>
					<td width="89"><derivserv:access privilege="Modify">
						<html:button styleClass="enroll" property="Submit" value="SUBMIT"
							onclick="javascript:return fnReload();" />
					</derivserv:access></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<html:hidden name="PendingApprovalForm"
		property="transaction.submitParam" value="Approve" />
</html:form>
<script type="text/javascript">
fnOnLoad();
</script>
