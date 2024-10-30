<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<head>
<title><bean:message key="mcx.enrollment.user_popup.title" /></title>
</head>
<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/primary.css" />
<script language="JavaScript" src="/mcx/static/js/dealerinfo_popup.js"></script>
<form method="post">

	<table width="605" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td class="attach_txt padL20"><bean:message
				key="mcx.enrollment.user_popup.title" /></td>
		</tr>
		<tr>
			<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td class="padLR5">
			<table width="100%" class="border" cellpadding="0" cellspacing="0">
				<tr>
					<td height="20px" colspan="2" class="blue_bg2_no_un_popup"><bean:message
						key="mcx.enrollment.user_popup.firmName" /></td>
				</tr>
				<tr>
					<td width="95%" class="con11" style="word-break:break-all"><bean:write name="FirmDetailsForm"
						property="dealerName" filter="false"/></td>
				</tr>
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
			<td colspan="4" class="attach_txt padL20">&nbsp;<bean:message
				key="mcx.enrollment.user_popup.primaryContact" /></td>
		</tr>
		<tr>
			<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td class="padLR5">
			<table width="100%" class="border" cellpadding="0" cellspacing="0">
				<tr>
					<td height="20px" class="blue_bg2_no_un_popup"><bean:message
						key="mcx.enrollment.user_popup.name" /></td>
					<td height="20px" class="blue_bg2_no_un_popup"><bean:message
						key="mcx.enrollment.user_popup.email" /></td>
					<td height="20px" class="blue_bg2_no_un_popup"><bean:message
						key="mcx.enrollment.user_popup.phone" /></td>
				</tr>
				<logic:iterate id="contact" name="FirmDetailsForm"
					property="firmDetails">
					<tr class="normal">
						<td width="31%" class="con11"><bean:write name="contact"
							property="primaryName" /></td>
						<td width="43%" class="con11"><bean:write name="contact"
							property="primaryEmail" /></td>
						<td width="20%" class="con11"><bean:write name="contact"
							property="primaryPhone" /></td>
					</tr>

					<tr class="grey_bg">
						<td width="31%" class="con11"><bean:write name="contact"
							property="secondaryName" /></td>
						<td width="43%" class="con11"><bean:write name="contact"
							property="secondaryEmail" /></td>
						<td width="20%" class="con11"><bean:write name="contact"
							property="secondaryPhone" /></td>
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
			<table border="0" cellpadding="0" cellspacing="0">
				<tr style="padding:0px 0px 0px 531px">										
					<td>
					<INPUT type="button" value="CLOSE" onclick="window.close()" class="enroll"/>					
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</form>
