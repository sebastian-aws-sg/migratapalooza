<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>
<html:html>
<head>
<title>Save Template</title>
<link rel="stylesheet" type="text/css" href="/mcx/static/css/primary.css" />
<script language="JavaScript" src="/mcx/static/js/mca_wizard.js"></script>
<script>
ISDATmpltNm = '<bean:write name="TemplateForm" property="transaction.ISDATmpltNm" />';
tmpltType 	= '<bean:write name="TemplateForm" property="transaction.tmpltTyp" />';
</script>
</head>
<body class="padTop_attach">
<html:form action="/saveTemplate" method="post" onsubmit="return submitForm()" >
<table width="420" border="0" cellpadding="0" cellspacing="0"> 
<html:hidden property="tmpltId" />
<html:hidden property="opnInd" />
<html:hidden name="TemplateForm" property="transaction.tmpltTyp" />
<html:hidden name="TemplateForm" property="transaction.ISDATmpltNm" />
<html:hidden name="TemplateForm" property="transaction.tmpltNm" />
  <tr>
    <td class="attach_txt padL20" colspan="6">File name: <br /><label class="save_file_txt"><bean:write name="TemplateForm" property="transaction.ISDATmpltNm" /> </label> <html:text name="TemplateForm" property="transaction.nxtTmpltNm" onchange="validate(this)" maxlength="30" /></td>
  </tr>
    <tr>
	<td height="10px" colspan="7"><img src="/mcx/static/images/spacer.gif" /></td>
 </tr>
  <tr>
    <td width="12">&nbsp;</td>
    <td width="30">&nbsp;</td>
    <td width="147">&nbsp;</td>
    <td width="80"><input class="enroll" type="button" name="Save" value="SAVE" onclick="submitForm()" />
    </td>
    <td width="8">&nbsp;</td>
    <td width="123">&nbsp;
    </td>
  </tr>
  </table>
<logic:present name="SHOW_ALERT" >
<script>
showAlert('<bean:write name="SHOW_ALERT" />');
</script>
</logic:present>
  
  </html:form>
</body>
</html:html>
