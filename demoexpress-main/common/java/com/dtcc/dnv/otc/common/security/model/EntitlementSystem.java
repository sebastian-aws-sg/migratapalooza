
package com.dtcc.dnv.otc.common.security.model;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation 55 Water Street,
 * New York, New York, 10041 All Rights Reserved
 * 
 * @author Rajeev
 * @date Jul 25, 2007
 * @version 1.0
 * 
 * Initial revision of EntitlementSystem
 * 
 * This class holds the entitled systems for the logged in user
 */

public class EntitlementSystem implements IEntitlementSystem {
	private String systemName;
	private String systemDesc;
	
	public EntitlementSystem( String systemName, String systemDesc)
	{
		setSystemDesc(systemDesc);
		setSystemName(systemName);
	}
	public EntitlementSystem( String systemName)
	{
		setSystemDesc(null);
		setSystemName(systemName);
	}
	/**
	 * @return Returns the systemDesc.
	 */
	public String getSystemDesc() {
		return systemDesc;
	}
	/**
	 * @param systemDesc The systemDesc to set.
	 */
	private void setSystemDesc(String systemDesc) {
		this.systemDesc = systemDesc;
	}
	/**
	 * @return Returns the systemName.
	 */
	public String getSystemName() {
		return systemName;
	}
	/**
	 * @param systemName The systemName to set.
	 */
	private void setSystemName(String systemName) {
		this.systemName = systemName;
	}
}
