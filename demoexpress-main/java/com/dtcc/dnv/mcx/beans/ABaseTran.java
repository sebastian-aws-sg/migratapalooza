package com.dtcc.dnv.mcx.beans;

import java.io.Serializable;

import com.dtcc.dnv.otc.common.layers.ITransactionBean;

/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55, Water Street,
 * New York, NY 10048
 * U.S.A
 * All Rights Reserved.
 *
 *     This software may contain (in part or full) confidential/proprietary 
 * information of DTCC.("Confidential Information"). Disclosure of such 
 * Confidential Information is prohibited and should be used only for its 
 * intended purpose in accordance with rules and regulations of DTCC.
 * Form bean for a Struts application.
 *
 * @version    1.0
 * @author     svijayak
 *  	 
 */
public abstract class ABaseTran implements ITransactionBean, Serializable {
	
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.layers.ITransactionBean#getClone()
	 */
	public ITransactionBean getClone() throws CloneNotSupportedException {
		return (ITransactionBean) super.clone();
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.layers.ITransactionBean#formatCurrencies()
	 */
	public void formatCurrencies() {}
}