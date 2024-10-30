<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/derivServ.tld" prefix="derivserv" %>
<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/primary.css" />
<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/displaytag.css" />
<head>
<title><bean:message key="mcx.homepage.pendingmca.title" /></title>
</head>
<script language="JavaScript" src="/mcx/static/js/dealerinfo_popup.js"></script>
<script language="JavaScript" src="/mcx/static/js/grouping.js"></script>
<form method="post">
<table cellpadding="0" cellspacing="0">
	<tr>
		<td height="10px" colspan="3"><img src="/mcx/static/images/spacer.gif" /></td>
	</tr>
	<tr>
		<td>
		<TABLE>
			<tr>
				<td width="110%" class="con1 padL20a"><bean:message
					key="mcx.homepage.pendingmca.title" /></td>
				<logic:notEmpty name="DisplayMCAsForm" property="dealerDetailsList">
					<logic:equal name="DisplayMCAsForm" property="printParam" value="N">
						<td><input class="attach" type="button" name="submit"
							value="PRINT VIEW" onclick="javascript:openPending('P','Y');" /></td>
					</logic:equal>
					<logic:equal name="DisplayMCAsForm" property="printParam" value="Y">
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
	<logic:empty name="DisplayMCAsForm" property="dealerDetailsList">
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
				<tr>
					<derivserv:userType role="D">
						<td width="100" height="26" class="blue_bg2"><bean:message
							key="mcx.homepage.pendingmca.counterparty" /></td>
					</derivserv:userType>
					<derivserv:userType role="C">
						<td width="100" height="26" class="blue_bg2"><bean:message
							key="mcx.homepage.pendingmca.dealer" /></td>
					</derivserv:userType>
					<td width="120" height="26" class="blue_bg2"><bean:message
						key="mcx.homepage.pendingmca.mcatype" /></td>
					<td width="120" height="26" class="blue_bg2"><bean:message
						key="mcx.homepage.pendingmca.modifiedby" /></td>
					<td width="120" height="26" class="blue_bg2"><bean:message
						key="mcx.homepage.pendingmca.updatedon" /></td>
					<td width="120" height="26" class="blue_bg2"><bean:message
						key="mcx.homepage.pendingmca.mcastatus" /></td>
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
	<logic:equal name="DisplayMCAsForm" property="printParam" value="N">
		<tr>
			<td align="center" width="100%"><display:table
				name="sessionScope.DisplayMCAsForm.dealerDetailsList" requestURI=""
				id="dealerTable"
				decorator="com.dtcc.dnv.mcx.decorator.MCXTableDecorator"
				class="border" defaultsort="1" defaultorder="ascending"
				style="width:95.5%" cellspacing="0">
				<derivserv:userType role="D">
					<display:column property="orgDlrNm" sortable="true"
						style="width:20%;text-align:left;word-break:break-all" defaultorder="ascending"
						title="Counterparty" group="1" headerClass="blue_bg2_cp_left"
						class="con11">
					</display:column>
				</derivserv:userType>
				<derivserv:userType role="C">
					<display:column property="orgDlrNm" sortable="true"
						style="width:20%;text-align:left;word-break:break-all" defaultorder="ascending"
						title="Dealer" group="1" headerClass="blue_bg2_cp_left"
						class="con11">
					</display:column>
				</derivserv:userType>
				<display:column property="tmpltNm" sortable="true"
					defaultorder="ascending" title="MCA Type"
					headerClass="blue_bg2_cp_left2" class="con9"
					style="width:30%;text-align:left;word-break:break-all" />
				<display:column property="rowUpdtName" sortable="true"
					defaultorder="ascending" title="Last Modified By"
					headerClass="blue_bg2_cp_left" class="con11"
					style="width:15%;text-align:left;word-break:break-all" />
				<display:column property="modifiedTime"
					format="{0,date,MM/dd/yyyy HH:mm:ss z}" sortable="true"
					defaultorder="ascending" title="Last Updated"
					headerClass="blue_bg2_cp_left" class="con11"
					style="width:16%;text-align:left" />
				<display:column property="pendingMcaStatusCd" sortable="true"
					defaultorder="ascending" title="Status"
					headerClass="blue_bg2_cp_left1" class="con9"
					style="width:15%;text-align:left" />
			</display:table></td>
		</tr>
	</logic:equal>
	<logic:equal name="DisplayMCAsForm" property="printParam" value="Y">
		<tr>
			<td align="center" width="100%"><display:table
				name="sessionScope.DisplayMCAsForm.dealerDetailsList" requestURI=""
				id="dealerTable"
				decorator="com.dtcc.dnv.mcx.decorator.MCXTableDecorator"
				class="border" defaultsort="1" defaultorder="ascending"
				style="width:99%" cellspacing="0">
				<derivserv:userType role="D">
					<display:column property="orgDlrNm" sortable="true"
						style="width:20%;text-align:left;word-break:break-all" defaultorder="ascending"
						title="Counterparty" headerClass="blue_bg2_cp_left" class="con11">
					</display:column>
				</derivserv:userType>
				<derivserv:userType role="C">
					<display:column property="orgDlrNm" sortable="true"
						style="width:20%;text-align:left;word-break:break-all" defaultorder="ascending"
						title="Dealer" headerClass="blue_bg2_cp_left" class="con11">
					</display:column>
				</derivserv:userType>
				<display:column property="templateName" sortable="true"
					defaultorder="ascending" title="MCA Type"
					headerClass="blue_bg2_cp_left" class="con9"
					style="width:30%;text-align:left;word-break:break-all" />
				<display:column property="rowUpdtName" sortable="true"
					defaultorder="ascending" title="Last Modified By"
					headerClass="blue_bg2_cp_left" class="con11"
					style="width:15%;text-align:left;word-break:break-all" />
				<display:column property="modifiedTime"
					format="{0,date,MM/dd/yyyy HH:mm:ss z}" sortable="true"
					defaultorder="ascending" title="Last Updated"
					headerClass="blue_bg2_cp_left" class="con11"
					style="width:16%;text-align:left" />
				<display:column property="status" sortable="true"
					defaultorder="ascending" title="Status"
					headerClass="blue_bg2_cp_left1" class="con9"
					style="width:19%;text-align:left" />
			</display:table></td>
		</tr>
	</logic:equal>
	<tr>
		<td height="10px" colspan="7"><img src="/mcx/static/images/spacer.gif" /></td>
	</tr>
	<logic:notEmpty name="DisplayMCAsForm" property="dealerDetailsList">
		<tr>		
		<td colspan="7" class="con9_space" style="padding:0px 0px 0px 18px"><img
				src="/mcx/static/images/lock.gif" alt="Locked by current user" /><bean:message
				key="mcx.homepage.pendingmca.current_user" /><img
				src="/mcx/static/images/unlock.gif" alt="Locked by another user" /><bean:message
				key="mcx.homepage.pendingmca.other_user" /></td>
		</tr>
	</logic:notEmpty>
</table>
</form>
