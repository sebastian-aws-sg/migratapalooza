package com.dtcc.dnv.otc.common.security.model;


/**
 * Copyright (c) 2006 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date May 17, 2006
 * @version 1.0
 * 
 * This class is intended to fit into the DerivSERV framework.  This 
 * object will a User in terms of the Single Signon isolation layer
 * and an IUSer in relation to a DerivSERV user.
 * 
 * Rev	1.1				May 25, 2006			sv
 * Added familyList to toString().
 * 
 * Rev	1.2				July 20, 2006			sv
 * Moved abstract code from this class to AbstractDSCUOUser.  Updated
 * toString() to print out the family list.
 * 
 * Rev	1.3				August 21, 2006			sv
 * Added hasRole() to determine the role of the user.
 * 
 * @version	1.4			April 6, 2007			sv
 * Moved roleList implementation upto parent classes.  This should eventually be populate with more
 * specific DerivSERV non system specific implementations.
 * 
 * @version	1.5			August 14, 2007			sv
 * Added protected implementation methods.  Updated comments.
 * 
 * @version	1.6			October 2, 2007			sv
 * Removed hasRole() as it should not be exposed to inheriting objects.  This is an older
 * implementation that was never removed.
 * 
 * @version	1.7			October 2, 3007			sv
 * Removed unused imports.
 */
public class DSCUOUser extends AbstractDSCUOUser{
	
	/**
	 * Super class implementing constructor.
	 * @param user EXUserobject underlying this object.
	 */
	protected DSCUOUser(ExUser user){
		super(user);
	}
    
	/**
	 * @return Returns the entitlements.
	 */
	protected IEntitlement getEntitlements() {
		return super.getEntitlements();
	}
	/**
	 * @param entitlements The entitlements to set.
	 */
	protected void setEntitlements(IEntitlement entitlements) {
		super.setEntitlements(entitlements);
	}
}
