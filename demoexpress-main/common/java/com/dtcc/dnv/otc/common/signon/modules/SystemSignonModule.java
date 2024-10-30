package com.dtcc.dnv.otc.common.signon.modules;

import org.apache.log4j.Logger;

import com.dtcc.dnv.otc.common.security.model.ExUser;
import com.dtcc.dnv.otc.common.signon.DSSignOnTO;
import com.dtcc.dnv.otc.common.signon.DSSignonConstants;
import com.dtcc.dnv.otc.common.signon.config.DSSignonConfiguration;
import com.dtcc.dnv.otc.common.signon.config.DSSignonConfigurationException;
import com.dtcc.dnv.otc.common.signon.config.IWorkflowStep;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date October 11, 2007
 * @version 1.0
 * 
 * This module is responsible for checking if the user has access to the system.  This module is
 * essentially a pass through module.
 * 
 * @version	1.1				October 18, 2007		sv
 * Updated logger to be static final.  Updated process() for system == null.
 */
public class SystemSignonModule extends AbstractSignonModule {
	
	// Constants
	private static final String SYSTEM = "system";

	private static final Logger log = Logger.getLogger(SystemSignonModule.class);
	
	/**
	 * Interface method to process the this signon step.
	 * 
	 * @see com.dtcc.dnv.otc.common.signon.modules.ISignonModule#process(com.dtcc.dnv.otc.common.signon.modules.ISignonModuleRequest)
	 */
	public ISignonModuleResponse process(ISignonModuleRequest request) throws SignonModuleException{
		DSSignonConfiguration config = null;
		boolean hasSystem = false;
		String system = "";
		// Instantiate response object
		SignonModuleResponse response = new SignonModuleResponse();
		// Get transfer object
		DSSignOnTO signTO = request.getSignonTo();
		ExUser user = request.getUser();

		try{
			// Gets the configuration object
			config = DSSignonConfiguration.getInstance();
			// Get system workflow step.
			IWorkflowStep wrkflowStep = config.getWorkflowStepType(request.getModuleName());
			// Get system attribute
			system = wrkflowStep.getWorkflowAttribute(SYSTEM);
			// If a system was not provided, then this means that the module was configured but was not
			// provided a system.  Return false.
			if (system == null || system.trim().length() == 0){
				hasSystem = false;
			}
			// If the user has the system role, then return true.
			else if (user.hasRole(system.trim())){
				hasSystem = true;
			}
			// Else the user does not hae access.
			else{
				hasSystem = false;
			}
		}
		catch(DSSignonConfigurationException e){
			throw new SignonModuleException(DSSignonConstants.EX_MODULE_FAILED,request.getModuleName(),DSSignonConstants.EX_MODULE_FAILED_STR + request.getModuleName(),e);
		}
		
		// If user does not haccess to the system, then throw a SignonModuleException
		if (!hasSystem){
			throw new SignonModuleException("DSSMSYS001",request.getModuleName(), "User does not have access to the system " + system);
		}
		else{ 
			if (system == null){
				throw new SignonModuleException("DSSMSYS002",request.getModuleName(), "Invalid system provided.");
			}
			
			response.setBypass(true);
			response.setSignonResponse(system.toLowerCase());
		}
		// Return the response object.		
		return response;
	}
}
