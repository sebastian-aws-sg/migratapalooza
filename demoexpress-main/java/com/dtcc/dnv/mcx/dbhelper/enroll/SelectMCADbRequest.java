/*
 * Created on Aug 27, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.dbhelper.enroll;

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
 * @author Prabhu K
 * @date Sep 4, 2007
 * @version 1.0
 * 
 * 
 */
public class SelectMCADbRequest extends MCXAbstractDbRequest
{

    /**
     * @param requestId
     * @param auditInfo
     */
    public SelectMCADbRequest(String requestId, AuditInfo auditInfo)
    {
        super(requestId, auditInfo);
    }

    private String CompanyID = null;
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
		return CompanyID;
	}
	/**
	 * @param companyID The companyID to set.
	 */
	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}
}
