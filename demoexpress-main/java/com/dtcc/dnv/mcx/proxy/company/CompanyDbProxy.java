package com.dtcc.dnv.mcx.proxy.company;

import com.dtcc.dnv.mcx.db.company.DPMXHCMDDAO;
import com.dtcc.dnv.mcx.dbhelper.company.CompanyListDbResponse;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.AbstractDbProxy;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;

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

public final class CompanyDbProxy extends AbstractDbProxy {

	/**
	 * @see com.dtcc.dnv.otc.common.layers.IDbProxy#processRequest(IDbRequest)
	 */
	public IDbResponse processRequest(IDbRequest dbRequest) throws DBException, ClassCastException {

		CompanyListDbResponse dbResponse = new CompanyListDbResponse();
		DPMXHCMDDAO  db = new DPMXHCMDDAO();
		db.callSP(dbRequest, dbResponse);		
		return dbResponse;
	}	
}
