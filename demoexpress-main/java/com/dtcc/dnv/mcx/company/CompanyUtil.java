package com.dtcc.dnv.mcx.company;

import java.util.List;

import com.dtcc.dnv.mcx.beans.CompanyBean;
import com.dtcc.dnv.mcx.util.MessageLogger;

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
public class CompanyUtil {
	
	/* Message Logger */
	private final static MessageLogger log = MessageLogger.getMessageLogger(CompanyUtil.class.getName());
	
	/**
	 * Get companies based on indicator
	 * @param companyEnvInd
	 * @return CompanyBean[]
	 */
	public static CompanyBean[] getCompanys(String companyEnvInd) {
		CompanyBean[] companies = CompanyListHandler.getInstance().getCompanyList(companyEnvInd);		
		return companies;
	}
	
	/**
	 * This method should be used by the signon module. CompanyUtil.getCompanys(String companyEnvInd)
	 * should be used in place of this method.
	 * @return CompanyBean[]
	 */
	public static CompanyBean[] getCompanys() {
		CompanyBean[] companies = CompanyListHandler.getInstance().getCompanyList();		
		return companies;
	}
	
	/**
	 * Get company object
	 * @param companyId
	 * @return CompanyBean
	 */
	public static CompanyBean getCompany(String companyId) {
		CompanyBean company = null;
		CompanyBean[] companies = CompanyListHandler.getInstance().getCompanyList();
		if(companies != null) {
			for (int i = 0; i < companies.length; i++) {
				CompanyBean bean = companies[i];
				if(bean.getCompanyId().trim().equalsIgnoreCase(companyId.trim())) {
					company = bean;
					break;
				}
			}
		} else {
		  log.error("CompanyUtil: CompanyListHandler returned empty company list");
		}
		return company;
	}
	
	/**
	 * Get company contacts
	 * @param companyId
	 * @return List
	 */
	public static List getCompanyContacts(String companyId) {
		List contacts = null;
		CompanyBean[] companies = CompanyListHandler.getInstance().getCompanyList();
		if(companies != null) {
			for (int i = 0; i < companies.length; i++) {
				CompanyBean bean = companies[i];
				if(bean.getCompanyId().trim().equalsIgnoreCase(companyId.trim())) {
					contacts = bean.getCompanyContacts();
					break;
				}
			}
		} else {
		  log.error("CompanyUtil: CompanyListHandler returned empty company list");
		}
		return contacts;
	}

}
