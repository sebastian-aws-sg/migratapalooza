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
 * The delegate for Search functionality.
 * 
 * RTM Reference : 3.3.16 Basic Flow (Except delete documents) Searching for the
 * documents based on the parameters Dealer/Client Id,Document type, Document
 * Name. Displays Dealer/ClientId MCA/File name,Document type, Execution date,CP
 * Viewable,uploaded by user,uploaded on date that satisfy the search criteria.
 * RTM Reference : 3.3.16.1 Actor wants to search for “Other documents” RTM
 * Reference : 3.3.16.2 Actor wants to search for “All” documents against “All”
 * counterparties RTM Reference : 3.3.16.3 Actor wants to search for “All”
 * documents against specific counterparty
 * 
 * RTM Reference : 3.3.16.12 The document library tab under the “search
 * documents” should display the file name if the documents uploaded is anything
 * other than pre-executed MCA RTM Reference : 3.3.16.13 The document library
 * tab under the “search documents” should display the MCA name if the documents
 * uploaded is a pre-executed MCA RTM Reference : 3.3.16.15 Execution Date
 * should be available only in case of Pre-Exec documents and it should be blank
 * for “other documents” RTM Reference : 3.3.16.16 The “MCA name” should not
 * have a link if it is just a placeholder and there is no document uploaded for
 * a pre-existing MCA Copyright © 2003 The Depository Trust & Clearing Company.
 * All rights reserved.
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
 * @date Sep 6, 2007
 * @version 1.0
 * 
 *  
 */
public class SearchDelegate extends MCXAbstractBusinessDelegate
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(SearchDelegate.class.getName());
    // private static final Logger log = Logger.getLogger(SearchDelegate.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
     */
    public IServiceResponse processRequest(IServiceRequest request) throws BusinessException, UserException
    {
        

        SearchServiceResponse serviceResponse = new SearchServiceResponse();
        SearchServiceRequest serviceRequest = (SearchServiceRequest) request;
        final String REQUEST_ID = "DPMXDGTD";
        final String REQUEST_ID_COUNTERPARTY = "DPMXDORG";
        List dealerClientDetails = new ArrayList();
        List dealerClientDetailsDisplay = new ArrayList();
        //DealerClientList dealerClient = new DealerClientList();
        
        List firmList = new ArrayList();
        List firmListIndicator = new ArrayList();

        try
        {
            
            DealerClientListDbRequest dbRequest_CounterParty = new DealerClientListDbRequest(REQUEST_ID_COUNTERPARTY, serviceRequest.getAuditInfo());

            DbRequestBuilder.copyObject(serviceRequest, dbRequest_CounterParty);
            dbRequest_CounterParty.setCmpnyId(serviceRequest.getCompanyId());

            DealerClientListProxy dbProxy_CounterParty = new DealerClientListProxy();
            DealerClientListDbResponse dbResponse_CounterParty = (DealerClientListDbResponse) dbProxy_CounterParty.processRequest(dbRequest_CounterParty);
            processDbRequest(serviceResponse,dbResponse_CounterParty);
           
            TemplateDocsBean dealerClient = new TemplateDocsBean();
            firmList = dbResponse_CounterParty.getDealerClientList();
            
            //To add indicator for the nonMCAexpress CounterParties.
            for (int index = 0; index < firmList.size(); index++)
            {
                dealerClient = new TemplateDocsBean();
                dealerClient = (TemplateDocsBean) firmList.get(index);
              
                FormatterDelegate.format(dealerClient, null, IFormatter.FORMAT_TYPE_OUTPUT);
                if (MCXConstants.NOTMCAFIRM.equals(dealerClient.getMcaRegisteredIndicator()))
                {
                    dealerClient.setOrgCltNm(dealerClient.getOrgCltNm().trim() + MCXConstants.MCAINDICATOR);
                }
              
                firmListIndicator.add(dealerClient);

            }
            serviceResponse.setDealerClient(firmListIndicator);
            
            
            
            
            log.info("In SearchDelegate");
            SearchDbRequest dbRequest = new SearchDbRequest(REQUEST_ID, serviceRequest.getAuditInfo());

            DbRequestBuilder.copyObject(serviceRequest, dbRequest);
            

            SearchProxy dbProxy = new SearchProxy();

            SearchDbResponse dbResponse = (SearchDbResponse) dbProxy.processRequest(dbRequest);
            processDbRequest(serviceResponse,dbResponse);
            dealerClientDetails = dbResponse.getDealerClientDetails();

            serviceResponse.setDealerClientDetails(dbResponse.getDealerClientDetails());
            log.info("Returning from search delegate");

        } catch (DBException dbe)
        {
            log.error(" DBException in SearchDelegate.processRequest() method ", dbe);
            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());

        } catch (Exception e)
        {
            log.error(" Exception in SearchDelegate.processRequest() method ", e);
            throw new BusinessException("SearchDelegate", "Error occurred building database request.");

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
