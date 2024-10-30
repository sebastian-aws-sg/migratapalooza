package com.dtcc.dnv.otc.common.action;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.dtcc.dnv.otc.common.form.DSSignonForm;
import com.dtcc.dnv.otc.common.security.model.ExUser;
import com.dtcc.dnv.otc.common.security.model.IUser;
import com.dtcc.dnv.otc.common.signon.DSSignonManager;
import com.dtcc.dnv.otc.common.signon.DSSignonUtil;
import com.dtcc.dnv.otc.common.signon.exception.DSSignonException;
import com.dtcc.dnv.otc.common.signon.modules.ISignonModule;
import com.dtcc.dnv.otc.common.signon.modules.ISignonModuleRequest;
import com.dtcc.dnv.otc.common.signon.modules.ISignonModuleResponse;
import com.dtcc.dnv.otc.common.signon.modules.SignonModuleException;
import com.dtcc.dnv.otc.common.signon.modules.SignonModuleLocator;
import com.dtcc.dnv.otc.common.signon.modules.SignonModuleRequest;
import com.dtcc.sharedservices.security.common.CUOUser;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date June 23, 2007
 * @version 1.0
 *
 * Action class used to handle user sign on.  Initiates user creation to be made available
 * throughout the application.
 * 
 * @version	1.1				July 24, 2007				sv
 * Updated comments.  Removed initForm() method and replaced it with signon module
 * framework.  Removed explicit setting of sign mgr in the session as signon mgr will
 * take care of this.  Updated manageNextSteps() to handle signon modules.  Created 
 * processSignonWorfklow() to actual signon work flow logic and return the respective 
 * forward name.
 * 
 * @version	1.2				July 25, 2007				sv
 * Updated processSignonWorfklow() by adding else statement for when data is entered 
 * to process the workflow.
 * 
 * @version	1.3				July 27, 2007				sv
 * Updated manageNextStep() to set the transfer object from the signon module response.
 * 
 * @version	1.4				August 14, 2007				sv
 * Updated manageNextStep() to support passing user object to signon module via the signon
 * request.
 * 
 * @version 1.5				August 29, 2007				sv
 * Updated returnForward() to catch and handle exceptions.  Updated methods to throw 
 * DSSignonException, SignonModuleException.  Updated processSignonWorfklow() to
 * reset trace of the user.  Updated comments.
 * 
 * @version	1.6				September 12, 2007			sv
 * Updated to support module post processing.  Updated comments.
 * 
 * @version	1.7				September 16, 2007			sv
 * Updated to support postProcess() better.  Added javadoc and comments.  Added
 * processWorkflowResponse().  Updated to use SignonRequest factory method rather than
 * instantiating it directly.
 * 
 * @version	1.8				September 19, 2007			sv
 * Refactored code to use DSSignonUtil.  Added error handling.  Added more constants
 * for forwarding labels.  Added built error handling for empty workflow values.
 * Added manageError() to support managing erros that occur by looking for the 
 * resource message and then determining the forward.  Added processWorkflowResponse()
 * to handle workflow/module responses.
 * 
 * @version	1.9				September 19, 2007			sv
 * Updated manageNextStep() to set workflowStep from processWorkflowStep call.
 * 
 * @version	1.10			September 24, 2007			sv
 * Updated processSignonWorfklow() to use static removeUser() rather than directly
 * touching the session.
 * 
 * @version	1.11			October 4, 2007				sv
 * Updated returnForward() to use the module name from the SignonModuleException to 
 * manage the error.  Updated returnForward() to create generic error message when a 
 * SignonException occurs.  Updated comments.  Updated manageError() to handle
 * SignonModuleException on re-process and to set the forwardStep to be the messageKey 
 * if it was found.
 * 
 * @version	1.12			October 10, 2007			sv
 * Updated returnForward() to use forwardName from processWorkflowStep() rather than
 * SIGON_FAILURE when no data has been entered.  Updated catch of SignonModuleException
 * in returnForward() to use overloaded methods that takes an exception directly.  Updated
 * manageNextStep() to handle SignonModuleException while processing the next step 
 * in the workflow.  Updated manageError() to handle exception.  Updated processWorkflowStep()
 * to handle exception.  Added usage of more utility methods and completed error handling and 
 * recovery processing.  Updated to remove the user once the signon transfer object is committed
 * and the request is forward to the success forward. Updated comments.
 * 
 * @version	1.13			October 18, 2008			sv
 * Updated logger to be static final.  Added null check for the response object in 
 * manageRecoveryError().
 * 
 * @version	1.14			October 22, 2007			sv
 * Updated processSignonWorfklow() to use commitAction() and resetAction() from DSSignonUtil.
 * Updated method name from persistFormData() to persistStateData().
 */
public class DSSignonAction extends DSBaseAction {
	// Static members
	private static final Logger log = Logger.getLogger(DSSignonAction.class);
	// Forward constants
	private static final String SUCCESS_FORWARD = "success";
	public static final String FAILURE_FORWARD = "failure";
	public static final String SIGNON_FAILURE_FORWARD = "signon.failure";
	private static final String GENERAL_INTERNAL_ERROR = "general.internal.error1";
	private static final String SIGNON_NO_DATA_ERROR = "signon.nodata.error";
	// General constants
	private static final String SIGNON_KEY = "signon";
	
	/**
	 * @see com.dtcc.sharedservices.cwf.web.struts.DtccBaseAction#returnForward(com.dtcc.sharedservices.security.common.CUOUser, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward  returnForward(CUOUser user, ActionMapping mapping, 
			ActionForm form, HttpServletRequest req, HttpServletResponse res){
		ActionForward forward = null;
		String forwardName = "";
		DSSignonForm signonForm = (DSSignonForm)form;
		ActionErrors errors = new ActionErrors();
		// Get the current workflow step and value.
		String workflowStep = signonForm.getSignonStep();
		if (workflowStep!= null){
			workflowStep = workflowStep.trim();
		}
		String workflowValue = signonForm.getSignonWorkflowValue();
		if (workflowValue!= null){
			workflowValue = workflowValue.trim();
		}
		try{
			// The signon mgr has started so process the workflow value
			if (DSSignonUtil.workflowHasStarted(workflowStep)){
				// The signon mgr session has begun, but there was no data entered.
				if(!DSSignonUtil.workflowHasValue(workflowValue)){
					// Add the error message for the exception occured.
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(SIGNON_NO_DATA_ERROR, user.getUserId() + " " + new Timestamp(System.currentTimeMillis())));
					// Set forward name.
					forwardName = processWorkflowStep(workflowStep, user, signonForm, req);
				}
				else{
					// Execute workflow logic and return the respective forward name.
					forwardName = processSignonWorfklow(workflowStep, workflowValue, signonForm, req, user);
				}
			}
			else{
				// Execute workflow logic and return the respective forward name.
				forwardName = processSignonWorfklow(workflowStep, workflowValue, signonForm, req, user);
			}
		}
		catch (SignonModuleException e){
			// Log the module exception
			log.info(e);
			// Manage the error specifically for a module exception and find the forward name.
			forwardName = manageError(req, errors, e, user, signonForm, mapping);
			
		}
		catch (DSSignonException e){
			// Log the error.
			log.info(e);
			// Add the error message for the exception occured.
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(GENERAL_INTERNAL_ERROR, user.getUserId() + " " + new Timestamp(System.currentTimeMillis())));
			// If an exception occurred while trying to proces the signon workflow step, forward to failure ActionForward
			forwardName = "failure";
		}
		// Find forward.
		forward = mapping.findForward(forwardName);
		// Save the errors.
		if (!errors.isEmpty())
			saveErrors(req, errors);
		return forward;
	}
	
	/**
	 * This method controls the logic for the signon mechanism.
	 * 
	 * @param workflowStep String value of the workflow step
	 * @param workflowValue Value for the work flow step
	 * @param signonForm ActionForm form bean
	 * @param req HttpServletRequest object
	 * @return String value of step to find forward.
	 */
	private String processSignonWorfklow(String workflowStep, String workflowValue, DSSignonForm signonForm, HttpServletRequest req, CUOUser user)
		throws DSSignonException, SignonModuleException{
		ActionForward forward = null;
		String fowardStep = "";
		// Get instance of the signon mgr
		DSSignonManager signonMgr = DSSignonManager.getInstance(req);
		// The signon mgr has started so process the workflow value
		if (workflowStep != null && workflowStep.trim().length() > 0){
			// The signon mgr session has begun, but there was no data entered.
			if(workflowValue != null && workflowValue.trim().length() > 0){
				ISignonModuleRequest request = SignonModuleRequest.createRequest(workflowStep, signonMgr.getSignOnTransferObject(), (ExUser)user);
				// Execute signon modules.
				ISignonModule module = SignonModuleLocator.locateModule(workflowStep);
				// Process workflow response
				DSSignonUtil.processWorkflowResponse(signonMgr, workflowStep, workflowValue, module, request);
				
				// If there are more steps, then iterate to the next step.
				if (signonMgr.hasMoreWorkflowSteps()){
					// Get the next step
					workflowStep = manageNextStep(workflowStep, signonForm, req, user);
					// store to the next workflow step
					fowardStep = workflowStep;
				}
				else{
					// Commit the action.
					DSSignonUtil.commitAction(req, signonForm);
					// set next step to forward to the subsequent page afer the signon workflow is complete.
					fowardStep = SUCCESS_FORWARD;
				}
			}
		}
		// The signon worflow has not started, so get the first step to setup the intiation.
		else{
			// Reset the action.
			DSSignonUtil.resetAction(req);
			// This would be the first signon workflow step.
			workflowStep = manageNextStep(workflowStep, signonForm, req, user);
			// store to the next workflow step
			fowardStep = workflowStep;
		}
		// return the next workflow step.
		return fowardStep;
	}

	/**
	 * This manages the next signon workflow step by attaining the next step, persisting it on the form
	 * bean, and then resolving the forward to the next step.
	 * 
	 * @param workflowStep String value of the workflow step
	 * @param signonForm ActionForm form bean
	 * @param req HttpServletRequest object
	 * @param user CUOUser object
	 */
	private String manageNextStep(String workflowStep, DSSignonForm signonForm, HttpServletRequest req, CUOUser user)
		throws DSSignonException, SignonModuleException{
		ExUser exUser =(ExUser)user;
		// Get instance of signon manager
		DSSignonManager signonMgr = DSSignonManager.getInstance(req);
		// Default the next step to the current step.
		String nextWorkflowStep = workflowStep;
		// Get the next signon workflow step (this may even be the first).
		nextWorkflowStep = signonMgr.getNextWorkflowStep(workflowStep).trim();
		// Process workflow step.
		nextWorkflowStep = processWorkflowStep(nextWorkflowStep, user, signonForm, req);
		// return the workflow step.
		return nextWorkflowStep;
	}
	
	/**
	 * This method take in a SignonModuleException and calls overloaded method that
	 * works with non exception specific error handling.
	 * 
	 * @param req HttpServletRequest object
	 * @param errors ActionErrors object for error messages
	 * @param user CUOUser Object
	 * @param signonForm ActionForm bean.
	 * @param mapping ActionMapping to locate forward.
	 * @return String value of the forward.
	 */
	private String manageError(HttpServletRequest req, ActionErrors errors, SignonModuleException e, CUOUser user, DSSignonForm signonForm, ActionMapping mapping){
		// Initialize the forward step.
		String forwardStep = "";
		// Initialize the workflowStep to the module name of the exception.
		String workflowStep = e.getModuleName();
		try{
			// Recover from the error by determining the appropriate step to recover to. 
			forwardStep = manageRecoveryError(workflowStep, e, req, user, signonForm);
			// Now manage the eror now that the recovery step has been determined.
			forwardStep = this.manageError(req, errors, DSSignonUtil.buildMessageKey(e), user, signonForm, mapping, forwardStep, e.isProcessingException());;
		}
		catch(SignonModuleException ex){
			// A signon module exeption was caught so just manage the error.
			forwardStep = this.manageError(req, errors, DSSignonUtil.buildMessageKey(ex), user, signonForm, mapping, ex.getModuleName(), ex.isProcessingException());;
		}
		catch(DSSignonException ex){
			// If there another excepion, then default to the FAILURE forward.
			forwardStep = SIGNON_FAILURE_FORWARD;
		}
		// return the forward step.
		return forwardStep;
	}
	
	/**
	 * @param req HttpServletRequest object
	 * @param errors ActionErrors object for error messages
	 * @param messageKey String value for the message resource
	 * @param user CUOUser Object
	 * @param signonForm ActionForm bean.
	 * @param mapping ActionMapping to locate forward.
	 * @param moduleName String value of the module/workflow step name
	 * @param exceptionProcessing boolean value indicating that an exception 
	 * 					occurred during processing rather than post processing
	 * @return String value of the forward.
	 */
	private String manageError(HttpServletRequest req, ActionErrors errors, String messageKey, CUOUser user, DSSignonForm signonForm, ActionMapping mapping, String moduleName, boolean exceptionProcessing){
		// Holds the forward mapping
		String forwardStep = "";
		// Get the Message Resource for the messageKey
		MessageResources messageResources = (MessageResources)getResources(req);
		// Message text
		String msgText = messageResources.getMessage(messageKey.toString());
		
		// If the message text was not found, then use a generic message.
		if(msgText == null){
			// Add the error message for the exception occured.
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(GENERAL_INTERNAL_ERROR, user.getUserId() + " " + new Timestamp(System.currentTimeMillis())));
		}
		// Message resource was found, so use this.
		else{
			errors.add(SIGNON_KEY,new ActionMessage(messageKey));
		}
		// Look for forward by message key.  This if for cases when using exact error type for error forward.
		ActionForward forward = mapping.findForward(messageKey);
		// If one is not found, then default to the signon failure forward.
		if (forward == null){
			forward = mapping.findForward(SIGNON_FAILURE_FORWARD);
			forwardStep = SIGNON_FAILURE_FORWARD;
		}
		else{
			// If a forward was found then look it up by message key.  This would 
			// a error specific forward.
			forwardStep = messageKey;
		}
		// If exception occurred during processing (as oppossed to post processing) then
		// do NOT process again because it will just fail again.
		if (!exceptionProcessing){
			// Try to re process the curent step to reestablish workflow step state.
			try{
				// Process workflow step and set the forward step.
				forwardStep = processWorkflowStep(moduleName, user, signonForm, req);
			}
			// An exception occurred while 
			catch(SignonModuleException e){
				// Error handling already done.
			}
			// If an exception occurred, then null out the forward to cause a generic error.
			catch(Exception e){
				forward = null;
			}
		}
		// If no forward was found, or exception occured during re-processing, then go to generic error forward.
		if (forward == null){
			forwardStep = FAILURE_FORWARD;
		}
		// Return the forward name.
		return forwardStep;
	}
	
	/**
	 * @param workflowStep String value of the workflow step.
	 * @param user CUOUser object
	 * @param signonForm ActionForm bean
	 * @param req HttpServletRequest
	 * @return String value of the forward.
	 * @throws DSSignonException Exception occurred during processing
	 * @throws SignonModuleException Exception occurred in specific module.
	 */
	private String processWorkflowStep(String workflowStep, CUOUser user, DSSignonForm signonForm, HttpServletRequest req)throws DSSignonException, SignonModuleException{
		ISignonModuleResponse response = null;
		DSSignonManager signonMgr = DSSignonManager.getInstance(req);
		// Execute signon modules.
		ISignonModule module = SignonModuleLocator.locateModule(workflowStep);
		// Setup signon request
		ISignonModuleRequest request = SignonModuleRequest.createRequest(workflowStep, signonMgr.getSignOnTransferObject(), (ExUser)user);		
		response = DSSignonUtil.processWorkflowModule(module, request);

		// If response from the signon module indicate to bypass then add the response object to DSSignonTO
		// and move to the next step.
		if (response.isBypass()){
			workflowStep =	processSignonWorfklow(workflowStep, (String)response.getSignonResponse(), signonForm, req, user);
		}
		else{
			// Persist it in the form
			DSSignonUtil.persistStateData(signonForm, workflowStep, response.getSignonResponse());
		}
		// Return the workflow step.
		return workflowStep;
	}
	
	/**
	 * @param previousWorkflowStep String value of the previous 
	 * @param currentWorkflowStep
	 * @param e
	 * @param req
	 * @param user
	 * @param signonForm
	 * @return
	 * @throws DSSignonException
	 * @throws SignonModuleException
	 */
	private String manageRecoveryError(String currentWorkflowStep, SignonModuleException e, HttpServletRequest req, CUOUser user, DSSignonForm signonForm)
		throws DSSignonException, SignonModuleException{
		// Initialize object response value.
		Object workflowValue = null;
		// Signon response
		ISignonModuleResponse response = null;
		// Get the previous step.
		String previousWorkflowStep = DSSignonManager.getPreviousWorkflowStep(currentWorkflowStep);
		// Iniitialize the next step to be the previous step.
		String nextWorkflowStep = previousWorkflowStep;
		// If the exception is a processing exception, then try to see if the previous step can be recovered.  If it can be, then use this
		// as the next forward step.  If an error occurs while processing the previous step, then throw the original exception.
		if (e.isProcessingException()){
			DSSignonManager signonMgr = DSSignonManager.getInstance(req);
			// Execute signon modules.
			ISignonModule module = SignonModuleLocator.locateModule(previousWorkflowStep);
			// Setup signon request
			ISignonModuleRequest request = SignonModuleRequest.createRequest(previousWorkflowStep, signonMgr.getSignOnTransferObject(), (ExUser)user);
			
			try{
				response = DSSignonUtil.processWorkflowModule(module, request);
				nextWorkflowStep = previousWorkflowStep;
			}
			// An exception occurred during process, so setup exeption and continue to throw it up.
			catch(SignonModuleException sme){
				// TODO Somehowe make this failure.
				throw e;
			}
		}
		// If the exception is a post rocessing exception, then try to see if the current step can be recovered.  If it can be, then use this
		// as the next forward step.  If an error occurs while re-processing the current step, then throw the original exception.
		else if (e.isPostProcessingException()){
			DSSignonManager signonMgr = DSSignonManager.getInstance(req);
			// Execute signon modules.
			ISignonModule module = SignonModuleLocator.locateModule(currentWorkflowStep);
			// Setup signon request
			ISignonModuleRequest request = SignonModuleRequest.createRequest(currentWorkflowStep, signonMgr.getSignOnTransferObject(), (ExUser)user);
			
			try{
				response = DSSignonUtil.processWorkflowModule(module, request);
				nextWorkflowStep = currentWorkflowStep;
			}
			// An exception occurred during process, so setup exeption and continue to throw it up.
			catch(SignonModuleException sme){
				// TODO Somehowe make this failure.
				throw e;
			}			
		}
		// Validate that a response object was returned.
		if (response == null){
			throw new DSSignonException("DSSIGNON001", "Invalid response returned from signon module: " + currentWorkflowStep);
		}
		
		// If response from the signon module indicate to bypass then to avoid getting back into the point failure, then just return the original
		// exception.
		if (response.isBypass()){
			// TODO Somehowe make this failure.
			throw e;
		}
		else{
			workflowValue = response.getSignonResponse();
		}
		// Set the state datat on the form.
		DSSignonUtil.persistStateData(signonForm, nextWorkflowStep, workflowValue);
		// Return the workflow step.
		return nextWorkflowStep;
	}
	
	/**
	 * This is method is not used in this action and should never be implemented/executed.
	 * 
	 * @see com.dtcc.dnv.otc.common.action.DSBaseAction#returnForward(com.dtcc.dnv.otc.common.security.model.IUser, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward returnForward(IUser user, ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception{
		throw new Exception("DSSignonAction: Invalid execution method used.");
	}
}