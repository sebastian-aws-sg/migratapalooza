package com.dtcc.dnv.mcx.db.mca;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

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
 * This class is used as a DAO to retrive the List of Templates associated with a
 * Product, Sub-Product, Region, User
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
 * 
 *  
 */

public class DPMXMALLDAO extends MCXCommonDB {
	
	private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXMALLDAO.class.getName());
	

	/**
	 * DPMXMALLDAO
	 */
	public DPMXMALLDAO() {
		super();
		SPName = "DPMXMALL";
	}

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void execute(IDbRequest dbRequest, IDbResponse dbResponse)
			throws DBException, SQLException {
		
		TemplateDbRequest templateDbRequest = (TemplateDbRequest) dbRequest;
		TemplateDbResponse templateDbResponse = (TemplateDbResponse) dbResponse;
		TemplateBean templateBean = (TemplateBean) templateDbRequest
				.getTransaction();
		List tmpltList = null;

		cstmt = con.prepareCall("{ call " + QUAL + "DPMXMALL (?,?,?,?,?,?,?,?) }");

		cstmt.registerOutParameter(1, Types.CHAR);
		cstmt.registerOutParameter(2, Types.CHAR);
		cstmt.registerOutParameter(3, Types.CHAR);

		cstmt.setString(4, templateBean.getDerPrdCd());
		cstmt.setString(5, templateBean.getDerSubPrdCd()); 
		cstmt.setString(6, templateDbRequest.getRgnCd());
		cstmt.setString(7, templateDbRequest.getCmpnyId());		
		cstmt.setString(8, templateDbRequest.getTmpltTypInd());		

		rs = cstmt.executeQuery();

		sqlca = new SQLCA(cstmt.getString(1));
		sSpErrArea = (String) cstmt.getObject(2);
		templateDbResponse.setSpReturnCode(cstmt.getString(3).trim());
		templateDbResponse.setSpResponseMessage(sSpErrArea);

        if (sqlca.getSqlCode() == 0 && templateDbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
        {
			tmpltList = new ArrayList();

			while (rs != null && rs.next()) 
			{
				TemplateBean tmpltBean = new TemplateBean();
				tmpltBean.setTmpltId(rs.getInt(1));
				tmpltBean.setTmpltNm(rs.getString(2).trim());
				
				tmpltList.add(tmpltBean);
			}
			templateDbResponse.setTmpltList(tmpltList);	
		}
	}	
}
