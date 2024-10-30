package com.dtcc.dnv.mcx.delegate.enroll;

import com.dtcc.dnv.mcx.dbhelper.enroll.SelectMCADbRequest;
import com.dtcc.dnv.mcx.dbhelper.enroll.SelectMCADbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.proxy.enroll.SelectMCAProxy;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.builders.DbRequestBuilder;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;

/**
 * This class is used as a delegate to retrive List of MCA details
 * 
 * RTM Reference : 3.3.3.1 To create an enrolment request, actor is directed to the enrolment list of dealers, selection of MCAs throuhg enrolment grid and the enrolment confirmation screen
 * RTM Reference : 3.3.3.2 Actor chooses to select a another MCA for enrolment after the previous MCA is approved by dealer
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
 * @author Prabhu K
 * @date Sep 3, 2007
 * @version 1.0
 * 
 *  
 */
public class SelectMCADelegate extends MCXAbstractBusinessDelegate
{
    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
     */
    public IServiceResponse processRequest(IServiceRequest request) throws BusinessException, UserException
    {
        MessageLogger log = MessageLogger.getMessageLogger(SelectMCADelegate.class.getName());
        SelectMCAServiceResponse serviceResponse = new SelectMCAServiceResponse();
        SelectMCAServiceRequest serviceRequest = (SelectMCAServiceRequest) request;
        final String REQUEST_ID = "DPMXESEL";
        try
        {
            log.info("Entering into SelectMCADelegate");
            SelectMCADbRequest dbRequest = new SelectMCADbRequest(REQUEST_ID, serviceRequest.getAuditInfo());
            DbRequestBuilder.copyObject(serviceRequest,dbRequest);
            SelectMCAProxy dbProxy = new SelectMCAProxy();
            SelectMCADbResponse dbResponse = (SelectMCADbResponse) dbProxy.processRequest(dbRequest);
            processDbRequest(serviceResponse,dbResponse);
            serviceResponse.setProductList(dbResponse.getProductList());
            serviceResponse.setRegionList(dbResponse.getRegionList());
            serviceResponse.setProductRegionMap(dbResponse.getProductRegionMap());
            log.info("Exiting from SelectMCADelegate");

        } catch (DBException dbe)
        {
            log.error(" DBException in DealerListDelegate.processRequest() method ", dbe);
            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());

        } catch (Exception e)
        {
            log.error(" Exception in DealerListDelegate.processRequest() method ", e);
            throw new BusinessException("DealerListDelegate", "Error occurred building database request.");
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
