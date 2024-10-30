package com.dtcc.dnv.mcx.delegate.enroll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dtcc.dnv.mcx.beans.DealerList;
import com.dtcc.dnv.mcx.dbhelper.enroll.DealerListDbRequest;
import com.dtcc.dnv.mcx.dbhelper.enroll.DealerListDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.formatters.FormatterDelegate;
import com.dtcc.dnv.mcx.formatters.IFormatter;
import com.dtcc.dnv.mcx.proxy.enroll.DealerListProxy;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.builders.DbRequestBuilder;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;

/**
 * This class is used as a delegate to retrive List of Dealer details
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
public class DealerListDelegate extends MCXAbstractBusinessDelegate
{

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
     */
    public IServiceResponse processRequest(IServiceRequest request) throws BusinessException, UserException
    {
    	MessageLogger log = MessageLogger.getMessageLogger(DealerListDelegate.class.getName());
    	//Logger log = Logger.getLogger(DealerListDelegate.class);
        DealerListServiceResponse serviceResponse = new DealerListServiceResponse();
        DealerListServiceRequest serviceRequest = (DealerListServiceRequest) request;
        final String REQUEST_ID = "DPMXDLRD";
        try
        {
            DealerListDbRequest dbRequest = new DealerListDbRequest(REQUEST_ID, serviceRequest.getAuditInfo());
            DbRequestBuilder.copyObject(serviceRequest,dbRequest);
            
            DealerListProxy dbProxy = new DealerListProxy();
            DealerListDbResponse dbResponse = (DealerListDbResponse) dbProxy.processRequest(dbRequest);
            processDbRequest(serviceResponse,dbResponse);
            
            // To format the dealer name.Replace the space with &nbsp;
            List fedDealerList = dbResponse.getFedDealerList();
            List otherDealerList = dbResponse.getOtherDealerList(); 
            ArrayList fedDealerListFormtd = new ArrayList();
            ArrayList otherDealerListFormtd = new ArrayList();            
            HashMap dealerMap = new HashMap();
            String dealerName = null;
            DealerList dealerList = null;
            for ( int index=0;index < fedDealerList.size();index++){
                dealerList = (DealerList)fedDealerList.get(index);
                FormatterDelegate.format(dealerList, null, IFormatter.FORMAT_TYPE_OUTPUT);
                fedDealerListFormtd.add(dealerList);
                dealerMap.put(dealerList.getDealerCode(),dealerList);
            }
            
            for ( int index=0;index < otherDealerList.size();index++){
                dealerList = (DealerList)otherDealerList.get(index);
                FormatterDelegate.format(dealerList, null, IFormatter.FORMAT_TYPE_OUTPUT);
                otherDealerListFormtd.add(dealerList);
                dealerMap.put(dealerList.getDealerCode(),dealerList);
            }
            
            serviceResponse.setFedDealerList(fedDealerListFormtd);
            serviceResponse.setOtherdealerList(otherDealerListFormtd);
            serviceResponse.setDealerMap(dealerMap);
            
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
