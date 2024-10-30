package com.dtcc.dnv.otc.common.security.model;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.dtcc.sharedservices.cwf.exceptions.CwfException;
import com.dtcc.sharedservices.cwf.exceptions.CwfNoUserException;
import com.dtcc.sharedservices.cwf.security.CUOUserManagerImpl;
import com.dtcc.sharedservices.cwf.security.UserManager;
import com.dtcc.sharedservices.security.common.CUOUser;
import com.dtcc.sharedservices.security.common.NoUserException;
import com.dtcc.sharedservices.security.common.UserGenerator;
import com.dtcc.sharedservices.security.common.UserGeneratorInterface;

/**
 * Copyright (c) 2006 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date August 14, 2007
 * @version 1.0
 * 
 * This class is used as a factory manager for creating a ExUser.
 * 
 * @version	1.1				October 16, 2007			sv
 * Updated with trimming logic for the roles.
 * 
 * @version	1.2				October 18, 2007			sv
 * Updated logger to be static final.
 */
public class ExCUOUserManager implements UserManager {
	
	// Static members
	private static final String _class = ExCUOUserManager.class.getName();
	private static final String ROLES_DELIM = ",";
	private static String userRoles ="";
	
	/**
	 * This class implements UserManager. It interacts with isolation layer
	 * get CUOUser Object and puts it in session.
	 *
	*/
    private static final Logger logger = Logger.getLogger(ExCUOUserManager.class);

    /**
     * userGenerator is the interface to the object defined in CSF.  It is
     * specified here with the default to be the abstract factory implementation
     * UserGenerator that uses property files to specify the classes to load.
     * If desired, this value may be integrated via Spring by specifying the
     * userGenerator properety in the CUOUserGenerator object.
     */
    private UserGeneratorInterface userGenerator = new UserGenerator();
   

	/**
	 * Default constructor for user manager that do not care to manage roles
	 * from ExUser
	 */
	public ExCUOUserManager(){
	}
    
	/**
	 * Cconstructor used to load user roles needed in user manager.
	 * 
	 * @param roles
	 */
	public ExCUOUserManager(String roles){
		userRoles = roles;
	}
    
    /**
     * This method returns the CUOUser object for this UserManager
     * 
     * @see com.dtcc.sharedservices.cwf.security.UserManager#getCUOUser(javax.servlet.http.HttpServletRequest)
     */
    public CUOUser getCUOUser(HttpServletRequest req) {
    	return this.getExCUOUser(req);
    }

    /**
     * This method has default access to allot only this class to access this implemetation.
     * This class will get the DSCUOUser by either creating one if it does not exist, generate
     * one based on CUOUser, or return one that was already created.
     * 
     * @param req HttpServletRequest object used to retrieve some user information
     * @return ExUser user object
     * @throws CwfException Exception occurred trying to get DSCUOUser user object
     */
    private ExUser getExCUOUser(HttpServletRequest req) throws CwfException{
    	ExUser exUser = null;
    	CUOUser cuoUser = null;

        HttpSession session = req.getSession(false);
        if (session == null) {
            // Make a new session.
            session = req.getSession(true);
            if (session == null) {
                throw new CwfException("Unable to make session for " + "ExCUOUserManager.getExCUOUser.");
            }
        }

        // Try to get the user from the session.
        cuoUser = (CUOUser) session.getAttribute(CUOUserManagerImpl.CUO_SESSION_NAME);
        if (cuoUser == null) {
        	// If in test mode this will be an ExUser if using the DSTestUserGenerator
        	// Otherwise it will be CUOUser
        	cuoUser = createCUOUser(req);
	        // Generate ExUser based on CUOUser
        	exUser = new ExUser(cuoUser);
        	createRoles(exUser, this.getUserRoles(), req);
            session.setAttribute(CUOUserManagerImpl.CUO_SESSION_NAME, exUser);
        }
        // if the user has already been formed then just cast it for return
        else if (cuoUser instanceof ExUser){
            	exUser = (ExUser) cuoUser;
        }
        return exUser;
    }

    /**
     * @param req HttpServletRequest object used to retrieve some user information
     * @return CUOUser user object
     * @throws CwfException Exception occurred trying to create a CUOUser user object
     */
    private CUOUser createCUOUser(HttpServletRequest req) throws CwfNoUserException{
    	CUOUser cuoUser = null;
    	// Need to create new user.
        try {
        	// Generate CUOUser
            cuoUser = userGenerator.getUser(req);
        }
        catch (NoUserException e) {
            // We will catch this exception and wrap it in a CWF exception (runtime exception)
            throw new CwfNoUserException("Unable to get user from isolation layer");
        }
        catch (Exception e) {
        	// We will catch this exception and wrap it in a CWF exception (runtime exception)
    		throw new CwfException("Exception: " + e.getMessage() + " in get user from " + "isolation layer");
        }
        return cuoUser;
    }
    
    /**
     * Method to create all the roles respective to this user manager.
     * 
     * @param user ExUser object.
     * @param userRoles String value of roles, comma delimted.
     * @param req HttpServletRequest request object.
     */
    protected final void createRoles(ExUser user, String userRoles, HttpServletRequest req){
    	if (this.getUserRoles() != null){
    	   	StringTokenizer st = new StringTokenizer(userRoles, ROLES_DELIM);
    	   	while (st.hasMoreElements()){
    	   		String roleName = (String)st.nextElement();
    	   		user.hasRole(roleName.trim(), req);
    	   	}
    	}
    }
     
	/**
	 * @return Returns the userRoles.
	 */
	private String getUserRoles() {
		return userRoles;
	}
}
