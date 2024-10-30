<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>
<html:html>
<head>
<title>Apply Template to counterparty Apply</title>
<link rel="stylesheet" type="text/css" href="/mcx/static/css/primary.css" />
<script language="JavaScript" src="/mcx/static/js/mca_wizard.js"></script>
</head>
<body class="padTop_attach">
<html:form action="/applyTemplateToCP" method="post">
<html:hidden property="enableCustomize" />
<html:hidden property="cltCd" />
<html:hidden property="cltNm" />
<logic:empty name="ENROLLEDCPs" >
<table width="480" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td class="comment_head_txt">There are no Enrolled Clients for this Template.</td>
  </tr>
  <tr>
    <td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
  </tr>
   <tr align="right" >
     <td><input class="enroll" type="button" name="Submit2" value="CLOSE" onclick="self.close()" /></td>
    </tr>
  
</table>
</logic:empty>
<logic:notEmpty name="ENROLLEDCPs" >
<logic:present name="ENROLL_CP">
<table width="480" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td class="comment_head_txt">The selected Client has not enrolled for this Template.</td>
  </tr>
  <tr>
    <td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
  </tr>
   <tr align="right" >
     <td><input class="enroll" type="button" name="Submit2" value="CLOSE" onclick="self.close()" /></td>
    </tr>
  
</table>
</logic:present>
<logic:notPresent name="ENROLL_CP">
<table width="480" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td class="comment_head_txt">Are you sure you want to apply this MCA template to a counterparty?</td>
  </tr>
  <tr>
    <td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
  </tr>
  <tr>
    <td>
	   <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="19">&nbsp;</td>
        <td width="71" class="attach_txt">
        <input type="radio" checked>Yes
    	</td>
    	<td width="10">&nbsp;</td>
    	<td width="337" class="attach_txt" >
        <input type="radio" onclick="self.close()" >No
    </td>
      </tr>
    </table>
	</td>
	</tr>
	<tr>
    <td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
  </tr>
    <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="92%" class="padL20">
        <html:select name="TemplateForm" property="applyToCP" styleClass="apply_temp">
        <html:optionsCollection name="ENROLLEDCPs" label="value" value="key" />
        </html:select>
        </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td colspan="5" class="attach_txt padL60"></td>
        </tr>
      <tr>
        <td align="right" width="44"><input type="checkbox" name="customize" value="true"  /></td>
      <td width="353" class="con9">Click here to go to Dealer Wizard Step2 & Further Customize</td>
     <td width="83"><input class="enroll" type="button" value="SUBMIT" onclick="apply()" /></td>
      </tr>
    </table></td>
  </tr>
  </table>
</logic:notPresent>
  </logic:notEmpty>
  <script>
  function apply()
  {
  	var tmpltId 	= '<bean:write name="TemplateForm" property="transaction.tmpltId" />';
  	var tmpltType 	= '<bean:write name="TemplateForm" property="transaction.tmpltTyp" />';
  	var ISDATmpltNm	= '<bean:write name="TemplateForm" property="transaction.ISDATmpltNm" />';
	document.forms[0].cltCd.value=document.forms[0].applyToCP[document.forms[0].applyToCP.selectedIndex].value
	document.forms[0].cltNm.value=document.forms[0].applyToCP[document.forms[0].applyToCP.selectedIndex].text

  	if(document.forms[0].customize.checked)
  	{
  		document.forms[0].enableCustomize.value = "true";
  	}
  	else
  	{
  		document.forms[0].enableCustomize.value = "false";
  	}
  	document.forms[0].action = "/mcx/action/applyTemplateToCP?transaction.tmpltId="+tmpltId+"&transaction.tmpltTyp="+tmpltType+"&transaction.ISDATmpltNm="+ISDATmpltNm;
  	document.forms[0].submit();
  }
  </script>
  </html:form>
</body>
</html:html>
