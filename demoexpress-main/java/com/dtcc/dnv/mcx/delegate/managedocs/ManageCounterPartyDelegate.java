package com.dtcc.dnv.mcx.delegate.managedocs;

import com.dtcc.dnv.mcx.dbhelper.managedocs.ManageCounterPartyDbRequest;
import com.dtcc.dnv.mcx.dbhelper.managedocs.ManageCounterPartyDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.proxy.managedocs.ManageCounterPartyProxy;
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
 * RTM Reference : 3.3.17.7, 3.3.18.7 Actor wants to manually add a counterparty
 * name who does not exist in the MCA Xpress as member firm
 * 
 * RTM Reference : 3.3.17.8, 3.3.18.8 Actor wants to rename a manually added
 * counterparty name RTM Reference : 3.3.17.31, 3.3.18.19 Only a user with
 * read/write access should be able to add, rename, delete a counterparty RTM
 * Reference : 3.3.18.29 Only one new manual upload counterparty should be
 * re-named at a time
 * 
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
 * @date Sep 10, 2007
 * @version 1.0
 * 
 *  
 */

public class ManageCounterPartyDelegate extends MCXAbstractBusinessDelegate
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(ManageCounterPartyDelegate.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
     */
    public IServiceResponse processRequest(IServiceRequest request) throws BusinessException, UserException
    {
       
        ManageCounterPartyServiceResponse serviceResponse = new ManageCounterPartyServiceResponse();
        ManageCounterPartyServiceRequest serviceRequest = (ManageCounterPartyServiceRequest) request;
        final String REQUEST_ID = "DPMXDODO";

        try
        {
            log.info("In ManageCounterPartyDelegate");
            ManageCounterPartyDbRequest dbRequest = new ManageCounterPartyDbRequest(REQUEST_ID, serviceRequest.getAuditInfo());
            
            DbRequestBuilder.copyObject(serviceRequest,dbRequest);
               
           
            ManageCounterPartyProxy dbProxy = new ManageCounterPartyProxy();
            ManageCounterPartyDbResponse dbResponse = (ManageCounterPartyDbResponse) dbProxy.processRequest(dbRequest);
            processDbRequest(serviceResponse,dbResponse);
            DbRequestBuilder.copyObject(dbResponse,serviceResponse);
            
           log.info("Returning from ManageCounterPartyDelegate");
        } catch (DBException dbe)
        {

            log.error(" DBException in ManageCounterPartyDelegate.processRequest() method ", dbe);
            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());

        } catch (Exception e)
        {
            log.error(" Exception in ManageCounterPartyDelegate.processRequest() method ", e);
            throw new BusinessException("ManageCounterPartyDelegate", "Error occurred building database request.");

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
