/*
 * Created on Sep 3, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dtcc.dnv.mcx.proxy.home;

import com.dtcc.dnv.mcx.db.home.DPMXHEXPDAO;
import com.dtcc.dnv.mcx.dbhelper.home.DisplayMCAsDbRequest;
import com.dtcc.dnv.mcx.dbhelper.home.DisplayMCAsDbResponse;
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
 * @author Nithya R
 * @date Sep 10, 2007
 * @version 1.0
 * 
 * Proxy class for getting the pending mca details.
 * RTM References:
 * 	3.3.6.1 	To view the executed MCA from the executed MCA tab and actor clicks the MCA type to view the detailed fields of the document
 *  3.3.6.15 	Client users should only be able to view the records in executed MCAs tab irrespective of their read/write privileges
 *  3.3.14.1 	The actor clicks the executed MCAs tab
 			 	System would show the executed MCA records in the executed MCA tab
 *  3.3.14.16 	Pre-executed MCAs uploaded by the dealer should be viewable only by the dealer users on the 
 * 				executed MCAs tab and not by the client users in their executed MCAs tab
 *  3.3.5.13	Actor chooses to view the “Executed MCAs” tab
 
 */
public class DisplayMCAsProxy extends AbstractDbProxy
{
	private final static MessageLogger log = MessageLogger.getMessageLogger(DisplayMCAsProxy.class.getName());
 //  private static final Logger log = Logger.getLogger(DisplayMCAsProxy.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.IDbProxy#processRequest(com.dtcc.dnv.otc.common.layers.IDbRequest)
     *      This method calls the sp to fetch the pending mca details.
     */
    public IDbResponse processRequest(IDbRequest dbRequest) throws DBException, Exception
    {

        log.info("inside Display mca proxy");

        DisplayMCAsDbRequest request = (DisplayMCAsDbRequest) dbRequest;

        DisplayMCAsDbResponse dbResponse = new DisplayMCAsDbResponse();        

        DPMXHEXPDAO dao = new DPMXHEXPDAO();
        dao.callSP(request,dbResponse);
        return dbResponse;
    }
}
