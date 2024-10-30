package com.dtcc.dnv.mcx.delegate.home;

import com.dtcc.dnv.mcx.dbhelper.home.DisplayMCAsDbRequest;
import com.dtcc.dnv.mcx.dbhelper.home.DisplayMCAsDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.proxy.home.DisplayMCAsProxy;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.builders.DbRequestBuilder;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;

/**
 * This class is used to delegate the service request to the db request for
 * getting the pending mca details.
 *
 * RTM References: 
 * 3.3.6.1 To view the executed MCA from the executed MCA tab
 * and actor clicks the MCA type to view the detailed fields of the document
 * 3.3.6.15 Client users should only be able to view the records in executed
 * MCAs tab irrespective of their read/write privileges 
 * 3.3.14.1 The actor clicks the executed MCAs tab System would show the executed MCA records in
 * the executed MCA tab
 * 3.3.14.16 Pre-executed MCAs uploaded by the dealer should be viewable only by the dealer users on the executed MCAs tab and not
 * by the client users in their executed MCAs tab
 * 3.3.5.13 Actor chooses to view the "Executed MCAs" tab

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
 * @author Nithya R
 * @date Sep 10, 2007
 * @version 1.0
 *  
 */
public class DisplayMCAsDelegate extends MCXAbstractBusinessDelegate
{

    private final static MessageLogger log = MessageLogger.getMessageLogger(DisplayMCAsDelegate.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
     */
    public IServiceResponse processRequest(IServiceRequest request) throws BusinessException, UserException
    {

        final String REQUEST_ID = "DPMXHEXP";
        log.info("inside DisplayMCAsDelegate delegate");
        DisplayMCAsServiceRequest serviceRequest = (DisplayMCAsServiceRequest) request;
        DisplayMCAsServiceResponse serviceResponse = new DisplayMCAsServiceResponse();
        DisplayMCAsDbResponse dbResponse = new DisplayMCAsDbResponse();
        try
        {

            DisplayMCAsDbRequest dbRequest = new DisplayMCAsDbRequest(REQUEST_ID, serviceRequest.getAuditInfo());
            /*
             * The input values are copied from the service request to the DbRequest.
             */
            DbRequestBuilder.copyObject(serviceRequest, dbRequest);

            /*
             * DbRequest is passed as a parameter to the proxy's processrequest method.
             */

            DisplayMCAsProxy proxy = new DisplayMCAsProxy();
            dbResponse = (DisplayMCAsDbResponse) proxy.processRequest(dbRequest);
            processDbRequest(serviceResponse,dbResponse);
            /*
             * The details fetched from the SP are copied from the DbResponse to the serviceResponse.
             */

            DbRequestBuilder.copyObject(dbResponse, serviceResponse);
        } catch (DBException dbe)
        {
            log.error(dbe);
            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());
        } catch (Exception e)
        {
            log.error(" Exception in DisplayMCAsDelegate.processRequest() method ", e);
            throw new BusinessException("DisplayMCAsDelegate", "Error occurred building database request.");

        }
        return serviceResponse;

    }

    public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse) throws BusinessException
    {
        processSPReturnCode(serviceResponse, dbResponse);

    }

}
