<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/alert.tld" prefix="alert" %>
<%@ taglib uri="/WEB-INF/derivServ.tld" prefix="derivserv" %>
<div class="menu_side" id="sidemenu" style="overflow:visible">
<derivserv:formMCAMenu>
	<bean:write name="MCASideMenu" scope="request" filter="false" />
</derivserv:formMCAMenu>
</div>
	<table width="100%" height="25px" cellspacing="0">
		<tr class="tab_bg">
				<td width="99" class="tab_con" id="tab"
					onmouseover="home2a_mouseover()" 
  				    onmouseout="menumouseout('tab')"
					onclick="menu_click('/mcx/action/getDealerDetails?mcaStatus=P&printParam=N')"
					>Home <img 
					align="absmiddle" src="/mcx/static/images/orange_arrow.gif"
					alt="orange arrow for drop down" width="7" height="4" /></td>
			<logic:present name="MCASideMenu" scope="request" >
			<td width="98" class="tab_con" id="tab2"
					onmouseover="mca_mouseover(false,true)" 
				onmouseout="menumouseout('tab2')"
				>MCAs <img
				align="absmiddle" src="/mcx/static/images/orange_arrow.gif"
				alt="arrow for drop down" /></td>
			</logic:present>
			<logic:notPresent name="MCASideMenu" scope="request" >
				<td width="98" class="tab_con" id="tab2"
					onmouseover="mca_mouseover(false,false)" 
					onmouseout="menumouseout('tab2')"
					>MCAs <img
					align="absmiddle" src="/mcx/static/images/orange_arrow.gif"
					alt="arrow for drop down" /></td>
			</logic:notPresent>
			<td width="164" class="tab_con" id="tab3"
				onmouseover="home2a_mngdoc_mouseover(false)"
				onmouseout="menumouseout('tab3')"
				onclick="menu_click('/mcx/action/manageDoc?manageDocTab=P')"				
				>Manage Documents <img
				align="absmiddle" src="/mcx/static/images/orange_arrow.gif"
				alt="orange arrow for drop down" width="7" height="4" /></td>
			<td width="103" class="tab_con" id="tab4"
				onmouseover="this.className='tab_mouseover'"
				onmouseout="menumouseout('tab4')"
				onclick="menu_click('/mcx/action/getDealerInfo')">Enrollment</td>
		   <td width="257" class="tab_con3 padR28" title='<bean:message key="mcx.release.number"/>'>&nbsp;</td>
		<td width="97" class="tab_con2 padR5" style="text-align:right"><a href="/mcx/action/userViewAlert">
				<alert:FoundNewAlerts>
				<img
				border="0" src="/mcx/static/images/alert.gif" alt="New Alerts Have Been Posted" width="17"
				height="17" align="absmiddle" /> 
				</alert:FoundNewAlerts>
				<alert:NotFoundNewAlerts>
				<img
				border="0" src="/mcx/static/images/alert-black.gif" alt="No New Alerts Have Been Posted" width="17"
				height="17" align="absmiddle" /> 
				</alert:NotFoundNewAlerts>
				Alerts </a></td>			
		</tr>
	</table>
