package com.dtcc.dnv.mcx.delegate.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dtcc.dnv.mcx.beans.TemplateDocsBean;
import com.dtcc.dnv.mcx.dbhelper.home.PendingEnrollApprovalDbRequest;
import com.dtcc.dnv.mcx.dbhelper.home.PendingEnrollApprovalDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;

import com.dtcc.dnv.mcx.proxy.home.PendingApprovalProxy;
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
 * getting the data to load the Pending Enrollments Approval Screen and Approved
 * Firms Screen.
 * RTM Reference : 3.3.11.1 The actor views the pending Enrolment
 * approval tab for the respective MCA types against the clients awaiting
 * approval, approves a client and all the MCAs requested for by him 
 * RTM Reference : 3.3.11.2 Actor selects to approve only one or more but not all of
 * the MCA Enrolment requests for the client 
 * RTM Reference : 3.3.11.7 A Dealer should only be able to approve or deny Enrolment of a Client that has
 * initiated firm Enrolment RTM Reference : 3.3.11.12 Once a Client firm is
 * approved with at least one associated MCA(s), that Client should never be
 * removed from the Dealer’s ‘Approved Firms’ tab
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
 * @author Vigneshwari R
 * @date Sep 7, 2007
 * @version 1.0
 * 
 *  
 */
public class PendingApprovalDelegate extends MCXAbstractBusinessDelegate
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(PendingApprovalDelegate.class.getName());

    /*
     * 
     * This method is used to pass the request to the Dao to retrieve the values needed to populate the Pending 
     * Enrollments Approval and Approved Firms screen.
     * inside this method all the businesslogic needs to be done to pass
     * the values to the pending enrollment approval screen.
     * 
     * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
     */
    public IServiceResponse processRequest(IServiceRequest request) throws BusinessException, UserException
    {
        final String REQUEST_ID = "DPMXHAPE";
        PendingEnrollApprovalServiceResponse serviceResponse = new PendingEnrollApprovalServiceResponse();
        PendingEnrollApprovalServiceRequest serviceRequest = (PendingEnrollApprovalServiceRequest) request;
        try
        {
            log.info("Inside Pending Approval Delegate class");
            PendingEnrollApprovalDbRequest dbRequest = new PendingEnrollApprovalDbRequest(REQUEST_ID, serviceRequest.getAuditInfo());
            DbRequestBuilder.copyObject(serviceRequest, dbRequest);

            PendingApprovalProxy dbProxy = new PendingApprovalProxy();
            //dbRequest
            PendingEnrollApprovalDbResponse dbResponse = (PendingEnrollApprovalDbResponse) dbProxy.processRequest(dbRequest);
            processDbRequest(serviceResponse, dbResponse);
            List pendingApprovalList = dbResponse.getPendingEnrollApprovalList();
            serviceResponse.setDealerDetailsList(pendingApprovalList);
            serviceResponse.setStatus(dbResponse.getStatus());
            List dealerDetailsList = new ArrayList();
            List mcaList = null;
            Map dealerMcaMap = new HashMap();
            String prevDealerCode = "";
            String dealerCode = "";
            /*
             * If a CounterParty enrolls more than one MCA at a time against a particular dealer then in that dealer homepage
             * (Pending Enrollments Approval Screen) the details like "Counterparty","Requested By" and "Requested On" 
             * should be dispalyed as a single row but on clicking the approve or deny button all the 
             * mca's enrolled against that dealer by that counterparty at a particular time 
             * should be dispalyed.
             */
            for (int count = 0; count < pendingApprovalList.size(); count++)
            {
                TemplateDocsBean templateBean = new TemplateDocsBean();
                dealerCode = ((TemplateDocsBean) pendingApprovalList.get(count)).getOrgDlrCd() + ((TemplateDocsBean) pendingApprovalList.get(count)).getRowUpdtName()
                        + ((TemplateDocsBean) pendingApprovalList.get(count)).getModifiedTime();

                if (dealerCode.equals(prevDealerCode))
                {
                    if (mcaList != null)
                    {
                        mcaList.add((TemplateDocsBean) pendingApprovalList.get(count));
                    }
                } else
                {
                    if (mcaList != null)
                    {
                        dealerMcaMap.put(prevDealerCode, mcaList);
                    }
                    mcaList = new ArrayList();
                    mcaList.add((TemplateDocsBean) pendingApprovalList.get(count));
                    dealerDetailsList.add((TemplateDocsBean) pendingApprovalList.get(count));

                }

                prevDealerCode = dealerCode;
            }
            dealerMcaMap.put(dealerCode, mcaList);

            serviceResponse.setDealerNameList(dealerDetailsList);
            serviceResponse.setMcaDetailsMap(dealerMcaMap);

            log.info("Returning from the Pending Approval Delegate class");
        } catch (DBException dbe)
        {
            log.error("DB Exception in the Pending Approval Delegate class " + dbe);
            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());
        } catch (Exception e)
        {
            log.error("general exception in Pending Approval Delegate class : " + e);
            throw new BusinessException("PendingApprovalDelegate", "Error occurred building database request.");
        }
        return serviceResponse;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate#processDbRequest(com.dtcc.dnv.otc.common.layers.IServiceResponse,
     *      com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse) throws BusinessException
    {
        processSPReturnCode(serviceResponse, dbResponse);

    }
}
