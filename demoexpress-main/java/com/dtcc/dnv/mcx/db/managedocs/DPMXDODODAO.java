package com.dtcc.dnv.mcx.db.managedocs;

import java.sql.SQLException;

import com.dtcc.dnv.mcx.beans.ManageDocsTransactionBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.managedocs.ManageCounterPartyDbRequest;
import com.dtcc.dnv.mcx.dbhelper.managedocs.ManageCounterPartyDbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
 * 
 * RTM Reference : 3.3.17.7, 3.3.18.7 Actor wants to manually add a counterparty
 * name who does not exist in the MCA Xpress as member firm
 * 
 * RTM Reference : 3.3.17.8, 3.3.18.8 Actor wants to rename a manually added
 * counterparty name RTM Reference : 3.3.17.31, 3.3.18.19 Only a user with
 * read/write access should be able to add, rename, delete a counterparty RTM
 * Reference : 3.3.18.29 Only one new manual upload counterparty should be
 * re-named at a time
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
 * @date Sep 10, 2007
 * @version 1.0
 * 
 *  
 */

public class DPMXDODODAO extends MCXCommonDB
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXDODODAO.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest,
     *      com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException
    {
        
        log.info("In DPMXDODODAO to add edit counterparty");
        SPName = "DPMXDODO";
      
        ManageCounterPartyDbResponse dbResponse = (ManageCounterPartyDbResponse) response;
        ManageCounterPartyDbRequest dbRequest = (ManageCounterPartyDbRequest) request;
        
        ManageDocsTransactionBean transactionBean = new ManageDocsTransactionBean();
        
        transactionBean = (ManageDocsTransactionBean)dbRequest.getTransaction();

        StringBuffer sb = new StringBuffer();
        sb.append("{ call ");
        sb.append(QUAL);
        sb.append(SPName);
        sb.append("( ?, ?, ?, ? ,?, ?, ?, ?, ?) }");

        

        cstmt = con.prepareCall(sb.toString());
        cstmt.registerOutParameter(1, java.sql.Types.CHAR);
        cstmt.registerOutParameter(2, java.sql.Types.CHAR);
        cstmt.registerOutParameter(3, java.sql.Types.CHAR);
        
        
        cstmt.setString(4, transactionBean.getSelectedDealerClient());
        cstmt.setString(5,transactionBean.getDealerClientName().trim());
        cstmt.setString(6,transactionBean.getAddRenameFlag());
        
        cstmt.setString(7, dbRequest.getLoginDealerClientId());
        cstmt.setString(8, dbRequest.getUpdatedUserId());
        cstmt.registerOutParameter(9, java.sql.Types.CHAR);

        rs = cstmt.executeQuery();

        sqlca = new SQLCA(cstmt.getString(1));
        sSpErrArea = cstmt.getString(2).trim();
        dbResponse.setSpReturnCode(cstmt.getString(3).trim());
        dbResponse.setSpResponseMessage(sSpErrArea);  
        if (sqlca.getSqlCode() == 0 && dbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
        {
            String cmpny_Id = (String) cstmt.getString(9);
            dbResponse.setCmpny_Id(cmpny_Id);
            //transactionBean.setCounterPartyId(cmpny_Id);
        }

        

        log.info("Returning from DPMXDODODAO");

    }

}
