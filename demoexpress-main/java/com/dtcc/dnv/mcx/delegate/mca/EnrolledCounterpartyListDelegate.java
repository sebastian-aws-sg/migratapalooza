/*
 * Created on Sep 20, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.delegate.mca;

import com.dtcc.dnv.mcx.dbhelper.mca.CounterpartyDbResponse;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.proxy.mca.EnrolledCounterpartyListProxy;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.builders.util.BuilderUtil;
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
public class EnrolledCounterpartyListDelegate extends MCXAbstractBusinessDelegate {
	
	private final static MessageLogger log = MessageLogger.getMessageLogger(EnrolledCounterpartyListDelegate.class.getName());

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
	 */
	public IServiceResponse processRequest(IServiceRequest serviceRequest)
			throws BusinessException, UserException 
	{
		TemplateServiceRequest templateServiceRequest = (TemplateServiceRequest) serviceRequest;
		CounterpartyServiceResponse cpServiceResponse = new CounterpartyServiceResponse();		
		EnrolledCounterpartyListProxy enrollCPListProxy = new EnrolledCounterpartyListProxy();
		
		try
		{
			TemplateDbRequest dbRequest = new TemplateDbRequest("", templateServiceRequest.getAuditInfo());
			new BuilderUtil().copyObject(templateServiceRequest, dbRequest);			
			
			//Get the List of CounterParty enrolled for the Template
			CounterpartyDbResponse dbResponse = (CounterpartyDbResponse) enrollCPListProxy.processRequest(dbRequest);			
			processDbRequest(cpServiceResponse, dbResponse);
			
			new BuilderUtil().copyObject(dbResponse, cpServiceResponse);
		}
		catch(Exception e)
		{
			throw new BusinessException("BE03", "Unable to update Amendment");
		}
		
		return cpServiceResponse;
	}

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate#processDbRequest(com.dtcc.dnv.otc.common.layers.IServiceResponse, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse) throws BusinessException 
	{
		processSPReturnCode(serviceResponse, dbResponse);
	}

}
