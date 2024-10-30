<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/primary.css" />
<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/displaytag.css" />
<script language="JavaScript" src="/mcx/static/js/dealerinfo_popup.js"></script>
<script language="JavaScript" src="/mcx/static/js/pendingEnrollments.js"></script>
<script language="JavaScript" src="/mcx/static/js/grouping.js"></script>

<html:form action="/dealerPendingApproval" method="post">
	<table cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td height="10px" colspan="3"><img
				src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td>
			<TABLE>
				<tr>
					<td width="110%" class="con1 padL20a"><bean:message
						key="mcx.homePage.dealerApprovedFirms.pageTitle" /></td>
					<logic:notEmpty name="PendingApprovalForm"
						property="dealerDetailsList">
						<logic:equal name="PendingApprovalForm" property="printParam"
							value="N">
							<td><input class="attach" type="button" name="submit"
								value="PRINT VIEW"
								onclick="javascript:openPendingEnrollmentApproval('Y','A');" /></td>
						</logic:equal>
						<logic:equal name="PendingApprovalForm" property="printParam"
							value="Y">
							<td><input class="attach" type="button" name="submit"
								value="PRINT" onclick="window.print();" /></td>
						</logic:equal>
					</logic:notEmpty>
				</tr>
			</TABLE>
			</td>
		</tr>
		<tr>
			<td height="10" colspan="8"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<logic:empty name="PendingApprovalForm" property="dealerDetailsList">
			<tr>
				<td>
				<TABLE>
					<tr>
						<td colspan="1" class="con10 padL20a"><bean:message
							key="mcx.homePage.noRecords" /></td>
					</tr>
				</TABLE>
				</td>
			</tr>
			<tr>
				<td height="10" colspan="8"><img src="/mcx/static/images/spacer.gif" /></td>
			</tr>

			<tr>
				<td align="center">
				<table class="border" style="width:95.5%" cellspacing="0">
					<tr align="center">
						<td width="100" height="26" class="blue_bg2"><bean:message
							key="mcx.homePage.counterparty" /></td>
						<td width="120" height="26" class="blue_bg2"><bean:message
							key="mcx.homePage.MCAType" /></td>
						<td width="120" height="26" class="blue_bg2"><bean:message
							key="mcx.homePage.ApprovedBy" /></td>
						<td width="120" height="26" class="blue_bg2"><bean:message
							key="mcx.homePage.ApprovedOn" /></td>
						<td width="120" height="26" class="blue_bg2"><bean:message
							key="mcx.homePage.Status" /></td>
					</tr>
					<tr height="30" class="grey_bg">
						<td width="100" class="con9" />
						<td width="120" class="con9" />
						<td width="120" class="con9" />
						<td width="120" class="con9" />
						<td width="120" class="con9" />
					</tr>
				</table>
				</td>
			</tr>
		</logic:empty>
		<logic:equal name="PendingApprovalForm" property="printParam"
			value="N">
			<tr>
				<td align="center"><display:table
					name="sessionScope.PendingApprovalForm.dealerDetailsList"
					class="border" defaultsort="1" defaultorder="ascending"
					decorator="com.dtcc.dnv.mcx.decorator.MCXTableDecorator"
					requestURI="" style="width:95.5%" cellspacing="0">

					<display:column property="orgDlrNm" title="Counterparty"
						class="con9" headerClass="blue_bg2_cp_left1" sortable="true"
						defaultorder="ascending" group="1"
						style="width:20%;text-align:left;word-break:break-all" />
					<display:column property="tmpltNm" title="MCA Type" class="con11"
						headerClass="blue_bg2_cp_left2" sortable="true"
						defaultorder="ascending" style="width:32%;word-break:break-all" />
					<display:column property="rowUpdtName" title="Approved By"
						class="con11" headerClass="blue_bg2_cp_left" sortable="true"
						defaultorder="ascending" style="text-align:left;width:15%;word-break:break-all" />
					<display:column property="modifiedTime" title="Approved On"
						class="con11" headerClass="blue_bg2_cp_left"
						format="{0,date,MM/dd/yyyy HH:mm:ss z}" sortable="true"
						defaultorder="ascending" style="width:17%" />
					<display:column property="mcaStatusMsg" title="Status" class="con9"
						headerClass="blue_bg2_cp_left1" sortable="true"
						defaultorder="ascending" style="width:15%;text-align:left" />
				</display:table></td>
			</tr>
		</logic:equal>
		<logic:equal name="PendingApprovalForm" property="printParam"
			value="Y">
			<tr>
				<td align="center"><display:table
					name="sessionScope.PendingApprovalForm.dealerDetailsList"
					class="border" defaultsort="1" defaultorder="ascending"
					decorator="com.dtcc.dnv.mcx.decorator.MCXTableDecorator"
					requestURI="" style="width:99%" cellspacing="0">

					<display:column property="orgDlrNm" title="Counterparty"
						class="con9" headerClass="blue_bg2_cp_left1" sortable="true"
						defaultorder="ascending" style="width:20%;text-align:left;word-break:break-all" />
					<display:column property="templateName" title="MCA Type"
						class="con11" headerClass="blue_bg2_cp_left" sortable="true"
						defaultorder="ascending" style="width:32%;word-break:break-all" />
					<display:column property="rowUpdtName" title="Approved By"
						class="con11" headerClass="blue_bg2_cp_left" sortable="true"
						defaultorder="ascending" style="text-align:left" style="width:15%;word-break:break-all" />
					<display:column property="modifiedTime" title="Approved On"
						class="con11" headerClass="blue_bg2_cp_left"
						format="{0,date,MM/dd/yyyy HH:mm:ss z}" sortable="true"
						defaultorder="ascending" style="width:17%" />
					<display:column property="status" title="Status" class="con9"
						headerClass="blue_bg2_cp_left1" sortable="true"
						defaultorder="ascending" style="width:15%;text-align:left" />
				</display:table></td>
			</tr>
		</logic:equal>
		<tr>
			<td height="10px" colspan="7"><img
				src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<logic:notEmpty name="PendingApprovalForm"
			property="dealerDetailsList">
			<tr>
				<td colspan="7" class="con9_space" style="padding:0px 0px 0px 18px"><img
					src="/mcx/static/images/lock.gif" alt="Locked by current user" /><bean:message
					key="mcx.homepage.pendingmca.current_user" /><img
					src="/mcx/static/images/unlock.gif" alt="Locked by another user" /><bean:message
					key="mcx.homepage.pendingmca.other_user" /></td>
			</tr>
		</logic:notEmpty>
		<tr>
			<td colspan="7">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="7">&nbsp;</td>
		</tr>
	</table>
</html:form>
