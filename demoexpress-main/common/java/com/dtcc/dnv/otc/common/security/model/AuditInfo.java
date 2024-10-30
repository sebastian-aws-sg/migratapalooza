package com.dtcc.dnv.otc.common.security.model;

/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55, Water Street,
 * New York, NY 10048
 * U.S.A
 * All Rights Reserved.
 *
 * This software may contain (in part or full) confidential/proprietary information of DTCC.
 * ("Confidential Information"). Disclosure of such Confidential
 * Information is prohibited and should be used only for its intended purpose
 * in accordance with rules and regulations of DTCC.
 * Form bean for a Struts application.
 *
 * AuditInfo is a value object used to propagate user, session and action to the business and
 * persistence layers.
 * 
 * 03/25/2005 Shashi
 * ver 1.1
 * Added UserName and EmailId to the value object for Audit trail
 */

public final class AuditInfo {

	private String userId;			// Login Id (currently the PCWeb Operator Id)
	private String sessionId;		// The HTTPS session id
	private String transaction;	// The transaction the user is performing
	private String userName;
	private String userEmailId;
	

	AuditInfo (String userId, String sessionId, String userName, String userEmail) {
		super();
		this.userId = userId;
		this.sessionId = sessionId;
		this.userName = userName;
		this.userEmailId = userEmail;
	}
	
	private AuditInfo() {}
	
	public void setTransaction (String transaction) {
		this.transaction = transaction;
	}
	
	public String getUserId() 		{ return this.userId; }
	public String getSessionId() 	{ return this.sessionId; }
	public String getTransaction()	{ return this.transaction; }
	
	/**
	 * Method getUserName()
	 * @author Shashi
	 * @param nothing
	 * @return Operator Name as String
	 */
	public String getUserName() 	{ return this.userName; }
	/**
	 * Method getUserEmailId()
	 * @author Shashi
	 * @param nothing
	 * @return Operator Email Id as String
	 */
	public String getUserEmailId() { return this.userEmailId; }		
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(this.userId).append(",");
		sb.append(this.sessionId).append(",");
		sb.append(this.userName).append(",");
		sb.append(this.userEmailId).append(",");
		sb.append(this.transaction);
		sb.append("]");
		return sb.toString();
	}
}
