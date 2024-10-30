package com.dtcc.dnv.otc.common.layers;

import com.dtcc.dnv.otc.common.security.model.AuditInfo;
import com.dtcc.dnv.otc.common.layers.IDbRequest;

/**
 * @author Toshi Kawanishi
 * @version 1.0
 * 
 * AbstractDbRequest is the superclass for all the response classes passed to a DbProxy
 * 
 * 
 * @see com.dtcc.dnv.otc.common.layers.IDbProxy
 * 
 * Rev 1.1	May 31, 2004	TK
 * Added the requestId, servCall and auditInfo with the required constructor that take these parameters
 * 
 * Rev 1.2  June 5, 2004        sav
 * Added getter methods (getRequestId(), getServiceCall()) for the corresponding properties.
 * 
 * Rev 1.3      June 18, 2004       sav
 * Added private member for ITransactionBean, and provided getter and setter methods for it.
 * This bean is used to propagate from a calling application layer to the database call.
 * 
 * rev 1.4		June 25, 2004	TK
 * +Added constructor public AbstractDbRequest ( String requestId, AuditInfo auditInfo )
 * +Added public AuditInfo getAuditInfo() for use by CommonDB
 * 
 * Rev	1.5		March 21, 2005		sav
 * Removed default empty constructor.
 */
public abstract class AbstractDbRequest implements IDbRequest {

	private String requestId;		// DB Request Identifier that uniquely maps to a Stored Procedure
	private String servCall;		// For Stored Procedures that take the SERV_CALL parameter
	private AuditInfo auditInfo;	// AuditInfo that needs to propagate down to the persistence layer
    private ITransactionBean transaction;   // Bean representing transaction/deal used to 
                                             // propagate to database call
	
	public AbstractDbRequest ( String requestId, String servCall, AuditInfo auditInfo ) {
		super();
		this.requestId = requestId;
		this.servCall = servCall;
		this.auditInfo = auditInfo;
	}

	public AbstractDbRequest ( String requestId, AuditInfo auditInfo ) {
		super();
		this.requestId = requestId;
		this.servCall = "";
		this.auditInfo = auditInfo;
	}
		
	/**
	 * Returns the requestId.
	 * @return String
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * Returns the servCall.
	 * @return String
	 */
	public String getServiceCall() {
		return servCall;
	}

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
	 * Returns the auditInfo.
	 * @return AuditInfo
	 */
	public AuditInfo getAuditInfo() {
		return auditInfo;
	}

}
