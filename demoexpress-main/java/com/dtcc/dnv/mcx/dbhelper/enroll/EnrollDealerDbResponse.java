package com.dtcc.dnv.mcx.dbhelper.enroll;

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
 * @author Prabhu K
 * @date Sep 4, 2007
 * @version 1.0
 * 
 *  
 */
public class EnrollDealerDbResponse extends MCXAbstractDbResponse
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
