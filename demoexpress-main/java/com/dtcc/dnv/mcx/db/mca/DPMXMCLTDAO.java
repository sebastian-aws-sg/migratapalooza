package com.dtcc.dnv.mcx.db.mca;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.mca.CounterpartyDbResponse;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
 * This class is used as a DAO to fetch list of categories in a given template
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
 */

public class DPMXMCLTDAO extends MCXCommonDB {	

	/**
	 * DPMXMCGYDAO
	 */
	public void execute(IDbRequest dbRequest, IDbResponse dbResponse)
			throws DBException, SQLException {
		
		SPName = "DPMXMCLT";
		CounterpartyDbResponse enrollCpDbResponse = (CounterpartyDbResponse) dbResponse;

		Map enrollCpMap = null;
		String cpId = null;
		String cpNm = null;		

		TemplateDbRequest templateDbRequest = (TemplateDbRequest) dbRequest;
		TemplateBean templateBean = (TemplateBean) templateDbRequest
				.getTransaction();

		cstmt = con.prepareCall("{ call " + QUAL + "DPMXMCLT (?,?,?,?,?) }");

		cstmt.registerOutParameter(1, Types.CHAR);
		cstmt.registerOutParameter(2, Types.CHAR);
		cstmt.registerOutParameter(3, Types.CHAR);

		cstmt.setInt(4, templateBean.getTmpltId());
		cstmt.setString(5, templateDbRequest.getCmpnyId());

		rs = cstmt.executeQuery();

		sqlca = new SQLCA(cstmt.getString(1));
		sSpErrArea = (String) cstmt.getObject(2);
		enrollCpDbResponse.setSpReturnCode(cstmt.getString(3).trim());
		enrollCpDbResponse.setSpResponseMessage(sSpErrArea);

        if (sqlca.getSqlCode() == 0 && enrollCpDbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
        {
			enrollCpMap = new HashMap();

			while (rs != null && rs.next()) {
				cpId = rs.getString(1).trim();
				cpNm = rs.getString(2).trim();
				enrollCpMap.put(cpId, cpNm);
			}
			enrollCpDbResponse.setEnrollCpMap(enrollCpMap);

		}
	}
}
