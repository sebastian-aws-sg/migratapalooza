/*
 * Created on Oct 6, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.beans;

import com.dtcc.dnv.otc.common.layers.ITransactionBean;

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
 * @author Vigneshwari R
 * @date Oct 6, 2007
 * @version 1.0
 * 
 *  
 */

public class PendingEnrollmentsTransaction implements ITransactionBean
{

    private String[] selectedMCAs = null;

    private String dealerCode = "";

    private String userNm = "";

    private String upLoadTime = "";

    private String pendingEnrollmentSuccess = "";

    private String submitParam = "";

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.ITransactionBean#formatCurrencies()
     */
    public void formatCurrencies()
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.ITransactionBean#getClone()
     */
    public ITransactionBean getClone() throws CloneNotSupportedException
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return Returns the dealerCode.
     */
    public String getDealerCode()
    {
        return dealerCode;
    }

    /**
     * @param dealerCode
     *            The dealerCode to set.
     */
    public void setDealerCode(String dealerCode)
    {
        this.dealerCode = dealerCode;
    }

    /**
     * @return Returns the pendingEnrollmentSuccess.
     */
    public String getPendingEnrollmentSuccess()
    {
        return pendingEnrollmentSuccess;
    }

    /**
     * @param pendingEnrollmentSuccess
     *            The pendingEnrollmentSuccess to set.
     */
    public void setPendingEnrollmentSuccess(String pendingEnrollmentSuccess)
    {
        this.pendingEnrollmentSuccess = pendingEnrollmentSuccess;
    }

    /**
     * @return Returns the submitParam.
     */
    public String getSubmitParam()
    {
        return submitParam;
    }

    /**
     * @param submitParam
     *            The submitParam to set.
     */
    public void setSubmitParam(String submitParam)
    {
        this.submitParam = submitParam;
    }

    /**
     * @return Returns the upLoadTime.
     */
    public String getUpLoadTime()
    {
        return upLoadTime;
    }

    /**
     * @param upLoadTime
     *            The upLoadTime to set.
     */
    public void setUpLoadTime(String upLoadTime)
    {
        this.upLoadTime = upLoadTime;
    }

    

    /**
     * @return Returns the selectedMCAs.
     */
    public String[] getSelectedMCAs()
    {
        return selectedMCAs;
    }

    /**
     * @param selectedMCAs
     *            The selectedMCAs to set.
     */
    public void setSelectedMCAs(String[] selectedMCAs)
    {
        this.selectedMCAs = selectedMCAs;
    }
    /**
     * @return Returns the userNm.
     */
    public String getUserNm()
    {
        return userNm;
    }
    /**
     * @param userNm The userNm to set.
     */
    public void setUserNm(String userNm)
    {
        this.userNm = userNm;
    }
}
