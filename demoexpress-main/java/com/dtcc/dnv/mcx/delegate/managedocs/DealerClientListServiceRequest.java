package com.dtcc.dnv.mcx.delegate.managedocs;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceRequest;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/**
 * The Service Request class for getting dealer details. Copyright © 2003 The
 * Depository Trust & Clearing Company. All rights reserved.
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
public class DealerClientListServiceRequest extends MCXAbstractServiceRequest
{
    /*
     * 
     * @param auditInfo
     */
    public DealerClientListServiceRequest(AuditInfo auditInfo)
    {
        super(auditInfo);
    }

    /*
     * The dealerid of the user.
     */
    private String cmpnyId = null;

    

   
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
}
