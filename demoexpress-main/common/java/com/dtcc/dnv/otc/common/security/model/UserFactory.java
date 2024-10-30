/*
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 *
 */

package com.dtcc.dnv.otc.common.security.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dtcc.dnv.otc.common.exception.UserException;
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
 * UserFactory is the public interface class used for the initial creation of the user object
 * during sign-on and to obtain a reference to the user object via the IUser interface.
 * 
 * Once created, the user object is maintained in the HttpSession under a private key.
 * Application should use the getUser() method and pass the IUser to the classes
 * that need user information
 * 
 * Rev 1.2	May 31, 2004	TK
 * Added the call to setSessionId() so the user object holds the session id for creating AuditInfo
 * 
 * Rev	1.3					May 24, 2006		sv
 * Added createUser(HttpServletRequest, List) to support creating a user by providing a 
 * List of roles that the application cares about.  This method returns a DSCUOUser.
 * Updated imports.
 * 
 * Rev	1.5					July 21, 2006		sv
 * Overloaded createUser() to also take a CUOUser object.
 * 
 * ############### Imported into new project.  New versioning sequence used.
 * 
 * @version	1.3				April 6, 2007		sv
 * Removed createUser (HttpServletRequest req, List roleList, CUOUser cuoUser) as it
 * is not applicable anymore since the roles will be managed by the user object.
 * Renamed createUser(HttpServletRequest req, List roleList) to 
 * createCUOUser (HttpServletRequest req).
 * 
 * @version	1.4				September 19, 2007		sv
 * Updated this factory class specifically for CRE.  This change attempts to be
 * backward caompatible, where if on calling the getUser() if the non CRE user is not already
 * created, then looks for the CRE user object.
 * 
 * @version	1.5				October 2, 2007			sv
 * Updated to only have getUser() public method, and a private method for
 * attaining CRE user object.  This is the official COMMON 2.0.0 CRE
 * compliant version.
 * 
 * @vesrion	1.6				October 30, 2007		sv
 * Updated getCUOUser() to use static DSCUOUserManager.getUser() to attain
 * already create user manager.
 */
public abstract class UserFactory {

	private static final String _class = "com.dtcc.dnv.otc.security.UserFactory";
	public static final String UF_SESSIONKEY_USER = "DERIVSERV_USER_KEY";
	
	/**
	 * A factory-like method used for the initial creation of an IUser implementation object
	 * 
	 * @param HttpServletRequest req
	 * @param roleList List of roles the application care to know about.
	 * @return IUser (DSCUOUser) object
	 * @throws UserException
	 * @throws XMLDatabaseException
	 */
	private static IUser getCUOUser (HttpServletRequest req) throws UserException{
		IUser user = DSCUOUserManager.getUser(req);
		return user;
	}

	/**
	 * Method getUser
	 * @param HttpServletRequest req
	 * @return IUser
	 * @throws UserException
	 * 
	 * This method must be used by application code to obtain the reference to the user object
	 * that is maintained in the session.
	 */
	public static IUser getUser (HttpServletRequest req) throws UserException {

		HttpSession session = req.getSession(false);

		// Always check to see if the user has a valid HttpSession
		// If a null session indicates that the user needs to be authenticated		
		if ( session == null )
			throw UserException.createFatal(UserException.SEC_ERROR_CODE_NO_SESSION,"Cannot retrieve a user without a valid session");

		// Get the user object from the session
		IUser user = (IUser) session.getAttribute(UF_SESSIONKEY_USER);
		// If non CRE user does not exist, then attempt to get CRE version.
		if (user == null){
			// Get CRE version of user object.
			user = UserFactory.getCUOUser(req);
		}
		
		return user;
	}
}
