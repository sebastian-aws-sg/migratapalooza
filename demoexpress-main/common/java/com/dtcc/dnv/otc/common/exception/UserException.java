package com.dtcc.dnv.otc.common.exception;

/**
 * @author TKawanis
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public final class UserException extends AbstractException {

	// Well defined set of error codes that are useful for diagnostics
	public static final String SEC_ERROR_CODE_NO_RESULT 		= "101";
	public static final String SEC_ERROR_CODE_NO_SYSTEM 		= "901";
	public static final String SEC_ERROR_CODE_BAD_OPERATION	= "902";
	public static final String SEC_ERROR_CODE_NO_SESSION 	= "903";
	public static final String SEC_ERROR_CODE_AUTHENTICATION	= "904";
	public static final String SEC_ERROR_CODE_DB_ERROR		= "905";
	public static final String SEC_ERROR_CODE_ENTITLEMENT	= "906";

	private static final String[] SEC_ERROR_CODES = { SEC_ERROR_CODE_NO_RESULT,
														SEC_ERROR_CODE_NO_SYSTEM,
														SEC_ERROR_CODE_BAD_OPERATION,
														SEC_ERROR_CODE_NO_SESSION,
														SEC_ERROR_CODE_AUTHENTICATION,
														SEC_ERROR_CODE_DB_ERROR,
														SEC_ERROR_CODE_ENTITLEMENT,
														};

	/**
	 * @param String errorCode - Exception specific error code
	 * @param String errorString - Stored Procedure name
	 * @return nothing
	 * @throws nothing
	 * 
	 * Private constructor called by the static createInfo / createFatal methods
	 * This method is private to prohibit the creation of adhoc exceptions.
	 */
	private UserException(String errorCode, String errorString) {
		super(errorString, ERROR_CLASS_SECURITY, errorCode);
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
		for ( int i = 0; i < SEC_ERROR_CODES.length; i++ ) {
			if (errorCode.equals(SEC_ERROR_CODES[i])) {
				found = true;
				break;
			}
		}
		if ( ! found ) {
			String xxx  = (String) null;	// Force an ugly error to make sure valid error codes are used
		}
	}

	/**
	 * Static Method privateCreate
	 * @param String errorCode - Exception specific error code
	 * @param String errorMessage - Descriptive message
	 * @return UserException
	 * @throws nothing
	 * 
	 * Utility method for creating exceptions properly
	 */
	private static UserException privateCreate (String errorCode, String errorMessage) {
		validateErrorCode(errorCode);			
		UserException ue = new UserException (ERROR_LEVEL_INFO, errorMessage);
		return ue;
	}

	/**
	 * Static Method createInfo
	 * @param String errorCode - Exception specific error code
	 * @param String errorMessage - Descriptive message
	 * @return UserException
	 * @throws nothing
	 * 
	 * Utility method for creating exceptions properly
	 */
	public static UserException createInfo (String errorCode, String errorMessage) {
		UserException ue = privateCreate(ERROR_LEVEL_INFO, errorMessage);
		ue.setLevelInfo();
		return ue;
	}

	/**
	 * Static Method createInfo
	 * @param String errorCode - Exception specific error code
	 * @param String errorMessage - Descriptive message
	 * @param String className
	 * @param String methodName
	 * @return UserException
	 * @throws nothing
	 * 
	 * Utility method for creating exceptions properly
	 */
	public static UserException createInfo (String errorCode, String errorMessage, String className, String methodName) {
		UserException ue = privateCreate(ERROR_LEVEL_INFO, errorMessage);
		ue.setLevelInfo();
		ue.setThrowingClass(className);
		ue.setThrowingMethod(methodName);
		return ue;
	}

	/**
	 * Static Method createFatal
	 * @param String errorCode - Exception specific error code
	 * @param String spName - Stored Procedure name
	 * @param SQLException - The SQLException that was caught
	 * @return DBException
	 * @throws nothing
	 * 
	 * Linkage to the standard exception constructor.  
	 * This method is protected to prohibit the creation of adhoc exceptions.
	 */
	public static UserException createFatal (String errorCode, String errorMessage) {
		UserException ue = privateCreate (ERROR_LEVEL_FATAL, errorMessage);
		ue.setLevelFatal();
		return ue;
	}

}
