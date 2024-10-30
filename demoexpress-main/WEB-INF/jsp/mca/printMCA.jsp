<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="/WEB-INF/struts-html.tld"      prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"      prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"     prefix="logic" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>DTCC - MCA - Xpress - Home</title>
<link rel="stylesheet" type="text/css" href="/mcx/static/css/primary.css" />
<script language="JavaScript" src="/mcx/static/js/mca_wizard.js"></script>
<script>
lockSt 		= '<bean:write name ="TemplateForm" property="transaction.lockSt" />';
tmpltID 	= '<bean:write name ="TemplateForm" property="transaction.tmpltId" />';
tmpltType 	= '<bean:write name="TemplateForm" property="transaction.tmpltTyp" />';
ISDATmpltNm = '<bean:write name="TemplateForm" property="transaction.tmpltShrtNm" />';
lockUsrInd = '<bean:write name="TemplateForm" property="transaction.lockUsrInd" />';
isDealer	= '<bean:write name ="TemplateForm" property="actingDealer" />';
</script>
</head>
<body onclick="clickBody()" >
<script>window.onscroll=scrollHeightWidth;</script>
<div id=rightmenu  onclick="rightClickAction()" onmouseover="switchMenu()" onmouseout="switchMenu()" style="position:absolute;
	background-color:#FFFFFF;
	border:1px solid #3A7BB8;
	width:176px;
	height:60px;
	display:none;
	padding-left:2px;">
<table id="test" width="100%" border="0"  cellspacing="0"  cellpadding="2" >
<tr>
<td class="menuItem" id="postAmend"><img src="/mcx/static/images/post_comm_his_icon.gif" alt="Post Amendment" /> Post Amendment</td>
</tr>
<tr>
<td class="menuItem" id="postComnt"><img src="/mcx/static/images/post_comm_his_icon.gif" alt="post" /> Post Comment/View History</td>
</tr>
<tr>
<td class="menuItem" id="viewComnt" style="display:none;" ><img src="/mcx/static/images/view_ind_icon.gif" alt="view"  /> View Comment History</td>
</tr>
<tr>
<td class="menuItem" id="agreeAmend" style="display:none;" ><img src="/mcx/static/images/agree_amend_icon.gif" alt="agree" /> Agree to Amendment</td>
</tr>
<tr>
<td class="menuItem" id="viewISDA"><img src="/mcx/static/images/view_ind_icon.gif" alt="view" /> View Industry Published Term</td>
</tr>
</table>
</div>
<html:form action="/ViewMCA" method="post">
<html:hidden name ="TemplateForm" property="tmpltId" />
<html:hidden property="frmScr" value="MW1"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
   <tr>
    <td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
  </tr>
  <tr>
    <td class="padLR10"><table width="100%" cellpadding="0" cellspacing="0" class="border_indus_pub">
      <tr>
        <td><table width="100%" class="ter_tab_bg"  cellpadding="0" cellspacing="0">
  <tr>
  <bean:define id="prdCd" name="TemplateForm" property="transaction.derPrdCd" />
  <logic:present name="Products">
  <logic:iterate id="prods" name="Products"> 
  <logic:equal name='prods' property='prodId' value='<%=(String)prdCd %>' >
	  <td width="123" style="padding: 0px 0px 0px 32px;" class="ter_tab_txt" align="justify"><bean:write name="prods" property="prodNm" /></td>
	</logic:equal>
	<logic:notEqual name='prods' property='prodId' value='<%=(String)prdCd %>' >
	  <td width="123" style="padding: 0px 0px 0px 32px;" class="ter_tab_dis_txt" align="justify"><bean:write name="prods" property="prodNm" /></td>
	</logic:notEqual>
   </logic:iterate>
  </logic:present>
    <td width="62" rowspan="5" class="ter_tab_txt_small"></td>
    <td width="97" rowspan="5">
    </td>
  </tr>
  <tr>
  <bean:define id="prdCd" name="TemplateForm" property="transaction.derPrdCd" />
  <bean:define id="subPrdCd" name="TemplateForm" property="transaction.derSubPrdCd" />
    <logic:present name="Products">
  <logic:iterate id="mainProds" name="Products"> 
    <td class="border_ter_tab" align="center"><table width="112px"  cellpadding="0" cellspacing="0">
      <tr>
	  <logic:iterate id="subProds" name="mainProds"  property="subProds" >
	  <logic:equal name='mainProds' property='prodId' value='<%=(String)prdCd %>' >
	  <logic:equal name='subProds' property='subProdCd' value='<%=(String)subPrdCd %>' >
	    <td width="4" >&nbsp;</td>
        <td width="5"><img src="/mcx/static/images/ter_whitetab_left_corner.gif" alt="tab" width="5" height="24"/></td>
		<logic:equal name='subProds' property='tmpltPresent' value='true' >
				<td width="40" class="ter_inner_ontab_txt" ><bean:write name="subProds" property='subProdNm'  /></td>
		</logic:equal>
		<logic:notEqual name='subProds' property='tmpltPresent' value='true' >
			<td width="40" class="ter_inner_ontab_txt" ><bean:write name="subProds" property='subProdNm'  /></td>
		</logic:notEqual>
        <td width="5"><img src="/mcx/static/images/ter_whitetab_right_corner.gif" alt="tab" width="5" height="24" /></td>
        <td width="4">&nbsp;</td>
       </logic:equal>
	  <logic:notEqual name='subProds' property='subProdCd' value='<%=(String)subPrdCd %>' >
	    <td width="4" >&nbsp;</td>
        <td width="5"><img src="/mcx/static/images/ter_bluetab_left_corner.gif" alt="tab" width="5" height="24" /></td>
		<logic:equal name='subProds' property='tmpltPresent' value='true' >
				<td width="40" class="ter_inner_offtab_txt" ><bean:write name="subProds" property='subProdNm'  /></td>
		</logic:equal>
		<logic:notEqual name='subProds' property='tmpltPresent' value='true' >
			<td width="40" class="ter_inner_offtab_txt" ><bean:write name="subProds" property='subProdNm'  /></td>
		</logic:notEqual>
        <td width="5"><img src="/mcx/static/images/ter_bluetab_right_corner.gif" alt="tab" width="5" height="24" /></td>
        <td width="4">&nbsp;</td>
        </logic:notEqual>
       </logic:equal>
	  <logic:notEqual name='mainProds' property='prodId' value='<%=(String)prdCd %>' >
	  <logic:equal name='subProds' property='subProdCd' value='<%=(String)subPrdCd %>' >
	    <td width="4" >&nbsp;</td>
        <td width="5"><img src="/mcx/static/images/ter_bluetab_left_corner.gif" alt="tab" width="5" height="24" /></td>
        <td width="40" class="ter_inner_offtab_txt ">
		<logic:equal name='subProds' property='tmpltPresent' value='true' >
				<td width="40" class="ter_inner_offtab_txt" ><bean:write name="subProds" property='subProdNm'  /></td>
		</logic:equal>
		<logic:notEqual name='subProds' property='tmpltPresent' value='true' >
			<td width="40" class="ter_inner_offtab_txt" ><bean:write name="subProds" property='subProdNm'  /></td>
		</logic:notEqual>
		</td>
        <td width="5"><img src="/mcx/static/images/ter_bluetab_right_corner.gif" alt="tab" width="5" height="24" /></td>
        <td width="4">&nbsp;</td>
       </logic:equal>
	  <logic:notEqual name='subProds' property='subProdCd' value='<%=(String)subPrdCd %>' >
	    <td width="4" >&nbsp;</td>
        <td width="5"><img src="/mcx/static/images/ter_bluetab_left_corner.gif" alt="tab" width="5" height="24" /></td>
        <td width="40" class="ter_inner_offtab_txt ">
		<logic:equal name='subProds' property='tmpltPresent' value='true' >
				<td width="40" class="ter_inner_offtab_txt" ><bean:write name="subProds" property='subProdNm'  /></td>
		</logic:equal>
		<logic:notEqual name='subProds' property='tmpltPresent' value='true' >
			<td width="40" class="ter_inner_offtab_txt" ><bean:write name="subProds" property='subProdNm'  /></td>
		</logic:notEqual>
		</td>
        <td width="5"><img src="/mcx/static/images/ter_bluetab_right_corner.gif" alt="tab" width="5" height="24" /></td>
        <td width="4">&nbsp;</td>
        </logic:notEqual>
        </logic:notEqual>
	 	</logic:iterate>
      </tr>
    </table></td>
    </logic:iterate>
    </logic:present>
    </tr>
</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	  <tr>
		<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
	  </tr>	  
				          <tr>
							<td colspan="2" height="10"><img src="/mcx/static/images/spacer.gif" /></td>
						  </tr>
				
							  <tr>
							  <td>
							  <table>
							  <tr>
								<td height="10px" class="con1 padL20a" colspan="9"><bean:write name="TemplateForm" property="transaction.ISDATmpltNm" />&nbsp&nbsp</td>
								<td height="10px"><input class="attach" onclick="window.print()" type="button" name="Submit" value="PRINT"/></td>
								</tr>
								</table>
								</td>
							 </tr>
							  <tr>
								<td height="10px" colspan="9"><img src="/mcx/static/images/spacer.gif" /></td>
							 </tr>
							 <tr align="right">
							 <td>
							 </td>
						  </tr>
						  <tr>
							<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
						  </tr>
						<logic:equal name="TemplateForm" property="frmScr" value="MW1" >
						  <tr>
							<td colspan="9">
							  <table width="100%"  border="0" class="mca_table" cellspacing="0" cellpadding="0">
									<logic:iterate id="categoryBean" name="TemplateForm" indexId="categyIdIndex" property ="categyBeanList">
									<logic:equal name="categoryBean" property="catgyLineWrap" value="1">
									<tr>
										<td height="12" width="22%" class="blue_bg1_mca">
											<bean:write name="categoryBean" property="catgyNm"/>
										</td>
										<td height="12" style="cursor:default" colspan="10" class="blue_bg1_mca">
											<logic:present  name="categoryBean"  property ="catgyTrm">
											<a href="#" style="cursor:default" >Click Here</a>
											</logic:present>
										</td>
										</tr>
										</logic:equal>
										<logic:equal name="categoryBean" property="catgyLineWrap" value="2">
									<tr>
										<td height="12" width="22%" class="blue_bg2_mca">
											<bean:write name="categoryBean" property="catgyNm"/>
										</td>
										<td style="cursor:default" height="12" colspan="10" class="blue_bg2_mca">
											<logic:present  name="categoryBean"  property ="catgyTrm">
											<a href="#" style="cursor:default">Click Here</a>
											</logic:present>
										</td>
										</tr>
										</logic:equal>
										<logic:equal name="categoryBean" property="catgyLineWrap" value="3">
									<tr>
										<td height="12" width="22%" class="blue_bg3_mca">
											<bean:write name="categoryBean" property="catgyNm"/>
										</td>
										<td height="12" colspan="10" class="blue_bg3_mca">
											<logic:present  name="categoryBean"  property ="catgyTrm">
											<a href="#" style="cursor:default">Click Here</a>
											</logic:present>
										</td>
										</tr>
										</logic:equal>
										<logic:equal name="categoryBean" property="catgyLineWrap" value="4">
									<tr>
										<td height="12" width="22%" class="blue_bg4_mca">
											<bean:write name="categoryBean" property="catgyNm"/>
										</td>
										<td height="12" colspan="10" class="blue_bg4_mca">
											<logic:present  name="categoryBean"  property ="catgyTrm">
											<a href="#" style="cursor:default">Click Here</a>
											</logic:present>
										</td>
										</tr>
										</logic:equal>
										<logic:equal name="categoryBean" property="catgyLineWrap" value="5">
									<tr>
										<td height="12" width="22%" class="blue_bg5_mca">
											<bean:write name="categoryBean" property="catgyNm"/>
										</td>
										<td height="12" colspan="10" class="blue_bg5_mca">
											<logic:present  name="categoryBean"  property ="catgyTrm">
											<a href="#" style="cursor:default">Click Here</a>
											</logic:present>
										</td>
										</tr>
										</logic:equal>
											<logic:present  name="categoryBean"  property ="termList">
											<logic:iterate id="termBean" name="categoryBean" indexId="tmpltIdIndex" property ="termList">
											<bean:define id="tempTermVal" name="termBean" property="termVal" />
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
													<logic:notEqual name="termBean" property="amndtStCd" value="I">
														<td colspan="10" width="76%" valign="top" class="red border5_bluemca">
														<%=tempTermVal %>
														<logic:equal name="TemplateForm" property="transaction.tmpltTyp" value="D">
														<logic:equal name="termBean" property="cmntInd" value="true">
															<img src="/mcx/static/images/note_icon.gif"/>
														</logic:equal>
														</logic:equal>&nbsp;
														</td>
													</logic:notEqual>
													<logic:equal name="termBean" property="amndtStCd" value="I">
														<td colspan="10" width="76%" valign="top" class="normal border5_bluemca">
														<%=tempTermVal %>
														<logic:equal name="TemplateForm" property="transaction.tmpltTyp" value="D">
														<logic:equal name="termBean" property="cmntInd" value="true">
															<img src="/mcx/static/images/note_icon.gif"/>
														</logic:equal>
														</logic:equal>&nbsp;
														</td>
													</logic:equal>
												</logic:equal>
												<logic:equal name="termBean" property="termStCd" value="N">
												<td colspan="10" width="76%" valign="top" class="normal border5_bluemca">
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
						</logic:equal>
						<logic:equal name="TemplateForm" property="frmScr" value="MW2" >
						<tr>
							<td colspan="9">
							  <table width="100%"  border="0" class="mca_table" cellspacing="0" cellpadding="0">
									<logic:iterate id="categoryBean" name="TemplateForm" indexId="categyIdIndex" property ="categyBeanList">
									<logic:equal name="categoryBean" property="catgyLineWrap" value="1">
									<tr>
										<td height="12" width="22%" class="blue_bg1_mca">	
											<bean:write name="categoryBean" property="catgyNm"/> 
										</td>
										<td height="12" colspan="8" class="blue_bg1_mca">	
											<logic:present  name="categoryBean"  property ="catgyTrm">
											<a href="#" style="cursor:default">Click Here</a>
											</logic:present>
										</td></tr>
										</logic:equal>
										<logic:equal name="categoryBean" property="catgyLineWrap" value="2">
									<tr>
										<td height="12" width="22%" class="blue_bg2_mca">	
											<bean:write name="categoryBean" property="catgyNm"/> 
										</td>
										<td height="12" colspan="8" class="blue_bg2_mca">	
											<logic:present  name="categoryBean"  property ="catgyTrm">
											<a href="#" style="cursor:default">Click Here</a>
											</logic:present>
										</td></tr>
										</logic:equal>
										<logic:equal name="categoryBean" property="catgyLineWrap" value="3">
									<tr>
										<td height="12" width="22%" class="blue_bg3_mca">	
											<bean:write name="categoryBean" property="catgyNm"/> 
										</td>
										<td height="12" colspan="8" class="blue_bg3_mca">	
											<logic:present  name="categoryBean"  property ="catgyTrm">
											<a href="#" style="cursor:default">Click Here</a>
											</logic:present>
										</td></tr>
										</logic:equal>
										<logic:equal name="categoryBean" property="catgyLineWrap" value="4">
									<tr>
										<td height="12" width="22%" class="blue_bg4_mca">	
											<bean:write name="categoryBean" property="catgyNm"/> 
										</td>
										<td height="12" colspan="8" class="blue_bg4_mca">	
											<logic:present  name="categoryBean"  property ="catgyTrm">
											<a href="#" style="cursor:default">Click Here</a>
											</logic:present>
										</td></tr>
										</logic:equal>
										<logic:equal name="categoryBean" property="catgyLineWrap" value="5">
									<tr>
										<td height="12" width="22%" class="blue_bg5_mca">	
											<bean:write name="categoryBean" property="catgyNm"/> 
										</td>
										<td height="12" colspan="8" class="blue_bg5_mca">	
											<logic:present  name="categoryBean"  property ="catgyTrm">
											<a href="#" style="cursor:default">Click Here</a>
											</logic:present>
										</td></tr>
										</logic:equal>
											<logic:present  name="categoryBean"  property ="termList">
											<logic:iterate id="termBean" name="categoryBean" indexId="tmpltIdIndex" property ="termList">
											<bean:define id="tempTermVal" name="termBean" property="termVal" />
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
														<td colspan="10" width="76%" valign="top" class="red border5_bluemca" >
														<%=tempTermVal %>
														<logic:equal name="termBean" property="cmntInd" value="true">
															<img src="/mcx/static/images/note_icon.gif"/>
														</logic:equal>&nbsp;
														</td>		
													</logic:equal>
													<logic:notEqual name="termBean" property="amndtStCd" value="P">
														<td colspan="10" width="76%" valign="top" class="normal border5_bluemca" >
														<%=tempTermVal %>
														<logic:equal name="termBean" property="cmntInd" value="true">
															<img src="/mcx/static/images/note_icon.gif"/>
														</logic:equal>&nbsp;
														</td>		
													</logic:notEqual>
												</logic:equal>
												<logic:equal name="termBean" property="termStCd" value="N">
												<td colspan="10" width="76%" valign="top" class="normal border5_bluemca">
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
						</logic:equal>
						<logic:equal name="TemplateForm" property="frmScr" value="NEG" >
													  <tr>
							<td colspan="9">
							  <table width="100%" border="0" class="mca_table" cellspacing="0" cellpadding="0">
									<logic:iterate id="categoryBean" name="TemplateForm" indexId="categyIdIndex" property ="categyBeanList">
									<logic:equal name="categoryBean" property="catgyLineWrap" value="1">
									<tr>
										<td height="12" width="22%" class="blue_bg1_mca">	
											<bean:write name="categoryBean" property="catgyNm"/> 
										</td>
										<td height="12" colspan="10" class="blue_bg1_mca">	
											<logic:present  name="categoryBean"  property ="catgyTrm">
											<a href="#" style="cursor:default">Click Here</a>
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
											<a href="#" style="cursor:default">Click Here</a>
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
											<a href="#" style="cursor:default">Click Here</a>
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
											<a href="#" style="cursor:default">Click Here</a>
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
											<a href="#" style="cursor:default">Click Here</a>
											</logic:present>
										</td></tr>
										</logic:equal>
											<logic:present  name="categoryBean"  property ="termList">
											<logic:iterate id="termBean" name="categoryBean" indexId="tmpltIdIndex" property ="termList">

											<bean:define id="tempTermVal" name="termBean" property="termVal" />
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
														<td colspan="10" width="76%" valign="top" class="red border5_bluemca" >
														<%=tempTermVal %>
														<logic:equal name="termBean" property="cmntInd" value="true">
															<img src="/mcx/static/images/note_icon.gif"/>
														</logic:equal>&nbsp;
														</td>		
													</logic:equal>
													<logic:equal name="termBean" property="amndtStCd" value="A">
														<td colspan="10" width="76%" valign="top" class="green border5_bluemca" >
														<%=tempTermVal %>
														<logic:equal name="termBean" property="cmntInd" value="true">
															<img src="/mcx/static/images/note_icon.gif"/>
														</logic:equal>&nbsp;
														</td>		
													</logic:equal>
													<logic:equal name="termBean" property="amndtStCd" value="I">
														<td colspan="10" width="76%" valign="top" class="normal border5_bluemca" >
														<%=tempTermVal %>
														<logic:equal name="termBean" property="cmntInd" value="true">
															<img src="/mcx/static/images/note_icon.gif"/>
														</logic:equal>&nbsp;
														</td>		
													</logic:equal>
												</logic:equal>
												<logic:equal name="termBean" property="termStCd" value="N">
												<td colspan="10" width="76%" valign="top" class="normal border5_bluemca">
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
						</logic:equal>
					 <logic:equal name="TemplateForm" property="frmScr" value="ISDA" >
						  <tr>
							<td colspan="9">
							  <table width="100%"  border="0" class="mca_table" cellspacing="0" cellpadding="0">
									<logic:iterate id="categoryBean" name="TemplateForm" indexId="categyIdIndex" property ="categyBeanList">
									<logic:notEqual name="categoryBean" property="catgyStCd" value="P">
									<logic:equal name="categoryBean" property="catgyLineWrap" value="1">
									<tr>
										<td height="12" width="22%" class="blue_bg1_mca">	
											<bean:write name="categoryBean" property="catgyNm"/> 
										</td>
										<td height="12" colspan="8" class="blue_bg1_mca">	
											<logic:present  name="categoryBean"  property ="catgyTrm">
											<a href="#" style="cursor:default">Click Here</a>
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
											<a href="#" style="cursor:default">Click Here</a>
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
											<a href="#" style="cursor:default">Click Here</a>
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
											<a href="#" style="cursor:default">Click Here</a>
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
											<a href="#" style="cursor:default">Click Here</a>
											</logic:present>
										</td></tr>
										 </logic:equal>
											<logic:present  name="categoryBean"  property ="termList">
											<logic:iterate id="termBean" name="categoryBean" indexId="tmpltIdIndex" property ="termList">
											<bean:define id="tempTermVal" name="termBean" property="termVal" />
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
														<td colspan="10" width="76%" valign="top" class="normal border5_bluemca">
														<%=tempTermVal %>
														<logic:equal name="termBean" property="cmntInd" value="true">
															<img src="/mcx/static/images/note_icon.gif" align="right" />
														</logic:equal>&nbsp;
														</td>		
												</logic:equal>
												<logic:equal name="termBean" property="termStCd" value="N">
												<td colspan="10" width="76%" valign="top" class="normal border5_bluemca">
												<bean:write name="termBean" property="termNm"/>&nbsp;
												</td>					
												</logic:equal>

											  </tr>	       
											</logic:iterate>
									</tr>
									</logic:present>
									</logic:notEqual>
									</logic:iterate>  
									
							  </table>
							 </td>
						  </tr> 
					</logic:equal>
			</table>
		  </td>
      </tr>
  <tr>
	   <td width="100%" height="10"><img src="/mcx/static/images/spacer.gif" /></td>
  </tr>
  <tr>
    <td width="100%" height="10"><img src="/mcx/static/images/spacer.gif" /></td>
  </tr>
    <tr align="right"><td>
     </td> </tr>

    </table></td>
  </tr>
  <tr>
    <td width="100%" height="10"><img src="/mcx/static/images/spacer.gif" /></td>
  </tr>

</table>
</html:form>
</body>
</html>
