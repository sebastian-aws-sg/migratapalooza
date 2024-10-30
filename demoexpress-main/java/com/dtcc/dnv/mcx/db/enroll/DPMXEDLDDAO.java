package com.dtcc.dnv.mcx.db.enroll;

import java.sql.SQLException;

import com.dtcc.dnv.mcx.beans.DealerList;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.enroll.DealerListDbRequest;
import com.dtcc.dnv.mcx.dbhelper.enroll.DealerListDbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
 * This class is used as a DAO to retrive List of Dealer details
 * 
 * RTM Reference : 3.3.3.1 To create an enrolment request, actor is directed to
 * the enrolment list of dealers, selection of MCAs throuhg enrolment grid and
 * the enrolment confirmation screen
 * 
 * RTM Reference : 3.3.3.2 Actor chooses to select a another MCA for enrolment
 * after the previous MCA is approved by dealer
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
 * @author Prabhu K
 * @date Sep 3, 2007
 * @version 1.0
 * 
 *  
 */
public class DPMXEDLDDAO extends MCXCommonDB
{

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest,
     *      com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException
    {
        SPName = "DPMXEDLD";

        DealerListDbResponse dbResponse = (DealerListDbResponse) response;
        DealerListDbRequest dbRequest = (DealerListDbRequest) request;

        StringBuffer sb = new StringBuffer();
        sb.append("{ call ");
        sb.append(QUAL);
        sb.append(SPName);
        sb.append("( ?, ?, ?, ? ) }");

        cstmt = con.prepareCall(sb.toString());
        cstmt.registerOutParameter(1, java.sql.Types.CHAR);
        cstmt.registerOutParameter(2, java.sql.Types.CHAR);
        cstmt.registerOutParameter(3, java.sql.Types.CHAR);
        cstmt.setString(4, dbRequest.getCompanyID());

        rs = cstmt.executeQuery();

        sqlca = new SQLCA(cstmt.getString(1));
        sSpErrArea = cstmt.getString(2).trim();
        dbResponse.setSpReturnCode(cstmt.getString(3).trim());
        dbResponse.setSpResponseMessage(sSpErrArea);  
        if (sqlca.getSqlCode() == 0 && dbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
        {
            dbResponse = getDealerList(dbResponse);
        }
    }

    /**
     * This method is used to get the Dealer list from the result set
     * 
     * @param dbResponse
     * @return
     */
    private DealerListDbResponse getDealerList(DealerListDbResponse dbResponse) throws SQLException
    {
        String FED_18_DEALER = "F";
        while (rs != null && rs.next())
        {
            DealerList dealerList = new DealerList();
            dealerList.setDealerCode(rs.getString(1).trim());
            dealerList.setDealerName(rs.getString(2).trim());
            dealerList.setFed18Indicator(rs.getString(3).trim());
            dealerList.setEnrollStatus(rs.getString(4).trim());

            if (dealerList.getFed18Indicator().equals(FED_18_DEALER))
            {
                dbResponse.addFedDealerList(dealerList);
            } else
            {
                dbResponse.addOtherDealerList(dealerList);
            }
            dbResponse.addDealerMap(dealerList, dealerList.getDealerCode());
        }

        return dbResponse;
    }

}
