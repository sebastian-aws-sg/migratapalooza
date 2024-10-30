
package com.dtcc.dnv.mcx.delegate.enroll;

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
 * @author Prabhu K
 * @date Sep 4, 2007
 * @version 1.0
 * 
 * 
 */
public class SelectMCAServiceRequest extends MCXAbstractServiceRequest
{
    /**
     * @param auditInfo
     */
    public SelectMCAServiceRequest(AuditInfo auditInfo)
    {
        super(auditInfo);
    }

    private String companyID = null;
    private String dealerCode = null;



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
	 * @return Returns the companyID.
	 */
	public String getCompanyID() {
		return companyID;
	}
	/**
	 * @param companyID The companyID to set.
	 */
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
}
