package com.dtcc.dnv.mcx.beans;

import java.io.Serializable;

/**
 * This class is used as a bean to get and set MCA Type details
 * RTM Reference : 1.21.2
 *
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
 * @author Seeni Mohammad Azharudin S
 * @date Sep 17, 2007
 * @version 1.0
 *
 *
 */
public class MCATypeList implements  Serializable
{
	private String templateId = null;
	private String templateName = null;
	private String templateStatus = null;
	private String publishedDate = null;
	private String lockIndicator = null;
	private String userName = null;
	private String lastUpdatedTimestamp = null;
	private String loggeduser = null;
	private String companyId = null;


		public String getLastUpdatedTimestamp() {
		return lastUpdatedTimestamp;
	}

	public String getLockIndicator() {
		return lockIndicator;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public String getTemplateName() {
		return templateName;
	}

	public String getTemplateStatus() {
		return templateStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setLastUpdatedTimestamp(String string) {
		lastUpdatedTimestamp = string;
	}

	public void setLockIndicator(String string) {
		lockIndicator = string;
	}

	public void setPublishedDate(String string) {
		publishedDate = string;
	}

	public void setTemplateName(String string) {
		templateName = string;
	}

	public void setTemplateStatus(String string) {
		templateStatus = string;
	}

	public void setUserName(String string) {
		userName = string;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String string) {
		templateId = string;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

}
