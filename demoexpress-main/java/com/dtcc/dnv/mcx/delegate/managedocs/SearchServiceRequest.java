package com.dtcc.dnv.mcx.delegate.managedocs;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceRequest;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/**
 * The service Request for Search functionality.
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
public class SearchServiceRequest extends MCXAbstractServiceRequest
{

    /**
     * @param auditInfo
     */
    public SearchServiceRequest(AuditInfo auditInfo)
    {
        super(auditInfo);
    }

   
    private String companyId;

    
    
    

    /**
     * @return Returns the companyId.
     */
    public String getCompanyId()
    {
        return companyId;
    }
    /**
     * @param companyId The companyId to set.
     */
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }
    
}
