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

import com.dtcc.dnv.mcx.action.IManagedSession;
import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.beans.PendingEnrollmentsTransaction;
import com.dtcc.dnv.mcx.delegate.home.PendingApprovalDelegate;
import com.dtcc.dnv.mcx.delegate.home.PendingEnrollApprovalServiceRequest;
import com.dtcc.dnv.mcx.delegate.home.PendingEnrollApprovalServiceResponse;
import com.dtcc.dnv.mcx.form.PendingApprovalForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
 * This class is used to retrieve the data for listing Pending Enrollments and
 * listing of Approved Firms. This action class is called when the dealer logs
 * into the MCA-X system or the dealer selects the Approved Firms and the Pending
 * Enrollments Approval tab. 
 * RTM Reference : 3.3.11.1 The actor views the pending Enrolment approval tab for 
 * the respective MCA types against the clients awaiting approval, approves a client 
 * and all the MCAs requested for by him 
 * RTM Reference : 3.3.11.2 Actor selects to approve only one or more but
 * not all of the MCA Enrolment requests for the client 
 * RTM Reference : 3.3.11.7 A Dealer should only be able to approve or deny Enrolment 
 * of a Client that has initiated firm Enrolment 
 * RTM Reference : 3.3.11.12 Once a Client firm is approved with at least one associated 
 * MCA(s), that Client should never be removed from the Dealer’s ‘Approved Firms’ tab
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
public class PendingApprovalAction extends MCXBaseAction implements IManagedSession
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(PendingApprovalAction.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.mcx.action.DtccBaseAction#returnForward(com.dtcc.sharedservices.security.common.CUOUser,
     *      org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse) this method will return
     *      parameters needed for the pending enrollemnts approval screen.
     */

    public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        final String SUCCESS_FORWARD = "success";
        final String FAILURE_FORWARD = "failure";
        final String PRINTSUCCESS_FORWARD = "printsuccess";
        final String APPROVEDFIRMS_SUCESS_FORWARD = "ApproveFirmSuccess";
        final String DENY_SUCCESS_FORWARD = "denysuccess";
        final String APPROVE_SUCCESS_FORWARD = "appsuccess";
        final String PRINTAPPROVEDFIRMS_SUCESS_FORWARD = "PrintApproveFirmSuccess";
        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;
        boolean errorpage = true;

        PendingApprovalForm pendingApprovalForm = (PendingApprovalForm) form;
        PendingEnrollmentsTransaction pendingEnrollTrans = new PendingEnrollmentsTransaction();
        pendingEnrollTrans = (PendingEnrollmentsTransaction) pendingApprovalForm.getTransaction();
        if (pendingEnrollTrans != null && pendingApprovalForm != null)
        {
            pendingEnrollTrans.setPendingEnrollmentSuccess("");

            String action = request.getParameter(MCXConstants.MCA_ACTION);
            String dealer = pendingApprovalForm.getDealer();
            String user1 = pendingApprovalForm.getUser();
            String uploadTime = pendingApprovalForm.getUploadtime();
            String selectedTab = pendingApprovalForm.getSelectedTab();

            if (MCXConstants.MCA_STATUS_PENDING_ENROLLMENTS.equals(selectedTab))
            {
                pendingApprovalForm.setPendingStatus(MCXConstants.MCA_PENDING_STATUS);
            } else if (MCXConstants.MCA_STATUS_APPROVEDFIRMS.equals(selectedTab))
            {
                pendingApprovalForm.setPendingStatus(MCXConstants.MCA_APPROVE_STATUS);
            }

            pendingEnrollTrans.setDealerCode(dealer);
            pendingEnrollTrans.setUserNm(user1);
            pendingEnrollTrans.setUpLoadTime(uploadTime);

            String userId = dealer + user1 + uploadTime;
            String dealerCode = user.getCompanyId();
            String usercode = user.getMCXUserGUID();

            try
            {
                log.info("inside pending approval action");
                PendingEnrollApprovalServiceRequest serviceRequest = new PendingEnrollApprovalServiceRequest(user.getAuditInfo());
                PendingEnrollApprovalServiceResponse serviceResponse = new PendingEnrollApprovalServiceResponse();

                serviceRequest.setDealerCode(dealerCode);
                serviceRequest.setUserCode(usercode);
                serviceRequest.setSelectedTab(pendingApprovalForm.getSelectedTab());
                PendingApprovalDelegate delegate = new PendingApprovalDelegate();

                /*
                 * Delegate is called only during the loading of the pending
                 * enrollments approval screen.
                 */
                if ((!MCXConstants.MCA_STATUS_APPROVE_ACTION.equals(action)) && (!MCXConstants.MCA_STATUS_DENY_ACTION.equals(action)))
                {
                    /* will come inside the loop only on load of the screen */
                    serviceResponse = (PendingEnrollApprovalServiceResponse) delegate.processRequest(serviceRequest);
                    if (serviceResponse.hasError())
                    {
                        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", serviceResponse.getSpReturnMessage()));

                    }

                    //the details about a particular counter party are set to
                    // the
                    // pending approval form
                    pendingApprovalForm.setDealerNameList(serviceResponse.getDealerNameList());
                    pendingApprovalForm.setMcaDetailsStatus(serviceResponse.getStatus());
                    pendingApprovalForm.setMcaDetailsMap(serviceResponse.getMcaDetailsMap());
                    pendingApprovalForm.setDealerDetailsList(serviceResponse.getDealerDetailsList());
                }
                Map userMcaDetailsMap = pendingApprovalForm.getMcaDetailsMap();
                /*
                 * MCA enrolled by the particular counterparty is added into a
                 * list
                 */
                if (userMcaDetailsMap != null)
                {
                    List mcaDetailsList = (ArrayList) userMcaDetailsMap.get(userId);

                    if (mcaDetailsList != null)
                    {
                        pendingApprovalForm.setListSize(mcaDetailsList.size());
                    } else
                    {
                        pendingApprovalForm.setListSize(0);
                    }
                    pendingApprovalForm.setMcaDetailsList(mcaDetailsList);
                }
                log.info("returning from pending approval action");

                if (pendingApprovalForm.getMcaDetailsStatus() != null && MCXConstants.SUCCESS_FORWARD.equals(pendingApprovalForm.getMcaDetailsStatus()))
                {
                    errorpage = false;
                }

            } catch (BusinessException bsnse)
            {
                log.error("business exception in pending approval action class " + bsnse);
                log.error(bsnse);
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR , new Timestamp(System.currentTimeMillis())));

            } catch (Exception e)
            {
                log.error("general exception in pending approval action class " + e);
                log.error(e);
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));

            }
            if (errorpage)
            {

                if (!errors.isEmpty())
                {
                    saveMessages(request, errors);
                }
                forward = mapping.findForward(MCXConstants.ERROR_FORWARD);
            } else
            {

                if (MCXConstants.MCA_STATUS_APPROVE_ACTION.equals(action))
                {
                    /*
                     * After clicking the approve button tis action is called
                     * and the values are returned to the
                     * dealer_cp_approve_confirm.jsp.
                     */
                    pendingApprovalForm.setAll(null);
                    pendingEnrollTrans.setSelectedMCAs(null);
                    forward = mapping.findForward(APPROVE_SUCCESS_FORWARD);
                } else if (MCXConstants.MCA_STATUS_DENY_ACTION.equals(action))
                {
                    /*
                     * After clicking the approve button tis action is called
                     * and the values are returned to the
                     * dealer_cp_deny_confirm.jsp.
                     */
                    pendingEnrollTrans.setSelectedMCAs(null);
                    forward = mapping.findForward(DENY_SUCCESS_FORWARD);
                } else
                {
                    /*
                     * on loading the pending enrollements approval screen the
                     * values are returned to the
                     * dealer_pending_enroll_approval.jsp.
                     */
                    if (MCXConstants.MCA_STATUS_PENDING_ENROLLMENTS.equals(selectedTab) || MCXConstants.MCA_PENDING_STATUS.equals(pendingApprovalForm.getPendingStatus()))
                    {
                        if ("Y".equals(pendingApprovalForm.getPrintParam()))
                        {                            
                            forward = mapping.findForward(PRINTSUCCESS_FORWARD);
                        }else{
                        forward = mapping.findForward(SUCCESS_FORWARD);
                        }
                    } else
                    {
                        if ("Y".equals(pendingApprovalForm.getPrintParam()))
                        {                            
                            forward = mapping.findForward(PRINTAPPROVEDFIRMS_SUCESS_FORWARD);   
                        }else{
                        forward = mapping.findForward(APPROVEDFIRMS_SUCESS_FORWARD);
                        }
                    }
                }

            }
        }

        return forward;

    }

}
