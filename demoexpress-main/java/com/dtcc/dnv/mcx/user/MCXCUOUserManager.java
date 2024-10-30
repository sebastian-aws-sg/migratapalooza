package com.dtcc.dnv.mcx.user;

import javax.servlet.http.HttpServletRequest;

import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.security.model.DSCUOUser;
import com.dtcc.dnv.otc.common.security.model.DSCUOUserManager;
import com.dtcc.dnv.otc.common.security.model.ExUser;
import com.dtcc.dnv.otc.common.signon.DSSignOnTO;
import com.dtcc.dnv.otc.common.signon.DSSignonManager;
import com.dtcc.sharedservices.cwf.exceptions.CwfException;
import com.dtcc.sharedservices.cwf.exceptions.CwfNoUserException;

/*
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
 * 
 * @author Kevin Lake
 * @version 1.0
 * Date: September 05, 2007
 */
public class MCXCUOUserManager extends DSCUOUserManager {
	
	// Static constants
	private static final String _class = MCXCUOUserManager.class.getName();
	
	// Static members
	private final static MessageLogger log = MessageLogger.getMessageLogger(MCXCUOUserManager.class.getName());
	
	// Errors
	private static final String EX_MESSAGE_INV_USERTYPE = "Invalid user type identified.";
	private static final String EX_MESSAGE_CMPY_NOINIT = "User Company Information Not Initialized.";	
	private static final String EX_MESSAGE_USER_NOID = "User Information Not Initialized.";
	
    /**
     * @see com.dtcc.dnv.otc.common.security.model.DSCUOUserManager#createDSCUOUser(javax.servlet.http.HttpServletRequest, com.dtcc.sharedservices.security.common.CUOUser)
     */
    protected DSCUOUser createDSCUOUser(HttpServletRequest req, ExUser cuoUser) 
    	throws CwfNoUserException, UserException{
    	return this.createMCXCUOUser(req, cuoUser);
    }
    
	/**
     * @param req HttpServletRequest object used to retrieve some user information
     * @param cuoUser ExUser user object
     * @return MCXCUOUser user object
     * @throws CwfException Exception occurred trying to create a ExUser user object
     */
    protected MCXCUOUser createMCXCUOUser(HttpServletRequest req, ExUser exUser) 
    	throws CwfNoUserException, UserException{
    	MCXCUOUser mcxUser = null;
    	try{
    		//HttpSession session = req.getSession(true);
    		DSSignOnTO signonTO = DSSignonManager.getSignonTO(req);  
	        
	        if (exUser != null) {
	        	log.info("CUOUser object from isolation layer: " + exUser);
	        	
	            // Now we need to add the cwfapp specific values to the user object.
	        	mcxUser = new MCXCUOUser(exUser);
	        	
	            // Manage information that is in the sigon module transfer object
	            manageSignonInfo(mcxUser, signonTO);
	        	
                // Manage company level details
	            manageCompanyDetails(mcxUser, signonTO);
	            
	            // Manage user level entitlements.
	            manageEntitlements(mcxUser);
	            
	            // Manage MCA-Xpress User GUID
	            manageMCXUserGUID(mcxUser, signonTO);
	            
	        }
        }
        catch (Exception e) {
        	// We will catch this exception and wrap it in a CWF exception (runtime exception)
    		throw new CwfException("Exception: while get user from isolation layer: " + e.getMessage());
        }
        return mcxUser;
    }
    
    /**
     * @param mcxUser
     * @param signonTO
     * @throws UserException
     */
    protected void manageSignonInfo(MCXCUOUser mcxUser, DSSignOnTO signonTO)
		throws UserException{
		if (signonTO != null){
	    	// Set usertype
	        String userType = (String)signonTO.getValue(USERTYPES_IND);
	        
	        // Process if value exist (which always should be the case.
	        if (userType != null && userType.trim().length() > 0){
	        	
	        	// Set Super User Indicator
		        if (userType.equalsIgnoreCase(UserConstants.SUPER_IND)){
	        		mcxUser.setSuperUser(true);	        	
		        }
		        
		        // Set Dealer Indicator
	        	if (userType.equalsIgnoreCase(UserConstants.DEALER_IND)){
	        		mcxUser.setDealer(true);	        	
		        }
	        	
	        	// Set Client Indicator
	        	if (userType.equalsIgnoreCase(UserConstants.CLIENT_IND)){
	        		mcxUser.setClient(true);	        	
		        }
	        	
	        	// Set Template Admin Indicator
	        	if (userType.equalsIgnoreCase(UserConstants.TMPLT_ADMIN_IND)){
	        		mcxUser.setTemplateAdmin(true);	        	
		        }
	        	
	        } else {
	        	throw UserException.createInfo(UserException.SEC_ERROR_CODE_BAD_OPERATION, 
	        			                       EX_MESSAGE_INV_USERTYPE);
	        }
		}
    }
    
    /**
     * @param req
     * @param mcxUser
     * @throws UserException
     */
    private void manageCompanyDetails(MCXCUOUser mcxUser, DSSignOnTO signonTO)
		throws UserException {
    
    	// Get firm details    	
    	if(mcxUser.isSuperUser()) {
    		
    		// Get user company bean from transfer object
    		UserCompanyBean userCompany = (UserCompanyBean)signonTO.getAttribute(UserConstants.MCX_USER_COMPANY);
    		
    		if(userCompany != null) {
    			
    			// Set company id. Super users assigned company id will be overriden with selected value    			
	    		mcxUser.setUserCompanyId(userCompany.getUserCompanyId());	    		
	    		
	    		// Set company name
	    		mcxUser.setUserCompanyName(userCompany.getUserCompanyName());
	    		
	    		// Set firm type 
	    		if(userCompany.getUserCompanyTypeInd().equalsIgnoreCase(MCXConstants.COMPANY_TYPE_DEALER_CD)) {
	    		  mcxUser.setDealer(true);
	    	    } else if (userCompany.getUserCompanyTypeInd().equalsIgnoreCase(MCXConstants.COMPANY_TYPE_CLIENT_CD)) {
	    	      mcxUser.setClient(true);
	    		}
	    	    
	    	    // Set internal / external indicator
	    	    if(userCompany.getUserCompanyEnvInd().equalsIgnoreCase(MCXConstants.COMPANY_STAT_INT_IN_CD)) {
	    	      mcxUser.setExternalFirm(false);
    		    } else if (userCompany.getUserCompanyEnvInd().equalsIgnoreCase(MCXConstants.COMPANY_STAT_EXT_IN_CD)) {
    		      mcxUser.setExternalFirm(true);
	    	    }	
    		    
    		} else {
    		  throw UserException.createInfo(UserException.SEC_ERROR_CODE_BAD_OPERATION, 
    		  		                         EX_MESSAGE_CMPY_NOINIT);
    		}
    	} else if (mcxUser.isDealer() || mcxUser.isClient()) {
    		
            // Get user company bean from transfer object
    		UserCompanyBean userCompany = (UserCompanyBean)signonTO.getAttribute(UserConstants.MCX_USER_COMPANY);
    		
    		if(userCompany != null) {
    			
                // Set company id    			
	    		mcxUser.setUserCompanyId(userCompany.getUserCompanyId());
    			
	            // Set company name
	    		mcxUser.setUserCompanyName(userCompany.getUserCompanyName());
	    		
	            // Set internal / external indicator
	    		if(userCompany.getUserCompanyEnvInd().equalsIgnoreCase(MCXConstants.COMPANY_STAT_INT_IN_CD)) {
		    	  mcxUser.setExternalFirm(false);
	  		    } else if (userCompany.getUserCompanyEnvInd().equalsIgnoreCase(MCXConstants.COMPANY_STAT_EXT_IN_CD)) {
	  		      mcxUser.setExternalFirm(true);
		    	}
  		        	  		    
    		} else {
      		  throw UserException.createInfo(UserException.SEC_ERROR_CODE_BAD_OPERATION, 
      		  	                             EX_MESSAGE_CMPY_NOINIT);
            }
    	} else if (mcxUser.isTemplateAdmin()) {
    		
    		UserCompanyBean userCompany = (UserCompanyBean)signonTO.getAttribute(UserConstants.MCX_USER_COMPANY);    		
    		if(userCompany != null) {    			
    			/* 
    			 * Default internal company id for internal users. This was done
    			 * because internal id's are different in every region, so instead
    			 * of having representations of every company id in the company 
    			 * table for every region a default company was created referred 
    			 * to by all internal users.
    			 */    			
	    		mcxUser.setUserCompanyId(UserConstants.DTCC_INTERNAL_COMPANYID);	    		
    		}
    	}
    }
   
    /**
     * Setup role base entitlements.
     * 
     * @param req HttpServletRequest object
     * @param mcxUser DSVCUOUser user object
     * @throws UserException
     */
    protected void manageEntitlements(MCXCUOUser mcxUser)
    	throws UserException{
    	
    	// Set current system to mcx if the user has the correct role.
    	if (mcxUser.hasRole(UserConstants.MCX_PRODUCT_ROLE)){
    		mcxUser.setCurrentSystem(UserConstants.MCX_PRODUCT_ROLE.toLowerCase());
    	}
    	   	
    	/* 
    	 * Set the user type entitlements based on their respective roles. For external 
    	 * firms, super users will be forced to have inquiry rights only irregardless
    	 * if user is provisioned with update rights. For internal firms, user will 
    	 * have what was provisioned.
    	 */    	
    	if(mcxUser.isSuperUser()){
    		if(mcxUser.isExternalCompany()) {
    		  mcxUser.setHasInquiry(true);          	  	
    		  mcxUser.setHasUpdate(false);
    		} else {
    		  mcxUser.setHasInquiry(mcxUser.hasRole(UserConstants.SUPERUSER_INQUIRY_ROLE));
        	  mcxUser.setHasUpdate(mcxUser.hasRole(UserConstants.SUPERUSER_UPDATE_ROLE));
    		}
    	} else if (mcxUser.isDealer()) {
    	  mcxUser.setHasInquiry(mcxUser.hasRole(UserConstants.DEALER_INQUIRY_ROLE));
          mcxUser.setHasUpdate(mcxUser.hasRole(UserConstants.DEALER_UPDATE_ROLE));        	    		  		
    	} else if (mcxUser.isClient()) {
    	  mcxUser.setHasInquiry(mcxUser.hasRole(UserConstants.CLIENT_INQUIRY_ROLE));
          mcxUser.setHasUpdate(mcxUser.hasRole(UserConstants.CLIENT_UPDATE_ROLE));                		
    	} 
    	
        // Set Template Administrator entitlement based on the respective role. Assumed update acl.
    	if(mcxUser.isTemplateAdmin()) 
    		mcxUser.setHasUpdate(true);    	    		
    	    	
    	super.manageEntitlements(mcxUser);
    }   
    
    /**
     * Set MCX User GUID for all users
     * @param mcxUser
     * @param signonTO
     * @throws UserException
     */
    private void manageMCXUserGUID(MCXCUOUser mcxUser, DSSignOnTO signonTO) throws UserException {
    	
	  // Get user GUID from transfer object
	  String mcxUserGuid = (String)signonTO.getValue(UserConstants.MCX_USERGUID_WORKFLOW);
	  
	  if(mcxUserGuid == null || mcxUserGuid.trim().length() == 0) {
	  	throw UserException.createInfo(UserException.SEC_ERROR_CODE_BAD_OPERATION, 
	  			                       EX_MESSAGE_USER_NOID);
	  } else {
	  	// Set MCA-Xpress User GUID
	  	mcxUser.setMCXUserGUID(mcxUserGuid);
	  }
	  
    }

}
