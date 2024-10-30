package com.dtcc.dnv.otc.common.signon.config;

import java.util.Hashtable;

/**
 * @author vxsubram
 * @date Aug 9, 2007
 * @version 1.0
 * 
 * This class contains the list of steps in entire signon framework workflow.
 * 
 * @version	1.1			September 2, 2007				sv
 * Checked in for Cognizant.  Updated comments and javadoc.  Updated getWorkflowStep()
 * to return IWorkflowStep.
 */

class WorkflowStepList {

	// Holds collection of workflow steps in signon framework
	private Hashtable workflowSteps = new Hashtable();
	
	/**
	 * Adds workflow step of signon framework
	 * @param workflowName	- String value which identifies the workflow; 
	 * @param workflowStep	- IWorkflowStep instance implementation object
	 **/
	void addWorkflowStep(String workflowName, IWorkflowStep workflowstep){
		workflowSteps.put(workflowName,workflowstep);
	}
	
	/**
	 * Gets the Workflow step for the given type
	 * 
	 * @param type	- String value which identifies the workflow;
	 * @return the workflow step interface
	 * @see IWorkflowStep
	 */
	IWorkflowStep getWorkflowStep(String workflowName){
		return (IWorkflowStep)workflowSteps.get(workflowName);
	}
}
