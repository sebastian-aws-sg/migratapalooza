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


<html:form styleId="deny_form" action="/dealerDenyEnrollments" method="post">
	<table width="492" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="6" class="blue_header border_comment"><bean:message
				key="mcx.homePage.dealerdenyConfirm.Deny" /></td>
		</tr>
		<tr>
			<td colspan="6">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="6" class="attach_txt padL20"><bean:message
						key="mcx.homePage.dealerdenyConfirm.Confirmation" /></td>
				</tr>
				<tr>
					<td width="13">&nbsp;</td>
					<td width="80">&nbsp;</td>
					<td width="70" class="attach_txt"><input type="radio"
						name="radioYes" value="yes" checked="checked" /><bean:message
						key="mcx.homePage.dealer_cp_approve_confirm.yes" /></td>
					<td width="9">&nbsp;</td>
					<td width="204" class="attach_txt"><input type="radio"
						name="radioYes" value="no" onclick="javascript:window.close();" /><bean:message
						key="mcx.homePage.dealer_cp_approve_confirm.no" /></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td height="30" colspan="6"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td class="padL20" colspan="6">
			<table width="100%" class="border" cellpadding="0" cellspacing="0">
				<tr>
					<td height="20" colspan="3" class="blue_bg2_cp"><bean:message
						key="mcx.homePage.dealerdenyConfirm.head" /></td>
				</tr>
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
						<td width="5%" class="con9"><html:multibox disabled="true"
							property="transaction.selectedMCAs">
							<bean:write name="mcaList" property="orgCltCd"></bean:write>-<bean:write
								name="mcaList" property="orgCltNm"></bean:write>-<bean:write
								name="mcaList" property="tmpltId"></bean:write>
						</html:multibox></td>
						<td width="95%" class="con9"><bean:write property="tmpltNm"
							name="mcaList" /></td>
					</tr>
				</logic:iterate>
			</table>
			</td>
		</tr>

		<tr>
			<td height="20"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td height="10" colspan="6"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>

			<td width="13">&nbsp;<html:hidden name="PendingApprovalForm"
				property="transaction.dealerCode" /></td>

			<td width="30">&nbsp;<html:hidden name="PendingApprovalForm"
				property="transaction.userNm" /></td>
			<td width="50">&nbsp;<html:hidden name="PendingApprovalForm"
				property="transaction.upLoadTime" /></td>

			<td width="9">&nbsp;<html:hidden name="PendingApprovalForm"
				property="transaction.pendingEnrollmentSuccess" /></td>

		</tr>
		<tr>
			<td colspan="6">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="6" class="attach_txt padL60"></td>
				</tr>
				<tr>
					<td width="15">&nbsp;</td>
					<td width="34">&nbsp;</td>
					<td width="58">&nbsp;</td>
					<td width="81">&nbsp;</td>
					<td width="159">&nbsp;</td>
					<td width="90"><derivserv:access privilege="Modify">
						<html:submit styleClass="enroll" property="Submit" value="SUBMIT"
							onclick="javascript:return fnReturn();" />
					</derivserv:access></td>

				</tr>
			</table>
			</td>
		</tr>

	</table>

	<html:hidden name="PendingApprovalForm"
		property="transaction.submitParam" value="Deny" />
</html:form>
<script type="text/javascript">
fnDenyOnLoad();
</script>

