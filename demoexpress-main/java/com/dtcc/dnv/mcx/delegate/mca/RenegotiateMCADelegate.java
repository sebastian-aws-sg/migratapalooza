/*
 * Created on Oct 24, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.delegate.mca;

import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.proxy.mca.UpdateDealerProxy;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.builders.util.BuilderUtil;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;

/**
 * @author vvaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RenegotiateMCADelegate extends MCXAbstractBusinessDelegate {
	
	private final static MessageLogger log = MessageLogger.getMessageLogger(RenegotiateMCADelegate.class.getName());

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
	 */
	public IServiceResponse processRequest(IServiceRequest request)
			throws BusinessException, UserException {
		
		TemplateServiceRequest serviceRequest = (TemplateServiceRequest) request;
		TemplateServiceResponse serviceResponse = new TemplateServiceResponse();
		
		UpdateDealerProxy dbProxy = new UpdateDealerProxy();
		
		try
		{
			TemplateDbResponse dbResponse = new TemplateDbResponse();
			TemplateDbRequest dbRequest = new TemplateDbRequest("",serviceRequest.getAuditInfo());
			new BuilderUtil().copyObject(serviceRequest, dbRequest);

			//Set the Operation as 'Renegotiate MCA'
			dbRequest.setOpnInd(MCXConstants.REEXECUTED_TEMPLATE_TYPE);

			TemplateBean tempBean = (TemplateBean) dbRequest.getTransaction();
			//Create the Working Template Name
			tempBean.setTmpltNm(tempBean.getTmpltNm().replaceFirst("EXECUTED", "WORKING"));

			dbResponse = (TemplateDbResponse)dbProxy.processRequest(dbRequest);
			processDbRequest(serviceResponse, dbResponse);
			if(!serviceResponse.hasError())	
			{
			serviceResponse.setTransaction(dbResponse.getTransaction());
		}
		}
		catch (Exception e)
        {
            log.error(" Exception in RenegotiateMCADelegate.processRequest() method ", e);
            throw new BusinessException("RenegotiateMCADelegate", "Error occurred building database request.");
        }
		return serviceResponse;
	}

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate#processDbRequest(com.dtcc.dnv.otc.common.layers.IServiceResponse, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void processDbRequest(IServiceResponse serviceResponse,
			IDbResponse dbResponse) throws BusinessException 
	{
		processSPReturnCode(serviceResponse, dbResponse);
	}

}
