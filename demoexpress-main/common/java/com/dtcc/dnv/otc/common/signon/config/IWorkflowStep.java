package com.dtcc.dnv.otc.common.signon.config;

import java.lang.String;
/**
 * @author vxsubram
 * @date Aug 9, 2007
 * 
 * This interface represents the workflow step of the signon module.
 */
public interface IWorkflowStep {
	
	// Returns the Type of the Work flow step
	public String getWorkflowStepType();
	// Returns the Name of the Work flow step
	public String getWorkflowStepName();
	// Returns the value of the Work flow step attribute
	public String getWorkflowAttribute(String attrKey);
	// Returns the value of the Work flow module
	public String getWorkflowModule();
}
