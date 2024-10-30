package com.dtcc.dnv.otc.common.signon.modules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

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
import com.dtcc.dnv.otc.common.signon.entitlement.EntitlementDelegate;
import com.dtcc.dnv.otc.common.signon.entitlement.EntitlementSignonRequest;
import com.dtcc.dnv.otc.common.signon.entitlement.EntitlementSignonResponse;
import com.dtcc.dnv.otc.common.signon.entitlement.UFOBean;
import com.dtcc.dnv.otc.legacy.Originator;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jul 24, 2007
 * @version 1.0
 *
 * Implementation class for entilements signon module.  Currently this will be used to
 * manage the originator codes.
 * 
 * @version	1.1				July 27, 2007			sv
 * Updated call and implementation to getOEntitlements(DSSignOnTO).
 * 
 * @version	1.2				August 30, 2007			sv
 * Updated comments and exception handling.   Updated getOEntitlements()
 * by adding more concrete implementation.
 * 
 * @version	1.3				September 3, 2007		sv
 * Checked in for Cognizant.  Updated commnts and javadoc.  Update logic to use 
 * module name and not module type.
 * 
 * @version	1.4				September 12, 2007		sv
 * Updated to extend AbstractSignonModule.  Checked in for Cognizant.  Handled Exception. 
 * SignonModuleException is thrown when exception occurs or for unexpected case.
 * 
 * @version	1.5				September 26, 2007		sv
 * Checked in for Cognizant.  Implemented postProcess method to set the Test/Prod 
 * indicator attribute based upon the workflow value.
 * 
 * @version	1.6				October 17, 2007		sv
 * Updated process() to remove the account type aatribute as a reset before processing.
 * This is done in case the attribute already existed.  Updated comments.
 * 
 * @version	1.7				October 18, 2007		sv
 * Updated logger to be static final.
 * 
 * @version	1.8				October 25, 2007		sv
 * Updated comments.  Checked in for Cognizant.  Updated the postProcess to validate the 
 * chosen originator code.
 * 
 * @version	1.9				October 30, 2007		sv
 * Checked in for Cognizant.  Updated comments.  Checked in for Cognizant.  Updated 
 * the User Entitlement code to display unique ocodes and and postProcess to store 
 * the selected ocode list as attribute.
 *   
 */
public class EntitlementSignonModule extends AbstractSignonModule {
	
	// Constants
	private static final String SYSTEM = "system";
	private static final String SUPER_USER_IND = "S";
	private static final String USER_IND = "U";
	private static final String ENTITLEMENT_LIST = "oCodes";
	private static final String SUPERUSER_ENTITLEMENT_LIST = "superuser-ocodes";
	private static final String PRODUCT_ID = "productid";
	private static final String USER_SELECTED_OCODES = "selected-ocodes";

	private static final Logger log = Logger.getLogger(EntitlementSignonModule.class);
	
	/**
	 * Interface method to process the this signon step.
	 * 
	 * @see com.dtcc.dnv.otc.common.signon.modules.ISignonModule#process(com.dtcc.dnv.otc.common.signon.modules.ISignonModuleRequest)
	 */
	public ISignonModuleResponse process(ISignonModuleRequest request) throws SignonModuleException{
		DSSignonConfiguration config = null;
		Vector entitlements = null;
		// Instantiate response object
		SignonModuleResponse response = new SignonModuleResponse();
		// Get transfer object
		DSSignOnTO signTO = request.getSignonTo();
		// Remove this attribute because it would be restablished.  This is necessary because
		// If this attribute remains there it may be interpreted as determied bypass some
		// core logic.
		signTO.removeAttribute(ENTITLEMENT_LIST);
		signTO.removeAttribute(DSSignonConstants.ACCOUNT_TYPE_ATTR);
		
		ExUser user = request.getUser();
		String usertype = (String)signTO.getValue(DSSignonConstants.USERTYPE_WORKFLOW);
		String participantId = (String)signTO.getValue(DSSignonConstants.SUPERUSER_WORKFLOW);
		
		try{
			// Gets the configuration object
			config = DSSignonConfiguration.getInstance();
			// Get OCode entitlements
			entitlements = getOEntitlements(user, usertype, participantId, config,request);
		}
		catch(DSSignonConfigurationException e){
			throw new SignonModuleException(DSSignonConstants.EX_MODULE_FAILED,request.getModuleName(),DSSignonConstants.EX_MODULE_FAILED_STR + request.getModuleName(),e);
		}
		
		// If entitlement size is zero, then throw SignonModuleException
		if (entitlements.size() == 0){
			throw new SignonModuleException(DSSignonConstants.EX_ENTITLEMENT_NOREC,request.getModuleName(),
					DSSignonConstants.EX_ENTITLEMENT_NOREC_STR);
		}
		// If only one entitlement exists for this user, then bypass the step and return the one
		// item in the response
		else if (entitlements.size() == 1){
			response.setBypass(true);
			LabelValueBean singleEntry = (LabelValueBean)entitlements.get(0);
			response.setSignonResponse(singleEntry.getValue());
		}
		// If more than one exist, then give the user the option.
		else{
			response.setBypass(false);
			response.setSignonResponse(entitlements);
		}
		// Return the response object.		
		return response;
	}
	
	/**
	 * This methods returns a collection of OCode entitlements.
	 * @param user ExUser instance
	 * @param userType String the value of the usertypes workflow
	 * @param participantId String the value of the superuser workflow
	 * @param config DSSignonConfiguration class instance
	 * @param moduleReq ISignonModuleRequest implementation instance
	 * @return Vector of OCode entitlements
	 * @throws SignonModuleException
	 */
	private Vector getOEntitlements(ExUser user,String userType, String participantId, DSSignonConfiguration config,ISignonModuleRequest moduleReq)
		throws SignonModuleException{
		
		// Store collection of entitlement[oCode] 
		Vector entitlement = new Vector();
		
		IWorkflowStep wrkflowStep = config.getWorkflowStepType(moduleReq.getModuleName());
		
		String system = wrkflowStep.getWorkflowAttribute(SYSTEM);
		String productId = wrkflowStep.getWorkflowAttribute(PRODUCT_ID);
		Vector oCodes = getOriginatorCodes(user,userType,participantId,system,productId,moduleReq);
		
		ArrayList uniqueList = new ArrayList();
		
		// The usertype is User so setup the list of ocodes based on UFOs
		if (userType.equalsIgnoreCase(USER_IND)) {
			// Instantiate iterator of UFOs
			Iterator UFOBeanListIter = oCodes.iterator();
			// Loop through the UFOs to build LabelValueBean collection.
			while(UFOBeanListIter.hasNext()){
				UFOBean ufoBean = (UFOBean)UFOBeanListIter.next();
				if(!uniqueList.contains(ufoBean.getOCode())){
					entitlement.add(new LabelValueBean(ufoBean.getOCode(),ufoBean.getOCode()));
				}
				uniqueList.add(ufoBean.getOCode());
			}
		}
		// The user is a superuser to call alterate method for attaining ocodes.
		else{ 
			Iterator OCodeListIter = oCodes.iterator();
			while(OCodeListIter.hasNext()){
				Originator originator = (Originator)OCodeListIter.next();
				entitlement.add(new LabelValueBean(originator.getOriginatorCode(), originator.getOriginatorCode()));
			}
		}
		// There is more than one, then place a default value as the first entry.
		if (entitlement.size() > 1){
			entitlement.add(0, new LabelValueBean("", ""));
		}
		// return the entitlements.
		return entitlement;
	}
	
	/**
	 * This method returns a Vector of ocode objects.
	 * 
	 * @param user User object
	 * @param userType String value of the type of user
	 * @param participantId String value of the participant id entered if it is a superuser
	 * @param system Strnig value of the system 
	 * @param productId String value fo the product id
	 * @param moduleReq Module request object
	 * @return Vector of ocode objects.
	 * @throws SignonModuleException
	 */
	private Vector getOriginatorCodes(ExUser user,String userType,String participantId,String system, String productId, ISignonModuleRequest moduleReq) throws SignonModuleException{
		
		Vector entitlement = new Vector();
		SignonDelegate delegate = new SignonDelegate();
		
		try{
			// Holds the originators collection
			if (userType.equalsIgnoreCase(USER_IND)) {
				EntitlementDelegate eCodeDelegate = new EntitlementDelegate();
				EntitlementSignonRequest eCodeRequest = new EntitlementSignonRequest(this.getAuditInfo(user));
				eCodeRequest.setUser(user);
				eCodeRequest.setProductId(productId);
				EntitlementSignonResponse response = (EntitlementSignonResponse)eCodeDelegate.processRequest(eCodeRequest);
				Iterator UFOBeanListIter = response.getUFOBeans().iterator();
				// Iterate through the list of UFOBeans
				while(UFOBeanListIter.hasNext()){
					UFOBean ufoBean = (UFOBean)UFOBeanListIter.next();
					entitlement.add(ufoBean);
				}
			}else{ 
				// Get the oCode list
				Vector superuserOcodes = (Vector)moduleReq.getSignonTo().getAttribute(SUPERUSER_ENTITLEMENT_LIST);
				// Set the collection.
				if(superuserOcodes != null && superuserOcodes.size() > 0){
					entitlement = superuserOcodes;
				}else{
					// If the User is Super User, then fetch the OriginatorList
					// Create the IServiceRequest and invoke the service.
					SignonRequest request = SignonRequest.familyForBranch(this.getAuditInfo(user), user.getGUID(),"",participantId,system.toUpperCase());
					// Get the response and retrieve the family list
					SignonResponse response = (SignonResponse) delegate.processRequest(request);
					Originator[] originatorList = response.getOriginatorList();
					// Populate Originator into entitlement 
					for(int i = 0;i < originatorList.length;i++){
						Originator originator = originatorList[i];
						entitlement.add(originator);
					}
				}
			}
			
			// Sets the Originator list in signonTO. This will be useful in postProcess. 
			moduleReq.getSignonTo().setAttribute(ENTITLEMENT_LIST,entitlement);
			
		}catch(BusinessException e){
			throw new SignonModuleException(DSSignonConstants.EX_BUSINESS,moduleReq.getModuleName(),
				e.getMessage() + moduleReq.getModuleName(),e);
		}catch(UserException e){
			throw new SignonModuleException(DSSignonConstants.EX_BUSINESS,moduleReq.getModuleName(),
				e.getMessage() + moduleReq.getModuleName(),e);
		}
		return entitlement;
	}
	
	/**
	 * Validates the chosen originator code.
	 * 
	 * @see com.dtcc.dnv.otc.common.signon.modules.ISignonModule#postProcess(com.dtcc.dnv.otc.common.signon.modules.ISignonModuleRequest)
	 */
	public void postProcess(ISignonModuleRequest request) throws SignonModuleException {
		// Get signon transfer object
		DSSignOnTO signTO = request.getSignonTo();
		// Get the oCode list
		Vector entitlements = (Vector)signTO.getAttribute(ENTITLEMENT_LIST);
		// Get usertype.
		String userType = (String)signTO.getValue(DSSignonConstants.USERTYPE_WORKFLOW);
		// Get Originator Code.
		String oCode = (String)signTO.getValue(request.getModuleName());
		
		boolean oCodeFound = false;
		// validate whether the oCode is valid oCode. If valid, get the Test/Prod indicator for Super User alone.
		if (userType.equalsIgnoreCase(SUPER_USER_IND)){
			// Iterate the originator list to get the Test/Prod account indicator. 
			Iterator OCodeListIter = entitlements.iterator();
			while(OCodeListIter.hasNext() && !oCodeFound){
				Originator originator = (Originator)OCodeListIter.next();
				if(originator.getOriginatorCode().equalsIgnoreCase(oCode)){
					// Sets the Test/Production attribute. 
					request.getSignonTo().setAttribute(DSSignonConstants.ACCOUNT_TYPE_ATTR,originator.getProdTestInd());
					oCodeFound = true;
				}
			}
		}else if (userType.equalsIgnoreCase(USER_IND)){
			// Collection that holds the matched oCode and entitlement for user
			ArrayList matchedOcodes = new ArrayList();
			// Iterate the originator list to validate the oCode in request. 
			Iterator OCodeListIter = entitlements.iterator();
			while(OCodeListIter.hasNext()){
				UFOBean ufoBean = (UFOBean)OCodeListIter.next();
				if(ufoBean.getOCode().equalsIgnoreCase(oCode)){
					oCodeFound = true;
					matchedOcodes.add(ufoBean);
				}
			}
			// Sets the matched ocodes and entitlement in attribute. 
			request.getSignonTo().setAttribute(USER_SELECTED_OCODES,matchedOcodes);
			
		}
		// If the Originator Code in request is not valid, then throw Exception 
		if(!oCodeFound){
			throw new SignonModuleException(DSSignonConstants.EX_MODULE_FAILED,request.getModuleName(),DSSignonConstants.EX_MODULE_FAILED_STR + request.getModuleName());
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
