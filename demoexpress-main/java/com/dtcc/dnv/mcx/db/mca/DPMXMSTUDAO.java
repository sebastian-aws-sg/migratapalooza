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
 * @author VVaradac
 */
public class DPMXMSTUDAO extends MCXCommonDB {
	
	private static MessageLogger log = MessageLogger.getMessageLogger(DPMXMSTUDAO.class.getName());	
	
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void execute(IDbRequest request, IDbResponse response)
			throws DBException, SQLException 
	{
		SPName = "DPMXMSTU";
		TemplateDbRequest tmpltDbRequest = (TemplateDbRequest) request;
		TemplateDbResponse tmpltDbResponse = (TemplateDbResponse) response;
		TemplateBean tmpltBean = (TemplateBean) tmpltDbRequest.getTransaction();	
		boolean isDealer = false;
		if(tmpltBean.getOrgDlrCd().equalsIgnoreCase(tmpltDbRequest.getCmpnyId()))
		{
			isDealer = true;
		}
		if(tmpltBean.getOrgDlrCd().equals(tmpltBean.getOrgCltCd()) && tmpltBean.getDlrStCd().equalsIgnoreCase("P"))
		{
			isDealer = true;
		}
		else if(tmpltBean.getOrgDlrCd().equals(tmpltBean.getOrgCltCd()) && !tmpltBean.getDlrStCd().equalsIgnoreCase("P"))
		{
			isDealer = false;
		}
		if(tmpltBean.getOrgDlrCd().equals(tmpltBean.getOrgCltCd()) 
				&& tmpltBean.getDlrStCd().equalsIgnoreCase("") 
				&& tmpltBean.getCltStCd().equalsIgnoreCase(""))
		{
			isDealer = true;
		}

		String execDt = MCXConstants.DEFAULT_DATETIMESTAMP;
		String opn = MCXConstants.EMPTY;

		cstmt = con.prepareCall("{ call " + QUAL + "DPMXMSTU (?,?,?,?,?,?,?,?,?,?,?,?,?) }");

		cstmt.registerOutParameter(1, Types.CHAR);
		cstmt.registerOutParameter(2, Types.CHAR);
		cstmt.registerOutParameter(3, Types.CHAR);
		cstmt.registerOutParameter(4, Types.INTEGER); 	// Template ID
		cstmt.registerOutParameter(5, Types.CHAR); 		//Template Name
		cstmt.registerOutParameter(6, Types.CHAR); 		// Template Type
		
			
		cstmt.setInt(4, tmpltBean.getTmpltId());
		cstmt.setString(5, tmpltBean.getTmpltNm());		
		cstmt.setString(6, tmpltBean.getTmpltTyp());
		if(!tmpltBean.getOrgDlrCd().equalsIgnoreCase(""))
			cstmt.setString(7, tmpltBean.getOrgDlrCd());
		else
		cstmt.setString(7, tmpltDbRequest.getCmpnyId());
		cstmt.setString(8, tmpltBean.getOrgCltCd());
		if(tmpltDbRequest.getOpnInd().equalsIgnoreCase(MCXConstants.APPLY))
		{
			cstmt.setString(9, MCXConstants.EMPTY);					//Dealer Status Code
			cstmt.setString(10, MCXConstants.EMPTY);					//Client Status Code
		}
		else if(tmpltDbRequest.getOpnInd().equalsIgnoreCase(MCXConstants.SUBMIT))
		{
			if(isDealer)
			{
				cstmt.setString(9, MCXConstants.MCA_STATUS_DENYFIRMS);					//Dealer Status Code
				cstmt.setString(10, MCXConstants.MCA_STATUS_PENDING);					//Client Status Code
			}
			else
			{
				cstmt.setString(9, MCXConstants.MCA_STATUS_PENDING);					//Dealer Status Code
				cstmt.setString(10, MCXConstants.MCA_STATUS_DENYFIRMS);					//Client Status Code
			}
		}
		else if(tmpltDbRequest.getOpnInd().equalsIgnoreCase(MCXConstants.EXECUTE))
		{
			if(isDealer)
			{
				cstmt.setString(9, MCXConstants.MCA_STATUS_APPROVEDFIRMS);					//Dealer Status Code
				cstmt.setString(10, MCXConstants.EMPTY);					//Client Status Code
				execDt = tmpltBean.getPublishDt();
				opn = MCXConstants.MCA_STATUS_EXECUTED;
			}
			else
			{
				cstmt.setString(9, MCXConstants.MCA_PENDING_STATUS);					//Dealer Status Code
				cstmt.setString(10, MCXConstants.MCA_STATUS_APPROVEDFIRMS);					//Client Status Code
			}
		}
		else if(tmpltDbRequest.getOpnInd().equalsIgnoreCase(MCXConstants.REEXECUTED_TEMPLATE_TYPE))
		{
			cstmt.setString(9, MCXConstants.MCA_PENDING_STATUS);		//Dealer Status Code
			cstmt.setString(10, MCXConstants.EMPTY);		//Client Status Code
			opn = MCXConstants.REEXECUTED_TEMPLATE_TYPE;
		}
		cstmt.setString(11,tmpltDbRequest.getUserId());
		cstmt.setString(12, execDt);   
		cstmt.setString(13, opn);
		
		cstmt.executeUpdate();

		sqlca = new SQLCA(cstmt.getString(1));
		sSpErrArea = (String) cstmt.getObject(2);
		tmpltDbResponse.setSpReturnCode(cstmt.getString(3));
		tmpltDbResponse.setSpResponseMessage(sSpErrArea);
		
        if (sqlca.getSqlCode() == 0 && tmpltDbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
		{			
			tmpltBean.setTmpltId(cstmt.getInt(4));      		//Template Id
			tmpltBean.setTmpltNm(cstmt.getString(5).trim());  	//Template Name
			tmpltBean.setTmpltTyp(cstmt.getString(6).trim());   //Template Type
			
			tmpltDbResponse.setTransaction(tmpltBean);
		}		
	}

}
