/*
 * Created on Sep 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.delegate.home;


import java.util.HashMap;

import java.util.Map;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceRequest;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/**
 * Service request class for getting the status while updating the Approval or denial status when the dealer Approves or Denies a MCA.
 * RTM Reference : 3.3.11.8 A Dealer should be able to approve the Client and approve all MCAs requested by the Client for setup 
 * RTM Reference : 3.3.11.9 A Dealer should be able to approve the Counterparty and approve one or some MCAs requested by the Client for setup 
 * RTM Reference : 3.3.11.10 A Dealer should be able to deny the Client in which case all MCAs requested by that Client should be automatically denied for setup
 * RTM Reference : 3.3.11.11 A Dealer should not be able to approve the Client and simultaneously deny all MCAs requested by the Client
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
 * @author Vigneshwari R
 * @date Sep 11, 2007
 * @version 1.0
 * 
 * 
 */

public class EnrollmentApprovalServiceRequest extends MCXAbstractServiceRequest
{

    /**
     * @param auditInfo
     */
    public EnrollmentApprovalServiceRequest(AuditInfo auditInfo)
    {
        super(auditInfo);
        // TODO Auto-generated constructor stub
    }

    private String dealerCode = "";
    private String userNm = "";
    private String templateId = "";
    private String actionParam = "";
    private String uploadTime = "";
    private String companyId = "";
    private String userID = "";

    /**
     * @return Returns the selectedMca.
     */
    public String[] getSelectedMca()
    {
        return selectedMca;
    }

    /**
     * @param selectedMca
     *            The selectedMca to set.
     */
    public void setSelectedMca(String[] selectedMca)
    {
        this.selectedMca = selectedMca;
    }

    private String[] selectedMca;

    /**
     * @return Returns the mcaListMap.
     */
    public Map getMcaListMap()
    {
        return mcaListMap;
    }

    /**
     * @param mcaListMap
     *            The mcaListMap to set.
     */
    public void setMcaListMap(Map mcaListMap)
    {
        this.mcaListMap = mcaListMap;
    }

    private Map mcaListMap = new HashMap();

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
     * @return Returns the templateId.
     */
    public String getTemplateId()
    {
        return templateId;
    }

    /**
     * @param templateId
     *            The templateId to set.
     */
    public void setTemplateId(String templateId)
    {
        this.templateId = templateId;
    }

    

    /**
     * @return Returns the actionParam.
     */
    public String getActionParam()
    {
        return actionParam;
    }

    /**
     * @param actionParam
     *            The actionParam to set.
     */
    public void setActionParam(String actionParam)
    {
        this.actionParam = actionParam;
    }

    /**
     * @return Returns the uploadTime.
     */
    public String getUploadTime()
    {
        return uploadTime;
    }

    /**
     * @param uploadTime
     *            The uploadTime to set.
     */
    public void setUploadTime(String uploadTime)
    {
        this.uploadTime = uploadTime;
    }

    /**
     * @return Returns the companyId.
     */
    public String getCompanyId()
    {
        return companyId;
    }

    /**
     * @param companyId
     *            The companyId to set.
     */
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
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
