
package com.dtcc.dnv.otc.common.security.model;

import java.util.List;

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
 * This interface returns the entitled system and entitled value for the logged in user 
 */
public interface IEntitlementRequest {
	public List getEntitlementValue();
	public List getEntitlementSystem();
}
