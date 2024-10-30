<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Submission to Counterparty</title>
<link rel="stylesheet" type="text/css" href="/mcx/static/css/primary.css" />
<script language="JavaScript" src="/mcx/static/js/primary.js"></script>
</head>
<body class="padTop_attach">
<html:form action="/sendTemplateToCP" method="post">
<html:hidden property="opnInd" value="S" />	
<html:hidden property="tmpltId" />
<table width="376" border="0" cellpadding="0" cellspacing="0">
  <tr>
	<td class="attach_txt" colspan="7">Please ensure your firm's necessary internal review & approval(s) have been met and click Submit to send to your Counterparty for their review</td>
 </tr>
 <tr>
	<td height="10px" colspan="7"><img src="/mcx/static/images/spacer.gif" /></td>
 </tr>
  <tr>
    <td width="12">&nbsp;</td>
    <td width="29">&nbsp;</td>
    <td width="18">&nbsp;</td>
    <td width="83"><input class="enroll" type="submit" name="Submit" value="SUBMIT" />    </td>
    <td width="5">&nbsp;</td>
    <td width="229"><input class="enroll" type="button" value="CANCEL" onclick="self.close()"/>    </td>
  </tr>
  </table>
  </html:form>
</body>
</html:html>
