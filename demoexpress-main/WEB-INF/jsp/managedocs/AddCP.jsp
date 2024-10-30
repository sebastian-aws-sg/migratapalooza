
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>




<link rel="stylesheet" type="text/css" href="/mcx/static/css/primary.css" />
<script language="JavaScript" src="/mcx/static/js/managedocs.js"></script>



<html:form action="/mcaManageCP" onsubmit="return emptyCP();" method="post">
	<table width="400" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td class="blue_header border_comment"><bean:message
						key="mcx.ManageDocs.addCP.header" /></td>
		</tr>
		<tr>
			<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td class="comment_head_txt"><bean:message
						key="mcx.ManageDocs.addEditCP.Counterparty" /> <html:text styleId="dealerClientName"
				 property="transaction.dealerClientName" maxlength = "50" /></td>
		</tr>

		<tr>
			<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="46%" class="padL20">
						<html:hidden styleId="manageCPStatus" property="transaction.manageCPStatus" ></html:hidden>
						<html:hidden styleId="counterPartyId" property="counterPartyId" ></html:hidden>
						<html:hidden styleId="addRenameFlag" property="transaction.addRenameFlag" value = 'A' ></html:hidden>
					</td>
					<td width="46%" class="padL20">&nbsp;</td>
					<td width="92%" align="right">
						<html:submit value="SUBMIT" property = "ADDCP" onclick = "return emptyCP();" styleClass="enroll" >
						</html:submit>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
	</table>
</html:form>

<script type="text/javascript">

addload();
</script>
