package com.dtcc.dnv.otc.common.security.model;

import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.AbstractDbProxy;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;

import com.dtcc.dnv.otc.common.security.model.SignonDbRequest;
import com.dtcc.dnv.otc.common.security.model.SignonDbResponse;

import com.dtcc.dnv.otc.legacy.DERS64C0CounterPartyLookup;
import com.dtcc.dnv.otc.legacy.DERS66C0PartyListDB;
import com.dtcc.dnv.otc.legacy.DERS71C0FamiliesByPartcDB;

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
 */
final class SignonDbProxy extends AbstractDbProxy {

	/**
	 * @see com.dtcc.dnv.otc.common.layers.IDbProxy#processRequest(IDbRequest)
	 */
	public IDbResponse processRequest(IDbRequest dbRequest) throws DBException, ClassCastException {

		SignonDbResponse response = null;

		SignonDbRequest request = (SignonDbRequest) dbRequest;

		if ( request.isForBranchFamily() ) {

			response = callDbForBranch(dbRequest);

		} else if ( request.isForNonBranchFamily() ) {

			response = callDbForNonBranch(request);

		} else if ( request.isForCounterParty() ) {

			response = this.callDbForCounterParty(request);

		}

		return response;
	}

	private SignonDbResponse callDbForBranch(IDbRequest request) throws DBException {

		SignonDbResponse response = new SignonDbResponse();

		DERS71C0FamiliesByPartcDB  db = new DERS71C0FamiliesByPartcDB();
		db.callSP(request, response);//make DB call
		
		return response;
	}
	
	private SignonDbResponse callDbForNonBranch(IDbRequest request) throws DBException {

		SignonDbResponse response = new SignonDbResponse();

		DERS66C0PartyListDB  db = new DERS66C0PartyListDB();
		db.callSP(request, response);//make DB call
		
		return response;
	}

	private SignonDbResponse callDbForCounterParty (IDbRequest request) throws DBException {

		SignonDbResponse response = new SignonDbResponse();

		DERS64C0CounterPartyLookup db = new DERS64C0CounterPartyLookup();
		db.callSP(request, response);//make DB call
		
		return response;
	}
	
}
