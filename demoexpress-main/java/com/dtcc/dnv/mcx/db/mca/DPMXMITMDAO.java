/*
 * Created on Sep 13, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.db.mca;

import java.sql.SQLException;
import java.sql.Types;

import com.dtcc.dnv.mcx.beans.TermBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
 * @author VVaradac
 *
 * To fetch the Term Values for the given Term ID
 */
public class DPMXMITMDAO extends MCXCommonDB {
	private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXMITMDAO.class.getName());
	

	
	public DPMXMITMDAO()
	{
		super();
		SPName = "DTMXMITM";
	}

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void execute(IDbRequest request, IDbResponse response)
			throws DBException, SQLException 
	{
	    log.debug("1. Inside the DPMXMITMDAO >>>");
	    
		ModifyTermDbRequest dbRequest = (ModifyTermDbRequest) request;
		ModifyTermDbResponse dbResponse = (ModifyTermDbResponse) response;
		
		TermBean termBean = (TermBean) dbRequest.getTransaction();
		TermBean tempBean = new TermBean();
		
		cstmt = con.prepareCall("{ call " + QUAL + "DPMXMITM (?,?,?,?,?,?,?) }");
		
		cstmt.registerOutParameter(1, Types.CHAR);
		cstmt.registerOutParameter(2, Types.CHAR);
		cstmt.registerOutParameter(3, Types.CHAR);
		
		cstmt.setLong(4, termBean.getTermValId());
		cstmt.setString(5, dbRequest.getFunctionalIndicator());
		if(dbRequest.isISDA())
			cstmt.setString(6, MCXConstants.REQ_ISDA_TERM);		//I - if ISDA Template requested
		else
			cstmt.setString(6, MCXConstants.REQ_AMEND_TERM);	//C - if amendment details requested
		
		cstmt.setString(7, dbRequest.getUserInd());  //User Indicator
		
		
		//log.debug("2. termBean.getTermValId() Inside the DPMXMITMDAO is >>>" + termBean.getTermValId());
		//log.debug("3. dbRequest.isISDA() Inside the DPMXMITMDAO is >>>" + dbRequest.isISDA());
		//log.debug("4. dbRequest.getUserInd() Inside the DPMXMITMDAO is >>>" + dbRequest.getUserInd());
		
		rs = cstmt.executeQuery();

		sqlca = new SQLCA(cstmt.getString(1));
		sSpErrArea = (String) cstmt.getObject(2);
		dbResponse.setSpReturnCode(cstmt.getString(3));
		dbResponse.setSpResponseMessage(sSpErrArea);

        if (sqlca.getSqlCode() == 0 && dbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
        {
			String valType = null;
			while (rs != null && rs.next()) {
				 tempBean.setTmpltId(rs.getInt(1));
				 tempBean.setTmpltTyp(rs.getString(2).trim());
				 tempBean.setISDATmpltNm(rs.getString(3).trim());
				 tempBean.setCatgyId(rs.getString(4).trim());
				 tempBean.setCatgySqId(rs.getInt(5));
				 tempBean.setCatgyNm(rs.getString(6).trim());
				 tempBean.setTermId(rs.getString(7).trim());
				 tempBean.setTermSqId(rs.getInt(8));
				 tempBean.setTermNm(rs.getString(9).trim());
				 valType = rs.getString(13);
				 if(valType == null || valType.equalsIgnoreCase(MCXConstants.TEXT_INDICATOR))
				 {
				 tempBean.setTermValId(new Long(rs.getLong(10)).intValue());
				 tempBean.setTermTextId(rs.getInt(11));
				 tempBean.setTermVal(rs.getString(12).trim());				
				 }
				 else 
				 {
				 	tempBean.setDocId(rs.getInt(11));
				 }
				 //log.debug("5. tempBean.getTmpltId() Inside the DPMXMITMDAO is >>>" + tempBean.getTmpltId());
				 //log.debug("6. tempBean.getTmpltTyp() Inside the DPMXMITMDAO is >>>" + tempBean.getTmpltTyp());
				 //log.debug("7. tempBean.getISDATmpltNm() Inside the DPMXMITMDAO is >>>" + tempBean.getISDATmpltNm());
				 //log.debug("8. tempBean.getCatgyId() Inside the DPMXMITMDAO is >>>" + tempBean.getCatgyId());
				 //log.debug("9. tempBean.getCatgySqId() Inside the DPMXMITMDAO is >>>" + tempBean.getCatgySqId());
				 //log.debug("10. tempBean.getCatgyNm() Inside the DPMXMITMDAO is >>>" + tempBean.getCatgyNm());
				 //log.debug("11. tempBean.getTermId() Inside the DPMXMITMDAO is >>>" + tempBean.getTermId());
				 //log.debug("12. tempBean.getTermSqId() Inside the DPMXMITMDAO is >>>" + tempBean.getTermSqId());
				 //log.debug("13. tempBean.getTermNm() Inside the DPMXMITMDAO is >>>" + tempBean.getTermNm());
				 //log.debug("5. tempBean.getTermValId() Inside the DPMXMITMDAO is >>>" + tempBean.getTermValId());
				 //log.debug("5. tempBean.getTermTextId() Inside the DPMXMITMDAO is >>>" + tempBean.getTermTextId());
				 //log.debug("5. tempBean.getTermVal() Inside the DPMXMITMDAO is >>>" + tempBean.getTermVal());
				 //log.debug("5. tempBean.getTermVal() Inside the DPMXMITMDAO is >>>" + tempBean.getTermVal());
				 
			}

		}		
		dbResponse.setTransaction(tempBean);
	}
}
