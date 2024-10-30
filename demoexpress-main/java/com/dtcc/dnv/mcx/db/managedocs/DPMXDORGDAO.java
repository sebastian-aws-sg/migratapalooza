package com.dtcc.dnv.mcx.db.managedocs;

import java.sql.SQLException;

import com.dtcc.dnv.mcx.beans.ManageDocsTransactionBean;
import com.dtcc.dnv.mcx.beans.TemplateDocsBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbRequest;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
 * DAO to get the dealerClients names
 * 
 * RTM Reference : 3.3.17.34,3.3.18.34 It should be possible for a user to 
 * 		differentiate between a manually added/renamed counterparty and the MCA 
 * 		Xpress registered member firm
 * 
 * 
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
public class DPMXDORGDAO extends MCXCommonDB
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXDORGDAO.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest,
     *      com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException
    {
        log.info("In DPMXDORGDAO to fetch list of dealers");
        SPName = "DPMXDORG";

        DealerClientListDbResponse dbResponse = (DealerClientListDbResponse) response;
        DealerClientListDbRequest dbRequest = (DealerClientListDbRequest) request;
        
        ManageDocsTransactionBean transactionBean = new ManageDocsTransactionBean();
        
        transactionBean = (ManageDocsTransactionBean)dbRequest.getTransaction();
        
        if(transactionBean != null){
            
            StringBuffer sb = new StringBuffer();
            sb.append("{ call ");
            sb.append(QUAL);
            sb.append(SPName);
            sb.append("( ?, ?, ?, ?, ? ) }");
            

            cstmt = con.prepareCall(sb.toString());
            cstmt.registerOutParameter(1, java.sql.Types.CHAR);
            cstmt.registerOutParameter(2, java.sql.Types.CHAR);
            cstmt.registerOutParameter(3, java.sql.Types.CHAR);
            cstmt.setString(4, dbRequest.getCmpnyId());
            cstmt.setString(5, transactionBean.getDeleteCmpnyId());
            
            rs = cstmt.executeQuery();

            sqlca = new SQLCA(cstmt.getString(1));
            sSpErrArea = cstmt.getString(2).trim();
            dbResponse.setSpReturnCode(cstmt.getString(3).trim());
            dbResponse.setSpResponseMessage(sSpErrArea); 
            
            if (sqlca.getSqlCode() == 0 && dbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
            {
                dbResponse = getDealerClientList(dbResponse);
            }
            
        }

        
        log.info("Returning from DPMXDORGDAO");
    }

    /**
     * This method is used to get the list of DealerClient names from the result
     * set
     * 
     * @param dbResponse
     * @return dbResponse
     */
    private DealerClientListDbResponse getDealerClientList(DealerClientListDbResponse dbResponse) throws SQLException
    {
        log.info("In getDealerClientList function to store the values in bean");
        while (rs != null && rs.next())
        {
            TemplateDocsBean dealerClientList = new TemplateDocsBean();

            dealerClientList.setOrgCltCd(rs.getString(1));
            dealerClientList.setOrgCltNm(rs.getString(2).trim());
            dealerClientList.setMcaRegisteredIndicator(rs.getString(3));
            dealerClientList.setDocumentInd(rs.getString(4));
            dbResponse.addDealerClientList(dealerClientList);
        }
        return dbResponse;
    }

}
