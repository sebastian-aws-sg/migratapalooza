<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<html:html>
<head>

<title><bean:message key="mcx.title"/></title>

<LINK rel="stylesheet" href="/mcx/static/css/primary.css" type="text/css">

</head>

<body>

<html:form action="/DSSignon" method="post">

	<html:hidden property="signonStep" />

	<table width="100%" class="sign_in_border" cellpadding="0" cellspacing="0">
		<tr>
			<td class="pad">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">

				<tr bgcolor="#336633">
					<td width="72%"><img src="/mcx/static/images/home_header.gif"
						width="712" height="81" alt="" />
				    </td>
				</tr>

				<tr>
					<td class="pad">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr bgcolor="#f7f7f9">
  						  <td align="center" valign="middle" class="pad_part">
  						    <br><br><br>
  						   	<table border="0" class="ellipse_bg" cellpadding="0" cellspacing="0">
							    <tr>
									<td height="80" align="center" valign="middle" class="con2">

										<logic:messagesPresent>
											<html:errors />
											<br>
										</logic:messagesPresent>

										<logic:equal name="DSSignonForm" property="signonStep" value="usertypes">
											<logic:notEqual name="DSSignonForm" property="signonObject"	value="S">
												<bean:message key="mcx.user.initialMessage" />
											</logic:notEqual>
										</logic:equal>

										<logic:equal name="DSSignonForm" property="signonStep" value="mcxsuperuser">
											<logic:notEmpty name="DSSignonForm"	property="signonWorkflowValue">
												<bean:message key="mcx.superuser.initialMessage" />
											</logic:notEmpty>
										</logic:equal>

										<br>
										<br>
										<logic:equal name="DSSignonForm" property="signonStep"	value="mcxsuperuser">

											<logic:notEmpty name="DSSignonForm"	property="signonWorkflowValue">
											    <IMG border="0" src="/mcx/static/images/clearpixel.gif" width="15" height="1"><html:select property="signonWorkflowValue" styleId="signonWorkflowValue">
													<html:optionsCollection property="signonObject"	label="label" value="value" filter="false"/>
												</html:select><IMG border="0" src="/mcx/static/images/clearpixel.gif" width="15" height="1">
											</logic:notEmpty>

											<logic:empty name="DSSignonForm" property="signonWorkflowValue">
												<logic:notEqual name="DSSignonForm" property="signonObject"	value="true">
													<html:select property="signonWorkflowValue"	styleId="signonWorkflowValue">
														<html:optionsCollection property="signonObject" label="label" value="value" filter="false"/>
													</html:select>
												</logic:notEqual>
											</logic:empty>

										</logic:equal>

										<logic:equal name="DSSignonForm" property="signonStep" value="usertypes">
											<html:select property="signonWorkflowValue"	styleId="signonWorkflowValue">
												<html:optionsCollection property="signonObject"	label="label" value="value" filter="false"/>
											</html:select>
										</logic:equal>
                                   </td>
								</tr>
								<tr>
									<td height="50" align="center" valign="top" class="padLT">
									  <input name="submit" type="submit" class="enroll"	value="SUBMIT">
									</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr bgcolor="#f7f7f9">
						  <td>
						    <IMG border="0" src="/mcx/static/images/clearpixel.gif" width="1" height="140">
						  </td>
						</tr>
					</table>
					</td>
				</tr>

			</table>
			</td>
		</tr>
	</table>

</html:form>

</body>

</html:html>
