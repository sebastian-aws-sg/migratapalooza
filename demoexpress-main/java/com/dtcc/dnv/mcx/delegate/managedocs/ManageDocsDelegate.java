package com.dtcc.dnv.mcx.delegate.managedocs;

import java.util.ArrayList;
import java.util.List;

import com.dtcc.dnv.mcx.beans.TemplateDocsBean;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbRequest;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbResponse;
import com.dtcc.dnv.mcx.dbhelper.managedocs.SearchDbRequest;
import com.dtcc.dnv.mcx.dbhelper.managedocs.SearchDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.formatters.FormatterDelegate;
import com.dtcc.dnv.mcx.formatters.IFormatter;
import com.dtcc.dnv.mcx.proxy.managedocs.DealerClientListProxy;
import com.dtcc.dnv.mcx.proxy.managedocs.ISDATemplateListProxy;
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
 * RTM Reference : 3.3.17.1 Listing of PreExisting documents and drop down of
 * CounterParties RTM Reference : 3.3.18.1 Listing of Other documents alone and
 * drop down of CounterParties RTM Reference : 3.3.17.7,3.3.18.15 There is no
 * ALL option in the counterParty dropdown RTM Reference : 3.3.17.34,3.3.18.34
 * It should be possible for a user to differentiate between a manually
 * added/renamed counterparty and the MCA Xpress registered member firm
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

public class ManageDocsDelegate extends MCXAbstractBusinessDelegate
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(SearchProxy.class.getName());
    

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
     */
    public IServiceResponse processRequest(IServiceRequest request) throws BusinessException, UserException
    {
      

        ManageDocsServiceResponse serviceResponse = new ManageDocsServiceResponse();
        ManageDocsServiceRequest serviceRequest = (ManageDocsServiceRequest) request;

        List dealerClientDetails = new ArrayList();

        final String REQUEST_ID = "DPMXDORG";
        final String REQUEST_ID_SEARCH = "DPMXDGTD";
        List firmList = new ArrayList();
        List isdaList =  new ArrayList();
        List isdaDetailsList = new ArrayList();
        List firmListIndicator = new ArrayList();
        final String SPACE = "&nbsp;";
        final String DELIMITER = "-";
        //String cmpnyId ="";
        String cmpnyName = "";

        try
        {
            log.info("In ManageDocsDelegate");

            //To get the counterParty list.
            DealerClientListDbRequest dbRequest = new DealerClientListDbRequest(REQUEST_ID, serviceRequest.getAuditInfo());

            DbRequestBuilder.copyObject(serviceRequest, dbRequest);

            DealerClientListProxy dbProxy = new DealerClientListProxy();
            DealerClientListDbResponse dbResponse = (DealerClientListDbResponse) dbProxy.processRequest(dbRequest);
            processDbRequest(serviceResponse,dbResponse);
           
            TemplateDocsBean dealerClient = new TemplateDocsBean();
            firmList = dbResponse.getDealerClientList();
            //cmpnyId = serviceRequest.getNewCmpnyId();
            //To add indicator for the nonMCAexpress CounterParties.
            for (int index = 0; index < firmList.size(); index++)
            {
                dealerClient = new TemplateDocsBean();
                dealerClient = (TemplateDocsBean) firmList.get(index);
                
                /*if(dealerClient.getOrgCltCd().equals(cmpnyId))
                {
                   //Used for displaying the CP which is added or edited after succesful operation 
                    cmpnyName = dealerClient.getOrgCltNm().trim()+DELIMITER+dealerClient.getDocumentInd().trim();
                }*/
                //Appending the name for EditCP and Doc Indicator for delete CP
                //dealerClient.setOrgCltCd(dealerClient.getOrgCltCd()+DELIMITER+dealerClient.getOrgCltNm().trim()+DELIMITER+dealerClient.getDocumentInd().trim());
                FormatterDelegate.format(dealerClient, null, IFormatter.FORMAT_TYPE_OUTPUT);
                if (MCXConstants.NOTMCAFIRM.equals(dealerClient.getMcaRegisteredIndicator()))
                {
                    dealerClient.setOrgCltNm(dealerClient.getOrgCltNm().trim() + MCXConstants.MCAINDICATOR);
                }
              
                firmListIndicator.add(dealerClient);

            }
            serviceResponse.setDealerClient(firmListIndicator);
           
            
            // To fetch the isda templates
            DealerClientListDbRequest isdaTemplateListDbRequest = new DealerClientListDbRequest(REQUEST_ID_SEARCH, serviceRequest.getAuditInfo());   
            ISDATemplateListProxy isdaTemplateListDbProxy = new ISDATemplateListProxy();
            DealerClientListDbResponse isdaTemplateListDbResponse = (DealerClientListDbResponse) isdaTemplateListDbProxy.processRequest(isdaTemplateListDbRequest);

            processDbRequest(serviceResponse,isdaTemplateListDbResponse);
            DbRequestBuilder.copyObject(isdaTemplateListDbResponse, serviceResponse);
            

            // To get the document library.
            SearchDbRequest searchDbRequest = new SearchDbRequest(REQUEST_ID_SEARCH, serviceRequest.getAuditInfo());
            
            DbRequestBuilder.copyObject(serviceRequest, searchDbRequest);

            searchDbRequest.setCompanyId(serviceRequest.getCmpnyId());
            

            SearchProxy searchDbProxy = new SearchProxy();
            SearchDbResponse searchDbResponse = (SearchDbResponse) searchDbProxy.processRequest(searchDbRequest);
            processDbRequest(serviceResponse,searchDbResponse);

            DbRequestBuilder.copyObject(searchDbResponse, serviceResponse);
            serviceResponse.setDocName(cmpnyName);
            

            log.info("Returning from manageDocsDelegate");

        } catch (DBException dbe)
        {
            log.error(" DBException in ManageDocsDelegate.processRequest() method ", dbe);
            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());

        } catch (Exception e)
        {
            log.error(" Exception in ManageDocsDelegate.processRequest() method ", e);
            throw new BusinessException("ManageDocsDelegate", "Error occurred building database request.");

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
