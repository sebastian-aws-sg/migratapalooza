package com.dtcc.dnv.otc.common.builders.exception;

import com.dtcc.dnv.otc.common.exception.AbstractException;
import org.apache.log4j.Logger;

/**
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jun 18, 2004
 * @version 1.0
 *
 * Exception representing error occuring during builder process.
 * 
 * @version	1.1				October 18, 2007			sv
 * Updated writeAppLog() to write to System.out.
 */
public class BuilderException extends AbstractException {

	private static final Logger log = Logger.getLogger(BuilderException.class);
	/**
	 * @see java.lang.Throwable#Throwable(String)
	 */
    public BuilderException(String message){
        super(message);
    }
    
	/**
	 * @see com.dtcc.dnv.otc.common.exception.AbstractException#writeItoLog()
	 */
    public void writeItoLog(){
        log.info("ITO LOG: " + this);
    }
    
	/**
	 * @see com.dtcc.dnv.otc.common.exception.AbstractException#writeAppLog()
	 */
    public void writeAppLog(){
    	log.info("APP LOG: " + this);
    }
}
