package com.dtcc.dnv.mcx.delegate.mca;

import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.proxy.mca.LockUnlockProxy;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.builders.DbRequestBuilder;
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

public class UnlockTemplateDelegate extends MCXAbstractBusinessDelegate {

	private final static MessageLogger log = MessageLogger.getMessageLogger(UnlockTemplateDelegate.class.getName());

	public IServiceResponse processRequest(IServiceRequest request)
											throws BusinessException 
    {
		TemplateServiceResponse serviceResponse = new TemplateServiceResponse();
		LockUnlockProxy unlockProxy = new LockUnlockProxy();
		TemplateServiceRequest serviceRequest = (TemplateServiceRequest) request;
		
		try 
    {
			TemplateDbRequest dbRequest = new TemplateDbRequest("",serviceRequest.getAuditInfo());
			DbRequestBuilder.copyObject(serviceRequest, dbRequest);

			//Lock or Unlock the template based on indicator 
			TemplateDbResponse dbResponse = (TemplateDbResponse) unlockProxy.processRequest(dbRequest);
			processDbRequest(serviceResponse, dbResponse);
			serviceResponse.setTransaction(dbResponse.getTransaction());
		} 
		catch (Exception e)
        {
            log.error(" Exception in UnlockTemplateDelegate.processUnlockTemplateRequest() method ", e);
            throw new BusinessException("UnlockTemplateDelegate", "Error occurred building database request.");
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
