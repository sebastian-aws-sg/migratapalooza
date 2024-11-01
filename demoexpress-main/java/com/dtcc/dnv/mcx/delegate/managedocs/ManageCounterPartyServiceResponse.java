/*
 * Created on Sep 10, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.delegate.managedocs;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceResponse;

/**
 * Copyright � 2003 The Depository Trust & Clearing Company. All rights
 * reserved.
 * 
 * Depository Trust & Clearing Corporation (DTCC) 55, Water Street, New York, NY
 * 10048, U.S.A All Rights Reserved.
 * 
 * This software may contain (in part or full) confidential/proprietary
 * information of DTCC. ("Confidential Information"). Disclosure of such
 * Confidential Information is prohibited and should be used only for its
 * intended purpose in accordance with rules and regulations of DTCC.
 * 
 * @author Ravikanth G
 * @date Sep 10, 2007
 * @version 1.0
 * 
 * 
 */

public class ManageCounterPartyServiceResponse extends MCXAbstractServiceResponse
{
    private String cmpny_Id = "";
    
       
	/**
	 * @return Returns the cmpny_Id.
	 */
	public String getCmpny_Id() {
		return cmpny_Id;
	}
	/**
	 * @param cmpny_Id The cmpny_Id to set.
	 */
	public void setCmpny_Id(String cmpny_Id) {
		this.cmpny_Id = cmpny_Id;
	}
}
