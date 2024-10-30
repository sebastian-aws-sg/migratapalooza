package com.dtcc.dnv.mcx.dbhelper.admin;

import com.dtcc.dnv.mcx.dbhelper.MCXAbstractDbRequest;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/**
 * This class is used as a DBRequest helper to retrive MCA Type list
 * RTM Reference : 1.21.2
 * 
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights
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
 * @author Seeni Mohammad Azharudin S
 * @date Sep 17, 2007
 * @version 1.0
 * 
 *  
 */

public class MCATypeListDbRequest extends MCXAbstractDbRequest{
	
	
	/**
	 * @param requestId
	 * @param auditInfo
	 */
	public MCATypeListDbRequest(String requestId, AuditInfo auditInfo) 
	{
		super(requestId, auditInfo);
	}

	private String companyID = null;

	/**
	 * @return Returns the companyID.
	 */
	public String getCompanyID() {
		return companyID;
	}

	/**
	 * @param companyID
	 *            The companyID to set.
	 */
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}


}
