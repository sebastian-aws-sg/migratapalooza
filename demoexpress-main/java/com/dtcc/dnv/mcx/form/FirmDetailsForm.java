/*
 * Created on Aug 29, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dtcc.dnv.mcx.form;

import java.util.List;

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
 * This class is used to hold the input values for the firm details popup page.
 */
public class FirmDetailsForm extends MCXActionForm
{
    private List firmDetails;
    private String dealerName;
    private String dealer;

    /**
     * @return Returns the firmDetails.
     */
    public List getFirmDetails()
    {
        return firmDetails;
    }

    /**
     * @param list
     *            The firmdetails list to set.
     */
    public void setFirmDetails(List list)
    {
        firmDetails = list;
    }

    /**
     * @return Returns the dealerName.
     */
    public String getDealerName()
    {
        return dealerName;
    }

    /**
     * @param string
     *            The dealerName to set.
     */
    public void setDealerName(String string)
    {
        dealerName = string;
    }

    /**
     * @return Returns the dealer.
     */
    public String getDealer()
    {
        return dealer;
    }

    /**
     * @param dealer The dealer to set.
     */
    public void setDealer(String dealer)
    {
        this.dealer = dealer;
    }
}
