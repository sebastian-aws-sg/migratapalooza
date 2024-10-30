/*
 * Created on Sep 20, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.delegate.mca;

import com.dtcc.dnv.mcx.beans.TermBean;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbResponse;
import com.dtcc.dnv.mcx.proxy.mca.ModifyTermProxy;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.builders.DbRequestBuilder;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;

/**
 * @author VVaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AgreeAmendmentDelegate extends MCXMCABusinessDelegate {
	
    private final static MessageLogger log = MessageLogger.getMessageLogger(
    		AgreeAmendmentDelegate.class.getName());


	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(
	 * com.dtcc.dnv.otc.common.layers.IServiceRequest)
	 */
	public IServiceResponse processRequest(IServiceRequest request)
			throws BusinessException, UserException 
	{
		ModifyTermServiceResponse serviceResponse = new ModifyTermServiceResponse();
		ModifyTermServiceRequest serviceRequest = (ModifyTermServiceRequest) request;
		ModifyTermProxy agreeAmendProxy = new ModifyTermProxy();
		boolean eligForLock = false;
		try
		{
			ModifyTermDbRequest dbRequest = new ModifyTermDbRequest("", serviceRequest.getAuditInfo());
			DbRequestBuilder.copyObject(request, dbRequest);
			dbRequest.setTmpltLocked(serviceRequest.isTmpltLocked());
			dbRequest.setFunctionalIndicator(MCXConstants.FUNCTION_INDICATOR_MCA);
			TermBean termBean = (TermBean) dbRequest.getTransaction();
			
			if ((termBean.getTmpltTyp().equalsIgnoreCase(MCXConstants.WORKING_TEMPLATE_TYPE)
					|| termBean.getTmpltTyp().equalsIgnoreCase(MCXConstants.REEXECUTED_TEMPLATE_TYPE))
					&& !dbRequest.isTmpltLocked())
			{
					eligForLock = isEligibleForLock(serviceRequest,serviceResponse,termBean.getTmpltId());
					if(!eligForLock)
					{
						return serviceResponse;
					}
			}

			//Lock the Template if the template is not locked
			if(!dbRequest.isTmpltLocked())
			{
				lockTemplate(serviceRequest, serviceResponse, termBean.getTmpltId());
			}
			//If the lock is success, send to Agree Amendment
			if(!serviceResponse.hasError())
			{
			ModifyTermDbResponse dbResponse = (ModifyTermDbResponse) agreeAmendProxy.processRequest(dbRequest);
				processDbRequest(serviceResponse, dbResponse);
				serviceResponse.setTransaction(dbResponse.getTransaction());
			}
		}
		catch(Exception e)
		{
			throw new BusinessException("BE03", "Unable to update Amendment");
		}
		
		return serviceResponse;
	}

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate#processDbRequest(
	 * com.dtcc.dnv.otc.common.layers.IServiceResponse, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse) throws BusinessException 
	{
		processSPReturnCode(serviceResponse, dbResponse);
	}

}
