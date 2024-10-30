<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>


<table border="0" width="100%">
<tr>
<td><html:link action="welcome"> Welcome page </html:link></td>
<td><html:link action="taskschedule"> Schedule Data Synch Page </html:link></td>
</tr>
<tr>
	<td colspan="2">
	<form action="employeedetail.do" method="get">
		Get Employee Detail for <input type="text" max="10"><input type="submit"> 
	</form>
	</td>
</tr>
</table>
