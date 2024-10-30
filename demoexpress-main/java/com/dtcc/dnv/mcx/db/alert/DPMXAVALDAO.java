package com.dtcc.dnv.mcx.db.alert;

import java.sql.SQLException;

import com.dtcc.dnv.mcx.beans.AlertInfo;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.alert.AlertDbRequest;
import com.dtcc.dnv.mcx.dbhelper.alert.AlertDbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
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
 * @author Peng Zhou
 * @date Oct 01, 2007
 * @version 1.0
 * 
 * This class is used as DAO to retrieve alert list.
 *  
 */
public class DPMXAVALDAO extends MCXCommonDB
{
    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest,
     *      com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException
    {
        SPName = "DPMXAVAL";

        AlertDbResponse dbResponse = (AlertDbResponse) response;
        AlertDbRequest dbRequest = (AlertDbRequest) request;
        StringBuffer sb = new StringBuffer();
        sb.append("{ call ");
        sb.append(QUAL);
        sb.append(SPName);
        sb.append(" ( ?, ?, ?, ?) }");

        cstmt = con.prepareCall(sb.toString());
        cstmt.registerOutParameter(1, java.sql.Types.CHAR);
        cstmt.registerOutParameter(2, java.sql.Types.CHAR);
        cstmt.registerOutParameter(3, java.sql.Types.CHAR);
        AlertInfo thealertinfo = (AlertInfo) dbRequest.getTransaction();

        cstmt.setString(4, thealertinfo.getUsertype());

        rs = cstmt.executeQuery();

        sqlca = new SQLCA(cstmt.getString(1));
        sSpErrArea = (String) cstmt.getObject(2);
        String thisreturncode = (String) cstmt.getObject(3);
        dbResponse.setSpReturnCode(thisreturncode);
        dbResponse.setSpResponseMessage(sSpErrArea);
        if (sqlca.getSqlCode() == 0 && dbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
        {
            getAlertList(dbResponse);
            dbResponse.setAlertStatus("success");
        }

    }

    /**
     * get alert list from resultset
     * 
     * @param dbResponse
     * @throws SQLException
     */
    private void getAlertList(AlertDbResponse dbResponse) throws SQLException
    {
        String updatetimestamp;

        while (rs.next())
        {
            AlertInfo alertinfo = new AlertInfo();
            alertinfo.setAlertid(rs.getString(1));
            alertinfo.setSubject(rs.getString(2));
            alertinfo.setUpdatetimestamp(rs.getString(3));
            alertinfo.setUserName(rs.getString(4));
            alertinfo.setUserid(rs.getString(5));

            dbResponse.addAlert(alertinfo);
        }

    }

}
