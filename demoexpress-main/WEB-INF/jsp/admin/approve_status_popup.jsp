<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Confirm</title>
<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/primary.css" />
<script language="JavaScript" src="/mcx/static/js/primary.js"></script>
<script language="JavaScript" src="/mcx/static/js/jsDate.js"></script>
<script language="JavaScript" src="/mcx/static/js/Calendar.js"></script>
<script language="JavaScript"
	src="/mcx/static/js/mca_setup_term_details.js"></script>
</head>

<body class="padTop_attach">
<html:form action="/ApproveRejectAdminTemplate" onsubmit="javascript: return approveTemplate();" method="post" >
	<html:hidden name="TemplateForm" property="appRejStatus" value="P"/>
	<table width="376" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td class="attach_txt" colspan="7">Prior to external posting ensure
			the necessary internal review & approval(s) have been met ?</td>
		</tr>
		<tr>
			<td height="10px" colspan="7"><img
				src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td height="10px" colspan="7">
			<table width="300" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="35%" class="attach_txt">MCA Agreement Date :</td>
					<td width="27%" class="attach_txt" align="left"><html:text name="TemplateForm"
						property="publishDt" size="11" /></td>
					<td width="3%" class="attach_txt" align="left"><a
						href="javascript:show_calendar('TemplateForm.publishDt', null, null, 'MM/DD/YYYY', 'no')"><img
						src="/mcx/static/images/cal.gif" width="16" height="16"
						style="cursor:hand" border="0" alt="Pick a date"></a></td>
				</tr>
			</table>
			</td>
		</tr>


		<tr>
			<td height="10px" colspan="7"><img
				src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td colspan="6" class="attach_txt">&nbsp;</td>
		</tr>
		<tr>
			<td width="12">&nbsp;</td>
			<td width="29">&nbsp;</td>
			<td width="18">&nbsp;</td>
			<td width="83"><input class="enroll" type="button" name="Submit"
				value="POST" onclick="javascript:approveTemplate();" /></td>
			<td width="5">&nbsp;</td>
			<td width="229"><input class="enroll" type="button" name="Submit2" 
				value="CANCEL"  onclick="window.close()" /></td>
		</tr>
	</table>
</html:form>
</body>
</html>
