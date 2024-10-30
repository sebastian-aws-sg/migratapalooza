package com.dtcc.dnv.otc.common.signon.modules;

import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.dtcc.dnv.otc.common.signon.DSSignOnTO;
import com.dtcc.dnv.otc.common.signon.DSSignonConstants;
import com.dtcc.dnv.otc.common.signon.config.DSSignonConfiguration;
import com.dtcc.dnv.otc.common.signon.config.DSSignonConfigurationException;
import com.dtcc.dnv.otc.common.signon.config.Label;
import com.dtcc.dnv.otc.common.signon.config.LabelsWorkflowStep;


/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jul 24, 2007
 * @version 1.0
 *
 * This class in intended to process the account type (test/prodction)
 * module for the signon framework.
 * 
 * @version	1.1				August 30, 2007			sv
 * Updated process() signature to throw exception. Updated hasProduction()
 * to return TRUE.
 * 
 * @version	1.2				September 3, 2007		sv
 * Checked in for Cognizant.  Updated comments and javadaoc.  Updated to use configuration 
 * objects.
 * 
 * @version	1.3				September 12, 2007		sv
 * Updated module to extend AbstractSignonModule.  Removed all log.info().
 * Checked in for Cognizant.  Handled Exception. SignonModuleException is 
 * thrown when exception occurs or for unexpected case.
 * 
 * @version	1.4				September 26, 2007		sv
 * Checked in for Cognizant.  Updated to handle "mutuallyExclusive" property. 
 * Updated to get Test/Production value from attribute of DSSignonTO.
 * 
 * @version	1.5				October 2, 2007			sv
 * Checked in for Cognizant.  Updated the implementation to handle 
 * "mutuallyExclusive" property. 
 * 
 * @vserion	1.6				October 18, 2007		sv
 * Updated logger to be static final.
 * 
 * @version	1.7				October 21, 2007		sv
 * Removed unsused isUserAccountsMutuallyExclusive variable from process().
 */
public class AccountTypeSignonModule extends AbstractSignonModule {
	// Logger instance
	private static final Logger log = Logger.getLogger(AccountTypeSignonModule.class);

	/**
	 * This method process the user account type workflow step.
	 * 
	 * @see com.dtcc.dnv.otc.common.signon.modules.ISignonModule#process(com.dtcc.dnv.otc.common.signon.modules.ISignonModuleRequest)
	 */
	public ISignonModuleResponse process(ISignonModuleRequest request) throws SignonModuleException{
		
		DSSignonConfiguration config = null;
		LabelsWorkflowStep labelswrkFlwStep = null;
		
		// Instantiate response object
		SignonModuleResponse response = new SignonModuleResponse();
		// Get transfer object
		DSSignOnTO signTO = request.getSignonTo();
		
		try{
			// Gets the configuration object
			config = DSSignonConfiguration.getInstance();
			labelswrkFlwStep =  (LabelsWorkflowStep)config.getWorkflowStepType(request.getModuleName());
		}catch(DSSignonConfigurationException e){
			// Throws SignonModuleException. AccountTypeModule failed.
			throw new SignonModuleException(DSSignonConstants.EX_MODULE_FAILED,request.getModuleName(),DSSignonConstants.EX_MODULE_FAILED_STR,e);
		}
		
		// The user is of type "User", then value is "U".
		//If the user is  "Super User", then value is "S"
		String userType = (String)signTO.getValue(DSSignonConstants.USERTYPE_WORKFLOW);
		
		// If user has both test and production accounts, then give them the option.
		if ( hasTestandProdIndicator(request)){
			
				Vector testProdIndicators = new Vector();
				// Add an Empty value
				testProdIndicators.add(new LabelValueBean("",""));
				// Get an iterator for all the labels
				Iterator labelsIter = labelswrkFlwStep.getAllLabels().iterator();
				// Iteratte through the Labels collection
				while(labelsIter.hasNext()){
					Label label = (Label)labelsIter.next();
					// If User can have only one account type, either Test or Production but not both then throw Exception
					if(label.isMutuallyExclusive()){
						// Clear the collection before throwing Exception.
						testProdIndicators.clear();	
						throw new SignonModuleException(DSSignonConstants.EX_ACCTTYPE_INVALID,request.getModuleName(),
								DSSignonConstants.EX_ACCTTYPE_INVALID_STR);
					}
						
					testProdIndicators.add(new LabelValueBean(label.getLabelName(),label.getLabelValue()));
				}
				// Set the Label collection as name value object in the response
				response.setSignonResponse((Vector)testProdIndicators);
				response.setBypass(false);
			
		}
		// If the user has test/production accounts, then bypass the step and return the value in the response
		else{
			DSSignOnTO signonTO = request.getSignonTo();
			String accIndicator = (String)signonTO.getAttribute(DSSignonConstants.ACCOUNT_TYPE_ATTR);
			if(accIndicator != null && accIndicator.length() >0)
				response.setSignonResponse(accIndicator);
			else
				throw new SignonModuleException(DSSignonConstants.EX_ACCTTYPE_INVALID,request.getModuleName(),DSSignonConstants.EX_ACCTTYPE_INVALID_STR);
			response.setBypass(true);
		}
		return response;
	}
	
	/**
	 * Determines if the user has test/production accounts type indicator.
	 * @param request ISignonModuleRequest implementation
	 * @return boolean value determining the user has test or produiction accounts types.
	 */
	private boolean hasTestandProdIndicator(ISignonModuleRequest request){
		DSSignOnTO signonTO = request.getSignonTo();
		String accIndicator = (String)signonTO.getAttribute(DSSignonConstants.ACCOUNT_TYPE_ATTR);
		if(accIndicator == null)
			return true;
		else
			return false;
	}
}
