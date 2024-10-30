/*
 * Created on Sep 10, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.dbhelper.managedocs;

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
 * @author Ravikanth G
 * @date Sep 10, 2007
 * @version 1.0
 * 
 *  
 */

public class ManageCounterPartyDbRequest extends MCXAbstractDbRequest
{

    /**
     * @param requestId
     * @param auditInfo
     */
    public ManageCounterPartyDbRequest(String requestId, AuditInfo auditInfo)
    {
        super(requestId, auditInfo);
    }
    
    
    private String loginDealerClientId = "";
    private String updatedUserId = "";
    
    
    
	/**
	 * @return Returns the loginDealerClientId.
	 */
	public String getLoginDealerClientId() {
		return loginDealerClientId;
	}
	/**
	 * @param loginDealerClientId The loginDealerClientId to set.
	 */
	public void setLoginDealerClientId(String loginDealerClientId) {
		this.loginDealerClientId = loginDealerClientId;
	}
    
    
	
    /**
     * @return Returns the updatedUserId.
     */
    public String getUpdatedUserId()
    {
        return updatedUserId;
    }
    /**
     * @param updatedUserId The updatedUserId to set.
     */
    public void setUpdatedUserId(String updatedUserId)
    {
        this.updatedUserId = updatedUserId;
    }
}
