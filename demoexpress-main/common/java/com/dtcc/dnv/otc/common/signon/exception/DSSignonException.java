package com.dtcc.dnv.otc.common.signon.exception;

import com.dtcc.dnv.otc.common.exception.AbstractException;

/**
 * @author vxsubram
 * Created on Aug 17, 2007
 * @version 1.0
 * 
 * This class serves as the signon module exception.
 * 
 */
public class DSSignonException extends AbstractException {

	// Indicates application service in which a error condition was detected.
	private static final String ERROR_CLASS_SIGNON = "SIGNON";

	public DSSignonException(String errorCode, String errorString) {
		super(errorString, ERROR_CLASS_SIGNON, errorCode);
	}
	
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.exception.AbstractException#writeAppLog()
	 */
	protected void writeAppLog() {
	}

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.exception.AbstractException#writeItoLog()
	 */
	protected void writeItoLog() {
	}

}
