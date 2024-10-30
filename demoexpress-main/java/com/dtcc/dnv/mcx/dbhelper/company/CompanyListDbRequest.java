package com.dtcc.dnv.mcx.dbhelper.company;

import com.dtcc.dnv.otc.common.layers.AbstractDbRequest;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/*
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
 * 
 * @author Kevin Lake
 * @version 1.0
 * Date: September 05, 2007
 */

public final class CompanyListDbRequest extends AbstractDbRequest {

	
	public CompanyListDbRequest ( String requestId, AuditInfo auditInfo) {
		super(requestId, auditInfo);
	}
}
