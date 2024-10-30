package com.dtcc.dnv.otc.common.security.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.exception.XMLDatabaseException;
import com.dtcc.dnv.otc.common.signon.DSSignOnTO;
import com.dtcc.dnv.otc.common.signon.DSSignonManager;
import com.dtcc.dnv.otc.common.signon.entitlement.UFOBean;
import com.dtcc.dnv.otc.legacy.CounterpartyIdName;
import com.dtcc.sharedservices.cwf.exceptions.CwfException;
import com.dtcc.sharedservices.cwf.exceptions.CwfNoUserException;
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
 * @date May 17, 2006
 * @version 1.0
 * 
 * This class is used as a factory manager for creating a DSCUOUSer.
 * 
 * Rev	1.1				July 20, 2006			sv
 * Updated commelnts and some naming variable naming conventions.
 * 
 * Rev	1.2				July 21, 2006			sv
 * Reorganized the code, and added functionality to support CUOUser
 * already existing.
 * 
 * @version 	1.3		Arpil 6, 2007			sv
 * Removed manageRoles() as it is no longer required.  The roles will be managed by the user
 * object.
 * 
 * @version	1.4			July 21, 2007			sv
 * Removed getCUOUser(HttpServletRequest req, CUOUser cuoUser).  Made
 * getDSCUOUser(HttpServletRequest req) and 
 * getDSCUOUser(HttpServletRequest req, CUOUser cuoUser) private scope.
 * Added lookupFamily(DSCUOUser) and lookupCounterParty(DSCUOUser).
 * 
 * @version	1.5			July 25, 2007			sv
 * Added signon constants.  Updated getDSCUOUser() catch UserException 
 * and throw CwfException.  Updated other methods to throw UserException.
 * Cleaned up lookupFamily().  Added manageSignonInfo() as a utility method 
 * that is actually not called from this method but can be used by classes 
 * that extend it.
 * 
 * @version	1.6			July 27, 2007			sv
 * Updated manageSignonInfo() to manager the usertypes differently.
 * 
 * @version	1.7			August 7, 2007			sv
 * Updated createDSCUOUser() to remove the usage of BeanUtils.copyProperties().
 * Added APIs for entitlement layer but did not implement it yet.  Added
 * manageEntitlements().  Cleanup code and updated comments.  Added more constants.
 * 
 * @version	1.8			August 14, 2007			sv
 * Updates UPDATE_IND constant to be "D".
 * 
 * @version	1.9			August 15, 2007			sv
 * Updated manageSignonInfo() to only process if signonTO is not null.
 * 
 * @version	1.10		August 30, 2007			sv
 * Added functionality for OriginatoCode and UFO implementation.
 * 
 * @version	1.11		September 3, 2007		sv
 * Updated manageSignonInfo() to utilize UFOBean.
 * 
 * @version	1.12		September 24, 2007		sv
 * Added constant for session key attribute user object.  Changed implementation
 * to use this constant rather than CWF version to support future changes.
 * Added static method to remove user from session.  Updated
 * manageFamilyList().  Updated comments and javadoc.
 * 
 * @version	1.13		Septemeber 25, 2007		sv
 * Removed createFamilyList().  Added 
 * createDSCUOUser(HttpServletRequest req, DSCUOUser dsUser) to handle
 * application specfic user object that want the default DerivSERV 
 * behavior.  Updated createDSCUOUser(HttpServletRequest req, ExUser exUser)
 * to instantiate the DSCUOUser and pass it to the DSCUOUser overloaded
 * createDSCUOUser(). Updated javadoc and comments.
 * 
 * @version	1.14		October 17, 2007		sv
 * Updated the session key for the user object to be different than the 
 * CWF key.  Updated getDSCUOUser() to manage already existing application user
 * object in conjunction with using the new/different key for the session attribute
 * holding the user.
 * 
 * @version	1.15		October 18, 2007		sv
 * Updated logger to be static final.
 * 
 * @version	1.16		October 30, 2007		sv
 * Added static getUser() to get an already created user object.
 * 
 * @version	1.17		October 30, 2007		sv
 * Checked in for Cognizant.  Updated the logic to selct the ocode and 
 * entitlement for user role.
 */
public class DSCUOUserManager extends ExCUOUserManager {
	
	// Static members
	private static final String _class = DSCUOUserManager.class.getName();
	
	// Static signon constants.
	public static final String SIGNON_ENTITLEMENT = "entitlement";
	public static final String SIGNON_TEST_PROD_IND = "testProdIndicator";
	public static final String USERTYPES_IND = "usertypes";
	
	// Static user type constants
	public static final String USER_IND = "U";

	// Static entitlement constants
	public static final String INQUIRY_IND = "I";
	public static final String UPDATE_IND = "D";
	private static final String USER_SELECTED_OCODES = "selected-ocodes";
	
	// Generic constants
	public static final String UM_SESSIONKEY_USER = "DERIVSERV_USER_KEY";
	
	/**
	 * This class implements UserManager. It interacts with isolation layer
	 * get CUOUser Object and puts it in session.
	 *
	*/
    private static final Logger logger = Logger.getLogger(DSCUOUserManager.class);

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
	public DSCUOUserManager(){
		super();
	}
    
	/**
	 * Only constructor used to load user roles needed in user manager.
	 * 
	 * @param roles
	 */
	public DSCUOUserManager(String roles){
		super(roles);
	}
    
    /**
     * This method returns the CUOUser object for this UserManager
     * 
     * @see com.dtcc.sharedservices.cwf.security.UserManager#getCUOUser(javax.servlet.http.HttpServletRequest)
     */  
    public CUOUser getCUOUser(HttpServletRequest req) {
    	CUOUser user = super.getCUOUser(req);
    	DSCUOUser dsUser = null;
    	// If user is an instance of an ExUser, then try to create application specific user.
    	if (user instanceof ExUser){
    		ExUser exUser = (ExUser) user;
            try{
    	        // Generate DSCUOUser based on ExUser
    	        dsUser = getDSCUOUser(req, exUser);
            }
            catch(UserException ue){
            	throw new CwfException("Unable to create DSCUOUser for " + "DSCUOUserManager.getDSCUOUser.", ue); 
            }
    	}
    	else{
    		throw new CwfException("Ivalid user instance DSCUOUserManager.getDSCUOUser for " + user.getUserId());
    	}
    	return dsUser;
    }
    
    /**
     * @param req HttpServletRequest object used to retrieve some user information
     * @param exUser CUOUser object used to create the DSCUOUser
     * @return DSCUOUser user object
     * @throws CwfException Exception occurred trying to get DSCUOUser user object
     */
    private DSCUOUser getDSCUOUser(HttpServletRequest req, ExUser exUser) 
    	throws CwfException, UserException{
    	DSCUOUser dsUser = null;
    	Object user = null;
    	// Get reference to session.
        HttpSession session = req.getSession(false);
        if (session == null) {
            // Make a new session.
            session = req.getSession(true);
            if (session == null) {
                throw new CwfException("Unable to make session for " + "DSCUOUserManager.getDSCUOUser.");
            }
        }
    	// Try to look for existing application user object.  This is not the CWF user object (i.e. CUOUser)
        user = session.getAttribute(DSCUOUserManager.UM_SESSIONKEY_USER);
        // If either the exUser or the application specific user object does NOT exist, then thrown an exception.
    	if (exUser == null && user == null) {
    		throw new CwfException("Unable to generate DSCUOUser from CUOUser DSCUOUserManager.getDSCUOUser()");
        }
    	// If the user object is in fact the application specific user object, then just cast it appropriately.
    	if (user instanceof DSCUOUser){
    		dsUser = (DSCUOUser) user;
    	}
        // if the exUser has already been formed then just cast it for return
        else if (exUser instanceof DSCUOUser){
        	dsUser = (DSCUOUser) exUser;
        }
        // If a generic CUOUser object was created, then form the DSCUOUser from it.
        else{
        	dsUser = createDSCUOUser(req, exUser);
            dsUser.setSessionId(session.getId());
            // Set user back in session.
            session.setAttribute(DSCUOUserManager.UM_SESSIONKEY_USER, dsUser);
        }
        return dsUser;
    }
    
    /**
     * Static method to remove the user object.
     * 
     * @param req HttpServletRequest object.
     */
    public static void removeUser(HttpServletRequest req) {
    	// Remove user from the session.
    	req.getSession(true).removeAttribute(DSCUOUserManager.UM_SESSIONKEY_USER);
    }
    
    /**
     * Static method to get the already created user object.  This method will 
     * NOT create the user if it does not exist.
     * 
     * @param req HttpServletRequest object.
     */
    public static DSCUOUser getUser(HttpServletRequest req) 
    	throws UserException{
    	// Get user from the session.
    	DSCUOUser user = (DSCUOUser) req.getSession(true).getAttribute(DSCUOUserManager.UM_SESSIONKEY_USER);
    	if (user == null){
    		throw UserException.createInfo(UserException.SEC_ERROR_CODE_BAD_OPERATION, "User object is not available.");
    	}
    	return user;
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
     * This method creates a user by updating the provided DSCUOUser object.
     * Generally this method can be used if the default DerivSERV behavior want to 
     * utilized and the application specific user object is of type DSCUOUser.
     * If the default user object (DSCUOUser) can be used then there is no need
     * to overload this method.
     * 
     * @param req HttpServletRequest object used to retrieve some user information
     * @param DSCUOUser dsUser user object
     * @throws CwfException Exception occurred trying to create a ExUser user object
     * @throws UserException Exception occurred while trying to create DSCUOUser.
     */
    protected void createDSCUOUser(HttpServletRequest req, DSCUOUser dsUser)
    	throws CwfNoUserException, UserException{
    	DSSignOnTO signonTO = DSSignonManager.getSignonTO(req);
    	if (dsUser != null) {
        	logger.info("CUOUser object from isolation layer: " + dsUser);
            // Now we need to add the cwfapp specific values to the user object.
            // Manage information that is in the sigon module transfer object
            manageSignonInfo(dsUser, signonTO);
            // Manage user level entitlements.
            manageEntitlements(dsUser);
            // LOok up family information
            manageFamilyList(dsUser);
    		// Lookup Counterparty information.
            manageCounterPartyList(dsUser);
        }
    }
    
    /**
     * This methods creates a user by instantiating a DSCUOUser and calling the overloaded
     * method for DSCUOUser objects.  Generally this method can be overriden when 
     * the intention is to utilize the default DerivSERV behavior but with a 
     * application specific user object (i.e. DSVCUOUser)
     * 
     * @param req HttpServletRequest object used to retrieve some user information
     * @param exUser ExUser user object
     * @return DSCUOUser user object
     * @throws CwfException Exception occurred trying to create a ExUser user object
     * @throws UserException Exception occurred while trying to create DSCUOUser.
     */
    protected DSCUOUser createDSCUOUser(HttpServletRequest req, ExUser exUser) 
    	throws CwfNoUserException, UserException{
   		HttpSession session = req.getSession(true);
   		// get transfer object
   		DSSignOnTO signonTO = DSSignonManager.getSignonTO(req);
   		// Instantiate DSCUOUser
   		DSCUOUser dsUser = new DSCUOUser(exUser);
   		// // Create user
        this.createDSCUOUser(req, dsUser);
        return dsUser;
    }
    
	/**
	 * Method manageFamilyList
	 * @param nothing
	 * @return nothing
	 * @throws UserException
	 * 
	 * Call the DAO to retrieve the family list from the database
	 */
    protected void manageFamilyList(DSCUOUser dsUser) throws UserException{
    	// Local members
    	CounterpartyIdName[] familyArrayList;
		String operatorId = dsUser.getGUID();
		String originatorCode = dsUser.getOriginatorCode();
		String participantId = dsUser.getParticipantId();
		String dtccPartyId = dsUser.getDTCCPartyId();
		String sCurrentSystem 	= dsUser.getCurrentSystem().toUpperCase();
		// local collections
		Vector prodTypeList = new Vector();
		Vector orgList = new Vector();	
		Vector moduleList = new Vector();

		try {
			// Setup business delegate for attaining family list information.
			SignonDelegate delegate = new SignonDelegate();
			SignonRequest request = SignonRequest.familyForNonBranch(dsUser.getAuditInfo(), originatorCode, sCurrentSystem, dsUser.getProdIndicator());
			SignonResponse response = (SignonResponse) delegate.processRequest(request);
			// Retrieve the response.
			familyArrayList = response.getList();
			prodTypeList = response.getProductTypeList();				
			moduleList = response.getModuleList();
			
			// For NON-BRANCH users, the participant id is set if there is only one participant in the family
			if ( familyArrayList.length == 1 ){
				dsUser.setParticipantId(familyArrayList[0].getCounterpartyId());
				dsUser.setParticipantName(familyArrayList[0].getCounterpartyName());		
				dsUser.setDTCCPartyId(familyArrayList[0].getDTCCpartyId());
			}
			// Set the participant type.
			dsUser.setParticipantType(familyArrayList[0].getParticipantType());

			// Finally set the familyList instance variable
			dsUser.setFamilyList(familyArrayList);
			dsUser.setProducts(prodTypeList);
			dsUser.setModules(moduleList);		
		} 
		catch (BusinessException be) {
			UserException ue = UserException.createFatal(UserException.SEC_ERROR_CODE_DB_ERROR, be.getMessage());
			ue.setThrowingClass(_class);
			ue.setThrowingMethod("manageFamilyList()");
			throw ue;
		} 
		catch (ArrayIndexOutOfBoundsException abe) {
			// This exception is thrown when familyList[0] is accessed
			throw UserException.createInfo(UserException.SEC_ERROR_CODE_NO_RESULT, "No Family List found for user "+ dsUser.getUserId());
		} 
		catch (NullPointerException npe) {
			// This exception is thrown when familyList[0] is NULL
			throw UserException.createInfo(UserException.SEC_ERROR_CODE_NO_RESULT, "No Family List found for user "+ dsUser.getUserId());
		}
    }

	/**
	 * Method manageCounterPartyList
	 * @param nothing
	 * @return nothing
	 * @throws UserException
	 * 
	 * Call the DAO to retrieve the counter party list from the database
	 */
    protected void manageCounterPartyList(DSCUOUser dsUser) throws UserException {
		CounterpartyIdName[] cpList;

		//get Counterparty Array from Database
		//We do not get the counterparty for Branch because we don't know the eight digit participant
		
		try {
			// If there is no default participant Id, use one from the family list
//			String partId = this.getParticipantId();
//			if ( partId == null || partId.length() == 0 )
			String partId = dsUser.getFamilyList()[0].getCounterpartyId();

			// added 06/14/2005
			//If there is no default dtccId, use one from the family list
//			String dtccpartId = this.getDTCCPartyId();
//			if ( dtccpartId == null || dtccpartId.length() == 0 )
			String dtccpartId = dsUser.getFamilyList()[0].getDTCCpartyId();

			cpList = CounterPartyListHandler.getInstance().getCounterPartyList(dsUser.getProdIndicator());
			
			if ( cpList.length == 0 ) {
				throw UserException.createInfo(UserException.SEC_ERROR_CODE_NO_RESULT, "No Counter Parties found for user "+ dsUser.getUserId());
			} 
			else {
				dsUser.setCounterPartyList(cpList);	// store the user's counter party list
			}
		}
		catch (Exception be) {
			UserException ue = UserException.createFatal(UserException.SEC_ERROR_CODE_DB_ERROR, be.getMessage());
			ue.setThrowingClass(_class);
			ue.setThrowingMethod("manageCounterPartyList()");
		}
    }
    
    /**
     * Manage information that comes from the sign on modules.
     * 
     * @param dsUser User object
     * @param signonTO Transfer object from signon modules.
     */
    protected void manageSignonInfo(DSCUOUser dsUser, DSSignOnTO signonTO)
    	throws UserException{
    	if (signonTO != null){
	    	// Set test/production indicator
	        dsUser.setProdIndicator((String)signonTO.getValue(SIGNON_TEST_PROD_IND));
	        // Set usertype (superuser vs user).
	        String userType = (String)signonTO.getValue(USERTYPES_IND);
	        // Process if value exist (which always should be the case.
	        if (userType != null && userType.trim().length() > 0){
		        // If the usertype is U, then it is a normal user
	        	if (userType.equalsIgnoreCase(USER_IND)){
		        	dsUser.setSuperUser(false);
		        	// Set originator code. The Entitlement value will have oCode_EntitlementCode. Anyway check for EntitlementCode
		        	String signonEntitlementValue = (String)signonTO.getValue(SIGNON_ENTITLEMENT);
		        	// get collection of ocodes
		        	ArrayList selectedOCodes = (ArrayList)signonTO.getAttribute(USER_SELECTED_OCODES);

					UFOBean selectedUFOBean = null;
					// If there is only one UFO selected, then use it.
					if(selectedOCodes.size() == 1){
						selectedUFOBean = (UFOBean)selectedOCodes.get(0);
					}
					// 
					else{
						// There is a possibility that there are more than one UFOBeans.
						// This will manage the entitlement heirarchy for a selected ocode.
						Iterator OCodeListIter = selectedOCodes.iterator();
						boolean ufoFound = false;
						while(OCodeListIter.hasNext() && !ufoFound){
							UFOBean ufoBean = (UFOBean)OCodeListIter.next();
							if(ufoBean.getEntitlement().equalsIgnoreCase("U")){
								selectedUFOBean = ufoBean;
								ufoFound = true;
							}
							else if(ufoBean.getEntitlement().equalsIgnoreCase("I")){
								selectedUFOBean = ufoBean;
							}
							else{
								throw UserException.createFatal(UserException.SEC_ERROR_CODE_ENTITLEMENT, "Invalid entitlement found.");
							}
						}
					}
					dsUser.setOriginatorCode(selectedUFOBean.getOCode());
	        		dsUser.setHasInquiry(selectedUFOBean.getEntitlement().equalsIgnoreCase("I"));
	        		dsUser.setHasUpdate(selectedUFOBean.getEntitlement().equalsIgnoreCase("U"));
		        }
	        	// If it is a superuser, this field will be populated with the entered account id.
	        	else{
		        	dsUser.setSuperUser(true);
			        // Set originator code
			    	dsUser.setOriginatorCode((String)signonTO.getValue(SIGNON_ENTITLEMENT));
		        }
	        }
	        else{
	        	throw UserException.createInfo(UserException.SEC_ERROR_CODE_BAD_OPERATION, "Invalid user type identified.");
	        }
    	}
    }
    
    /**
     * Method to manage signon entitlements.
     * 
     * @param DSCUOUser dsCUOUser user object
     * @throws UserException Exception occurred while managing entitlements.
     */
    protected void manageEntitlements(DSCUOUser dsCUOUser) throws UserException{
    	// Systems permitted to access
    	Vector accessSystems = new Vector();
    	 // entitlements permitted to access
    	Vector entitlementValues = new Vector();
    	// Add respective systems
   		accessSystems.add(dsCUOUser.getCurrentSystem().toLowerCase());
   		// user has inquiry, then add entitlement
    	if (dsCUOUser.isInquiryOnly())
    		entitlementValues.add(INQUIRY_IND);
    	// If user has update then entitlement
		if (dsCUOUser.isUpdateAllowed())
			entitlementValues.add(UPDATE_IND);

		try {
			// Crete request
	    	IEntitlementRequest eRequest = new ResourceEntitlementRequest(accessSystems, entitlementValues);
	    	// Set entitlements
			dsCUOUser.setEntitlements(EntitlementFactory.getEntitlements(eRequest));
		} 
		catch (XMLDatabaseException e) {
			throw UserException.createInfo(UserException.SEC_ERROR_CODE_NO_RESULT, "No Entitlement for this user: " + dsCUOUser.getUserId());
		}
		// Validate system access.
		dsCUOUser.validateSystemAccess(dsCUOUser.getCurrentSystem().toLowerCase());
    }
}
