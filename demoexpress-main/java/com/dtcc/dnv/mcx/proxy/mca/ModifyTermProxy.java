/*
 * Created on Sep 12, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.proxy.mca;

import com.dtcc.dnv.mcx.db.mca.DPMXMAMNDAO;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbResponse;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.AbstractDbProxy;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.CommonDB;

/**
 * @author VVaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ModifyTermProxy extends AbstractDbProxy {

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.layers.IDbProxy#processRequest(com.dtcc.dnv.otc.common.layers.IDbRequest)
	 */
	public IDbResponse processRequest(IDbRequest dbRequest) throws DBException,
			Exception 
	{
		ModifyTermDbResponse dbResponse = new ModifyTermDbResponse();	
        CommonDB dao = new DPMXMAMNDAO();
        dao.callSP(dbRequest, dbResponse);
       
        return dbResponse;
	}
	
}
