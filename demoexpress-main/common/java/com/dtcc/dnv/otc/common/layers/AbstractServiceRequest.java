package com.dtcc.dnv.otc.common.layers;

import com.dtcc.dnv.otc.common.security.model.AuditInfo;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;

/**
 * @author Toshi Kawanishi
 * @version 1.0
 * 
 * AbstractServiceRequest is the superclass for all the request classes passed to business delegates.
 * It is the responsibility of the subclass to implement the methods defined in IServiceRequest
 * 
 * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate
 * 
 * Rev 1.1	May 31, 2004	TK
 * Added auditInfo and a requisite constructor that takes the AuditInfo as a parameter
 * 
 * Rev 1.2      June 18, 2004       sav
 * Added private member for ITransactionBean, and provided getter and setter methods for it.
 * This bean is used to propagate from a calling application layer to the delegate.
 * 
 * Rev	1.3		March 21, 2005		sav
 * Removed default empty constructor.  Implementors must use constructor passing an
 * AuditInfo object.
 */
public abstract class AbstractServiceRequest implements IServiceRequest {

	private AuditInfo auditInfo;	// AuditInfo that needs to propagate down to the business layer
    private ITransactionBean transaction;   // Bean representing transaction/deal used to 
                                             // propagate to the delegate
	
	public AbstractServiceRequest ( AuditInfo auditInfo ) {
		super();
		this.auditInfo = auditInfo;
	}

	public AuditInfo getAuditInfo() { return this.auditInfo; }

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
