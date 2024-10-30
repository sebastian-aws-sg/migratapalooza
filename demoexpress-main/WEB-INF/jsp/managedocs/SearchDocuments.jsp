<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/derivServ.tld" prefix="derivserv"%>

<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/displaytag.css" />
<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/primary.css" />
<script language="JavaScript" src="/mcx/static/js/managedocs.js"></script>
<script language="JavaScript" src="/mcx/static/js/grouping.js"></script>
<script language="JavaScript" src="/mcx/static/js/dealerinfo_popup.js"></script>

<body onload="AlertMessagesSearch()">

<html:form method="POST" enctype="multipart/form-data" action="/search" onsubmit="searchAction()">
	

	<table cellspacing="0" width="97.5%">
		<tr>
			<td height="14" colspan="4"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td height="14" colspan="4"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td colspan="4" class="con1 padL20a">&nbsp;<bean:message
				key="mcx.ManageDocs.search.title" /></td>
		</tr>
		<tr>
			<td style="padding-left:18px;">
			<table>
				<tr>

					<td align=right class="text3" width="15%"><bean:message
						key="mcx.ManageDocs.search.filterby" /></td>
						
						<html:hidden
						property="transaction.selectedDealerClient" styleId="selectedDealerClientId"></html:hidden>

					<td width="85%"><html:select styleId="selectedDealerClient"
						property="transaction.selectedDealerClient" styleClass="drop1">
						<html:option value="ALL">-- All Counterparties --</html:option>
						<html:optionsCollection property="dealerClient" filter="false"
							label="orgCltNm" value="orgCltCd" />
					</html:select></td>

				</tr>
				<tr>
					<td height="3" colspan="2"><img src="/mcx/static/images/spacer.gif" /></td>
				</tr>
				<tr>
				<html:hidden
						styleId="selectedDocumentTypeHidden" property="transaction.selectedDocumentType"></html:hidden>
					<td nowrap align=right class="text3"><bean:message
						key="mcx.ManageDocs.search.doctype" /></td>
					<td><html:select property="transaction.selectedDocumentType" 
						styleId="selectedDocumentTypeValue" styleClass="drop1">
						<html:option key="mcx.ManageDocs.search.DocumentType.label.All"
							value="ALL" />
						<html:option
							key="mcx.ManageDocs.search.DocumentType.label.Pre-Exec" value="P" />
						<html:option key="mcx.ManageDocs.search.DocumentType.label.Others"
							value="O" />
					</html:select></td>
				</tr>
				<tr>
					<td height="3" colspan="2"><img src="/mcx/static/images/spacer.gif" /></td>
				</tr>
				<tr>
				<html:hidden
						styleId="docNameHidden" property="transaction.docName"></html:hidden>
					<td nowrap align=right class="text3"><bean:message
						key="mcx.ManageDocs.search.docname" /><html:hidden
						property="docsDeleted" styleId="docsDeleted"></html:hidden></td>
					<td nowrap><html:text property="transaction.docName" 
						styleId="docNameTextBox" /> <html:hidden property="manageDocTab"
						styleId="manageDocTab"></html:hidden> <html:hidden
						styleId="searchActionParam" property="manageDocs"></html:hidden>
					&nbsp; <html:button value="SEARCH" styleClass="enroll"  property="Search"
						onclick=" searchAction();">
					</html:button> &nbsp; <html:button value="CLEAR" property="Clear"
						styleClass="enroll" onclick="clearAction()" /></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td height="14px" colspan="4"><img
				src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		
		

		<logic:greaterThan name="ManagedDocumentForm" scope = "session"
			property="numberOfDocuments" value="-1">

			<tr id="row2a">
			

			</tr>

			<tr>
				<td height="14px" colspan="4"><img
					src="/mcx/static/images/spacer.gif" /></td>
			</tr>

			<tr id="row2a">
				<logic:equal name="ManagedDocumentForm" property="numberOfDocuments" scope = "session"
					value="0">
					
					<logic:equal name="ManagedDocumentForm" property="docsDeleted" scope = "session"
						value="false">
						
						

						<td class="con9b">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <bean:message
							key="mcx.ManageDocs.search.noDocs" /></td>
					</logic:equal>
				</logic:equal>
				<logic:greaterThan name="ManagedDocumentForm" scope = "session"
					property="numberOfDocuments" value="0">
					
					

					<logic:equal name="ManagedDocumentForm" property="docsDeleted" scope = "session"
						value="false">
						
						<td class="con9b">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <bean:write 
							name="ManagedDocumentForm" property="numberOfDocuments" scope = "session"/> <bean:message
							key="mcx.ManageDocs.search.noOfDocs" /></td>
						
					</logic:equal>


					<derivserv:access privilege="Modify">

						<td align="right"><html:button property="DeleteMCADoc"
							value="DELETE" styleClass="enroll"
							onclick="javascript:deleteMCADoc()" disabled="false" /></td>

					</derivserv:access>
			</tr>

			<tr>
				<td height="14" colspan="4"><img src="/mcx/static/images/spacer.gif"
					width="1" height="1" /></td>
			</tr>



			<tr>
				<td colspan="4" class="padL20a" width="100%"
					style="text-align: left;"><display:table
					name="sessionScope.ManagedDocumentForm.dealerClientDetails"
					id="DealerClientDetail" class="border" defaultsort="1"
					defaultorder="ascending" style="width: 100%" cellspacing="0"
					decorator="com.dtcc.dnv.mcx.decorator.MCXTableDecorator"
					requestURI="/mcx/action/search">
					<display:column property="orgDlrNm" title="CounterParty"
						class="con11" headerClass="blue_bg2_cp_left" sortable="true"
						defaultorder="ascending" group="1"
						style="text-align:left;width:17%;word-break: break-all" />
					<display:column property="mcaTempName" title="MCA Name/File Name"
						class="con11" headerClass="blue_bg2_cp_left" sortable="true"
						defaultorder="ascending"
						style="text-align:left;width:26%;word-break: break-all" />
					<display:column property="tmpltTyp" title="Doc Type" class="con11"
						headerClass="blue_bg2_cp_left" sortable="true"
						defaultorder="ascending" style="text-align:left;width:8%" />
					<display:column property="mcaAgreementDate" title="Execution Date"
						class="con11" headerClass="blue_bg2_cp_left" sortable="true"
						defaultorder="ascending" format="{0,date,MM/dd/yyyy}"
						style="text-align:left;width:13%" />
					<display:column property="cpViewable" title="CP-Viewable"
						class="con11" headerClass="blue_bg2_cp_left" sortable="true"
						defaultorder="ascending" style="text-align:center;width:10%" />
					<display:column property="rowUpdtName" title="Uploaded by"
						class="con11" headerClass="blue_bg2_cp_left" sortable="true"
						defaultorder="ascending"
						style="text-align:left;width:10%;word-break: break-all" />
					<display:column property="modifiedTime" title="Uploaded on"
						class="con11" headerClass="blue_bg2_cp_left" sortable="true"
						defaultorder="ascending" format="{0,date,MM/dd/yyyy HH:mm:ss z}"
						style="text-align:left;width:15%" />
					<derivserv:access privilege="Modify">

						<display:column class="con11"
							title="<input type='checkbox' onclick='CheckAll(this)' name='selectall' />"
							headerClass="blue_bg2_cp_left" style="text-align:left;width:3%">
							<html:multibox property="transaction.selectedDocuments"
								styleId="selectedDocuments" onclick="UncheckHeader()">
								<bean:write name="DealerClientDetail" property="orgDlrCd"></bean:write>-<bean:write
									name="DealerClientDetail" property="docId"></bean:write>-<bean:write
									name="DealerClientDetail" property="tmpltId"></bean:write>
							</html:multibox>
						</display:column>
					</derivserv:access>
				</display:table></td>
			</tr>



			<tr>
				<td height="10px" colspan="9"><img
					src="/mcx/static/images/spacer.gif" /></td>
			</tr>



		</logic:greaterThan>
		</logic:greaterThan>

		<tr>
			<td height="14" colspan="4"><img src="/mcx/static/images/spacer.gif"
				width="1" height="1" /></td>
		</tr>

		<tr>
			<td class="con9_space" colspan="9">&nbsp; <bean:message
				key="mcx.ManageDocs.NonmcaNames" /></td>
			
		</tr>
		<tr>
			<td height="5" colspan="9"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
	</table>
<script>
	searchLoad();
	
	
</script>	
</html:form>

</body>
