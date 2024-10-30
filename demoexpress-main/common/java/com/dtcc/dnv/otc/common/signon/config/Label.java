package com.dtcc.dnv.otc.common.signon.config;

/**
* @author vxsubram
*
* @date Aug 29, 2007
* @version 1.0
* 
* This class represent the Label item of the workflow type Label.
* 
* @version	1.1				October 2, 2007		sv
* Checked in for Cognizant.  Updated Label to contain mutually exclusive property.
*/

public class Label {
	
	// Holds the Label name
	private String labelName;
	// Holds the Lable Value
	private String labelValue;
	//	 Holds the mutually exclusive property
	private boolean mutuallyExclusive = false;
	
	/**
	 * Default scope constructor because it should only be created by configuration mangers.
	 */
	Label(){
		
	}
	/**
	 * Gets the label name
	 * @return the label name.
	 */
	public String getLabelName() {
		return labelName;
	}
	/**
	 * Sets the label name
	 * @param labelName The label name to set.
	 */
	void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	/**
	 * Gets the Label value
	 * @return the label value.
	 */
	public String getLabelValue() {
		return labelValue;
	}
	/**
	 * Sets the label value
	 * @param labelValue The labelValue to set.
	 */
	void setLabelValue(String labelValue) {
		this.labelValue = labelValue;
	}
	/**
	 * @return Returns the mutuallyExclusive.
	 */
	public boolean isMutuallyExclusive() {
		return mutuallyExclusive;
	}
	/**
	 * Sets the Mutually Exclusive property of the role
	 * @param mutuallyExclusive The mutuallyExclusive to set.
	 */
	void setMutuallyExclusive(boolean mutuallyExclusive) {
		this.mutuallyExclusive = mutuallyExclusive;
	}	
}
