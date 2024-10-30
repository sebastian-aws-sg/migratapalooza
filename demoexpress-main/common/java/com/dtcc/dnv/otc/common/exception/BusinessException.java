package com.dtcc.dnv.otc.common.exception;

/**
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 *
 * @author Toshi Kawanishi
 * @version 1.0
 * 
 * BusinessExcepion is the exception that is thrown from the business tier to the
 * presentation tier
 * 
 * rev 1.2 June 27, 2004 TK
 * Changed constructor from private to public
 * 
 */
public final class BusinessException extends AbstractException {

	
	/**
	 * @param String errorCode - Exception specific error code
	 * @param String errorString - Stored Procedure name
	 * @return nothing
	 * @throws nothing
	 * 
	 * Private constructor called by the static createInfo / createFatal methods
	 * This method is private to prohibit the creation of adhoc exceptions.
	 */
	public BusinessException(String errorCode, String errorString) {
		super(errorString, ERROR_CLASS_BO_LOGIC, errorCode);
	}

	/**
	 * @see com.dtcc.dnv.otc.common.exception.AbstractException#writeAppLog()
	 */
	protected void writeAppLog() {
	}

	/**
	 * @see com.dtcc.dnv.otc.common.exception.AbstractException#writeItoLog()
	 */
	protected void writeItoLog() {
	}

}
