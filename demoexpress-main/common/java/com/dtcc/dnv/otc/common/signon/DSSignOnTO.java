package com.dtcc.dnv.otc.common.signon;

import java.util.Hashtable;

/**
 * @author Steve Velez
 * @date Jun 27, 2007
 * @version 1.0
 * 
 * This class serves as the transfer object for the implementing layer
 * after the signon module.
 * 
 * This transfer object can hole the following items:
 * 		values
 * 		attributes
 * 
 * Values are used to hold the results of each signon module.  This is 
 * manages by the signon framework.  Attributes are modules specific attribute 
 * that can be added to the transfer object.
 * 
 * @version	1.1					September 12, 2007				sv
 * Added facility to add attributes to the transfer object.  This is different
 * than values as values are controlled by the signon framework.  Attributes 
 * can be added by the modules.
 * 
 * @version	1.2					September 26, 2007				sv
 * Updated clear method to clear workflowAttributes
 * 
 * @version	1.3					October 17, 2007				sv
 * Added removeAttribute(String attributeKey) to allow implementing classes 
 * to clear or remove their values.		
 */
public class DSSignOnTO {
	// private members
	private Hashtable workflowValues = new Hashtable();
	private Hashtable workflowAttributes = new Hashtable();
	
	/**
	 * Returns the size of the collection of workflow values. 
	 * 
	 * @return
	 */
	public int size(){
		return this.getWorkflowValues().size();
	}
	
	/**
	 * Clears the collection of registered workflow values.
	 */
	void clear(){
		this.getWorkflowValues().clear();
		this.workflowAttributes.clear();
	}
	
	/**
	 * Removes a workflow item from the collection.
	 * 
	 * @param workflowStep String value of the name of the workflow item
	 */
	void remove(String workflowStep){
		this.getWorkflowValues().remove(workflowStep);
	}
	
	/**
	 * Removes the attribute from the transfer object.
	 * 
	 * @param attributeKey String avlue of attribute key.
	 */
	public void removeAttribute(String attributeKey){
		this.getWorkflowAttributes().remove(attributeKey);
	}
	
	/**
	 * Adds a value for a workflow item.
	 * 
	 * @param name String value of the name of the workflow item
	 * @param value Object value for workflow item.
	 */
	void addValue(String name, Object value){
		this.getWorkflowValues().put(name, value);
	}
	
	/**
	 * @return Returns the workflowValues.
	 */
	private Hashtable getWorkflowValues() {
		if (this.workflowValues == null){
			this.workflowValues = new Hashtable();
		}
		return this.workflowValues;
	}
	
	/**
	 * Return the value for by worflow item name.
	 * 
	 * @param name String value of the name of the workflow item
	 * @return Object value for workflow item.
	 */
	public Object getValue(String name){
		return this.getWorkflowValues().get(name);
	}
	
	/**
	 * @return Returns the workflowAttributes.
	 */
	private Hashtable getWorkflowAttributes() {
		if (this.workflowAttributes == null){
			this.workflowAttributes = new Hashtable();
		}
		return this.workflowAttributes;
	}
	
	/**
	 * Adds a value for a workflow attribute.
	 * 
	 * @param name String value of the name of the workflow attribute
	 * @param value Object value for workflow attribute.
	 */
	public void setAttribute(String name, Object value){
		this.getWorkflowAttributes().put(name, value);
	}
	
	/**
	 * Return the attribute for by worflow item name.
	 * 
	 * @param name String value of the name of the workflow item
	 * @return Object value for workflow attribute.
	 */
	public Object getAttribute(String name){
		return this.getWorkflowAttributes().get(name);
	}
}
