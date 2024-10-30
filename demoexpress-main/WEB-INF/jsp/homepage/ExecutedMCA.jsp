<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/derivServ.tld" prefix="derivserv" %>
<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/primary.css" />
<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/displaytag.css" />
<script language="JavaScript" src="/mcx/static/js/dealerinfo_popup.js"></script>
<script language="JavaScript" src="/mcx/static/js/grouping.js"></script>
<script language="JavaScript" src="/mcx/static/js/managedocs.js"></script>
<html:form action="/getDealerDetails" method="post">
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
						key="mcx.homepage.executedmca.title" /></td>
					<logic:notEmpty name="DisplayMCAsForm" property="dealerDetailsList">
						<logic:equal name="DisplayMCAsForm" property="printParam"
							value="N">
							<td><input class="attach" type="button" value="PRINT VIEW"
								onclick="javascript:openPending('E','Y');" /></td>
						</logic:equal>
						<logic:equal name="DisplayMCAsForm" property="printParam"
							value="Y">
							<td><input class="attach" type="button" value="PRINT"
								onclick="window.print();" /></td>
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
					<tr align="center">
						<derivserv:userType role="D">
							<td width="100" height="26" class="blue_bg2"><bean:message
							key="mcx.homepage.executedmca.counterparty" /></td>
						</derivserv:userType>
						<derivserv:userType role="C">
							<td width="100" height="26" class="blue_bg2"><bean:message
							key="mcx.homepage.executedmca.dealer" /></td>
						</derivserv:userType>
						<td width="120" height="26" class="blue_bg2"><bean:message
							key="mcx.homepage.executedmca.mcatype" /></td>
						<td width="120" height="26" class="blue_bg2"><bean:message
							key="mcx.homepage.executedmca.doctype" /></td>
						<td width="120" height="26" class="blue_bg2"><bean:message
							key="mcx.homepage.executedmca.executiondate" /></td>
						<td width="150" height="26" class="blue_bg2"><bean:message
							key="mcx.homepage.executedmca.submittedby" /></td>
					</tr>
					<tr height="30" class="grey_bg">
						<td width="100" class="con9" />
						<td width="120" class="con9" />
						<td width="120" class="con9" />
						<td width="120" class="con9" />
						<td width="150" class="con9" />
					</tr>
				</table>

				</td>
			</tr>
		</logic:empty>
		<logic:equal name="DisplayMCAsForm" property="printParam" value="N">
			<tr>
				<td align="center"><display:table
					name="sessionScope.DisplayMCAsForm.dealerDetailsList" requestURI=""
					id="dealerTable"
					decorator="com.dtcc.dnv.mcx.decorator.MCXTableDecorator"
					class="border" defaultsort="1" defaultorder="ascending"
					style="width:95.5%" cellspacing="0">
					<derivserv:userType role="D">
						<display:column property="orgDlrNm" sortable="true"
							defaultorder="ascending" title="Counterparty" group="1"
							headerClass="blue_bg2_cp_left" class="con11"
							style="width:20%;text-align:left;word-break:break-all">
						</display:column>
					</derivserv:userType>
					<derivserv:userType role="C">
						<display:column property="orgDlrNm" sortable="true"
							defaultorder="ascending" title="Dealer" group="1"
							headerClass="blue_bg2_cp_left" class="con11"
							style="width:20%;text-align:left;word-break:break-all">
						</display:column>
					</derivserv:userType>
					<display:column property="executedMcaFileName" sortable="true"
						defaultorder="ascending" title="MCA Type"
						headerClass="blue_bg2_cp_left3" class="con9"
						style="width:35%;text-align:left;word-break:break-all" />
					<display:column property="tmpltTyp" sortable="true"
						defaultorder="ascending" title="Doc Type"
						headerClass="blue_bg2_cp_left" class="con11"
						style="width:10%;text-align:left" />
					<display:column property="modifiedTime"
						format="{0,date,MM/dd/yyyy}" sortable="true"
						defaultorder="ascending" title="Execution Date"
						headerClass="blue_bg2_cp_left" class="con11"
						style="width:16%;text-align:left" />
					<display:column property="rowUpdtName" sortable="true"
						defaultorder="ascending" title="Final Submission By"
						headerClass="blue_bg2_cp_left" class="con11"
						style="width:15%;text-align:left;word-break:break-all" />
				</display:table></td>
			</tr>
		</logic:equal>
		<logic:equal name="DisplayMCAsForm" property="printParam" value="Y">
			<tr>
				<td align="center"><display:table
					name="sessionScope.DisplayMCAsForm.dealerDetailsList" requestURI=""
					id="dealerTable"
					decorator="com.dtcc.dnv.mcx.decorator.MCXTableDecorator"
					class="border" defaultsort="1" defaultorder="ascending"
					style="width:99%" cellspacing="0">
					<derivserv:userType role="D">
						<display:column property="orgDlrNm" sortable="true"
							defaultorder="ascending" title="Counterparty"
							headerClass="blue_bg2_cp_left" class="con11"
							style="width:20%;text-align:left;word-break:break-all">
						</display:column>
					</derivserv:userType>
					<derivserv:userType role="C">
						<display:column property="orgDlrNm" sortable="true"
							defaultorder="ascending" title="Dealer"
							headerClass="blue_bg2_cp_left" class="con11"
							style="width:20%;text-align:left;word-break:break-all">
						</display:column>
					</derivserv:userType>
					<display:column property="templateName" sortable="true"
						defaultorder="ascending" title="MCA Type"
						headerClass="blue_bg2_cp_left1" class="con9"
						style="width:35%;text-align:left;word-break:break-all" />
					<display:column property="tmpltTyp" sortable="true"
						defaultorder="ascending" title="Doc Type"
						headerClass="blue_bg2_cp_left" class="con11"
						style="width:10%;text-align:left" />
					<display:column property="modifiedTime"
						format="{0,date,MM/dd/yyyy}" sortable="true"
						defaultorder="ascending" title="Execution Date"
						headerClass="blue_bg2_cp_left" class="con11"
						style="width:16%;text-align:left" />
					<display:column property="rowUpdtName" sortable="true"
						defaultorder="ascending" title="Final Submission By"
						headerClass="blue_bg2_cp_left" class="con11"
						style="width:15%;text-align:left;word-break:break-all" />
				</display:table></td>
			</tr>
		</logic:equal>
		
		<tr>
			<td height="5px" colspan="4"><img src="/mcx/static/images/spacer.gif"
				width="1" height="1" /></td>
		</tr>

		<tr>
			<td class="con9_space" >&nbsp; <bean:message
				key="mcx.ManageDocs.NonmcaNames" /></td>
			<td align="right"></td>
		</tr>

		<tr>
			<td height="5" colspan="4"><img src="/mcx/static/images/spacer.gif"
				width="1" height="1" /></td>
		</tr>
	</table>
</html:form>
