package com.dtcc.dnv.mcx.db.mca;

import java.sql.SQLException;
import java.sql.Types;

import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
 * 
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
 * @author VVaradac
 * 
 */
public  class DPMXMLCKDAO extends MCXCommonDB {
	
	private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXMLCKDAO.class.getName());
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void execute(IDbRequest request, IDbResponse response)
			throws DBException, SQLException 
	{				
		SPName = "DPMXMLCK";
		log.debug("1. Inside the DPMXMLCKDAO >>>");
		TemplateDbRequest temDbRequest = (TemplateDbRequest) request;
		TemplateDbResponse temDbResponse = (TemplateDbResponse) response;
		TemplateBean temBean = (TemplateBean) temDbRequest
				.getTransaction();

		cstmt = con.prepareCall("{ call " + QUAL + "DPMXMLCK (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");

		cstmt.registerOutParameter(1, Types.CHAR);
		cstmt.registerOutParameter(2, Types.CHAR);
		cstmt.registerOutParameter(3, Types.CHAR);
		cstmt.registerOutParameter(8, Types.CHAR);
		cstmt.registerOutParameter(9, Types.CHAR);
		cstmt.registerOutParameter(10, Types.CHAR);

		cstmt.setInt(4, temBean.getTmpltId());
		cstmt.setString(5, temDbRequest.getCmpnyId());  	
		cstmt.setString(6, temDbRequest.getUserId());		
		cstmt.setString(7, temDbRequest.getOpnInd());
		
		//log.debug("2. temBean.getTmpltId() in DPMXMLCKDAO is >>>" + temBean.getTmpltId());
		//log.debug("3. temDbRequest.getUserId() in DPMXMLCKDAO is >>>" + temDbRequest.getUserId());
		//log.debug("4. temDbRequest.getOpnInd() in DPMXMLCKDAO is >>>" + temDbRequest.getOpnInd());
		//log.debug("5. temDbRequest.getCmpnyId() in DPMXMLCKDAO is >>>" + temDbRequest.getCmpnyId());
		
		cstmt.executeUpdate();

		sqlca = new SQLCA(cstmt.getString(1));
		sSpErrArea = (String) cstmt.getObject(2);
		temDbResponse.setSpReturnCode(cstmt.getString(3));
		temDbResponse.setSpResponseMessage(sSpErrArea);
		
        if (sqlca.getSqlCode() == 0 && temDbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
		{
			temDbResponse.setTransaction(temBean);
		}			
        //log.debug("6. sqlca.getSqlCode() in DPMXMLCKDAO is >>>" + sqlca.getSqlCode());
        //log.debug("7. temDbResponse.getSpReturnCode() in DPMXMLCKDAO is >>>" + temDbResponse.getSpReturnCode());
	}
}
