package com.dtcc.dnv.otc.common.signon.config;

/**
* @author vxsubram
*
* @date Aug 29, 2007
* @version 1.0
* 
* This class represent the User Role.
* 
* @version	1.1				September 9, 2007		sv
* Checked in for Cognzizant.  Updated Role to contain Label object having Display Name 
* and value property of the Role.
* 
* @version	1.2				October 2, 2007			sv
* Checked in for Cognizant.  Updated Label to contain mutually exclusive property.
*/
public class Role {
	// Holds the name of the role
	private String roleName;
	//Holds the Label (Display Name and Value) of the role
	private Label roleLabel;
	//Holds the user role configured
	private String userRoles;
	// Holds the mutually exclusive property
	private boolean mutuallyExclusive = false;
	
	/**
	 * Default scope constructor because it should only be created by configuration mangers.
	 */
	Role(){
		
	}
	/**
	 * Gets the Role Name
	 * @return the role name.
	 */
	public String getRoleName() {
		return this.roleName;
	}
	/**
	 * Sets the role name
	 * @param roleName The role name to set.
	 */
	void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * Gets the role value
	 * @return the role value.
	 */
	public String getRoleValue() {
		return roleLabel.getLabelValue();
	}
	/**
	 * Gets the user roles mapped to the role name
	 * @return Returns the userroles.
	 */
	public String getUserRoles() {
		return userRoles;
	}
	/**
	 * sets the user roles of a role
	 * @param userroles the String value of userroles to set.
	 */
	void setUserRoles(String userroles) {
		this.userRoles = userroles;
	}
	/**
	 * Returns the role item display label 
	 * @return Returns the role item display label.
	 */
	public String getRoleDisplayName() {
		return roleLabel.getLabelName();
	}
	/**
	 * @param roleLabel The roleLabel to set.
	 */
	void setRoleLabel(Label roleLabel) {
		this.roleLabel = roleLabel;
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
