
package com.dtcc.dnv.otc.common.security.model;
/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation 55 Water Street,
 * New York, New York, 10041 All Rights Reserved
 * 
 * @author Rajeev
 * @date Jul 25, 2007
 * @version 1.0
 * 
 * Initial revision of EntitlementFactory
 * 
 * This interface returns the entitled system name and description
 */
public interface IEntitlementSystem {
	/**
	 * @return Returns the systemDesc.
	 */
	public String getSystemDesc() ;
	/**
	 * @param systemDesc The systemDesc to set.
	 */
	public String getSystemName() ;
}