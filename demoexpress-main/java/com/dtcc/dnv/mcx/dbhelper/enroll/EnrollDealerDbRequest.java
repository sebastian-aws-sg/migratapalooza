package com.dtcc.dnv.mcx.dbhelper.enroll;

import com.dtcc.dnv.mcx.dbhelper.MCXAbstractDbRequest;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

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
public class EnrollDealerDbRequest extends MCXAbstractDbRequest
{

    /**
     * @param requestId
     * @param auditInfo
     */
    public EnrollDealerDbRequest(String requestId, AuditInfo auditInfo)
    {
        super(requestId, auditInfo);
    }

    private String companyID;
    private String dealerCode;
    private String mcaTemplateID;
    private String userID;
    private String enrollTimeStamp;
    private String enrollStatus;

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
     * @return Returns the enrollTimeStamp.
     */
    public String getEnrollTimeStamp()
    {
        return enrollTimeStamp;
    }

    /**
     * @param enrollTimeStamp
     *            The enrollTimeStamp to set.
     */
    public void setEnrollTimeStamp(String enrollTimeStamp)
    {
        this.enrollTimeStamp = enrollTimeStamp;
    }

    /**
     * @return Returns the mcaTemplateID.
     */
    public String getMcaTemplateID()
    {
        return mcaTemplateID;
    }

    /**
     * @param mcaTemplateID
     *            The mcaTemplateID to set.
     */
    public void setMcaTemplateID(String mcaTemplateID)
    {
        this.mcaTemplateID = mcaTemplateID;
    }

    /**
     * @return Returns the userID.
     */
    public String getUserID()
    {
        return userID;
    }

    /**
     * @param userID
     *            The userID to set.
     */
    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    /**
     * @return Returns the enrollStatus.
     */
    public String getEnrollStatus()
    {
        return enrollStatus;
    }

    /**
     * @param enrollStatus
     *            The enrollStatus to set.
     */
    public void setEnrollStatus(String enrollStatus)
    {
        this.enrollStatus = enrollStatus;
    }

    /**
     * @return Returns the companyID.
     */
    public String getCompanyID()
    {
        return companyID;
    }

    /**
     * @param companyID
     *            The companyID to set.
     */
    public void setCompanyID(String companyID)
    {
        this.companyID = companyID;
    }
}
