package com.dtcc.dnv.mcx.proxy.managedocs;

import com.dtcc.dnv.mcx.db.managedocs.DPMXDORGDAO;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbResponse;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.AbstractDbProxy;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;

/**
 * The proxy to get dealer Details.
 * 
 * Drop down of counterParties for ManagedDocuments
 * RTM Reference : 3.3.17.34,3.3.18.34 It should be possible for a user to 
 * 		differentiate between a manually added/renamed counterparty and the MCA 
 * 		Xpress registered member firm
 * 
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
 * @author Ravikanth G
 * @date Sep 6, 2007
 * @version 1.0
 * 
 *  
 */
public class DealerClientListProxy extends AbstractDbProxy
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(DealerClientListProxy.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.IDbProxy#processRequest(com.dtcc.dnv.otc.common.layers.IDbRequest)
     */
    public IDbResponse processRequest(IDbRequest dbRequest) throws DBException, Exception
    {
       
        
        log.info("In DealerClientListProxy to fetch list of dealers");

        DealerClientListDbResponse dbResponse = new DealerClientListDbResponse();
        
          DPMXDORGDAO dao = new DPMXDORGDAO();
          dao.callSP(dbRequest,dbResponse);
          
          log.info("Returning from DealerClientListProxy");
         

        return dbResponse;

    }

}
