package com.dtcc.dnv.otc.common.layers;

/**
 * @author Toshi Kawanishi
 * @version 1.0
 * 
 * IDbResponse is the interface that must be implemented by the transport object
 * that is returned by the DbProxy
 * 
 * @see com.dtcc.dnv.otc.common.layers.IDbProxy
 * @see com.dtcc.dnv.otc.common.layers.AbstractDbProxy
 * 
 * Changes:
 * DLV 7/3/2004 Added getTransaction() setTransaction(..)
 * method in order to make it consistent with changes to
 * AbstractDbResponse class.
 * 
 * DLV 7/15/2004 In order to synch up with changes in abstruct
 * DB response, following methods have been added
 *     getSpReturnCode():String
 *     setSpReturnCode(String spReturnCode):void
 * 
 * Rev 1.3      December 15, 2004       sav
 * Removed previous revision changes as it is not part of the framework.  This workfile
 * has been reverted to 1.1
 */
public interface IDbResponse {
	
	public void setContent(Object dbResult);
	public Object getContent ();
	public void setSqlcaErrorCode ( String error );
}
