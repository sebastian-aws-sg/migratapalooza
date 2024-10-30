/*
 * Created on Oct 23, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.db.home;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtcc.dnv.mcx.beans.UserDetails;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.home.UserDetailsDbRequest;
import com.dtcc.dnv.mcx.dbhelper.home.UserDetailsDbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

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
 * This class calls the stored procedures for fetching the user details.
 * 
 */

public class DPMXHUSDDAO extends MCXCommonDB
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXHUSDDAO.class.getName());

    /* (non-Javadoc)
     * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest, com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException
    {
        log.info("inside execute method in DPMXHUSDDAO");
        SPName = "DPMXHUSD";
        UserDetailsDbRequest dbRequest = (UserDetailsDbRequest) request;
        UserDetailsDbResponse dbResponse = (UserDetailsDbResponse) response;
        UserDetails userDetails = new UserDetails();
        List userDetailsList = new ArrayList();
        String userId = dbRequest.getUserId();
        StringBuffer buffer = new StringBuffer();
        buffer.append("{ call ");
        buffer.append(QUAL);
        buffer.append(SPName);
        buffer.append("( ?, ?, ?, ?, ?, ?, ?, ?) }");

        cstmt = con.prepareCall(buffer.toString());
        cstmt.registerOutParameter(1, java.sql.Types.CHAR);
        cstmt.registerOutParameter(2, java.sql.Types.CHAR);
        cstmt.registerOutParameter(3, java.sql.Types.CHAR);
        cstmt.setString(4, dbRequest.getUserId());
        cstmt.registerOutParameter(5, java.sql.Types.CHAR);
        cstmt.registerOutParameter(6, java.sql.Types.CHAR);
        cstmt.registerOutParameter(7, java.sql.Types.CHAR);
        cstmt.registerOutParameter(8, java.sql.Types.CHAR);

        rs = cstmt.executeQuery();

        sqlca = new SQLCA(cstmt.getString(1));
        sSpErrArea = (String) cstmt.getObject(2);
        String thisreturncode = (String) cstmt.getObject(3);
        dbResponse.setSpReturnCode(thisreturncode);
        dbResponse.setSpResponseMessage(sSpErrArea);
        if (sqlca.getSqlCode() == 0 & dbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
        {
            dbResponse.setUserDetailsStatus(MCXConstants.SUCCESS_FORWARD);
            userDetails.setFirmName(cstmt.getString(5));            
            userDetails.setUserName(cstmt.getString(6));
            userDetails.setUserEmail(cstmt.getString(7));
            userDetails.setPhoneNumber(cstmt.getString(8));
        }
        dbResponse.setUserDetails(userDetails);
    }

}
