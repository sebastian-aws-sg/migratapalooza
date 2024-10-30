/*
 * Created on Sep 3, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dtcc.dnv.mcx.dbhelper.home;

import java.util.List;

import com.dtcc.dnv.mcx.dbhelper.MCXAbstractDbResponse;

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
 * DbResponse class for fetching the pending mca details.
 *  
 */
public class DisplayMCAsDbResponse extends MCXAbstractDbResponse
{

    private List dealerDetailsList;
    private String pendingExecutedStatus;

    /**
     * @return Returns the dealerDetailsList.
     */
    public List getDealerDetailsList()
    {
        return dealerDetailsList;
    }

    /**
     * @param dealerDetailsList
     *            The dealerDetailsList to set.
     */
    public void setDealerDetailsList(List dealerDetailsList)
    {
        this.dealerDetailsList = dealerDetailsList;
    }

    /**
     * @return Returns the pendingExecutedStatus.
     */
    public String getPendingExecutedStatus()
    {
        return pendingExecutedStatus;
    }

    /**
     * @param pendingExecutedStatus
     *            The pendingExecutedStatus to set.
     */
    public void setPendingExecutedStatus(String pendingExecutedStatus)
    {
        this.pendingExecutedStatus = pendingExecutedStatus;
    }
}
