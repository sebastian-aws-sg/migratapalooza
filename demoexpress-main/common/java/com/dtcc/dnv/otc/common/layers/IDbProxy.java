package com.dtcc.dnv.otc.common.layers;

import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;

/**
 * @author Toshi Kawanishi
 * @version 1.0
 * 
 * IDbProxy is the interface that must be implemented by the database proxy object.
 * The processRequest() method defines the interface contract between the business
 * tier and the persistence tier.
 * 
 * @see com.dtcc.dnv.otc.common.layers.IDbRequest
 * @see com.dtcc.dnv.otc.common.layers.IDbResponse
 * @see com.dtcc.dnv.otc.common.layers.AbstractDbProxy
 * 
 * Rev 1.1 TK 06/25/2004
 * Added Exception to throws to permit non DBExceptions to be propagated up to the delegate
 */

public interface IDbProxy {

	/**
	 * @param IDbReqeust
	 * @return IDbResponse
	 * @throws DBException, Exception
	 * 
	 * This is the method that must be implemented by the class which acts as the
	 * delegate between the business and persistence tiers.
	 * 
	 * The business delegate is responsible for constructing the dbRequest with all
	 * the information needed by the database tier to service the request.
	 * 
	 * A DbProxy object may not directly access business or presentation tier objects.
	 * 
	 * DbProxy objects must be stateless.
	 * 
	 * DBException is logged automatically.
	 * Exception is not logged and must be caught and logged in the delegate.
	 */
	public IDbResponse processRequest ( IDbRequest dbRequest ) throws DBException, Exception;

}
