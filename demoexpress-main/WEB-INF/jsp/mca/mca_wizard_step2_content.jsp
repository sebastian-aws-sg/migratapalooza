<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="/WEB-INF/struts-html.tld"      prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"      prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"     prefix="logic" %>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>DTCC - MCA - Xpress - Home</title>
<link rel="stylesheet" type="text/css" href="/mcx/static/css/primary.css" />
<script language="JavaScript" src="/mcx/static/js/mca_wizard.js"></script>
<script>
lockSt 		= '<bean:write name ="TemplateForm" property="transaction.lockSt" />';
tmpltID 	= '<bean:write name ="TemplateForm" property="transaction.tmpltId" />';
tmpltType 	= '<bean:write name="TemplateForm" property="transaction.tmpltTyp" />';
tmpltShrtNm = '<bean:write name="TemplateForm" property="transaction.tmpltShrtNm" />';
ISDATmpltNm = '<bean:write name="TemplateForm" property="transaction.ISDATmpltNm" />';
lockUsrInd  = '<bean:write name="TemplateForm" property="transaction.lockUsrInd" />';
isDealer	= '<bean:write name ="TemplateForm" property="actingDealer" />';
cpName		= '<bean:write name="TemplateForm" property="transaction.orgCltNm" />';
displayNm	= '<bean:write name ="TemplateForm" property="displayNm" />';
</script>
</head>
<body onclick="clickBody()" >
<script>window.onscroll=scrollHeightWidth;</script>
<div id=rightmenu  onclick="rightClickAction()" onmouseover="switchMenu()" onmouseout="switchMenu()" style="position:absolute;
	background-color:#FFFFFF;
	border:1px solid #3A7BB8;
	width:186px;
	height:60px;
	display:none;
	padding-left:2px;">
<table width="100%" border="0"  cellspacing="0"  cellpadding="2" >
<tr>
<td class="menuItem" id="postAmend"><img id="postAmend" src="/mcx/static/images/post_comm_his_icon.gif" alt="Post Amendment" /> Post Amendment</td>
</tr>
<tr>
<td class="menuItem" id="postComnt"><img id="postComnt" src="/mcx/static/images/post_comm_his_icon.gif" alt="Post Comment/View History" /> Post Comment/View History</td>
</tr>
<tr>
<td class="menuItem" id="viewComnt"><img id="viewComnt" src="/mcx/static/images/view_ind_icon.gif" alt="View Comment History" /> View Comment History</td>
</tr>
<tr>
<td class="menuItem" id="agreeAmend" style="display:none;" ><img id="agreeAmend" src="/mcx/static/images/agree_amend_icon.gif" alt="Agree to Amendment" /> Agree to Amendment</td>
</tr>
<tr>
<td class="menuItem" id="viewISDA"><img id="viewISDA" src="/mcx/static/images/view_ind_icon.gif" alt="View Industry Published Term" /> View Industry Published Term</td>
</tr>
</table> 
</div>
<html:form action="/sendTemplateToCP" method="post">	
<html:hidden property="frmScr" value="MW2"/>
<html:hidden property="enableCustomize" />
<html:hidden name ="TemplateForm" property="tmpltId" />


	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	  <tr>
		<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
	  </tr>	  
				          <tr>
							<td colspan="2" height="10"><img src="/mcx/static/images/spacer.gif" /></td>
						  </tr>
							<tr>
								<td colspan="9" class="con1 padL20a">STEP 2 : Apply MCA Template to Counterparty </td>
							  </tr>
						  <tr>
					        <td colspan="9" class="con9_mcaWiz"> Further Counterparty-specific customization</td>
					      </tr>
							  <tr>
								<td height="5px" colspan="9"><img src="/mcx/static/images/spacer.gif" /></td>
							  </tr>
					  <tr>
				        <td colspan="9">
				          <table width="100%" border="0" cellpadding="0" cellspacing="0">
				            <tr>
				              <td class="ter_temp_txt_small">Counterparty: <bean:write name="TemplateForm" property="transaction.orgCltNm"/></td>
				            </tr>
							  <tr>
								<td height="5px" colspan="9"><img src="/mcx/static/images/spacer.gif" /></td>
							  </tr>
							  <tr>
				              <td class="ter_temp_txt_small">Template: 
					              <select id="tmpltName" class="temp1" disabled>
					              <option><bean:write name="TemplateForm" property="transaction.ISDATmpltNm" /></option>
					              </select>
				              </td>
							 </tr>
						  <tr>							
								<td height="10px" colspan="9"><img src="/mcx/static/images/spacer.gif" /></td>
							 </tr>
							 <tr align="right">
							 <td>
							  <table>
								<tr>
									<td  class="ter_tab_txt_small" width="10%">Filter By:</td>
									<td>
										<html:select name ="TemplateForm" property="catgyId" styleId="Category"  onchange="javascript:selectCategory()" styleClass="category" >
											<option value="">Select All Category</option>
											<html:optionsCollection name ="TemplateForm" property="categyList" label="catgyNm" value="catgyId" />
										</html:select>&nbsp;&nbsp;&nbsp;&nbsp; 
									</td>
									<td>
										<html:select name ="TemplateForm" property="viewInd" styleId="viewIndicator" onchange="javascript:selectView()" styleClass="region" >
											<html:optionsCollection name ="TemplateForm" property="viewMap" label="value" value="key" />
										</html:select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</td>
									<td class="reset"><a href="javascript:resetFilter()">Reset Filter</a></td>
									<td>&nbsp;&nbsp;&nbsp;<input class="attach" onclick="javascript:printMCA()" type="button" name="Submit" value="PRINT VIEW"/>&nbsp;&nbsp;&nbsp;</td>
									<td width="12%"  colspan="2" align="right">
									</td>
								</tr>
							  </table>
							 </td>
						  </tr>
						  <tr>
							<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
						  </tr>
						  <tr>
							<td colspan="9">
							  <table width="100%"  border="0" class="mca_table" cellspacing="0" cellpadding="0">
									<logic:iterate id="categoryBean" name="TemplateForm" indexId="categyIdIndex" property ="categyBeanList">
									<logic:equal name="categoryBean" property="catgyLineWrap" value="1">
									<tr>
										<td height="12" width="22%" class="blue_bg1_mca">	
											<bean:write name="categoryBean" property="catgyNm"/> 
										</td>
										<td height="12" colspan="10" class="blue_bg1_mca">	
											<logic:present  name="categoryBean"  property ="catgyTrm">
											<a style="cursor:hand;" onclick="viewTerm( '<bean:write name="TemplateForm" property="transaction.tmpltTyp" />','<bean:write name="TemplateForm" property="transaction.lockSt" />','<bean:write name="categoryBean" property="catgyTrm.termValId" />');" >Click Here</a>
											</logic:present>
										</td></tr>
										</logic:equal>
										<logic:equal name="categoryBean" property="catgyLineWrap" value="2">
									<tr>
										<td height="12" width="22%" class="blue_bg2_mca">	
											<bean:write name="categoryBean" property="catgyNm"/> 
										</td>
										<td height="12" colspan="10" class="blue_bg2_mca">	
											<logic:present  name="categoryBean"  property ="catgyTrm">
											<a style="cursor:hand;" onclick="viewTerm( '<bean:write name="TemplateForm" property="transaction.tmpltTyp" />','<bean:write name="TemplateForm" property="transaction.lockSt" />','<bean:write name="categoryBean" property="catgyTrm.termValId" />');" >Click Here</a>
											</logic:present>
										</td></tr>
										</logic:equal>
										<logic:equal name="categoryBean" property="catgyLineWrap" value="3">
									<tr>
										<td height="12" width="22%" class="blue_bg3_mca">	
											<bean:write name="categoryBean" property="catgyNm"/> 
										</td>
										<td height="12" colspan="10" class="blue_bg3_mca">	
											<logic:present  name="categoryBean"  property ="catgyTrm">
											<a style="cursor:hand;" onclick="viewTerm( '<bean:write name="TemplateForm" property="transaction.tmpltTyp" />','<bean:write name="TemplateForm" property="transaction.lockSt" />','<bean:write name="categoryBean" property="catgyTrm.termValId" />');" >Click Here</a>
											</logic:present>
										</td></tr>
										</logic:equal>
										<logic:equal name="categoryBean" property="catgyLineWrap" value="4">
									<tr>
										<td height="12" width="22%" class="blue_bg4_mca">	
											<bean:write name="categoryBean" property="catgyNm"/> 
										</td>
										<td height="12" colspan="10" class="blue_bg4_mca">	
											<logic:present  name="categoryBean"  property ="catgyTrm">
											<a style="cursor:hand;" onclick="viewTerm( '<bean:write name="TemplateForm" property="transaction.tmpltTyp" />','<bean:write name="TemplateForm" property="transaction.lockSt" />','<bean:write name="categoryBean" property="catgyTrm.termValId" />');" >Click Here</a>
											</logic:present>
										</td></tr>
										</logic:equal>
										<logic:equal name="categoryBean" property="catgyLineWrap" value="5">
									<tr>
										<td height="12" width="22%" class="blue_bg5_mca">	
											<bean:write name="categoryBean" property="catgyNm"/> 
										</td>
										<td height="12" colspan="10" class="blue_bg5_mca">	
											<logic:present  name="categoryBean"  property ="catgyTrm">
											<a style="cursor:hand;" onclick="viewTerm( '<bean:write name="TemplateForm" property="transaction.tmpltTyp" />','<bean:write name="TemplateForm" property="transaction.lockSt" />','<bean:write name="categoryBean" property="catgyTrm.termValId" />');" >Click Here</a>
											</logic:present>
										</td></tr>
										</logic:equal>
											<logic:present  name="categoryBean"  property ="termList">
											<logic:iterate id="termBean" name="categoryBean" indexId="tmpltIdIndex" property ="termList">
											  <tr>
												<logic:equal name="termBean" property="termStCd" value="Y">										  										 
												<td width="21%" height="60" valign="top" class="product_bg border4a1mca" style="padding:0px 0px 0px 12px">	
													<bean:write name="termBean" property="termNm"/>
													</td>
													</logic:equal>
													 <logic:notEqual name="termBean" property="termStCd" value="Y">										  										 
												<td width="21%" height="60" valign="top" class="product_bg border4a1mca">	
													<bean:write name="termBean" property="termNm"/>
													</td>
													</logic:notEqual>
												<logic:equal name="termBean" property="termStCd" value="Y">
													<logic:equal name="termBean" property="amndtStCd" value="P">
														<td colspan="10" width="76%"  valign="top" class="red border5_bluemca" oncontextmenu="if (!event.ctrlKey){displayMenu('<bean:write name="termBean" property="termValId" />','<bean:write name="termBean" property="amndtStCd" />','<bean:write name="termBean" property="cmntInd" />','<bean:write name="categoryBean" property="catgyStCd"/>');return false;}">
														<bean:write name="termBean" property="termVal" filter="false" />
														<logic:equal name="termBean" property="cmntInd" value="true">
															<img src="/mcx/static/images/note_icon.gif"/>
														</logic:equal>&nbsp;
														</td>		
													</logic:equal>
													<logic:notEqual name="termBean" property="amndtStCd" value="P">
														<td colspan="10" width="76%"  valign="top" class="normal border5_bluemca" oncontextmenu="if (!event.ctrlKey){displayMenu('<bean:write name="termBean" property="termValId" />','<bean:write name="termBean" property="amndtStCd" />','<bean:write name="termBean" property="cmntInd" />','<bean:write name="categoryBean" property="catgyStCd"/>');return false;}">
														<bean:write name="termBean" property="termVal" filter="false" />
														<logic:equal name="termBean" property="cmntInd" value="true">
															<img src="/mcx/static/images/note_icon.gif"/>
														</logic:equal>&nbsp;
														</td>		
													</logic:notEqual>
												</logic:equal>
												<logic:equal name="termBean" property="termStCd" value="N">
												<td colspan="10" width="76%"  valign="top" class="normal border5_bluemca">
												<bean:write name="termBean" property="termNm"/>&nbsp;
												</td>					
												</logic:equal>
											  </tr>	    
											</logic:iterate>
									</tr>
									</logic:present>
									</logic:iterate>  
							  </table>
							 </td>
						  </tr>           
  <tr>
    <td width="100%" height="10"><img src="/mcx/static/images/spacer.gif" /></td>
  </tr>
        <tr>
    	 <td align="right" width="15%"><input type="button" class="long_sendback_coun" value="SUBMIT TO COUNTERPARTY" onclick="sendToCP('<bean:write name="TemplateForm" property="transaction.tmpltNm" />')" /></td>		 	
      </tr>
  
</table>
<script>
if(document.getElementById("catgyID").value == ""){
	document.getElementById("Category").value = "";
	}else{
	document.getElementById("Category").value = document.getElementById("catgyID").value;
	}
if( document.getElementById("viewIND").value == ""){
		document.getElementById("viewIndicator").value = "F";
	}else{
		document.getElementById("viewIndicator").value = document.getElementById("viewIND").value;
	}

</script>
</html:form>
</body>
</html>