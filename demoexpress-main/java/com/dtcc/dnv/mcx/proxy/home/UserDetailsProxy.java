/*
 * Created on Oct 23, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.proxy.home;

import com.dtcc.dnv.mcx.db.home.DPMXHUSDDAO;
import com.dtcc.dnv.mcx.dbhelper.home.UserDetailsDbRequest;
import com.dtcc.dnv.mcx.dbhelper.home.UserDetailsDbResponse;
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
 * @date Oct 23, 2007
 * @version 1.0
 * 
 * 
 * Proxy class for getting the user details
 */

public class UserDetailsProxy extends AbstractDbProxy
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(UserDetailsProxy.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.IDbProxy#processRequest(com.dtcc.dnv.otc.common.layers.IDbRequest)
     *      This method calls the sp to fetch the pending mca details.
     */
    public IDbResponse processRequest(IDbRequest dbRequest) throws DBException, Exception
    {

        log.info("inside user details proxy");
        UserDetailsDbRequest request = (UserDetailsDbRequest) dbRequest;
        UserDetailsDbResponse dbResponse = new UserDetailsDbResponse();
        DPMXHUSDDAO dao = new DPMXHUSDDAO();
        dao.callSP(dbRequest, dbResponse);
        return dbResponse;
    }
}
