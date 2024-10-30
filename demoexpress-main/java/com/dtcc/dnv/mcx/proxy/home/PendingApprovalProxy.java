package com.dtcc.dnv.mcx.proxy.home;

import com.dtcc.dnv.mcx.db.home.DPMXHAPEDAO;
import com.dtcc.dnv.mcx.dbhelper.home.PendingEnrollApprovalDbResponse;

import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.AbstractDbProxy;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;

/**
 * proxy class to send the db request to the Dao to retrieve the data for
 * loading the Pending Enrollments Approval and Approved Firms screen RTM
 * Reference : 3.3.11.1 The actor views the pending Enrolment approval tab for
 * the respective MCA types against the clients awaiting approval, approves a
 * client and all the MCAs requested for by him RTM Reference : 3.3.11.2 Actor
 * selects to approve only one or more but not all of the MCA Enrolment requests
 * for the client RTM Reference : 3.3.11.7 A Dealer should only be able to
 * approve or deny Enrolment of a Client that has initiated firm Enrolment RTM
 * Reference : 3.3.11.12 Once a Client firm is approved with at least one
 * associated MCA(s), that Client should never be removed from the Dealer’s
 * ‘Approved Firms’ tab
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
 * @author Vigneshwari R
 * @date Sep 7, 2007
 * @version 1.0
 * 
 *  
 */
public class PendingApprovalProxy extends AbstractDbProxy
{

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.IDbProxy#processRequest(com.dtcc.dnv.otc.common.layers.IDbRequest)
     *      Passes the request to the db from the delegate and returns the
     *      response coming from the db to the delegate.
     */
    public IDbResponse processRequest(IDbRequest dbRequest) throws DBException, Exception
    {

        PendingEnrollApprovalDbResponse dbResponse = new PendingEnrollApprovalDbResponse();

        DPMXHAPEDAO dao = new DPMXHAPEDAO();
        dao.callSP(dbRequest, dbResponse);
        return dbResponse;
    }

}
