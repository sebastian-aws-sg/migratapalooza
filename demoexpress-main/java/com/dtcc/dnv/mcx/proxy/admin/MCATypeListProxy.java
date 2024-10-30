package com.dtcc.dnv.mcx.proxy.admin;

import com.dtcc.dnv.mcx.beans.MCATypeList;
import com.dtcc.dnv.mcx.dbhelper.admin.MCATypeListDbResponse;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.AbstractDbProxy;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IDbResponse;

/**
 * This class is used as a proxy to retrive MCA Type List
 * RTM Reference : 1.21.2
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
 * @author Seeni Mohammad Azharudin S
 * @date Sep 17, 2007
 * @version 1.0
 * 
 *  
 */

public class MCATypeListProxy extends AbstractDbProxy
{

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.layers.IDbProxy#processRequest(com.dtcc.dnv.otc.common.layers.IDbRequest)
	 */
	public IDbResponse processRequest(IDbRequest dbRequest) throws DBException, Exception 
	{
		MCATypeListDbResponse dbResponse = new MCATypeListDbResponse();
		
		for(int i=0 ;i <= 5 ;i++ )
		{
			MCATypeList mcaTypeList = new MCATypeList();
			mcaTypeList.setTemplateId("Template ID " + i);
			mcaTypeList.setTemplateName("Template Name " + i);
			mcaTypeList.setTemplateStatus("N");
			mcaTypeList.setPublishedDate("Date "+i);
			mcaTypeList.setLockIndicator("Lock Indicator "+i);
			mcaTypeList.setUserName("User "+i);
			mcaTypeList.setLastUpdatedTimestamp("LastDate"+i);
			dbResponse.addMcaTypeList(mcaTypeList);
		}

		return dbResponse;
	}

}
