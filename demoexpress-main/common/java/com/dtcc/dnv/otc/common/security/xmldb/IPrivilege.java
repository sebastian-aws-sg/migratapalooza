/*
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 */

package com.dtcc.dnv.otc.common.security.xmldb;


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
 * IPrivilege defines the interface that support the semantics of how application code
 * determines a role-based access resources.
 * 
 * V1.0 is a simplistic model that reflects ???
 */
public interface IPrivilege {


	/**
	 * Method isProductTypes
	 * @param String permission
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns true if the user is allowed to read records from a system
	 */
    public boolean isProductKey(String productKey); 

	/**
	 * Method isProductTypes
	 * @param String permission
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns IPrivilege if the user is allowed to read records from a system
	 */
    public IPrivilege getProductKey(String productKey); 

	/**
	 * Method isAllowed
	 * @param String key
	 * @param String permission
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns true if the user is allowed to update records in a system
	 */
	 public boolean isModuleAccess(String key, String permission);
	 
	/**
	 * Method getAllowed
	 * @param String key
	 * @param String permission
	 * @return IPrivilege
	 * @throws nothing
	 * 
	 * Returns true if the user is allowed to update records in a system
	 */
	 public IPrivilege getModuleAccess(String key, String permission);	 
}
