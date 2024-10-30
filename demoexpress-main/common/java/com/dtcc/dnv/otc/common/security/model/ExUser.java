package com.dtcc.dnv.otc.common.security.model;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import com.dtcc.sharedservices.security.common.CUOUser;
import com.dtcc.sharedservices.security.common.User;
/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Apr 3, 2007
 * @version 1.0
 * 
 * This class represent the extended user object to cache the user roles.
 * The intention of this class is to serve as an extension of the underlying 
 * user objects.  Since the user object shuold be immutable, there are no
 * getter methods exposed for this object.  Although it extends User which 
 * has public getter methods, calling the getter methods on this object 
 * will not update anything, as the actual core object is stored in this
 * object.
 * 
 * @version	1.1				April 19, 2007			sv
 * Updated javadoc.  Removed setRoleList().
 * 
 * @version	1.2				May 16, 2007			sv
 * Updated toString() to use parent toString() as well.
 * 
 * @version	1.3				July 21, 2007			sv
 * Updated to support DSRoleList implementation instead of local Hashtable.
 * Removed addRole() as it should be part of the DSRoleList implementation.
 * Added setter and getter for DSRoleList.
 * 
 * @version 1.4				August 6, 2007			sv
 * Reverted to 1.2 for common 1.6.6 changes.  Added the setter and getter for 
 * roleList as temporary solution.  This will be removed in a later version of 
 * common.
 * 
 * @version	1.5				August 7, 2007			sv
 * Updated the container for the roles from a Hashtable to DSRoleList.  Updated
 * this user object to hold an instance of the CUOUser object.  Added protectected
 * constructor to enforce all concrete user object to pass in the CUOUser object
 * in the constructor.  Added default scope setter/getter for DSROleList.  Added
 * private scope setter/getter for CUOUser object.  Added CUOUser getter methods to 
 * GET from the CUOUser instance member of this object, but intentionally left out
 * the setter to maintain the integrity of the object as it should be immutable.
 * The getters from the parent class will simply set values in this object, but 
 * what is referenced in the getters is the underlying user object (i.e. CUOUser). 
 * Updated comments.
 * 
 * @version	1.6				October 2, 2007			sv
 * Updated the hasRole(String roleName, HttpServletRequest req) to be 
 * default scope.
 * 
 * @version	1.7				October 10, 2007		sv
 * Updated the constructor to handle different type of users.  This way the 
 * underlying user is set correctly.  Updated checkRoleInRequest() to trim 
 * the roleName when checking the role in the request.
 * 
 * @version	1.8				October 11, 2007		sv
 * Added error handling and trimming logic to hasRole().
 * 
 * @version	1.9				October 17, 2007		sv
 * Updated CUOUser setter methods to throw RuntimeException to ensure
 * core user data is immutable.
 * 
 * @version	1.10			October 26, 2007		sv
 * Updated toString() to use underlying user object for data rather than
 * super.toString().
 * 
 * @version	1.11			October 30, 2007		sv
 * Updated to hasRole() to instantiate the role list in case a list of roles 
 * was not provided.
 */
public class ExUser extends User {
	// Instance members.
	private DSRoleList roleList = null;
	private CUOUser user = null;
	
	/**
	 * Stores the basic form of the user object as an instance member.  If the 
	 * user object is already of type ExUser, then  move the ExUser 
	 * properties (i.e. roleList) as well.
	 * 
	 * @param user
	 */
	protected ExUser(CUOUser user){
		// If instance of ExUser already then move properties.
		if (user instanceof ExUser){
			// Cast the user object
			ExUser exUser = (ExUser) user;
			// Set the ExUser specific properties
			setExUserProperties(exUser);
			// Set the underlying user object.
			setUser(exUser.getUser());
		}
		else if (user instanceof CUOUser){
			// Set the underlying user object.
			setUser(user);
		}
	}

	/**
	 * Method used to determine if the user is in a role, by name.  This method
	 * will cache the boolean value of whther or not the user has the role. This method
	 * is protected an is only intended to be used by the user layer.  It is not to
	 * be exposed to public scope.
	 * 
	 * @param roleName String value of the role name
	 * @param req HttpServletRequest object to validate role.
	 * @return boolean value for if the user is in the role specified.
	 */
	boolean hasRole(String roleName, HttpServletRequest req){
		Boolean hasRole = new Boolean(false);
		// If the roleList has not been instantiated (which should never be the case,
		// but just in case) then create one and attempt to locate the 
		// role in the request.
		if (this.roleList == null){
			// Instantiate the role list
			setRoleList(new DSRoleList(new Hashtable()));
		}
		// Look up a cached version of the role.
		hasRole = (getRoleList().getRole(roleName));
		// If a cached value does not exist, then check for a value in the
		// request and cache this value.
		if (hasRole == null){
			hasRole = checkRoleInRequest(roleName, req);
			// Add role and boolean value.
			getRoleList().addRole(roleName, hasRole);
		}
		// return boolean value for this role.
		return hasRole.booleanValue();
	}

	/**
	 * Utility method to return a boolean value as a public interface.
	 * Because this is a utility methid and serves as a public interface
	 * it will effect the state of the DSRoleList.  It will return false
	 * if there is no role, but will not cahce or update the cached
	 * role list.
	 * 
	 * @param roleName String value of the name of the role.
	 * @return boolean value indicating the user has the role or not.
	 */
	public boolean hasRole(String roleName){
		// If an invalid roleName is passed then return false.
		if (roleName == null || roleName.trim().length() == 0){
			return false;
		}
		// Get the role list.
		DSRoleList roleList = this.getRoleList();
		// If it is null, then instantiate an empty one.
		if (roleList == null){
			this.roleList = new DSRoleList(new Hashtable());
		}
		// Attempt to get a cached version of the role.  Must trim the roleName.
		Boolean hasRole = roleList.getRole(roleName.trim());
		// If there is not cached role, return false.  Do not update the DSRoleList.
		if (hasRole == null)
			return false;
		else
			// If it has been found, then return what the boolean value is.
			return hasRole.booleanValue();
	}
	
	
    /**
     * @see java.lang.Object#toString()
     */
    public String toString(){
    	StringBuffer sb = new StringBuffer();
    	sb.append(" User " + this.getClass() + "; ");
    	sb.append(this.getUser().toString() + "; ");    	
    	return sb.toString();
    }
    
	/**
	 * @return Returns the roleList.
	 */
    DSRoleList getRoleList() {
		return roleList;
	}
	/**
	 * @param roleList The roleList to set.
	 */
	void setRoleList(DSRoleList roleList) {
		this.roleList = roleList;
	}
	
	/**
	 * @return Returns the user.
	 */
	private CUOUser getUser() {
		return user;
	}
	
	/**
	 * Check the role via the J2EE check in the HttpServletRequest.
	 * 
	 * @param roleName String value of the role name to validate
	 * @param req HttpServletRequest used to check for the role.
	 * @return Boolean value of the J2EE role check.
	 */
	private Boolean checkRoleInRequest(String roleName, HttpServletRequest req){
		boolean hasRole = false;
		// If roleName is null, then just set to false as it is invalid
		if (roleName == null){
			hasRole = false;
		}
		// If it does exist, then trim the String incase there are any padded spaces.
		else{
			hasRole = req.isUserInRole(roleName.trim());			
		}
		// return boolean value.
		return new Boolean(hasRole);
	}
	
	/**
	 * @param user The user to set.
	 */
	private void setUser(CUOUser user) {
		this.user = user;
	}
	
	/**
	 * Move Exuser properties from original ExUser to this instance.
	 * 
	 * @param user
	 */
	private void setExUserProperties(ExUser user){
		ExUser exUser = (ExUser) user;
		this.setRoleList(exUser.getRoleList());
	}
	
	/**
	 * This section is the methods accessed through the internal user 
	 * implementation (i.e. CUOUser). 
	 */
	
	/**
	 * @see com.dtcc.sharedservices.security.common.CUOUser#getUserId()
	 */
	public String getUserId(){
		return this.getUser().getUserId();
	}
	
	/**
	 * Method is not allowed to be used.
	 * 
	 * @see com.dtcc.sharedservices.security.common.User#setUserId(java.lang.String)
	 */
	public void setUserId(String userid){
		throw new RuntimeException("Method [setUserId] is not supported.");
	}
	
	/**
	 * @see com.dtcc.sharedservices.security.common.CUOUser#getCompanyId()
	 */
	public String getCompanyId(){
		return this.getUser().getCompanyId(); 
	}
	
	/**
	 * Method is not allowed to be used.
	 * 
	 * @see com.dtcc.sharedservices.security.common.User#setCompanyId(java.lang.String)
	 */
	public void setCompanyId(String companyId){
		throw new RuntimeException("Method [setCompanyId] is not supported.");
	}
	
	/**
	 * @see com.dtcc.sharedservices.security.common.CUOUser#getCompanyName()
	 */
	public String getCompanyName(){
		return this.getUser().getCompanyName(); 
	}
	
	/**
	 * Method is not allowed to be used.
	 * 
	 * @see com.dtcc.sharedservices.security.common.User#setCompanyName(java.lang.String)
	 */
	public void setCompanyName(String companyName){
		throw new RuntimeException("Method [setCompanyName] is not supported.");
	}
	
	/**
	 * @see com.dtcc.sharedservices.security.common.CUOUser#getEmailAddress()
	 */
	public String getEmailAddress(){
		return this.getUser().getEmailAddress(); 
	}
	
	/**
	 * Method is not allowed to be used.
	 * 
	 * @see com.dtcc.sharedservices.security.common.User#setEmailAddress(java.lang.String)
	 */
	public void setEmailAddress(String emailAddress){
		throw new RuntimeException("Method [setEmailAddress] is not supported.");
	}
	
	/**
	 * @see com.dtcc.sharedservices.security.common.CUOUser#getEntitlementId()
	 */
	public String getEntitlementId(){
		return this.getUser().getEntitlementId(); 
	}
	
	/**
	 * Method is not allowed to be used.
	 * 
	 * @see com.dtcc.sharedservices.security.common.User#setEntitlementId(java.lang.String)
	 */
	public void setEntitlementId(String entitlementId){
		throw new RuntimeException("Method [setEntitlementId] is not supported.");
	}

	/**
	 * @see com.dtcc.sharedservices.security.common.CUOUser#getGUID()
	 */
	public String getGUID(){
		return this.getUser().getGUID(); 
	}
	
	/**
	 * Method is not allowed to be used.
	 * 
	 * @see com.dtcc.sharedservices.security.common.User#setGUID(java.lang.String)
	 */
	public void setGUID(String GUID){
		throw new RuntimeException("Method [setGUID] is not supported.");
	}
	
	/**
	 * @see com.dtcc.sharedservices.security.common.CUOUser#getPhoneNumber()
	 */
	public String getPhoneNumber(){
		return this.getUser().getPhoneNumber(); 
	}
	
	/**
	 * Method is not allowed to be used.
	 * 
	 * @see com.dtcc.sharedservices.security.common.User#setPhoneNumber(java.lang.String)
	 */
	public void setPhoneNumber(String phoneNumber){
		throw new RuntimeException("Method [setPhoneNumber] is not supported.");
	}
	
	/**
	 * @see com.dtcc.sharedservices.security.common.CUOUser#getUserFirstName()
	 */
	public String getUserFirstName(){
		return this.getUser().getUserFirstName(); 
	}
	
	/**
	 * Method is not allowed to be used.
	 * 
	 * @see com.dtcc.sharedservices.security.common.User#setUserFirstName(java.lang.String)
	 */
	public void setUserFirstName(String userFirstName){
		throw new RuntimeException("Method [setUserFirstName] is not supported.");
	}
	
	/**
	 * @see com.dtcc.sharedservices.security.common.CUOUser#getUserLastName()
	 */
	public String getUserLastName(){
		return this.getUser().getUserLastName();
	}
	
	/**
	 * Method is not allowed to be used.
	 * 
	 * @see com.dtcc.sharedservices.security.common.User#setUserLastName(java.lang.String)
	 */
	public void setUserLastName(String userLastName){
		throw new RuntimeException("Method [setUserLastName] is not supported.");
	}
}
