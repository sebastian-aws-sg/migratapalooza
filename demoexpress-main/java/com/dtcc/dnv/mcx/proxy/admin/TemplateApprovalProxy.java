package com.dtcc.dnv.mcx.proxy.admin;

import com.dtcc.dnv.mcx.db.admin.DPMXAPUBDAO;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbResponse;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.AbstractDbProxy;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;

/**
 * This class is used as a proxy to fetch list of categories  in 
 * a given template
 * 
 * Copyright � 2003 The Depository Trust & Clearing Company. All rights
 * reserved.
 * 
 * Depository Trust & Clearing Corporation (DTCC) 55, Water Street, New York, NY
 * 10048, U.S.A All Rights Reserved.
 * 
 * This software may contain (in part or full) confidential/proprietary
 * information of DTCC. ("Confidential Information"). Disclosure of such
 * Confidential Information is prohibited and should be used only for its
 * intended purpose in accordance with rules and regulations of DTCC.
 * 
 * @author Elango TR
 * @date Sep 19, 2007
 * @version 1.0
 * 
 */

public  class TemplateApprovalProxy extends AbstractDbProxy {
	/**
	 * @see com.dtcc.dnv.otc.common.layers.IDbProxy#processRequest(IDbRequest)
	 */
	public IDbResponse processRequest(IDbRequest dbRequest) throws DBException
	{
		TemplateDbResponse dbResponse = new TemplateDbResponse();
		DPMXAPUBDAO dao = new DPMXAPUBDAO();
		dao.callSP(dbRequest, dbResponse);
		return dbResponse;
	}
	
}
