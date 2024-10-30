package com.dtcc.dnv.otc.common.security.model;

import com.dtcc.dnv.otc.common.layers.AbstractDbRequest;

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
 * @author Shashi Lingaiah
 * @version 1.0
 * 
 * @version	1.1				October 18, 2007			sv
 * Removed unused imports.
 */

public final class CounterPartyDbRequest extends AbstractDbRequest {

	
	public CounterPartyDbRequest ( String requestId, String servCall, AuditInfo auditInfo) {
		super(requestId, servCall, auditInfo);
	}
}
