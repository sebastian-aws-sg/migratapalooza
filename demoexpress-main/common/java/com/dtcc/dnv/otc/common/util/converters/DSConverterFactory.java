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
 * Factory class used to return instance of converter by id.
 * 
 * @version	1.1					July 21, 2007			sv
 * Updated to return null if a converter is not found, rather than throw an
 * exception.  Added converter for modules (DS_MODULES).
 */
public class DSConverterFactory {
	// Static convert type id constants
	public static final int DS_PRODUCT = 1;
	public static final int DS_PARTICPANT_LIST = 2;
	public static final int DS_PARTICPANT_LIST_ID = 3;
	public static final int DS_MODULES = 4;
	
	/**
	 * This method returns the respective converter by convert id.
	 * 
	 * @param converterId
	 * @return
	 * @throws DSConverterException
	 */
	public static IConverter getInstance(int converterId){
		// Local instance
		IConverter converter = null;
		// Look for voncerter id.
		if (converterId == DS_PRODUCT){
			converter = new DSProductConverter(); 
		}
		else if (converterId == DS_PARTICPANT_LIST){
			converter = new DSParticipantListConverter(); 
		}
		else if (converterId == DS_PARTICPANT_LIST_ID){
			converter = new DSParticipantListIdConverter(); 
		}
		else if (converterId == DS_MODULES){
			converter = new DSModuleConverter(); 
		}
		// If a converter is not found, then null is intentionally returned.
		return converter;
	}
}
