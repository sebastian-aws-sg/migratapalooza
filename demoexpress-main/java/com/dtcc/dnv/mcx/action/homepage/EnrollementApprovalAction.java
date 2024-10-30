/*
 * Created on Sep 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.action.homepage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dtcc.dnv.mcx.action.MCXBaseAction;

import com.dtcc.dnv.mcx.beans.PendingEnrollmentsTransaction;
import com.dtcc.dnv.mcx.delegate.home.EnrollmentApprovalDelegate;
import com.dtcc.dnv.mcx.delegate.home.EnrollmentApprovalServiceRequest;
import com.dtcc.dnv.mcx.delegate.home.EnrollmentApprovalServiceResponse;
import com.dtcc.dnv.mcx.form.PendingApprovalForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
 * This class is used to update the Approved or denied MCA's status to the
 * database when the user approves or denies a MCA.This Action class is called when
 * the dealer selects one or MCA's to approve and clicks on the submit button or
 * the dealer chooses to deny all the MCA's enrolled by a particular user of the 
 * counterparty and clicks on the submit button. 
 * 
 * RTM Reference : 3.3.11.8 A Dealer should be able to approve the Client and 
 * approve all MCAs requested by the Client for setup 
 * RTM Reference : 3.3.11.9 A Dealer should be able to approve the Counterparty 
 * and approve one or some MCAs requested by the Client  for setup 
 * RTM Reference : 3.3.11.10 A Dealer should be able to deny the
 * Client in which case all MCAs requested by that Client should be
 * automatically denied for setup 
 * RTM Reference : 3.3.11.11 A Dealer should not be able to approve the Client 
 * and simultaneously deny all MCAs requested by the Client
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

public class EnrollementApprovalAction extends MCXBaseAction
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(EnrollementApprovalAction.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.mcx.action.DtccBaseAction#returnForward(com.dtcc.sharedservices.security.common.CUOUser,
     *      org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse) This method returns the
     *      status after approving or denying the MCA's.
     */

    public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        final String SUCCESS_FORWARD = "success";
        final String FAILURE_FORWARD = "failure";

        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;

        PendingApprovalForm pendingApprovalForm = (PendingApprovalForm) form;
        PendingEnrollmentsTransaction pendingEnrollTrans = new PendingEnrollmentsTransaction();
        pendingEnrollTrans = (PendingEnrollmentsTransaction) pendingApprovalForm.getTransaction();
        if (pendingEnrollTrans != null)
        {
            String actionParam = pendingEnrollTrans.getSubmitParam();
            String dealercode = pendingEnrollTrans.getDealerCode();
            String userNm = pendingEnrollTrans.getUserNm();
            String uploadTime = pendingEnrollTrans.getUpLoadTime();
            String companyId = user.getCompanyId();
            String userId = user.getMCXUserGUID();
            String userID = dealercode + userNm + uploadTime;
            boolean status = false;
            try
            {

                log.info("inside enrollment approval action");
                EnrollmentApprovalServiceRequest serviceRequest = new EnrollmentApprovalServiceRequest(user.getAuditInfo());
                EnrollmentApprovalServiceResponse serviceResponse = new EnrollmentApprovalServiceResponse();

                String[] selectedMCA = pendingEnrollTrans.getSelectedMCAs();
                Map mcaListMap = pendingApprovalForm.getMcaDetailsMap();
                List mcaList = (ArrayList) mcaListMap.get(userID);                

                /*
                 * setting the map which contain details about the MCA for a
                 * particular counterparty to the service request
                 */
                serviceRequest.setMcaListMap(pendingApprovalForm.getMcaDetailsMap());
                /*
                 * Setting the details about the MCA's Selected for approval to
                 * the service request
                 */
                serviceRequest.setSelectedMca(pendingEnrollTrans.getSelectedMCAs());
                /*
                 * setting the Action Parameter to the service request to do
                 * some manipulation in the delegate class
                 */
                serviceRequest.setActionParam(actionParam);
                serviceRequest.setDealerCode(dealercode);
                serviceRequest.setUserNm(userNm);
                serviceRequest.setUploadTime(uploadTime);
                serviceRequest.setCompanyId(companyId);
                serviceRequest.setUserID(userId);

                EnrollmentApprovalDelegate delegate = new EnrollmentApprovalDelegate();

                serviceResponse = (EnrollmentApprovalServiceResponse) delegate.processRequest(serviceRequest);
                if (serviceResponse.hasError())
                {                    
                    pendingApprovalForm.setMcaDetailsList(mcaList);
                    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, serviceResponse.getSpReturnMessage()));
                    forward = mapping.findForward(MCXConstants.FAILURE_FORWARD);
                }
                status = serviceResponse.getSaveStatus();
                if (serviceResponse.getSaveStatus())
                {
                    pendingEnrollTrans.setPendingEnrollmentSuccess(SUCCESS_FORWARD);
                } else
                {                    
                    pendingEnrollTrans.setPendingEnrollmentSuccess(FAILURE_FORWARD);
                }

                log.info("Returning back from the enrolment approval action");
            } catch (BusinessException bsnse)
            {                
                log.error("business exception in Enrollement approval action class " + bsnse);
                log.error(bsnse);
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));

            } catch (Exception e)
            {                
                log.error("business exception in Enrollement approval action class " + e);
                log.error(e);
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));

            }
            if (status)
            {
                forward = mapping.findForward(SUCCESS_FORWARD);
            } else
            {
                if (!errors.isEmpty())
                {                    
                    saveErrors(request, errors);
                }
                forward = mapping.findForward(MCXConstants.FAILURE_FORWARD);
            }
        }
        
        return forward;
        
    }

}
