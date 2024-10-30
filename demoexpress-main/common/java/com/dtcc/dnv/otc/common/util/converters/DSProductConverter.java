package com.dtcc.dnv.otc.common.util.converters;

import java.util.Vector;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jul 13, 2007
 * @version 1.0
 * 
 * Converts a product list to various converter interface formats.
 * 
 * @version	1.1				July 21, 2007				sv
 * Added implementation for convertToStringArray(Object dataToConvert).
 */
public class DSProductConverter extends DSBaseConverter {
	/**
	 * @see com.dtcc.dnv.otc.common.util.converters.IDSConverter#convertToString(java.lang.Object)
	 */
	public String convertToString(Object dataToConvert){
		// Local instance of StringBuffer
		StringBuffer sb = new StringBuffer();
		// Proces if the instance of the input object is correct.
		if (dataToConvert instanceof Vector){
			// Cast input object to accurate instance type.
			Vector productsAsVector = (Vector)dataToConvert;
			// Iterate through the collection of products.
			for(int ii=0; ii < productsAsVector.size(); ii++){
				// Append a , on every instance after the first one to separate each instance
				if (ii >0){
					sb.append(",");
				}
				// Generate string for product.
				sb.append(productsAsVector.get(ii));
			}
		}
		// Return string value.
		return (sb.toString());
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.util.converters.IDSConverter#convertToString(java.lang.Object)
	 */
	public String[] convertToStringArray(Object dataToConvert){
		// Local instance of StringBuffer
		String [] st = null;
		// Proces if the instance of the input object is correct.
		if (dataToConvert instanceof Vector){
			// Cast input object to accurate instance type.
			Vector productsAsVector = (Vector)dataToConvert;
			int sz = productsAsVector.size();
			st = new String[sz];
			// Iterate through the collection of products.
			for (int ii = 0; ii < sz; ii++){
				String sProdTypCode = (String) productsAsVector.get(ii);
				st[ii] = sProdTypCode ;
			}
		}
		// Return string array.
		return st;
	}
}
