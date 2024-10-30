package com.dtcc.dnv.mcx.beans;

import java.util.ArrayList;

import com.dtcc.dnv.otc.common.layers.ITransactionBean;

/**
 * This bean used to store the Enrollment deatils for the Enrollment Screen
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
 * @author Prabhu K
 * @date Sep 4, 2007
 * @version 1.0
 * 
 *  
 */
public class Enrollment implements ITransactionBean
{
	private ArrayList selectedDealersList;
	private String[] selectedDealers;
	private String[] mcaIds;
	private String[] mcaNames;
	

    /**
     * @return Returns the mcaIds.
     */
    public String[] getMcaIds()
    {
        return mcaIds;
    }
    /**
     * @param mcaIds The mcaIds to set.
     */
    public void setMcaIds(String[] mcaIds)
    {
        this.mcaIds = mcaIds;
    }
    /**
     * @return Returns the mcaNames.
     */
    public String[] getMcaNames()
    {
        return mcaNames;
    }
    /**
     * @param mcaNames The mcaNames to set.
     */
    public void setMcaNames(String[] mcaNames)
    {
        this.mcaNames = mcaNames;
    }
    /**
     * @return Returns the selectedDealers.
     */
    public String[] getSelectedDealers()
    {
        return selectedDealers;
    }
    /**
     * @param selectedDealers The selectedDealers to set.
     */
    public void setSelectedDealers(String[] selectedDealers)
    {
        this.selectedDealers = selectedDealers;
    }
    /**
     * @return Returns the selectedDealersList.
     */
    public ArrayList getSelectedDealersList()
    {
        return selectedDealersList;
    }
    /**
     * @param selectedDealersList The selectedDealersList to set.
     */
    public void setSelectedDealersList(ArrayList selectedDealersList)
    {
        this.selectedDealersList = selectedDealersList;
    }
    /* (non-Javadoc)
     * @see com.dtcc.dnv.otc.common.layers.ITransactionBean#formatCurrencies()
     */
    public void formatCurrencies()
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see com.dtcc.dnv.otc.common.layers.ITransactionBean#getClone()
     */
    public ITransactionBean getClone() throws CloneNotSupportedException
    {
        // TODO Auto-generated method stub
        return null;
    }
}
