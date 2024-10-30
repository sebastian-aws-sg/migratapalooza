<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/primary.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />
	<script language="JavaScript" src="/mcx/static/js/alerts.js"></script>


<html:form action="/adminViewAlert" method="post">
		<table width="94%" cellpadding="0" cellspacing="0" border="0">

			<tr>

				<td height="14" colspan="4"><img src="/mcx/static/images/spacer.gif" /></td>

			</tr>

			<tr>

				<td width="69%" colspan="4" class="con1 padL20a"><bean:message key="alert.viewalert"/></td>

			</tr>

			<tr>

				<td height="14px" colspan="4"><img src="/mcx/static/images/spacer.gif" /></td>

			</tr>
			<tr>
				<td colspan="4" class="padL20a">
				<table width="800" height="153" class="border" cellpadding="0"
					cellspacing="0" border="0">

					<tr>
						<td width="100%" height="26px" class="product_bg1 border4a1"><b>&nbsp;&nbsp;<bean:message key="alert.sentby"/>:&nbsp;&nbsp;</b><c:out value="${alertinfo.userName}"/><br /><b>&nbsp;&nbsp;<bean:message key="alert.senton"/>:&nbsp;&nbsp;</b><c:out value="${alertinfo.updatetimestamp}"/><br /><b>&nbsp;&nbsp;<bean:message key="alert.subject"/>:&nbsp;&nbsp;</b><c:out value="${alertinfo.subject}"/></td>
					</tr>
					<tr>

						<td height="26px" class="con9">
						<div id="msgstr">
						<Script>alertgetHtml('<c:out value="${alertinfo.message}"/>');</Script>
						</div>
						</td>

					</tr>


					<tr>

						<td height="10px" colspan="4"><img src="/mcx/static/images/spacer.gif" /></td>

					</tr>

					<tr>

						<td width="24%">&nbsp;</td>

					</tr>

					<tr>

						<td colspan="4">&nbsp;</td>

					</tr>

					<tr>

						<td colspan="4">&nbsp;</td>

					</tr>
				</table>
    </td>
    </tr>
<tr>
		<td height="14px" colspan="4"><img src="/mcx/static/images/spacer.gif" /></td>
	</tr>
	<tr>
	    <td align="left" colspan="3" class="padL20a"><input class="save" type="submit"
					name="submit" value="<bean:message key='alert.backtoalerts'/>" /> </td>
	</tr>
	</table>


</html:form>