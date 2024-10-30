<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<script language="JavaScript" src="/mcx/static/js/enrollment.js"></script>
<script language="JavaScript" src="/mcx/static/js/dealerinfo_popup.js"></script>
<html:form action="/enrollDealer" method="post">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
	<td>
		<table cellpadding="0" cellspacing="0">
			<tr>
			<td height="10px" colspan="6"><img src="/mcx/static/images/spacer.gif" /></td>
			</tr>
			<tr>
			<td colspan="6" class="con1 padL20a"><bean:message key="mcx.enroll.step2.selectmca" /></td>
			</tr>
			<tr>
				<td height="14px" colspan="6" ><img src="/mcx/static/images/spacer.gif" /></td>
			</tr>
			<tr>
				<td class="padLR20">
					<table width="730px" class="border1" cellpadding="0" cellspacing="0" style="word-break: break-all">
					
						<logic:iterate id="selectedDealersList" indexId="index"
						name="EnrollmentForm" property="transaction.selectedDealersList" >
								
							<tr>
								<td height="26px" class="blue_bg2_cp" ><bean:write name="selectedDealersList" property="dealerName" filter="false"/></td>
							    <td height="26px" class="blue_bg2_cp" NOWRAP><a style="cursor:hand;margin-right:5px" onclick="javascript:{window.showModalDialog('/mcx/action/selectMCA?dealerCode=<bean:write name="selectedDealersList" property="dealerCode" />',self,'top:50;left:50px;status:no;dialogHeight:700px;dialogWidth:900px;status:no;help:no;unadorned:yes')}"><bean:message key="mcx.enroll.step2.selectmcalink" /></a></td>							</tr>
							<tr>
								<td height="10px" colspan="2"><img src="/mcx/static/images/spacer.gif" /></td>
							</tr>
							<tr>
								<td colspan="2" class="blue_con" >
									<bean:message key="mcx.enroll.step2.mcaselected" />
								</td>
							</tr>
							<tr>
								<td width="86%" class="blue_con1" id="<bean:write name="selectedDealersList" property="dealerCode" />Name">
									<textarea  name="EnrollmentForm" class="mcaTextArea" cols="150"   readonly><bean:write name="selectedDealersList" property="mcaNames" /></textarea>
									<input type="hidden" name="transaction.selectedDealers" value="<bean:write name="selectedDealersList" property="dealerCode" />" />
								</td>
								<td width="14%" class="blue_con1" id="<bean:write name="selectedDealersList" property="dealerCode" />Id">
									<html:hidden name="EnrollmentForm" property="transaction.mcaNames" value=""></html:hidden>
									<html:hidden name="EnrollmentForm" property="transaction.mcaIds" value=""></html:hidden>
									<input type="hidden" id="<bean:write name="selectedDealersList" property="dealerCode" />" value="<bean:write name="selectedDealersList" property="dealerName" />">
								</td>
							</tr>
							<tr>
								<td height="10px" colspan="2"><img src="/mcx/static/images/spacer.gif" /></td>
							</tr>
						</logic:iterate>
					</table>
	</td>
</tr>
         <tr>
		 <td>  
		   <table width="750" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="10px" colspan="2"><img src="/mcx/static/images/spacer.gif" /></td>
			</tr>
			<tr>
				<td align="right" width="659">&nbsp;</td>
				<td width="10"><img src="/mcx/static/images/spacer.gif" /></td>
				<td align="right" width="81">
					<input type="button" class="enroll" onclick="enrollDealer(document.forms['EnrollmentForm'])" value="ENROLL" >
				</td>
			</tr>
			</table>
		</td>
		</tr>
</table>
</html:form>
