<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

	<table width="100%" height="25px" cellspacing="0">
		<tr class="tab_bg">
			<td width="14%" class="tab_con" id="mcasetup"		
				onmouseover="this.className='tab_mouseover'"
				onmouseout="menumouseout('mcasetup')"
				onclick="menu_click('/mcx/action/adminSetup')">MCA Setup</td>
			<td width="5%" class="tab_con" id="alerts"
				onmouseover="adminalert_mouseover()"
				onmouseout="menumouseout('alerts')"
				onclick="menu_click('/mcx/action/adminViewAlert')"
				><bean:message key="alert.alerts"/></td>
			<td align="right" width="75%" class="tab_con3 padR22" title='<bean:message key="mcx.release.number"/>'></td>
		</tr>
	</table>
