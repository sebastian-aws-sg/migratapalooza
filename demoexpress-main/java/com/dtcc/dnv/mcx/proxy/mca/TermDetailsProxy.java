/*
 * Created on Sep 13, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.proxy.mca;

import com.dtcc.dnv.mcx.db.mca.DPMXMITMDAO;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbResponse;
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
public class TermDetailsProxy extends AbstractDbProxy {

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.layers.IDbProxy#processRequest(com.dtcc.dnv.otc.common.layers.IDbRequest)
	 */
	public IDbResponse processRequest(IDbRequest dbRequest) throws DBException,
			Exception 
	{
		ModifyTermDbResponse dbResponse = new ModifyTermDbResponse();	
		DPMXMITMDAO dao = new DPMXMITMDAO();       
		dao.callSP(dbRequest, dbResponse);

		return dbResponse;
	}

}
