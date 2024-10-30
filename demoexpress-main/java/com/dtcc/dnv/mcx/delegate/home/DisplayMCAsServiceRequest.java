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
 * @author Nithya R
 * @date Sep 10, 2007
 * @version 1.0
 * 
 * Service request class for getting the counterparty's pending mca details.
 *  
 */
public class DisplayMCAsServiceRequest extends MCXAbstractServiceRequest
{

    public DisplayMCAsServiceRequest(AuditInfo auditInfo)
    {
        super(auditInfo);
    }

    private String userID;
    private String companyID;
    private String pendingExecutedIndicator;

    /**
     * @return Returns the companyID.
     */
    public String getCompanyID()
    {
        return companyID;
    }

    /**
     * @param companyID
     *            The companyID to set.
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
     * @param pendingExecutedIndicator
     *            The pendingExecutedIndicator to set.
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
     * @param userID
     *            The userID to set.
     */
    public void setUserID(String userID)
    {
        this.userID = userID;
    }
}
