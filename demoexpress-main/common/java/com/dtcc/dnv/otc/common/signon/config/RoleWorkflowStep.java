package com.dtcc.dnv.otc.common.signon.config;

import java.util.Collection;
import java.util.ArrayList;


/**
* @author vxsubram
*
* @date Aug 9, 2007
* @version 1.0
* 
* This class represent the Role Type workflow step of signon framework.
* 
* @version	1.1				September 2, 2007				sv
* Checked in for Cognizant.  Updated comments and javadoc.  Added usage of
* Role object.
* 
* @version	1.2				September 16, 2007				sv
* Checked in for Cognizant.  Updated the Roles object collection type to LinkedHashMap 
* from Hashtable in order to maintain order of insertion.
* 
* @version	1.3				October 03, 2007				sv
* Checked in for Cognizant.  Updated the Roles object collection type to ArrayList ftom LinkedHashMap 
* in order to be compatible with JDK 1.3.
*/
public class RoleWorkflowStep extends WorkflowStep {

	// Holds the Roles object list in order of insertion
	private ArrayList roles = new ArrayList();
	
	/**
	 * Default scope constructor because it should only be created by configuration mangers.
	 */
	RoleWorkflowStep(){
	}
	/**
	 * Adds the Role to the collection with key as RoleName
	 * @param roleName String the role name of the role item
	 * @param role the Role object
	 * @see Role
	 */
	void addRole(String roleName,Role role){
		roles.add(role);
	}
	/**
	 * Gets the size of roles
	 * @return the total number of roles 
	 */
	int getRoleSize(){
		return roles.size();
	}
	/**
	 * Gets the collection of Role
	 * @return the collection of all Roles
	 */
	public Collection getAllRoles(){
		return roles;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return this.wrkflowType + this.wrkflowName + this.wrkflowStepAttr.toString() + this.roles;
	}
}
