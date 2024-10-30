/*
 * Created on Sep 17, 2007
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
public class PostCommentDelegate extends MCXMCABusinessDelegate {
	
	private final static MessageLogger log = MessageLogger.getMessageLogger(PostCommentDelegate.class.getName());

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
	 */
	public IServiceResponse processRequest(IServiceRequest request)
			throws BusinessException, UserException 
	{
		ModifyTermServiceResponse serviceResponse = new ModifyTermServiceResponse();
		ModifyTermProxy postCommentProxy = new ModifyTermProxy();
		ModifyTermServiceRequest serviceRequest = (ModifyTermServiceRequest) request;
		ModifyTermDbResponse dbResponse = null;
		TermBean termBean = null;
		TermBean tempBean = null;
		boolean eligForLock = false;
		try
		{
			ModifyTermDbRequest dbRequest = new ModifyTermDbRequest("", serviceRequest.getAuditInfo());			
			DbRequestBuilder.copyObject(serviceRequest, dbRequest);
			termBean = (TermBean) dbRequest.getTransaction();
			dbRequest.setIndicator(MCXConstants.COMMENT_INDICATOR);
			dbRequest.setFunctionalIndicator(MCXConstants.FUNCTION_INDICATOR_MCA);
			dbRequest.setTmpltLocked(serviceRequest.isTmpltLocked());
			
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
			
			//Lock the Template if it is already not locked
			if ((termBean.getTmpltTyp().equalsIgnoreCase(MCXConstants.GENERIC_TEMPLATE_TYPE)
					|| termBean.getTmpltTyp().equalsIgnoreCase(MCXConstants.WORKING_TEMPLATE_TYPE)
					|| termBean.getTmpltTyp().equalsIgnoreCase(MCXConstants.REEXECUTED_TEMPLATE_TYPE))
					&& !dbRequest.isTmpltLocked())
			{
				lockTemplate(serviceRequest,serviceResponse,termBean.getTmpltId());
				dbRequest.setTmpltLocked(true);
			}
			//Post the Comment
			if(!serviceResponse.hasError())
			{
				dbResponse = (ModifyTermDbResponse) postCommentProxy.processRequest(dbRequest);
			processSPReturnCode(serviceResponse, dbResponse);
			}			
			//Lock the Template if it is already not locked
			if(!serviceResponse.hasError() && !dbRequest.isTmpltLocked())
			{
				tempBean = (TermBean) dbResponse.getTransaction();				
				lockTemplate(serviceRequest,serviceResponse,tempBean.getTmpltId());
			}
			
			if(dbResponse != null )
			serviceResponse.setTransaction(dbResponse.getTransaction());
		}
		catch(Exception e)
		{
			throw new BusinessException("BE06", "Unable to Post Comments");
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
