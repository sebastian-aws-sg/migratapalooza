package com.dtcc.dnv.mcx.user;

/*
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
 * 
 * @author Kevin Lake
 * @version 1.0
 * Date: September 05, 2007
 */

public class UserConstants {
	
	/* 
	 * Marked as protected as these roles should not be referenced
	 * outside of this package.
	 */
	protected static final String MCX_PRODUCT_ROLE = "MCX";
	protected static final String DEALER_UPDATE_ROLE = "MCX-DEALERUPDATE";
	protected static final String DEALER_INQUIRY_ROLE = "MCX-DEALERVIEW";
	protected static final String CLIENT_UPDATE_ROLE = "MCX-CPUPDATE";
	protected static final String CLIENT_INQUIRY_ROLE = "MCX-CPVIEW";
	protected static final String SUPERUSER_UPDATE_ROLE = "MCX-SUPERUSERUPDATE";
	protected static final String SUPERUSER_INQUIRY_ROLE = "MCX-SUPERUSERVIEW";
	protected static final String TEMPLATE_ADMIN_ROLE = "MCX-TMPLTADMIN";
	
    // Static role value constants
	public static final String DEALER_IND = "D";
	public static final String CLIENT_IND = "C";
	public static final String TMPLT_ADMIN_IND = "T";
	public static final String SUPER_IND = "S";
	public static final String USER_INTERNAL = "I";
	public static final String USER_EXTERNAL = "E";

    // Holds the superuser workflow key
	public static final String MCX_SUPERUSER_WORKFLOW = "mcxsuperuser";
	
    // Hold the MCA-Xpress User GUID worflow key
	public static final String MCX_USERGUID_WORKFLOW = "mcxuserguid";
	
	// User company attribute identifier
	public static final String MCX_USER_COMPANY = "usercompany";    
	
	public static final String ADMINTYPE = "A";
	public static final String USERTYPE = "U";
	
	/* 
	 * Default internal company id for internal users. This was done
	 * because internal id is different in every region, so instead
	 * of having representations of every company id in the company table
	 * a default one was created referred to by all internal users.
	 */
	protected static final String DTCC_INTERNAL_COMPANYID = "99999999";
}
