<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/derivServ.tld" prefix="derivserv"%>

<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/primary.css" />
<script language="JavaScript" src="/mcx/static/js/managedocs.js"></script>
<script language="JavaScript" src="/mcx/static/js/grouping.js"></script>
<script language="JavaScript" src="/mcx/static/js/dealerinfo_popup.js"></script>
<script language="JavaScript" src="/mcx/static/js/manage_Calendar.js"></script>
<script language="JavaScript" src="/mcx/static/js/dateValidation.js"></script>


<link rel="stylesheet" type="text/css"
	href="/mcx/static/css/displaytag.css" />


<body onload="AlertMessages()" >


<html:form method="POST" enctype="multipart/form-data" 
	action="/manageDoc">
	<html:hidden property="transaction.filesPath[0]" styleId="filesPath[0]"></html:hidden>
	<html:hidden property="transaction.filesPath[1]" styleId="filesPath[1]"></html:hidden>
	<html:hidden property="transaction.filesPath[2]" styleId="filesPath[2]"></html:hidden>
	<html:hidden property="transaction.filesPath[3]" styleId="filesPath[3]"></html:hidden>
	<html:hidden property="transaction.filesPath[4]" styleId="filesPath[4]"></html:hidden>	
	
		
	<table width="99%" cellpadding="0" cellspacing="0" >
		<tr>
			<td height="14" colspan="4"><img src="/mcx/static/images/spacer.gif"
				width="1" height="1" /></td>
		</tr>
		<tr>
			<td height="14" colspan="4"><img src="/mcx/static/images/spacer.gif"
				width="1" height="1" /></td>
		</tr>
		<tr>
			<td colspan="4" class="con1 padL20a"><bean:message
				key="mcx.ManageDocs.preExisting.title" /></td>
		</tr>
		<tr>
			<td height="14" colspan="4"><img src="/mcx/static/images/spacer.gif"
				width="1" height="1" /></td>
		</tr>
		
		<tr>
				<td> 
				<div id="tooltip"></div>
				</td>
				
				
		</tr>
		


		<derivserv:access privilege="Modify">

			<tr>
				<td colspan="4">
				<table width=99% cellpadding="0" cellspacing="0">
					<tr>
			
						<td class="con9" height="26px" style="padding-left:20px;">
							<html:select styleId="selectedDealerClient" onchange="javascript:addCpWindow(this)"
									property="transaction.selectedDealerClient" styleClass="drop1">
								<html:option value="default">-- Select a Counterparty --</html:option>
								<html:option value="Add Counterparty">Add Counterparty</html:option>
								<html:optionsCollection property="dealerClient" label="orgCltNm" value="orgCltCd" filter="false">
								</html:optionsCollection>
							</html:select>
						</td>
		
						<td width="5%" style="padding-right:15px;">
							<html:button property="RenameCP" value="EDIT CP NAME" styleClass="attach" 
								onclick="javascript:renameCpWindow()" style = "cursor:'hand'"/>
							<html:button property="RenameCPDisabled" value="EDIT CP NAME" styleClass="attachDisabled" 
								  style = "cursor:'default'"/>
						</td>
						<td width="5%" style="padding-left:15px;">
							<html:button property="DeleteCP" value="DELETE CP" styleClass="attach"
							onclick="javascript:deleteCpWindow()" style = "cursor:'hand'"/>
							<html:button property="DeleteCPDisabled" value="DELETE CP" styleClass="attachDisabled"
							 style = "cursor:'default'"/>
						</td>
					</tr>
				</table>
				</td>
			</tr>

			<tr>
				<td height="8" colspan="4"><img src="/mcx/static/images/spacer.gif"
					width="1" height="1" /> <html:hidden property="manageDocTab"
					styleId="manageDocTab"></html:hidden></td>
				<td> <html:hidden property="cmpnyId"
					styleId="cmpnyId"></html:hidden><html:hidden property="uploadFileSizeError"
					styleId="uploadFileSizeError"></html:hidden></td>
			</tr>

			<tr>
				<td colspan="4" class="padL20a"><html:hidden property="docsDeleted"
					styleId="docsDeleted"></html:hidden><html:hidden property="uploadDocs"
					styleId="uploadDocs"></html:hidden><html:hidden property="selectedDealerClient"
					styleId="selectedDealerClientId" ></html:hidden></td>
			</tr>

			<tr>
				<td height="14" colspan="4"><img src="/mcx/static/images/spacer.gif"
					width="1" height="1" /></td>
			</tr>

			<tr>
				<td colspan="4" class="padL20a">
				<table width=99% class="border" cellpadding="0" cellspacing="0" >
					<tr>
						<td width="33%" height="26" class="blue_bg2_no_un"><u>MCA Name</u><font
							color="#CC0000">*</font></td>
						<td width="15%" nowrap colspan="2" height="26" class="blue_bg2_no_un"><u>Execution
						Date</u><font color="#CC0000">*</font></td>
						<td width="52%" height="26"  class="blue_bg2_no_un"><u>File
						(optional)</u></td>
					</tr>

					<tr>
						<td><html:select styleId="selectedMCANames"  
							 onmouseover="checkEvent(this.event);" onmouseout="checkEvent(this.event);" onmousemove="checkEvent(this.event);"
							property="transaction.selectedMCANames[0]" styleClass="drop1">
							<html:option value="default" >- Select -</html:option>
							<html:optionsCollection property="mcaNames" label="tmpltShrtNm"
								value='tmpltIdDetails' >
							</html:optionsCollection>
						</html:select></td>
						<td width="10%" height="26" class="con9"><input type="text" 
							name="transaction.executedDate[0]" size="10" value =  "mm/dd/yyyy" onfocus = "javascript:emptyDate(0)" onblur="javascript:populateAllDate()"/>&nbsp;&nbsp;</td>
						<td width="1%" class="attach_txt"><a   
							href="javascript:show_calendar('getElementsByName(\'transaction.executedDate[0]\')[0]', null, null, 'MM/DD/YYYY', 'no')">
						<img src="/mcx/static/images/cal.gif" width="16" height="16"
							style="cursor:hand" border="0" alt="Pick a date"
							> </a></td>
						<td height="26" class="con9"><input type="file" size="25%"
							name="transaction.uploads[0]" /></td>
					</tr>

					<tr>
						<td><html:select styleId="selectedMCANames"	
							onmouseover="checkEvent(this.event);" onmouseout="checkEvent(this.event);" onmousemove="checkEvent(this.event);"	
							property="transaction.selectedMCANames[1]" styleClass="drop1">
							<html:option value="default">- Select -</html:option>
							<html:optionsCollection property="mcaNames" label="tmpltShrtNm"
								value='tmpltIdDetails' >
							</html:optionsCollection>
						</html:select></td>
						<td width="10%" height="26" class="con9"><input type="text" 
							name="transaction.executedDate[1]" size="10" value =  "mm/dd/yyyy" onfocus = "javascript:emptyDate(1)" onblur="javascript:populateAllDate()"/>&nbsp;&nbsp; </td>
						<td width="1%" class="attach_txt"><a  
							href="javascript:show_calendar('getElementsByName(\'transaction.executedDate[1]\')[0]', null, null, 'MM/DD/YYYY', 'no')">
						<img src="/mcx/static/images/cal.gif" width="16" height="16"
							style="cursor:hand" border="0" alt="Pick a date"
							> </a></td>
						<td height="26" class="con9"><input type="file"  size="25%"
							name="transaction.uploads[1]" /></td>
					</tr>

					<tr>
						<td><html:select styleId="selectedMCANames" 
							onmouseover="checkEvent(this.event);" onmouseout="checkEvent(this.event);" onmousemove="checkEvent(this.event);"
							property="transaction.selectedMCANames[2]" styleClass="drop1">
							<html:option value="default">- Select -</html:option>
							<html:optionsCollection property="mcaNames" label="tmpltShrtNm"
								value='tmpltIdDetails' >
							</html:optionsCollection>
						</html:select></td>
						<td width="10%" height="26" class="con9"><input type="text"  
							name="transaction.executedDate[2]" size="10" value =  "mm/dd/yyyy" onfocus = "javascript:emptyDate(2)" onblur="javascript:populateAllDate()"/>&nbsp;&nbsp;</td>
						<td width="1%" class="attach_txt"><a   
							href="javascript:show_calendar('getElementsByName(\'transaction.executedDate[2]\')[0]', null, null, 'MM/DD/YYYY', 'no')">
						<img src="/mcx/static/images/cal.gif" width="16" height="16"
							style="cursor:hand" border="0" alt="Pick a date"
							> </a></td>
						<td height="26" class="con9"><input type="file"  size="25%"
							name="transaction.uploads[2]" /></td>
					</tr>

					<tr>
						<td><html:select styleId="selectedMCANames" 
							onmouseover="checkEvent(this.event);" onmouseout="checkEvent(this.event);" onmousemove="checkEvent(this.event);"
							property="transaction.selectedMCANames[3]" styleClass="drop1">
							<html:option value="default">- Select -</html:option>
							<html:optionsCollection property="mcaNames" label="tmpltShrtNm"
								value='tmpltIdDetails' >
							</html:optionsCollection>
						</html:select></td>
						<td width="10%" height="26" class="con9"><input type="text"   
							name="transaction.executedDate[3]" size="10" value =  "mm/dd/yyyy" onfocus = "javascript:emptyDate(3)" onblur="javascript:populateAllDate()"/>&nbsp;&nbsp;</td>
						<td width="1%" class="attach_txt"><a   
							href="javascript:show_calendar('getElementsByName(\'transaction.executedDate[3]\')[0]', null, null, 'MM/DD/YYYY', 'no')">
						<img src="/mcx/static/images/cal.gif" width="16" height="16"
							style="cursor:hand" border="0" alt="Pick a date"
							> </a></td>
						<td height="26" class="con9"><input type="file" size="25%"
							name="transaction.uploads[3]" /></td>
					</tr>

					<tr>
						<td><html:select styleId="selectedMCANames" 
							onmouseover="checkEvent(this.event);" onmouseout="checkEvent(this.event);" onmousemove="checkEvent(this.event);"
							property="transaction.selectedMCANames[4]" styleClass="drop1">
							<html:option value="default">- Select -</html:option>
							<html:optionsCollection property="mcaNames" label="tmpltShrtNm"
								value="tmpltIdDetails" >
							</html:optionsCollection>
						</html:select></td>

						<td width="10%" height="26" class="con9"><input type="text"   
							name="transaction.executedDate[4]" size="10" value =  "mm/dd/yyyy" onfocus = "javascript:emptyDate(4)" onblur="javascript:populateAllDate()"/>&nbsp;&nbsp;</td>

						<td width="1%" class="attach_txt"><a   
							href="javascript:show_calendar('getElementsByName(\'transaction.executedDate[4]\')[0]', null, null, 'MM/DD/YYYY', 'no')">
						<img src="/mcx/static/images/cal.gif" width="16" height="16"
							style="cursor:hand" border="0" alt="Pick a date"
							> </a></td>
						<td height="26" class="con9"><input type="file" size="25%"
							name="transaction.uploads[4]" /></td>
					</tr>
				</table>
				</td>
			</tr>

			<tr>
				<td height="14" colspan="4"><img src="/mcx/static/images/spacer.gif"
					width="1" height="1" /></td>
			</tr>

			<tr class="padL20a">
				<td colspan="4">
				<table width=100% cellspacing="0">
					<tr>
						<td align=right width = 75% style="padding-left:10px;"></td>

						<td align=right style="padding-right:10px;"><html:button
							styleClass="long" property="Upload" value="UPLOAD AND SAVE"
							onclick="uploadPreexistingDocs()" /></td> <td align=right><html:button
							styleClass="enroll" property="Cancel" value="CANCEL" style = "cursor:'hand'"
							onclick="cancel1()" /> <html:button
							styleClass="enrollDisabled" property="CancelDisabled" value="CANCEL" 
							 style = "cursor:'default'" /></td>

					</tr>
				</table>
				</td>
			</tr>

			<tr>
				<td height="25" colspan="4"><img src="/mcx/static/images/spacer.gif"
					width="1" height="1" /></td>
			</tr>

		</derivserv:access>



		<logic:greaterThan name="ManagedDocumentForm" property="numberOfDocuments" value="0" scope = "session">
			<tr class="padL20a">
				<td colspan="4">
					<table width=99% cellspacing="0">
						<tr>				
							<derivserv:access privilege="Modify">
								<td colspan="4" align="right" style="padding-right:0px;">
									<html:button
									property="DeleteMCADoc" value="DELETE" styleClass="enroll"
									onclick="javascript:deleteMCADoc()" disabled="false" />
								</td>
							</derivserv:access>
						</tr>
					</table>
				</td>
			</tr>

			<tr>
				<td height="14" colspan="4">
					<img src="/mcx/static/images/spacer.gif" width="1" height="1" />
				</td>
			</tr>
			<tr>
				<td colspan="8" class="padL20a" style="width: 99%"><display:table
					name="sessionScope.ManagedDocumentForm.dealerClientDetails"
					id="DealerClientDetail" class="border" defaultsort="1"
					defaultorder="ascending" style="width: 99%" cellspacing="0"
					decorator="com.dtcc.dnv.mcx.decorator.MCXTableDecorator"
					requestURI="/mcx/action/manageDoc">
					<display:column property="orgDlrNm" title="Counterparty" class="con11"
						headerClass="blue_bg2_cp_left" sortable="true" 
						defaultorder="ascending" group="1"
						style="text-align:left;width:17%;word-break: break-all" />
					<display:column property="mcaTempName" title="MCA Name"
						class="con11" headerClass="blue_bg2_cp_left" sortable="true"
						defaultorder="ascending" style="text-align:left;width:29%;word-break: break-all" />

					<display:column property="mcaAgreementDate" title="Execution Date"
						class="con11" headerClass="blue_bg2_cp_left" sortable="true"
						defaultorder="ascending" format="{0,date,MM/dd/yyyy}"
						style="text-align:left;width:11%;" />
					<display:column property="cpViewable" title="CP-Viewable"
						class="con11" headerClass="blue_bg2_cp_left" sortable="true"
						defaultorder="ascending" style="text-align:center;width:10%;" />
					<display:column property="rowUpdtName" title="Uploaded by"
						class="con11" headerClass="blue_bg2_cp_left" sortable="true"
						defaultorder="ascending" style="text-align:left;width:10%;word-break: break-all" />
					<display:column property="modifiedTime" title="Uploaded on"
						class="con11" headerClass="blue_bg2_cp_left" sortable="true"
						defaultorder="ascending" format="{0,date,MM/dd/yyyy HH:mm:ss z}"
						style="text-align:left;width:15%;" />

					<derivserv:access privilege="Modify">

						<display:column class="con11"
							title="<input type='checkbox' onclick='CheckAll(this)' name='selectall'  />"
							headerClass="blue_bg2_cp_left" style="text-align:left;width:3%;">
							<html:multibox property="transaction.selectedDocuments" styleId="selectedDocuments"
								onclick="UncheckHeader()">
								<bean:write name="DealerClientDetail" property="orgDlrCd"></bean:write>-<bean:write
									name="DealerClientDetail" property="docId"></bean:write>-<bean:write
									name="DealerClientDetail" property="tmpltId"></bean:write>
							</html:multibox>
						</display:column>
					</derivserv:access>
				</display:table></td>
			</tr>
		</logic:greaterThan>

		<logic:equal name="ManagedDocumentForm" property="numberOfDocuments" scope = "session"
			value="0">
			<tr>
				<td width="99%" class="con9b">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message
					key="mcx.ManageDocs.label.norecordsfound" /></td>
			</tr>
			<tr>
				<td colspan="8" class="padL20a" style="width:99%">
				<table width="99%" height="100%" class="border" cellpadding="0"
					cellspacing="0">
					<tr>
						<td width="17%" height="30" class="blue_bg2_cp"
							style="padding: 0px 0px 0px 6px;">Counterparty</td>
						<td width="25%" height="30" class="blue_bg2_cp"
							style="padding: 0px 0px 0px 13px;">MCA Name</td>
						<td width="11%" height="30" class="blue_bg2_cp"
							style="padding: 0px 0px 0px 6px;">Execution Date</td>
						<td width="10%" height="30" class="blue_bg2_cp"
							style="padding: 0px 0px 0px 6px;">CP-Viewable</td>
						<td width="10%" height="30" class="blue_bg2_cp"
							style="padding: 0px 0px 0px 6px;">Uploaded by</td>
						<td width="15%" height="30" class="blue_bg2_cp"
							style="padding: 0px 0px 0px 6px;">Uploaded on</td>
						
					</tr>
					<tr height="30" class="grey_bg">
						<td width="17%" class="con9" />
						<td width="25%" class="con9" />
						<td width="11%" class="con9" />
						<td width="10%" class="con9" />
						<td width="10%" class="con9" />
						<td width="15%" class="con9" />
					</tr>
				</table>
				</td>
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
	
	
	<script>
	
	selectdefault();
	
</script>
</html:form>

</body>