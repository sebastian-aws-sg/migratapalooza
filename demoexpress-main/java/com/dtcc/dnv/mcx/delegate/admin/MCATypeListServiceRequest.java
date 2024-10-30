package com.dtcc.dnv.mcx.delegate.admin;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceRequest;

import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/**
 * This class is used to retrive MCA Type List for the MCA Setup & Posting screen
 * RTM Reference : 1.22.2 Clicking on MCA Setup or Home, would display the 
 * list of MCA's in a summary list. The fields of this list view are
 * “MCA Type”, ”Status”, “Date Posted”, “Last modified by” , 
 * “last modified on” and "MCA Agreement Date".  
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

public class MCATypeListServiceRequest extends MCXAbstractServiceRequest
{
	/**
	 * @param auditInfo
	 */
	public MCATypeListServiceRequest(AuditInfo auditInfo) {
		super(auditInfo);
	}

	private String userID;
    private String companyID;
    private String pendingExecutedIndicator;
    /**
     * @return Returns the companyId.
     */
    public String getCompanyID()
    {
        return companyID;
    }
    /**
     * @param companyId The companyId to set.
     */
    public void setCompanyID(String companyID)
    {
        this.companyID = companyID;
    }
    /**
     * @return Returns the pendingExecutedIndicator.
     */
    public String getPendingExecutedIndicator()
    {
        return pendingExecutedIndicator;
    }
    /**
     * @param pendingExecutedIndicator The pendingExecutedIndicator to set.
     */
    public void setPendingExecutedIndicator(String pendingExecutedIndicator)
    {
        this.pendingExecutedIndicator = pendingExecutedIndicator;
    }
    /**
     * @return Returns the userID.
     */
    public String getUserID()
    {
        return userID;
    }
    /**
     * @param userID The userID to set.
     */
    public void setUserID(String userID)
    {
        this.userID = userID;
    }
}
