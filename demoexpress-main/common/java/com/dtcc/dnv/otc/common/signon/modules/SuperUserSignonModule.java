package com.dtcc.dnv.otc.common.signon.modules;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;
import com.dtcc.dnv.otc.common.security.model.ExUser;
import com.dtcc.dnv.otc.common.security.model.SignonDelegate;
import com.dtcc.dnv.otc.common.security.model.SignonRequest;
import com.dtcc.dnv.otc.common.security.model.SignonResponse;
import com.dtcc.dnv.otc.common.signon.DSSignOnTO;
import com.dtcc.dnv.otc.common.signon.DSSignonConstants;
import com.dtcc.dnv.otc.common.signon.config.DSSignonConfiguration;
import com.dtcc.dnv.otc.common.signon.config.DSSignonConfigurationException;
import com.dtcc.dnv.otc.common.signon.config.IWorkflowStep;
import com.dtcc.dnv.otc.legacy.Originator;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author vxsubram
 * @date Aug 28, 2007
 * @version 1.0
 * 
 * Implementation class for processing the superuser workflow step.
 * 
 * @version	1.1					September 3, 2007			sv
 * Checked in for Cognizant.  Updated comments and javadoc.
 * Updated logic to support superuser module.
 * 
 * @version	1.2					September 6, 2007			vx
 * Checked in for Cognizant.  userType value is compared with 
 * SUPER_USER_IND variable.
 * 
 * @version	1.3					September 12, 2007			sv
 * Updated module to extend AbstractSignonModule.  Removed log.info().
 * 
 * @version	1.4					September 16, 2007			sv
 * Added postProcess() implementation.
 * 
 * @version	1.5					October 18, 2007			sv
 * Updated logger to be static final.
 * 
 * @version	1.6					October 25, 2007			sv
 * Checked in for Cognizant.  Updated postProcess to be validate the participant 
 * number against the family list.
 * */
public class SuperUserSignonModule extends AbstractSignonModule {
	
	// Holds the logger instance
	private static final Logger log = Logger.getLogger(SuperUserSignonModule.class);
	
	// Holds the value for the Super User value.
	private static final String SUPER_USER = "true";
	private static final String SUPER_USER_IND = "S";
	private static final String NOT_SUPER_USER = "false";
	private static final String SYSTEM = "system";
	private static final String SUPERUSER_ENTITLEMENT_LIST = "superuser-ocodes";
	
	/***
	 * This method process the user type sign workflow step.
	 * 
	 * @see com.dtcc.dnv.otc.common.signon.modules.ISignonModule#process(com.dtcc.dnv.otc.common.signon.modules.ISignonModuleRequest)
	 */
	
	public ISignonModuleResponse process(ISignonModuleRequest request) throws SignonModuleException{
		DSSignonConfiguration config = null;
		// Instantiate response object.
		SignonModuleResponse response = new SignonModuleResponse();
		
		ExUser user = request.getUser();
		
		// Get signon transfer object
		DSSignOnTO signTO = request.getSignonTo();
		
		//	The user is of type "User", then value is "U".
		//If the user is  "Super User", then it is holds "S"
		String userType = (String)signTO.getValue(DSSignonConstants.USERTYPE_WORKFLOW);
		signTO.removeAttribute(SUPERUSER_ENTITLEMENT_LIST);
		
		// If the user is a super user only, then do not bypass the step and return true in the response
		if(userType.equalsIgnoreCase(SUPER_USER_IND)){
			response.setSignonResponse(SUPER_USER);
			response.setBypass(false);
		}else{
			// If the user is not a super user, then bypass the step and return false in the response.
			response.setSignonResponse(NOT_SUPER_USER);
			response.setBypass(true);
		}
		
		return response;
	}
	
	/**
	 * Validates the participant mbr acct id that was submitted.
	 * 
	 * @see com.dtcc.dnv.otc.common.signon.modules.ISignonModule#postProcess(com.dtcc.dnv.otc.common.signon.modules.ISignonModuleRequest)
	 */
	public void postProcess(ISignonModuleRequest request) throws SignonModuleException {
		// Get signon transfer object
		DSSignOnTO signTO = request.getSignonTo();
		// Get usertype.
		String userType = (String)signTO.getValue(DSSignonConstants.USERTYPE_WORKFLOW);
		// Get account id.
		String partcMbrAcctid = (String)signTO.getValue("superuser");
		Vector entitlement = new Vector();
		ExUser user = request.getUser();
		SignonDelegate delegate = new SignonDelegate();
		String system = "";
		try{
			// Gets the configuration object
			DSSignonConfiguration config = DSSignonConfiguration.getInstance();
			IWorkflowStep wrkflowStep = config.getWorkflowStepType(request.getModuleName());
			system = wrkflowStep.getWorkflowAttribute(SYSTEM);
		}
		catch(DSSignonConfigurationException e){
			throw new SignonModuleException(DSSignonConstants.EX_MODULE_FAILED,request.getModuleName(),DSSignonConstants.EX_MODULE_FAILED_STR + request.getModuleName(),e);
		}
		
		try{
			// If the user is a superuser, then validate the account id.
			if (userType.equalsIgnoreCase(SUPER_USER_IND)){
				// accoutn id must be a length of 8
				if (partcMbrAcctid.trim().length() != 8){
					throw new SignonModuleException("invalidpartcmbracctid", request.getModuleName(), "Account number mnust be 8 alphanumeric characters.");
				}
				
				// If the User is Super User, then fetch the OriginatorList
				// Create the IServiceRequest and invoke the service.
				SignonRequest signonRequest = SignonRequest.familyForBranch(this.getAuditInfo(user), user.getGUID(),"",partcMbrAcctid.trim(),system.toUpperCase());
				// Get the response and retrieve the family list
				SignonResponse response = (SignonResponse) delegate.processRequest(signonRequest);
				Originator[] originatorList = response.getOriginatorList();
				if(originatorList != null){
					if(originatorList.length == 0)
						throw new SignonModuleException(DSSignonConstants.EX_ENTITLEMENT_NOREC,request.getModuleName(), DSSignonConstants.EX_ENTITLEMENT_NOREC_STR);
					
					// Populate Originator into entitlement 
					for(int i = 0;i < originatorList.length;i++){
						Originator originator = originatorList[i];
						entitlement.add(originator);
					}
					
					// Sets the Originator list in signonTO. 
					request.getSignonTo().setAttribute(SUPERUSER_ENTITLEMENT_LIST,entitlement);
					
				}else{
					throw new SignonModuleException(DSSignonConstants.EX_ENTITLEMENT_NOREC,request.getModuleName(),
							DSSignonConstants.EX_ENTITLEMENT_NOREC_STR);
				}
			}
		}catch(BusinessException e){
			throw new SignonModuleException(DSSignonConstants.EX_BUSINESS,request.getModuleName(),
					e.getMessage() + request.getModuleName(),e);
		}catch(UserException e){
			throw new SignonModuleException(DSSignonConstants.EX_BUSINESS,request.getModuleName(),
					e.getMessage() + request.getModuleName(),e);
		}
	}
	
	/**
	 * Returns AuditInfo instance
	 * 
	 * @param ExUser
	 * @return AuditInfo
	 * @throws UserException
	 * 
	 * Returns the AuditInfo containing the operator id and session id
	 */
	private AuditInfo getAuditInfo(ExUser user) throws UserException {
		String sessionID = "NA";
//		return new AuditInfo(user.getGUID(), sessionID, 
//				user.getUserLastName() + ", " + user.getUserFirstName(), 
//				user.getEmailAddress());
		return null;
	}
}
