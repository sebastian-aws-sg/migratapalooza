package com.dtcc.dnv.mcx.db.managedocs;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.dtcc.dnv.mcx.beans.ManageDocsTransactionBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbRequest;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.mcx.util.MessageResources;
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
public class DPMXDDLDDAO extends MCXCommonDB
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXDDLDDAO.class.getName());

    /**
     * DPMXDDLDDAO
     *
     */
    public DPMXDDLDDAO()
    {
        super();
        this.poolName =  MessageResources.getMessage("common.db2.pool");
        SPName = "DPMXDDLD";
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest,
     *      com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException
    {
        StringTokenizer st = null;
        String docName = "";
        String docTypeCode = "";
        byte[] docBlob = null;
        String strDocId = "";
        int docId = 0;
        
        String counterPartyId = "";
        
        DealerClientListDbResponse dbResponse = (DealerClientListDbResponse) response;
        DealerClientListDbRequest dbRequest = (DealerClientListDbRequest) request;
        ManageDocsTransactionBean manageDocsTransactionBean = null;
        
        if(dbRequest.getTransaction() != null)
        	manageDocsTransactionBean = (ManageDocsTransactionBean)dbRequest.getTransaction();
        
        if(manageDocsTransactionBean !=null){
            strDocId = manageDocsTransactionBean.getDocId();
            if (strDocId != null && strDocId.trim().length() >0)
            {
            	docId = Integer.parseInt(strDocId);
            }
            counterPartyId = manageDocsTransactionBean.getCounterPartyId();
        }
        
        

            StringBuffer sb = new StringBuffer();
            sb.append("{ call ");
            sb.append(QUAL);
            sb.append(SPName);
            sb.append("( ?, ?, ?, ?, ?, ? ) }");
            
            cstmt = con.prepareCall(sb.toString());
            cstmt.registerOutParameter(1, java.sql.Types.CHAR);
            cstmt.registerOutParameter(2, java.sql.Types.CHAR);
            cstmt.registerOutParameter(3, java.sql.Types.CHAR);
            cstmt.setInt(4, docId);
            cstmt.setString(5, counterPartyId);
            cstmt.setString(6, dbRequest.getManageDocs());
            
            //rs = cstmt.executeQuery();
            log.info("Before Execute query");
            boolean flag = cstmt.execute();
            log.info("After Execute query success"+flag);
            if(flag){
                rs = cstmt.getResultSet();
            }

            sqlca = new SQLCA(cstmt.getString(1));
            sSpErrArea = cstmt.getString(2).trim();
            dbResponse.setSpReturnCode(cstmt.getString(3).trim());
            dbResponse.setSpResponseMessage(sSpErrArea);  
            
            if (sqlca.getSqlCode() == 0 && dbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
            {
                
            	Map images = new HashMap();
                while (rs != null && rs.next())
                {
                    docName = rs.getString(2);
                    docTypeCode = rs.getString(3);
                    docBlob = rs.getBytes(4);                
                    log.info("Document " + docName + "; Size = " + docBlob.length + "bytes");
                    images.put(rs.getString(1).trim(), docBlob);
                }
                dbResponse.setDocName(docName);
                dbResponse.setDocFile(docBlob);
                dbResponse.setImages(images);
            }  
                      
    }
}