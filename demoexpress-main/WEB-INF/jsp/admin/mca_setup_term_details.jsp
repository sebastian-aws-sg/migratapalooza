<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="/WEB-INF/struts-html.tld"      prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"      prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"     prefix="logic" %>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>DTCC - MCA - Xpress - Home</title>
<link rel="stylesheet" type="text/css" href="/mcx/static/css/primary.css" />
<script language="JavaScript" src="/mcx/static/js/primary.js"></script>
<script language="JavaScript" src="/mcx/static/js/mca_setup_term_details.js"></script>
<script>
lockSt 		= '<bean:write name ="TemplateForm" property="transaction.lockSt" />';
tmpltID 	= '<bean:write name ="TemplateForm" property="transaction.tmpltId" />';
tmpltType 	= '<bean:write name="TemplateForm" property="transaction.tmpltTyp" />';
ISDATmpltNm = '<bean:write name="TemplateForm" property="transaction.tmpltShrtNm" />';
mcaStatusCd = '<bean:write name="TemplateForm" property="transaction.mcaStatusCd" />';		
lockUsrInd = '<bean:write name="TemplateForm" property="transaction.lockUsrInd" />';
tempValAvl = '<bean:write name="TemplateForm" property="transaction.tempValAvl" />';
</script>
</head>
<body onclick="clickBody()" onload="scrollToPosotion()">
<div id=rightmenu  onclick="rightClickAction()" onmouseover="switchMenu()" onmouseout="switchMenu()" style="position:absolute;
	background-color:#FFFFFF;
	border:1px solid #3A7BB8;
	width:116px;
	display:none;
	padding-left:2px;">
<table width="100%" border="0"  cellspacing="0"  cellpadding="2" >
<tr>
<td class="menuItem" id="modify"><img id="modify" src="/mcx/static/images/post_comm_his_icon.gif" alt="Modify"/> Modify</td>
</tr>
</table> 
</div>

<html:form action="/ViewAdminISDATemplate" method="post">
<html:hidden name ="TemplateForm" property="tmpltId" />
<html:hidden name ="TemplateForm" property="tmpltNm" />
<html:hidden name ="TemplateForm" property="tmpltTyp" />
<html:hidden name ="TemplateForm" property="mcaStatusCd" />
<html:hidden name ="TemplateForm" property="lockInd" />
<html:hidden name ="TemplateForm" property="appRejStatus" />
<html:hidden name ="TemplateForm" property="publishDt" />
<html:hidden name ="TemplateForm" property="opnInd" />
<html:hidden name="TemplateForm" property="scrollPosition"/>
<html:hidden name ="TemplateForm" property="sltInd" />
<html:hidden name="TemplateForm" property="flagTemplateApprovedRejected"/>
<html:hidden name="TemplateForm" property="errorTemplateApprovedRejected"/>
<html:hidden name="TemplateForm" property="enableSave" />
<html:hidden name="TemplateForm" property="enablePublish" />
<html:hidden name="TemplateForm" property="enableApprRej" />

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
	  <td width="123" style="padding: 0px 0px 0px 50px;" class="ter_tab_txt" align="justify"><bean:write name="prods" property="prodNm" /></td>
	</logic:equal>
	<logic:notEqual name='prods' property='prodId' value='<%=(String)prdCd %>' >
	  <td width="123" style="padding: 0px 0px 0px 50px;" class="ter_tab_dis_txt" align="justify"><bean:write name="prods" property="prodNm" /></td>
	</logic:notEqual>
   </logic:iterate>
  </logic:present>
    <td width="62" rowspan="5" class="ter_tab_txt_small"></td>
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
    <td height="10px" colspan="12"><img src="/mcx/static/images/spacer.gif" /></td>
  </tr>  
  <tr>
        <td colspan="9" class="con1 padL20a"><bean:write name="TemplateForm" property="transaction.tmpltNm"/>   </td>
  </tr>
  <tr>
	<td height="10px" colspan="9"><img src="/mcx/static/images/spacer.gif" /></td>
  </tr>
  
  <tr>
    <td colspan="12" class="padLR5"><table width="100%" cellpadding="0" cellspacing="0">
      
      <tr>
        <td colspan="9" ><table width="100%" cellpadding="0" cellspacing="0" >
  <tr>
    <td colspan="7" >
        <td width="118px" >&nbsp;</td>

      <td width="11%" align="right" ><input class="attach" type="button" name="Submit" value="PRINT VIEW" onclick="javascript:printMCA()"/></td>
	  <td width="4%" >&nbsp;</td>
  </tr>
      <tr>
        <td height="10px" colspan="9"><img src="/mcx/static/images/spacer.gif" /></td>
      </tr>
      <tr>
        <td width="12%" class="padL20a">
	        <html:select name ="TemplateForm" property="catgyId"  styleClass="category" onchange="viewSetupByCategory()" >
				<option value="">All</option>
				<html:optionsCollection name ="TemplateForm" property="categyList" label="catgyNm" value="catgyId" />
			</html:select>
    	</td>

        <td width="3%">&nbsp;</td>
        <td width="15%" class="con9">&nbsp;</td>
        <td width="3%" class="con9">&nbsp;</td>
        <td width="22%" class="con9">&nbsp;</td>
        <td width="12%" class="padL20a con9">&nbsp;</td>

		<logic:equal name="TemplateForm" property="transaction.lockSt" value="Y">        
				<logic:equal name="TemplateForm" property="transaction.lockUsrInd" value="L">
					<td width="12%"  colspan="2" align="right">
						<input class="enroll_red" 	type="button" name="Submit_unlock" 	value="UNLOCK" 	onclick="admin_unlock_inprogress_mca('<bean:write name ="TemplateForm" property="transaction.tmpltId" />','<bean:write name ="TemplateForm" property="transaction.tempValAvl" />')" id="back" />
						<img border="0" align="middle" src="/mcx/static/images/lock.gif" alt="This template is locked by  <bean:write name="TemplateForm" property="transaction.lockByUsrNm"/>"/>
					</td>
				</logic:equal>
				<logic:notEqual name="TemplateForm" property="transaction.lockUsrInd" value="L">
					<td width="12%"  colspan="2" align="right">		
						<input class="enroll_red" disabled type="button" name="Submit_unlock" value="UNLOCK" onclick="admin_unlock_inprogress_mca('<bean:write name ="TemplateForm" property="transaction.tmpltId" />','<bean:write name ="TemplateForm" property="transaction.tempValAvl" />')" id="back" />
						<img border="0" align="middle" src="/mcx/static/images/unlock.gif" alt="This template is locked by  <bean:write name="TemplateForm" property="transaction.lockByUsrNm"/>"/>
					</td>
				</logic:notEqual>
				<td width="8%" align="right"><input class="enroll" type="button" name="Submit" value="BACK" onclick="javascript:goBack()"/></td>	  
		</logic:equal>

		<logic:notEqual name="TemplateForm" property="transaction.lockSt" value="Y">
	        <td width="20%" colspan="3" align="right"><input class="enroll" type="button" name="Submit" value="BACK" onclick="javascript:goBack()"/></td>
		</logic:notEqual>	


      </tr>
     
      <tr>
        <td height="10px" colspan="9"><img src="/mcx/static/images/spacer.gif" /></td>
      </tr>
      
      <tr>
        <td height="10px" colspan="9"><img src="/mcx/static/images/spacer.gif" /></td>
      </tr>
      <tr>
        <td colspan="9"><table width="100%" class="border" cellpadding="0" cellspacing="0">
          <tr>
            <td colspan="4">
              <table width="100%"  border="0" class="mca_table" cellspacing="0" cellpadding="0">
              </table></td>
            </tr>

		<logic:iterate id="categoryBean" name="TemplateForm" indexId="categyIdIndex" property ="categyBeanList">
			<logic:notEqual name="categoryBean" property="catgyStCd" value="P">
			<logic:equal name="categoryBean" property="catgyLineWrap" value="1">
			<tr>
				<td height="12" width="22%" class="blue_bg1_mca">	
					<bean:write name="categoryBean" property="catgyNm"/> 
				</td>
				<td height="12" colspan="8" class="blue_bg1_mca">	
					<logic:present  name="categoryBean"  property ="catgyTrm">
						<logic:empty name="categoryBean" property="catgyTrm.termVal">						
						</logic:empty>	
					<a onClick="editTerm('<bean:write name="TemplateForm" property="transaction.tmpltTyp" />','<bean:write name="categoryBean" property="catgyTrm.termValId" />','<bean:write name="categoryBean" property="catgyTrm.catgyId" />','<bean:write name="categoryBean" property="catgyTrm.catgySqId" />','<bean:write name="categoryBean" property="catgyTrm.catgyNm" />'
		 ,'<bean:write name="categoryBean" property="catgyTrm.termId" />','<bean:write name="categoryBean" property="catgyTrm.termSqId" />','<bean:write name="categoryBean" property="catgyTrm.termNm" />');" style="cursor:hand">Click Here</a>
					</logic:present>
				</td>
			</tr>
			</logic:equal>
			<logic:equal name="categoryBean" property="catgyLineWrap" value="2">
			<tr>
				<td height="12" width="22%" class="blue_bg2_mca">	
					<bean:write name="categoryBean" property="catgyNm"/> 
				</td>
				<td height="12" colspan="8" class="blue_bg2_mca">	
					<logic:present  name="categoryBean"  property ="catgyTrm">
						<logic:empty name="categoryBean" property="catgyTrm.termVal">						
						</logic:empty>	
					<a onClick="editTerm('<bean:write name="TemplateForm" property="transaction.tmpltTyp" />','<bean:write name="categoryBean" property="catgyTrm.termValId" />','<bean:write name="categoryBean" property="catgyTrm.catgyId" />','<bean:write name="categoryBean" property="catgyTrm.catgySqId" />','<bean:write name="categoryBean" property="catgyTrm.catgyNm" />'
		 ,'<bean:write name="categoryBean" property="catgyTrm.termId" />','<bean:write name="categoryBean" property="catgyTrm.termSqId" />','<bean:write name="categoryBean" property="catgyTrm.termNm" />');" style="cursor:hand">Click Here</a>
					</logic:present>
				</td>
			</tr>
			</logic:equal>
			<logic:equal name="categoryBean" property="catgyLineWrap" value="3">
			<tr>
				<td height="12" width="22%" class="blue_bg3_mca">	
					<bean:write name="categoryBean" property="catgyNm"/> 
				</td>
				<td height="12" colspan="8" class="blue_bg3_mca">	
					<logic:present  name="categoryBean"  property ="catgyTrm">
						<logic:empty name="categoryBean" property="catgyTrm.termVal">						
						</logic:empty>	
					<a onClick="editTerm('<bean:write name="TemplateForm" property="transaction.tmpltTyp" />','<bean:write name="categoryBean" property="catgyTrm.termValId" />','<bean:write name="categoryBean" property="catgyTrm.catgyId" />','<bean:write name="categoryBean" property="catgyTrm.catgySqId" />','<bean:write name="categoryBean" property="catgyTrm.catgyNm" />'
		 ,'<bean:write name="categoryBean" property="catgyTrm.termId" />','<bean:write name="categoryBean" property="catgyTrm.termSqId" />','<bean:write name="categoryBean" property="catgyTrm.termNm" />');" style="cursor:hand">Click Here</a>
					</logic:present>
				</td>
			</tr>
			</logic:equal>
			<logic:equal name="categoryBean" property="catgyLineWrap" value="4">
			<tr>
				<td height="12" width="22%" class="blue_bg4_mca">	
					<bean:write name="categoryBean" property="catgyNm"/> 
				</td>
				<td height="12" colspan="8" class="blue_bg4_mca">	
					<logic:present  name="categoryBean"  property ="catgyTrm">
						<logic:empty name="categoryBean" property="catgyTrm.termVal">						
						</logic:empty>	
					<a onClick="editTerm('<bean:write name="TemplateForm" property="transaction.tmpltTyp" />','<bean:write name="categoryBean" property="catgyTrm.termValId" />','<bean:write name="categoryBean" property="catgyTrm.catgyId" />','<bean:write name="categoryBean" property="catgyTrm.catgySqId" />','<bean:write name="categoryBean" property="catgyTrm.catgyNm" />'
		 ,'<bean:write name="categoryBean" property="catgyTrm.termId" />','<bean:write name="categoryBean" property="catgyTrm.termSqId" />','<bean:write name="categoryBean" property="catgyTrm.termNm" />');" style="cursor:hand">Click Here</a>
					</logic:present>
				</td>
			</tr>
			</logic:equal>
			<logic:equal name="categoryBean" property="catgyLineWrap" value="5">
			<tr>
				<td height="12" width="22%" class="blue_bg5_mca">	
					<bean:write name="categoryBean" property="catgyNm"/> 
				</td>
				<td height="12" colspan="8" class="blue_bg5_mca">	
					<logic:present  name="categoryBean"  property ="catgyTrm">
						<logic:empty name="categoryBean" property="catgyTrm.termVal">						
						</logic:empty>	
					<a onclick="editTerm('<bean:write name="TemplateForm" property="transaction.tmpltTyp" />','<bean:write name="categoryBean" property="catgyTrm.termValId" />','<bean:write name="categoryBean" property="catgyTrm.catgyId" />','<bean:write name="categoryBean" property="catgyTrm.catgySqId" />','<bean:write name="categoryBean" property="catgyTrm.catgyNm" />'
		 ,'<bean:write name="categoryBean" property="catgyTrm.termId" />','<bean:write name="categoryBean" property="catgyTrm.termSqId" />','<bean:write name="categoryBean" property="catgyTrm.termNm" />');" style="cursor:hand">Click Here</a>
					</logic:present>
				</td>
			</tr>
			</logic:equal>
			<logic:present  name="categoryBean"  property ="termList">
			<logic:iterate id="termBean" name="categoryBean" indexId="tmpltIdIndex" property ="termList">			
			<logic:empty name="termBean" property="termVal">			
			</logic:empty>		
			  <tr>
				<td width="21%" height="60" valign="top" class="product_bg border4a1mca" >	 <bean:write name="termBean" property="termNm"/><br /></td>

				<logic:equal name="TemplateForm" property="transaction.lockSt" value="Y">
					<logic:equal name="TemplateForm" property="transaction.lockUsrInd" value="L">
						<logic:equal name="TemplateForm" property="transaction.mcaStatusCd" value="N">
							<td colspan="6" width="76%" valign="top" class="normal border5_bluemca"  oncontextmenu="if (!event.ctrlKey){displayMenu( '<bean:write name="TemplateForm" property="transaction.tmpltTyp" />','<bean:write name="termBean" property="termValId" />','<bean:write name="categoryBean" property="catgyId" />','<bean:write name="categoryBean" property="catgySqId" />','<bean:write name="categoryBean" property="catgyNm" />'
		 ,'<bean:write name="termBean" property="termId" />','<bean:write name="termBean" property="termSqId" />','<bean:write name="termBean" property="termNm" />');return false;}">&nbsp;<bean:write name="termBean" property="termVal" filter="false" /></td>					
						 </logic:equal>
						<logic:equal name="TemplateForm" property="transaction.mcaStatusCd" value="I">
							<td colspan="6" width="76%" valign="top" class="normal border5_bluemca" oncontextmenu="if (!event.ctrlKey){displayMenu( '<bean:write name="TemplateForm" property="transaction.tmpltTyp" />','<bean:write name="termBean" property="termValId" />','<bean:write name="categoryBean" property="catgyId" />','<bean:write name="categoryBean" property="catgySqId" />','<bean:write name="categoryBean" property="catgyNm" />'
		 ,'<bean:write name="termBean" property="termId" />','<bean:write name="termBean" property="termSqId" />','<bean:write name="termBean" property="termNm" />');return false;}">&nbsp;<bean:write name="termBean" property="termVal" filter="false" /></td>					
						 </logic:equal>
						<logic:equal name="TemplateForm" property="transaction.mcaStatusCd" value="W">
							<td colspan="6" width="76%" valign="top"  class="normal border5_bluemca">&nbsp;<bean:write name="termBean" property="termVal" filter="false" /></td>					
						 </logic:equal>
						<logic:equal name="TemplateForm" property="transaction.mcaStatusCd" value="P">
							<td colspan="6" width="76%" valign="top"  class="normal border5_bluemca" >&nbsp;<bean:write name="termBean" property="termVal" filter="false" /></td>					
						 </logic:equal>
					 </logic:equal>
				 </logic:equal>
				<logic:equal name="TemplateForm" property="transaction.lockSt" value="Y">
					<logic:notEqual name="TemplateForm" property="transaction.lockUsrInd" value="L">
							<td colspan="6" width="76%" valign="top"  class="normal border5_bluemca">&nbsp;<bean:write name="termBean" property="termVal" filter="false" /></td>					
					 </logic:notEqual>
				 </logic:equal>

				<logic:notEqual name="TemplateForm" property="transaction.lockSt" value="Y">
						<logic:equal name="TemplateForm" property="transaction.mcaStatusCd" value="N">
							<td colspan="6" width="76%" valign="top" class="normal border5_bluemca"  oncontextmenu="if (!event.ctrlKey){displayMenu( '<bean:write name="TemplateForm" property="transaction.tmpltTyp" />','<bean:write name="termBean" property="termValId" />','<bean:write name="categoryBean" property="catgyId" />','<bean:write name="categoryBean" property="catgySqId" />','<bean:write name="categoryBean" property="catgyNm" />'
		 ,'<bean:write name="termBean" property="termId" />','<bean:write name="termBean" property="termSqId" />','<bean:write name="termBean" property="termNm" />');return false;}">&nbsp;<bean:write name="termBean" property="termVal" filter="false" /></td>					
						 </logic:equal>
						<logic:equal name="TemplateForm" property="transaction.mcaStatusCd" value="I">
							<td colspan="6" width="76%" valign="top"  class="normal border5_bluemca"  oncontextmenu="if (!event.ctrlKey){displayMenu( '<bean:write name="TemplateForm" property="transaction.tmpltTyp" />','<bean:write name="termBean" property="termValId" />','<bean:write name="categoryBean" property="catgyId" />','<bean:write name="categoryBean" property="catgySqId" />','<bean:write name="categoryBean" property="catgyNm" />'
		 ,'<bean:write name="termBean" property="termId" />','<bean:write name="termBean" property="termSqId" />','<bean:write name="termBean" property="termNm" />');return false;}">&nbsp;<bean:write name="termBean" property="termVal" filter="false" /></td>					
						 </logic:equal>
						<logic:equal name="TemplateForm" property="transaction.mcaStatusCd" value="W">
							<td colspan="6" width="76%" valign="top"  class="normal border5_bluemca">&nbsp;<bean:write name="termBean" property="termVal" filter="false" /></td>					
						 </logic:equal>
						<logic:equal name="TemplateForm" property="transaction.mcaStatusCd" value="P">
							<td colspan="6" width="76%" valign="top"  class="normal border5_bluemca" >&nbsp;<bean:write name="termBean" property="termVal" filter="false" /></td>					
						 </logic:equal>
				 </logic:notEqual>
			  </tr>	       
			</logic:iterate>
			</logic:present>
			 </logic:notEqual>
		</logic:iterate>          
		</table>
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
           <tr>
                <td height="10" colspan="12"><img src="/mcx/static/images/spacer.gif" /></td>
            </tr>
		   </table>
		  <table width="100%" border="0" cellpadding="1" cellspacing="1">
			<logic:notEqual name="TemplateForm" property="transaction.mcaStatusCd" value="P">
				<tr>
				  <td width="268">&nbsp;</td>  
				  <td width="76" align="right">&nbsp;
				  <logic:equal name="TemplateForm" property="enableSave" value="true" >
					  <input class="enroll_sendback" id="save_pending" type="button" name="Submit2" value="SAVE" 
						onclick="admin_save_inprogress_mca( '<bean:write name ="TemplateForm" property="transaction.tmpltId" />',
															'<bean:write name ="TemplateForm" property="transaction.tmpltNm" />',
															'<bean:write name ="TemplateForm" property="transaction.tmpltTyp" />')"/>
				  </logic:equal>
				  <logic:notEqual name="TemplateForm" property="enableSave" value="true" >
					  <input class="enroll_greyed_disabled" id="save_pending" type="button" name="Submit2" value="SAVE" />
				  </logic:notEqual>
				  </td>
				  <td width="170" align="right">&nbsp;
				  <logic:equal name="TemplateForm" property="enablePublish" value="true" >
					  <input class="long_sendback" id="publish_pending" type="button" name="Submit" value="SUBMIT FOR APPROVAL" onclick="admin_send_approve_inprogress_mca('/mcx/action/ApproveRejectAdminTemplate', '<bean:write name ="TemplateForm" property="transaction.tmpltId" />', 'W')"/>
				  </logic:equal>
				  <logic:notEqual name="TemplateForm" property="enablePublish" value="true" >
					  <input class="long_greyed_disabled_sendback" id="publish_pending" type="button" name="Submit" value="SUBMIT FOR APPROVAL" />
				  </logic:notEqual>
				  </td>
				  <td width="170" align="right">&nbsp;
				  <logic:equal name="TemplateForm" property="enableApprRej" value="true" >
					  <input class="long_sendback" id="approve_pending" type="button" name="Submit" value="APPROVE AND POST" onclick="admin_approve_pending_mca()" />
				  </logic:equal>
				  <logic:notEqual name="TemplateForm" property="enableApprRej" value="true" >
					  <input class="long_greyed_disabled_sendback" id="approve_pending" type="button" name="Submit" value="APPROVE AND POST" />
				  </logic:notEqual>
				  </td>
				  <td width="75" align="left">&nbsp;
				  <logic:equal name="TemplateForm" property="enableApprRej" value="true" >
					  <input class="enroll_sendback" id="reject_pending" type="button" name="Submit" value="REJECT" onclick="admin_reject_pending_mca('<bean:write name ="TemplateForm" property="transaction.tmpltId" />')"/>  
				   </logic:equal>
				  <logic:notEqual name="TemplateForm" property="enableApprRej" value="true" >
					  <input class="enroll_greyed_disabled" id="reject_pending" type="button" name="Submit" value="REJECT" />  
				  </logic:notEqual>
				  </td>
				</tr>
			</logic:notEqual>
			<logic:equal name="TemplateForm" property="transaction.mcaStatusCd" value="P">
				<tr>
				  <td width="218">&nbsp;</td>  
				  <td width="146" align="right">&nbsp;</td>
				  <td width="182" align="right">&nbsp;</td>
				  <td width="182" align="right">&nbsp;</td>
				  <td width="75" align="right">&nbsp;</td>  
				</tr>
			</logic:equal>
			<tr>
                <td height="10" colspan="12"><img src="/mcx/static/images/spacer.gif" /></td>
            </tr>
          </table>
		  </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="10" colspan="12"><img src="/mcx/static/images/spacer.gif" /></td>
  </tr>
</table>
</html:form>
</body>
</html>


