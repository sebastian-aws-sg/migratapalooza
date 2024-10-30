package com.dtcc.dnv.mcx.delegate.managedocs;

import java.util.ArrayList;
import java.util.List;

import com.dtcc.dnv.mcx.beans.UploadBean;
import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceRequest;
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
 * @author Ravikanth G
 * @date Sep 14, 2007
 * @version 1.0
 * 
 * 
 */

public class ManageDocsServiceRequest extends MCXAbstractServiceRequest
{

    /**
     * @param auditInfo
     */
    public ManageDocsServiceRequest(AuditInfo auditInfo)
    {
        super(auditInfo);
        
    }
    
    private String cmpnyId = "";
    private String docId = "";
    private String manageDocs = "";
    private String DocumentType = "";
    private String deleteCmpnyId = "";
    private String newCmpnyId = "";
       
    private String userId = ""; 
    private String documentInd = "";
    
    private UploadBean uploadBean = new UploadBean();
    private List dealerClient = new ArrayList();
    
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
     * @return Returns the documentType.
     */
    public String getDocumentType()
    {
        return DocumentType;
    }
    /**
     * @param documentType The documentType to set.
     */
    public void setDocumentType(String documentType)
    {
        DocumentType = documentType;
    }
    
    /**
     * @return Returns the deleteCmpnyId.
     */
    public String getDeleteCmpnyId()
    {
        return deleteCmpnyId;
    }
    /**
     * @param deleteCmpnyId The deleteCmpnyId to set.
     */
    public void setDeleteCmpnyId(String deleteCmpnyId)
    {
        this.deleteCmpnyId = deleteCmpnyId;
    }
    
    /**
     * @return Returns the newCmpnyId.
     */
    public String getNewCmpnyId()
    {
        return newCmpnyId;
    }
    /**
     * @param newCmpnyId The newCmpnyId to set.
     */
    public void setNewCmpnyId(String newCmpnyId)
    {
        this.newCmpnyId = newCmpnyId;
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
     * @return Returns the docId.
     */
    public String getDocId()
    {
        return docId;
    }
    /**
     * @param docId The docId to set.
     */
    public void setDocId(String docId)
    {
        this.docId = docId;
    }
    
    /**
     * @return Returns the documentInd.
     */
    public String getDocumentInd()
    {
        return documentInd;
    }
    /**
     * @param documentInd The documentInd to set.
     */
    public void setDocumentInd(String documentInd)
    {
        this.documentInd = documentInd;
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