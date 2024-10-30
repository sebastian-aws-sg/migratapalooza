/*
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 *
 */

package com.dtcc.dnv.otc.common.exception;

/**
 * @author Toshi Kawanishi
 * @version 1.0
 * 
 * AbstractExcepion is the super class of all custom exceptions in the OTC application.
 * 
 * rev 1.1 TK 06/25/2004
 * Added new constructor 	protected AbstractException(String errorClass, String errorCode)
 * rev 1.2 Aug 4, 2004 TK
 * Made getErrorCode() public
 */
public abstract class AbstractException extends Exception {

	/* ERROR CLASS
	 * An error class specifies the application service in which a error condition was
	 * detected.
	 */
	static final String ERROR_CLASS_DATABASE 	= "DAO";	// Data access layer
	static final String ERROR_CLASS_BO_LOGIC	= "BOL";	// Business logic
	static final String ERROR_CLASS_BO_BEAN	= "BOB";	// Business data
	static final String ERROR_CLASS_SERVLET	= "SVL";	// Presentation layer request processing
	static final String ERROR_CLASS_JSP		= "JSP";	// Presentation layer view generation
	static final String ERROR_CLASS_SECURITY	= "SEC";	// Security layer

	/* ERROR LEVEL
	 * An error level specifies the severity of the error
	 */
	static final String ERROR_LEVEL_INFO		= "01";	// Recoverable error
	static final String ERROR_LEVEL_FATAL		= "09"; // Un-recoverable error.  user cannot continue.

	private String errorClass;							// Set privately in the exceptino class constructor
														// to the appropriate the ERROR_CLASS const
	private String errorLevel = ERROR_LEVEL_FATAL;		// Default to fatal
	private String errorCode;							// Exception specific code indicating the kind off error
														// e.g. "901" in a "DAO" exception may mean "Failed to get connection"
														// 		while "101" may mean no results were returned.
														//		The errorCode should be meaningful enough to
														//		aid in problem determination.  "901" may be an issue with
														//		WAS or NEON or DB2 while "101" may be caused by missing data

	protected String messageResource;					// TBD: use resource strings?

	// The following OPTIONAL FIELDS are useful if symbols are stripped out
	protected String throwingClass;					// The class where the exception was thrown
	protected String throwingMethod;					// The method where the exception was thrown

	protected boolean logged = false;				// Was this exception written to the log?

	/**
	 * Method AbstractException
	 * @param nothing
	 * @return nothing
	 * @throws nothing
	 * 
	 * Applications may not create adhoc exceptions
	 */
	private AbstractException() {}

	/**
	 * Method AbstractException
	 * @param String message
	 * @return nothing
	 * @throws nothing
	 * 
	 * Linkage to the standard exception constructor.  
	 * This method is protected to prohibit the creation of adhoc exceptions.
	 */
	protected AbstractException(String message) {
		super(message);
	}

	/**
	 * Method AbstractException
	 * @param String errorClass ERROR_CLASS const
	 * @param String errorCode Exception specific error code
	 * @return nothing
	 * @throws nothing
	 * 
	 * Linkage to the standard exception constructor.  
	 * This method is protected to prohibit the creation of adhoc exceptions.
	 */
	protected AbstractException(String errorClass, String errorCode) {
		super();
		this.setErrorClass(errorClass);
		this.setErrorCode(errorCode);
	}

	/**
	 * Method AbstractException
	 * @param String message
	 * @param String errorClass ERROR_CLASS const
	 * @param String errorCode Exception specific error code
	 * @return nothing
	 * @throws nothing
	 * 
	 * Linkage to the standard exception constructor.  
	 * This method is protected to prohibit the creation of adhoc exceptions.
	 */
	protected AbstractException(String message, String errorClass, String errorCode) {
		super(message);
		this.setErrorClass(errorClass);
		this.setErrorCode(errorCode);
	}


	/**
	 * Method setErrorClass
	 * @param String
	 * @return nothing
	 * @throws nothing
	 * 
	 * Set the errorClass to one of the ERROR_CLASS constants
	 */
	protected final void setErrorClass(String errorClass) { this.errorClass = errorClass;}
	/**
	 * Method setErrorCode
	 * @param String
	 * @return nothing
	 * @throws nothing
	 * 
	 * Set the errorCode to an exception specific code
	 */
	protected final void setErrorCode(String errorCode) { this.errorCode = errorCode; }
	/**
	 * Method setErrorLevel
	 * @param String
	 * @return nothing
	 * @throws nothing
	 * 
	 * Set the errorClass to one of the ERROR_LEVEL constants
	 */
	protected final void setErrorLevel(String errorLevel) { this.errorLevel = errorLevel; }
	
	/**
	 * Method setLevelInfo
	 * @param nothing
	 * @return nothing
	 * @throws nothing
	 * 
	 * Sets the internal Error Level to ERROR_LEVEL_INFO
	 */
	protected final void setLevelInfo() {
		this.errorLevel = ERROR_LEVEL_INFO;
	}	

	/**
	 * Method setLevelFatal
	 * @param nothing
	 * @return nothing
	 * @throws nothing
	 * 
	 * Sets the internal Error Level to ERROR_LEVEL_FATAL
	 */
	protected final void setLevelFatal() {
		this.errorLevel = ERROR_LEVEL_FATAL;
	}	

	/**
	 * Method setThrowingClass
	 * @param String className
	 * @return nothing
	 * @throws nothing
	 * 
	 * Sets the class that threw the exception
	 */
	public void setThrowingClass(String className) {
		this.throwingClass = className;
	}
	
	/**
	 * Method setThrowingMethod
	 * @param String methodName
	 * @return nothing
	 * @throws nothing
	 * 
	 * Sets the method that threw the exception
	 */
	public void setThrowingMethod(String methodName) {
		this.throwingMethod = methodName;
	}

	/**
	 * Method isFatal
	 * @param nothing
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns true if the errorLevel indicates a fatal error
	 */
	public final boolean isFatal() {
		return this.errorLevel.equals(ERROR_LEVEL_FATAL);
	}

	/**
	 * Method isInfo
	 * @param nothing
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns true if the errorLevel indicates a non-fatal error
	 */
	public final boolean isInfo() {
		return this.errorLevel.equals(ERROR_LEVEL_INFO);
	}

	/**
	 * Method writeLog
	 * @param nothing
	 * @return nothing
	 * @throws nothing
	 * 
	 * Method used by the application to 
	 */
	public final void writeLog() {
		if ( ! logged ) {
			if (this.isFatal()) {
				// Log the error in the app and ito logs
				this.writeItoLog();
				this.writeAppLog();
			} else {
				// Log the error in the app log
				this.writeAppLog();
			}
			logged = true;	// set to true so it will not be logged again
		}
	}


	/**
	 * Method writeAppLog
	 * @param nothing
	 * @return nothing
	 * @throws nothing
	 * 
	 * Subclass responsibility to write the exception to the application log
	 */
	protected abstract void writeAppLog();

	/**
	 * Method writeItoLog
	 * @param nothing
	 * @return nothing
	 * @throws nothing
	 * 
	 * Subclass responsibility to write the exception to the ITO monitored log file
	 */
	protected abstract void writeItoLog();		
	/**
	 * Returns the errorClass.
	 * @return String
	 */
	protected String getErrorClass() {
		return errorClass;
	}

	/**
	 * Returns the errorCode.
	 * @return String
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Returns the throwingClass.
	 * @return String
	 */
	protected String getThrowingClass() {
		return throwingClass;
	}

}
