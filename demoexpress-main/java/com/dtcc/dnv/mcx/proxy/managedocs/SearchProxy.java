package com.dtcc.dnv.mcx.proxy.managedocs;

import com.dtcc.dnv.mcx.db.managedocs.DPMXDGTDDAO;
import com.dtcc.dnv.mcx.dbhelper.managedocs.SearchDbRequest;
import com.dtcc.dnv.mcx.dbhelper.managedocs.SearchDbResponse;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.AbstractDbProxy;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;

/**
 * The proxy for Search functionality.
 *  RTM Reference : 3.3.16 Basic Flow (Except delete documents) Searching for
 * 		the documents based on the parameters Dealer/Client Id,Document type,
 * 		Document Name. Displays Dealer/ClientId MCA/File name,Document type, 
 * 		Execution date,CP Viewable,uploaded by user,uploaded on date that
 * 		satisfy the search criteria.
 * RTM Reference : 3.3.16.1 Actor wants to search for “Other documents” 
 * RTM Reference : 3.3.16.2 Actor wants to search for “All” documents against
 * 		 “All” counterparties 
 * RTM Reference : 3.3.16.3 Actor wants to search for “All” documents against
 * 		specific counterparty 
 *  
 * 
 * RTM Reference : 3.3.16.15 Execution Date should be available only in case of
 * 		Pre-Exec documents and it should be blank for “other documents” 
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
public class SearchProxy extends AbstractDbProxy
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(SearchProxy.class.getName());
    //private static final Logger log = Logger.getLogger(SearchProxy.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.IDbProxy#processRequest(com.dtcc.dnv.otc.common.layers.IDbRequest)
     */
    public IDbResponse processRequest(IDbRequest dbRequest) throws DBException, Exception
    {     

        log.info("In SearchProxy to fetch the document library.");

        SearchDbResponse dbResponse = new SearchDbResponse();
        SearchDbRequest searchDbRequest = (SearchDbRequest) dbRequest;

       

         DPMXDGTDDAO dao = new DPMXDGTDDAO();
         dao.callSP(dbRequest,dbResponse);

        log.info("Returning from SearchProxy");

        return dbResponse;
    }

}
