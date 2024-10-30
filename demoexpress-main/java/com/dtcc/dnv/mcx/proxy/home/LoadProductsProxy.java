/*
 * Created on Oct 9, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.proxy.home;

import com.dtcc.dnv.mcx.db.mca.DPMXMPSPDAO;
import com.dtcc.dnv.mcx.dbhelper.home.AppContextDbResponse;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.AbstractDbProxy;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;

/**
 * @author VVaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LoadProductsProxy extends AbstractDbProxy {

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.layers.IDbProxy#processRequest(com.dtcc.dnv.otc.common.layers.IDbRequest)
	 */
	public IDbResponse processRequest(IDbRequest dbRequest) throws DBException,
			Exception 
	{
		AppContextDbResponse appContDbResp = new AppContextDbResponse();
		DPMXMPSPDAO dao = new DPMXMPSPDAO();
		dao.callSP(dbRequest, appContDbResp);
		
		return appContDbResp;
	}

}
