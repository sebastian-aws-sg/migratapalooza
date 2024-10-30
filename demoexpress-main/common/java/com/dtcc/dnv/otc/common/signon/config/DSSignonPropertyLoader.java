package com.dtcc.dnv.otc.common.signon.config;

import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.dtcc.dnv.otc.common.resourcelocator.LocatorConstants;
import com.dtcc.dnv.otc.common.resourcelocator.ResourceLocator;
import com.dtcc.dnv.otc.common.signon.DSSignonConstants;

/**
 * @author vxsubram
 * @date Aug 10, 2007
 * 
 * This class loads and parses the signon configuration property file and load 
 * the properties into signon object model.
 * 
 * @version	1.1				September 2, 2007				sv
 * Added usage of ResourceLocater.  Checked in for Cognizant.  Updated comments and javadoc.
 * Added support for handling steps as generic workflow types (i.e. roles, std, etc).
 * 
 * @version	1.2				September 9, 2007				sv
 * Checked in for Cognizant.  Updated implementation to have  Role have a Label object.
 * 
 * @version	1.3				October 2, 2007					sv
 * Updated comments.  Checked in for Cognizant.  Updated the implementation to load the 
 * "mutuallyExclusive" property.
 * 
 * @version	1.4				October 8, 2007					sv
 * Checked in for Cognizant.  Updated to throw exception for missing mandatory properties. 
 * 
 * @version	1.5				October 17, 2007				sv
 * Updated error message in the exception in cases where the configuration file could 
 * not be found to contain the original exception message as well.
 * 
 * @version	1.6				October 17, 2007				sv
 * Updated typo from version 1.5 change.
 * 
 * @version	1.7				October 18, 2007				sv
 * Made logger static final.  Checked in for Cognizant.  Added more trimming logic.
 */
class DSSignonPropertyLoader {
	
	// Logger instance
	private static final Logger log = Logger.getLogger(DSSignonPropertyLoader.class);

	/**
	 * Loads the signon configuration file into signon object framework with filled in work flow and its attributes
	 * @return the signon configuration description object
	 * @throws DSSignonConfigurationException
	 * @see DSSignonConfigDescriptor
	 */
	DSSignonConfigDescriptor getSignonWorkflows() throws DSSignonConfigurationException{
		// Create an instance of a Properties object for reading the Signon Properties 
		Properties props = new Properties();
		
		DSSignonConfigDescriptor signonDescriptor = null;
		
		try{
			// Load propery file configuration.
			props.load(ResourceLocator.getInstance().findResourceAsStream(DSSignonConstants.CONFIG_FILENAME, LocatorConstants.CLASS_PATH));
		}catch(Exception ex){
			throw new DSSignonConfigurationException(DSSignonConstants.EX_RROPS_LOAD_FAILED,DSSignonConstants.EX_RROPS_LOAD_FAILED_STR + "--" + ex.getMessage());
		}
		
		// Generate workflow steps
		signonDescriptor = generateWorkflowItems(props);
		// Generate Global Attributes
		signonDescriptor.setGlobalAttr(generateGlobalAttribute(props));
		
		// return DSSignonConfigDescriptor instance
		return signonDescriptor;
	}

	/**
	 * Generates workflow items from attributes in properties object.
	 * 
	 * @param props Properties object containing configuration for signon mgr.
	 * @return the signon configuration description object
	 * @throws DSSignonConfigurationException
	 * @see DSSignonConfigDescriptor
	 */
	private static DSSignonConfigDescriptor generateWorkflowItems(Properties props) throws DSSignonConfigurationException{
		// Instantiate DSSignonConfigDescriptor class
		DSSignonConfigDescriptor signonDescriptor = new DSSignonConfigDescriptor();
		
		
		// Holds the configured workflows of signon module
		String workflowsString = (String)props.get(DSSignonConstants.PREFIX + DSSignonConstants.ATTR_DELIMITER+DSSignonConstants.WORKFLOW_KEY);
		
		// Collection of workflows.
		Vector workflows = loadTokensIntoCollection(workflowsString);
		
		//Rule 1: workflows should be configured. If not, throw exception
		if(workflows.size()==0 )
			throw new DSSignonConfigurationException(DSSignonConstants.EX_MISSING_RROPS,DSSignonConstants.WRKFLW_MISSING_STR);
		
		// Holds the mapping of Workflow type and workflow steps
		WorkflowStepList workflowStepMaps = new WorkflowStepList();
		
		signonDescriptor.setWorkflowSteps(workflows);
		
		
		// Iterate through each workflow.
		for (int i =0; i < workflows.size(); i++){
			
			// Get a workflow of the signon
			String workflow = (String)workflows.get(i);
			
			// Get a workflow type of the workflow
			String workflowEntryType = (String)props.get(DSSignonConstants.PREFIX + DSSignonConstants.ATTR_DELIMITER+ 
					workflow + DSSignonConstants.ATTR_DELIMITER + DSSignonConstants.TYPE_KEY);
			// Process if workflow type entry is not null
			if (workflowEntryType != null){
				// Trim the String value
				workflowEntryType = workflowEntryType.trim();
				// If WorkflowItem is of Type roles
				if(workflowEntryType.equalsIgnoreCase(DSSignonConstants.TYPE_ROLE)){
					RoleWorkflowStep roleWorkflowStep = processRoleTypeWorkflow(workflow,workflowEntryType,props);
					// map workflow step to workflow type.
					workflowStepMaps.addWorkflowStep(workflow, roleWorkflowStep);
				}// If WorkflowItem is of Type STD
				else if(workflowEntryType.equalsIgnoreCase(DSSignonConstants.TYPE_STD)){
					STDWorkflowStep stdRoleWorkflowStep = processSTDTypeWorkflow(workflow,workflowEntryType,props);
					// map workflow step to workflow type.
					workflowStepMaps.addWorkflowStep(workflow, stdRoleWorkflowStep);

				}// If WorkflowItem is of Type labels
				else if(workflowEntryType.equalsIgnoreCase(DSSignonConstants.TYPE_LABELS)){
					LabelsWorkflowStep labelsWorkflowStep = processLabelsTypeWorkflow(workflow,workflowEntryType,props);
					// map workflow step to workflow type.
					workflowStepMaps.addWorkflowStep(workflow, labelsWorkflowStep);					
				}else{
					// Rule 3: Workflow Type implementation should be available. As of now ROLE, STD and LABELS implementation is handled. If not, throw exception
					throw new DSSignonConfigurationException(DSSignonConstants.EX_MISSING_RROPS,DSSignonConstants.WRKFLW_TYPE_MISSING_STR);
				}
			}else{
				// Rule 2: Each workflow should have a type. If not, throw exception
				throw new DSSignonConfigurationException(DSSignonConstants.EX_MISSING_RROPS,DSSignonConstants.WRKFLW_TYPE_MISSING_STR);
			}
		}
		// Sets the workflow configuration in workflow configuration descriptor object
		signonDescriptor.setWorkflowStepList(workflowStepMaps);
		// return DSSignonConfigDescriptor instance
		return signonDescriptor;
	}	
	
	/**
	 * Process workflow with TYPE=ROLE.
	 * 
	 * @param workflow String contains the workflow
	 * @param workflowItemType String contains the type of the workflow 
	 * @param props Properties object containing configuration for signon mgr.
	 * @return the role workflow step object containing all properties of work flow type "roles"
	 * @throws DSSignonConfigurationException
	 * @see RoleWorkflowStep
	 */
	private static RoleWorkflowStep processRoleTypeWorkflow(String workflow,String workflowItemType,Properties props) throws DSSignonConfigurationException{
		
		// Instantiates Role work flow step type class
		RoleWorkflowStep roleWorkflowStep = new RoleWorkflowStep();
		
		String workflowItemTypeValue = (String)props.getProperty(
				DSSignonConstants.PREFIX + DSSignonConstants.ATTR_DELIMITER + workflow + DSSignonConstants.ATTR_DELIMITER + workflowItemType);
		// Set the workflowstep type
		roleWorkflowStep.setWorkflowStepType(workflowItemType);
		// Set the workflow
		roleWorkflowStep.setWorkflowStepName(workflow);
		
		String module = (String)props.getProperty(
						DSSignonConstants.PREFIX + DSSignonConstants.ATTR_DELIMITER + workflow + DSSignonConstants.ATTR_DELIMITER + 
						DSSignonConstants.WORKFLOW_MODULE_KEY);
		// Trim the String value
		if(module != null)
			module = module.trim();
		
		// Set the workflow Module
		roleWorkflowStep.setWorkflowModule(module);
		
		SignonAttribute attributes = populateWorkflowAttributes(workflow,props);
		
		// Set the workflow attribute
		roleWorkflowStep.setWorkflowAttribute(attributes);
		
		// Build the global mutually exclusive property name for this workflow step.
		String workflowMutuallyExclusiveProp = (String)props.getProperty(
				DSSignonConstants.PREFIX + DSSignonConstants.ATTR_DELIMITER + workflow + DSSignonConstants.ATTR_DELIMITER + 
				DSSignonConstants.MUTUALLY_EXCLUSIVE);
		
		// Indicates whether roles are mutually exclusive for gloabl workflow step.
		boolean isRolesMutuallyExclusive = Boolean.valueOf(workflowMutuallyExclusiveProp).booleanValue();
		
		if(workflowItemTypeValue != null){
			StringTokenizer roleItems = new StringTokenizer(workflowItemTypeValue,DSSignonConstants.VALUE_DELIMITER);
			while(roleItems.hasMoreTokens()){
				// Instantiate the Role object
				Role role = new Role();
				// Instantiate the Label object
				Label roleLabel = new Label();
				
				String roleWorkflowItem = (String)roleItems.nextElement();
				
				// Sets the role workflow item
				role.setRoleName(roleWorkflowItem);
				
				String workflowItemName = (String)props.getProperty(DSSignonConstants.PREFIX + DSSignonConstants.ATTR_DELIMITER +
												workflow + DSSignonConstants.ATTR_DELIMITER + workflowItemType + DSSignonConstants.ATTR_DELIMITER +
												roleWorkflowItem + DSSignonConstants.ATTR_DELIMITER + DSSignonConstants.ROLE_NAME_KEY);
				// Sets the user roles 
				role.setUserRoles(workflowItemName);
				
				// Build the item specific mutually exclusive property.
				String wrkflowItmMutuallyExclusiveProp = (String)props.getProperty(DSSignonConstants.PREFIX + DSSignonConstants.ATTR_DELIMITER +
												workflow + DSSignonConstants.ATTR_DELIMITER + roleWorkflowItem + DSSignonConstants.ATTR_DELIMITER 
												+ DSSignonConstants.MUTUALLY_EXCLUSIVE);
				
				// If value is not found, then use the global boolean value.
				if(wrkflowItmMutuallyExclusiveProp != null){
				// Get the override of Mutually Exclusive property of each of the item in roles work flow
				boolean roleItemMutuallyExclusive = Boolean.valueOf(wrkflowItmMutuallyExclusiveProp).booleanValue();
					// Sets the item specific mutually exclusive property
					role.setMutuallyExclusive(roleItemMutuallyExclusive);
				}else{
					// Sets the globale workflow step mutually exclusive property
					role.setMutuallyExclusive(isRolesMutuallyExclusive);
				}
				
				String workflowItemValue = (String)props.getProperty(
						DSSignonConstants.PREFIX + DSSignonConstants.ATTR_DELIMITER + workflow + DSSignonConstants.ATTR_DELIMITER + workflowItemType + DSSignonConstants.ATTR_DELIMITER +
											roleWorkflowItem + DSSignonConstants.ATTR_DELIMITER + DSSignonConstants.ROLE_VALUE_KEY);
				// Rule 4: Workflow Item value cannot be null.If yes, then throw Exception
				if(workflowItemValue == null)
					throw new DSSignonConfigurationException(DSSignonConstants.EX_MISSING_RROPS,DSSignonConstants.WRKFLW_VALUE_MISSING_STR);
				else
					workflowItemValue = workflowItemValue.trim();
				
				// Sets the value for the role workflow item
				roleLabel.setLabelValue(workflowItemValue);
				
				String workflowItemLabel = (String)props.getProperty(
						DSSignonConstants.PREFIX + DSSignonConstants.ATTR_DELIMITER + workflow + DSSignonConstants.ATTR_DELIMITER + workflowItemType + DSSignonConstants.ATTR_DELIMITER +
											roleWorkflowItem + DSSignonConstants.ATTR_DELIMITER + DSSignonConstants.ROLE_LABEL_KEY);
				
				// Rule 5: Workflow Item label cannot be null.If yes, then throw Exception
				if(workflowItemLabel == null)
					throw new DSSignonConfigurationException(DSSignonConstants.EX_MISSING_RROPS,DSSignonConstants.WRKFLW_VALUE_MISSING_STR);
				
				// Sets the label for the role workflow item
				roleLabel.setLabelName(workflowItemLabel);
				
				// Sets the label in role instance
				role.setRoleLabel(roleLabel);
				
				// Add the role to the role work flow step
				roleWorkflowStep.addRole(roleWorkflowItem,role);
			}
		}
		//returns the RoleWorkflowStep instance
		return roleWorkflowStep;
	}
	
	/**
	 * process entitlement workflow with TYPE=STD.
	 * 
	 * @param workflow String contains the workflow
	 * @param workflowItemType String contains the type of the workflow 
	 * @param props Properties object containing configuration for signon mgr.
	 * @return the STD workflow step object containing all properties of work flow type "STD"
	 * @throws DSSignonConfigurationException
	 * @see STDWorkflowStep
	 */
	
	private static STDWorkflowStep processSTDTypeWorkflow(String workflow,String workflowItemType,Properties props) throws DSSignonConfigurationException{
		// Instantiate the STD workflow step class
		STDWorkflowStep stdWorkflowStep = new STDWorkflowStep();
		
		SignonAttribute attributes = populateWorkflowAttributes(workflow,props);
		// Set the workflow attribute
		stdWorkflowStep.setWorkflowAttribute(attributes);
		
		String module = (String)props.getProperty(
				DSSignonConstants.PREFIX + DSSignonConstants.ATTR_DELIMITER + workflow + DSSignonConstants.ATTR_DELIMITER + 
				DSSignonConstants.WORKFLOW_MODULE_KEY);
		
		// Trim the String value
		if(module != null)
			module = module.trim();
				
		// Set the workflowstep type
		stdWorkflowStep.setWorkflowStepType(workflowItemType);
		
		// Set the workflow module
		stdWorkflowStep.setWorkflowModule(module);
		
		// Set the workflow
		stdWorkflowStep.setWorkflowStepName(workflow);
		//returns the STDWorkflowStep instance
		return stdWorkflowStep;
	}
	
	/**
	 * process testProdIndicator workflow with TYPE=LABELS.
	 * 
	 * @param workflow String contains the workflow
	 * @param workflowItemType String contains the type of the workflow 
	 * @param props Properties object containing configuration for signon mgr.
	 * @return the labels workflow step object containing all properties of work flow type "labels"
	 * @throws DSSignonConfigurationException
	 * @see LabelsWorkflowStep
	 */
	private static LabelsWorkflowStep processLabelsTypeWorkflow(String workflow,String workflowItemType,Properties props) throws DSSignonConfigurationException{
		// Instantiates the LabelsWorkflowStep class
		LabelsWorkflowStep labelsWorkflowStep = new LabelsWorkflowStep();
		
		String workflowItemTypeValue = (String)props.getProperty(
				DSSignonConstants.PREFIX + DSSignonConstants.ATTR_DELIMITER + workflow + DSSignonConstants.ATTR_DELIMITER + workflowItemType);
		
		// Set the workflow step type
		labelsWorkflowStep.setWorkflowStepType(workflowItemType);
		// Set the workflow 
		labelsWorkflowStep.setWorkflowStepName(workflow);
		
		SignonAttribute attributes = populateWorkflowAttributes(workflow,props);
		// Set the workflow attribute
		labelsWorkflowStep.setWorkflowAttribute(attributes);
		
		String module = (String)props.getProperty(
				DSSignonConstants.PREFIX + DSSignonConstants.ATTR_DELIMITER + workflow + DSSignonConstants.ATTR_DELIMITER + 
				DSSignonConstants.WORKFLOW_MODULE_KEY);
		
		// Trim the String value
		if(module != null)
			module = module.trim();
		
		// Set the workflow module
		labelsWorkflowStep.setWorkflowModule(module);
		
		// Build the global mutually exclusive property name for this workflow step.
		String workflowMutuallyExclusiveProp = (String)props.getProperty(
				DSSignonConstants.PREFIX + DSSignonConstants.ATTR_DELIMITER + workflow + DSSignonConstants.ATTR_DELIMITER + 
				DSSignonConstants.MUTUALLY_EXCLUSIVE);
		
		// Indicates whether roles are mutually exclusive for gloabl workflow step.
		boolean isStepMutuallyExclusive = Boolean.valueOf(workflowMutuallyExclusiveProp).booleanValue();
		
		if(workflowItemTypeValue != null){
			StringTokenizer roleItems = new StringTokenizer(workflowItemTypeValue,DSSignonConstants.VALUE_DELIMITER);
			while(roleItems.hasMoreTokens()){
				// Instantiates a Label class 
				Label label = new Label();
				
				String workflowItemName = (String)roleItems.nextElement();
				
				// Sets the label name in the label object
				label.setLabelName(workflowItemName);
				
				String workflowItemValue = (String)props.getProperty(
						DSSignonConstants.PREFIX + DSSignonConstants.ATTR_DELIMITER + workflow + DSSignonConstants.ATTR_DELIMITER + workflowItemType + 
							DSSignonConstants.ATTR_DELIMITER +
									workflowItemName + DSSignonConstants.ATTR_DELIMITER + DSSignonConstants.VALUE_KEY);
				
				// Rule 6: Workflow Item value cannot be null.If yes, then throw Exception
				if(workflowItemValue == null)
					throw new DSSignonConfigurationException(DSSignonConstants.EX_MISSING_RROPS,DSSignonConstants.WRKFLW_VALUE_MISSING_STR);
				else{
					workflowItemValue = workflowItemValue.trim();
				}
				
				// Build the item specific mutually exclusive property.
				String wrkflowItmMutuallyExclusiveProp = (String)props.getProperty(DSSignonConstants.PREFIX + DSSignonConstants.ATTR_DELIMITER +
						workflow + DSSignonConstants.ATTR_DELIMITER + workflowItemName 
						+ DSSignonConstants.ATTR_DELIMITER + DSSignonConstants.MUTUALLY_EXCLUSIVE);
				
				// If value is not found, then use the global boolean value.
				if(wrkflowItmMutuallyExclusiveProp != null){
					// Get the override of Mutually Exclusive property of each of the item in roles work flow
					boolean roleItemMutuallyExclusive = Boolean.valueOf(wrkflowItmMutuallyExclusiveProp).booleanValue();
					// Sets the item specific mutually exclusive property
					label.setMutuallyExclusive(roleItemMutuallyExclusive);
				}else{
					// Sets the globale workflow step mutually exclusive property
					label.setMutuallyExclusive(isStepMutuallyExclusive);
				}
					
				// Sets the label value in the label object
				label.setLabelValue(workflowItemValue);
				//Add the label to the work flow step
				labelsWorkflowStep.addLabel(workflowItemName,label);
			}
		}
		//returns the LabelsWorkflowStep instance
		return labelsWorkflowStep;
	}
	
	/**
	 * Process attributes of workflow.
	 * 
	 * @param workflow String contains the workflow
	 * @param props Properties object containing configuration for signon mgr.
	 * @return the attributes of the workflow
	 * @see SignonAttribute
	 */
	private static SignonAttribute populateWorkflowAttributes(String workflow,Properties props){
		// Instantiate SignonAttribute class
		SignonAttribute workflowAttr = new SignonAttribute();
		// Get the workflow attribute key
		String workflowItemAttrKey = DSSignonConstants.PREFIX + DSSignonConstants.ATTR_DELIMITER + workflow + DSSignonConstants.ATTR_DELIMITER + DSSignonConstants.ATTR_KEY;
		// Get the iterator for the workflow properties	
		Iterator propKeysIter = props.keySet().iterator();
		// Iterate through the workflow properties
		while(propKeysIter.hasNext()){
			String propsKey = (String)propKeysIter.next();
			
			if(propsKey.startsWith(workflowItemAttrKey)){
				// Get the attribute key
				String workflowItemAttr = propsKey.substring(propsKey.indexOf(workflowItemAttrKey) + workflowItemAttrKey.length() + 1,propsKey.length());
				// Get the attribute value
				String workflowItemAttrValue = (String)props.getProperty(workflowItemAttrKey + DSSignonConstants.ATTR_DELIMITER + workflowItemAttr);
				// Add the attribute value to the SignonAttribute
				workflowAttr.setAttribute(workflowItemAttr.trim(),workflowItemAttrValue.trim());
			}
		}
		// return the SignonAttribute
		return workflowAttr;
	}
	/**
	 * Process global attributes applicable to signon module.
	 * @param props Properties object containing configuration for signon mgr.
	 * @return the global attributes of the signon framework
	 * @see SignonAttribute
	 */
	private static SignonAttribute generateGlobalAttribute(Properties props){
		// Instantiate SignonAttribute class
		SignonAttribute workflowAttr = new SignonAttribute();
		// Get the global attribute key
		String workflowItemAttrKey = DSSignonConstants.PREFIX + DSSignonConstants.ATTR_DELIMITER + DSSignonConstants.ATTR_KEY;
		// Get the iterator for the global properties
		Iterator propKeysIter = props.keySet().iterator();
		// Iterate through the global properties
		while(propKeysIter.hasNext()){
			String propsKey = (String)propKeysIter.next();
			
			if(propsKey.startsWith(workflowItemAttrKey)){
				// Get the attribute key
				String workflowItemAttr = propsKey.substring(propsKey.indexOf(workflowItemAttrKey) + workflowItemAttrKey.length() + 1,propsKey.length());
				// Get the attribute value
				String workflowItemAttrValue = (String)props.getProperty(workflowItemAttrKey + DSSignonConstants.ATTR_DELIMITER + workflowItemAttr);
				// Add the attribute value to the SignonAttribute
				workflowAttr.setAttribute(workflowItemAttr.trim(),workflowItemAttrValue.trim());
			}
		}
		//return the SignonAttribute
		return workflowAttr;
	}
	
	/**
	 * Utility to create a vector of tokens based on the tokenString.
	 * 
	 * @param tokenString String value of tokens to be loaded.
	 * @return Vector object represting string value of tokens.
	 */
	private static Vector loadTokensIntoCollection(String tokenString){
		// Instantiate vector.
		Vector collection = new Vector();
		// Instantiate StringTokenizer
		StringTokenizer st = new StringTokenizer(tokenString,DSSignonConstants.VALUE_DELIMITER);

		// Loop through tokenizer tokens.
		while (st.hasMoreTokens()){
			// Add token to vector.
			collection.add(st.nextToken().trim());
		}
		// Return Vector.
		return collection;
	}
	
}
