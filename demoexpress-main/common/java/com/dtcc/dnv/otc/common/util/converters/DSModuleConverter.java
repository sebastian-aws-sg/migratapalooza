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
 * Converts a module list to various converter interface formats.
 */
public class DSModuleConverter extends DSBaseConverter {
	/**
	 * @see com.dtcc.dnv.otc.common.util.converters.IDSConverter#convertToString(java.lang.Object)
	 */
	public String[] convertToStringArray(Object dataToConvert){
		// Local instance of String array
		String [] st = null;
		// Proces if the instance of the input object is correct.
		if (dataToConvert instanceof Vector){
			// Cast input object to accurate instance type.
			Vector modulesAsVector = (Vector)dataToConvert;
			int sz = modulesAsVector.size();
			// Instantiate array.
			st = new String[sz];
			// Iterate through array and create string entries.
			for (int ii = 0; ii < sz; ii++){
				String sModuleCode = (String) modulesAsVector.get(ii);
				st[ii] = sModuleCode ;
			}
		}
		// Return string array.
		return st;
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.util.converters.IDSConverter#convertToString(java.lang.Object)
	 */
	public String convertToString(Object dataToConvert){
		// Local instance of StringBuffer
		StringBuffer sb = new StringBuffer();
		// Proces if the instance of the input object is correct.
		if (dataToConvert instanceof Vector){
			// Cast input object to accurate instance type.
			Vector modulesAsVector = (Vector)dataToConvert;
			// Iterate through the collection of products.
			for(int ii=0; ii < modulesAsVector.size(); ii++){
				// Append a , on every instance after the first one to separate each instance
				if (ii >0){
					sb.append(",");
				}
				// Generate string for modules.
			    sb.append(modulesAsVector.get(ii));
			}			
		}
		// Return string value.
		return (sb.toString());
	}
}
