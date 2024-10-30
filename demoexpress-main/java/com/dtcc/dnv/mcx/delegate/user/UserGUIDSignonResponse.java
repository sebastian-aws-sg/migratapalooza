package com.dtcc.dnv.mcx.delegate.user;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceResponse;
/*
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55, Water Street,
 * New York, NY 10048
 * U.S.A
 * All Rights Reserved.
 *
 * This software may contain (in part or full) confidential/proprietary information of DTCC.
 * ("Confidential Information"). Disclosure of such Confidential
 * Information is prohibited and should be used only for its intended purpose
 * in accordance with rules and regulations of DTCC.
 *
 * @author Kevin Lake
 * @version 1.0
 * Date: September 05, 2007
 */
public class UserGUIDSignonResponse extends MCXAbstractServiceResponse {
	
	private String mcxGuid = "";	
	
	/**
	 * @return Returns the mcxGuid.
	 */
	public String getMcxGuid() {
		return mcxGuid;
	}
	/**
	 * @param mcxGuid The mcxGuid to set.
	 */
	public void setMcxGuid(String mcxGuid) {
		this.mcxGuid = mcxGuid;
	}
}
