package com.dtcc.dnv.otc.common.security.model;

import java.util.Hashtable;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.dtcc.sharedservices.security.common.CUOUser;
import com.dtcc.sharedservices.security.common.User;
import com.dtcc.sharedservices.security.common.UserGeneratorInterface;

/**
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author vchernov
 * @date Nov 24, 2006
 * @version 1.0
 * 
 * @version	1.1				April 19, 2007			sv
 * Updated default value for _roleList to "".
 * 
 * @version	1.2				May 16, 2007			sv
 * Updated getUser() to use the super class's getUser() and then use BeanUtils
 * to copy the properties.
 * 
 * @version	1.3				May 17, 2007			sv
 * Corrected Logger class name.
 * 
 * @version	1.4				July 21, 2007			sv
 * Updated  getUser(HttpServletRequest req) to support DSRoleList implementation.  Added
 * javadoc and comments.
 * 
 * @version	1.5				August 14, 2007			sv
 * Updated to use new ExUser implementation.
 * 
 * @version	1.6				October 18, 2007		sv
 * Updated logger to be static final.
 * 
 * @version	1.7				October 20, 2007		sv
 * Updated to stop extending CSF TestUserGenerator as it changes.  Added
 * real implementation in this class.
 */
public class DSTestUserGenerator implements UserGeneratorInterface {
	
	// Static members.
	private static final Logger logger = Logger.getLogger(DSTestUserGenerator.class);
	private static final String DEFAULT_TOKEN = ",";
	
    // Used to check if the read of the property file has been done.
    protected static boolean _initialized = false;

    protected static String _userId = "DEFAULT_USERID";
    protected static String _userFirstName = "DEFAULT_USERFIRSTNAME";
    protected static String _userLastName = "DEFAULT_USERLASTNAME";
    protected static String _emailAddress = "DEFAULT_EMAIL";
    protected static String _GUID = "DEFAULT_GUID";
    protected static String _firmId = "DEFAULT_FIRMID";
    protected static String _participantId = "DEFAULT_PARTICIPANTID";
    protected static String _originatorCode = "DEFAULT_ORIGINATORCODE";
    protected static String _entitlementId = "DEFAULT_ENTITLEMENTID";
	protected static String _companyName = "DEFAULT_COMPANYNAME";
	protected static String _companyId = "DEFAULT_COMPANYID";
	protected static String _phoneNumber = "DEFAULT_PHONENUMBER";
	protected static String _RACFid = "DEFAULT_RACFID";
	protected static Vector _RACFidList = new Vector();
    private static String _propertyFileName = "TestUser";
	protected static String _roleList = "";
	/**
	 * Empty constructor
	 */
	public DSTestUserGenerator() {
		super();
	}

	/**
	 * Initilized proerties.
	 * 
	 * @see com.dtcc.csf.userauth.test.TestUserGenerator#initProperties()
	 */
	protected static void initProperties() throws com.dtcc.sharedservices.security.common.NoUserException {
		PropertyResourceBundle testUserProperties = null;
        try {
        	// Get this generator specific properties.
            testUserProperties = (PropertyResourceBundle) PropertyResourceBundle.getBundle(getPropertyFileName());
            _userId = getString(testUserProperties, "TestUserGenerator.userId");
            _userFirstName = getString(testUserProperties, "TestUserGenerator.userFirstName");
            _userLastName = getString(testUserProperties, "TestUserGenerator.userLastName");
            _emailAddress = getString(testUserProperties, "TestUserGenerator.emailAddress");
            _GUID = getString(testUserProperties, "TestUserGenerator.GUID");
            _firmId = getString(testUserProperties, "TestUserGenerator.firmId");
            _participantId = getString(testUserProperties, "TestUserGenerator.participantId");
            _originatorCode = getString(testUserProperties, "TestUserGenerator.originatorCode");
            _entitlementId = getString(testUserProperties, "TestUserGenerator.entitlementId");
			_companyName = getString(testUserProperties, "TestUserGenerator.companyName");
			_companyId = getString(testUserProperties, "TestUserGenerator.companyId");
			_phoneNumber= getString(testUserProperties, "TestUserGenerator.phoneNumber");
			_RACFid= getString(testUserProperties, "TestUserGenerator.RACFid");
			_RACFidList.add(_RACFid);
			//read role list
			_roleList = getString(testUserProperties, "TestUserGenerator.roleList");	
            _initialized = true;
        } 
        catch (Exception e) {
        	logger.error("Failed to load  Properties file " + testUserProperties + ".   Reason[" + e + "].   " + "Be sure " + getPropertyFileName() + " is located correctly.");
            throw new com.dtcc.sharedservices.security.common.NoUserException("TestUserGenerator " + getPropertyFileName() + " exception: " + e.getMessage());
        }
	}
    /**
     * Creates and returns the test user.  For a test user, the information is pass in as a part of the request.
     * @return CUOUser  - returns a user object with those fields filled in.
     * @param req javax.servlet.http.HttpServletRequest request - the  servlet request to process.
     * @throws java.lang.Exception.  May through a MissingParameterException.  Others may come from called methods.
     */
    public CUOUser getUser(HttpServletRequest req) throws com.dtcc.sharedservices.security.common.NoUserException, java.lang.Exception {
        // Initialize if not yet done.
        if (_initialized == false) {
            initProperties();
        }
        // Get the user object.
        User cuoUser = new User();
        // Put in the values for the test user.
        cuoUser.setUserId(_userId);
        cuoUser.setUserFirstName(_userFirstName);
        cuoUser.setUserLastName(_userLastName);
        cuoUser.setEmailAddress(_emailAddress);
        cuoUser.setGUID(_GUID);
        cuoUser.setEntitlementId(_entitlementId);
        cuoUser.setCompanyId(_companyId);
        cuoUser.setCompanyName(_companyName);
        cuoUser.setPhoneNumber(_phoneNumber);
        cuoUser.setRACFAttributeList(_RACFidList);
    	// Extended common user
        ExUser user = new ExUser(cuoUser);
		
        // Populate role list
		if (_roleList != null){
			// Create Hashtable of role permissions
			Hashtable rolesListHT = new Hashtable();
			StringTokenizer tok = new StringTokenizer(_roleList, DEFAULT_TOKEN);
			// Iterate through role permissions
			while (tok.hasMoreElements()){
				// Add TRUE permission to hashtable for every token found.
				rolesListHT.put(tok.nextToken().trim(),Boolean.TRUE);
			}
			// Instantiate the role list.
			DSRoleList roleList = new DSRoleList(rolesListHT);
			// Set the role list in the user object.
			user.setRoleList(roleList);
		}
        return user;
    }
    
    /**
     * Utility method to get a resource from the resource bundle.  Handles exceptions
     * and returns null if the value is not found.
     * 
     * @param bundle PropertyResourceBundle object containing user properties
     * @param name String value to look for.
     * @return String value for name found.
     */
    private static String getString(PropertyResourceBundle bundle, String name){
    	String value = null;
    	try{
    		value = bundle.getString(name);
    	}
    	catch(Throwable t){
    		//ignore, because the entries are optional.
    	}
    	return value;
    }
    
    /**
     * Returns the property file that this class read from.
     * @return java.lang.String  property file name.
     */
    protected static String getPropertyFileName() {
        return _propertyFileName;
    }

    /**
     * Sets the property file name to use.
     * @param newPropertyFileName java.lang.String.  This is the name of the property file.
     */
    protected static void setPropertyFileName(String newPropertyFileName) {
        _propertyFileName = newPropertyFileName;
    }
}
