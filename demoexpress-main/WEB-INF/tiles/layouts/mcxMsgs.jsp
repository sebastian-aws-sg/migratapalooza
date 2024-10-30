<%@ taglib uri="/WEB-INF/tlds/struts-html.tld"  prefix="html"  %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld"  prefix="bean"  %>

    <logic:messagesPresent message="true">
    <tr>
        <td colspan="3" style="word-break: break-all">
	        <ul>
	            <html:messages id="message" message="true">
	                <li><font color="red"><bean:write name="message" /></font></li>
	            </html:messages>
	        </ul>
        </td>
    </tr>       
    </logic:messagesPresent>			
