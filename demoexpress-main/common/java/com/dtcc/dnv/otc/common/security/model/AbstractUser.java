/*
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 *
 */

package com.dtcc.dnv.otc.common.security.model;

import java.io.Serializable;

import com.dtcc.dnv.otc.common.exception.UserException;

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
 * AbstractUser is the super class of all custom exceptions in the OTC application.
 * 
 */
abstract class AbstractUser implements Serializable, IUser {

	private static final String _class = "com.dtcc.dnv.otc.common.security.AbstractUser";
	
	protected IEntitlement entitlements = null;	// EntitlementDefinitions - PCWeb based for now
	protected String currentSystem = null;		// The system the user is accessing - otcd or otcp
	protected boolean valid = false;				// Is the user object fully instantiated

	
	/**
	 * Method getCurrentSystem
	 * @param nothing
	 * @return String The system the user is accessing - otcd or otcp
	 * @throws UserException
	 * @see com.dtcc.dnv.otc.security.model.IUser
	 * 
	 * The system under which this user instance was created.  This information is set
	 * during the initial creation by looking at the URL context.
	 * This value is used for entitlement checks and must not be null
	 */
	public final String getCurrentSystem() throws UserException {
		if ( currentSystem == null)
			throw UserException.createFatal(UserException.SEC_ERROR_CODE_NO_SYSTEM, "Cannot determine the system being accessed");
		else
			return currentSystem;
	}

	/**
	 * Method setCurrentSystem
	 * @param String - otcd or otcp
	 * @return nothing
	 * @throws nothing
	 * 
	 */
	public final void setCurrentSystem(String systemName) {
		currentSystem = systemName;
	}

	/**
	 * Method isValid
	 * @param nothing
	 * @return boolean
	 * @throws nothing
	 * @see com.dtcc.dnv.otc.security.model.IUser
	 * 
	 * Returns true if the user is fully instantiated by:
	 * 1. has an underlying security context - PCWebUser
	 * 2. has entitlements
	 * 3. has a system
	 * 4. has family list
	 * 5. has conterparty list
	 * 
	 */
	public final boolean isValid() {
		return valid;
	}
	
    /**
     * This method is not implemented in this version of the user object
     * so it always returns false.
     * 
     * @return boolean value of true if the user has the role
     */
    public boolean hasRole(String roleName){
    	return false;
    }
}
