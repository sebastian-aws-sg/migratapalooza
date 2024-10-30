<html>
<%@ taglib uri="/WEB-INF/struts-html.tld"      prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"      prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"     prefix="logic" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>DTCC - MCA - Xpress - Home</title>
<link rel="stylesheet" type="text/css" href="/mcx/static/css/primary.css" />
<script language="JavaScript" src="/mcx/static/js/mca_wizard.js"></script>
<script language="JavaScript" src="/mcx/static/js/Calendar.js"></script>
</head>
<body class="padTop_attach">
<html:form action="/executeMCA" onsubmit="return submitExecuteMCA()" method="post">
<html:hidden property="opnInd" value="S" />
<html:hidden property="tmpltId" />
<table width="376" border="0" cellpadding="0" cellspacing="0">
<logic:equal name="TemplateForm" property="actingDealer" value="true" >
  <tr>
	<td class="attach_txt" colspan="7">Please ensure your firm's necessary internal review & approval(s) have been met and click "Execute" for a message to be sent to your Counterparty that the MCA is executed from the date entered below</td>
 </tr>
 <tr>
	<td height="10px" colspan="7"><img src="/mcx/static/images/spacer.gif" /></td>
 </tr>
  <tr >
    <td width="45%" class="attach_txt">MCA Execution Date: </td>
	<td width="32%" class="attach_txt"><html:text name="TemplateForm" property="publishDt" size="11" />  </td>
	<td width="3%">&nbsp;</td>
    <td width="3%" class="attach_txt"><a href="javascript:show_calendar('TemplateForm.publishDt', null, null, 'MM/DD/YYYY', 'no')"><img src="/mcx/static/images/cal.gif" style="cursor:hand" border="0" alt="Pick a date" ></a> </td>
  </tr>
  <tr>
	<td height="10px" colspan="7"><img src="/mcx/static/images/spacer.gif" /></td>
 </tr>
  <tr>
    <td colspan="6" class="attach_txt">&nbsp;</td>
  </tr>
  <tr>
    <td width="12">&nbsp;</td>
    <td width="29">&nbsp;</td>
    <td width="18">&nbsp;</td>
    <td width="83"><input class="enroll" type="button" name="Execute" value="EXECUTE" onclick="submitExecuteMCA()" />
    </td>
    <td width="5">&nbsp;</td>
    <td width="229"><input class="enroll" type="button" name="Cancel" value="CANCEL" onclick="cancel()"/>
    </td>
  </tr>
  </logic:equal>
<logic:equal name="TemplateForm" property="actingDealer" value="false" >
  <tr>
	<td class="attach_txt" colspan="7">Please ensure your firm's necessary internal review & approval(s) have been met and click "Final Execution" for a message to be sent to your Dealer that the MCA can be executed</td>
 </tr>
 <tr>
	<td height="10px" colspan="7"><img src="/mcx/static/images/spacer.gif" /></td>
 </tr>
  <tr>
    <td colspan="6" class="attach_txt">&nbsp;</td>
  </tr>
  <tr>
    <td width="12">&nbsp;</td>
    <td width="29">&nbsp;</td>
    <td width="18">&nbsp;</td>
    <td width="83"><input class="long" type="button" name="Execute" value="FINAL EXECUTION" onclick="submitExecuteMCA()" />
    </td>
    <td width="5">&nbsp;</td>
    <td width="229"><input class="enroll" type="button" name="Cancel" value="CANCEL" onclick="cancel()"/>
    </td>
  </tr>
  </logic:equal>
  </table>
  </html:form>
</body>
</html>