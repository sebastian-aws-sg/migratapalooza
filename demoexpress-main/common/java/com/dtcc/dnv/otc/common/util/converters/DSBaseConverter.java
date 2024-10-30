package com.dtcc.dnv.otc.common.util.converters;

import java.util.Vector;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jul 21, 2007
 * @version 1.0
 * 
 * Bast converter class which defaults the implementations to return null in
 * the case where a converter does not support the conversion method.
 * 
 * @version	1.1					July 21, 2007			sv
 * Updated to implement IConverter and added default implementations for 
 * convertToStringArray(Object)
 */
public class DSBaseConverter implements IConverter {
	/**
	 * Default constructor at default scope.
	 */
	DSBaseConverter(){
		super();
	}
	
	/**
	 * Used to convert object to a String
	 * 
	 * @see com.dtcc.dnv.otc.common.util.converters.IDSConverter#convertToString(java.lang.Object)
	 */
	public String convertToString(Object dataToConvert){
		return null;
	}

	/**
	 * Used to convert object to a Vector.
	 * 
	 * @see com.dtcc.dnv.otc.common.util.converters.IDSConverter#convertToLabelValueVector(java.lang.Object)
	 */
	public Vector convertToLabelValueVector(Object dataToConvert){
		return null;
	}
	
	/**
	 * Used to convert object to a String array.
	 * 
	 * @see com.dtcc.dnv.otc.common.util.converters.IDSConverter#convertToStringArray(java.lang.Object)
	 */
	public String[] convertToStringArray(Object dataToConvert){
		return null;
	}
}
