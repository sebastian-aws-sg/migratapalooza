package com.dtcc.dnv.otc.common.security.model;

import com.dtcc.dnv.otc.common.layers.AbstractDbRequest;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;
import com.dtcc.dnv.otc.common.security.model.SignonRequest;

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
 * SignonDbRequest is the IDbRequest that is passed to SignonDbProxy.
 * This request is used for the following cases:
 * 
 * 1. Get the family list for a Branch User
 * 2. Get the family list for a NonBranch User
 * 3. Get the counter party list for both types of users
 * 
 * Rev 1.1	May 31, 2004	TK
 * Changed the constructor to take the requestId, servCall and auditInfo parameters
 */

public final class SignonDbRequest extends AbstractDbRequest {

	private SignonRequest request;
	
	public SignonDbRequest ( String requestId, String servCall, AuditInfo auditInfo, SignonRequest request ) {
		super(requestId, servCall, auditInfo);
		this.request = request;
	}
	
	public boolean isForBranchFamily() 	{ return request.isForBranchFamily(); }
	public boolean isForNonBranchFamily()	{ return request.isForNonBranchFamily(); }
	public boolean isForCounterParty() 	{ return request.isForCounterParty(); }

	public String getOperatorId 		() 	{ return request.getOperatorId(); 	}
	public String getOriginatorCode	()	{ return request.getOriginatorCode();	}
	public String getParticipantId 	() 	{ return request.getParticipantId();	}
	public String getProductType 		() 	{ return request.getProductType(); 	}
	public String getProdFlag			()	{ return request.getProdFlag();		}
	
}
