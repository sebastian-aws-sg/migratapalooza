<%@ taglib uri="/WEB-INF/derivServ.tld" prefix="derivserv" %>
<%@ taglib uri="/WEB-INF/taglibs-datetime.tld" prefix="dt" %>
<script language="JavaScript" src="/mcx/static/js/dealerinfo_popup.js"></script>

<table  cellpadding="0" width="100%"  cellspacing=" 0"  >
	<tr>
		<td rowspan="2"  class="header_bg" width="460px" height="66px" id=" head" valign="top">
		</td>
		<td valign="bottom" align="right" class="header sign"  id=" head" >
			<table  >
				<tr>
					<td  align="right" class="sign"  id=" head" nowrap>
						Signed in as:&nbsp;
					</td>
					<td align="left" class="con8"  id=" head" style="word-break: break-all" >
						<derivserv:userName></derivserv:userName> 
					</td>
					<td align="left" class="con8"  id=" head" >
						&nbsp;
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="right" >
			<table >
				<tr>
					<td  align="right" class=" timestamp"  id=" head" >
						      <dt:format pattern="MM/dd/yyyy HH:mm:ss zzz"> 
					               <dt:currentTime/> 
						      </dt:format>
								&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td  class="signout"  id=" head" >
						<a href="/mcx/action/logOut">Sign out</a>
					</td>
					<td class="timestamp" >
						&nbsp;
					</td>
				</tr>
			</table>
		</td>
	
		
	</tr>
</table>