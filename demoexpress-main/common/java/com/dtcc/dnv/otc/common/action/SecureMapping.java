package com.dtcc.dnv.otc.common.action;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;

/**
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
 * Form bean for a Struts application.
 *
 * @author Dmitriy Larin
 * @version 1.0
 * 
 * SecurityMapping is implements  the ability to take additional 
 * configuration parameters in struts-config.xml.
 * Each DerivSERV Action is assigned 
 */
public class SecureMapping extends ActionMapping {

    /*
     * String Privilege that user must have in order to 
     * access a particular functional module.
     */
     protected String privilege = null;


	/**
	 * Constructor for SecureMapping.
	 */
	public SecureMapping() {
		super();
	}

	/**
	 * @see org.apache.struts.action.ActionMapping#findException(Class)
	 */
	public ExceptionConfig findException(Class arg0) {
		return super.findException(arg0);
	}

	/**
	 * @see org.apache.struts.action.ActionMapping#findForward(String)
	 */
	public ActionForward findForward(String arg0) {
		return super.findForward(arg0);
	}

	/**
	 * @see org.apache.struts.action.ActionMapping#findForwards()
	 */
	public String[] findForwards() {
		return super.findForwards();
	}

	/**
	 * @see org.apache.struts.action.ActionMapping#getInputForward()
	 */
	public ActionForward getInputForward() {
		return super.getInputForward();
	}
	/**
	 * Returns the privilege.
	 * @return String
	 * 
     * get Privilege method, that user must have in order to 
     * access a particular functional module.	 
	 */
	public String getPrivilege() {
		return privilege;
	}

	/**
	 * Sets the privilege.
	 * @param privilege The privilege to set
	 * 
     * setPrivilege method, that user must have in order to 
     * access a particular functional module.	  
	 */
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

    /*
     * to obtain a particular privilege for 
     * specific functional module
     */
    private String getRequiredPrivilege() {
    	return privilege;
    }

}
