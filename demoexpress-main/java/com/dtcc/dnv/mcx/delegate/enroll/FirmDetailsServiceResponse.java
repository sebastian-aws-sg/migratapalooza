/*
 * Created on Aug 29, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dtcc.dnv.mcx.delegate.enroll;

import java.util.List;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceResponse;

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
 * @date Sep 25, 2007
 * @version 1.0
 * 
 * Service response class for retrieving the firm details.
 * 
 */
public class FirmDetailsServiceResponse extends MCXAbstractServiceResponse
{
    private List firmDetails;
    private String dealerName;

    /**
     * @return Returns the dealerName.
     */
    public String getDealerName()
    {
        return dealerName;
    }

    /**
     * @param dealerName The dealerName to set.
     */
    public void setDealerName(String dealerName)
    {
        this.dealerName = dealerName;
    }

    /**
     * @return Returns the firmDetails.
     */
    public List getFirmDetails()
    {
        return firmDetails;
    }

    /**
     * @param firmDetails The firmDetails to set.
     */
    public void setFirmDetails(List firmDetails)
    {
        this.firmDetails = firmDetails;
    }
}
