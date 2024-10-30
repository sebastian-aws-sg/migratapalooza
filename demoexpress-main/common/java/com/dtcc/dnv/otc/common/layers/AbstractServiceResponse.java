package com.dtcc.dnv.otc.common.layers;

import java.io.Serializable;

import com.dtcc.dnv.otc.common.layers.IServiceResponse;

/**
 * @author Toshi Kawanishi
 * @version 1.0
 * 
 * AbstractServiceResponse is the superclass for all the response classes returned by business delegates.
 * 
 * 
 * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate
 * 
 * Added private member for ITransactionBean, and provided getter and setter methods for it.
 * This bean is used to propagate back to the implementing application layer.
 */

public abstract class AbstractServiceResponse implements IServiceResponse,Serializable {

    private ITransactionBean transaction;   // Bean representing transaction/deal used to 
                                             // propagate back to the implementing layer
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

}
