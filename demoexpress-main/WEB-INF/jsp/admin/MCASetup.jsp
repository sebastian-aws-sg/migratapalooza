<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/primary.css" />
<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/displaytag.css" />
<script>highlightmenusandsubmenus('mcasetup',null);</script>

<form method="post">
<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
			<tr>
				<td height="10px" colspan="3"><img src="/mcx/static/images/spacer.gif" /></td>
			</tr>
			<tr>
				<td colspan="7" class="con1 padL20a"><bean:message
					key="mcx.admin.mcasetup.title" /></td>
			</tr>
			<tr>
				<td height="10" colspan="8"><img src="/mcx/static/images/spacer.gif" /></td>
			</tr>
			<logic:empty name="MCASetupForm" property="mcaTypeList">
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
					<td height="10" colspan="8"><img
						src="/mcx/static/images/spacer.gif" /></td>
				</tr>

				<tr>
					<td align="center">
					<table class="border" style="width:95.5%" cellspacing="0">
						<tr align="center">
							<td width="100" height="26" class="blue_bg2"><bean:message
								key="mcx.homePage.MCAType" /></td>
							<td width="120" height="26" class="blue_bg2"><bean:message
								key="mcx.homePage.Status" /></td>
							<td width="120" height="26" class="blue_bg2"><bean:message
								key="mcx.admin.mcasetup.DatePosted" /></td>
							<td width="120" height="26" class="blue_bg2"><bean:message
								key="mcx.admin.mcasetup.LastModifiedBy" /></td>
							<td width="120" height="26" class="blue_bg2"><bean:message
								key="mcx.admin.mcasetup.LastModifiedOn" /></td>
							<td width="130" class="blue_bg2"><bean:message
								key="mcx.admin.mcasetup.MCAAgreementDate" /></td>
						</tr>
						<tr height="30" class="grey_bg">
							<td width="100" class="con9" />
							<td width="120" class="con9" />
							<td width="120" class="con9" />
							<td width="120" class="con9" />
							<td width="120" class="con9" />
							<td width="130" class="con9" />
						</tr>
					</table>

					</td>
				</tr>

			</logic:empty>
			<tr>
				<td colspan="7" style="padding:0px 0px 0px 18px"><display:table
					name="sessionScope.MCASetupForm.mcaTypeList" requestURI=""
					id="mcaTable"  cellspacing="0"
					decorator="com.dtcc.dnv.mcx.decorator.MCXTableDecorator"
					class="border" defaultorder="ascending" style="width:975">
					<display:column property="mcaTemplateName" sortable="true"
						defaultorder="ascending" title="MCA Type"
						headerClass="blue_bg2_cp_left" class="con11" style="width:28%" />
					<display:column property="mcaStatusCd" defaultorder="ascending"
						sortable="true" title="Status" headerClass="blue_bg2_cp_left1"
						class="con9" style="width:15%;text-align:left" />
					<display:column property="postedDate" sortable="true"
						format="{0,date,MM/dd/yyyy}" defaultorder="ascending"
						title="Date Posted" headerClass="blue_bg2_cp_left" class="con11"
						style="width:12%" />
					<display:column property="rowUpdtName" sortable="true"
						defaultorder="ascending" title="Last Modified By"
						headerClass="blue_bg2_cp_left" class="con11" style="width:15%" />
					<display:column property="modifiedTime"
						format="{0,date,MM/dd/yyyy HH:mm:ss z}" sortable="true"
						defaultorder="ascending" title="Last Modified On "
						headerClass="blue_bg2_cp_left" class="con11" style="width:15%" />
					<display:column property="mcaAgreementDate" sortable="true"
						defaultorder="ascending" title="MCA Agreement Date"
						format="{0,date,MM/dd/yyyy}" headerClass="blue_bg2_cp_left"
						class="con11" style="width:20%" />
				</display:table></td>
			</tr>

			<tr>
				<td height="10px" colspan="7"><img
					src="/mcx/static/images/spacer.gif" /></td>
			</tr>
			<logic:notEmpty name="MCASetupForm" property="mcaTypeList">
				<tr>
					<td colspan="7" class="con9_space" style="padding:0px 0px 0px 18px"><img
						src="/mcx/static/images/lock.gif" alt="lock" /><bean:message
						key="mcx.homepage.pendingmca.current_user" /><img
						src="/mcx/static/images/unlock.gif" alt="lock" /><bean:message
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
</form>
