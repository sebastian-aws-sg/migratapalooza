
package com.dtcc.dnv.mcx.delegate.enroll;

import com.dtcc.dnv.mcx.beans.Enrollment;
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
public class EnrollDealerServiceRequest extends MCXAbstractServiceRequest
{
	/**
	 * @param auditInfo
	 */
	public EnrollDealerServiceRequest(AuditInfo auditInfo) {
		super(auditInfo);
	}
	private String companyID;
	private String userID;
	private Enrollment enrollmentBean;
	
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
    /**
     * @return Returns the userID.
     */
    public String getUserID()
    {
        return userID;
    }
    /**
     * @param userID The userID to set.
     */
    public void setUserID(String userID)
    {
        this.userID = userID;
    }
    /**
     * @return Returns the enrollmentBean.
     */
    public Enrollment getEnrollmentBean()
    {
        return enrollmentBean;
    }
    /**
     * @param enrollmentBean The enrollmentBean to set.
     */
    public void setEnrollmentBean(Enrollment enrollmentBean)
    {
        this.enrollmentBean = enrollmentBean;
    }
}
