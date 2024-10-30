package com.dtcc.dnv.mcx.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dtcc.dnv.mcx.beans.CompanyContactBean;
import com.dtcc.dnv.mcx.company.CompanyUtil;
import com.dtcc.dnv.otc.common.resourcelocator.IResourceLocator;
import com.dtcc.dnv.otc.common.resourcelocator.LocatorConstants;
import com.dtcc.dnv.otc.common.resourcelocator.ResourceLocator;

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
public class EmailUtil {
	// Logger
	private final static MessageLogger log = MessageLogger.getMessageLogger(EmailUtil.class.getName());
	
	/**
	 * Get multi-dimensional array of company contacts
	 * @param companyId
	 * @return String[]
	 */
	public static String[] getCompanyContactEmails(String companyId) {
		List companyContactEmails = new ArrayList();
		
		// Get company contacts
	    List companyContactList = CompanyUtil.getCompanyContacts(companyId);
	    
	    Iterator iter = companyContactList.iterator();
	    while (iter.hasNext()) {
			CompanyContactBean contact = (CompanyContactBean)iter.next();
			String email = contact.getContactEmail();
			if(email != null && email.trim().length() > 0) {
				if(contact.isPrimary()) {
					companyContactEmails.add(0, email);					
				} else {
					companyContactEmails.add(email);
				}
	        }
		}
	    
	    // Set to String[]
	    String[] contacts = companyContactEmails.size() > 0 ? 
	    		            (String[])companyContactEmails.toArray(new String[companyContactEmails.size()]) : 
	    		            null; 
		return contacts;
	}
	
	/**
	 * Get email message content
	 * @param msgKey
	 * @return String
	 */
	public static String getEmailMessage(String msgKey) {
		String message = null;
		
		String fileLoc = MessageResources.getMessage(msgKey);
		IResourceLocator locator = ResourceLocator.getInstance();
		try {
			
		    // Existing code 
		    //InputStream is = locator.findResourceAsStream(fileLoc, LocatorConstants.EAR_FILE);
		    
		    InputStream is = locator.findResourceAsStream(fileLoc, LocatorConstants.NO_PATH);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			StringBuffer buffer = new StringBuffer();
			String line = null;
            while ((line = br.readLine()) != null) {
              buffer.append(line);
            }
            br.close();	
            message = buffer.toString();
		} catch (Exception e) {
		   // e.printStackTrace();
		  log.error(e);
		}		
		return message;
	}

}
