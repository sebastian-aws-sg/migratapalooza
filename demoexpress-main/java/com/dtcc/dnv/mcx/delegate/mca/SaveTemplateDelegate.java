package com.dtcc.dnv.mcx.delegate.mca;

import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.proxy.admin.TemplateSaveProxy;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.builders.util.BuilderUtil;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;

/**
 * This class is used as a Delegate to fetch details of a ISDA published MCA
 * template
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
 */

public class SaveTemplateDelegate extends MCXAbstractBusinessDelegate {

	private final static MessageLogger log = MessageLogger.getMessageLogger(SaveTemplateDelegate.class.getName());

	public IServiceResponse processRequest(IServiceRequest request)
											throws BusinessException 
    {
		TemplateServiceResponse serviceResponse = new TemplateServiceResponse();
		TemplateSaveProxy saveTemplateProxy = new TemplateSaveProxy();
		TemplateServiceRequest serviceRequest = (TemplateServiceRequest) request;

		try 
		{
			TemplateDbRequest dbRequest = new TemplateDbRequest("",serviceRequest.getAuditInfo());
			new BuilderUtil().copyObject(serviceRequest, dbRequest);
			
			TemplateBean tempBean = (TemplateBean) dbRequest.getTransaction();
			//Set the Generic Template Name with user provided data
			if(!tempBean.getNxtTmpltNm().equals(""))
			tempBean.setTmpltNm(tempBean.getISDATmpltNm() + " " + tempBean.getNxtTmpltNm());
			
			TemplateDbResponse dbResponse = (TemplateDbResponse) saveTemplateProxy.processRequest(dbRequest);
			processDbRequest(serviceResponse, dbResponse);
			
			serviceResponse.setTransaction(dbResponse.getTransaction());
			}
		catch (Exception e)
			{
            log.error(" Exception in SaveTemplateDelegate.processSaveTemplateRequest() method ", e);
            throw new BusinessException("SaveTemplateDelegate", "Error occurred building database request.");
			}			
			return serviceResponse;
	}

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate#processDbRequest(com.dtcc.dnv.otc.common.layers.IServiceResponse, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse) throws BusinessException 
        {
		processSPReturnCode(serviceResponse, dbResponse);
	}
}
