/*
 * Created on Oct 19, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.delegate.mca;

import java.sql.Timestamp;
import java.util.Date;

import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.formatters.FormatterUtils;
import com.dtcc.dnv.mcx.proxy.mca.TemplateInfoProxy;
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
 * @author VVaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExecuteMCADelegate extends MCXAbstractBusinessDelegate {
	
	private final static MessageLogger log = MessageLogger.getMessageLogger(ExecuteMCADelegate.class.getName());

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
	 */
	public IServiceResponse processRequest(IServiceRequest request)
			throws BusinessException, UserException {
		
		TemplateServiceRequest serviceRequest = (TemplateServiceRequest) request;
		TemplateServiceResponse serviceResponse = new TemplateServiceResponse();
		
		UpdateDealerProxy dbProxy = new UpdateDealerProxy();
		TemplateInfoProxy templateInfoProxy = new TemplateInfoProxy();
		try
		{
			TemplateDbResponse dbResponse = new TemplateDbResponse();
			TemplateDbRequest dbRequest = new TemplateDbRequest("",serviceRequest.getAuditInfo());
			new BuilderUtil().copyObject(serviceRequest, dbRequest);

			//Set the Operation as 'Execute'
			dbRequest.setOpnInd(MCXConstants.EXECUTE);
			
			dbResponse = (TemplateDbResponse) templateInfoProxy.processRequest(dbRequest);
			
			TemplateBean tempBean = (TemplateBean) dbResponse.getTransaction();
			String tmpltNm = tempBean.getTmpltNm();
			
			if(tempBean.getTmpltTyp().equalsIgnoreCase(MCXConstants.EXECUTED_TEMPLATE_TYPE))
			{
				serviceResponse.setSpReturnMessage("The template is already Executed");
				serviceResponse.setHasError(true);
				return serviceResponse;
			}
			//When Execute MCA called, make the Working Template as 'Executed' Template
			if(!tempBean.getPublishDt().equalsIgnoreCase(""))
			{
				tmpltNm = tmpltNm.replaceFirst("WORKING", "EXECUTED");
				if(tempBean.getTmpltTyp().equalsIgnoreCase(MCXConstants.REEXECUTED_TEMPLATE_TYPE))
				{
					Timestamp ts = new Timestamp(new Date().getTime());
					String temp = FormatterUtils.formatOutputDateTimeStamp(ts);
					tmpltNm = tmpltNm + " " + temp;
				}
			}
			tempBean.setTmpltNm(tmpltNm);

			dbResponse = (TemplateDbResponse)dbProxy.processRequest(dbRequest);
			processDbRequest(serviceResponse, dbResponse);
			serviceResponse.setTransaction(dbResponse.getTransaction());
		}
		catch (Exception e)
        {
            log.error(" Exception in ExecuteMCADelegate.processRequest() method ", e);
            throw new BusinessException("ExecuteMCADelegate", "Error occurred building database request.");
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
