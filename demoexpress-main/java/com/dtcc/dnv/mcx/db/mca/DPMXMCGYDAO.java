package com.dtcc.dnv.mcx.db.mca;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.dtcc.dnv.mcx.beans.CategoryBean;
import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.mca.CategoryDbResponse;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
 * This class is used as a DAO to fetch list of categories  in 
 * a given template
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

public class DPMXMCGYDAO extends MCXCommonDB {
	
	private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXMCGYDAO.class.getName());

	
	/**
	 * DPMXMCGYDAO
	 */
	public DPMXMCGYDAO() {
		super();
		SPName = "DPMXMCGY";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest,
	 *      com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void execute(IDbRequest dbRequest, IDbResponse dbResponse)
			throws DBException, SQLException 
	{
		TemplateDbRequest templateDbRequest = (TemplateDbRequest) dbRequest;
		CategoryDbResponse categyDbResponse = (CategoryDbResponse) dbResponse;
			TemplateBean templateBean = (TemplateBean) templateDbRequest
					.getTransaction();
			List tmpltCatgyList = null;

		cstmt = con.prepareCall("{ call " + QUAL + "DPMXMCGY (?,?,?,?) }");

		cstmt.registerOutParameter(1, Types.CHAR);
		cstmt.registerOutParameter(2, Types.CHAR);
		cstmt.registerOutParameter(3, Types.CHAR);

		cstmt.setInt(4, templateBean.getTmpltId());

		rs = cstmt.executeQuery();

		sqlca = new SQLCA(cstmt.getString(1));
		sSpErrArea = (String) cstmt.getObject(2);
			categyDbResponse.setSpReturnCode(cstmt.getString(3).trim());
			categyDbResponse.setSpResponseMessage(sSpErrArea);

	        if (sqlca.getSqlCode() == 0 && categyDbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
	        {
			tmpltCatgyList = new ArrayList();

			while (rs != null && rs.next()) {
				CategoryBean catgyBean = new CategoryBean();
				if( !(templateBean.getTmpltTyp().equalsIgnoreCase(MCXConstants.ISDA_TEMPLATE_TYPE) &&  rs.getString(4).trim().equalsIgnoreCase(MCXConstants.PROPREITARY_CATEGORY_STATUS)) ){
					String cateId = rs.getString(1).trim() + "~" + rs.getInt(3);
					//Set the Category Id with Id~Sequence
					catgyBean.setCatgyId(cateId);
					catgyBean.setCatgyNm(rs.getString(2).trim());
					catgyBean.setCatgySqId(rs.getInt(3));
					tmpltCatgyList.add(catgyBean);
				}
			}
			categyDbResponse.setCatgyList(tmpltCatgyList);
		}
	}

}
