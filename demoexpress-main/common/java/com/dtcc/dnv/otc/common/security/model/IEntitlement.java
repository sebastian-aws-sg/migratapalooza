/*
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 *
 */

package com.dtcc.dnv.otc.common.security.model;

import com.dtcc.dnv.otc.common.exception.XMLDatabaseException;

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
 * @author Toshi Kawanishi
 * @version 1.0
 * 
 * IEntitlement defines the interface that support the semantics of how application code
 * determines a user's access to application functionality
 * 
 * V1.0 is a simplistic model that reflects the Update or View entitlement
 * 
 * Changes:
 *    5/4/2004 DLV added Entitlements property to carry over access value
 */
interface IEntitlement {


	/**
	 * Method addEntitlement
	 * @param String system name
	 * @param Privilege
	 * @return nothing
	 * @throws nothing
	 * 
	 * Add the entitlement for a system to the user's entitlements
	 */
	public void addEntitlement(String systemName, Entitlement entitlement) throws XMLDatabaseException ;

	/**
	 * Method hasSystemAccess
	 * @param String system name
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns true if the user is allowed access to the system
	 */
	public boolean hasSystemAccess(String systemName);

	/**
	 * Method hasCreatePrivilege
	 * @param String systemName
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns true if the user is allowed to create records in a system
	 */
	public boolean hasCreatePrivilege(String systemName);

	/**
	 * Method hasReadPrivilege
	 * @param String systemName
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns true if the user is allowed to read records from a system
	 */
	public boolean hasReadPrivilege(String systemName);

	/**
	 * Method hasUpdatePrivilege
	 * @param String systemName
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns true if the user is allowed to update records in a system
	 */
	public boolean hasUpdatePrivilege(String systemName);

	/**
	 * Method hasDeletePrivilege
	 * @param String systemName
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns true if the user is allowed to delete records from a system
	 */
	public boolean hasDeletePrivilege(String systemName);

	/**
	 * Method isUpdateAllowed
	 * @param String key f.e.:"Trade"
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns true if the user's entitlements allow Updates
	 */
	public boolean isAclContains(String key);
}
