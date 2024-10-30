package com.dtcc.dnv.otc.common.resourcelocator.exception;

import com.dtcc.dnv.otc.common.exception.AbstractException;

/**
 * @author raugustu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class LocatorException extends AbstractException {

	private static final String ERROR_CLASS_UTILITY = "util";

	public LocatorException(String errorCode, String errorString) {
		super(errorString, ERROR_CLASS_UTILITY, errorCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtcc.dnv.otc.common.exception.AbstractException#writeAppLog()
	 */
	protected void writeAppLog() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtcc.dnv.otc.common.exception.AbstractException#writeItoLog()
	 */
	protected void writeItoLog() {
		// TODO Auto-generated method stub

	}

}
