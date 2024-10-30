package com.dtcc.dnv.mcx.beans;

import java.util.List;
import java.util.Vector;

/*
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights
 * reserved.
 * 
 * Depository Trust & Clearing Corporation (DTCC) 55, Water Street, New York, NY
 * 10048 U.S.A All Rights Reserved.
 * 
 * This software may contain (in part or full) confidential/proprietary
 * information of DTCC. ("Confidential Information"). Disclosure of such
 * Confidential Information is prohibited and should be used only for its
 * intended purpose in accordance with rules and regulations of DTCC.
 * 
 * @author Kevin Lake
 * 
 * @version 1.0 Date: September 25, 2007
 */
public class CompanyBean extends ABaseTran {
	
	/* Company ID */
	private String companyId;
	
	/* Company Name */
	private String companyName;	

	/* Company Group (Dealer or Client)*/
	private String companyTypeInd;
	
	/* Internal or External Firm */
	private String companyEnvInd;
	
	/* FED 18 indicator */
	private String companyGroupInd;

	/* Company Contacts */
	private List companyContacts = new Vector();
			
	/**
	 * @return Returns the companyContacts.
	 */
	public List getCompanyContacts() {
		return companyContacts;
	}
	
	/**
	 * Add company contact
	 */
	public void addCompanyContact(CompanyContactBean contact) {
		getCompanyContacts().add(contact);
	}
	
	/**
	 * @return Returns the companyEnvInd.
	 */
	public String getCompanyEnvInd() {
		return companyEnvInd;
	}
	
	/**
	 * @param companyEnvInd The companyEnvInd to set.
	 */
	public void setCompanyEnvInd(String companyEnvInd) {
		this.companyEnvInd = companyEnvInd;
	}
	
	/**
	 * @return Returns the companyGroupInd.
	 */
	public String getCompanyTypeInd() {
		return companyTypeInd;
	}
	
	/**
	 * @param companyGroupInd The companyGroupInd to set.
	 */
	public void setCompanyTypeInd(String companyGroupInd) {
		this.companyTypeInd = companyGroupInd;
	}
	
	/**
	 * @return Returns the companyId.
	 */
	public String getCompanyId() {
		return companyId;
	}
	
	/**
	 * @param companyId The companyId to set.
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	/**
	 * @return Returns the companyName.
	 */
	public String getCompanyName() {
		return companyName;
	}
	
	/**
	 * @param companyName The companyName to set.
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}	
	
	/**
	 * @return Returns the companyGroupInd.
	 */
	public String getCompanyGroupInd() {
		return companyGroupInd;
	}
	
	/**
	 * @param companyGroupInd The companyGroupInd to set.
	 */
	public void setCompanyGroupInd(String companyGroupInd) {
		this.companyGroupInd = companyGroupInd;
	}
}
