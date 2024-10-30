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
 * Base signon module request.
 * 
 * @version	1.1				July 27, 2007			sv
 * Updated access modifier for setSignonTo(DSSignOnTO signonTo) to public
 * 
 * @version	1.2				August 14, 2007			sv
 * Added user property, setter, and getter.
 * 
 * @version	1.3				September 16, 2007		sv
 * Updated to make constructor private and create a factoy method.
 * 
 * @version	1.4				Septemeber 19, 2007		sv
 * Updated createRequest() to return ISignonModuleRequest rather than SignonModuleRequest.
 */
public class SignonModuleRequest implements ISignonModuleRequest {
	// Instance members
	private DSSignOnTO signonTo = null;
	private String moduleName = "";
	private ExUser user = null;
	
	/**
	 * Private constructor.  This object is to be constructed by the
	 * createRequest() factory method 
	 */
	private SignonModuleRequest(){
	}

	public static ISignonModuleRequest createRequest(String workflowStep, DSSignOnTO signonTO, ExUser user){
		SignonModuleRequest request = new SignonModuleRequest();
		request.setModuleName(workflowStep);
		request.setSignonTo(signonTO);
		request.setUser(user);
		return request;
	}
	
	/**
	 * @return Returns the signonTo.
	 */
	public DSSignOnTO getSignonTo() {
		return signonTo;
	}
	/**
	 * @param signonTo The signonTo to set.
	 * TODO Protect this better
	 */
	private void setSignonTo(DSSignOnTO signonTo){
		this.signonTo = signonTo;
	}

	/**
	 * @return Returns the moduleName.
	 */
	public String getModuleName() {
		return moduleName;
	}
	
	/**
	 * @param moduleName The moduleName to set.
	 */
	private void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	/**
	 * @return Returns the ExUser.
	 */
	public ExUser getUser() {
		return user;
	}
	
	/**
	 * @param user The ExUser to set.
	 */
	private void setUser(ExUser user) {
		this.user = user;
	}
}
