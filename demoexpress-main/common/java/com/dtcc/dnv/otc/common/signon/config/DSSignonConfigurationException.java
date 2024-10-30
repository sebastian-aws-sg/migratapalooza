package com.dtcc.dnv.otc.common.signon.config;

import com.dtcc.dnv.otc.common.exception.AbstractException;

/**
 * @author vxsubram
 * Created on Aug 29 2007
 * @version 1.0
 * 
 * This class serves as the signon configuration exception.
 * 
 */
public class DSSignonConfigurationException extends AbstractException {

	// Indicates application service in which a error condition was detected.
	private static final String ERROR_CLASS_SIGNON_CONFIG = "SIGNON_CONFIG";

	public DSSignonConfigurationException(String errorCode, String errorString) {
		super(errorString, ERROR_CLASS_SIGNON_CONFIG, errorCode);
	}
	
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.exception.AbstractException#writeAppLog()
	 */
	protected void writeAppLog() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.exception.AbstractException#writeItoLog()
	 */
	protected void writeItoLog() {
		// TODO Auto-generated method stub

	}

}
