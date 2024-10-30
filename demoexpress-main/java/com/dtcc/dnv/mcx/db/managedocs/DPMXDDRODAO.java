package com.dtcc.dnv.mcx.db.managedocs;

import java.sql.SQLException;

import com.dtcc.dnv.mcx.beans.ManageDocsTransactionBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbRequest;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbResponse;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
 * 
 * This class is used to delete the CPs and reassign the documents to another CP if specified.
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
 * @author Elango TR
 * @date Sep 6, 2007
 * @version 1.0
 * 
 *  
 */
public class DPMXDDRODAO extends MCXCommonDB
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXDDRODAO.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest,
     *      com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException
    {
        log.info("In DPMXDDRODAO to delete/reassign counterparty");

        SPName = "DPMXDDRO";
        String reassign = "";
        DealerClientListDbResponse dbResponse = (DealerClientListDbResponse) response;
        DealerClientListDbRequest dbRequest = (DealerClientListDbRequest) request;
        
        ManageDocsTransactionBean transactionBean = new ManageDocsTransactionBean();
        
        transactionBean = (ManageDocsTransactionBean)dbRequest.getTransaction();
        
        if(transactionBean !=null){
            
            
            StringBuffer sb = new StringBuffer();
            sb.append("{ call ");
            sb.append(QUAL);
            sb.append(SPName);
            sb.append("( ?, ?, ?, ?, ?, ?, ?, ?, ? ) }");
            
            String deleteCompanyId = transactionBean.getDeleteCmpnyId();
            String dupDoc = transactionBean.getDuplicateDocsCheck();
            
            if (transactionBean.getReassignedDealerClient() == null || MCXConstants.EMPTY_SPACE.equals(transactionBean.getReassignedDealerClient()))
            {
                reassign = MCXConstants.MANAGE_DOC_ORG_DELETE;
            } else {
                reassign = MCXConstants.MANAGE_DOC_ORG_REASSIGN;
            }
            //log.debug("reassign"+reassign);
            //log.debug("delcmpid"+deleteCompanyId);
            //log.debug("newcmpid"+transactionBean.getReassignedDealerClient());
           
            cstmt = con.prepareCall(sb.toString());
            cstmt.registerOutParameter(1, java.sql.Types.CHAR);
            cstmt.registerOutParameter(2, java.sql.Types.CHAR);
            cstmt.registerOutParameter(3, java.sql.Types.CHAR);
            cstmt.registerOutParameter(9, java.sql.Types.CHAR);
            cstmt.setString(4, deleteCompanyId);
            cstmt.setString(5, transactionBean.getReassignedDealerClient());
            cstmt.setString(6, dbRequest.getCmpnyId());
            cstmt.setString(7, reassign);  //D for delete, R for Reassign
            cstmt.setString(8, dbRequest.getUserId());
            cstmt.setString(9, dupDoc);   //I to check if duplicates are present while reassigning the documents. 
            							  //I also passed for deleting Counterpartys with out reassigning.
            							  //C to allow duplicates to transfer	
                        
            rs = cstmt.executeQuery();
            
            sqlca = new SQLCA(cstmt.getString(1));
            sSpErrArea = cstmt.getString(2).trim();
            dbResponse.setSpReturnCode(cstmt.getString(3).trim());
            dbResponse.setSpResponseMessage(sSpErrArea);  
            dbResponse.setDocumentInd(cstmt.getString(9));
            
            log.info("Returning from DPMXDDRODAO");
            
        }

       
    }    
}