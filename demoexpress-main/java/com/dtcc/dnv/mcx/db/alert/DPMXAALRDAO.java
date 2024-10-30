package com.dtcc.dnv.mcx.db.alert;
import java.sql.SQLException;

import com.dtcc.dnv.mcx.beans.AlertInfo;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.alert.AlertDbRequest;
import com.dtcc.dnv.mcx.dbhelper.alert.AlertDbResponse;
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
 * This class is used as DAO to post alert.
 *
 */
public class DPMXAALRDAO extends MCXCommonDB {
    /* (non-Javadoc)
     * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest, com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException
    {
        SPName = "DPMXAALR";

        AlertDbResponse dbResponse = (AlertDbResponse) response;
        AlertDbRequest dbRequest = (AlertDbRequest) request;
        StringBuffer sb = new StringBuffer();
        sb.append("{ call ");
        sb.append(QUAL);
        sb.append(SPName);
        sb.append(" ( ?, ?, ?, ? ,?, ?, ?) }");

        cstmt = con.prepareCall(sb.toString());
        cstmt.registerOutParameter(1, java.sql.Types.CHAR);
        cstmt.registerOutParameter(2, java.sql.Types.CHAR);
        cstmt.registerOutParameter(3, java.sql.Types.CHAR);
        cstmt.registerOutParameter(4, java.sql.Types.CHAR);

        AlertInfo thealertinfo = (AlertInfo)dbRequest.getTransaction();
        cstmt.setString(5, thealertinfo.getSubject());
        cstmt.setString(6, thealertinfo.getMessage());
        cstmt.setString(7, dbRequest.getUser().getMCXUserGUID());

        cstmt.executeUpdate();

        sqlca = new SQLCA(cstmt.getString(1));
        sSpErrArea = (String) cstmt.getObject(2);
        sprc = (String) cstmt.getObject(3);

		if (sqlca.getSqlCode() == 0)
		{
			dbResponse.setAlertStatus("success");
		}

    }

}
