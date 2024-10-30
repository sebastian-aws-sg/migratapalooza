package com.dtcc.dnv.mcx.db.admin;

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
 * This class is used as a DAO to SAVE the MCA Template
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
 * @date Sep 14, 2007
 * @version 1.0
 * 
 */

public  class DPMXMSAVDAO extends MCXCommonDB {

	private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXMSAVDAO.class.getName());
	
	/**
	 * DPMXMSAVDAO
	 */
	public DPMXMSAVDAO() {
		super();
		SPName = "DPMXMSAV";
	}

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void execute(IDbRequest dbRequest, IDbResponse dbResponse)
			throws DBException, SQLException {
		
		TemplateDbRequest templateDbRequest = (TemplateDbRequest) dbRequest;
		TemplateDbResponse templateDbResponse = (TemplateDbResponse) dbResponse;
		
		TemplateBean templateBean = (TemplateBean)templateDbRequest.getTransaction();
		cstmt = con.prepareCall("{ call " + QUAL + "DPMXMSAV ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) }");

		cstmt.registerOutParameter(1, Types.CHAR);
		cstmt.registerOutParameter(2, Types.CHAR);
		cstmt.registerOutParameter(3, Types.CHAR);
		cstmt.registerOutParameter(5, Types.CHAR);
		
		cstmt.setInt(4, templateBean.getTmpltId());
		cstmt.setInt(5, 0);
		cstmt.setString(6, templateBean.getTmpltNm());
		cstmt.setString(7, templateBean.getTmpltTyp()); // Template Type D - Dealer for testing only. It should be I for Admin
		cstmt.setString(8, templateDbRequest.getCmpnyId()); // Company ID
		cstmt.setString(9, templateDbRequest.getUserId()); // User ID
		cstmt.setString(10,templateDbRequest.getOpnInd()); // Save Indicator

		rs = cstmt.executeQuery();

		sqlca = new SQLCA(cstmt.getString(1));
		sSpErrArea = (String) cstmt.getObject(2);
		templateDbResponse.setSpReturnCode(cstmt.getString(3));
		templateDbResponse.setSpResponseMessage(sSpErrArea);
		
		if (templateDbResponse.getSpReturnCode() != null && 
				templateDbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00)) 
		{
			templateBean.setTmpltId(cstmt.getInt(5));
		}
		
		templateDbResponse.setTransaction(templateBean);
		
	}
}
	

