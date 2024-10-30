	<div id="home_sub" style="display:none">
			<table width="100%" border="0" cellspacing="0"
				cellpadding="0" class="tab2_bg ">
				<tr>
					<td>
						<table width="819" cellpadding="0" cellspacing="0">
							<tr>
								<td width="250" height="14" class="tab2_con" id="pending"
									onclick="menu_click('/mcx/action/dealerPendingApproval?selectedTab=P&printParam=N')">Pending
									Enrollment Approval</td>
								<td width="10"><img src="/mcx/static/images/sub_tab_divider.gif" /></td>
								<td width="174" height="14" class="tab2a_con" id="approved"
									onclick="menu_click('/mcx/action/dealerPendingApproval?selectedTab=A&printParam=N')">Approved
									Firms</td>
								<td width="8"><img src="/mcx/static/images/sub_tab_divider.gif" /></td>
								<td width="168" height="14" class="tab2a_con" id="pendingMca"
									onclick="menu_click('/mcx/action/getDealerDetails?mcaStatus=P&printParam=N')">Pending
									MCAs</td>
								<td width="9"><img src="/mcx/static/images/sub_tab_divider.gif" /></td>
								<td width="231" class="tab2a_con" id="executedMca"
									onclick="menu_click('/mcx/action/getDealerDetails?mcaStatus=E&printParam=N')">Executed
									MCAs</td>

							</tr>
						</table>
					</td>
				</tr>
			</table>
	</div>
	<div id="magdoc_sub" style="display:none">
			<table width="100%" border="0" cellspacing="0"
				cellpadding="0" class="tab2_bg ">
				<tr>
					<td>
						<table width="819" cellpadding="0" cellspacing="0">
							<tr>
								<td width="250" height="14" class="tab2_con" id="preexistmca"
									onclick="menu_click('/mcx/action/manageDoc?manageDocTab=P')">Pre-existing MCAs
									</td>
								<td width="10"><img src="/mcx/static/images/sub_tab_divider.gif" /></td>
								<td width="174" height="14" class="tab2a_con" id="otherdoc"
									onclick="menu_click('/mcx/action/manageDoc?manageDocTab=O')">Other Documents
									</td>
								<td width="8"><img src="/mcx/static/images/sub_tab_divider.gif" /></td>
								<td width="168" height="14" class="tab2a_con" id="searchdoc"
									onclick="menu_click('/mcx/action/searchDealerNames')">Search Documents
									</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
	</div>		
