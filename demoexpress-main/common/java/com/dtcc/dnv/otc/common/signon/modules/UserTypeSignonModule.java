package com.dtcc.dnv.otc.common.signon.modules;

import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.dtcc.dnv.otc.common.security.model.ExUser;
import com.dtcc.dnv.otc.common.signon.DSSignonConstants;
import com.dtcc.dnv.otc.common.signon.config.DSSignonConfiguration;
import com.dtcc.dnv.otc.common.signon.config.DSSignonConfigurationException;
import com.dtcc.dnv.otc.common.signon.config.Role;
import com.dtcc.dnv.otc.common.signon.config.RoleWorkflowStep;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jul 24, 2007
 * @version 1.0
 * 
 * Implentation class for processing the user typeworkflow step.
 * 
 * @version	1.1				July 27, 2007			sv
 * Temporarily updated to be both user and superuser.  Updated
 * suepruser IF branch to disable the bypass so that the user
 * can get a text box to enter a participant id, rather than
 * bypassing the step and moving to the next step.
 * 
 * @version	1.2				August 14, 2007			sv
 * Updated with a more real implementation where the user object is
 * made available to the module.  Currently this module is hard coded for 
 * DSV until the configuration object are ready.
 * 
 * @version	1.3				August 30, 2007			sv
 * Added more concrete implementation and updated interface method to 
 * throw exception.
 * 
 * @version	1.4				September 2, 2007		sv
 * Checked in for Cognizant.  updated comments and javadoc.  Updated 
 * implementation to use module name and not module type to locate
 * the configuaration object.  Updated logic to be more generic, than
 * specific to user and superusers.
 *
 * @version	1.5				September 9, 2007		sv
 * Checked in for Cognizant.  Updated with Role object getRoleDisplayName 
 * method to get the Label name.
 * 
 * @version	1.6				September 12, 2007		sv
 * Updated module to extend AbstractSignonModule.  Checked in for Cognizant.  
 * Handled Exception. SignonModuleException is thrown when exception 
 * occurs or for unexpected case.
 * 
 * @version	1.7				September 26, 2007		sv
 * Checked in for Cognizant.  Added logic to handle mutually exclusive property.
 * 
 * @version	1.8				October 2, 2007			sv
 * Checked in for Cognizant.  Updated the implementation to handle 
 * "mutuallyExclusive" property. 
 * 
 * @version	1.9				October 11, 2007		sv
 * Updatd to trim roles.
 * 
 * @version	1.10				October 18, 2007		sv
 * Updated logger to be static final.
 */
public class UserTypeSignonModule extends AbstractSignonModule {
	
	// Holds the logger instance
	private static final Logger log = Logger.getLogger(UserTypeSignonModule.class);
	
	/***
	 * This method process the user type sign workflow step.
	 * 
	 * @see com.dtcc.dnv.otc.common.signon.modules.ISignonModule#process(com.dtcc.dnv.otc.common.signon.modules.ISignonModuleRequest)
	 */
	public ISignonModuleResponse process(ISignonModuleRequest request) throws SignonModuleException{
		DSSignonConfiguration config = null;
		RoleWorkflowStep rolewrkFlwStep = null;
		// Instantiate response object.
		SignonModuleResponse response = new SignonModuleResponse();
		// Gets the reference for the Exuser Object
		ExUser user = request.getUser();
		try{
			// Gets the configuration object
			config = DSSignonConfiguration.getInstance();
			rolewrkFlwStep =  (RoleWorkflowStep)config.getWorkflowStepType(request.getModuleName());
		}
		catch(DSSignonConfigurationException e){
			// Throws SignonModuleException. UserTypeSignonModule failed.
			throw new SignonModuleException(DSSignonConstants.EX_MODULE_FAILED,request.getModuleName(),
					DSSignonConstants.EX_MODULE_FAILED_STR + request.getModuleName(),e);
		}
		// Holds the roles of the logged in user
		Vector roles = getUserRoles(user,rolewrkFlwStep);
				
		// If user type is ambiguous, then give the user the option to choose.
		if ( roles.size() > 1 ){
				// Holds the user types response.
				Vector userTypes = new Vector();
				userTypes.add(new LabelValueBean("",""));
				Iterator rolesIter = roles.iterator();
				while(rolesIter.hasNext()){
					Role role = (Role)rolesIter.next();
					
					// User can have only one role, either user or superuser but not both
					if(role.isMutuallyExclusive()){
						// Clear the collection before throwing Exception.
						userTypes.clear();
						throw new SignonModuleException(DSSignonConstants.EX_USERTYPE_INVALID,request.getModuleName(),
								DSSignonConstants.EX_USERTYPE_INVALID_STR);
					}
						
					// add user role name and value as user types
					userTypes.add(new LabelValueBean(role.getRoleDisplayName(),role.getRoleValue()));
				}
				// add the user types to the response.
				response.setSignonResponse(userTypes);
				response.setBypass(false);
			
		}
		// If the user has one role, then bypass the step and return the value in the response. 
		// If user has Super User role, then user role will be handled in SuperUserSignonModule 
		else if (roles.size() == 1){
			response.setSignonResponse( ((Role)roles.get(0)).getRoleValue());
			response.setBypass(true);
		}
		else{
			throw new SignonModuleException(DSSignonConstants.EX_USERTYPE_INVALID,request.getModuleName(),
					DSSignonConstants.EX_USERTYPE_INVALID_STR);
		}
		// return the response
		return response;
	}
	
	/**
	 * Determines if the user is a super user or user or have both roles and returns the user roles as collection
	 * @param user the Exuser instance
	 * @param rolewrkFlwStep the RoleWorkflowStep instance
	 * @return Vector collection of roles the user is entitled.
	 */
	private Vector getUserRoles(ExUser user,RoleWorkflowStep rolewrkFlwStep){
		// Holds the Roles the user is entitled
		Vector rolesEntitled = new Vector();
		
		// Gets the collection of roles
		Collection roles = rolewrkFlwStep.getAllRoles();
		boolean hasRole = false;
		
		// Iterate through the collection to validate the roles
		if(roles.size() > 0){
			Iterator rolesIter = roles.iterator();
			while(rolesIter.hasNext()){
				Role role = (Role)rolesIter.next();
				String userRoles = role.getUserRoles();
				hasRole = validateUserRoles(user,loadTokensIntoCollection(userRoles));
				// If the user is entitled to the role, then add it to the entitled role response
				if(hasRole)
					rolesEntitled.add(role);
			}
		}
		
		return rolesEntitled;
	}
	
	/**
	 * Determines if the user has a role.
	 * @param user the Exuser instance
	 * @param roles the collection of user roles
	 * @return boolean value determining the user has a role.
	 */
	private boolean validateUserRoles(ExUser user,Vector roles){
		boolean hasRole = false;
		for(int cnt = 0;cnt <roles.size();cnt++){
			
			if(!hasRole){
				String role = (String)roles.get(cnt);
				hasRole = user.hasRole(role.trim());
			}
		}
		return hasRole;
	}
	
	/**
	 * Utility to create a vector of tokens based on the tokenString.
	 * 
	 * @param tokenString String value of tokens to be loaded.
	 * @return Vector object represting string value of tokens.
	 */
	private static Vector loadTokensIntoCollection(String tokenString){
		// Instantiate vector.
		Vector collection = new Vector();
		// Instantiate StringTokenizer
		StringTokenizer st = new StringTokenizer(tokenString,DSSignonConstants.VALUE_DELIMITER);

		// Loop through tokenizer tokens.
		while (st.hasMoreTokens()){
			// Add token to vector.
			collection.add(st.nextToken().trim());
		}
		// Return Vector.
		return collection;
	}
}
