
package com.dtcc.dnv.otc.common.security.model;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation 55 Water Street,
 * New York, New York, 10041 All Rights Reserved
 * 
 * @author Rajeev
 * @date Jul 17, 2007
 * @version 1.0
 * 
 * Initial revision of EntitlementFactory
 * 
 * This class creates the entitlement type for the logged in user 
 */
public class ResourceEntitlementType implements IEntitlementType {

	private String value;
	private String description;
	
	public ResourceEntitlementType( String entitlementType)
	{
		setDescription( null);
		setValue( entitlementType);
	}
	
	public ResourceEntitlementType( String entitlementType, String entitlementDesc)
	{
		setDescription( entitlementDesc);
		setValue( entitlementType);
	}
	
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	private void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value The value to set.
	 */
	private void setValue(String value) {
		this.value = value;
	}
}
