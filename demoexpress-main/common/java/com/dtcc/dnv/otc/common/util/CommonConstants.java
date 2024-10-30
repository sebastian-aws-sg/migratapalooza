package com.dtcc.dnv.otc.common.util;

/**
 * <p>Title: OTC</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: DTCC</p>
 * @author unascribed
 * @version 1.0
 * 
 * Rev  1.134 January 24, 2004    sav
 * Added kProcessId constant to represent a process (i.e Search, Trade Upload, etc).
 */

public class CommonConstants
{
    public static final String SYSTEM_OTCD =               "otcd";	// DerivSERV in PCWeb context
    public static final String SYSTEM_DSV = 				"dsv"; 		// DerivSERV in Struts context
    public static final String SYSTEM_OTCP =               "otcp";	// Payment Rec in PCWeb context
    public static final String SYSTEM_DPR = 				"dpr";		// Payment Rec in Struts context
    public static final String SYSTEM_MCX = 				"mcx";		// MCA-Xpress in Struts context
    
    public static final String SYSTEM_OTCD_PRODUCT_TYPE =  "CreditDefaultSwapShort";
    public static final String SYSTEM_OTCP_PRODUCT_TYPE =  "DerivativeNetting";
    
}

