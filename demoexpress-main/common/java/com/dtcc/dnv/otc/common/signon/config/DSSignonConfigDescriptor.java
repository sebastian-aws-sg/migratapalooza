package com.dtcc.dnv.otc.common.signon.config;

import java.util.Vector;

/**
 * @author vxsubram
 * @date Aug 13, 2007
 * @version 1.0
 *  This class serves as the descriptor object holding the workflow configuration details
 * 
 * @version	1.1				September 2, 2007				sv
 * Updated javadoc.  Checked in for Cognizant.
 * 
 * @version	1.2				September 11, 2007				vx
 * Removed the Signon workflow Module handler implementation.
 */
class DSSignonConfigDescriptor {

	//	 Holds the workflow steps
	private Vector workflowSteps = new Vector();
	// Holds the Global Attributes of signon module
	private SignonAttribute globalAttr = new SignonAttribute();
	// Holds the WorkflowStepList
	WorkflowStepList workflowStepList = new WorkflowStepList();
	
	/**
	 * Returns the global attribute value of the given attribute key
	 * @param String the global attribute key
	 * @return the global attribute value.
	 */
	public String getGlobalAttr(String key) {
		return (String)globalAttr.getAttribute(key);
	}
	/**
	 * Sets the global attribute of the signon framework
	 * @param globalAttr the global attribute to set.
	 */
	void setGlobalAttr(SignonAttribute globalAttr) {
		this.globalAttr = globalAttr;
	}
	/**
	 * Returns the workflow step list
	 * @return the workflowStepList.
	 * @see WorkflowStepList
	 */
	public WorkflowStepList getWorkflowStepList() {
		return workflowStepList;
	}
	/**
	 * Sets the workflow step list
	 * @param workflowStepList the workflowStepList to set.
	 */
	void setWorkflowStepList(WorkflowStepList workflowStepList) {
		this.workflowStepList = workflowStepList;
	}
	/**
	 * Gets the collection of workflow steps of the sigon framework
	 * @return the collection of workflowSteps
	 */
	public Vector getWorkflowSteps() {
		return workflowSteps;
	}
	/**
	 * Sets the workflow steps of the signon framework
	 * @param workflowSteps the collection of workflowSteps to set.
	 */
	void setWorkflowSteps(Vector workflowSteps) {
		this.workflowSteps = workflowSteps;
	}
}
