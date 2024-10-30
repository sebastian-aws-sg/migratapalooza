package com.dtcc.dnv.otc.legacy;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.AbstractDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;
import com.dtcc.dnv.otc.common.util.MessageResources;


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
 * @version 	1.0
 * @author     Dmitriy Larin
 *
 *  Rev 1.9 January 25, 2004    sav
 * Added bypassSQLCheck boolean to bypass the SQLCODE check in the SP().  This avoids a dependency
 * on the SQLCODE.  setBypassSQLCheck and bypassSQLCheck are setter and getter methods to the
 * boolean.  This boolean can be set from a subclass, and after the stored procedure has been called
 * this class will still set the ErrCd of the boContainer to the SQLCODE and return.
 *
 * Rev 1.16 4/27/2004 sbrown
 * Added call to rollback connection when exception and to commit connection in the "finally" clause
 * Rev 1.0	May 11, 2004	TK
 * Moved from otcd project to legacy
 * Rev 1.1	May 24, 2004	TK
 * Migrated to using the DBProxy framework
 *
 * Rev 1.5	June 25, 2004	TK
 * Added Error Handling:
 *
 * Case 1: 	Call to execute() returns.  Check sqlca for the SQL Code.
 * 			a. If 0 or 100 do nothing (the delegate will make decisions on 100)
 * 			b. if greater than 0, log INFO
 * 			c. if less than 0, log ERROR and throw DBException
 * 			In ALL cases, the SQL Code is set in the DBResponse.
 * Case 2:	SQLException thrown.  Throw DBException
 * Case 3:	Unexpected exception thrown.  Throw DBException
 *
 * Rev 1.6	July 1, 2004	TK
 * Added logic in the catch (Throwable) to just rethrow if it's a DBException
 *
 * Rev 1.7	July 11, 2004	TK
 * Removed reference to OTCDTemplateConstants for QUAL.
 * Get the DB2 schema name from ApplicationResources
 * 
 * Rev 1.8 Aug 3, 2004	TK
 * Added sprc variable that can be set in the subclass's execute() method with
 * the SP_RC out param of the stored procedure.  CommonDB will check to see if
 * sprc is not "SP00" and throw a DBException in such cases.  The RC is logged in
 * the application log
 * 
 * Rev	1.10		May 5, 2005			sv
 * Added String poolName property to alloq DAOs to overide the pool name.
 * Updated callSP() to check if the poolName to determine what poolName
 * to use.  If one was provided by the imlpementing class, then use it.  
 * Otherwise, perform the default action.  Updated exception handling
 * to continue to throw DBException even if the AuditInfo object is null.
 * 
 * Rev	1.11		may 25, 2006		sv
 * Updated references to OTCDConnection.getInstance().closeAll() to also pass in
 * poolName so that the appropriate resources could be cleaned up.
 * 
 * Rev	1.12		June 20, 2006		sv
 * Updated all references to AuditInfo where creating a DBException or logInfo() to
 * check if there is an AuditInfo object.  If not the userid and session will be 
 * default to a String value.  This is done to prevent NullPointerException when 
 * applications don't use AuditInfo.
 * Rev	1.13		June 28, 2006		sl
 * defaulted poolName to OTCDPool if PoolName is empty.
 * 
 * *************************************************************
 * PROJECT MOVED TO NEW REPOSITORY
 * @version	1.3			October 18, 2007		sv
 * Updated logger to be static final.
 */

abstract public class CommonDB
{
	protected String SPName           = "";
	protected SQLCA sqlca             = null;
	protected String sqlcaString      = null;
	protected String sSpErrArea       = null; //extra error area as a last parameter of request.
	protected Connection con          = null;
	protected CallableStatement cstmt = null;
	protected ResultSet rs            = null;
	protected String loggingInfo      = null;
	protected String QUAL             = db2Schema;
	protected String sprc			  = null;
	protected String poolName 	 	  = "";
	private static final String OTCDPool = "OTCDPool";
	private static String db2Schema   = "DER.";

	static {
		String schemaName = MessageResources.getMessage("common.db.schema");
		if (schemaName != null && schemaName.length() > 0 )
			db2Schema = schemaName;
	}
	/**
	 * Constructor for CommonDB.
	 */
	public CommonDB()
	{
		super();
	}

	public abstract void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException;

	public void callSP(IDbRequest request, IDbResponse response) throws DBException
	{

		long beforeGetCon = 0l,
			afterGetCon = 0l,
			beforeEx = 0l,
			afterEx = 0l,
			beforeRelCon = 0l,
			afterRelCon = 0l;
		long start = System.currentTimeMillis();

		try
		{
			if ( log.isDebugEnabled() ) beforeGetCon = System.currentTimeMillis();

			// If a poolName was not provided but the implementation class, 
			// then use the default and retrieve the connection.
			// If it was provided, then use that name for the lookup.
			//Default poolName to OTCDPool, if poolName is spaces
			if (poolName == null || poolName.trim().length() == 0)
				poolName = OTCDPool;

			con = OTCDConnection.getInstance().getConnection(poolName);

			if (log.isDebugEnabled()) afterGetCon = System.currentTimeMillis();

			if (con != null)
			{
				if (log.isDebugEnabled()) beforeEx = System.currentTimeMillis();

				this.execute(request, response);

				if (log.isDebugEnabled()) afterEx = System.currentTimeMillis();

				// Error Handling Case 1: execute() returned
				int sqlCode = sqlca.getSqlCode();	// assumption is that the DB class was successful in
													// creating SQLCA since execute() returned
				response.setSqlcaErrorCode(sqlca.getSqlCodeAsString());
				if (sqlCode == 100 || sqlCode == 0)
				{
					// Case 1a: Do nothing.  sqlCode 100 returned in the response for the delegate to decide
				}
				else if (sqlCode >= 0)
				{
					// Case 1b: Log INFO
                    response.setSqlcaErrorCode(sqlca.getSqlCodeAsString());
                    this.logInfo((AbstractDbRequest) request);
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
														this.sqlca.getSqlCodeAsString(),
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
			else
			{
// OTCDConnection.getInstance().getConnection() should throw SQLException
				throw new SQLException("Unable to get a database connection");
			}
		}
		catch (SQLException sqle)
		{
			// Case 2: SQLException
			if ( con != null )
				OTCDConnection.getInstance().rollbackConn(con);
		
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
												sqle );
			throw dbe;

		}
		catch (Throwable unexpected)
		{
			// Case 3: Unexpected exception
			if ( con != null )
				OTCDConnection.getInstance().rollbackConn(con);

			if ( unexpected instanceof DBException)
				// The DBException was thrown above in Case 1b, 1c.  Just rethrow it.
				throw (DBException) unexpected;
			else {
				// Really got an unexpected exceptino
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
												unexpected );
				throw dbe;
			}
		}
		finally
		{
			if (log.isInfoEnabled()) beforeRelCon = System.currentTimeMillis();

			OTCDConnection.getInstance().commitConn(con);
			OTCDConnection.getInstance().closeAll(rs, cstmt, con, this.poolName);

			if (log.isInfoEnabled())
			{
				afterRelCon = System.currentTimeMillis();
				log.info(
					"******** Stored Procedure "
						+ SPName
						+ " total execution time: "
						+ (afterRelCon - start)
						+ " ms., getConnection time "
						+ (afterGetCon - beforeGetCon)
						+ " ms., execute time "
						+ (afterEx - beforeEx)
						+ " ms., release connection time "
						+ (afterRelCon - beforeRelCon));
			}
		} //end finally
	} //end Fucntion

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
	/**
	 * @deprecated
	 */
	public void SP(IDbRequest request, IDbResponse response)
	{

		long beforeGetCon = 0l,
			afterGetCon = 0l,
			beforeEx = 0l,
			afterEx = 0l,
			beforeRelCon = 0l,
			afterRelCon = 0l;
		long start = System.currentTimeMillis();
		Timestamp errorTs = new Timestamp(start);

		// Originator / Operator / Participant
		//
		String genericLoggerErrorMessage =
			"Stored Procedure "
				+ SPName
				+ " "
				+ "had an error. "
				+ ((loggingInfo == null) ? "" : loggingInfo)
				+ "Error Key is: "
				+ errorTs
				+ "; \r\n"
				+ "Please contact DTCC";

		try
		{
			if ( log.isDebugEnabled() )
				beforeGetCon = System.currentTimeMillis();
			con = OTCDConnection.getInstance().getConnection();
			if (log.isDebugEnabled())
				afterGetCon = System.currentTimeMillis();

			if (con != null)
			{
				if (log.isDebugEnabled())
					beforeEx = System.currentTimeMillis();

				this.execute(request, response);

				if (log.isDebugEnabled())
					afterEx = System.currentTimeMillis();

				if (sqlca.getSqlCode() == 100 || sqlca.getSqlCode() == 0)
				{
					response.setSqlcaErrorCode(sqlca.getSqlCodeAsString());
					//let the action class deal with the logic for a 100
				}
				else if (sqlca.getSqlCode() != 0)
				{
//					DBException.createInfo(sqlca.getSqlCodeAsString(),SPName,"","");
                    //dlv 5/30/2004 log bad sqlcode in case of exception happened in
                    response.setSqlcaErrorCode(sqlca.getSqlCodeAsString());
                    log.error( genericLoggerErrorMessage
						+ " sqlcode: "+sqlca.getSqlCode()
						+ ", sqlca[as string]: " + sqlcaString
						+", Error Area: '" + sSpErrArea
						+ "'");
				}

			}
			else
			{
				throw new SQLException("Unable to get a database connection");
			}
		}
		catch (SQLException e)
		{
			/******************************************************************
			 *  Proper error handling and logging still has to be done      ***
			 ******************************************************************/
			// place log4j loggin code here
			//add some error processing - issue with getting connection
			//we need to add operator, partc id, and originator
			if (sqlca == null)
			{
				//For "Host communication failed" type of exception
				response.setSqlcaErrorCode(e.getSQLState());
				log.error(
					genericLoggerErrorMessage
						+ e.getMessage()
						+ " sqlca: , Error Area: '"
						+ sSpErrArea
						+ "'",
					e);
			}
			else
			{
				response.setSqlcaErrorCode(sqlca.getSqlCodeAsString());
				log.error(
					genericLoggerErrorMessage
						+ e.getMessage()
						+ " sqlca:"
						+ sqlca.toString()
						+ ", Error Area:'"
						+ sSpErrArea
						+ "'",
					e);
			}

			OTCDConnection.getInstance().rollbackConn(con);
		}
		catch (Throwable e)
		{
			/******************************************************************
			 *  Proper error handling and logging still has to be done      ***
			 ******************************************************************/
			// place log4j loggin code here
			//add some error processing - issue with getting connection
			//we need to add operator, partc id, and originator
			if (sqlca != null)
				genericLoggerErrorMessage = genericLoggerErrorMessage + " ,sqlca:" + sqlca;
			if (sSpErrArea != null)
				genericLoggerErrorMessage =
					genericLoggerErrorMessage + " , Error Area:" + sSpErrArea + " ";
			log.fatal(genericLoggerErrorMessage + e.toString(), e);

			OTCDConnection.getInstance().rollbackConn(con);
		}
		finally
		{
			if (log.isInfoEnabled())
				beforeRelCon = System.currentTimeMillis();
			OTCDConnection.getInstance().commitConn(con);
			OTCDConnection.getInstance().closeAll(rs, cstmt, con, this.poolName);
			if (log.isInfoEnabled())
			{
				afterRelCon = System.currentTimeMillis();
				log.info(
					"******** Stored Procedure "
						+ SPName
						+ " total execution time: "
						+ (afterRelCon - start)
						+ " ms., getConnection time "
						+ (afterGetCon - beforeGetCon)
						+ " ms., execute time "
						+ (afterEx - beforeEx)
						+ " ms., release connection time "
						+ (afterRelCon - beforeRelCon));
			}
		} //end finally
	} //end Fucntion

	private static final Logger log = Logger.getLogger(CommonDB.class);

} //end class