package com.dtcc.dnv.otc.common.signon;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dtcc.dnv.otc.common.action.DSSignonAction;
import com.dtcc.dnv.otc.common.form.DSSignonForm;
import com.dtcc.dnv.otc.common.security.model.DSCUOUserManager;
import com.dtcc.dnv.otc.common.signon.exception.DSSignonException;
import com.dtcc.dnv.otc.common.signon.modules.ISignonModule;
import com.dtcc.dnv.otc.common.signon.modules.ISignonModuleRequest;
import com.dtcc.dnv.otc.common.signon.modules.ISignonModuleResponse;
import com.dtcc.dnv.otc.common.signon.modules.SignonModuleException;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Sep 18, 2007
 * @version 1.0
 *
 * This class serves as a utility class for the signon action process.
 * 
 * @version	1.1				October 17, 2007			sv
 * Updated processWorkflowResponse() to set the exception type so that it
 * can be centralized to this utility method and avoid redundancy.
 * 
 * @version	1.2				October 17, 2007			sv
 * Added processWorkflowModule() to centralize the logic behind module processing.
 * Added persistFormData to persist the state data for signon.
 * 
 * @version	1.3				October 22, 2007			sv
 * Updaetd to add utility methds for restAction() and commitAction().  Updated 
 * method name from persistFormData() to persistStateData().  Updated comments.
 * 
 * @version	1.4				October 22, 2007			sv
 * Removed unused imports.
 */
public class DSSignonUtil {

	/**
	 * This method will locate the appropriate error mapping for this respective
	 * errorForwardName.  If it cannot find the forward requested, then it will default 
	 * to the signon failure forward.  If that is not found, then it will default to 
	 * the gloabl failure forward.
	 * 
	 * @param mapping ActionMapping used to determine the forward mapping.
	 * @param errorForwardName
	 * @return
	 */
	public static String locateErrorMapping(ActionMapping mapping, String errorForwardName){
		// Default the forward name to the one provided
		String forwardName = errorForwardName;
		// try to find the forward for this.
		ActionForward forward = mapping.findForward(forwardName);
		// If a forward is not found, then default to SIGNON_FAILURE_FORWARD
		if (forward == null){
			forward = mapping.findForward(DSSignonAction.SIGNON_FAILURE_FORWARD);
			forwardName = DSSignonAction.SIGNON_FAILURE_FORWARD;
		}
		// If a forward is still not found, then default to FAILURE_FORWARD
		if (forward == null){
			forwardName = DSSignonAction.FAILURE_FORWARD;
		}
		// Return forward name.
		return forwardName;
	}
	
	/**
	 * Handles workflow responses.
	 * 
	 * @param signonMgr DSSignonManager object used to manage workflow.
	 * @param workflowStep String value of the workflow step
	 * @param workflowValue Value for the work flow step
	 * @param module Module that processes this step
	 * @param request Signonrequest pused by the module.
	 * @throws SignonModuleException Exception occured from module.
	 */
	public static void processWorkflowResponse(DSSignonManager signonMgr, String workflowStep, Object workflowValue, ISignonModule module, ISignonModuleRequest request)
		throws SignonModuleException, DSSignonException{
		// Add the workflow value
		signonMgr.addWorkflowValue(workflowStep, workflowValue);
		try{
			// Initiate post processing.
			module.postProcess(request);
		}
		// Set exception type appropriately.
		catch(SignonModuleException e){
			e.setPostProcessingException();
			throw e;
		}
	}
	
	/**
	 * Handles workflow module processing.
	 * 
	 * @param module Module that processes this step
	 * @param request Signonrequest pused by the module.
	 * @throws SignonModuleException Exception occured from module.
	 */
	public static ISignonModuleResponse processWorkflowModule(ISignonModule module, ISignonModuleRequest request)
		throws SignonModuleException{
		ISignonModuleResponse response = null;
		try{
			// Initiate post processing.
			response = module.process(request);
		}
		// Set exception type appropriately.
		catch(SignonModuleException e){
			e.setProcessingException();
			throw e;
		}
		return response;
	}
	
	/**
	 * @param signonModEx
	 * @return
	 */
	public static String buildMessageKey(SignonModuleException signonModEx){
		// Holds the errorclass
		String errorClassName = "signon";
		// Holds the attribute delimiter
		String ATTR_DELIM = ".";
		// Holds the module name where the exception occured
		String moduleName = signonModEx.getModuleName();
		// Holds the Error Type / Error code. This should be the part of key in ApplicationResource.properties.
		String errorType = signonModEx.getErrorType();
		// Forms the message key
		StringBuffer messageKeySB = new StringBuffer();
		messageKeySB.append(errorClassName.trim());
		// Apped the module name if it is provided.
		if (moduleName.trim().length() > 0){
			messageKeySB.append(ATTR_DELIM + moduleName);
		}
		// Append the error type if it is provided.
		if (errorType.trim().length() > 0){
			messageKeySB.append(ATTR_DELIM + errorType);
		}
		// Return string version of message key.
		return messageKeySB.toString();
	}
	
	/**
	 * @param workflowValue String value of workflow value.
	 * @return boolean value indicating whether or not there is a workflow value.
	 */
	public static boolean workflowHasValue(String workflowValue){
		boolean hasValue = (workflowValue != null && workflowValue.trim().length() > 0);
		return hasValue;
	}
	
	/**
	 * @param workFlowStep String value fo workflow step.
	 * @return boolean value indicating whether the workflow has started based on workflow step.
	 */
	public static boolean workflowHasStarted(String workFlowStep){
		boolean hasStarted = (workFlowStep != null && workFlowStep.trim().length() > 0);
		return hasStarted;
	}
	
	/**
	 * Centralized utility method to persist signon state data to the ActionForm.
	 * 
	 * @param signonForm ActionForm form bean
	 * @param workflowStep String value of the workflow step for the module processing
	 * @param workflowResponse Response object from the module.
	 */
	public static void persistStateData(DSSignonForm signonForm, String workflowStep, Object workflowResponse){
		// Persist it in the form
		signonForm.setSignonStep(workflowStep);
		signonForm.setSignonObject(workflowResponse);
	}
	
	/**
	 * This method commits tha action.  It resets the form, commits the signon amanger and 
	 * removes the user object.
	 * 
	 * @param req HttpServletRequest object
	 * @param signonForm DSSignonForm form bean.
	 */
	public static void commitAction(HttpServletRequest req, DSSignonForm signonForm)
		throws DSSignonException{
		// get instance of signon manager.
		DSSignonManager signonMgr = DSSignonManager.getInstance(req);
		// There are no more step to do, so reset the form
		signonForm.reset();
		// Commit the signonMgr to cleanup aid in transition to application.
		signonMgr.commit(req);
		// If this is the first time in the signon workflow then remove all traces of the user object.
		DSCUOUserManager.removeUser(req);
	}
	
	/**
	 * Resets this action.  It removes the user object and resets the signon manager.
	 * 
	 * @param req HttpServletRequest request object.
	 */
	public static void resetAction(HttpServletRequest req)
		throws DSSignonException{
		// If this is the first time in the signon workflow then remove all traces of the user object.
		DSCUOUserManager.removeUser(req);
		// Reset the signon manager.
		// Get instance of signon manager
		DSSignonManager signonMgr = DSSignonManager.getInstance(req);
		// Rest signon manager.
		signonMgr.reset();
	}
}
