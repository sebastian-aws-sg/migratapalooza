/*
 * Created on Oct 23, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.delegate.home;

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
 * @author Nithya R
 * @date Oct 23, 2007
 * @version 1.0
 * 
 * ServiceRequest class for fetching the user details.
 * 
 */

public class UserDetailsServiceRequest extends MCXAbstractServiceRequest
{

    /**
     * @param auditInfo
     */
    public UserDetailsServiceRequest(AuditInfo auditInfo)
    {
        super(auditInfo);

    }

    private String userId;

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
}
