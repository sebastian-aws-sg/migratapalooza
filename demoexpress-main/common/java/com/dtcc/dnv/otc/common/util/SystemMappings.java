/*
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 *
 */
package com.dtcc.dnv.otc.common.util;

//not in use
//import java.util.Arrays;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Set;

import com.dtcc.dnv.otc.common.util.CommonConstants;

/**
 * @author Toshi Kawanishi
 * @version 1.0
 * 
 * SystemMappings is a utility class that holds translations that are needed
 * when crossing system boundaries (e.g. between PCWEB or mainframe stored procedures)
 * 
 * sisIdToProductType maps a system (e.g. dsv, dpr) to a
 * "Product Type" ("CreditDefaultSwapShort", "DerivativeNetting")
 * This translation is needed when DB2 stored procedure is called
 * 
 * pcwebToSidId maps the PCWeb system identifiers (otcd, otcp) to the new SIS ids (dsv, dpr)
 * sisIdToPcweb maps the SIS Id (dsv, dpr) to the PCWeb system identifiers (otcd, otcp)

 */
public abstract class SystemMappings {

	static private HashMap sisIdToProductType = null;
	static private HashMap pcwebToSisId = null;
	static private HashMap sisIdToPCWeb = null;


	// Static initializer to create the mapping	between a system (dsv, dpr) to a product type
	// This translation is needed when stored procedure requires the product type parameter
	// to distinguish between DerivSERV and Payment Reconcilliation
	static {
		sisIdToProductType = new HashMap();
		sisIdToProductType.put(CommonConstants.SYSTEM_DSV, CommonConstants.SYSTEM_OTCD_PRODUCT_TYPE);
		sisIdToProductType.put(CommonConstants.SYSTEM_DPR, CommonConstants.SYSTEM_OTCP_PRODUCT_TYPE);
		sisIdToProductType.put(CommonConstants.SYSTEM_MCX, CommonConstants.SYSTEM_MCX);

		pcwebToSisId = new HashMap();
		pcwebToSisId.put(CommonConstants.SYSTEM_OTCD, CommonConstants.SYSTEM_DSV);
		pcwebToSisId.put(CommonConstants.SYSTEM_OTCP, CommonConstants.SYSTEM_DPR);

		sisIdToPCWeb = new HashMap();
		sisIdToPCWeb.put(CommonConstants.SYSTEM_DSV, CommonConstants.SYSTEM_OTCD);
		sisIdToPCWeb.put(CommonConstants.SYSTEM_DPR, CommonConstants.SYSTEM_OTCP);

	}
	
	/**
	 * Method getProdyctType
	 * @param String systemName
	 * @return String
	 * @throws nothing
	 * 
	 * Return the appropriate "Product Type" for the system
	 */
	public static String getProductType ( String sisId ) {
		return (String) sisIdToProductType.get(sisId.toLowerCase());
	}
	
	/**
	 * Method getSisIds
	 * @param nothing
	 * @return String[]
	 * @throws nothing
	 * 
	 * Return a String array containing the SIS IDs
	 */
	public static String[] getSisIds() {
		Set keys = sisIdToProductType.keySet();
		int size = keys.size();
		String[] sisIds = new String[size];
		Iterator iter = keys.iterator();
		int idx = 0;
		while ( iter.hasNext() )
			sisIds[idx++] = (String) iter.next();
		
		return sisIds;
	}

	/**
	 * Method getPCWebSystems
	 * @param nothing
	 * @return String[]
	 * @throws nothing
	 * 
	 * Return a String array containing the PCWeb system names (otcd, otcp)
	 */
	public static String[] getPCWebSystems() {
		Set keys = pcwebToSisId.keySet();
		int size = keys.size();
		String[] systems = new String[size];
		Iterator iter = keys.iterator();
		int idx = 0;
		while ( iter.hasNext() )
			systems[idx++] = (String) iter.next();
		
		return systems;
	}

	/**
	 * Method sisToPCWeb
	 * @param nothing
	 * @return String
	 * @throws nothing
	 * 
	 * Return a String containing the PCWeb system name that corresponds to a SIS ID
	 */
	public static String sisToPCWeb(String sysId) {
		
		return (String) sisIdToPCWeb.get(sysId.toLowerCase());

	}

	/**
	 * Method pcwebToSis
	 * @param nothing
	 * @return String
	 * @throws nothing
	 * 
	 * Return a String containing the SIS ID that corresponds to a PCWeb system
	 */
	public static String pcwebToSis(String system) {
		
		return (String) pcwebToSisId.get(system.toLowerCase());

	}

}
