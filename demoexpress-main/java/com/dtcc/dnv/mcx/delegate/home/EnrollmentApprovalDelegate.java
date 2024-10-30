package com.dtcc.dnv.mcx.delegate.home;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dtcc.dnv.mcx.beans.TemplateDocsBean;
import com.dtcc.dnv.mcx.dbhelper.enroll.EnrollDealerDbRequest;
import com.dtcc.dnv.mcx.dbhelper.enroll.EnrollDealerDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;

import com.dtcc.dnv.mcx.proxy.enroll.EnrollDealerProxy;
import com.dtcc.dnv.mcx.util.Email;
import com.dtcc.dnv.mcx.util.EmailUtil;
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
 * This class is used to delegate the service request to the db request to save
 * the Approved or denied status of the MCA's to the database when the user
 * tries to approve or denies a MCA.This Class is called from the EnrollmentApproval
 * Action class when the dealer selects one or MCA's to approve and clicks on the submit 
 * button or the dealer chooses to deny all the MCA's enrolled by 
 * a particular user of the  counterparty and clicks on the submit button.
 *   
 * RTM Reference : 3.3.11.8 A Dealer should be able to approve the Client and 
 * approve all MCAs requested by the Client for setup 
 * RTM Reference : 3.3.11.9 A Dealer should be able to approve the Counterparty 
 * and approve one or some MCAs requested by the Client for setup
 * RTM Reference : 3.3.11.10 A Dealer should be able to deny the Client in which
 * case all MCAs requested by that Client should be automatically denied for setup
 * RTM Reference : 3.3.11.11 A Dealer should not be able to approve the
 * Client and simultaneously deny all MCAs requested by the Client
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
 * @date Sep 11, 2007
 * @version 1.0
 * 
 *  
 */

public class EnrollmentApprovalDelegate extends MCXAbstractBusinessDelegate
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(EnrollmentApprovalDelegate.class.getName());

    /*
     *
     * This method returns the Status after the approval or denial process.This method is used to pass the request to
     * the dao to approve or deny an MCA.
	 *
	 * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
     */
    public IServiceResponse processRequest(IServiceRequest request) throws BusinessException, UserException
    {
        final String REQUEST_ID = "DPMXENRL";
        EnrollmentApprovalServiceResponse serviceResponse = new EnrollmentApprovalServiceResponse();
        EnrollmentApprovalServiceRequest serviceRequest = (EnrollmentApprovalServiceRequest) request;

        log.info("inside Enrollment Approval Delegate class");
        EnrollDealerDbRequest dbRequest = new EnrollDealerDbRequest(REQUEST_ID, serviceRequest.getAuditInfo());
        EnrollDealerProxy dbProxy = new EnrollDealerProxy();
        EnrollDealerDbResponse dbResponse = new EnrollDealerDbResponse();
        Map mcaListMap = serviceRequest.getMcaListMap();
        String actionParam = serviceRequest.getActionParam();
        String dealer = serviceRequest.getDealerCode();
        String user = serviceRequest.getUserNm();
        String uploadTime = serviceRequest.getUploadTime();
        String userID = dealer + user + uploadTime;
        int denyParam = 0;
        /*
         * If three MCA's are enrolled against a dealer by a particular counterparty at a particular time 
         * and if he wants to approve only one MCA and wants to Deny the other two then the status will be set 
         * as 'A' for the one MCA which the dealer wants to approve and for the other two MCA's the status will 
         * be set as 'D'.
         */
        
        if (actionParam.equals(MCXConstants.MCA_STATUS_APPROVE))
        {
            List mcaList = (ArrayList) mcaListMap.get(userID);

            String[] selectedMca = serviceRequest.getSelectedMca();
            for (int listSize = 0; listSize < mcaList.size(); listSize++)
            {
                int temp_ID = ((TemplateDocsBean) mcaList.get(listSize)).getTmpltId();
                String temp_Id = String.valueOf(temp_ID);
                boolean isApprove = false;
                for (int index = 0; index < selectedMca.length; index++)
                {
                    String templateId = selectedMca[index];
                    if (temp_Id.equals(templateId))
                    {
                        isApprove = true;
                        break;
                    }
                }

                if (isApprove)
                {
                    callProxy(MCXConstants.MCA_STATUS_APPROVEDFIRMS, dealer, temp_Id, dbRequest, dbResponse, dbProxy, serviceRequest, serviceResponse);

                } else
                {
                    denyParam = denyParam + 1;
                    callProxy(MCXConstants.MCA_STATUS_DENYFIRMS, dealer, temp_Id, dbRequest, dbResponse, dbProxy, serviceRequest, serviceResponse);

                }

            }
            sendMail(dealer, serviceRequest.getCompanyId(), MCXConstants.MCA_STATUS_APPROVEDFIRMS);
            if(denyParam > 0){
                sendMail(dealer, serviceRequest.getCompanyId(), MCXConstants.MCA_STATUS_DENYFIRMS);
            }
        } else
        {

            List mcaDetailsList = (ArrayList) mcaListMap.get(userID);

            for (int listSize = 0; listSize < mcaDetailsList.size(); listSize++)
            {
                int temp_id = ((TemplateDocsBean) mcaDetailsList.get(listSize)).getTmpltId();
                String templateID = String.valueOf(temp_id); 
                callProxy(MCXConstants.MCA_STATUS_DENYFIRMS, dealer, templateID, dbRequest, dbResponse, dbProxy, serviceRequest, serviceResponse);

            }
            sendMail(dealer, serviceRequest.getCompanyId(), MCXConstants.MCA_STATUS_DENYFIRMS);
        }
        log.info("Returning from the enrollment approval delegate class");

        return serviceResponse;
    }

    /**
     * 
     * This method is used to send a mail for the Enrollmen process
     * 
     * 
     * 
     * @param serviceRequest
     *  
     */

    private void sendMail(String toCompanyID, String ccCompanyID, String action)

    {
        // Getting toAddress & ccAddress for the given company id
        String[] toAddress = EmailUtil.getCompanyContactEmails(toCompanyID);
        String[] ccAddress = EmailUtil.getCompanyContactEmails(ccCompanyID);
        // Sending an Email using to & cc
        if (action.equals(MCXConstants.MCA_STATUS_APPROVEDFIRMS))
        {
            Email.sendMail(MCXConstants.MCX_EMAIL_APPROVAL_SUBJ_KEY, MCXConstants.MCX_EMAIL_APPROVAL_FILE_KEY, toAddress, ccAddress, null, MCXConstants.MIME_HTML);
        } else
        {
            Email.sendMail(MCXConstants.MCX_EMAIL_DENIAL_SUBJ_KEY, MCXConstants.MCX_EMAIL_DENIAL_FILE_KEY, toAddress, ccAddress, null, MCXConstants.MIME_HTML);
        }

    }

    

    /**
     * @param status
     * @param dealerCode
     * @param temp_id
     * @param dbRequest
     * @param dbResponse
     * @param dbProxy
     * @param serviceRequest
     * @param serviceResponse
     * @throws BusinessException
     * method to call the proxy
     */
    private void callProxy(String status, String dealerCode, String temp_id, EnrollDealerDbRequest dbRequest, EnrollDealerDbResponse dbResponse, EnrollDealerProxy dbProxy,
            EnrollmentApprovalServiceRequest serviceRequest, EnrollmentApprovalServiceResponse serviceResponse) throws BusinessException
    {
        try
        {
            dbRequest.setEnrollStatus(status);
            dbRequest.setCompanyID(dealerCode);
            dbRequest.setDealerCode(serviceRequest.getCompanyId());
            dbRequest.setMcaTemplateID(temp_id);
            dbRequest.setEnrollTimeStamp("");
            dbRequest.setUserID(serviceRequest.getUserID());
            dbResponse = (EnrollDealerDbResponse) dbProxy.processRequest(dbRequest);
            processDbRequest(serviceResponse,dbResponse);
            DbRequestBuilder.copyObject(dbResponse, serviceResponse);
        } catch (DBException dbe)
        {
            log.error("DB exception in Enrollment Approval Delegate class " + dbe);
            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());
        } catch (Exception e)
        {

            log.error("general exception in Enrollment Approval Delegate class : " + e);
            throw new BusinessException("EnrollmentApprovalDelegate", "Error occurred building database request.");
        }
    }

    /* (non-Javadoc)
     * @see com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate#processDbRequest(com.dtcc.dnv.otc.common.layers.IServiceResponse, com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse) throws BusinessException
    {
        processSPReturnCode(serviceResponse,dbResponse);
        
    }
 
}
