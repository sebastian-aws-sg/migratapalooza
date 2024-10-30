package com.dtcc.dnv.mcx.db.managedocs;


import java.sql.SQLException;

import com.dtcc.dnv.mcx.beans.ManageDocsTransactionBean;
import com.dtcc.dnv.mcx.beans.TemplateDocsBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.managedocs.SearchDbRequest;
import com.dtcc.dnv.mcx.dbhelper.managedocs.SearchDbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
 * DAO to get the document details from the database.
 * 
 *  RTM Reference : 3.3.16 Basic Flow (Except delete documents) Searching for
 * 		the documents based on the parameters Dealer/Client Id,Document type,
 * 		Document Name. Displays Dealer/ClientId MCA/File name,Document type, 
 * 		Execution date,CP Viewable,uploaded by user,uploaded on date that
 * 		satisfy the search criteria.
 * RTM Reference : 3.3.16.1 Actor wants to search for “Other documents” 
 * RTM Reference : 3.3.16.2 Actor wants to search for “All” documents against
 * 		 “All” counterparties 
 * RTM Reference : 3.3.16.3 Actor wants to search for “All” documents against
 * 		specific counterparty 
 *  
 * 
 * RTM Reference : 3.3.16.15 Execution Date should be available only in case of
 * 		Pre-Exec documents and it should be blank for “other documents” 
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
public class DPMXDGTDDAO extends MCXCommonDB
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXDGTDDAO.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest,
     *      com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException
    {
        
        log.info("In DPMXDGTDDAO to fetch the document library");
        SPName = "DPMXDGTD";
        SearchDbResponse dbResponse = (SearchDbResponse) response;
        SearchDbRequest dbRequest = (SearchDbRequest) request;
        
        ManageDocsTransactionBean transactionBean = new ManageDocsTransactionBean();
        transactionBean =(ManageDocsTransactionBean) dbRequest.getTransaction();
        
        if(transactionBean!=null){
            
            if(MCXConstants.ALL.equals(transactionBean.getSelectedDocumentType())){
                transactionBean.setSelectedDocumentType(" ");
            }
            if(MCXConstants.ALL.equals(transactionBean.getSelectedDealerClient())){
                transactionBean.setSelectedDealerClient(" ");
            }

            StringBuffer sb = new StringBuffer();
            sb.append("{ call ");
            sb.append(QUAL);
            sb.append(SPName);
            sb.append("( ?, ?, ?, ? ,?, ?, ? ) }");

            cstmt = con.prepareCall(sb.toString());
            cstmt.registerOutParameter(1, java.sql.Types.CHAR);
            cstmt.registerOutParameter(2, java.sql.Types.CHAR);
            cstmt.registerOutParameter(3, java.sql.Types.CHAR);
            cstmt.setString(4, dbRequest.getCompanyId());
            
            cstmt.setString(5, transactionBean.getSelectedDocumentType());
            cstmt.setString(6, transactionBean.getDocName());
            cstmt.setString(7, transactionBean.getSelectedDealerClient());

            rs = cstmt.executeQuery();

            sqlca = new SQLCA(cstmt.getString(1));
            sSpErrArea = cstmt.getString(2).trim();
            dbResponse.setSpReturnCode(cstmt.getString(3).trim());
            dbResponse.setSpResponseMessage(sSpErrArea);  
            if (sqlca.getSqlCode() == 0 && dbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
            {
                dbResponse = getDealerClientDetails(dbResponse);
            }
            
        }
        
        
    }

    /**
     * This method is used to get the list of DealerClient names from the result
     * set
     * 
     * @param dbResponse
     * @return dbResponse
     */
    private SearchDbResponse getDealerClientDetails(SearchDbResponse dbResponse) throws SQLException
    {
        String YES = "Yes";
        String NO = "No";
        String Y = "Y";

        

        while (rs != null && rs.next())
        {
            TemplateDocsBean dealerClientList = new TemplateDocsBean();

            dealerClientList.setOrgDlrCd(rs.getString(1));
            dealerClientList.setOrgDlrNm(rs.getString(2).trim());
            dealerClientList.setMcaRegisteredIndicator(rs.getString(3));

            dealerClientList.setTmpltId(rs.getInt(4));
            dealerClientList.setTmpltNm(rs.getString(5).trim());
            dealerClientList.setTmpltShrtNm(rs.getString(6).trim());

            dealerClientList.setMcaAgreementDate(rs.getTimestamp(7));

            if (Y.equals(rs.getString(8)))
            {
                dealerClientList.setCpViewable(YES);
            } else
            {
                dealerClientList.setCpViewable(NO);
            }

            dealerClientList.setRowUpdtId(rs.getString(9));
            dealerClientList.setRowUpdtName(rs.getString(10).trim());
            dealerClientList.setModifiedTime(rs.getTimestamp(11));

            dealerClientList.setDocId(rs.getString(12));
            dealerClientList.setDocName(rs.getString(13).trim());
            
            if (MCXConstants.PREEXISTINGTAB.equals(rs.getString(14)))
            {
                dealerClientList.setTmpltTyp(MCXConstants.PREEXISTINGDISP);
            } else
            {
                dealerClientList.setTmpltTyp(MCXConstants.OTHERS);
                dealerClientList.setMcaAgreementDate(null);
            }

            dbResponse.addDealerClientDetails(dealerClientList);

        }

        return dbResponse;
    }

}
