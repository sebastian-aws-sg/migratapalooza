package com.dtcc.dnv.mcx.formatters;

import com.dtcc.dnv.otc.common.exception.BusinessException;
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
 * @version 	1.0
 * @author     Kevin Lake
 * 
 */
public interface IFormatter {

    /* Format input */
	public static final int FORMAT_TYPE_INPUT = 0;
	
	/* Format output */
	public static final int FORMAT_TYPE_OUTPUT = 1;	
	
	/**
	 * @param request
	 * @throws BusinessException
	 */
	void format(FormatRequest request) throws BusinessException;		
	
}
