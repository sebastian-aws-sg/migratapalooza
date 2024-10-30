
package com.dtcc.dnv.mcx.proxy.managedocs;

import com.dtcc.dnv.mcx.db.managedocs.DPMXDODODAO;
import com.dtcc.dnv.mcx.dbhelper.managedocs.ManageCounterPartyDbResponse;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.AbstractDbProxy;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;

/**
 * 
 * 
 * 
 * RTM Reference : 3.3.17.7, 3.3.18.7   Actor wants to manually add a 
 * counterparty name who does not exist in the MCA Xpress as member firm 
 * 
 * RTM Reference : 3.3.17.8, 3.3.18.8   Actor wants to rename a manually added
 * 			 counterparty name
 * RTM Reference : 3.3.17.31, 3.3.18.19 Only a user with read/write access 
 * 			should be able to add, rename, delete a counterparty
 * RTM Reference : 3.3.18.29 Only one new manual upload counterparty should
 * 			 be re-named at a time
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
 * @date Sep 10, 2007
 * @version 1.0
 * 
 * 
 */

public class ManageCounterPartyProxy extends AbstractDbProxy
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(ManageCounterPartyProxy.class.getName());

    /* (non-Javadoc)
     * @see com.dtcc.dnv.otc.common.layers.IDbProxy#processRequest(com.dtcc.dnv.otc.common.layers.IDbRequest)
     */
    public IDbResponse processRequest(IDbRequest dbRequest) throws DBException, Exception
    {
        
        
        log.info("In ManageCounterPartyProxy");
        ManageCounterPartyDbResponse dbResponse = new ManageCounterPartyDbResponse();
        
       
          DPMXDODODAO dao = new DPMXDODODAO();
          dao.callSP(dbRequest,dbResponse);
         
        log.info("Returning from ManageCounterPartyProxy");        
        return dbResponse;
    }
    

}
