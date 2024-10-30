
package com.dtcc.dnv.mcx.delegate.managedocs;

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
 * @date Sep 10, 2007
 * @version 1.0
 * 
 * 
 */

public class ManageCounterPartyServiceRequest extends MCXAbstractServiceRequest
{
    public ManageCounterPartyServiceRequest(AuditInfo auditInfo){
        super(auditInfo);        
    }
    
    private String loginDealerClientId = null;
    private String updatedUserId = null;
    
    
    
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
