package com.dtcc.dnv.mcx.delegate.user;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceRequest;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

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
public class UserGUIDSignonRequest extends MCXAbstractServiceRequest {
	
	private String userCompanyId = "";
	private String userName = "";
	private String userId = "";
	private String userGUID = "";
	private String phoneNumber = "";
	private String userCompanyTypeInd = "";
	
	/**
	 * @param auditInfo
	 */
	public UserGUIDSignonRequest(AuditInfo auditInfo) {
		super(auditInfo);		
	}	
	
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
	 * @return Returns the userGUID.
	 */
	public String getUserGUID() {
		return userGUID;
	}
	
	/**
	 * @param userGUID The userGUID to set.
	 */
	public void setUserGUID(String userGUID) {
		this.userGUID = userGUID;
	}
	
	/**
	 * @return Returns the userId.
	 */
	public String getUserId() {		
		return userId;
	}
	
	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * @return Returns the phoneNumber.
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber The phoneNumber to set.
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * @return Returns the userCompanyTypeInd.
	 */
	public String getUserCompanyTypeInd() {
		return userCompanyTypeInd;
	}
	/**
	 * @param userCompanyTypeInd The userCompanyTypeInd to set.
	 */
	public void setUserCompanyTypeInd(String userCompanyTypeInd) {
		this.userCompanyTypeInd = userCompanyTypeInd;
	}
}
