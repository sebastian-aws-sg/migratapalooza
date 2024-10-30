package com.dtcc.dnv.otc.common.security.model;

import com.dtcc.dnv.otc.common.security.model.AuditInfo;
import com.dtcc.dnv.otc.common.layers.AbstractServiceRequest;

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
 * @author Toshi Kawanishi
 * @version 1.0
 * 
 * SignonRequest is the IServiceRequest that is passed to SignonDelegate.
 * This request is used for the following cases:
 * 
 * 1. Get the family list for a Branch User
 * 2. Get the family list for a NonBranch User
 * 3. Get the counter party list for both types of users
 * 
 * Rev 1.1	May 31, 2004	TK
 * Added the requisite constructor that takes the AuditInfo parameter
 * Removed the default constructor
 * Rev 1.2	July 21, 2004	TK
 * Fixed familyForNonBranch to use the "P" / "T" passed in prodFlag
 * 
 * *******************************************************************
 * Moved to new project version numbering restarted.
 * 
 * @version	1.1				August 30, 2007			sv
 * Made the class public.
 */
public final class SignonRequest extends AbstractServiceRequest {

	public static SignonRequest familyForBranch(AuditInfo auditInfo, String operatorId, String originatorCode, String participantId, String productType) {
		SignonRequest request = new SignonRequest(auditInfo);
		request.setForBranchFamily();
		request.setOperatorId(operatorId);
		request.setOriginatorCode(originatorCode);
		request.setParticipantId(participantId);
		request.setProductType(productType);
		return request;
	}

	public static SignonRequest familyForNonBranch(AuditInfo auditInfo, String originatorCode, String productType, String prodFlag) {
		SignonRequest request = new SignonRequest(auditInfo);
		request.setForNonBranchFamily();
		request.setOriginatorCode(originatorCode);
		request.setProdFlag(prodFlag);
		request.setProductType(productType);
		return request;
	}

	public static SignonRequest familyForNonBranch(AuditInfo auditInfo, String originatorCode, String participantId, String productType, String prodFlag) {
		SignonRequest request = new SignonRequest(auditInfo);
		request.setForNonBranchFamily();
		request.setOriginatorCode(originatorCode);
		request.setProdFlag(prodFlag);
		request.setParticipantId(participantId);		
		request.setProductType(productType);
		return request;
	}

	public static SignonRequest counterParty(AuditInfo auditInfo, String participantId) {
		SignonRequest request = new SignonRequest(auditInfo);
		request.setForCounterParty();
		request.setParticipantId(participantId);
		return request;
	}

	private SignonRequest (AuditInfo auditInfo) {
		super(auditInfo);
	}

	// One of these is set to true in the static method
	private boolean requestForBranchFamily = false;
	private boolean requestForNonBranchFamily = false;
	private boolean requestForCounterParty = false;
	

									// REQUIRED PARAMETERS
	private String operatorId;		// Only for Branch
	private String originatorCode;	// For both
	private String participantId;	// Only for Branch, and for counterparty list
	private String productType;	// Both
	private String prodFlag;		// Only for NonBranch

	// Methods for determining the type of request
	private void setForBranchFamily() 	{ requestForBranchFamily = true; }
	private void setForNonBranchFamily()	{ requestForNonBranchFamily = true; }
	private void setForCounterParty() 	{ requestForCounterParty = true; }
	public boolean isForBranchFamily() 	{ return requestForBranchFamily; }
	public boolean isForNonBranchFamily()	{ return requestForNonBranchFamily; }
	public boolean isForCounterParty() 	{ return requestForCounterParty; }
	

	// Get / Set methods for request parameters	
	public void setOperatorId 	( String operatorId ) 		{ this.operatorId 		= operatorId; 		}
	public void setOriginatorCode	( String originatorCode ) 	{ this.originatorCode 	= originatorCode; 	}
	public void setParticipantId	( String participantId ) 	{ this.participantId 	= participantId; 	}
	public void setProductType	( String productType ) 		{ this.productType 	= productType; 		}
	public void setProdFlag		( String prodFlag )			{ this.prodFlag		= prodFlag;			}

	public String getOperatorId 		() 	{ return this.operatorId; 	}
	public String getOriginatorCode	()	{ return this.originatorCode;	}
	public String getParticipantId 	() 	{ return this.participantId;	}
	public String getProductType 		() 	{ return this.productType; 	}
	public String getProdFlag			()	{ return this.prodFlag;		}

}
