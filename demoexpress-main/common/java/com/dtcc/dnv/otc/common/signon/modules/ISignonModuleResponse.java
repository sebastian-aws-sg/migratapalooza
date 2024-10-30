package com.dtcc.dnv.otc.common.signon.modules;


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
 */
public interface ISignonModuleResponse {
	// Method to get the response from the signon module
	public Object getSignonResponse();
	// Method to set the respnse for the signon module.
	public void setSignonResponse(Object signonResponse);
	// Methos to determine that the module step should be bypassesd.
	public boolean isBypass();
}
