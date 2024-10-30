package com.dtcc.dnv.mcx.db.mca;

import java.sql.SQLException;
import java.sql.Types;

import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
 * This class is used as a DAO to fetch the MCA Grid
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
 * @author Narahari A
 * @date Sep 3, 2007
 * @version 1.0
 */

public class DPMXMSTGDAO extends MCXCommonDB {

	private static MessageLogger log = MessageLogger.getMessageLogger(DPMXMSTGDAO.class.getName());
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void execute(IDbRequest dbRequest, IDbResponse dbResponse)
			throws DBException, SQLException {
		
		SPName = "DPMXMSTG";

		TemplateDbRequest templateDbRequest = (TemplateDbRequest) dbRequest;
		TemplateDbResponse templateDbResponse = (TemplateDbResponse) dbResponse;
		TemplateBean templateBean = (TemplateBean)templateDbRequest.getTransaction() ;
		cstmt = con.prepareCall("{ call " + QUAL + "DPMXMSTG (?,?,?,?,?,?,?) }");

		cstmt.registerOutParameter(1, Types.CHAR);
		cstmt.registerOutParameter(2, Types.CHAR);
		cstmt.registerOutParameter(3, Types.CHAR);

		cstmt.setInt(4, templateBean.getTmpltId()); // Template Id
		cstmt.setString(5, templateDbRequest.getCmpnyId()); // Organisation Id
		cstmt.setString(6, templateDbRequest.getUserId()); // User Id		
		cstmt.setString(7, templateDbRequest.getDfltFlg()); // Default Flag
				
		rs = cstmt.executeQuery();

		sqlca = new SQLCA(cstmt.getString(1));
		sSpErrArea = (String) cstmt.getObject(2);
		templateDbResponse.setSpReturnCode(cstmt.getString(3));
		templateDbResponse.setSpResponseMessage(sSpErrArea);
		
        if (sqlca.getSqlCode() == 0 && templateDbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
        {
			while (rs != null && rs.next()) {
				
				templateBean.setTmpltId(rs.getInt(1));
				templateBean.setTmpltNm(rs.getString(2).trim());
				templateBean.setTmpltShrtNm(rs.getString(3).trim());
				templateBean.setTmpltTyp(rs.getString(4).trim());				
				templateBean.setOrgDlrCd(rs.getString(5).trim());
				templateBean.setOrgCltCd(rs.getString(6).trim());	
				templateBean.setDlrStCd(rs.getString(7).trim());
				templateBean.setCltStCd(rs.getString(8).trim());				
				templateBean.setDerPrdCd(rs.getString(9).trim());
				templateBean.setDerPrdNm(rs.getString(10).trim());
				templateBean.setDerSubPrdCd(rs.getString(11).trim());
				templateBean.setDerSubPrdNm(rs.getString(12).trim());
				templateBean.setRgnCd(rs.getString(13).trim());
				templateBean.setRgnNm(rs.getString(14).trim());
				templateBean.setMcaStatusCd(rs.getString(15).trim());	
				templateBean.setLockSt(rs.getString(16).trim());
				templateBean.setLockCmpnyId(rs.getString(17).trim());
				templateBean.setLockByUsrId(rs.getString(18).trim());
				templateBean.setLockByUsrNm(rs.getString(19).trim());
				templateBean.setLockUsrInd(rs.getString(20).trim());
				 
				if(rs.getString(21).trim().equalsIgnoreCase(MCXConstants.CONST_YES)){
					templateBean.setTempValAvl(true );
				}else {
					templateBean.setTempValAvl(false);
				}		
              	templateBean.setISDATmpltNm(rs.getString(22).trim());
              	
              	//Set Approval Enabled if the Requester Id is not same as current UserId
              	if(rs.getString(23).trim().equalsIgnoreCase(templateDbRequest.getUserId()))
              	{
              		templateBean.setApprEnabled(false);
              	}
              	else
              	{
              		templateBean.setApprEnabled(true);
              	}

              	//Set Eligible for Approval of the Template in Admin Module
              	if(rs.getString(24).trim().equalsIgnoreCase(MCXConstants.CONST_YES))
              	{
              		templateBean.setEligibleForAppr(true);
              	}
              	else
              	{
              		templateBean.setEligibleForAppr(false);
              	}
              	
              	
              	//Set Submission to CP enabled if there are no history records
              	if(rs.getString(25).trim().equalsIgnoreCase(MCXConstants.CONST_YES))
              	{
              		templateBean.setEligibleForSub(true);
              	}
              	else
              	{
              		templateBean.setEligibleForSub(false);
              	}
              	
              	//Set Execution enabled if there are no Pending Amendments
              	if(rs.getString(26).trim().equalsIgnoreCase(MCXConstants.CONST_YES))
              	{
              		templateBean.setEligibleForExc(true);
              	}
              	else
              	{
              		templateBean.setEligibleForExc(false);
              	}
              	if(! rs.getString(27).trim().equalsIgnoreCase(MCXConstants.CONST_YES))
              	{
              		templateBean.setPropValInsReq(true);
              	}
              	else
              	{
              		templateBean.setPropValInsReq(false);
              	}  
		templateBean.setOrgDlrNm(rs.getString(28).trim());
		templateBean.setOrgCltNm(rs.getString(29).trim());	              	
		      	if(rs.getString(30).trim().equalsIgnoreCase(MCXConstants.CONST_YES))
		      	{
		      		templateBean.setEligibleForReNeg(true);
		      	}
		      	else
		      	{
		      		templateBean.setEligibleForReNeg(false);
		      	} 		
			}
			templateDbResponse.setTransaction(templateBean);	
		}
	}
}