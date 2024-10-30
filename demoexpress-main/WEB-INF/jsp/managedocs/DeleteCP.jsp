<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>


<head>
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta HTTP-EQUIV="Expires" CONTENT="-1">
<title><bean:message key="mcx.ManageDocs.deleteCP.header" /></title>
</head>

<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/primary.css" />
<script language="JavaScript" src="/mcx/static/js/managedocs.js"></script>
<body onload = "deleteload()">
<html:form action="/deleteReassignCPWindow" onsubmit="return deleteReassignButton();" method="post">

	<table width="99%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td class="blue_header border_comment"><bean:message
				key="mcx.ManageDocs.deleteCP.header" /></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">

				<tr>
					<td colspan="6" align="center" class="attach_txt ">Are you sure you want to
					delete the Counterparty?</td>
				</tr>
				<tr>
					<td width="13">&nbsp;</td>
					<td width="30">&nbsp;</td>
					<td width="100">&nbsp;</td>
					<td width="70" class="attach_txt"><input name="reassign"
						type="radio" value="Yes"
						onclick="cplistStatus('<bean:write name="ManagedDocumentForm" property="documentInd"/>')"
						checked="true" />Yes</td>
					<td width="9">&nbsp;</td>
					<td width="204" class="attach_txt"><input name="reassign"
						type="radio" value="No"
						onclick="cplistStatus('<bean:write name="ManagedDocumentForm" property="documentInd"/>')" />No
					</td>
				</tr>
			</table>
			</td>
		</tr>

		<tr>

			<td width="46%" class="padL20"><html:hidden styleId="manageCPStatus"
				property="transaction.manageCPStatus"></html:hidden><html:hidden 
				styleId="deleteCmpnyId" property="transaction.deleteCmpnyId"></html:hidden>
				<html:hidden styleId="documentInd" property="documentInd"></html:hidden>
				<html:hidden styleId="duplicateDocsCheck" property="transaction.duplicateDocsCheck"></html:hidden></td>

		</tr>

		<tr>
			<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>

		<logic:equal name="ManagedDocumentForm" property="documentInd"
			value="Y">
			<tr>
				<td class="comment_head_txt">There are documents related to this
				Counterparty name.<br />
				Please select a Counterparty to transfer documents to, else they
				will be deleted.</td>
			</tr>
		</logic:equal>

		<tr>
			<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td>
			<table width="432" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<logic:equal name="ManagedDocumentForm" property="documentInd"
						value="Y">
						<td width="46%" class="padL20"><html:select name="ManagedDocumentForm"
							styleId="reassignedDealerClient"  
							property="transaction.reassignedDealerClient" styleClass="drop1">
							<html:option value="default">-- Select a Counterparty --</html:option>
							<html:optionsCollection property="reassignList" label="orgCltNm" filter="false"
								value="orgCltCd">
							</html:optionsCollection>
						</html:select></td>
					</logic:equal>

					<logic:notEqual name="ManagedDocumentForm" property="documentInd"
						value="Y">
						<td width="46%" class="padL20">&nbsp;</td>
					</logic:notEqual>

					<td width="46%" class="padL20">&nbsp;</td>
					<td width="92%" align="right"><html:submit value="SUBMIT"
						property="DELETECP" styleClass="enroll"
						onclick="return deleteReassignButton();">
					</html:submit></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
	</table>

	<script>
	
	setCPListStatus('<bean:write name="ManagedDocumentForm" property="documentInd"/>');
</script>

</html:form>
</body>
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta HTTP-EQUIV="Expires" CONTENT="-1">