package com.dtcc.dnv.otc.common.layers;

import com.dtcc.dnv.otc.common.layers.IDbProxy;

/**
 * @author Toshi Kawanishi
 * @version 1.0
 * 
 * AbstractDbProxy is the superclass for all the DbProxy classes in the application.
 * It is the responsibility of the subclass to implement the methods defined in IDbProxy
 * 
 * @see com.dtcc.dnv.otc.common.layers.IDbRequest
 * @see com.dtcc.dnv.otc.common.layers.IDbResponse
 * @see com.dtcc.dnv.otc.common.layers.IDbProxy
 */
public abstract class AbstractDbProxy implements IDbProxy {

}
