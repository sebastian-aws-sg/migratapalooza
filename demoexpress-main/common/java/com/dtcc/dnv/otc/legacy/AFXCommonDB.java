package com.dtcc.dnv.otc.legacy;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.AbstractDbRequest;
import com.dtcc.dnv.otc.common.layers.AbstractDbResponse;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/**
 * Copyright © 2005 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55 Water Street,
 * New York, NY 10041
 * 
 * 
 * @author Saravanan Sridharan
 * @date May 16, 2006
 * @version 1.0
 * 
 * Rev	1.1			June 20, 2006		sv
 * Updated to hold instance members for sqlCodeAsString and
 * sqlState since UDB doesn't support SQLCA.  Updated all references to 
 * AuditInfo where creating a DBException or logInfo() to check if 
 * there is an AuditInfo object.  If not the userid and session will be 
 * default to a String value.  This is done to prevent NullPointerException when 
 * applications don't use AuditInfo.
 * 
 * *************************************************************
 * PROJECT MOVED TO NEW REPOSITORY
 * @version	1.1			October 18, 2007		sv
 * Updated logger to be static final.
 */

public abstract class AFXCommonDB extends CommonDB{
	
	// Instance members
	protected String sqlCodeAsString = "-99999";
	protected String sqlState = "";
	
	public AFXCommonDB()
	{
		super();
		sqlca = new SQLCA("SQLCA        +136       +0   +0                                                                      DSN            +0       +0       +0       -1       +0       +0           00000");
	}
	
	public void callSP(IDbRequest request, IDbResponse response) throws DBException
	{
		super.callSP(request,response);
		
		AbstractDbResponse dbResponse = (AbstractDbResponse) response;
		int sqlCode = new Integer(sqlCodeAsString).intValue();
		// Error Handling Case 1: execute() returned	
		if (sqlCode == 100 || sqlCode == 0)
		{
			// Case 1a: Do nothing.  sqlCode 100 returned in the response for the delegate to decide
		}
		else if (sqlCode >= 0)
		{
			// Case 1b: Log INFO
            response.setSqlcaErrorCode(dbResponse.getSqlcaErrorCode());
            logInfo((AbstractDbRequest) request);
		} else if (sqlCode <= 0) {
			// Case 1c: Negative SQL Code.  Throw DBException
			AbstractDbRequest dbReq = (AbstractDbRequest) request;
			AuditInfo ai = dbReq.getAuditInfo();
			
			// Sometimes there is no auditInfo object.  If this the case this if statement
			// will prevent a NullPointerException from happening, and the exception will not
			// be thrown.  This allows for this error to be propagated appropriately.
			String userId = "";
			String sessionId = "";
			if (ai != null){
				userId = ai.getUserId();
				sessionId = ai.getSessionId();
			}
			else{
				// Since there is no auditinfo object, provided default msg. 
				userId = "Userid not provided.";
				sessionId = "sessionId not provided.";
			}
			DBException dbe = new DBException ( this.SPName, dbReq.getServiceCall(),
												this.getClass().getName(),
												new Timestamp(System.currentTimeMillis()).toString(),
												dbReq.getRequestId(),
												userId,
												sessionId,
												this.sqlCodeAsString,
												this.sqlcaString,
												this.sSpErrArea );
			throw dbe;

		}
		if ( sprc != null && !sprc.equals("SP00") ) {
			// Case 1d: SP returned a failure return code
			AbstractDbRequest dbReq = (AbstractDbRequest) request;
			AuditInfo ai = dbReq.getAuditInfo();

			// Sometimes there is no auditInfo object.  If this the case this if statement
			// will prevent a NullPointerException from happening, and the exception will not
			// be thrown.  This allows for this error to be propagated appropriately.
			String userId = "";
			String sessionId = "";
			if (ai != null){
				userId = ai.getUserId();
				sessionId = ai.getSessionId();
			}
			else{
				// Since there is no auditinfo object, provided default msg. 
				userId = "Userid not provided.";
				sessionId = "sessionId not provided.";
			}
			
			DBException dbe = new DBException ( this.SPName, 
												dbReq.getServiceCall(),
												this.getClass().getName(),
												new Timestamp(System.currentTimeMillis()).toString(),
												dbReq.getRequestId(),
												userId,
												sessionId,
												sprc );
			throw dbe;
			
		}
	}

	private void logInfo( AbstractDbRequest request ) {

		AuditInfo ai = request.getAuditInfo();
		// Sometimes there is no auditInfo object.  If this the case this if statement
		// will prevent a NullPointerException from happening, and the exception will not
		// be thrown.  This allows for this error to be propagated appropriately.
		String userId = "";
		String sessionId = "";
		if (ai != null){
			userId = ai.getUserId();
			sessionId = ai.getSessionId();
		}
		else{
			// Since there is no auditinfo object, provided default msg. 
			userId = "Userid not provided.";
			sessionId = "sessionId not provided.";
		}
		String message =
			"SQLCA Warning from Stored Procedure " + SPName + " on Service Call: " + request.getServiceCall()
				+ "\n\t     Class: " + this.getClass().getName()
				+ "\n\t Timestamp: " + new Timestamp(System.currentTimeMillis())
				+ "\n\tRequest Id: " + request.getRequestId()
				+ "\n\t      User: " + userId
				+ "\n\t   Session: " + sessionId
				+ "\n\t  SQL Code: " + sqlca.getSqlCodeAsString()
				+ "\n\t     SQLCA: " + sqlcaString
				+ "\n\t ErrorArea: " + sSpErrArea;


		log.info( message );

	}
	private static final Logger log = Logger.getLogger(AFXCommonDB.class);
}
