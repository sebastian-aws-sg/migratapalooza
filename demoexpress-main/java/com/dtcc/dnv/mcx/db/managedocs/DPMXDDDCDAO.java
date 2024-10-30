package com.dtcc.dnv.mcx.db.managedocs;

import java.sql.SQLException;
import java.util.StringTokenizer;

import com.dtcc.dnv.mcx.beans.ManageDocsTransactionBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbRequest;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbResponse;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
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
public class DPMXDDDCDAO extends MCXCommonDB
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXDDDCDAO.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest,
     *      com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException
    {
        log.info("In DPMXDDDCDAO to delete documents");
        
        SPName = "DPMXDDDC";
        DealerClientListDbResponse dbResponse = (DealerClientListDbResponse) response;
        DealerClientListDbRequest dbRequest = (DealerClientListDbRequest) request;
        
        ManageDocsTransactionBean manageDocsTransactionBean = (ManageDocsTransactionBean)dbRequest.getTransaction();
        String selectedDocs[] = manageDocsTransactionBean.getSelectedDocuments();
        
        StringTokenizer st = null;
        String dealerId = "";
        String docId = "";
        String templateId = "";
        
        if (selectedDocs != null)
        {
            for (int i=0; i<selectedDocs.length; i++)
            {
                st = new StringTokenizer(selectedDocs[i], "-");
                dealerId = st.nextToken();
                docId = st.nextToken();
                templateId = st.nextToken();
                
                StringBuffer sb = new StringBuffer();
                sb.append("{ call ");
                sb.append(QUAL);
                sb.append(SPName);
                sb.append("( ?, ?, ?, ?, ?, ?, ? ) }");
                
                cstmt = con.prepareCall(sb.toString());
                cstmt.registerOutParameter(1, java.sql.Types.CHAR);
                cstmt.registerOutParameter(2, java.sql.Types.CHAR);
                cstmt.registerOutParameter(3, java.sql.Types.CHAR);
                cstmt.setString(4, dealerId);
                cstmt.setString(5, templateId);
                cstmt.setString(6, docId);
                cstmt.setString(7, dbRequest.getUserId()); 
                
                rs = cstmt.executeQuery();

                sqlca = new SQLCA(cstmt.getString(1));
                sSpErrArea = cstmt.getString(2).trim();
                dbResponse.setSpReturnCode(cstmt.getString(3).trim());
                dbResponse.setSpResponseMessage(sSpErrArea);  
                
            }
        }
        
        log.info("Returning from DPMXDDDCDAO");
    }    
}