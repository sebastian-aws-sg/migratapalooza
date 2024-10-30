package com.dtcc.dnv.mcx.formatters;

import com.dtcc.dnv.otc.common.layers.ITransactionBean;
import com.dtcc.dnv.otc.common.security.model.IUser;
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
public class FormatRequest {
    
	private ITransactionBean transaction;
	private IUser user;
	private int formatType;
	
	/**
	 * @param transaction
	 * @param user
	 * @param formatType
	 */
	public FormatRequest(ITransactionBean transaction, IUser user, int formatType) {
		this.transaction = transaction;
		this.user = user;
		this.formatType = formatType;		
	}

	/**
	 * @return ITransactionBean
	 */
	public ITransactionBean getTransaction() {
		return transaction;
	}

	/**
	 * @return IUser
	 */
	public IUser getUser() {
		return user;
	}

	/**
	 * @return int
	 */
	public int getFormatType() {
		return formatType;
	}

	/**
	 * @param formatType
	 */
	public void setFormatType(int formatType) {
		this.formatType = formatType;
	}    

}
