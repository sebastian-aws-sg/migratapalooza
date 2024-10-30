package com.dtcc.dnv.mcx.proxy.managedocs;

import com.dtcc.dnv.mcx.db.managedocs.DPMXDDLDDAO;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbResponse;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.AbstractDbProxy;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;

/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights
 * reserved.
 * 
 * Depository Trust & Clearing Corporation (DTCC) 55, Water Street, New York, NY
 * 10048, U.S.A All Rights Reserved.
 * 
 * This software may contain (in part or full) confidential/proprietary
 * information of DTCC. ("Confidential Information"). Disclosure of such
 * Confidential Information is prohibited and should be used only for its
 * intended purpose in accordance with rules and regulations of DTCC.
 * 
 * @author Elango TR
 * @date Sep 6, 2007
 * @version 1.0
 * 
 *  
 */
public class DownloadMCADocProxy extends AbstractDbProxy
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(DownloadMCADocProxy.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.IDbProxy#processRequest(com.dtcc.dnv.otc.common.layers.IDbRequest)
     */
    public IDbResponse processRequest(IDbRequest dbRequest) throws DBException, Exception
    {
        log.info("In DownloadMCADocProxy to fetch list of dealers");
        DealerClientListDbResponse dbResponse = new DealerClientListDbResponse();
        DPMXDDLDDAO dao = new DPMXDDLDDAO();
        dao.callSP(dbRequest,dbResponse);
        log.info("Returning from DownloadMCADocProxy");
        return dbResponse;
    }
}
