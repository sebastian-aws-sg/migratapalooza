package com.dtcc.dnv.otc.common.signon.config;

/**
* @author vxsubram
*
* @date Aug 9, 2007
* @version 1.0
* 
* This class represent the workflow step of signon framework.
* 
* @version	1.1				September 2, 2007				sv
* Checked in for Cognizant.  Updated javadoc and comments.
*/
public class WorkflowStep implements IWorkflowStep {
	
	// Holds the type of the Workflow Step
	protected String wrkflowType = "";
	// Holds the name of the Workflow Step 
	protected String wrkflowName = "";
	// Holds the attribute to the workflow step
	protected SignonAttribute wrkflowStepAttr = null;
	// Holds the String name of the signon module that handles the workflow implementation
	protected String signonModule = null;
	
	/**
	 * Protected scope constructor because it should only be created by configuration mangers.
	 */
	protected WorkflowStep(){
	}
	/** 
	 * @return the workflowstep type name
	 */
	public String getWorkflowStepType() {
		return wrkflowType;
	}
	/** 
	 * @return the workflowstep name
	 */
	public String getWorkflowStepName() {
		return wrkflowName;
	}
	/** 
	 * @return the workflow attribute for a given attribute key
	 * @param attrKey String Key of the attribute
	 */
	public String getWorkflowAttribute(String attrKey) {
		return (String)wrkflowStepAttr.getAttribute(attrKey);
	}
	/** 
	 * @return the registered workflow module
	 */
	public String getWorkflowModule() {
		return signonModule;
	}
	/** 
	 * Registers the workflowstep type for the workflow
	 * @param wrkflowType String the value of the workflowstep type 
	 */
	void setWorkflowStepType(String wrkflowType) {
		this.wrkflowType = wrkflowType;
	}
	/** 
	 * Registers the workflowstep name for the workflow
	 * @param wrkflowName String the value of the workflowstep name
	 */
	void setWorkflowStepName(String wrkflowName) {
		this.wrkflowName = wrkflowName;
	}
	/** 
	 * Registers the workflow attribute of the workflow
	 * @param wrkflowStepAttr the attribute of a workflow
	 * @see SignonAttribute
	 */
	void setWorkflowAttribute(SignonAttribute wrkflowStepAttr) {
		this.wrkflowStepAttr = wrkflowStepAttr;
	}
	/**
	 * Registers the workflow module for the workflow
	 * @param signonModule the name of the workflow module handler for the workflow
	 */
	void setWorkflowModule(String signonModule) {
		this.signonModule = signonModule;
	}
}
