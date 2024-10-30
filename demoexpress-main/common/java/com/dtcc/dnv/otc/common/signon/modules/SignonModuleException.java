package com.dtcc.dnv.otc.common.signon.modules;

import org.apache.log4j.Logger;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Aug 30, 2007
 * @version 1.0
 * 
 * This class represent an exception thrown by signon modules.
 *
 * @version	1.1				August 30, 2007			sv
 * Added comments
 * 
 * @version	1.2				September 12, 2007		sv
 * Updated eception to hold more properties.
 * 
 * @version	1.3				October 3, 2007			sv
 * Updated DSConverterException()String, Throwable) method to 
 * get message from throwable.
 * 
 * @version	1.4				October 17, 2007		sv
 * Updated settters for type of module exception to handle all types of
 * boolean values rather than using a conventional setter.  This is used 
 * to isolate the implementation.
 * 
 * @version	1.6				October 18, 2007		sv
 * Updated logger to be static final.
 */
public class SignonModuleException extends Exception {
	// Logger instance
	private static final Logger log = Logger.getLogger(SignonModuleException.class);
	
	// Indicates the module in which the error condition was detected.
	private String moduleName = "";
	private String errorType = "";
	private boolean processingException = false;
	private boolean postProcessingException = false;
	
	/**
     *  Constructs a SignonModuleException
     * 
     * @param errorType the value that indicates the error type
     * @param moduleName the value that indicates the signon module name
     * @param errorMessage the error message
     */
    public SignonModuleException(String errorType,String moduleName,String errorMessage){
    	super(errorMessage);
    	this.setModuleName(moduleName);
    	this.setErrorType(errorType);
    }
    
    /**
     *  Constructs a SignonModuleException
     * 
     * @param errorType the value that indicates the error type
     * @param moduleName the value that indicates the signon module name
     * @param errorMessage the error message
     */
    public SignonModuleException(String errorType,String moduleName,String errorMessage,Throwable moduleExcep){
    	super(errorMessage + ": " + moduleExcep.getMessage());
    	this.setModuleName(moduleName);
    	this.setErrorType(errorType);
    }
    
	/**
	 * @return Returns the moduleName.
	 */
	public String getModuleName() {
		return this.moduleName;
	}
	
	public String getErrorType() {
		return this.errorType;
	}
	
	/**
	 * @param moduleName The moduleName to set.
	 */
	private void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	/**
	 * @param errorType
	 */
	private void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	/**
	 * @return Returns the postProcessingException.
	 */
	public boolean isPostProcessingException() {
		return postProcessingException;
	}
	/**
	 * @param postProcessingException The postProcessingException to set.
	 */
	public void setPostProcessingException() {
		this.processingException = false;
		this.postProcessingException = true;
	}
	/**
	 * @return Returns the processingException.
	 */
	public boolean isProcessingException() {
		return processingException;
	}
	/**
	 * @param processingException The processingException to set.
	 */
	public void setProcessingException() {
		this.processingException = true;
		this.postProcessingException = false;
	}
}
