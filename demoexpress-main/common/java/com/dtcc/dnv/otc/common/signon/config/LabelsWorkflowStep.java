package com.dtcc.dnv.otc.common.signon.config;

import java.util.Collection;
import java.util.ArrayList;


/**
* @author vxsubram
*
* @date on Aug 9, 2007
* @version 1.0
* 
* This class represent the Labels Type workflow step of signon framework.
* 
* @version	1.1				September 2, 2007				sv
* Checked in for Cognizant.  Updated comments and javadoc.  Added support for 
* Label object.
* 
* @version	1.2				September 16, 2007				sv
* Checked in for Cognizant.  Updated the Labels object collection type to 
* LinkedHashMap from Hashtable in order to maintain order of insertion.
* 
* @version	1.3				October 03, 2007				sv
* Checked in for Cognizant.  Updated the Labels object collection type to 
* ArrayList from LinkedHashMap in order to be compatible with JDK 1.3.
*/
public class LabelsWorkflowStep extends WorkflowStep {
	
	// Holds the Label object list in order of insertion
	private ArrayList labels = new ArrayList();
	
	/**
	 * Default scope constructor because it should only be created by configuration mangers.
	 */
	LabelsWorkflowStep(){
	}
	/**
	 * Adds a Label object to the collection with key as labelName.
	 * @param labelName the label name of a label item
	 * @param label the Label object
	 * @see Label
	 */
	void addLabel(String labelName,Label label){
		labels.add(label);
	}
	/**
	 * Gets the collection of Labels
	 * @return the collection of all Labels
	 */
	public Collection getAllLabels(){
		return labels;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return this.wrkflowType + this.wrkflowName + this.wrkflowStepAttr.toString() + this.labels;
	}
}
