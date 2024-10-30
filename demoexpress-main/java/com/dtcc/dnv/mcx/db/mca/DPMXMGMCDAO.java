package com.dtcc.dnv.mcx.db.mca;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dtcc.dnv.mcx.beans.CategoryBean;
import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.beans.TermBean;
import com.dtcc.dnv.mcx.db.MCXCommonDB;
import com.dtcc.dnv.mcx.dbhelper.mca.CategoryDbResponse;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
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
 * @author Narahari A
 * @date Sep 3, 2007
 * @version 1.0
 * 
 */

public  class DPMXMGMCDAO extends MCXCommonDB {

	private final static MessageLogger log = MessageLogger.getMessageLogger(DPMXMGMCDAO.class.getName());

	/**
	 * DPMXMGMCDAO
	 */
	public DPMXMGMCDAO() {
		super();
		SPName = "DPMXMGMC";
	}

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.legacy.CommonDB#execute(com.dtcc.dnv.otc.common.layers.IDbRequest, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void execute(IDbRequest dbRequest, IDbResponse dbResponse)
			throws DBException, SQLException {
		
		TemplateDbRequest templateDbRequest = (TemplateDbRequest) dbRequest;
		CategoryDbResponse categyDbResponse =(CategoryDbResponse) dbResponse;
		TemplateBean templateBean = (TemplateBean) templateDbRequest
				.getTransaction();
		String lckUsrInd = templateBean.getLockUsrInd();
		String tmpltType = templateBean.getTmpltTyp();
		if((tmpltType.equalsIgnoreCase(MCXConstants.WORKING_TEMPLATE_TYPE) 
				|| tmpltType.equalsIgnoreCase(MCXConstants.REEXECUTED_TEMPLATE_TYPE)) 
				&& lckUsrInd.equalsIgnoreCase("D"))
		{
			boolean isActingDealer = false;
			if(templateBean.getOrgDlrCd().equalsIgnoreCase(templateDbRequest.getCmpnyId()))
			{
				isActingDealer = true;
			}
			if(templateBean.getOrgDlrCd().equals(templateBean.getOrgCltCd()) 
					&& templateBean.getDlrStCd().equalsIgnoreCase("P"))
			{
				isActingDealer = true;
			}
			else if(templateBean.getOrgDlrCd().equals(templateBean.getOrgCltCd()) 
					&& !templateBean.getDlrStCd().equalsIgnoreCase("P"))
			{
				isActingDealer = false;
			}

			
			if(isActingDealer)
			{
				//Dealer
				if(templateBean.getDlrStCd().equalsIgnoreCase(MCXConstants.MCA_STATUS_PENDING))
					lckUsrInd = MCXConstants.ORG_USR;
			}
			else
			{
				//Client
				if(templateBean.getCltStCd().equalsIgnoreCase(MCXConstants.MCA_STATUS_PENDING))
					lckUsrInd = MCXConstants.ORG_USR;
			}
		}
		templateBean.setLockUsrInd(lckUsrInd);

		//Get the Category Id and Sequence from Id Value
		String cateID = templateDbRequest.getCatgyId();
		if(!cateID.equalsIgnoreCase(MCXConstants.EMPTY))
		{
			if(cateID.indexOf(MCXConstants.TILD) != 0)
			{
				templateDbRequest.setCatgyId(cateID.substring(0,cateID.indexOf(MCXConstants.TILD)));
				templateDbRequest.setCatgySeqId(Integer.parseInt(cateID.substring(cateID.indexOf(MCXConstants.TILD)+1)));
			}
		}

		cstmt = con.prepareCall("{ call " + QUAL + "DPMXMGMC (?,?,?,?,?,?,?,?) }");

		cstmt.registerOutParameter(1, Types.CHAR);
		cstmt.registerOutParameter(2, Types.CHAR);
		cstmt.registerOutParameter(3, Types.CHAR);		
		
		cstmt.setInt(4,templateBean.getTmpltId()); 			//Template ID
		cstmt.setString(5, templateDbRequest.getCatgyId());	// Category Id
		cstmt.setInt(6, templateDbRequest.getCatgySeqId());	//Category Seq Id
		cstmt.setString(7,templateDbRequest.getFunctInd()); // Functional Indicator "A" for Admin or "M" for MCA		
		cstmt.setString(8,lckUsrInd);   //Lock User Indicator "D" or "L" or "O"

		rs = cstmt.executeQuery();

		sqlca = new SQLCA(cstmt.getString(1));
		sSpErrArea = (String) cstmt.getObject(2);
		categyDbResponse.setSpReturnCode(cstmt.getString(3));
		categyDbResponse.setSpResponseMessage(sSpErrArea);

        if (sqlca.getSqlCode() == 0 && categyDbResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00))
		{
			CategoryBean cateBean = null;
			TermBean trmBean = null;
			int prevCatId = 0;		//Previous Category Sequence Id
			int prevTermID = 0;		//Previous Term Sequence Id
			List categoryList = new ArrayList();
			List termList = null;
			
			while (rs != null && rs.next()) {
				String indicator = rs.getString(13).trim();
				if(prevCatId == 0 || prevCatId != rs.getInt(4))
				{
					//Set the Category values
					cateBean = new CategoryBean();
					cateBean.setCatgyId(rs.getString(1).trim());
					cateBean.setCatgyNm(rs.getString(2).trim());
					cateBean.setCatgyStCd( rs.getString(3).trim());
					cateBean.setCatgySqId(rs.getInt(4));
					categoryList.add(cateBean);
					termList = new ArrayList();
					prevTermID = 0;
				}
				if(prevTermID == 0 || prevTermID != rs.getInt(8))
				{
					trmBean = new TermBean();
					//Set all Term Values
					trmBean.setCatgyId(rs.getString(1).trim());
					trmBean.setCatgyNm(rs.getString(2).trim());					
					trmBean.setCatgySqId(rs.getInt(4));
					trmBean.setTermId(rs.getString(5).trim()); // Term ID
					trmBean.setTermNm(rs.getString(6).trim()); // Term Description
					trmBean.setTermStCd(rs.getString(7).trim()); // Term Status Code
					trmBean.setTermSqId(rs.getInt(8));
				}
				
				if(MCXConstants.TEXT_INDICATOR.equalsIgnoreCase(indicator))
				{
					trmBean.setTermValId(new Long(rs.getLong(9)).intValue()); // Amendment ID				
					trmBean.setAmndtStCd(rs.getString(10).trim()); //Amendment Status Code
					trmBean.setTermTextId(rs.getInt(11));
					trmBean.setTermVal(rs.getString(12).trim());
				}
				else if(MCXConstants.DOCUMENT_INDICATOR.equalsIgnoreCase(indicator))
				{
					trmBean.setDocId(rs.getInt(11));
					trmBean.setDocName(rs.getString(12));
				}
				else if(MCXConstants.COMMENT_INDICATOR.equalsIgnoreCase(indicator))
				{
					trmBean.setCmntInd(true);
				}
				
				if(termList != null && prevTermID != trmBean.getTermSqId())
				{
					if(trmBean.getTermStCd().equalsIgnoreCase("N") && 
							cateBean.getCatgyNm().equalsIgnoreCase(trmBean.getTermNm()))
					{
						cateBean.setCatgyTrm(trmBean);
					}
					else
					{
					termList.add(trmBean);
				}
				}
				if((termList == null || termList.isEmpty()) && cateBean.getCatgyTrm() == null)
				{
					categoryList.remove(cateBean);
				}
				if(cateBean != null)
					cateBean.setTermList(termList);

				prevTermID = trmBean.getTermSqId();
				prevCatId = cateBean.getCatgySqId();
			}
			updateCategoryListWithView(categoryList, templateDbRequest.getViewInd());
			categyDbResponse.setCatgyList(categoryList);		
		}
	}
	
	private void updateCategoryListWithView(List categoryList, String viewInd)
	{
		if(viewInd.equalsIgnoreCase(MCXConstants.VIEW_INDICATOR_COMPLETE))
			return;
		
		CategoryBean cateBean = null;
		List termList = null;
		TermBean termBean = null;
		Iterator cateIter = categoryList.iterator();
		while(cateIter.hasNext())
		{
			cateBean = (CategoryBean) cateIter.next();
			termList = cateBean.getTermList();
			if(termList == null || termList.isEmpty())
			{
				cateIter.remove();
				continue;
			}
			Iterator iter = termList.iterator();
			if(viewInd.equalsIgnoreCase(MCXConstants.VIEW_INDICATOR_AMENDMENT))
			{
				while(iter.hasNext())
				{
					termBean = (TermBean) iter.next();
					if(termBean.getAmndtStCd().equalsIgnoreCase("I"))
					{
						iter.remove();
					}
				}
			}
			else if(viewInd.equalsIgnoreCase(MCXConstants.VIEW_INDICATOR_COMMENTS))
			{
				while(iter.hasNext())
				{
					termBean = (TermBean) iter.next();
					if(!termBean.isCmntInd())
					{
						iter.remove();
					}
				}
			}
			if(termList == null || termList.isEmpty())
				cateIter.remove();
		}
	}
	
}
