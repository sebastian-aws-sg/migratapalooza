/*
 * Created on Oct 23, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.delegate.home;

import com.dtcc.dnv.mcx.dbhelper.home.UserDetailsDbRequest;
import com.dtcc.dnv.mcx.dbhelper.home.UserDetailsDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.formatters.FormatterDelegate;
import com.dtcc.dnv.mcx.formatters.IFormatter;
import com.dtcc.dnv.mcx.proxy.home.UserDetailsProxy;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.builders.DbRequestBuilder;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;

/**
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
 * @date Oct 23, 2007
 * @version 1.0
 * 
 * This class is used to delegate the service request to the db request for
 * getting the user details.
 * 
 */

public class UserDetailsDelegate extends MCXAbstractBusinessDelegate
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(UserDetailsDelegate.class.getName());

    /* (non-Javadoc)
     * @see com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate#processDbRequest(com.dtcc.dnv.otc.common.layers.IServiceResponse, com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse) throws BusinessException
    {
        processSPReturnCode(serviceResponse, dbResponse);

    }

    /* (non-Javadoc)
     * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
     */
    public IServiceResponse processRequest(IServiceRequest request) throws BusinessException, UserException
    {

        final String REQUEST_ID = "DPMXHUSD";
        log.info("inside User Details delegate");
        UserDetailsServiceResponse serviceResponse = new UserDetailsServiceResponse();
        UserDetailsServiceRequest serviceRequest = (UserDetailsServiceRequest) request;
        UserDetailsDbResponse dbResponse = new UserDetailsDbResponse();
        try
        {

            UserDetailsDbRequest dbRequest = new UserDetailsDbRequest(REQUEST_ID, serviceRequest.getAuditInfo());
            //copying the values from service request to DbRequest
            DbRequestBuilder.copyObject(serviceRequest, dbRequest);

            //calling proxy's processrequest method.

            UserDetailsProxy proxy = new UserDetailsProxy();
            dbResponse = (UserDetailsDbResponse) proxy.processRequest(dbRequest);
            processDbRequest(serviceResponse, dbResponse);
            //getting details from dbresponse and setting it in service
            // response.

            DbRequestBuilder.copyObject(dbResponse, serviceResponse);
            FormatterDelegate.format(serviceResponse.getUserDetails(),null,IFormatter.FORMAT_TYPE_OUTPUT);
        } catch (DBException dbe)
        {
            log.error(dbe);
            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());
        } catch (Exception e)
        {
            log.error(" Exception in userdetailsdelegate.processRequest() method ", e);
            throw new BusinessException("UserDetailsDelegate", "Error occurred building database request.");

        }
        return serviceResponse;

    }

}
