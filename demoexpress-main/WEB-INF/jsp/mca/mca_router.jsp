<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>

<html:html>
<HEAD>
<TITLE>MCA Router</TITLE>
<script language="JavaScript" src="/mcx/static/js/mca_wizard.js"></script>
<script>
var scrolltop = document.documentElement.scrollTop;
</script>
</HEAD>
<BODY>
<html:form action="/save_amendment" method="post">
<logic:messagesPresent>
<logic:present name="SHOW_ALERT">
<script>
showAlert('<bean:write name="SHOW_ALERT" />');
var url = "/mcx/action/ViewMCA";
window.opener.location.href = url;
self.close();
</script>
</logic:present>
</logic:messagesPresent>
<logic:messagesNotPresent>
<logic:present name="TERMFORM">
<html:hidden styleId="tempId" name="TermForm"
						property="transaction.tmpltId" />												
<script>
var myObject = new Object();
myObject.refresh = "refresh";
myObject.tempId = document.TermForm.elements['transaction.tmpltId'].value;
window.returnValue = myObject;
self.close();
</script>
</logic:present>
<logic:present name="SAVE_TEMPLATE_SUCCESS">
<html:hidden styleId="temp1Id" name="TemplateForm"
						property="transaction.tmpltId" />												
<script>
var myObject = new Object();
myObject.refresh = "refresh";
myObject.tempId = document.getElementById('temp1Id').value;
window.returnValue = myObject;
self.close();
</script>
</logic:present>
<logic:present name="APPLY_TEMPLATE_SUCCESS">
<html:hidden styleId="tempId2" name="TemplateForm"
						property="transaction.tmpltId" />												
<html:hidden styleId="tempVal" name="TemplateForm" property="enableCustomize" />
<script>
var myObject = new Object();
myObject.refresh = "refresh";
myObject.tempId = document.getElementById('tempId2').value;
myObject.tempVal = document.getElementById('tempVal').value;
window.returnValue = myObject;
self.close();
</script>
</logic:present>
<logic:present name="SUBMIT_SUCCESS">
<script>
window.returnValue = "refresh";
self.close();
</script>
</logic:present>
<logic:present name="EXECUTE_MCA_SUCCESS">
<script>
window.returnValue = "refresh";
self.close();
</script>
</logic:present>
</logic:messagesNotPresent>
</html:form>
</BODY>
</html:html>
