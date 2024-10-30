<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*" %>
<%@ page import="com.mytelco.common.CommonUtility" %>


<table width="100%" height="100%">
<tr>
<td>
<html:errors/>
<html:form action="/submitLogon.do" focus="userId">
             <table  width="100%">
				<tr><td colspan="2"><p align="center">Please Enter Your Login Information. </p></td></tr>
                <tr><td> Login ID: </td>
                    <td align="left" width='100%'> <html:text property="userId" size="22" maxlength="22"/>
                    </td>
                </tr>
				<tr><td> Password: </td>
                    <td align="left" width='100%'> <html:text property="password" size="22" maxlength="22"/>
                    </td>
                </tr>
                <tr><td colspan="2">
                        <html:submit property="submit" value="Enter"/>
                        </input>
                    </td>
                </tr>
            </table>
    </html:form>
</td>
</tr>
</table>



