package com.dtcc.dnv.otc.common.security.model;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jun 30, 2007
 * @version 1.0
 *
 * This class serves as an abstract of the role list collection and is an 
 * immutable object representation of the roles a user has.
 * 
 * @version	1.1					September 20, 2007			sv
 * Updated class to implement Serializable.
 */
public class DSRoleList implements Serializable{
	// Instance members.
	private Hashtable roleList = new Hashtable();

	/**
	 * DerivSERV Role list used to contain the respective rolelist for a user
	 * obejct.
	 * 
	 * @param roleList
	 */
	public DSRoleList(Hashtable roleList){
		this.roleList = roleList;
	}
	
	/**
	 * Used to get boolean value for role.
	 * 
	 * @param roleName
	 * @return
	 */
	public Boolean getRole(String roleName){
		return (Boolean)roleList.get(roleName);
	}
	
	/**
	 * Adds role permission to role list.
	 * 
	 * @param roleName String value of the rolename to enable permissions.
	 * @param hasRole Boolean value of permission for the role.
	 */
	void addRole(String roleName, Boolean hasRole){
		this.roleList.put(roleName, hasRole);
	}
}
