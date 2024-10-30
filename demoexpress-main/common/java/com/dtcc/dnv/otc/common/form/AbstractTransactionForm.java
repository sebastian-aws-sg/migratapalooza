package com.dtcc.dnv.otc.common.form;

import org.apache.struts.action.ActionForm;

import com.dtcc.dnv.otc.common.layers.ITransactionBean;


/**
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jun 7, 2004
 * @version 1.0
 *
 * Abstract class used to provide common APIs for forms that use a ITransactionBean.
 * 
 * Rev  1.1     June 22, 2004        sav
 * Added firstTime property and getter and setter methods.
 */
public class AbstractTransactionForm extends ActionForm{
    
    private ITransactionBean transaction = null;
    private boolean firstTime = true;
    
	/**
	 * Returns the transaction.
	 * @return ITransactionBean
	 */
	public ITransactionBean getTransaction() {
		return transaction;
	}

	/**
	 * Sets the transaction.
	 * @param transaction The transaction to set
	 */
	public void setTransaction(ITransactionBean transaction) {
		this.transaction = transaction;
	}

	/**
	 * Returns the firstTime.
	 * @return boolean
	 */
	public boolean isFirstTime() {
		return firstTime;
	}
    
    /**
     * Returns the firstTime.
     * @return boolean
     */
    public boolean getFirstTime() {
        return firstTime;
    }

	/**
	 * Sets the firstTime.
	 * @param firstTime The firstTime to set
	 */
	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}

}
