/*
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 *
 */

package com.dtcc.dnv.otc.common.security.model;

import java.io.Serializable;
import com.dtcc.dnv.otc.common.security.xmldb.IPrivilege;

//never used
//import com.dtcc.dnv.otc.common.exception.XMLDatabaseException;



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
 * Privilege represents the user's privileges
 * V1.0 simply accomodates the Update or View privileges that corresponds to the PCWEB 'D' or 'I'
 * D = CRUD
 * I = _R__
 * 
 * Changes
 *    5/4/2004 DLV added productTypes: IPrivilege[]
 *    5/4/2004 DLV added accessControlList: IPrivilege[] 
 *    5/4/2004 DLV added method getProductTypes(String entitlements);
 *    5/4/2004 DLV added method getModuleAccess(String entitlements);
 * 
 */
public class Entitlement implements Serializable {

	private static String ENTITLEMENT_INQUIRY = "I";  //entitlement inquiry
	private static String ENTITLEMENT_UPDATE  = "D";	//entitlement update

	private IPrivilege[] productTypes          = null;
	private IPrivilege[] accessControlList     = null;
	
	
	private boolean hasCreate = false;	// Create records
	private boolean hasRead   = false;	// Read records
	private boolean hasUpdate = false;	// Update records
	private boolean hasDelete = false;	// Delete records
	private String entitlement = null;	// Delete records
	
	/**
	 * Constructor for Entitlement.
	 */
	public Entitlement(String _entitlement) {
		super();
		this.entitlement = _entitlement;
		if (entitlement.equals(ENTITLEMENT_UPDATE)) 
		{
			// User gets "CRUD"
			this.setCreatePrivilege();
			this.setDeletePrivilege();
			this.setReadPrivilege();
			this.setUpdatePrivilege();
		} 
		else if (entitlement.equals(ENTITLEMENT_INQUIRY)) 
		{
			// user gets "R" - read only
			this.setReadPrivilege();
		}
		else {
			/*
			 * Throw ugly exception in case entitlements are missing
			 */
			String XXX = (String)null;
		}
	}

	// Set
	public void setCreatePrivilege() { hasCreate = true; }
	public void setReadPrivilege()   { hasRead = true; }
	public void setUpdatePrivilege() { hasUpdate = true; }
	public void setDeletePrivilege() { hasDelete = true; }
	// UnSet
	public void unsetCreatePrivilege() { hasCreate = true; }
	public void unsetReadPrivilege()   { hasRead = true; }
	public void unsetUpdatePrivilege() { hasUpdate = true; }
	public void unsetDeletePrivilege() { hasDelete = true; }
	// Test
	public boolean hasCreatePrivilege() { return hasCreate; }
	public boolean hasReadPrivilege()   { return hasRead; }
	public boolean hasUpdatePrivilege() { return hasUpdate; }
	public boolean hasDeletePrivilege() { return hasDelete; }
	
	public String       getEntitlement() { return entitlement;}
	public IPrivilege[] getAccessControlList() { return accessControlList; }
	public IPrivilege[] getProductTypes() { return productTypes; }
	public void setEntitlement(String entitlement) { this.entitlement = entitlement; }
	public void setAccessControlList(IPrivilege[] accessControlList) { this.accessControlList = accessControlList; }
	public void setProductTypes(IPrivilege[] productTypes) { this.productTypes = productTypes; }

	/**
	 * Checks the isAclContains.
	 * @see    isAclContains(String key)
	 * @return boolean
	 * @param  String
	 * 
	 * verified that "resource" key is valid for this participant
	 */
	public boolean isAclContains(String key) {
		
	   int sz = accessControlList.length ;
	   for(int ii=0; ii< sz; ii++) {
	   	  IPrivilege iPrivilege = accessControlList[ii];
	   	  if( iPrivilege.isModuleAccess(key, entitlement) )
	   	      return true ;
	   }
	   return false;
	}

}
