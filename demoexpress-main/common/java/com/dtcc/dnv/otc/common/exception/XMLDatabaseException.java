package com.dtcc.dnv.otc.common.exception;

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
 * @author Dmitriy Larin
 * @version 1.0
 */

/**
 * XMLDatabaseException - parent of all XML database exceptions
 *
 * Make sure that it complient with all the Toshi's exception
 * concepts.
 */
public class XMLDatabaseException extends AbstractException  {

	// Well defined set of error codes that are useful for diagnostics
	public static final String SEC_ERROR_CODE_NO_RESULT 		                      = "101";
	public static final String SEC_ERROR_CODE_NO_SYSTEM 		                      = "901";
	public static final String SEC_ERROR_CODE_BAD_OPERATION	                      = "902";
	public static final String SEC_ERROR_CODE_IO_ERROR	                          = "907";
	public static final String SEC_ERROR_CODE_XML_PARSING_ERROR                     = "908";	
	public static final String SEC_ERROR_CODE_XML_FILE_NOT_FOUND_ERROR              = "909";	
	public static final String SEC_ERROR_CODE_XML_RULES_FILE_NOT_FOUND_ERROR        = "908";		
	public static final String SEC_ERROR_CODE_XML_ENTITLEMENTS_FILE_NOT_FOUND_ERROR = "908";			

	private static final String[] SEC_ERROR_CODES = { SEC_ERROR_CODE_NO_RESULT,
                                                        SEC_ERROR_CODE_NO_SYSTEM, 
                                                        SEC_ERROR_CODE_BAD_OPERATION,
                                                        SEC_ERROR_CODE_IO_ERROR,
                                                        SEC_ERROR_CODE_XML_PARSING_ERROR,
                                                        SEC_ERROR_CODE_XML_FILE_NOT_FOUND_ERROR,
                                                        SEC_ERROR_CODE_XML_RULES_FILE_NOT_FOUND_ERROR,
                                                        SEC_ERROR_CODE_XML_ENTITLEMENTS_FILE_NOT_FOUND_ERROR
													   };


	/**
	 * @param String errorLevel - one of the ERROR_LEVEL values
	 * @param String errorCode - Exception specific error code
	 * @param String errorString - Descriptive message
	 * @return XMLDatabaseException
	 * @throws nothing
	 * 
	 * 
	 * Private constructor called by the static createInfo / createFatal methods
	 * This method is private to prohibit the creation of adhoc exceptions.
	 */
	private XMLDatabaseException(String errorLevel, String errorString) {
		super(errorString, ERROR_CLASS_BO_BEAN, errorLevel);
	}
	
	/**
	 * Static Method privateCreate
	 * @param String errorCode - Exception specific error code
	 * @return nothing
	 * @throws runtime exception if the developer did not use a valid code
	 * Validate to make sure that the error code is in the ERROR_CODES arran
	 * 
	 * Utility method for creating exceptions properly
	 */
	private static XMLDatabaseException privateCreate (String errorCode, String errorMessage) {
		validateErrorCode(errorCode);			
		XMLDatabaseException xde = new XMLDatabaseException(ERROR_LEVEL_INFO, errorMessage);
		return xde;
	}

	
	/**
	 * Static Method createInfo
	 * @param String errorMessage - Descriptive message
	 * @return XMLDatabaseException
	 * @throws nothing
	 * 
	 * Utility method for creating exceptions properly
	 */
	public static XMLDatabaseException createInfo (String errorCode, String errorMessage) {
		XMLDatabaseException xde = privateCreate(ERROR_LEVEL_INFO, errorMessage);
		xde.setLevelInfo();
		return xde;
	}

	/**
	 * Static Method createInfo
	 * @param String errorMessage - Descriptive message
	 * @return XMLDatabaseException
	 * @throws nothing
	 * 
	 * Utility method for creating exceptions properly
	 */
	public static XMLDatabaseException createInfo (String errorCode, String errorMessage, String className, String methodName) {
		XMLDatabaseException xde = privateCreate(ERROR_LEVEL_INFO, errorMessage);
		xde.setLevelInfo();
		xde.setThrowingClass(className);
		xde.setThrowingMethod(methodName);
		return xde;
	}

	/**
	 * Static Method createFatal
	 * @param String errorMessage - Exception specific error code
	 * @throws nothing
	 * @return XMLDatabaseException
	 * 
	 * Linkage to the standard exception constructor.  
	 * This method is protected to prohibit the creation of adhoc exceptions.
	 */
	public static XMLDatabaseException createFatal (String errorCode, String errorMessage) {
		XMLDatabaseException xde = privateCreate (ERROR_LEVEL_FATAL, errorMessage);
		xde.setLevelFatal();
		return xde;
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
}