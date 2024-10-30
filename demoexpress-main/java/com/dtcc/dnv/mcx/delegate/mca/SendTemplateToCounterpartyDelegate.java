package com.dtcc.dnv.mcx.delegate.mca;

import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbResponse;
import com.dtcc.dnv.mcx.proxy.mca.TemplateInfoProxy;
import com.dtcc.dnv.mcx.proxy.mca.UpdateDealerProxy;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.builders.util.BuilderUtil;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.DBException;
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

public class SendTemplateToCounterpartyDelegate extends MCXMCABusinessDelegate {

	private final static MessageLogger log = MessageLogger.getMessageLogger(SendTemplateToCounterpartyDelegate.class.getName());

	public IServiceResponse processRequest(IServiceRequest request)
			throws BusinessException {
		
		TemplateServiceRequest serviceRequest = (TemplateServiceRequest) request;
		TemplateServiceResponse serviceResponse = new TemplateServiceResponse();
		
		UpdateDealerProxy dbProxy = new UpdateDealerProxy();
		TemplateInfoProxy templateInfoProxy = new TemplateInfoProxy();
		try
		{
			TemplateDbRequest dbRequest = new TemplateDbRequest("",serviceRequest.getAuditInfo());
			new BuilderUtil().copyObject(serviceRequest, dbRequest);
			TemplateDbResponse dbResponse = null;

			TemplateBean tempBean = (TemplateBean) dbRequest.getTransaction();
			int oldTmpltId  = tempBean.getTmpltId();
			String oldTmpltTyp = tempBean.getTmpltTyp();
			
			TemplateDbResponse tmpltDBResponse = (TemplateDbResponse) templateInfoProxy.processRequest(dbRequest);
			dbRequest.setTransaction(tmpltDBResponse.getTransaction());
			
				//Set the Working Template name
				tempBean.setTmpltNm(tempBean.getTmpltNm().replaceFirst("FINAL", "WORKING"));
				//Set the operation as Submit
			dbRequest.setOpnInd(MCXConstants.SUBMIT);
			dbResponse = (TemplateDbResponse)dbProxy.processRequest(dbRequest);
			processDbRequest(serviceResponse, dbResponse);

			if(!serviceResponse.hasError() && oldTmpltTyp.equalsIgnoreCase("C"))
			{
				//Unlock the old CP Final template
				unLockTemplate(serviceRequest, serviceResponse, oldTmpltId);
			}
	
			serviceResponse.setTransaction(dbResponse.getTransaction());
		}
		catch (DBException dbe)
	    {
	          log.error(" DBException in SendTemplateToCounterpartyDelegate.processRequest() method ", dbe);
	         throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());
	    }
		catch (Exception e)
        {
            log.error(" Exception in SendTemplateToCounterpartyDelegate.processRequest() method ", e);
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
