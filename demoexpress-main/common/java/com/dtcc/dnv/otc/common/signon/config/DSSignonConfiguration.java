package com.dtcc.dnv.otc.common.signon.config;

import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * @author Steve Velez
 * @date Jun 24, 2007
 * @version 1.0
 * 
 * This class represent the configuration for the signon manager.
 * 
 * @vesrion	1.1				September 2, 2007				sv
 * Updated javadoc.  Checked in for Cognizant.
 * 
 * @version	1.2				September 11, 2007				sv
 * Checked in for Cognizant.  Updated the Signon workflow Module 
 * handler implementation.
 * 
 * @version	1.3				October 18, 2007				sv
 * Updated logger to be static final.
 */
public class DSSignonConfiguration {
	// Static instance members
	private static DSSignonConfiguration config = null;
	private static final Logger log = Logger.getLogger(DSSignonConfiguration.class);
	
	// Holds alll the workflow system attributes
	private static DSSignonConfigDescriptor configDescriptor = null;
	
	/**
	 * Private constructor.  Only accessed by internal factory method.
	 */
	private DSSignonConfiguration(){
	}
	
	/**
	 * Gets the singleton instance of a configuration object.
	 * 
	 * @return the sigon configuration instance containing the sigon properties
	 */
	public static DSSignonConfiguration getInstance() throws DSSignonConfigurationException{
		// If instance does not already exist, then create one.
		if (config == null){
			createInstance();
		}
		// Return singleton instance of the configuration object.
		return config;
	}
	
	/**
	 * Synchronized method to actually create an instance of this configuration object.
	 * @throws DSSignonConfigurationException
	 */
	private synchronized static void createInstance() throws DSSignonConfigurationException{
		// Should only be NOT null when a this thread was waiting for another thread which already created it.
		if (config == null){
			// Create an instance of the object.
			config = new DSSignonConfiguration();
			
			// Create an instance of DSSignonConfigManager
			DSSignonConfigManager signonConfigmgr = new DSSignonConfigManager();
			DSSignonPropertyLoader propLoader = signonConfigmgr.getPropertyLoader();
			configDescriptor = propLoader.getSignonWorkflows();
			
		}
	}
	
	/**
	 * Returns the workflow step of the signon framework
	 * @return the collection of workflowSteps.
	 */
	private Vector getWorkflowSteps() {
		return configDescriptor.getWorkflowSteps();
	}
	
	/**
	 * Methods used to return workflow step by index
	 * 
	 * @param index int value to return workflow step.
	 * @return String value of workflow step.
	 */
	public String getWorkflowSteps(int index){
		return (String)configDescriptor.getWorkflowSteps().get(index);
	}
	
	/**
	 * Method used to return the number of registered workflow steps.
	 * 
	 * @return int size of the number of workflows.
	 */
	public int getNumOfWorkflowSteps(){
		return configDescriptor.getWorkflowSteps().size();
	}
	
	/**
	 * Method used to workflow steps type for the specific module handler.
	 * @param type the String value indicating the workflow step type
	 * @return the interface IWorkflowStep.
	 * @see IWorkflowStep
	 */
	public IWorkflowStep getWorkflowStepType(String type){
		return (IWorkflowStep)configDescriptor.getWorkflowStepList().getWorkflowStep(type);
	}
	
	/**
	 * Gets the global attribute of the signon module
	 * @param key the String value representing the key of the attribute
	 * @return the value of the global attribute for the given key
	 */
	public String getGlobalSignonAttribute(String key){
		return configDescriptor.getGlobalAttr(key);
	}
	
	/**
	 * Gets the module name of the workflow
	 * @param key the String value representing the module name of the work flow
	 * @return the module handler class of the workflow
	 */
	public String getWorkFlowModule(String key){
		return getWorkflowStepType(key).getWorkflowModule();
	}
	
}

