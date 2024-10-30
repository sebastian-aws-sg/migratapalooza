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
 * @author     Toshi Kawanishi
 *
 *    5/15/2004 Dlv added SP_ERROR error code for stored-procedure related issues.
 *
 * rev 1.2 TK 06/25/2004
 * Restructured for the cases described in CommonDB
 *
 * rev 1.4 TK 06/30/2004
 * Added getSqlCode() and getDisplayString() methods
 * rev 1.5 TK 07/15/2004
 * Added getSQLException() per Sarosh's request
 * Rev 1.6 Aug 3, 2004 TK
 * Added constructor that takes a sprc (the SP_RC from the stored procedure)
 * Added getSPRC() and getSpErrorArea() getter methods.
 * 
 * *************************************************************
 * PROJECT MOVED TO NEW REPOSITORY
 * @version	1.1				October 18, 2007		sv
 * Updated logger to be static final.
 */
package com.dtcc.dnv.otc.common.exception;

import java.sql.SQLException;
import org.apache.log4j.Logger;

public final class DBException extends AbstractException {

	private static final Logger log = Logger.getLogger(DBException.class);

	// Well defined set of error codes that are useful for diagnostics
	public static final String DB_ERROR_NEGATIVE_SQL_CODE = "100";
	public static final String DB_ERROR_SQL_EXCEPTION     = "101";
	public static final String DB_ERROR_UNEXPECTED        = "200";	// Unexpected Exception
	public static final String DB_ERROR_SPRC        		= "300";	// Stored procedure returned a return code

	private static final String[] DB_ERROR_CODES = {	DB_ERROR_NEGATIVE_SQL_CODE,
														DB_ERROR_SQL_EXCEPTION,
														DB_ERROR_UNEXPECTED
													  };
	private String spName;			// Name of the stored procedure that was called when the exception occruued
	private String serviceCall;	// Optional service call parameter passed in the DB Request
	private String sqlCode;		// From SQLCA
	private String sqlca;			// The full SQLCA string for logging
	private String spErrorArea;	// The spErrorArea string for logging
	private String timestamp;		// timestamp of when the exception was caught
	private String requestId;		// The request id passed in the db request
	private String userId;			// Identity of the user who initiated the db request
	private String sessionId;		// The http session id of the user
	private String sqlcaString;	// The SQLCA string from the database
	private SQLException sqle;
	private String sprc;			// stored procedure return code
	private Throwable unexpected;

	/**
	 * @param String spName
	 * @param String serviceCall
	 * @param String className
	 * @param String tmiestamp
	 * @param String requestId
	 * @param String userId
	 * @param String sessionId
	 * @param String sqlCode
	 * @param String sqlcaString
	 * @param String spErrorArea
	 *
	 * DBException constructor for SQLCA negative sqlCode case
	 */
	public DBException( String spName,    String serviceCall,
						 String className,    String timestamp, String requestId,   String userId,
						 String sessionId,    String sqlCode,   String sqlcaString, String spErrorArea) {

		super(ERROR_CLASS_DATABASE, DB_ERROR_NEGATIVE_SQL_CODE);
		super.setLevelFatal();									// Will always be fatal if
		super.setThrowingClass(className);

		this.spName = spName;
		this.serviceCall = serviceCall;
		this.timestamp = timestamp;
		this.requestId = requestId;
		this.userId = userId;
		this.sessionId = sessionId;
		this.sqlCode = sqlCode;
		this.sqlcaString = sqlcaString;
		this.spErrorArea = spErrorArea;

		this.writeLog();

	}

	/**
	 * @param String spName
	 * @param String serviceCall
	 * @param String className
	 * @param String tmiestamp
	 * @param String requestId
	 * @param String userId
	 * @param String sessionId
	 * @param String sprc
	 *
	 * DBException constructor for when the stored procedure returns SP_RC
	 */
	public DBException( String spName,    String serviceCall,
						 String className,    String timestamp, String requestId,   String userId,
						 String sessionId,    String sprc ) {

		super(ERROR_CLASS_DATABASE, DB_ERROR_SPRC);
		super.setLevelFatal();									// Will always be fatal if
		super.setThrowingClass(className);

		this.spName = spName;
		this.serviceCall = serviceCall;
		this.timestamp = timestamp;
		this.requestId = requestId;
		this.userId = userId;
		this.sessionId = sessionId;
		this.sprc = sprc;

		this.writeLog();

	}

	/**
	 * @param String spName
	 * @param String serviceCall
	 * @param String className
	 * @param String tmiestamp
	 * @param String requestId
	 * @param String userId
	 * @param String sessionId
	 * @param SQLException
	 *
	 * DBException constructor for SQLException case
	 */
	public DBException( String spName,      String serviceCall,
						 String className,    String timestamp, String requestId,   String userId,
						 String sessionId,    SQLException sqle) {

		super(ERROR_CLASS_DATABASE, DB_ERROR_SQL_EXCEPTION);
		super.setLevelFatal();									// Will always be fatal if
		super.setThrowingClass(className);

		this.spName = spName;
		this.serviceCall = serviceCall;
		this.timestamp = timestamp;
		this.requestId = requestId;
		this.userId = userId;
		this.sessionId = sessionId;
		this.sqle = sqle;


		this.writeLog();
	}

	/**
	 * @param String spName
	 * @param String serviceCall
	 * @param String className
	 * @param String tmiestamp
	 * @param String requestId
	 * @param String userId
	 * @param String sessionId
	 * @param Throwable
	 *
	 * DBException constructor for SQLException case
	 */
	public DBException( String spName,      String serviceCall,
						 String className,    String timestamp, String requestId,   String userId,
						 String sessionId,    Throwable unexpected) {

		super(ERROR_CLASS_DATABASE, DB_ERROR_UNEXPECTED);
		super.setLevelFatal();									// Will always be fatal if
		super.setThrowingClass(className);

		this.spName = spName;
		this.serviceCall = serviceCall;
		this.timestamp = timestamp;
		this.requestId = requestId;
		this.userId = userId;
		this.sessionId = sessionId;
		this.unexpected = unexpected;

		this.writeLog();
						 }
	/**
	 * Method writeAppLog
	 * @param nothing
	 * @return nothing
	 * @throws nothing
	 *
	 * Write the exception to the Application log file.
	 * Called from AbstractException.writeLog()
	 *
	 */
	protected void writeAppLog() {
		if ( super.getErrorCode().equals(DB_ERROR_NEGATIVE_SQL_CODE) ) {
			String message =
				"SQLCA Error Code from Stored Procedure " + spName + " on Service Call: " + serviceCall
					+ "\n\t     Class: " + super.getThrowingClass()
					+ "\n\t Timestamp: " + timestamp
					+ "\n\tRequest Id: " + requestId
					+ "\n\t      User: " + userId
					+ "\n\t   Session: " + sessionId
					+ "\n\t  SQL Code: " + sqlCode
					+ "\n\t     SQLCA: " + sqlcaString
					+ "\n\t ErrorArea: " + spErrorArea;
			log.info(message);
		} else if ( super.getErrorCode().equals(DB_ERROR_SQL_EXCEPTION)) {
			String message =
				"SQLException from Stored Procedure " + spName + " on Service Call: " + serviceCall
					+ "\n\t     Class: " + super.getThrowingClass()
					+ "\n\t Timestamp: " + timestamp
					+ "\n\tRequest Id: " + requestId
					+ "\n\t      User: " + userId
					+ "\n\t   Session: " + sessionId
					+ "\n\t   message: " + sqle.getMessage()
					+ "\n\t errorCode: " + sqle.getErrorCode()
					+ "\n\terrorState: " + sqle.getSQLState();

			log.info(message, sqle);
		} else if ( super.getErrorCode().equals(DB_ERROR_SPRC)) {
			String message =
				"Stored Procedure " + spName + " returned SP_RC "+ sprc +" on Service Call: " + serviceCall
					+ "\n\t     Class: " + super.getThrowingClass()
					+ "\n\t Timestamp: " + timestamp
					+ "\n\tRequest Id: " + requestId
					+ "\n\t      User: " + userId
					+ "\n\t   Session: " + sessionId
					+ "\n\tReturn code: " + sprc;

			log.info(message, sqle);
		} else {
			String message =
				"Unexpected exception from Stored Procedure " + spName + " on Service Call: " + serviceCall
					+ "\n\t     Class: " + super.getThrowingClass()
					+ "\n\t Timestamp: " + timestamp
					+ "\n\tRequest Id: " + requestId
					+ "\n\t      User: " + userId
					+ "\n\t   Session: " + sessionId
					+ "\n\t   message: " + unexpected.getMessage();

			log.info(message, this.unexpected);
		}

	}

	/**
	 * Method writeItoLog
	 * @param nothing
	 * @return nothing
	 * @throws nothing
	 *
	 * Write the exception to the ITO log file.
	 * Called from AbstractException.writeLog()
	 *
	 */
	protected void writeItoLog() {
		// Start the ITO log entry with "DAO900" or "DAO901"
		String message = this.getErrorClass() + this.getErrorCode();
		if ( super.getErrorCode().equals(DB_ERROR_NEGATIVE_SQL_CODE) ) {
			// Do not log as fatal if
			if ( ! sqlCode.equals("-438") ) {
				// Since we don't have an exception, provide as much info as possible
				message = message +
					"~Negative SQL Code in Stored Procedure " + spName + " on Service Call: " + serviceCall
						+ "~     Class: " + super.getThrowingClass()
						+ "~ Timestamp: " + timestamp
						+ "~Request Id: " + requestId
						+ "~      User: " + userId
						+ "~   Session: " + sessionId
						+ "~  SQL Code: " + sqlCode
						+ "~     SQLCA: " + sqlcaString
						+ "~ ErrorArea: " + spErrorArea;

				log.fatal(message);
			}
		} else if ( super.getErrorCode().equals(DB_ERROR_SQL_EXCEPTION)) {
			message = message +
				"SQLException from Stored Procedure " + spName + " on Service Call: " + serviceCall;
			log.fatal(message, this.sqle);

		} else {
			message =
				"Unexpected exception from Stored Procedure " + spName + " on Service Call: " + serviceCall;
			log.fatal(message,this.unexpected);
		}
	}

	/**
	 * Static Method validateErrorCode
	 * @param String errorCode - Exception specific error code
	 * @return nothing
	 * @throws runtime exception if the developer did not use a valid code
	 *
	 * Validate to make sure that the error code is in the DB_ERROR_CODES arran
	 *
	 */
	private static void validateErrorCode(String errorCode) {
		boolean found = false;
		for ( int i = 0; i < DB_ERROR_CODES.length; i++ ) {
			if (errorCode.equals(DB_ERROR_CODES[i])) {
				found = true;
				break;
			}
		}
		if ( ! found ) {
			String xxx  = (String) null;	// Force an ugly error to make sure valid error codes are used
		}
	}

	/**
	 * Static Method createFatal
	 * @param String errorCode - Exception specific error code
	 * @param String spName - Stored Procedure name
	 * @param SQLException - The SQLException that was caught
	 * @return DBException
	 * @throws nothing
	 * @deprecated
	 *
	 * Linkage to the standard exception constructor.
	 * This method is protected to prohibit the creation of adhoc exceptions.
	 */
	public static DBException createFatal (String errorCode, String spName, String dbErrorCode, String errorMessage) {
		validateErrorCode(errorCode);
		DBException dbe = new DBException (ERROR_LEVEL_FATAL, errorCode, spName, dbErrorCode, "", errorMessage);
		dbe.setLevelFatal();
		return dbe;
	}

	/**
	 * @param String errorLevel - one of the ERROR_LEVEL values
	 * @param String errorCode - Exception specific error code
	 * @param String spName - Stored Procedure name
	 * @param String dbErrorCode - Return code from SP
	 * @param String errorMessage - Descriptive message
	 * @return nothing
	 * @throws nothing
	 * @deprecated
	 *
	 * Private constructor called by the static createInfo / createFatal methods
	 * This method is private to prohibit the creation of adhoc exceptions.
	 */
	private DBException(String errorLevel, String errorCode, String spName, String dbErrorCode, String dbErrorState, String errorMessage) {
		super(errorMessage, ERROR_CLASS_DATABASE, errorCode);

		this.spName = spName;
//		this.errorCode = dbErrorCode;
//		this.errorState = dbErrorState;

		if ( errorLevel.equals(super.ERROR_LEVEL_FATAL) )
			super.setLevelFatal();
		else
			super.setLevelInfo();
	}

	/**
	 * Returns the sqlCode.
	 * @return String
	 */
	public String getSqlCode() {
		return sqlCode;
	}

	/**
	 * Returns a String used in the error message to the User.
	 * The actual message should add a context specific user friendly text.
	 * @return String
	 */
	public String getDisplayString () {
		return "[ DB Error - User Id:"+userId+",Time:"+timestamp+"]";
	}

	/**
	 * Returns a String containing the message from the SQLException
	 *
	 * @return String
	 */
	public String getSqleMessage() {
		if ( sqle != null )
			return sqle.getMessage();
		else
			return new String();
	}

	/**
	 * Returns a String containing the message from the SQLException
	 *
	 * @return String
	 */
	public String getSpErrorArea() {
		if ( this.spErrorArea != null )
			return this.spErrorArea;
		else
			return new String();
	}

	/**
	 * Returns a String containing the SP_RC returned by the stored procedure
	 *
	 * @return String
	 */
	public String getSPRC() {
		if ( this.sprc != null )
			return this.sprc;
		else
			return new String();
	}

}
