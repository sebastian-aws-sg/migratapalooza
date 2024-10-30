package com.dtcc.dnv.mcx.user;

import com.dtcc.dnv.otc.common.layers.ITransactionBean;

/*
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55, Water Street,
 * New York, NY 10048
 * U.S.A
 * All Rights Reserved.
 *
 * This software may contain (in part or full) confidential/proprietary information of DTCC.
 * ("Confidential Information"). Disclosure of such Confidential
 * Information is prohibited and should be used only for its intended purpose
 * in accordance with rules and regulations of DTCC.
 * 
 * @author Kevin Lake
 * @version 1.0
 * Date: September 05, 2007
 */

public class UserCompanyBean implements ITransactionBean {
	
	/* Company Group Indicator (Dealer / Client) */
	private String userCompanyTypeInd = "";
	
	/* Company Internal / External */
	private String userCompanyEnvInd = "";
	
	/* Company ID */
	private String userCompanyId = "";
	
	/* Company Name */
	private String userCompanyName = "";	
	
	/**
	 * @return Returns the userCompanyId.
	 */
	public String getUserCompanyId() {
		return userCompanyId;
	}
	/**
	 * @param userCompanyId The userCompanyId to set.
	 */
	public void setUserCompanyId(String userCompanyId) {
		this.userCompanyId = userCompanyId;
	}
	/**
	 * @return Returns the userCompanyInd.
	 */
	public String getUserCompanyEnvInd() {
		return userCompanyEnvInd;
	}
	/**
	 * @param userCompanyInd The userCompanyInd to set.
	 */
	public void setUserCompanyEnvInd(String userCompanyInd) {
		this.userCompanyEnvInd = userCompanyInd;
	}
	/**
	 * @return Returns the userCompanyName.
	 */
	public String getUserCompanyName() {
		return userCompanyName;
	}
	/**
	 * @param userCompanyName The userCompanyName to set.
	 */
	public void setUserCompanyName(String userCompanyName) {
		this.userCompanyName = userCompanyName;
	}
	/**
	 * @return Returns the userCompanyType.
	 */
	public String getUserCompanyTypeInd() {
		return userCompanyTypeInd;
	}
	/**
	 * @param userCompanyType The userCompanyType to set.
	 */
	public void setUserCompanyTypeInd(String userCompanyType) {
		this.userCompanyTypeInd = userCompanyType;
	}
	/**
	 * @see com.dtcc.dnv.otc.common.layers.ITransactionBean#formatCurrencies()
	 */
	public void formatCurrencies() {
	}	

	/**
	 * @see com.dtcc.dnv.otc.common.layers.ITransactionBean#getClone()
	 */
	public ITransactionBean getClone() throws CloneNotSupportedException {
		return (ITransactionBean) super.clone();
	}	

}
