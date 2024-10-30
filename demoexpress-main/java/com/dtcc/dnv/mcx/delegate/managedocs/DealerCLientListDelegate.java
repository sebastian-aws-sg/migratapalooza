package com.dtcc.dnv.mcx.delegate.managedocs;

import java.util.ArrayList;
import java.util.List;

import com.dtcc.dnv.mcx.beans.TemplateDocsBean;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbRequest;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.formatters.FormatterDelegate;
import com.dtcc.dnv.mcx.formatters.IFormatter;
import com.dtcc.dnv.mcx.proxy.managedocs.DealerClientListProxy;
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
 * The delegate class for fetching dealers list.
 * 
 * 
 * RTM Reference : 3.3.17.34,3.3.18.34 It should be possible for a user to
 * differentiate between a manually added/renamed counterparty and the MCA
 * Xpress registered member firm
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
 * @date Sep 6, 2007
 * @version 1.0
 * 
 *  
 */
public class DealerCLientListDelegate extends MCXAbstractBusinessDelegate
{
    
    private final static MessageLogger log = MessageLogger.getMessageLogger(DealerCLientListDelegate.class.getName());

    public IServiceResponse processRequest(IServiceRequest request) throws BusinessException, UserException
    {
       
        
        log.info("In DealerCLientListDelegate ");

        DealerClientListServiceResponse serviceResponse = new DealerClientListServiceResponse();
        DealerClientListServiceRequest serviceRequest = (DealerClientListServiceRequest) request;
        final String SPACE = "&nbsp;";

        final String REQUEST_ID = "DPMXDORG";
        

        try
        {

            DealerClientListDbRequest dbRequest = new DealerClientListDbRequest(REQUEST_ID, serviceRequest.getAuditInfo());
            
            DbRequestBuilder.copyObject(serviceRequest,dbRequest);

            
            DealerClientListProxy dbProxy = new DealerClientListProxy();
            DealerClientListDbResponse dbResponse = (DealerClientListDbResponse) dbProxy.processRequest(dbRequest);
            processDbRequest(serviceResponse,dbResponse);

           
            TemplateDocsBean dealerClient = new TemplateDocsBean();
            
            List firmList = dbResponse.getDealerClientList();
            List firmListIndicator = new ArrayList();
            // Looping through the counter partys to add the indicator for non mca express names.
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

            serviceResponse.setDealerClientList(firmListIndicator);
            log.info("Returning from DealerCLientListDelegate");

        } catch (DBException dbe)
        {
            log.error(" DBException in DealerCLientListDelegate.processRequest() method ", dbe);
            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());

        } catch (Exception e)
        {
            log.error(" Exception in DealerCLientListDelegate.processRequest() method ", e);
            throw new BusinessException("DealerCLientListDelegate", "Error occurred building database request.");

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
