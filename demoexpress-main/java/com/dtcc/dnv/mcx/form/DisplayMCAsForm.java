package com.dtcc.dnv.mcx.form;

import java.util.List;

import com.dtcc.dnv.mcx.util.MCXConstants;

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
 * This class is used to hold the input values for the PendingMCA.jsp and ExceutedMCA.jsp 
 * page.
 */
public class DisplayMCAsForm extends MCXActionForm
{
    private List dealerDetailsList;
    private String mcaStatus = MCXConstants.MCA_STATUS_PENDING;
    private String printParam = "N";

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
     * @return Returns the mcaStatus.
     */
    public String getMcaStatus()
    {
        return mcaStatus;
    }

    /**
     * @param mcaStatus
     *            The mcaStatus to set.
     */
    public void setMcaStatus(String mcaStatus)
    {
        this.mcaStatus = mcaStatus;
    }
    
    /**
     * @return Returns the printParam.
     */
    public String getPrintParam()
    {
        return printParam;
    }
    /**
     * @param printParam The printParam to set.
     */
    public void setPrintParam(String printParam)
    {
        this.printParam = printParam;
    }
}
