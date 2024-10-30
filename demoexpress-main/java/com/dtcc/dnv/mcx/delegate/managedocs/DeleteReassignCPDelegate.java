package com.dtcc.dnv.mcx.delegate.managedocs;

import com.dtcc.dnv.mcx.beans.ManageDocsTransactionBean;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbRequest;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.proxy.managedocs.DealerClientListProxy;
import com.dtcc.dnv.mcx.proxy.managedocs.DeleteReassignCPProxy;
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
 * The delegate class to delete the cps and reassign the documents to another Cp if present
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
 * @date Sep 14, 2007
 * @version 1.0
 * 
 *  
 */

public class DeleteReassignCPDelegate extends MCXAbstractBusinessDelegate
{
    
    private final static MessageLogger log = MessageLogger.getMessageLogger(DeleteReassignCPDelegate.class.getName());

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
        final String REQUEST_ID = "DPMXDDRO";
        final String REQUEST_ID_DEALERlIST = "DPMXDORG";
        final String DULICATES = "D";
        DealerClientListDbRequest dbRequest = new DealerClientListDbRequest(REQUEST_ID, serviceRequest.getAuditInfo());
       
        try
        {
            log.info("In DeleteReassignCPDelegate");
            serviceResponse.setDocumentInd(serviceRequest.getDocumentInd());
            transactionBean = (ManageDocsTransactionBean) serviceRequest.getTransaction();
            
            
            if (transactionBean != null)
            {

               
                DbRequestBuilder.copyObject(serviceRequest, dbRequest);
                if (MCXConstants.DEFAULTVALUE.equals(transactionBean.getReassignedDealerClient()))
                {
                    transactionBean.setReassignedDealerClient(MCXConstants.EMPTY_SPACE);
                }
                DeleteReassignCPProxy dbProxy = new DeleteReassignCPProxy();
                DealerClientListDbResponse dbResponse = (DealerClientListDbResponse) dbProxy.processRequest(dbRequest);
                processDbRequest(serviceResponse, dbResponse);
                DbRequestBuilder.copyObject(dbResponse, serviceResponse);
                //To fetch the dealers list if there are duplicates or an valid business exception.
                if(serviceResponse.hasError() || DULICATES.equals(serviceResponse.getDocumentInd())){
                    getDealerClients(dbRequest,serviceRequest,serviceResponse);
                }

            }
            
            log.info("Returning from DeleteReassignCPDelegate");
        } catch (DBException dbe)
        {
            try {
                getDealerClients(dbRequest,serviceRequest,serviceResponse);
            }catch (Exception e){ }
            log.error(" DBException in DeleteReassignCPDelegate.processRequest() method ", dbe);
            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());
        } catch (Exception e)
        {
            try {
                getDealerClients(dbRequest,serviceRequest,serviceResponse);
            }catch (Exception ex){ }
            log.error(" Exception in DeleteReassignCPDelegate.processRequest() method ", e);
            throw new BusinessException("DeleteReassignCPDelegate", "Error occurred building database request.");
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
    
//  To fetch the dealers list if there are duplicates or exception.
    public void getDealerClients(DealerClientListDbRequest dbRequest,ManageDocsServiceRequest serviceRequest, ManageDocsServiceResponse serviceResponse) throws Exception
    {
        final String REQUEST_ID_DEALERlIST = "DPMXDORG";
        dbRequest = new DealerClientListDbRequest(REQUEST_ID_DEALERlIST, serviceRequest.getAuditInfo());

        DbRequestBuilder.copyObject(serviceRequest, dbRequest);

        DealerClientListProxy dbProxyDealerList = new DealerClientListProxy();
        DealerClientListDbResponse dbResponse = (DealerClientListDbResponse) dbProxyDealerList.processRequest(dbRequest);
        serviceResponse.setDealerClient(dbResponse.getDealerClientList());
        serviceRequest.setDealerClient(dbResponse.getDealerClientList());
    }
}
