package com.dtcc.dnv.mcx.delegate.admin;

import java.util.List;

import com.dtcc.dnv.mcx.beans.CategoryBean;
import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.beans.TermBean;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbResponse;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceResponse;
import com.dtcc.dnv.mcx.proxy.admin.TemplateSaveProxy;
import com.dtcc.dnv.mcx.proxy.mca.ModifyTermProxy;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.builders.DbRequestBuilder;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;

/**
 * This class is used as a Delegate to save details of a ISDA template
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
 * @date Sep 12, 2007
 * @version 1.0
 */

public class SaveTemplateSetupDelegate extends MCXAbstractBusinessDelegate {

	private final static MessageLogger log = MessageLogger.getMessageLogger(SaveTemplateSetupDelegate.class.getName());
	//private Logger log = Logger.getLogger(SaveTemplateSetupDelegate.class);

	public IServiceResponse processRequest(IServiceRequest request)
											throws BusinessException 
    {
		IServiceResponse serviceResponse = null;
		TemplateServiceRequest serviceRequest = (TemplateServiceRequest) request;
		serviceResponse = processSaveTemplateRequest(serviceRequest);
		return serviceResponse;
	}

	/**
	 * Method serviceNewRequest.
	 * 
	 * @param request
	 * @return IServiceResponse
	 * @throws BusinessException
	 */
	private IServiceResponse processSaveTemplateRequest(
															TemplateServiceRequest serviceRequest) 
															throws BusinessException 
    {
		final String REQUEST_ID = "DTMXMSAV";
		TemplateServiceResponse serviceResponse = null;
		TemplateBean templateBean = null;
		ModifyTermDbResponse termDbResponse =  null;
		try 
		{
			TemplateSaveProxy saveTemplateProxy = new TemplateSaveProxy();
			TemplateDbResponse dbResponse = new TemplateDbResponse();
			TemplateDbRequest dbRequest = new TemplateDbRequest(REQUEST_ID,serviceRequest.getAuditInfo());
			serviceResponse = new TemplateServiceResponse();
			DbRequestBuilder.copyObject(serviceRequest, dbRequest);
			templateBean = (TemplateBean) serviceRequest.getTransaction();	

			if(templateBean != null && templateBean.isPropValInsReq()){
				termDbResponse = postPropreitaryTermValue(serviceRequest);
				processDbRequest(serviceResponse,termDbResponse);
			}			
			
			if(!serviceResponse.hasError())
			{
			dbResponse = (TemplateDbResponse) saveTemplateProxy.processRequest(dbRequest);	
			processDbRequest(serviceResponse, dbResponse);
			}
			return serviceResponse;
		} catch (DBException dbe)
        {
            log.error(" DBException in SaveTemplateSetupDelegate.processSaveTemplateRequest() method ", dbe);
            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());

        } catch (Exception e)
        {
            log.error(" Exception in SaveTemplateSetupDelegate.processSaveTemplateRequest() method ", e);
            throw new BusinessException("SaveTemplateSetupDelegate", "Error occurred building database request.");
        }
	}
	
	/* (non-Javadoc)
     * @see com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate#processDbRequest(com.dtcc.dnv.otc.common.layers.IServiceResponse, com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse) throws BusinessException
    {
        processSPReturnCode(serviceResponse,dbResponse);
        
    }
	/**
	 * Method serviceNewRequest.
	 * 
	 * @param request
	 * @return IServiceResponse
	 * @throws BusinessException
	 */
	private ModifyTermDbResponse postPropreitaryTermValue(
			TemplateServiceRequest serviceRequest) throws BusinessException {

		List categyBeanList = null;
		TermBean termBean = null; 
		int categyBeanListSize= 0;
		CategoryBean catgyBean = null;
		ModifyTermDbRequest termDbRequest = null;
		ModifyTermDbResponse dbResponse =  null;
		TemplateBean templateBean = null;
		ModifyTermProxy postAmendProxy = new ModifyTermProxy();

		try {
			templateBean = (TemplateBean) serviceRequest.getTransaction();
			categyBeanList = templateBean.getCategBeanList();
			if(categyBeanList != null) {
				categyBeanListSize = categyBeanList.size();
				for(int index=0; index < categyBeanListSize ; index++){
					catgyBean = (CategoryBean) categyBeanList.get(index);
					if(catgyBean.getCatgyStCd().equalsIgnoreCase(MCXConstants.PROPREITARY_CATEGORY_STATUS)){
						if(catgyBean.getTermList() != null )
						termBean = (TermBean)catgyBean.getTermList().get(0);						
						break;
					}					
				}
				termBean.setTermVal(MCXConstants.EMPTY_SPACE);
				termBean.setTmpltId(templateBean.getTmpltId());
				termBean.setTmpltTyp(templateBean.getTmpltTyp());
				termDbRequest = new ModifyTermDbRequest("REQ_ID", serviceRequest.getAuditInfo());
				termDbRequest.setUsrId(serviceRequest.getUserId());
				termDbRequest.setFunctionalIndicator(MCXConstants.FUNCTION_INDICATOR_ADMIN);
				termDbRequest.setIndicator(MCXConstants.TEXT_INDICATOR);
				termDbRequest.setTransaction(termBean);
				dbResponse = (ModifyTermDbResponse) postAmendProxy.processRequest(termDbRequest);

			}
		
		} catch (DBException dbe) {
			log.error(dbe);
			throw new BusinessException("", dbe.getMessage());
		} catch (Exception e) {
			throw new BusinessException("", e.getMessage());
		}
		return dbResponse;
	}      
}