package com.dtcc.dnv.otc.common.signon.modules;

import com.dtcc.dnv.otc.common.security.model.ExUser;
import com.dtcc.dnv.otc.common.signon.DSSignOnTO;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jul 24, 2007
 * @version 1.0
 * 
 * Interface class for all signon module request objects.
 * 
 * @version	1.1			August 14, 2007			sv
 * Added interface method for getUser().
 */
public interface ISignonModuleRequest {
	// Methodto return transfer object
	public DSSignOnTO getSignonTo();
	// Method to return module name (derived from signon workflow step).
	public String getModuleName();
	// Method to return user object
	public ExUser getUser();
}
