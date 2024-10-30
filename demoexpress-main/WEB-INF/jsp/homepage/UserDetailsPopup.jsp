<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<head>
<title><bean:message key="mcx.homepage.user_popup.title" /></title>
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
				key="mcx.homepage.user_popup.title" /></td>
		</tr>
		<tr>
			<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td class="padLR5">
			<table width="100%" class="border" cellpadding="0" cellspacing="0">
				<tr>
					<td height="20px" colspan="2" class="blue_bg2_no_un_popup"><bean:message
						key="mcx.homepage.user_popup.firmName" /></td>
				</tr>
				<tr>
					<td width="100%" class="con11" style="word-break: break-all"><bean:write name="UserDetailsForm"
						property="userDetails.firmName" filter="false"/></td>
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
				
					<tr class="normal">
						<td width="31%" class="con11"><bean:write name="UserDetailsForm"
							property="userDetails.userName" /></td>
						<td width="43%" class="con11"><bean:write name="UserDetailsForm"
							property="userDetails.userEmail" /></td>
						<td width="20%" class="con11"><bean:write name="UserDetailsForm"
							property="userDetails.phoneNumber" /></td>
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
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="15">&nbsp;</td>
					<td width="34">&nbsp;</td>
					<td width="58">&nbsp;</td>
					<td width="81">&nbsp;</td>
					<td width="160">&nbsp;</td>
					<td width="89">
					<INPUT type="button" value="CLOSE" onclick="window.close()" class="enroll"/>					
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</form>
