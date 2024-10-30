package com.dtcc.dnv.mcx.db.user;

import java.sql.SQLException;

import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.user.UserGUIDDbRequest;
import com.dtcc.dnv.mcx.dbhelper.user.UserGUIDDbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/*
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights
 * reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC) 55, Water Street, New York, NY
 * 10048 U.S.A All Rights Reserved.
 *
 * This software may contain (in part or full) confidential/proprietary
 * information of DTCC. ("Confidential Information"). Disclosure of such
 * Confidential Information is prohibited and should be used only for its
 * intended purpose in accordance with rules and regulations of DTCC.
 *
 * @author Kevin Lake
 *
 * @version 1.0 Date: September 16, 2007
 */

public class DPMXHUSRDAO extends MCXCommonDB {

    /*
     * (non-Javadoc)
     *
     * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest,
     *      com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException
    {
        SPName = "DPMXHUSR";

        UserGUIDDbRequest dbRequest = (UserGUIDDbRequest) request;
        UserGUIDDbResponse dbResponse = (UserGUIDDbResponse)response;

        StringBuffer sb = new StringBuffer();
        sb.append("{ call ");
        sb.append(QUAL);
        sb.append(SPName);
        sb.append("( ?, ?, ?, ?, ?, ?, ?, ?, ?) }");

        cstmt = con.prepareCall(sb.toString());
        cstmt.registerOutParameter(1, java.sql.Types.CHAR);
        cstmt.registerOutParameter(2, java.sql.Types.CHAR);
        cstmt.registerOutParameter(3, java.sql.Types.CHAR);
        cstmt.setString(4, dbRequest.getUserCompanyId());
        cstmt.registerOutParameter(5, java.sql.Types.CHAR);
        cstmt.setString(6, dbRequest.getUserName());
        cstmt.setString(7, dbRequest.getUserId());
        cstmt.setString(8, dbRequest.getUserGUID());
        cstmt.setString(9, dbRequest.getPhoneNumber());

        rs = cstmt.executeQuery();

        sqlca = new SQLCA(cstmt.getString(1));
        sSpErrArea = (String) cstmt.getObject(2);
        dbResponse.setSpReturnCode(cstmt.getString(3));

        if (sqlca.getSqlCode() == 0 &&
        	dbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00)) {
          dbResponse.setMcxGuid(cstmt.getString(5).trim());
		}

    }

}
