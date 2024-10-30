package com.dtcc.dnv.mcx.db.mca;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.sql.Types;

import com.dtcc.dnv.mcx.beans.TermBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.mcx.util.MessageResources;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.legacy.SQLCA;

/**
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
public class DPMXMAMNDAO extends MCXCommonDB {
	private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXMAMNDAO.class.getName());
	
	/**
	 * DPMXMAMNDAO
	 *
	 */
	public DPMXMAMNDAO()
	{
		super();
		this.poolName =  MessageResources.getMessage("common.db2.pool");
		SPName = "DPMXMAMN";
	}
	
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void execute(IDbRequest request, IDbResponse response)
			throws DBException, SQLException 
	{
	    log.debug("1. Inside the DPMXMAMNDAO >>>");
	    
		ModifyTermDbRequest termDbRequest = (ModifyTermDbRequest) request;
		ModifyTermDbResponse termDbResponse = (ModifyTermDbResponse) response;
		TermBean termBean = (TermBean) termDbRequest
				.getTransaction();
		//Set the Image Object to be sent
		byte[] imgObj = new byte[0];
		if(termDbRequest.getIndicator().equalsIgnoreCase(MCXConstants.DOCUMENT_INDICATOR))
		{
			imgObj = termBean.getImageObj();
		}
		//log.debug("2. termBean.getImageObj() in DPMXMAMNDAO is >>>" + termBean.getImageObj());
		int objLen = imgObj.length;

		cstmt = con.prepareCall("{ call " + QUAL + "DPMXMAMN (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");

		cstmt.registerOutParameter(1, Types.CHAR);
		cstmt.registerOutParameter(2, Types.CHAR);
		cstmt.registerOutParameter(3, Types.CHAR);
		cstmt.registerOutParameter(4, Types.INTEGER); //Template ID
		cstmt.registerOutParameter(5, Types.CHAR);    //Template Type
		cstmt.registerOutParameter(15, Types.DOUBLE); //Amendment ID
		cstmt.registerOutParameter(16, Types.DOUBLE); //Document ID
			
		cstmt.setInt(4, termBean.getTmpltId());
		cstmt.setString(5, termBean.getTmpltTyp());
		cstmt.setString(6, termBean.getCatgyId());
		cstmt.setInt(7, termBean.getCatgySqId());
		cstmt.setString(8, termBean.getTermId());
		cstmt.setInt(9, termBean.getTermSqId());
		cstmt.setString(10, termDbRequest.getUsrId());
		cstmt.setString(11, termBean.getAmndtStCd());
		cstmt.setString(12, termDbRequest.getFunctionalIndicator());
		cstmt.setString(13, termDbRequest.getIndicator());
		if(MCXConstants.DOCUMENT_INDICATOR.equalsIgnoreCase(termDbRequest.getIndicator()))
		{
			cstmt.setString(14, termBean.getDocName());
		}
		else if(MCXConstants.TEXT_INDICATOR.equalsIgnoreCase(termDbRequest.getIndicator()))
		{
			cstmt.setString(14, termBean.getTermVal());
		}
		else if(MCXConstants.COMMENT_INDICATOR.equalsIgnoreCase(termDbRequest.getIndicator()))
		{
			cstmt.setString(14, termBean.getCmntTxt());
		}
		else
		{
			cstmt.setString(14, MCXConstants.EMPTY);
		}
		
		//Set Amendment ID in case of Agree Amendment
		if(MCXConstants.AGREE_AMENDMENT_STATUS_CD.equalsIgnoreCase(termBean.getAmndtStCd()))
			cstmt.setDouble(15, termBean.getTermValId());
		else
			cstmt.setDouble(15, 0); 
		
		cstmt.setDouble(16, termDbRequest.getValueID()); 		//Value ID (Either Text Id or Document ID)
		cstmt.setString(17, termDbRequest.getDocDeleteInd());  	//Doc Delete Indicator
		
		cstmt.setBinaryStream(18, new ByteArrayInputStream(imgObj), objLen); //Image Object
				
		//log.debug("3. termBean.getTmpltId() in DPMXMAMNDAO is >>>" + termBean.getTmpltId());
		//log.debug("4. termBean.getTmpltTyp() in DPMXMAMNDAO is >>>" + termBean.getTmpltTyp());
		//log.debug("5. termBean.getCatgyId() in DPMXMAMNDAO is >>>" + termBean.getCatgyId());
		//log.debug("6. termBean.getCatgySqId() in DPMXMAMNDAO is >>>" + termBean.getCatgySqId());
		//log.debug("7. termBean.getTermId() in DPMXMAMNDAO is >>>" + termBean.getTermId());
		//log.debug("8. termBean.getTermSqId() in DPMXMAMNDAO is >>>" + termBean.getTermSqId());
		//log.debug("9. termDbRequest.getUsrId() in DPMXMAMNDAO is >>>" + termDbRequest.getUsrId());
		//log.debug("10. termBean.getAmndtStCd() in DPMXMAMNDAO is >>>" + termBean.getAmndtStCd());
		//log.debug("11. termDbRequest.getFunctionalIndicator() in DPMXMAMNDAO is >>>" + termDbRequest.getFunctionalIndicator());
		//log.debug("12. termDbRequest.getIndicator() in DPMXMAMNDAO is >>>" + termDbRequest.getIndicator());
		//log.debug("13. termBean.getDocName() in DPMXMAMNDAO is >>>" + termBean.getDocName());
		//log.debug("14. termBean.getTermVal() in DPMXMAMNDAO is >>>" + termBean.getTermVal());
		//log.debug("15. termBean.getCmntTxt() in DPMXMAMNDAO is >>>" + termBean.getCmntTxt());
		//log.debug("16. termBean.getTermValId() in DPMXMAMNDAO is >>>" + termBean.getTermValId());
		//log.debug("17. termBean.getTermValId() in DPMXMAMNDAO is >>>" + termBean.getTermValId());
		//log.debug("18. termDbRequest.getValueID() in DPMXMAMNDAO is >>>" + termDbRequest.getValueID());
		//log.debug("19. termDbRequest.getDocDeleteInd() in DPMXMAMNDAO is >>>" + termDbRequest.getDocDeleteInd());
		//log.debug("20. imgObj in DPMXMAMNDAO is >>>" + imgObj);

		
		cstmt.executeUpdate();

		sqlca = new SQLCA(cstmt.getString(1));
		sSpErrArea = (String) cstmt.getObject(2);
		termDbResponse.setSpReturnCode(cstmt.getString(3).trim());
		termDbResponse.setSpResponseMessage(sSpErrArea);
		
        if (sqlca.getSqlCode() == 0 && termDbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
		{
			TermBean trmBean = new TermBean();
			
			trmBean.setTmpltId(cstmt.getInt(4));      //InOut Parameter Template Id
			trmBean.setTmpltTyp(cstmt.getString(5));  //InOut Parameter Template Type
			
			trmBean.setTermValId(new Double(cstmt.getDouble(15)).intValue());  //InOut Parameter Amendment ID
			trmBean.setDocId(new Double(cstmt.getDouble(16)).intValue());      //InOut Parameter Document ID
			
			termDbResponse.setTransaction(trmBean);
			//log.debug("21. trmBean.getTmpltId in DPMXMAMNDAO is >>>" + trmBean.getTmpltId());
			//log.debug("22. trmBean.getTmpltTyp in DPMXMAMNDAO is >>>" + trmBean.getTmpltTyp());
			//log.debug("23. trmBean.getTermValId in DPMXMAMNDAO is >>>" + trmBean.getTermValId());
			//log.debug("24. trmBean.getDocId in DPMXMAMNDAO is >>>" + trmBean.getDocId());
		}
	}

}
