package com.dtcc.dnv.mcx.delegate.admin;

import java.util.List;

import com.dtcc.dnv.mcx.beans.CategoryBean;
import com.dtcc.dnv.mcx.dbhelper.mca.CategoryDbResponse;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceResponse;
import com.dtcc.dnv.mcx.formatters.FormatterDelegate;
import com.dtcc.dnv.mcx.formatters.IFormatter;
import com.dtcc.dnv.mcx.proxy.mca.CategoryListProxy;
import com.dtcc.dnv.mcx.proxy.mca.MCAGridProxy;
import com.dtcc.dnv.mcx.proxy.mca.TemplateInfoProxy;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.builders.util.BuilderUtil;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;

/**
 * This class is used as a Delegate to fetch details of a ISDA published MCA template
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

public class ViewAdminMCASetupDelegate extends MCXAbstractBusinessDelegate {

	private final static MessageLogger log = MessageLogger.getMessageLogger(ViewAdminMCASetupDelegate.class.getName());
	//private Logger log = Logger.getLogger(ViewAdminMCASetupDelegate.class);
	

	public IServiceResponse processRequest(IServiceRequest request)
											throws BusinessException 
    {
		IServiceResponse serviceResponse = null;
		TemplateServiceRequest serviceRequest = (TemplateServiceRequest) request;
		serviceResponse = processViewAdminISDATemplateRequest(serviceRequest);
		return serviceResponse;
	}

	/**
	 * Method serviceNewRequest.
	 * 
	 * @param request
	 * @return IServiceResponse
	 * @throws BusinessException
	 */
	private IServiceResponse processViewAdminISDATemplateRequest(
															TemplateServiceRequest serviceRequest) 
															throws BusinessException 
    {
		final String REQUEST_ID = "ViewAdminMCASetupDelegate";
		TemplateServiceResponse serviceResponse = null;
		try 
		{
			CategoryDbResponse categyDbResponse = null;
			CategoryDbResponse categyListDbResponse = null;
			
			CategoryListProxy categyProxy = new CategoryListProxy();
			MCAGridProxy gridProxy = new MCAGridProxy();
			TemplateInfoProxy templateProxy = new TemplateInfoProxy();
			
			TemplateDbResponse dbResponse = null;
			TemplateDbRequest dbRequest = new TemplateDbRequest(REQUEST_ID, serviceRequest.getAuditInfo());;
			TemplateDbResponse tmpltDbResponse = null;
			serviceResponse = new TemplateServiceResponse();
			new BuilderUtil().copyObject(serviceRequest,dbRequest);	
			log.debug(" B4 call templateProxy proxy in  ViewAdminMCASetupDelegate ");
			tmpltDbResponse = (TemplateDbResponse) templateProxy.processRequest(dbRequest);
			processDbRequest(serviceResponse, tmpltDbResponse);
			serviceResponse.setTransaction(tmpltDbResponse.getTransaction());
			dbRequest.setTransaction(tmpltDbResponse.getTransaction());
			log.debug(" B4 call categyProxy proxy in  ViewAdminMCASetupDelegate ");
			categyListDbResponse = (CategoryDbResponse) categyProxy.processRequest(dbRequest);
			processDbRequest(serviceResponse, tmpltDbResponse);
			serviceResponse.setCategyList(categyListDbResponse.getCatgyList());

			log.debug(" B4 call gridProxy proxy in  ViewAdminMCASetupDelegate ");
			categyDbResponse = (CategoryDbResponse) gridProxy.processRequest(dbRequest);
			processDbRequest(serviceResponse, tmpltDbResponse);
			serviceResponse.setCategyBeanList(categyDbResponse.getCatgyList());		
			List categyList = serviceResponse.getCategyBeanList();
			
			for(int count = 0 ; count < categyList.size() ; count++){
			    CategoryBean categyBean = (CategoryBean) categyList.get(count);
			    FormatterDelegate.format(categyBean, null, IFormatter.FORMAT_TYPE_OUTPUT);
			}
	        return serviceResponse;
		} catch (DBException dbe)
        {
            log.error(" DBException in ViewAdminMCASetupDelegate.processViewAdminISDATemplateRequest() method ", dbe);
            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());

        } catch (Exception e)
        {
            log.error(" Exception in ViewAdminMCASetupDelegate.processViewAdminISDATemplateRequest() method ", e);
            throw new BusinessException("ViewAdminMCASetupDelegate", "Error occurred building database request.");
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
