<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/derivServ.tld" prefix="derivserv"%>
<script language="JavaScript" src="/mcx/static/js/enrollment.js"></script>
<script language="JavaScript" src="/mcx/static/js/dealerinfo_popup.js"></script>
<html:form action="/selectDealer" method="post">
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td height="10px" colspan="3"><img
				src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td colspan="3" class="con1 padL20a"><bean:message
				key="mcx.enroll.step1.selectdealers" /></td>
		</tr>
		<tr>
			<td colspan="3">
			<table width="100%" height="10px" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="con9b" align="right"><img
						src="/mcx/static/images/tick.gif" alt="tick" /><bean:message
						key="mcx.enroll.step1.alreadyenroll" /></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="3" height="10px"><img
				src="/mcx/static/images/spacer.gif" /></td>
		</tr>

		<tr>
			<td colspan="3" class="con1ab padL20a"><bean:message
				key="mcx.enroll.step1.fed18" /></td>
		</tr>
		<tr>
			<td height="10" colspan="3"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>

			<td colspan="3" class="padL20a">
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<%int count = 1;%>
					<bean:define id="fedDealerCount" name="EnrollmentForm"
						property="fedDealerCount" type="java.lang.Integer"></bean:define>
					
					<logic:iterate id="fedDealerList" indexId="index"
						name="EnrollmentForm" property="fedDealerList">

						<%if (count % fedDealerCount.intValue() == 1)
            {

            %>
						<td width="260px" height="150px" valign=top style="word-break: break-all">
						<table width="100%" class="border" cellpadding="0" cellspacing="0">
							<tr>
								<td
									<derivserv:access privilege="Modify">width="24%" colspan="2"</derivserv:access>
									class="blue_bg2_no_un"><derivserv:access privilege="Modify">
									<bean:message key="mcx.enroll.step1.enroll" />
								</derivserv:access></td>
								<td
									<derivserv:access privilege="Modify">width="76%" </derivserv:access>
									class="blue_bg1"><bean:message
									key="mcx.enroll.step1.dealername" /></td>
							</tr>
							<%}%>

							<%if (count % 2 == 0)
            {%>
							<tr class="grey_bg" height="25px">
								<%} else
            {%>
							<tr height="25px">
								<%}

            %>
								<derivserv:access privilege="Modify">
									<td class="con9d"><input type="checkbox"
										name="transaction.selectedDealers"
										value="<bean:write name="fedDealerList" property="dealerCode" />" />
									</td>
								</derivserv:access>

								<td><logic:match name="fedDealerList" property="enrollStatus"
									value="A">
									<span class="con9"><img src="/mcx/static/images/tick.gif"
										alt="tick" /></span>
								</logic:match> <logic:notMatch name="fedDealerList"
									property="enrollStatus" value="A">
												&nbsp;
												</logic:notMatch></td>
								<td class="con9c"><a class="linkDealer"
									href="javascript:popUpWindow('<bean:write name="fedDealerList" property="dealerCode" />','<%=request.getContextPath()%>')">
								<bean:write name="fedDealerList" property="dealerName" filter="false"/> </a>
								</td>
							</tr>
							<%if (count % fedDealerCount.intValue() == 0)
            {

            %>
						</table>
						</td>
						<td width="10">&nbsp;</td>
						<%}

            %>


						<%count++;

            %>
					</logic:iterate>

					<%int var = count - 1;
            if (count % fedDealerCount.intValue() != 1)
            {
                while (var % fedDealerCount.intValue() > 0)
                {
                    if (count % 2 == 0)
                    {%>
				<tr class="grey_bg" height="25px">
					<%} else
                    {%>
				<tr height="25px">
					<%}

                    %>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<%var++;
                    count++;
                }%>
			</table>
			</td>
			<%}

            %>


		</tr>
	</table>
	</td>
	</tr>
	<tr>
		<td colspan="3" height="10px"><img src="/mcx/static/images/spacer.gif" /></td>
	</tr>
	<bean:size id="otherDealerListSize" name="EnrollmentForm"
		property="otherDealerList" />
	<logic:greaterThan name="otherDealerListSize" value="0">
		<tr>
			<td colspan="3" class="con1ab padL20a"><bean:message
				key="mcx.enroll.step1.otherdealer" /></td>
		</tr>
		<tr>
			<td height="10px" colspan="3"><img
				src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td colspan="3" class="padL20a">
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<bean:define id="otherTableCount" name="EnrollmentForm"
						property="otherDealerCount" type="java.lang.Integer"></bean:define>
					<%count = 1;%>
					<logic:iterate id="otherDealerList" indexId="index"
						name="EnrollmentForm" property="otherDealerList">

						<%if (count % otherTableCount.intValue() == 1)
            {

            %>
						<td width="260px" valign=top style="word-break: break-all">
						<table width="100%" class="border" cellpadding="0" cellspacing="0">
							<tr>
								<td
									<derivserv:access privilege="Modify">width="24%"  colspan="2"</derivserv:access>
									class="blue_bg2_no_un"><derivserv:access privilege="Modify">
									<bean:message key="mcx.enroll.step1.enroll" />
								</derivserv:access></td>
								<td
									<derivserv:access privilege="Modify">width="76%" </derivserv:access>
									class="blue_bg1"><bean:message
									key="mcx.enroll.step1.dealername" /></td>
							</tr>
							<%}%>

							<%if (count % 2 == 0)
            {%>
							<tr class="grey_bg" height="25px">
								<%} else
            {%>
							<tr height="25px">
								<%}

            %>
								<derivserv:access privilege="Modify">
									<td class="con9d"><input type="checkbox"
										name="transaction.selectedDealers"
										value="<bean:write name="otherDealerList" property="dealerCode" />" />
									</td>
								</derivserv:access>

								<td align="center"><logic:match name="otherDealerList"
									property="enrollStatus" value="A">
									<span class="con9"><img src="/mcx/static/images/tick.gif"
										alt="tick" /></span>
								</logic:match> <logic:notMatch name="otherDealerList"
									property="enrollStatus" value="A">
													&nbsp;
													</logic:notMatch></td>
								<td class="con9c"><a class="linkDealer"
									href="javascript:popUpWindow('<bean:write name="otherDealerList" property="dealerCode" />','<%=request.getContextPath()%>')">
								<bean:write name="otherDealerList" property="dealerName" filter="false"/> </a>
								</td>
							</tr>
							<%if (count % otherTableCount.intValue() == 0)
            {

            %>
						</table>
						</div>
						</td>
						<td width="10">&nbsp;</td>
						<%}

            %>


						<%count++;

            %>
					</logic:iterate>
					<%int var1 = count - 1;
            if (count % otherTableCount.intValue() != 1)
            {
                while (var1 % otherTableCount.intValue() > 0)
                {
                    if (count % 2 == 0)
                    {%>
				<tr class="grey_bg" height="25px">
					<%} else
                    {%>
				<tr height="25px">
					<%}

                    %>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<%var1++;
                    count++;
                }%>
			</table>
			</td>
			<%}

        %>


		</tr>
		</table>
		</td>
		</tr>
	</logic:greaterThan>
	<tr>
		<td colspan="3" height="10px"><img src="/mcx/static/images/spacer.gif" /></td>
	</tr>
	<tr>
		<td colspan="3" height="10px">
		<table width="100%">
			<tr>
				<td class="con1 padL20a" align="right"><derivserv:access
					privilege="Modify">
					<input type="button" class="next"
						onclick="selectDealer(document.forms['EnrollmentForm'])"
						value="NEXT">
				</derivserv:access></td>
			</tr>
		</table>
		</td>
	</tr>
	</table>
</html:form>

</body>
</html>
