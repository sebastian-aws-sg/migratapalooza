package com.dtcc.dnv.otc.common.security.model;

import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.AbstractDbProxy;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.DTMCPLSTCounterPartyListDB;

/**
 * @author Toshi Kawanishi
 * @version 1.0
 * 
 * SignonDbProxy handles the DB calls to SP 64, 66 and 71
 * 
 * Rev 1.1 TK 06/25/2004
 * Removed Exception handling and allow them to be caught in the delegate
 * 
 * Rev 1.2 DLV 9/16/2004
 * Removed unused import : import com.dtcc.dnv.otc.common.layers.AbstractDbRequest;
 * 
 * ***********************************************************
 * PROJECT MOVED TO NEW REPOSITORY.
 * @version	1.1				October 18, 2007		sv
 * Rmeoved unused imports.
 */
final class CounterPartyDbProxy extends AbstractDbProxy {

	/**
	 * @see com.dtcc.dnv.otc.common.layers.IDbProxy#processRequest(IDbRequest)
	 */
	public IDbResponse processRequest(IDbRequest dbRequest) throws DBException, ClassCastException {

		CounterPartyDbResponse dbResponse = new CounterPartyDbResponse();

		DTMCPLSTCounterPartyListDB  db = new DTMCPLSTCounterPartyListDB();
		db.callSP(dbRequest, dbResponse);//make DB call
		
		return dbResponse;
	}

	
}
