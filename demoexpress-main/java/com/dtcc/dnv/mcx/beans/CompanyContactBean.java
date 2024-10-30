package com.dtcc.dnv.mcx.beans;

import java.io.Serializable;

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
public class CompanyContactBean implements Serializable {
   
	/* Contact Name */
	private String contactName;
	
	/* Contact Phone */
	private String contactPhone;
	
	/* Contact Email */
	private String contactEmail;
	
	/* Is Primary Contact Flag */
	private boolean isPrimary;
		
	/**
	 * @return Returns the contactEmail.
	 */
	public String getContactEmail() {
		return contactEmail;
	}
	
	/**
	 * @param contactEmail The contactEmail to set.
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	
	/**
	 * @return Returns the contactName.
	 */
	public String getContactName() {
		return contactName;
	}
	
	/**
	 * @param contactName The contactName to set.
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	/**
	 * @return Returns the contactPhone.
	 */
	public String getContactPhone() {
		return contactPhone;
	}
	
	/**
	 * @param contactPhone The contactPhone to set.
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	/**
	 * @return Returns the isPrimary.
	 */
	public boolean isPrimary() {
		return isPrimary;
	}
	
	/**
	 * @param isPrimary The isPrimary to set.
	 */
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
}
