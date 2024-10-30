
package com.dtcc.dnv.mcx.delegate.alert;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceRequest;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
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
 * @author pzhou
 * @date Oct 8, 2007
 * @version 1.0
 *
 * This class is used to provide service request to delegate for
 * posting alert.
 *
 *
 */
public class AlertServiceRequest  extends MCXAbstractServiceRequest{
	
	
	   /**
     * @param auditInfo
     */
    public AlertServiceRequest(AuditInfo auditInfo)
    {
        super(auditInfo);
    }
    
    private MCXCUOUser theuser;
    /**
     * @param u set user
     */
    public void setUser(MCXCUOUser u)
    {
    	theuser = u;
    }
    /**
     * @return the user object
     */
    public MCXCUOUser getUser()
    {
    	return theuser;
    }

}
