package com.dtcc.dnv.mcx.db.admin;

import java.sql.SQLException;
import java.sql.Types;

import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbResponse;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
 * This class is used as a DAO to Approve/Reject the MCA Template
 * 
 * Copyright � 2003 The Depository Trust & Clearing Company. All rights
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
 * @date Sep 19, 2007
 * @version 1.0
 * 
 */

public  class DPMXAPUBDAO extends MCXCommonDB {

	private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXAPUBDAO.class.getName());
	
	/**
	 * DPMXAPUBDAO
	 */
	public DPMXAPUBDAO() {
		super();
		SPName = "DPMXAPUB";
	}

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void execute(IDbRequest dbRequest, IDbResponse dbResponse)
			throws DBException, SQLException {
		
		TemplateDbRequest templateDbRequest = (TemplateDbRequest) dbRequest;
		TemplateDbResponse templateDbResponse = (TemplateDbResponse) dbResponse;
		
		TemplateBean templateBean = (TemplateBean)templateDbRequest.getTransaction();

		cstmt = con.prepareCall("{ call " + QUAL + "DPMXAPUB (?, ?, ?, ?, ?, ?, ?) }");

		cstmt.registerOutParameter(1, Types.CHAR);
		cstmt.registerOutParameter(2, Types.CHAR);
		cstmt.registerOutParameter(3, Types.CHAR);
		cstmt.setInt(4, templateBean.getTmpltId());
		cstmt.setString(5, templateDbRequest.getUserId());
		cstmt.setString(6, templateBean.getAppRejStatus()); 
		cstmt.setString(7, templateBean.getPublishDt()); 

		cstmt.executeUpdate();

		sqlca = new SQLCA(cstmt.getString(1));
		sSpErrArea = (String) cstmt.getObject(2);
		templateDbResponse.setSpReturnCode(cstmt.getString(3));
		templateDbResponse.setSpResponseMessage(sSpErrArea);
		
	}
}