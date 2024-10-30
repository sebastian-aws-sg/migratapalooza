package com.dtcc.dnv.otc.common.signon;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.dtcc.dnv.otc.common.signon.config.DSSignonConfiguration;
import com.dtcc.dnv.otc.common.signon.config.DSSignonConfigurationException;
import com.dtcc.dnv.otc.common.signon.exception.DSSignonException;

/**
 * @author Steve Velez
 * @date Jun 24, 2007
 * @version 1.0
 * 
 * This class manages the worflow registered with the signon module.
 * 
 * @version	1.1				July 27, 2007			sv
 * Updated comments for getter methods for signon transfer object.
 * 
 * @version	1.2				August 30, 2007			sv
 * Added exception handling.  Update state management by updating the management
 * of the index.  All indices always start with 0 and NOT 1.  Added some private
 * utility methods to handle the workflow index to isolate changes to this index.
 * 
 * @version	1.3				October 17, 2007		sv
 * Made the configuration object static.  Added static getPreviousWorkflowStep() so
 * implementors that have access to the signonmanager can have a statelss lookup of
 * the previous step in reference.  This is also intended to NOT force implementors
 * to have to get an instance of the configuration as that should stay in the signon
 * layer.  Updated locateWorkflowStep() to be a static method as it is not related to 
 * the actual state of the signon manager, but serves moreas a utility method.
 * 
 * @version	1.4				October 18, 2007		sv
 * Updated class to implement Serializable since it is placed in the session.
 */
public class DSSignonManager implements Serializable{
	// Instance member
	// This index always start at 0, NOT 1.
	private int workflowIndex = 0;
	private DSSignOnTO signOnTransferObject = new DSSignOnTO();
	
	// Static members
	private static DSSignonConfiguration config = null;
	
	// Static constant members.
	private static final String SIGNON_TO_ID = "dsSignonTO";
	private static final String SIGNONMGR_ID = "dsSignonMgr"; 
	
	/**
	 * Private constructor.  Client methods should the getInstance().
	 * Created a static instance of the configuration object.
	 */
	private DSSignonManager() throws DSSignonException{
		try{
			// Get the configuration instance.
			config = DSSignonConfiguration.getInstance();
		}
		catch(DSSignonConfigurationException e){
			throw new DSSignonException("DSSMGR001","Exception occurred getting configuration instance: " + e.getMessage());
		}
	}
	
	/**
	 * This methods returns the next workflow step based on the current step.
	 * Essentially this returns the step after the value described by the 
	 * workflowStep parameter.
	 * 
	 * @param workflowStep String value of the current step.
	 * @return String value of the step after "workflowStep"
	 */
	public String getNextWorkflowStep(String workflowStep){
		// Get the location of the step passed in.  This will -1 if it cannot be located.
		int workflowStepIndex = locateWorkflowStep(workflowStep);
		int currentWorkflowIndex = this.workflowIndex;
		// If the workflowstep passed is empty then rest the state of tthe signon mgr 
		if (workflowStep == null || workflowStep.trim().length() == 0){
			this.reset();
		}
		// if the completed workflow step is before the current state, then reset to the one after the completed workflow step
		else if (workflowStepIndex < (currentWorkflowIndex)){
			adjustWorkflow(workflowStepIndex);
		}
		// If the completed workflow step is after the current state, then trust the current state and leave it alone.
		else if (workflowStepIndex > (currentWorkflowIndex)){
			// Somehow the workflow got ahead of itself.  Just stay where it is
		}
		else if (workflowStepIndex == (currentWorkflowIndex)){
			// The workflow is in the correct state, so advance the index.
			currentWorkflowIndex++;
			this.setWorkflowIndex(currentWorkflowIndex);
		}
		return config.getWorkflowSteps(currentWorkflowIndex);
	}
	
	public static String getPreviousWorkflowStep(String workflowStep){
		// Initialize previous workflow step value to ""
		String prevWorkflowStep = "";
		// Get the location of the step passed in.  This will -1 if it cannot be located.
		int workflowStepIndex = locateWorkflowStep(workflowStep);
		// If the workflowstep passed is empty or cannot be found then default to "" 
		if (workflowStep == null || workflowStep.trim().length() == 0 || workflowStepIndex < 0){
			prevWorkflowStep = "";
		}
		// If the workflow step is the first one, then just return the same step.
		else if (workflowStepIndex == 0){
			prevWorkflowStep = workflowStep;
		}
		// Otherwise set the step to be the the step before workflowStep.
		else{
			prevWorkflowStep = config.getWorkflowSteps(workflowStepIndex - 1);
		}
		// Return the previous workflow step.
		return prevWorkflowStep;
	}
	
	/**
	 * Returns boolean value indicating if there are more steps int he workflow.
	 * 
	 * @return bollean value.  True-if there are more steps, False-if there are no more steps.
	 */
	public boolean hasMoreWorkflowSteps(){
		return this.workflowIndex < (config.getNumOfWorkflowSteps()-1);
	}
	
	/**
	 * Resests the signon mgr by adjusting the index back the begininig and clearing the stored values previouslly
	 * sotred.
	 */
	public void reset(){
		this.setWorkflowIndex(-1);
		this.signOnTransferObject.clear();
	}
	
	/**
	 * Add a workflow value for the respective step.
	 * 
	 * @param workflowName String value of the name of the workflow stpe to the value on
	 * @param value Object value to store for the workflow step
	 */
	public void addWorkflowValue(String workflowName, Object workflowValue){
		// Add the value
		this.signOnTransferObject.addValue(workflowName, workflowValue);
		this.incrementWorkflowIndex();
		// Locate the index of the step.
		int workflowLocation = locateWorkflowStep(workflowName);
		// If the step is consistent with the state of the signon mgr, then increment the index.
		if (this.workflowIndex == workflowLocation){
			// Do nothing, this is normal.
		}
		// If the step is inconsistnt with the state of the sigon mgr, then adjust the state of the workflow.
		else{
			adjustWorkflow(workflowLocation);
		}
	}
	
	/**
	 * Returns the boolean value indicating of the signon mgr has started.
	 * 
	 * @return boolean value of whether or not the workflow has started.
	 */
	public boolean started(){
		return workflowIndex > -1;
	}
	
	/**
	 * Locates the workflow step.
	 * 
	 * @param workflowStepToLocate String value of the name of the workflow step to find.
	 * @return int value of the index of the workflow step.
	 */
	private static int locateWorkflowStep(String workflowStepToLocate){
		boolean workflowStepFound = false;
		int workflowStepIndex = -1;
		// Loop through the configuration to find the workflow step.
		for (int i = 0; i < config.getNumOfWorkflowSteps() && !workflowStepFound; i++){
			String workflowStep = config.getWorkflowSteps(i);
			if (workflowStepToLocate.equalsIgnoreCase(workflowStep)){
				workflowStepFound = true;
				workflowStepIndex = i;
			}
		}
		return workflowStepIndex;
	}
	/**
	 * Returns the current state of the transfer known to the signon mamanger.
	 * This should NOT be used by post signon modules.
	 * 
	 * @return Returns the signOnTransferObject.
	 */
	public DSSignOnTO getSignOnTransferObject() {
		return signOnTransferObject;
	}
	
	/**
	 * This method is used as a factory method to create an instance of the signon mgr.
	 * 
	 * @param req HttpServletRequest object used to locate and store the signon mgr
	 * @return DSSignonManager Instance value.
	 */
	public static DSSignonManager getInstance(HttpServletRequest req) throws DSSignonException{
		// Look in the session for the signon mgr
		DSSignonManager dsSignonMgr = (DSSignonManager)req.getSession(true).getAttribute(SIGNONMGR_ID);
		// If one doe snot exist, then instantiate one.
		if (dsSignonMgr == null){
			// Instante one
			dsSignonMgr = new DSSignonManager();
			// Store the instance.
			req.getSession(true).setAttribute(SIGNONMGR_ID, dsSignonMgr);
		}
		return dsSignonMgr;
	}
	
	/**
	 * Prepare the signonTO for the application.
	 * 
	 * @param req HttpServletRequest object
	 */
	public void commit(HttpServletRequest req){
		// Store transfer object in the request and forward.
		req.setAttribute(SIGNON_TO_ID, this.getSignOnTransferObject());
	}
	
	/**
	 * This method should be used by post signon modules.  This is the 
	 * commited signon transfer object.
	 * 
	 * @param req
	 * @return
	 */
	public static DSSignOnTO getSignonTO(HttpServletRequest req){
		// Store transfer object in the request and forward.
		return (DSSignOnTO)req.getAttribute(SIGNON_TO_ID);
	}
	
	/**
	 * Adjust the workflow where the current index is not accurate to the curent state.
	 * 
	 * @param currentworkflowIndex int value of what is thought to be the current state.
	 */
	private void adjustWorkflow(int currentworkflowIndex){
		// Loop through the transfer object and remove everything that comes after the new current state,
		// which is driven by currentworkflowIndex
		for (int i = currentworkflowIndex+1; i < this.signOnTransferObject.size(); i++){
			this.signOnTransferObject.remove(config.getWorkflowSteps(i));
		}		
		this.setWorkflowIndex(this.signOnTransferObject.size()-1);
	}
	
	/**
	 * Private utility method to increment the workflow index.
	 */
	private void incrementWorkflowIndex(){
		this.setWorkflowIndex(this.workflowIndex+1);
	}
	
	/**
	 * Private utility method to isolate all change to the workflow index since it is a
	 * critical memmber of the signon manager.
	 * 
	 * @param index
	 */
	private void setWorkflowIndex(int index){
		// If for some reason a negative number is passed
		// then set to the first index which is 0.
		if (index < 0){
			this.workflowIndex = 0;
		}
		// Set the index.
		else{
			this.workflowIndex = index;
		}
	}
}
