package com.dtcc.dnv.mcx.delegate.mca;

import java.util.List;

import com.dtcc.dnv.mcx.beans.CategoryBean;
import com.dtcc.dnv.mcx.beans.RegionBean;
import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.dbhelper.mca.CategoryDbResponse;
import com.dtcc.dnv.mcx.dbhelper.mca.RegionDbResponse;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.TemplateDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.formatters.FormatterDelegate;
import com.dtcc.dnv.mcx.formatters.FormatterUtils;
import com.dtcc.dnv.mcx.formatters.IFormatter;
import com.dtcc.dnv.mcx.proxy.mca.CategoryListProxy;
import com.dtcc.dnv.mcx.proxy.mca.MCAGridProxy;
import com.dtcc.dnv.mcx.proxy.mca.RegionListProxy;
import com.dtcc.dnv.mcx.proxy.mca.TemplateInfoProxy;
import com.dtcc.dnv.mcx.proxy.mca.TemplateListProxy;
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

public class ViewMCADelegate extends MCXAbstractBusinessDelegate {

	private final static MessageLogger log = MessageLogger.getMessageLogger(ViewMCADelegate.class.getName());

	public IServiceResponse processRequest(IServiceRequest request)
			throws BusinessException 
	{

		TemplateServiceResponse serviceResponse = new TemplateServiceResponse();
		TemplateServiceRequest serviceRequest = (TemplateServiceRequest) request;

		TemplateDbRequest dbRequest = new TemplateDbRequest("", serviceRequest.getAuditInfo());

		try
		{
			new BuilderUtil().copyObject(serviceRequest,dbRequest);			
		}
		catch (Exception e)
		{
			throw new BusinessException("", e.getMessage());
	}

		//processDefaultViewMCARequest - Default MCA View in MCA Wizard
		if (serviceRequest.getSltInd().equalsIgnoreCase(MCXConstants.SLT_IND_MENU) ||
				serviceRequest.getSltInd().equalsIgnoreCase(MCXConstants.SLT_IND_ENR_APP) )
		{
			if(dbRequest.getTransaction() != null)
			{
				TemplateBean tempBean = (TemplateBean) dbRequest.getTransaction();
				tempBean.setDerPrdCd("");
				tempBean.setDerSubPrdCd("");
			}
			getRegionList(dbRequest, serviceResponse);
			getTemplateDetails(dbRequest, serviceResponse);
			
			if(!serviceResponse.isTmpltPresent())
				return serviceResponse;
			
			dbRequest.setTransaction(serviceResponse.getTransaction());			

			dbRequest.setRgnCd(((TemplateBean)serviceResponse.getTransaction()).getRgnCd());

			getTemplateLists(dbRequest, serviceResponse);
			getCategoryList(dbRequest, serviceResponse);
			getMCAGrid(dbRequest, serviceResponse);
		}
		//processProductViewMCARequest - when user selected particular product, fetch its MCA
		else if (serviceRequest.getSltInd().equalsIgnoreCase(MCXConstants.SLT_IND_PRODUCT))
		{
			getRegionList(dbRequest, serviceResponse);
			
			List rgnList = serviceResponse.getRgnList();
			int rgnListSize =0;
			RegionBean rgnBean = null;
			if(rgnList !=null ){
				rgnListSize= rgnList.size();
				for(int index=0; index < rgnListSize; index++){
					rgnBean = (RegionBean)rgnList.get(index);
					if(rgnBean.isTmpltPresent()){
						dbRequest.setRgnCd(rgnBean.getRegionCd());
						break;
					}
				}

		    }	 			
		    getTemplateLists(dbRequest, serviceResponse);
		    if(serviceResponse.isTmpltPresent())
		    {
		    ((TemplateBean)dbRequest.getTransaction()).setTmpltId(((TemplateBean)
		    		serviceResponse.getTmpltList().get(0)).getTmpltId());

		    getTemplateDetails(dbRequest, serviceResponse);
		    getCategoryList(dbRequest, serviceResponse);
		    getMCAGrid(dbRequest, serviceResponse);
		    }
			serviceResponse.setRgnList(null);

	}
		//processRegionViewMCARequest - when user Selected particular Region, fetch its MCA
		else if (serviceRequest.getSltInd().equalsIgnoreCase(MCXConstants.SLT_IND_REGION))
		{
			getTemplateLists(dbRequest, serviceResponse);
			if(serviceResponse.isTmpltPresent())
			{
			((TemplateBean)dbRequest.getTransaction()).setTmpltId(((TemplateBean)
					serviceResponse.getTmpltList().get(0)).getTmpltId());
			getTemplateDetails(dbRequest, serviceResponse);
			getCategoryList(dbRequest, serviceResponse);
			getMCAGrid(dbRequest, serviceResponse);
		}
		}
		//processTemplateViewMCARequest - Get given Template Details
		else if (serviceRequest.getSltInd().equalsIgnoreCase(MCXConstants.SLT_IND_TEMPLATE)||
				serviceRequest.getSltInd().equalsIgnoreCase(MCXConstants.SLT_IND_PRINT)	)     
		{
			if(dbRequest.getTransaction() != null)
			{
				TemplateBean tempBean = (TemplateBean) dbRequest.getTransaction();
				tempBean.setDerPrdCd("");
				tempBean.setDerSubPrdCd("");
			}
			getRegionList(dbRequest, serviceResponse);
			getTemplateDetails(dbRequest, serviceResponse);
			
			dbRequest.setTransaction(serviceResponse.getTransaction());

			getCategoryList(dbRequest, serviceResponse);
			getMCAGrid(dbRequest, serviceResponse);
		}
		//processCategoryViewMCARequest - when user selected Category in particular Template
		else if (serviceRequest.getSltInd().equalsIgnoreCase(MCXConstants.SLT_IND_CATEGORY) ||
				serviceRequest.getSltInd().equalsIgnoreCase(MCXConstants.SLT_IND_VIEW))
		{
			getTemplateDetails(dbRequest, serviceResponse);
			getMCAGrid(dbRequest, serviceResponse);
		}
		//processAmndCmntViewMCARequest - Amendment, Comment View MCA
		else if (serviceRequest.getSltInd().equalsIgnoreCase(MCXConstants.SLT_IND_AMD_CMNT))
		{
			getTemplateDetails(dbRequest, serviceResponse);
			dbRequest.setRgnCd(((TemplateBean)serviceResponse.getTransaction()).getRgnCd());
			getTemplateLists(dbRequest, serviceResponse);
			getMCAGrid(dbRequest, serviceResponse);
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

	/**
	 * Get the Template Details
	 * @param dbRequest
	 * @param serviceResponse
	 * @return
	 * @throws BusinessException
	 */
	private IServiceResponse getTemplateDetails(TemplateDbRequest dbRequest, 
			TemplateServiceResponse serviceResponse) throws BusinessException
	{
		TemplateDbResponse tmpltDbResponse = null;		
		TemplateInfoProxy templateProxy = new TemplateInfoProxy();
			
		if(serviceResponse.hasError())
			return serviceResponse;
			
		try
		{
			tmpltDbResponse = (TemplateDbResponse) templateProxy.processRequest(dbRequest);
			serviceResponse.setTransaction(tmpltDbResponse.getTransaction());
			if(((TemplateBean)tmpltDbResponse.getTransaction()).getTmpltId() == 0)
				serviceResponse.setTmpltPresent(false);
			processDbRequest(serviceResponse, tmpltDbResponse);
		}
		catch (DBException dbe) 
		{
			log.error(dbe);
			throw new BusinessException("", dbe.getMessage());
		}
			
		return serviceResponse;
	}

	/**
	 * Get the List of Templates for the given Region
	 * @param dbRequest
	 * @param serviceResponse
	 * @return
	 * @throws BusinessException
	 */
	private IServiceResponse getTemplateLists(TemplateDbRequest dbRequest, 
			TemplateServiceResponse serviceResponse) throws BusinessException
	{
		TemplateDbResponse tmpltDbResponse = null;
		TemplateListProxy templateListProxy = new TemplateListProxy();

		if(serviceResponse.hasError())
			return serviceResponse;
		
		try
		{
			tmpltDbResponse = (TemplateDbResponse) templateListProxy.processRequest(dbRequest);
			if(!tmpltDbResponse.getTmpltList().isEmpty())
			{
			serviceResponse.setTmpltList(tmpltDbResponse.getTmpltList());
			} 
			else
			{
				serviceResponse.setTmpltPresent(false);
			}			
			processDbRequest(serviceResponse, tmpltDbResponse);
		}
		catch (DBException dbe) 
		{
			log.error(dbe);
			throw new BusinessException("", dbe.getMessage());
	}
		return serviceResponse;
	}

	/**
	 * Get MCA Grid Details for the givem Template
	 * @param dbRequest
	 * @param serviceResponse
	 * @return
	 * @throws BusinessException
	 */
	private IServiceResponse getMCAGrid(TemplateDbRequest dbRequest, 
			TemplateServiceResponse serviceResponse) throws BusinessException
	{
		CategoryDbResponse categyDbResponse = null;
		MCAGridProxy gridProxy = new MCAGridProxy();
		FormatterUtils format = new FormatterUtils();

		if(serviceResponse.hasError())
			return serviceResponse;
			
		try
		{
			categyDbResponse = (CategoryDbResponse) gridProxy.processRequest(dbRequest);
			serviceResponse.setCategyBeanList(categyDbResponse.getCatgyList());
			processDbRequest(serviceResponse, categyDbResponse);
			List categyList = serviceResponse.getCategyBeanList();
			
			for(int count = 0 ; count < categyList.size() ; count++){
			    CategoryBean categyBean = (CategoryBean) categyList.get(count);
			    FormatterDelegate.format(categyBean, null, IFormatter.FORMAT_TYPE_OUTPUT);
			}
}
		catch (DBException dbe) 
		{
			log.error(dbe);
			throw new BusinessException("", dbe.getMessage());
	}
	return serviceResponse;
	}
			
	/**
	 * Get the List of Regions for the Product, SubProduct
	 * @param dbRequest
	 * @param serviceResponse
	 * @return
	 * @throws BusinessException
	 */
	private IServiceResponse getRegionList(TemplateDbRequest dbRequest, 
			TemplateServiceResponse serviceResponse) throws BusinessException
	{
		RegionDbResponse regionDbResponse = null;
		RegionListProxy regionListProxy = new RegionListProxy();
			
		if(serviceResponse.hasError())
			return serviceResponse;
			
		try
		{
			regionDbResponse = (RegionDbResponse) regionListProxy.processRequest(dbRequest);
			serviceResponse.setRgnList(regionDbResponse.getRgnList());
			processDbRequest(serviceResponse, regionDbResponse);
		}
		catch (DBException dbe) 
		{
			log.error(dbe);
			throw new BusinessException("", dbe.getMessage());
		}
			
		return serviceResponse;
	}
	
	/**
	 * Get the List of Categories present for the particular template
	 * @param dbRequest
	 * @param serviceResponse
	 * @return
	 * @throws BusinessException
	 */
	private IServiceResponse getCategoryList(TemplateDbRequest dbRequest, 
			TemplateServiceResponse serviceResponse) throws BusinessException
	{
		CategoryDbResponse categyListDbResponse = null;
		CategoryListProxy cateListProxy = new CategoryListProxy();

		if(serviceResponse.hasError())
			return serviceResponse;

		try
		{
			categyListDbResponse = (CategoryDbResponse) cateListProxy.processRequest(dbRequest);
			serviceResponse.setCategyList(categyListDbResponse.getCatgyList());
			processDbRequest(serviceResponse, categyListDbResponse);
		}
		catch (DBException dbe) 
		{
			log.error(dbe);
			throw new BusinessException("", dbe.getMessage());
	}	
		return serviceResponse;
	}	

}
