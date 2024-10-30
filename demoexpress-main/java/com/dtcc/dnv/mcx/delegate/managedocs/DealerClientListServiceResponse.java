package com.dtcc.dnv.mcx.delegate.managedocs;

import java.util.ArrayList;
import java.util.List;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceResponse;

/**
 * The Service Response class for getting dealer details.
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
public class DealerClientListServiceResponse extends MCXAbstractServiceResponse
{

    private List dealerClientList = new ArrayList();

    /*
     * return list of dealers
     */
    public List getDealerClientList()
    {
        return dealerClientList;
    }

    /*
     * param list
     */
    public void setDealerClientList(List list)
    {
        dealerClientList = list;
    }

}
