package com.dtcc.dnv.mcx.db.mca;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.dtcc.dnv.mcx.beans.RegionBean;
import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.mca.RegionDbResponse;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
 * This class is used as a DAO to fetch regions for which ISDA templates have been published for a
 * Product, Sub-Product
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

public class DPMXMREGDAO extends MCXCommonDB {

	private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXMREGDAO.class.getName());
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void execute(IDbRequest dbRequest, IDbResponse dbResponse)
			throws DBException, SQLException {
		SPName = "DPMXMREG";
		TemplateDbRequest templateDbRequest = (TemplateDbRequest) dbRequest;
		RegionDbResponse rgnDbResponse = (RegionDbResponse) dbResponse;
		TemplateBean templateBean = (TemplateBean) templateDbRequest
				.getTransaction();
		RegionBean regionBean = null;
		String prevRegCd = null;
		List regnList = null;
		boolean tmpltPrsntFlg = false;

		cstmt = con.prepareCall("{ call " + QUAL + "DPMXMREG (?,?,?,?,?) }");

		cstmt.registerOutParameter(1, Types.CHAR);
		cstmt.registerOutParameter(2, Types.CHAR);
		cstmt.registerOutParameter(3, Types.CHAR);		
		
		cstmt.setString(4, templateBean.getDerPrdCd());
		cstmt.setString(5, templateBean.getDerSubPrdCd());		

		rs = cstmt.executeQuery();

		sqlca = new SQLCA(cstmt.getString(1));
		sSpErrArea = (String) cstmt.getObject(2);
		rgnDbResponse.setSpReturnCode(cstmt.getString(3).trim());
		rgnDbResponse.setSpResponseMessage(sSpErrArea);

        if (sqlca.getSqlCode() == 0 && rgnDbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
        {        	
        	regnList = new ArrayList();

			while (rs != null && rs.next()) {					
					regionBean = new RegionBean();
					regionBean.setRegionCd(rs.getString(1).trim());
					regionBean.setRegionNm(rs.getString(2).trim());
					regionBean.setTmpltPresent(true);
					regnList.add(regionBean);
			}
			rgnDbResponse.setRgnList(regnList);
		}
	}
}
