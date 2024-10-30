package com.dtcc.dnv.otc.common.util.converters;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jul 13, 2007
 * @version 1.0
 * 
 * Exception class for converters.
 * 
 * @version	1.1				October 3, 2007			sv
 * Updated DSConverterException()String, Throwable) method to 
 * get message from throwable.
 * 
 * @version	1.2				October 9, 2007			sv
 * Removed constructor which took Throwable parameter.
 */
public class DSConverterException extends Exception {

    /**
     * Default constructor method.
     */
    public DSConverterException() {
    	super();
    }

    /**
     * @param message
     */
    public DSConverterException(String message) {
    	super(message);
    }
 
    /**
     * @param message
     * @param cause
     */
    public DSConverterException(String message, Throwable cause) {
        super(message + ": " + cause.getMessage());
    }

}
