package com.dtcc.dnv.mcx.delegate.managedocs;

import java.util.ArrayList;
import java.util.List;

import com.dtcc.dnv.mcx.beans.ManageDocsTransactionBean;
import com.dtcc.dnv.mcx.beans.TemplateDocsBean;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbRequest;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbResponse;
import com.dtcc.dnv.mcx.dbhelper.managedocs.SearchDbRequest;
import com.dtcc.dnv.mcx.dbhelper.managedocs.SearchDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.formatters.FormatterDelegate;
import com.dtcc.dnv.mcx.formatters.IFormatter;
import com.dtcc.dnv.mcx.proxy.managedocs.DealerClientListProxy;
import com.dtcc.dnv.mcx.proxy.managedocs.SearchProxy;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.builders.DbRequestBuilder;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;

/**
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
 * @author Ravikanth G
 * @date Sep 14, 2007
 * @version 1.0
 * 
 *  
 */

public class ReassignListDelegate extends MCXAbstractBusinessDelegate
{
    
    private final static MessageLogger log = MessageLogger.getMessageLogger(ReassignListDelegate.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
     */
    public IServiceResponse processRequest(IServiceRequest request) throws BusinessException, UserException
    {
        ManageDocsServiceResponse serviceResponse = new ManageDocsServiceResponse();
        ManageDocsServiceRequest serviceRequest = (ManageDocsServiceRequest) request;
        ManageDocsTransactionBean transactionBean = new ManageDocsTransactionBean();

        List dealerClientDetails = new ArrayList();

        final String REQUEST_ID = "DPMXDORG";
        List firmList = new ArrayList();
        List firmListIndicator = new ArrayList();
        final String NO = "N";
        final String YES = "Y";

        try
        {
            log.info("In ReassignListDelegate");

            //To get the counterParty list.
            DealerClientListDbRequest dbRequest = new DealerClientListDbRequest(REQUEST_ID, serviceRequest.getAuditInfo());

            DbRequestBuilder.copyObject(serviceRequest, dbRequest);

            DealerClientListProxy dbProxy = new DealerClientListProxy();
            DealerClientListDbResponse dbResponse = (DealerClientListDbResponse) dbProxy.processRequest(dbRequest);
            processDbRequest(serviceResponse,dbResponse);

            TemplateDocsBean dealerClient = new TemplateDocsBean();

            firmList = dbResponse.getDealerClientList();

            //To add indicator for the nonMCAexpress CounterParties.
            for (int index = 0; index < firmList.size(); index++)
            {
                dealerClient = new TemplateDocsBean();
                dealerClient = (TemplateDocsBean) firmList.get(index);
                FormatterDelegate.format(dealerClient, null, IFormatter.FORMAT_TYPE_OUTPUT);
                if (MCXConstants.NOTMCAFIRM.equals(dealerClient.getMcaRegisteredIndicator()))
                {
                    dealerClient.setOrgCltNm(dealerClient.getOrgCltNm() + MCXConstants.MCAINDICATOR);
                }
                firmListIndicator.add(dealerClient);

            }
            serviceResponse.setDealerClient(firmListIndicator);
            //serviceResponse.setDocumentInd(serviceRequest.getDocumentInd());
            
                SearchDbRequest searchDbRequest = new SearchDbRequest(REQUEST_ID, serviceRequest.getAuditInfo());
                searchDbRequest.setCompanyId(serviceRequest.getCmpnyId());
               
                
                transactionBean = (ManageDocsTransactionBean) serviceRequest.getTransaction();
                if(transactionBean !=null){
                    
                    transactionBean.setSelectedDealerClient(transactionBean.getDeleteCmpnyId());
                    transactionBean.setSelectedDocumentType(MCXConstants.EMPTY_SPACE);
                    transactionBean.setDocName(MCXConstants.EMPTY_SPACE);
                    
                    SearchProxy searchDbProxy = new SearchProxy();
                    
                    DbRequestBuilder.copyObject(serviceRequest, searchDbRequest);

                    SearchDbResponse searchDbResponse = (SearchDbResponse) searchDbProxy.processRequest(searchDbRequest);
                    processDbRequest(serviceResponse,searchDbResponse);
                    List docList = searchDbResponse.getDealerClientDetails();
                    if (docList.size() > 0)
                    {
                        serviceResponse.setDocumentInd(YES);
                    }else
                    {
                        serviceResponse.setDocumentInd(NO);
                    }
                    
                    
                }
                
                
           
 
            log.info("Returning from ReassignListDelegate");

        } catch (DBException dbe)
        {
            log.error(" DBException in ReassignListDelegate.processRequest() method ", dbe);
            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());

        } catch (Exception e)
        {
            log.error(" Exception in ReassignListDelegate.processRequest() method ", e);
            throw new BusinessException("ReassignListDelegate", "Error occurred building database request.");

        }

        return serviceResponse;
    }

    /* (non-Javadoc)
     * @see com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate#processDbRequest(com.dtcc.dnv.otc.common.layers.IServiceResponse, com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse) throws BusinessException
    {
        processSPReturnCode(serviceResponse,dbResponse);
        
    }

}
