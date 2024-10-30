package com.dtcc.dnv.mcx.delegate.managedocs;

import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbRequest;
import com.dtcc.dnv.mcx.dbhelper.managedocs.DealerClientListDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.proxy.managedocs.UploadManageDocProxy;
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
 * @author Elango TR
 * @date Sep 14, 2007
 * @version 1.0
 * 
 *  
 */

public class UploadManageDocDelegate extends MCXAbstractBusinessDelegate
{
    
    private final static MessageLogger log = MessageLogger.getMessageLogger(UploadManageDocDelegate.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
     */
    public IServiceResponse processRequest(IServiceRequest request) throws BusinessException, UserException
    {
        ManageDocsServiceResponse serviceResponse = new ManageDocsServiceResponse();
        ManageDocsServiceRequest serviceRequest = (ManageDocsServiceRequest) request;
        final String REQUEST_ID = "DPMXDADC";
        try
        {
            log.info("In UploadManageDocDelegate");
            
            DealerClientListDbRequest dbRequest = new DealerClientListDbRequest(REQUEST_ID, serviceRequest.getAuditInfo());
            DbRequestBuilder.copyObject(serviceRequest, dbRequest);
            dbRequest.setUploadBean(serviceRequest.getUploadBean());
            UploadManageDocProxy dbProxy = new UploadManageDocProxy();
            DealerClientListDbResponse dbResponse = (DealerClientListDbResponse) dbProxy.processRequest(dbRequest);
            processDbRequest(serviceResponse,dbResponse);
            DbRequestBuilder.copyObject(dbResponse, serviceResponse);
            log.info("Returning from UploadManageDocDelegate");
        } catch (DBException dbe)
        {
            log.error("DBException in UploadManageDocDelegate.processRequest() method ", dbe);
            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());
        } catch (Exception e)
        {
            log.error("General Exception in UploadManageDocDelegate.processRequest() method ", e);
            throw new BusinessException("UploadManageDocDelegate", "Error occurred building database request.");
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
