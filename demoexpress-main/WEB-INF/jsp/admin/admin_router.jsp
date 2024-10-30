<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld"prefix="logic" %>

<html:html>
<HEAD>
<TITLE>DUMMY</TITLE>
</HEAD>
<BODY>
<html:form action="/ViewAdminISDATemplate" method="post" >
<logic:messagesPresent>
Error Page
</logic:messagesPresent>
<logic:messagesNotPresent>
<script>
var scrolltop = window.document.body.scrollTop;
window.returnValue = "refresh";
self.close();
</script>
</logic:messagesNotPresent>
</html:form>
</BODY>
</html:html>
