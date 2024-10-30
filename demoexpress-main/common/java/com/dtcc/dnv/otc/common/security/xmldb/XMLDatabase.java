package com.dtcc.dnv.otc.common.security.xmldb;

/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55, Water Street,
 * New York, NY 10048
 * U.S.A
 * All Rights Reserved.
 *
 * This software may contain (in part or full) confidential/proprietary information of DTCC.
 * ("Confidential Information"). Disclosure of such Confidential
 * Information is prohibited and should be used only for its intended purpose
 * in accordance with rules and regulations of DTCC.
 * Form bean for a Struts application.
 *
 * @author Dmitriy Larin
 * @version 1.0
 */

import java.io.File;

import javax.servlet.ServletContext;

import com.dtcc.dnv.otc.common.exception.XMLDatabaseException;


/**
 * 
 * UserXMLDatabase - Simple implementation of a User
 * repository stored in an XML document on the
 * file system. Implements the singleton pattern
 * to avoid thread safety issues. 
 * 
 * @version	1.1			October 18, 2007			sv
 * Removed unused imports.  Removed main().
 */
public class XMLDatabase {

	//private Document document = null;
	private File file = null;
	
	/**
	 * XMLDatabase Constructor 
	 * 
	 * 
	 * @param File The file containing the XML
	 * @exception 
	 * opening or parsing the file
	 */
	public XMLDatabase(File entitlements, File rules, ServletContext sc)
		throws XMLDatabaseException 
	{


	}
}