<%@ taglib uri="/WEB-INF/tlds/struts-html.tld"  prefix="html"  %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld"  prefix="bean"  %>

   <logic:messagesPresent>
    <tr>
        <td colspan="3">
	        <b><font color="red">There were errors:</font></b>
	        <ul>
	            <html:messages id="error">
	                <li><font><bean:write name="error" /></font></li>
	            </html:messages>
	        </ul>
        </td>
    </tr>
    </logic:messagesPresent>			

