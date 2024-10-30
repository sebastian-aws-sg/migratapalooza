package com.dtcc.dnv.mcx.user;

import com.dtcc.dnv.otc.common.security.model.ExUser;

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
public class MCXCUOUser extends MCXBaseUser {
	
	private boolean isClient = false;
	private boolean isDealer = false;
	private boolean isTemplateAdmin = false;
	private boolean isExternalCompany = false;
	private String _companyId = "";
	private String _companyName = "";
	private String _MCXUserGUID = "";
	
	/**
	 * @param user
	 */
	MCXCUOUser(ExUser user){
		super(user);
	}

	/**
	 * @return Returns the isClient.
	 */
	public boolean isClient() {
		return isClient;
	}

	/**
	 * @return Returns the isDealer.
	 */
	public boolean isDealer() {
		return isDealer;
	}

	/**
	 * @param isClient The isClient to set.
	 */
	protected void setClient(boolean isClient) {
		this.isClient = isClient;
	}

	/**
	 * @param isDealer The isDealer to set.
	 */
	protected void setDealer(boolean isDealer) {
		this.isDealer = isDealer;
	}	
	
	/**
	 * @return Returns the isAdmin.
	 */
	public boolean isTemplateAdmin() {
		return isTemplateAdmin;
	}
	
	/**
	 * @param isAdmin The isAdmin to set.
	 */
	protected void setTemplateAdmin(boolean isAdmin) {
		this.isTemplateAdmin = isAdmin;
	}
		
	/**
	 * @return Returns the isExternalCompany.
	 */
	public boolean isExternalCompany() {
		return isExternalCompany;
	}
	
	/**
	 * @param isExternalCompany The isExternalCompany to set.
	 */
	protected void setExternalFirm(boolean isExternalCompany) {
		this.isExternalCompany = isExternalCompany;
	}	
	
	/**
	 * @return Returns the companyId.
	 */
	public String getCompanyId() {
		return _companyId;
	}
	
	/**
	 * @param companyId The companyId to set.
	 */
	protected void setUserCompanyId(String companyId) {
		this._companyId = companyId;
	}
	
	/**
	 * @return Returns the companyName.
	 */
	public String getCompanyName() {
		return _companyName;
	}
	
	/**
	 * @param companyName The companyName to set.
	 */
	protected void setUserCompanyName(String companyName) {
		this._companyName = companyName;
	}	
	
	/**
	 * @return Returns the MCX Specific GUID.
	 */
	public String getMCXUserGUID() {		
		return _MCXUserGUID;
	}
	
	/**
	 * @param id 
	 */
	protected void setMCXUserGUID(String id) {
		_MCXUserGUID = id;
	}
}
