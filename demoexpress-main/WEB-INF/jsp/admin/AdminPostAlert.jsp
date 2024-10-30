<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<script language="JavaScript" src="/mcx/static/js/alerts.js"></script>

<html:form action="/adminPostAlert" method="post">
<html:hidden property="transaction.message" styleId="message"/> 
<html:hidden property="postaction"/>
<html:hidden property="transaction.subject" styleId="subject"/>

		<table  width="94%" cellpadding="0" border="0" cellspacing="0">

			<tr>

				<td height="14px"  colspan="4"><img src="/mcx/static/images/spacer.gif" /></td>

			</tr>

			<tr>

				<td width="69%" colspan="4" class="con1 padL20a"><bean:message key="alert.postalert"/></td>

			</tr>
			<tr>

				<td height="14px"   colspan="4"><img src="/mcx/static/images/spacer.gif" /></td>

			</tr>

			<tr >
				<td>
				<table  width="70%" cellpadding="0" border="0" cellspacing="0">
				
				<tr>

				<td height="14px" colspan="4"><img src="/mcx/static/images/spacer.gif" /></td>

				</tr>
				<tr>
				<td  width="15%" colspan="2" class="con9b1" valign="top"><bean:message key="alert.subject"/>:</td>
				<td width="69%" align="left" colspan="4" class="con9b1">
				<input type=text name="subjectval" size="100" maxlength="150"/> 
				</td>
				</tr>
			
				<tr>
				<td  width="15%" colspan="2" class="con9b1" valign="top"><bean:message key="alert.message"/>:</td>
				<td width="69%" align="left" colspan="4" class="con9b1">
				<textarea cols="90"
					rows="10" name="textmessage" wrap="off" onKeyUp="alerttextCounter(this.form.textmessage,32000);"></textarea>
				</td>
				</tr>
				<tr>

				<td height="14px" colspan="4"><img src="/mcx/static/images/spacer.gif" /></td>

				</tr>
	<tr>
 				<td  width="15%" colspan="2" class="con9b1" valign="top"></td>
        
		    <td lign="left" colspan="3" class="padL90"> &nbsp; &nbsp; <input class="save" type="button" 
			name="submitalert" value="<bean:message key='alert.postalert2'/>" onclick="alertsend();" /> <input class="enroll"
			type="button" name="cancelalert" value="<bean:message key='alert.cancel'/>" onclick="alertcancel();" /></td>
	</tr>
	<tr>

		<td height="10px" colspan="4"><img src="/mcx/static/images/spacer.gif" /></td>

	</tr>
	<tr>

		<td height="10px" colspan="4"><img src="/mcx/static/images/spacer.gif" /></td>

	</tr>
	<tr>

		<td height="10px" colspan="4"><img src="/mcx/static/images/spacer.gif" /></td>

	</tr>


				</table>
				</td>
			</tr>


	</table>
</html:form>