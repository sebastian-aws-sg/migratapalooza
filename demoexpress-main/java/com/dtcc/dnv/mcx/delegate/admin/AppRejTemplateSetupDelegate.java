package com.dtcc.dnv.mcx.delegate.admin;

import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceResponse;
import com.dtcc.dnv.mcx.proxy.admin.TemplateApprovalProxy;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.builders.DbRequestBuilder;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;

/**
 * This class is used as a Delegate to Approve/Reject the Admin template setup
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
 * @author Elango TR
 * @date Sep 19, 2007
 * @version 1.0
 */

public class AppRejTemplateSetupDelegate extends MCXAbstractBusinessDelegate {
	private final static MessageLogger log = MessageLogger.getMessageLogger(AppRejTemplateSetupDelegate.class.getName());

	//private Logger log = Logger.getLogger(AppRejTemplateSetupDelegate.class);

	public IServiceResponse processRequest(IServiceRequest request)
											throws BusinessException 
    {
		IServiceResponse serviceResponse = null;
		TemplateServiceRequest serviceRequest = (TemplateServiceRequest) request;
		serviceResponse = processAppRejTemplateRequest(serviceRequest);
		return serviceResponse;
	}

	/**
	 * Method serviceNewRequest.
	 * 
	 * @param request
	 * @return IServiceResponse
	 * @throws BusinessException
	 */
	private IServiceResponse processAppRejTemplateRequest(
															TemplateServiceRequest serviceRequest) 
															throws BusinessException 
    {
		// DTMXAPUB
		TemplateServiceResponse serviceResponse = null;
		final String REQUEST_ID = "DTMXAPUB";
		try {
			TemplateApprovalProxy appRejTemplateProxy = new TemplateApprovalProxy();
			TemplateDbResponse dbResponse = new TemplateDbResponse();
			TemplateDbRequest dbRequest = new TemplateDbRequest(REQUEST_ID,serviceRequest.getAuditInfo());
			serviceResponse = new TemplateServiceResponse();
			DbRequestBuilder.copyObject(serviceRequest, dbRequest);
			dbResponse = (TemplateDbResponse) appRejTemplateProxy.processRequest(dbRequest);
			processDbRequest(serviceResponse,dbResponse);
			return serviceResponse;
		} catch (DBException dbe)
        {
            log.error(" DBException in AppRejTemplateSetupDelegate.processAppRejTemplateRequest method ", dbe);
            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());

        } catch (Exception e)
        {
            log.error(" Exception in AppRejTemplateSetupDelegate.processAppRejTemplateRequest method ", e);
            throw new BusinessException("AppRejTemplateSetupDelegate", "Error occurred building database request.");
        }
	}
	
	/* (non-Javadoc)
     * @see com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate#processDbRequest(com.dtcc.dnv.otc.common.layers.IServiceResponse, com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse) throws BusinessException
    {
        processSPReturnCode(serviceResponse,dbResponse);
        
    }
}
