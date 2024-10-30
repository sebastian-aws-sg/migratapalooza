<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<script language="JavaScript" src="/mcx/static/js/enrollment.js"></script>
<script language="JavaScript" src="/mcx/static/js/dealerinfo_popup.js"></script>
<html:form action="/viewEnrollConfirm" method="post">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td height="14px" colspan="6"><img src="/mcx/static/images/spacer.gif" /></td>
				</tr>
				<tr>
					<td colspan="6" class="con1 padL20a"><bean:message key="mcx.enroll.confirm.confimration" /></td>
				</tr>
				<tr>
					<td class="con9b padL20a"><bean:message key="mcx.enroll.confirm.thankyou" /></td>
				</tr>
				<tr>
					<td height="14px" colspan="6" ><img src="/mcx/static/images/spacer.gif" /></td>
				</tr>
				<tr>
					<td class="padL20a" colspan="6" ><table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td width="67%" class="con1ab"><bean:message key="mcx.enroll.confirm.request" /></td>
                        <td width="33%" >&nbsp;</td>
                      </tr>
                    </table>
				    </td>
				</tr>
				<tr>
					<td height="14px" colspan="6" ><img src="/mcx/static/images/spacer.gif" /></td>
				</tr>
				<tr>
					<td class="padLR20">
						<table width="730px" class="border1" cellpadding="0" cellspacing="0">
							<tr>
								<td width="16%" height="26px" class="blue_bg2"><bean:message key="mcx.enroll.confirm.dealer" /> </td>
								<td width="70%" class="blue_bg2"><bean:message key="mcx.enroll.confirm.requestmcas" /></td>
							</tr>
							<%int count = 1;%>
							<logic:iterate id="selectedDealersList" indexId="index"
										name="EnrollmentForm" property="transaction.selectedDealersList" >
							<%if(count%2 == 0){%>
							<tr class="grey_bg">
							<% } else{%>
							<tr>
							<%} %>
								<td height="25" class="con9b padL12" style="width : 40%;word-break : break-all">
									<a href="javascript:popUpWindow('<bean:write name="selectedDealersList" property="dealerCode" />','<%=request.getContextPath()%>')" >
										<bean:write name="selectedDealersList" property="dealerName" filter="false" />
									</a>
								</td>
								<td class="con9b padL12" style="width = 60%"><bean:write filter="false" name="selectedDealersList" property="mcaNames" /></td>
							</tr>
							<% count++; %>
							</logic:iterate>
							</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" cellpadding="0" cellspacing="0" >
							<tr>
								<td height="14px" colspan="5" ><img src="/mcx/static/images/spacer.gif" /></td>
							</tr>
							<tr>
								<td colspan="5" align="right" class="padLR20">
									<html:button property="GetDealerList" styleClass="long_sendback"  value="SELECT MORE DEALERS" onclick="javascript:showDealerList()"></html:button>
								</td>
							</tr>
							<tr>
								<td height="14px" colspan="5" ><img src="/mcx/static/images/spacer.gif" /></td>
							</tr>
						</table>
					</td>
				</tr>
				
			</table>
		</td>
	</tr>
</table>
</html:form>