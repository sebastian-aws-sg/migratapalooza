package com.dtcc.dnv.mcx.dbhelper.managedocs;

import com.dtcc.dnv.mcx.beans.UploadBean;
import com.dtcc.dnv.mcx.dbhelper.MCXAbstractDbRequest;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/**
 * The dbRequest to get dealers list.
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
public class DealerClientListDbRequest extends MCXAbstractDbRequest
{

    /**
     * @param requestId
     * @param auditInfo
     */
    public DealerClientListDbRequest(String requestId, AuditInfo auditInfo)
    {
        super(requestId, auditInfo);
    }

    private String cmpnyId = null;    
    private String manageDocs = "";    
    private String userId = "";     
    private UploadBean uploadBean = new UploadBean();
    
    /**
     * @return Returns the cmpnyId.
     */
    public String getCmpnyId()
    {
        return cmpnyId;
    }
    /**
     * @param cmpnyId The cmpnyId to set.
     */
    public void setCmpnyId(String cmpnyId)
    {
        this.cmpnyId = cmpnyId;
    }
    /**
     * @return Returns the manageDocs.
     */
    public String getManageDocs()
    {
        return manageDocs;
    }
    /**
     * @param manageDocs The manageDocs to set.
     */
    public void setManageDocs(String manageDocs)
    {
        this.manageDocs = manageDocs;
    }
    /**
     * @return Returns the userId.
     */
    public String getUserId()
    {
        return userId;
    }
    /**
     * @param userId The userId to set.
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
   
    /**
     * @return Returns the uploadBean.
     */
    public UploadBean getUploadBean()
    {
        return uploadBean;
    }
    /**
     * @param uploadBean The uploadBean to set.
     */
    public void setUploadBean(UploadBean uploadBean)
    {
        this.uploadBean = uploadBean;
    }
}