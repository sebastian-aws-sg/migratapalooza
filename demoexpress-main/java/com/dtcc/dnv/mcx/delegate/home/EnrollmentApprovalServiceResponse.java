/*
 * Created on Sep 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.delegate.home;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceResponse;


/**
 * serviceresponse class for sending the data needed to send the status back to the action class 
 * RTM Reference : 3.3.11.8 A Dealer should be able to approve the Client and approve all MCAs requested by the Client for setup 
 * RTM Reference : 3.3.11.9 A Dealer should be able to approve the Counterparty and approve one or some MCAs requested by the Client for setup 
 * RTM Reference : 3.3.11.10 A Dealer should be able to deny the Client in which case all MCAs requested by that Client should be automatically denied for setup
 * RTM Reference : 3.3.11.11 A Dealer should not be able to approve the Client and simultaneously deny all MCAs requested by the Client
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
 * @date Sep 11, 2007
 * @version 1.0
 * 
 * 
 */

public class EnrollmentApprovalServiceResponse extends MCXAbstractServiceResponse
{
    private boolean saveStatus = false;

    /**
     * @return Returns the saveStatus.
     */
    public boolean getSaveStatus()
    {
        return saveStatus;
    }

    /**
     * @param saveStatus
     *            The saveStatus to set.
     */
    public void setSaveStatus(boolean saveStatus)
    {
        this.saveStatus = saveStatus;
    }
}
