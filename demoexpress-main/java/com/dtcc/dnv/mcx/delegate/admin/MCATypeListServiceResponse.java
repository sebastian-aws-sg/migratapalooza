package com.dtcc.dnv.mcx.delegate.admin;

import java.util.ArrayList;
import java.util.List;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceResponse;


/**
 * This class is used as a delegate to retrive MCA Type List for the MCA Setup & Posting
 * screen(Admin HomePage)
 *  
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

public class MCATypeListServiceResponse extends MCXAbstractServiceResponse
{
	private List mcaTypeList = new ArrayList();
	
	private String pendingExecutedStatus;
	
    /**
     * @return Returns the mcaTypeList.
     */
    public List getMcaTypeList()
    {
        return mcaTypeList;
    }
    /**
     * @param mcaTypeList The mcaTypeList to set.
     */
    public void setMcaTypeList(List mcaTypeList)
    {
        this.mcaTypeList = mcaTypeList;
    }
    /**
     * @return Returns the pendingExecutedStatus.
     */
    public String getPendingExecutedStatus()
    {
        return pendingExecutedStatus;
    }
    /**
     * @param pendingExecutedStatus The pendingExecutedStatus to set.
     */
    public void setPendingExecutedStatus(String pendingExecutedStatus)
    {
        this.pendingExecutedStatus = pendingExecutedStatus;
    }
}
