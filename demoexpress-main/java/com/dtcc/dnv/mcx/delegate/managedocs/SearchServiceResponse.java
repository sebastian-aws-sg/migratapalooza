package com.dtcc.dnv.mcx.delegate.managedocs;

import java.util.ArrayList;
import java.util.List;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceResponse;

/**
 * The service Response for Search functionality.
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
 * @author Ravikanth G
 * @date Sep 6, 2007
 * @version 1.0
 * 
 *  
 */
public class SearchServiceResponse extends MCXAbstractServiceResponse
{

    private List dealerClientDetails = new ArrayList();
    private List dealerClient = new ArrayList();

    /**
     * @return
     */
    public List getDealerClientDetails()
    {
        return dealerClientDetails;
    }

    /**
     * @param list
     */
    public void setDealerClientDetails(List list)
    {
        dealerClientDetails = list;
    }
    
    

    /**
     * @return Returns the dealerClient.
     */
    public List getDealerClient()
    {
        return dealerClient;
    }
    /**
     * @param dealerClient The dealerClient to set.
     */
    public void setDealerClient(List dealerClient)
    {
        this.dealerClient = dealerClient;
    }
}
