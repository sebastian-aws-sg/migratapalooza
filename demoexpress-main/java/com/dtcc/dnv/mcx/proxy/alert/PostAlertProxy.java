
package com.dtcc.dnv.mcx.proxy.alert;
import com.dtcc.dnv.mcx.db.alert.DPMXAALRDAO;
import com.dtcc.dnv.mcx.dbhelper.alert.AlertDbRequest;
import com.dtcc.dnv.mcx.dbhelper.alert.AlertDbResponse;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.AbstractDbProxy;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights
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
 * @author Peng Zhou
 * @date Oct 01, 2007
 * @version 1.0
 * 
 * This class is used as proxy to DAO for posting alert
 *  
 */
public class PostAlertProxy extends AbstractDbProxy {
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.layers.IDbProxy#processRequest(com.dtcc.dnv.otc.common.layers.IDbRequest)
	 */
	public IDbResponse processRequest(IDbRequest request) throws DBException{
		IDbResponse dbResponse = new AlertDbResponse();
		AlertDbRequest dbRequest = (AlertDbRequest) request;
		DPMXAALRDAO db = new DPMXAALRDAO();
		db.callSP(dbRequest, dbResponse);
		return dbResponse;
	}

}
