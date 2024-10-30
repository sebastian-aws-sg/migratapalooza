<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="/WEB-INF/struts-html.tld"      prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"      prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"     prefix="logic" %>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="/mcx/static/css/primary.css" />
<script language="JavaScript" src="/mcx/static/js/mca_wizard.js"></script>
<script>
tmpltID 	= '<bean:write name ="TemplateForm" property="transaction.tmpltId" />';
tmpltShrtNm = '<bean:write name="TemplateForm" property="transaction.tmpltShrtNm" />';
ISDATmpltNm = '<bean:write name="TemplateForm" property="transaction.ISDATmpltNm" />';
</script>
</head>
<body>
<script>window.onscroll=scrollHeightWidth;</script>
<html:form action="/ViewMCA" method="post">	
<html:hidden name ="TemplateForm" property="tmpltId" />
<html:hidden name ="TemplateForm" property="frmScr" value="ISDA"/>
<html:hidden property="viewInd" value="F" />

	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	  <tr>
		<td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
	  </tr>	  
							<tr>
								<td colspan="9" class="con1 padL20a">Industry Published View</td>
							  </tr>
							  <tr>
								<td colspan="9" class="con9_mcaWiz"></td>
							  </tr>
						  <tr>							
							 <td class="con1 padL20a">
								<html:select name ="TemplateForm" property="transaction.tmpltId"  onchange="selectTemplate()" styleClass="temp1">
									<html:optionsCollection name ="TemplateForm" property="tmpltList" label="tmpltNm" value="tmpltId" />
								</html:select> 
							 </td>
							 </tr>
							 <tr align="right">
							 <td>
							  <table>
								<tr>
									<td  class="ter_tab_txt_small">Filter By:</td>
									<td>
										<html:select name ="TemplateForm" styleId="Category" property="catgyId" onchange="selectCategory()" styleClass="category" >
											<option value="">Select All Category</option>
											<html:optionsCollection name ="TemplateForm" property="categyList" label="catgyNm" value="catgyId" />
										</html:select> &nbsp;&nbsp;&nbsp;&nbsp;
									</td>
									<td class="reset"><a href="javascript:resetFilter()">Reset Filter</a></td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;<input class="attach" onclick="javascript:printMCA()"  type="button" name="Submit" value="PRINT VIEW"/></td>
									<td  class="ter_tab_txt_small"></td>
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
										<logic:notEqual name="categoryBean" property="catgyStCd" value="P">
									<logic:equal name="categoryBean" property="catgyLineWrap" value="1">
									<tr>
										<td height="12" width="22%" class="blue_bg1_mca">	
											<bean:write name="categoryBean" property="catgyNm"/> 
										</td>
										<td height="12" colspan="10" class="blue_bg1_mca">	
											<logic:present  name="categoryBean"  property ="catgyTrm">
											<a style="cursor:hand;" onclick="viewTerm( '<bean:write name="TemplateForm" property="transaction.tmpltTyp" />','<bean:write name="TemplateForm" property="transaction.lockSt" />','<bean:write name="categoryBean" property="catgyTrm.termValId" />');" >Click Here</a>
											</logic:present>
										</td>
										</tr>
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
											<a style="cursor:hand;" onclick="viewTerm( '<bean:write name="TemplateForm" property="transaction.tmpltTyp" />','<bean:write name="TemplateForm" property="transaction.lockSt" />','<bean:write name="categoryBean" property="catgyTrm.termValId" />');" >Click Here</a>
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
											<a style="cursor:hand;" onclick="viewTerm( '<bean:write name="TemplateForm" property="transaction.tmpltTyp" />','<bean:write name="TemplateForm" property="transaction.lockSt" />','<bean:write name="categoryBean" property="catgyTrm.termValId" />');" >Click Here</a>
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
											<a style="cursor:hand;" onclick="viewTerm( '<bean:write name="TemplateForm" property="transaction.tmpltTyp" />','<bean:write name="TemplateForm" property="transaction.lockSt" />','<bean:write name="categoryBean" property="catgyTrm.termValId" />');" >Click Here</a>
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
  <tr>
    <td height="10"><img src="/mcx/static/images/spacer.gif" /></td>
  </tr>
</table>
</html:form>
</body>
</html>
