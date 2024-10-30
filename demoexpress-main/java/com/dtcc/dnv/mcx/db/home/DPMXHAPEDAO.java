package com.dtcc.dnv.mcx.db.home;

import java.sql.SQLException;

import com.dtcc.dnv.mcx.beans.TemplateDocsBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.home.PendingEnrollApprovalDbRequest;
import com.dtcc.dnv.mcx.dbhelper.home.PendingEnrollApprovalDbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
 * This class is used as a dao to retrieve the details needed to load the
 * Pending Enrollments Approval and Approved Firms screen. This class is used to
 * call the SP which will return those details 
 * RTM Reference : 3.3.11.1 The actor views the pending Enrolment approval tab for 
 * the respective MCA types against the clients awaiting approval,
 * approves a client and all the MCAs requested for by him 
 * RTM Reference : 3.3.11.2 Actor selects to approve only one or more but 
 * not all of the MCA Enrolment requests for the client RTM
 * Reference : 3.3.11.7 A Dealer should only be able to approve or deny
 * Enrolment of a Client that has initiated firm Enrolment 
 * RTM Reference :3.3.11.12 Once a Client firm is approved with at least one associated MCA(s),
 * that Client should never be removed from the Dealer’s ‘Approved Firms’ tab
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
 * @author Vigneshwari R
 * @date Sep 7, 2007
 * @version 1.0
 * 
 *  
 */
public class DPMXHAPEDAO extends MCXCommonDB
{

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest,
     *      com.dtcc.dnv.otc.common.layers.IDbResponse) This method will return
     *      the values needed for the pending enrollement approval screen.
     */
    public void execute(IDbRequest request, IDbResponse response) throws DBException, SQLException
    {
        SPName = "DPMXHAPE";
        PendingEnrollApprovalDbResponse dbResponse = (PendingEnrollApprovalDbResponse) response;
        PendingEnrollApprovalDbRequest dbRequest = (PendingEnrollApprovalDbRequest) request;
        String userId = dbRequest.getUserCode();
        String companyId = dbRequest.getDealerCode();
        StringBuffer sb = new StringBuffer();
        sb.append("{ call ");
        sb.append(QUAL);
        sb.append(SPName);
        sb.append("( ?, ?, ?, ?,? ) }");

        cstmt = con.prepareCall(sb.toString());
        cstmt.registerOutParameter(1, java.sql.Types.CHAR);
        cstmt.registerOutParameter(2, java.sql.Types.CHAR);
        cstmt.registerOutParameter(3, java.sql.Types.CHAR);
        cstmt.setString(4, dbRequest.getDealerCode());
        cstmt.setString(5, dbRequest.getSelectedTab());

        rs = cstmt.executeQuery();

        sqlca = new SQLCA(cstmt.getString(1));
        sSpErrArea = (String) cstmt.getObject(2);
        String thisreturncode = (String) cstmt.getObject(3);
        dbResponse.setSpReturnCode(thisreturncode);
        dbResponse.setSpResponseMessage(sSpErrArea);
        if (sqlca.getSqlCode() == 0 && dbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
        {
            getPendingEnrollApprovalList(dbResponse , userId, companyId);
            dbResponse.setStatus(MCXConstants.SUCCESS_FORWARD);
        }
    }

    private void getPendingEnrollApprovalList(PendingEnrollApprovalDbResponse dbResponse ,String userId, String companyId) throws SQLException
    {

        while (rs != null && rs.next())
        {
            TemplateDocsBean templateBean = new TemplateDocsBean();
            templateBean.setCurrentCompanyId(companyId);
            templateBean.setCurrentUserId(userId);
            templateBean.setTmpltId(rs.getInt(1));
            templateBean.setTmpltNm(rs.getString(2).trim());
            templateBean.setOrgDlrCd(rs.getString(3).trim());
            templateBean.setOrgDlrNm(rs.getString(4).trim());
            templateBean.setModifiedTime(rs.getTimestamp(5));
            templateBean.setRowUpdtName(rs.getString(6).trim());
            templateBean.setTmpltPubDt(rs.getString(7).trim());
            templateBean.setMcaStatusCd(rs.getString(8).trim());
            templateBean.setRowUpdtId(rs.getString(9).trim());
            templateBean.setLockByUsrId(rs.getString(10).trim());
            templateBean.setLockCmpnyId(rs.getString(11).trim());
            templateBean.setLockByUsrNm(rs.getString(12).trim());
            templateBean.setLockInd(rs.getString(13).trim());
            templateBean.setTmpltLngNm(rs.getString(14).trim());

            dbResponse.addPendingEnrollApprovalList(templateBean);

        }
    }

}
