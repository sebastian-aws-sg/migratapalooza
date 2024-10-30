/*
 * Created on Sep 3, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dtcc.dnv.mcx.db.home;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtcc.dnv.mcx.beans.TemplateDocsBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.home.DisplayMCAsDbRequest;
import com.dtcc.dnv.mcx.dbhelper.home.DisplayMCAsDbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
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
 * @author Nithya R
 * @date Sep 10, 2007
 * @version 1.0
 * 
 * 
 * This class calls the stored procedures for fetching the pending MCA and
 * executed MCA details.
 * 
 * 
 * RTM Reference : 
 * 3.3.6.1 To view the executed MCA from the executed MCA tab
 * and actor clicks the MCA type to view the detailed fields of the document
 * 3.3.6.15 Client users should only be able to view the records in executed
 * MCAs tab irrespective of their read/write privileges 
 * 3.3.14.1 The actor clicks the executed MCAs tab System would show the executed MCA records in
 * the executed MCA tab
 * 3.3.14.16 Pre-executed MCAs uploaded by the dealer
 * should be viewable only by the dealer users on the executed MCAs tab and not
 * by the client users in their executed MCAs tab
 * 3.3.5.13 Actor chooses to view the “Executed MCAs” tab
 *  
 */
public class DPMXHEXPDAO extends MCXCommonDB
{
	private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXHEXPDAO.class.getName());
  
    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest,
     *      com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException
    {
        log.info("inside execute method in DPMXHEXPDAO");
        SPName = "DPMXHEXP";        
        DisplayMCAsDbRequest dbRequest = (DisplayMCAsDbRequest) request;
        DisplayMCAsDbResponse dbResponse = (DisplayMCAsDbResponse) response;
        String userId = dbRequest.getUserID();
        String companyId = dbRequest.getCompanyID();
        String tabIndicator = dbRequest.getPendingExecutedIndicator();
        StringBuffer buffer = new StringBuffer();
        buffer.append("{ call ");
        buffer.append(QUAL);
        buffer.append(SPName);
        buffer.append("( ?, ?, ?, ?, ?, ? ) }");

        cstmt = con.prepareCall(buffer.toString());
        cstmt.registerOutParameter(1, java.sql.Types.CHAR);
        cstmt.registerOutParameter(2, java.sql.Types.CHAR);
        cstmt.registerOutParameter(3, java.sql.Types.CHAR);

        cstmt.setString(4, dbRequest.getCompanyID());
        cstmt.setString(5, dbRequest.getPendingExecutedIndicator());
        cstmt.setString(6, dbRequest.getUserID());

        rs = cstmt.executeQuery();

        sqlca = new SQLCA(cstmt.getString(1));
        sSpErrArea = (String) cstmt.getObject(2);
        String thisreturncode = (String) cstmt.getObject(3);
        dbResponse.setSpReturnCode(thisreturncode);
        dbResponse.setSpResponseMessage(sSpErrArea);        
        if (sqlca.getSqlCode() == 0 & dbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
        {
            getMCADetailsList(dbResponse,tabIndicator,userId,companyId);
            dbResponse.setPendingExecutedStatus(MCXConstants.SUCCESS_FORWARD);
        }

    }

    /**
     * This method gets the pending MCA or executed MCA details from the resultset.
     * 
     * @param dbResponse
     * @return
     * @throws SQLException
     */
    private void getMCADetailsList(DisplayMCAsDbResponse dbResponse, String tabIndicator, String userId, String companyId) throws SQLException
    {
        log.info("inside getMCADetailsList method in DAO");
        List dealerDetailsList = new ArrayList();
        while (rs != null && rs.next())
        {
            TemplateDocsBean templateBean = new TemplateDocsBean();
            templateBean.setCurrentCompanyId(companyId);
            templateBean.setCurrentUserId(userId);            
            templateBean.setOrgDlrCd(rs.getString(1).trim());
            templateBean.setOrgDlrNm(rs.getString(2).trim());
            templateBean.setTmpltId(rs.getInt(3));
            String mcaFileName = rs.getString(4).trim();           
            templateBean.setTmpltNm(mcaFileName);
            templateBean.setExecutedMcaFileName(mcaFileName);
            templateBean.setMcaTemplateName(mcaFileName);
            templateBean.setModifiedTime(rs.getTimestamp(5));
            templateBean.setRowUpdtName(rs.getString(6).trim());
            templateBean.setMcaStatusCd(rs.getString(7).trim());
            templateBean.setLockInd(rs.getString(8).trim());
            templateBean.setLockByUsrNm(rs.getString(9).trim());
            templateBean.setLockByUsrId(rs.getString(10).trim());
            templateBean.setLockCmpnyId(rs.getString(11).trim());
            templateBean.setTmpltTyp(rs.getString(12).trim());            
            templateBean.setMcaRegisteredIndicator(rs.getString(13).trim());
            templateBean.setDocId(rs.getString(14).trim());
            templateBean.setDocName(rs.getString(15).trim());
            if(MCXConstants.MCA_ADMIN_ACTION.equalsIgnoreCase(tabIndicator)){
                templateBean.setPostedDate(rs.getTimestamp(16));
                templateBean.setMcaAgreementDate(rs.getTimestamp(17));
            }   
            templateBean.setRowUpdtId(rs.getString(18));
            templateBean.setTmpltLngNm(rs.getString(19).trim());
            
            dealerDetailsList.add(templateBean);

        }
        dbResponse.setDealerDetailsList(dealerDetailsList);

    }

}
