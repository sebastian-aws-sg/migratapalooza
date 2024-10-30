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
 * Base sigon module response object.
 */
public class SignonModuleResponse implements ISignonModuleResponse {
	// Instance member
	private Object signonResponse = null;
	private boolean bypass = false;
	
	/**
	 * @return
	 */
	public Object getSignonResponse(){
		return this.signonResponse;
	}
	
	/**
	 * @param signResponse The signResponse to set.
	 */
	public void setSignonResponse(Object signResponse) {
		this.signonResponse = signResponse;
	}
	
	/**
	 * @return Returns the bypass.
	 */
	public boolean isBypass() {
		return bypass;
	}
	
	/**
	 * @param bypass The bypass to set.
	 */
	public void setBypass(boolean bypass) {
		this.bypass = bypass;
	}
}
