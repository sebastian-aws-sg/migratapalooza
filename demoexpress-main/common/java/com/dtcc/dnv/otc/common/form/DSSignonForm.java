package com.dtcc.dnv.otc.common.form;

import org.apache.struts.action.ActionForm;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 *
 * 
 * @author Steve Velez
 * @date June 28, 2007
 * @version 1.0
 *
 * Form bean used for the DSSignon action for common signon framework.
 * 
 * @version	1.1				July 24, 2007			sv
 * Replaced specfic collection objects with generic signObject.
 */
public class DSSignonForm extends ActionForm
{
    // Private members used for maintining workflow
	 private String signonStep = "";
	 private String signonWorkflowValue = "";
	 // Instance members for holding collections for the screen
	 private Object signonObject = null;
	 

    /**
     * Resets the value of the form bean.
     */
    public void reset(){
        this.signonStep = "";
        this.signonWorkflowValue = "";
    }
    
	/**
	 * @return Returns the signonStep.
	 */
	public String getSignonStep() {
		return signonStep;
	}
	/**
	 * @param signonStep The signonStep to set.
	 */
	public void setSignonStep(String signonStep) {
		this.signonStep = signonStep;
	}

	/**
	 * @return Returns the signonWorkflowValue.
	 */
	public String getSignonWorkflowValue() {
		return signonWorkflowValue;
	}
	
	/**
	 * @param signonWorkflowValue The signonWorkflowValue to set.
	 */
	public void setSignonWorkflowValue(String signonWorkflowValue) {
		this.signonWorkflowValue = signonWorkflowValue;
	}
		
	/**
	 * @return Returns the signonObject.
	 */
	public Object getSignonObject() {
		return signonObject;
	}
	
	/**
	 * @param signonObject The signonObject to set.
	 */
	public void setSignonObject(Object signonObject) {
		this.signonObject = signonObject;
	}
}