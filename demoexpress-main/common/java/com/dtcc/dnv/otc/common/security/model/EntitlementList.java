/*
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 *
 */

package com.dtcc.dnv.otc.common.security.model;

import java.io.Serializable;
import java.util.HashMap;

import com.dtcc.dnv.otc.common.exception.XMLDatabaseException;
import com.dtcc.dnv.otc.common.security.xmldb.EntitlementDefinitions;


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
 * EntitlementList is a collection of Privilege instances indexed by a system (otcd, otcp)
 * V1.0 is a simplistic model that reflects the Update or View entitlement
 * 
 */
class EntitlementList implements IEntitlement, Serializable {

	private HashMap listOfEntitlements = null;	// (system, Privilege) value pair
	private String  currentSystemName = null;

	/**
	 * Default Constructor
	 * @param nothing
	 * @return nothing
	 * @throws nothing
	 * 
	 * Simply initialize the hashmap
	 */
	public EntitlementList() 
	{
		super();
		listOfEntitlements = new HashMap();
	}
	
	/**
	 * Method addEntitlement
	 * @param String SIS id - 'dsv' or 'dpr'
	 * @param Privilege
	 * @return nothing
	 * @throws nothing
	 * @see com.dtcc.dnv.otc.common.security.model.IEntitlement
	 * 
	 * Add the entitlement for a SIS id into the hashmap
	 */
	public void addEntitlement(String sisId, Entitlement entitlement) 
	    throws XMLDatabaseException 
	{
		/*
		 * For now it is always a single system.
		 */
		listOfEntitlements.put(sisId, entitlement);
		/*
		 * In our specific case it always only one entitlement 
		 * as per TK
		 */
		 entitlement.setProductTypes( EntitlementDefinitions.getProductTypes(entitlement.getEntitlement()) ) ;
		 entitlement.setAccessControlList( EntitlementDefinitions.getModuleAccess(entitlement.getEntitlement()) );
		 currentSystemName = sisId ;
	}

	/**
	 * Method hasSystemAccess
	 * @param String sis Id
	 * @return boolean
	 * @throws nothing
	 * @see com.dtcc.dnv.otc.common.security.model.IEntitlement
	 * 
	 * Returns true if the user is allowed access to the system
	 */
	public boolean hasSystemAccess(String sisId) 
	{
		return listOfEntitlements.containsKey(sisId);
	}

	/**
	 * Method hasCreatePrivilege
	 * @param String systemName
	 * @return boolean
	 * @throws nothing
	 * @see com.dtcc.dnv.otc.security.model.IEntitlement
	 * 
	 * Returns true if the user is allowed to create records for a system
	 */
	public boolean hasCreatePrivilege(String systemName) 
	{
		Entitlement ent = (Entitlement) listOfEntitlements.get(systemName);
		if (ent != null)
			return ent.hasCreatePrivilege();
		else
			return false;
		
	}

	/**
	 * Method hasReadPrivilege
	 * @param String systemName
	 * @return boolean
	 * @throws nothing
	 * @see com.dtcc.dnv.otc.common.security.model.IEntitlement
	 * 
	 * Returns true if the user is allowed to read records for a system
	 */
	public boolean hasReadPrivilege(String systemName) 
	{
		Entitlement ent = (Entitlement) listOfEntitlements.get(systemName);
		if (ent != null)
			return ent.hasReadPrivilege();
		else
			return false;
		
	}

	/**
	 * Method hasUpdatePrivilege
	 * @param String systemName
	 * @return boolean
	 * @throws nothing
	 * @see com.dtcc.dnv.otc.common.security.model.IEntitlement
	 * 
	 * Returns true if the user is allowed to update records for a system
	 */
	public boolean hasUpdatePrivilege(String systemName) 
	{
		Entitlement ent = (Entitlement) listOfEntitlements.get(systemName);
		if (ent != null)
			return ent.hasUpdatePrivilege();
		else
			return false;
		
	}

	/**
	 * Method hasDeletePrivilege
	 * @param String systemName
	 * @return boolean
	 * @throws nothing
	 * @see com.dtcc.dnv.otc.common.security.model.IEntitlement
	 * 
	 * Returns true if the user is allowed to delete records for a system
	 */
	public boolean hasDeletePrivilege(String systemName) 
	{
		Entitlement ent = (Entitlement) listOfEntitlements.get(systemName);
		if (ent != null)
			return ent.hasDeletePrivilege();
		else
			return false;
	}
	
	/**
	 * Method isAclContains
	 * @param String systemName
	 * @return boolean
	 * @throws nothing
	 * @see com.dtcc.dnv.otc.common.security.model.IEntitlement
	 * 
	 * Returns true if the user is allowed to delete records for a system
	 */
	public boolean isAclContains(String key) 
	{
		Entitlement ent = (Entitlement) listOfEntitlements.get(currentSystemName);
		if (ent != null)
			return ent.isAclContains(key);
		else
			return false;
    }

	/**
	 * Sets the currentSystemName.
	 * @param currentSystemName The currentSystemName to set
	 * 
	 * to switch system depending upon state of ParticipantUserObject
	 */
	public void setCurrentSystemName(String currentSystemName) {
		this.currentSystemName = currentSystemName;
	}

}