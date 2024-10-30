/*
 * Created on Sep 3, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dtcc.dnv.mcx.delegate.home;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceRequest;

import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/**
 * Service request class for getting the data to load the Pending Enrollments Approval 
 * and Approved Firms screen
 * RTM Reference : 3.3.11.1 The actor views the pending Enrolment approval tab for the respective MCA types against the clients awaiting approval, approves a client and all the MCAs requested for by him
 * RTM Reference : 3.3.11.2 Actor selects to approve only one or more but not all of the MCA Enrolment requests for the client  
 * RTM Reference : 3.3.11.7 A Dealer should only be able to approve or deny Enrolment of a Client that has initiated firm Enrolment
 * RTM Reference : 3.3.11.12 Once a Client firm is approved with at least one associated MCA(s), that Client should never be removed from the Dealer’s ‘Approved Firms’ tab
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
 * @author Vigneshwari R
 * @date Sep 7, 2007
 * @version 1.0
 * 
 *  
 */
public class PendingEnrollApprovalServiceRequest extends MCXAbstractServiceRequest
{

    /**
     * @param auditInfo
     */
    public PendingEnrollApprovalServiceRequest(AuditInfo auditInfo)
    {
        super(auditInfo);
    }

    private String dealerCode = null;

    private String selectedTab = null;
    
    private String userCode  = "";

    /**
     * @return dealerCode
     */
    public String getDealerCode()
    {
        return dealerCode;
    }

    /**
     * @return selectedTab
     */
    public String getSelectedTab()
    {
        return selectedTab;
    }

    /**
     * @param string
     */
    public void setDealerCode(String string)
    {
        dealerCode = string;
    }

    /**
     * @param string
     */
    public void setSelectedTab(String string)
    {
        selectedTab = string;
    }

    /**
     * @return Returns the userCode.
     */
    public String getUserCode()
    {
        return userCode;
    }
    /**
     * @param userCode The userCode to set.
     */
    public void setUserCode(String userCode)
    {
        this.userCode = userCode;
    }
}
