package com.dtcc.dnv.otc.common.signon.config;


/**
 * @author vxsubram
 * @date Aug 9, 2007
 * 
 * This class contains the STD type workflow i.e. entitlement workflow
 */
public class STDWorkflowStep extends WorkflowStep {

	/**
	 * Default scope constructor because it should only be created by configuration mangers.
	 */
	STDWorkflowStep(){
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return this.wrkflowType + this.wrkflowName + this.wrkflowStepAttr.toString();
	}
}
