/*
 * Created on Sep 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.delegate.mca;

import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.proxy.mca.CommentListProxy;
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
public class ViewCommentDelegate extends MCXAbstractBusinessDelegate {
	
	private final static MessageLogger log = MessageLogger.getMessageLogger(ViewCommentDelegate.class.getName());

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
	 */
	public IServiceResponse processRequest(IServiceRequest request)
			throws BusinessException, UserException 
	{
		ModifyTermServiceResponse serviceResponse = new ModifyTermServiceResponse();
		ModifyTermServiceRequest serviceRequest = (ModifyTermServiceRequest) request;
		CommentListProxy fetchCommentProxy = new CommentListProxy();

		try
		{
			ModifyTermDbRequest dbRequest = new ModifyTermDbRequest("", serviceRequest.getAuditInfo());
			DbRequestBuilder.copyObject(serviceRequest, dbRequest);
			
			//Fetch the Comment Details
			ModifyTermDbResponse dbResponse = (ModifyTermDbResponse) fetchCommentProxy.processRequest(dbRequest);
			processDbRequest(serviceResponse, dbResponse);
			serviceResponse.setTransaction(dbResponse.getTransaction());
		}
		catch(Exception e)
		{
			throw new BusinessException("BE03", "Unable to Fetch Comments");
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
