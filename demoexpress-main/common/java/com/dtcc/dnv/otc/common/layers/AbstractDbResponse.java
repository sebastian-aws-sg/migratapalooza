package com.dtcc.dnv.otc.common.layers;

import com.dtcc.dnv.otc.common.layers.IDbResponse;

/**
 * @author Toshi Kawanishi
 * @version 1.0
 * 
 * AbstractDbResponse is the superclass for all the response classes returned by a DbProxy
 * It is the responsibility of the subclass to implement the methods defined in IDbResponse
 * 
 * @see com.dtcc.dnv.otc.common.layers.IDbProxy
 * 
 * Rev 1.1      June 18, 2004       sav
 * Added private member for ITransactionBean, and provided getter and setter methods for it.
 * This bean is used to propagate from a database call to the calling application layer.
 * 
 * rev 1.2 7/15/2004 scb
 * added spReturnCode
 */
public abstract class AbstractDbResponse implements IDbResponse {

	private Object dbResponse;				// This is where the result from the database is held on return
    private ITransactionBean transaction;   // Bean representing transaction/deal used to 
                                             // propagate from the database call

	// DO NOT USE THESE UNTIL WE DECIDE ON WHAT TO DO WITH SQLCA ERROR CODES
	// THIS WILL DEPEND ON HOW WE DO EXCEPTION / ERROR HANDLING
	private String sqlcaErrorCode = "";
	public void setSqlcaErrorCode ( String error ) { this.sqlcaErrorCode = error; }
	public String getSqlcaErrorCode() { return sqlcaErrorCode; }
	
	private String spReturnCode = "";
	
	

	
	/**
	 * @param Object
	 * 
	 * setContent is called by the DB class to pass any result from the database call
	 */
	public void setContent (Object dbResponse) { this.dbResponse = dbResponse;}
	
	/**
	 * 
	 * getContent is called by the Business Delegate to retrieve the result from the database call
	 */
	public Object getContent () { return this.dbResponse; }

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
	 * Returns the spReturnCode.
	 * @return String
	 */
	public String getSpReturnCode()
	{
		return spReturnCode;
	}

	/**
	 * Sets the spReturnCode.
	 * @param spReturnCode The spReturnCode to set
	 */
	public void setSpReturnCode(String spReturnCode)
	{
		this.spReturnCode = spReturnCode;
	}

}
