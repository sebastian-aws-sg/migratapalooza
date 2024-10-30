package com.dtcc.dnv.mcx.db.mca;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.dtcc.dnv.mcx.beans.CommentBean;
import com.dtcc.dnv.mcx.beans.TermBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbResponse;
import com.dtcc.dnv.mcx.formatters.FormatterUtils;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
 * 
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
 * @author VVaradac
 *
 */
public class DPMXMCMTDAO extends MCXCommonDB {
	private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXMCMTDAO.class.getName());
	//private static Logger log = Logger.getLogger(DPMXMCMTDAO.class);
	
	public DPMXMCMTDAO()
	{
		super();
		SPName = "DPMXMCMT";
	}

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void execute(IDbRequest request, IDbResponse response)
			throws DBException, SQLException {

		ModifyTermDbRequest termDbRequest = (ModifyTermDbRequest) request;
		ModifyTermDbResponse termDbResponse = (ModifyTermDbResponse) response;
		TermBean termBean = (TermBean) termDbRequest
				.getTransaction();
		
		cstmt = con.prepareCall("{ call " + QUAL + "DPMXMCMT (?,?,?,?,?,?) }");

		cstmt.registerOutParameter(1, Types.CHAR);
		cstmt.registerOutParameter(2, Types.CHAR);
		cstmt.registerOutParameter(3, Types.CHAR);
		
		cstmt.setInt(4, termBean.getTermValId());
		cstmt.setString(5, MCXConstants.VIEW_COMMENT);
		cstmt.setString(6, termDbRequest.getUserInd());				  
		
		rs = cstmt.executeQuery();
		
		sqlca = new SQLCA(cstmt.getString(1));
		sSpErrArea = (String) cstmt.getObject(2);
		termDbResponse.setSpReturnCode(cstmt.getString(3));
		termDbResponse.setSpResponseMessage(sSpErrArea);
		
        if (sqlca.getSqlCode() == 0 && termDbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
		{
			TermBean trmBean = new TermBean();
			CommentBean cmntBean = new CommentBean();
			List cmntList = new ArrayList();
			if(rs != null && rs.next())
			{
				trmBean.setCatgyId(rs.getString(1));
				trmBean.setCatgySqId(rs.getInt(2));
				trmBean.setCatgyNm(rs.getString(3));
				trmBean.setTermId(rs.getString(4));
				trmBean.setTermSqId(rs.getInt(5));
				trmBean.setTermNm(rs.getString(6));
				cmntBean.setCmntId(rs.getInt(7));
				if(cmntBean.getCmntId() !=0)
				{
					cmntBean.setRowUpdtName(rs.getString(9));
					cmntBean.setRowUpdtDt(FormatterUtils.formatOutputDateTimeStamp(rs.getTimestamp(10)));
					cmntBean.setCmntTxt(rs.getString(11));
					cmntList.add(cmntBean);
				}
				while(rs.next())
				{
					if(cmntBean.getCmntId() != rs.getInt(7))
					{
						cmntBean = new CommentBean();
						cmntBean.setCmntId(rs.getInt(7));
						cmntBean.setRowUpdtName(rs.getString(9));  //Updated User Name
						cmntBean.setRowUpdtDt(FormatterUtils.formatOutputDateTimeStamp(rs.getTimestamp(10)));
						cmntBean.setCmntTxt(rs.getString(11));
						cmntList.add(cmntBean);
					}
				}
			}
			trmBean.setCmntList(cmntList);
			trmBean.setTermValId(termBean.getTermValId());
			
			termDbResponse.setTransaction(trmBean);
		}
	}

}
