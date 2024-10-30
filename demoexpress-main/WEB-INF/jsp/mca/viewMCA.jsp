<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>
<%@ taglib uri="/WEB-INF/derivServ.tld" prefix="derivserv" %>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles" %>


<html:html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>DTCC - MCA - Xpress - Home</title>
<link rel="stylesheet" type="text/css" href="/mcx/static/css/primary.css" />
<script language="JavaScript" src="/mcx/static/js/primary.js"></script>
<script language="JavaScript" src="/mcx/static/js/mca_wizard.js"></script>
</head>
<body onload="scrollToPosotion()">
<html:form action="/ViewMCA" method="post" >
<html:hidden property="sltInd" />
<html:hidden property="opnInd" />
<html:hidden property="prodCd" />
<html:hidden property="subProdCd" />
<html:hidden property="scrollPosition" />
<html:hidden property="transaction.rgnCd" styleId="RegionCD"></html:hidden>
<html:hidden property="catgyId" styleId="catgyID"></html:hidden>
<html:hidden property="viewInd" styleId="viewIND"></html:hidden>

<logic:notEqual name="TemplateForm" property="tmpltPresent" value="true">
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td height="10px" colspan="3"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td>
				<TABLE>
					<tr>
						<td width="110%" class="con1 padL20a"></td>
					</tr>
				</TABLE>
			</td>
		</tr>
		<tr>
			<td height="10" colspan="8"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>	
		<tr>
			<td colspan="3" class="con10 padL20a">No Templates have been published</td>
		</tr>
		<tr>
			<td height="10" colspan="8"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
		<tr>
			<td align="center">
			</td>
		</tr>
		<tr>
			<td align="center" width="100%"></td>
		</tr>
		<tr>
			<td height="10px" colspan="7"><img src="/mcx/static/images/spacer.gif" /></td>
		</tr>
	</table>
</logic:notEqual>
<logic:equal name="TemplateForm" property="tmpltPresent" value="true">
<logic:present name="TemplateForm" property="transaction">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
   <tr>
    <td height="12"><img src="/mcx/static/images/spacer.gif" /></td>
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
    <td width="62" rowspan="5" class="ter_tab_txt_small">Filter By:</td>
    <td width="97" rowspan="5">
		<html:select name ="TemplateForm" property="transaction.rgnCd" styleId="RegCode" onchange="javascript:selectRegion()" styleClass="region" >
			<html:optionsCollection name ="TemplateForm" property="rgnList" label="regionNm" value="regionCd" />
		</html:select> 
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
			<logic:notEqual name="TemplateForm" property="frmScr" value="MW2" >
			<logic:notEqual name="TemplateForm" property="frmScr" value="NEG" >
				<td width="40" style="cursor:hand;" class="ter_inner_ontab_txt" onclick="selectProd('<bean:write name="mainProds" property="prodId"/>','<bean:write name="subProds" property="subProdCd"/>');" ><bean:write name="subProds" property='subProdNm'  /></td>
			</logic:notEqual>
			</logic:notEqual>
			<logic:equal name="TemplateForm" property="frmScr" value="MW2" >
				<td width="40" class="ter_inner_ontab_txt" ><bean:write name="subProds" property='subProdNm'  /></td>
			</logic:equal>
			<logic:equal name="TemplateForm" property="frmScr" value="NEG" >
				<td width="40" class="ter_inner_ontab_txt" ><bean:write name="subProds" property='subProdNm'  /></td>
			</logic:equal>
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
			<logic:notEqual name="TemplateForm" property="frmScr" value="MW2" >
			<logic:notEqual name="TemplateForm" property="frmScr" value="NEG" >
				<td width="40" style="cursor:hand;" class="ter_inner_offtab_txt" onclick="selectProd('<bean:write name="mainProds" property="prodId"/>','<bean:write name="subProds" property="subProdCd"/>');" ><bean:write name="subProds" property='subProdNm'  /></td>
			</logic:notEqual>
			</logic:notEqual>
			<logic:equal name="TemplateForm" property="frmScr" value="MW2" >
				<td width="40" class="ter_inner_offtab_txt" ><bean:write name="subProds" property='subProdNm'  /></td>
			</logic:equal>
			<logic:equal name="TemplateForm" property="frmScr" value="NEG" >
				<td width="40" class="ter_inner_offtab_txt" ><bean:write name="subProds" property='subProdNm'  /></td>
			</logic:equal>
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
			<logic:notEqual name="TemplateForm" property="frmScr" value="MW2" >
			<logic:notEqual name="TemplateForm" property="frmScr" value="NEG" >
				<td width="40" style="cursor:hand;" class="ter_inner_offtab_txt" onclick="selectProd('<bean:write name="mainProds" property="prodId"/>','<bean:write name="subProds" property="subProdCd"/>');" ><bean:write name="subProds" property='subProdNm'  /></td>
			</logic:notEqual>
			</logic:notEqual>
			<logic:equal name="TemplateForm" property="frmScr" value="MW2" >
				<td width="40" class="ter_inner_offtab_txt" ><bean:write name="subProds" property='subProdNm'  /></td>
			</logic:equal>
			<logic:equal name="TemplateForm" property="frmScr" value="NEG" >
				<td width="40" class="ter_inner_offtab_txt" ><bean:write name="subProds" property='subProdNm'  /></td>
			</logic:equal>
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
			<logic:notEqual name="TemplateForm" property="frmScr" value="MW2" >
			<logic:notEqual name="TemplateForm" property="frmScr" value="NEG" >
				<td width="40" style="cursor:hand;" class="ter_inner_offtab_txt" onclick="selectProd('<bean:write name="mainProds" property="prodId"/>','<bean:write name="subProds" property="subProdCd"/>');" ><bean:write name="subProds" property='subProdNm'  /></td>
			</logic:notEqual>
			</logic:notEqual>
			<logic:equal name="TemplateForm" property="frmScr" value="MW2" >
				<td width="40" class="ter_inner_offtab_txt" ><bean:write name="subProds" property='subProdNm'  /></td>
			</logic:equal>
			<logic:equal name="TemplateForm" property="frmScr" value="NEG" >
				<td width="40" class="ter_inner_offtab_txt" ><bean:write name="subProds" property='subProdNm'  /></td>
			</logic:equal>
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
</td>
      </tr>
      <tr>
        <td>
         <logic:equal name="TemplateForm" property="frmScr" value="ISDA" >
  		    <tiles:insert name="indus_pub_view_content" />
        </logic:equal>
        <logic:equal name="TemplateForm" property="frmScr" value="MW1" >
  		    <tiles:insert name="mca_wizard_step1_content" />
        </logic:equal>
        <logic:equal name="TemplateForm" property="frmScr" value="MW2" >
  		    <tiles:insert name="mca_wizard_step2_content" />
        </logic:equal>
        <logic:equal name="TemplateForm" property="frmScr" value="NEG" >
  		    <tiles:insert name="mca_negotiation_content" />
        </logic:equal>
          </td>
      </tr>
      <tr> <td>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
         <tr>
             <td height="10"><img src="/mcx/static/images/spacer.gif"></td>
         </tr>
  <tr>
    <td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
  </tr>
  <tr>
    <td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
  </tr>
  </table>
	</td></tr>
   </table></td></tr>
</table>
<derivserv:access privilege="Modify">
<script>
hasModify = true;
</script>
</derivserv:access>

<script>
if(!hasModify && document.forms[0].frmScr.value != "ISDA")
{
	postAmend.style.display = "none";
	postComnt.style.display = "none";
	agreeAmend.style.display = "none";
}
if(document.forms[0].frmScr.value == "NEG" || document.forms[0].frmScr.value == "MW2")
{
	document.getElementById('RegCode').disabled = "true";
}
document.getElementById("RegCode").value = document.getElementById("RegionCD").value;
document.getElementById("prodCd").value = '<%=(String)prdCd %>';
document.getElementById("subProdCd").value = '<%=(String)subPrdCd %>';
</script>
</logic:present>
</logic:equal>
</html:form>
</body>
</html:html>






						 
	 