package com.dtcc.dnv.mcx.action.homepage;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dtcc.dnv.mcx.action.IManagedSession;
import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.delegate.home.DisplayMCAsDelegate;
import com.dtcc.dnv.mcx.delegate.home.DisplayMCAsServiceRequest;
import com.dtcc.dnv.mcx.delegate.home.DisplayMCAsServiceResponse;
import com.dtcc.dnv.mcx.form.DisplayMCAsForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
 * This class is used to retrieve the counterparty pending MCA or executed MCA details.
 * 
 * RTM References:
 * 	3.3.5.3		Actor selects to click MCA with “Pending Client Review” and confirms to submit MCA for dealer execution
 *  3.3.6.1 	To view the executed MCA from the executed MCA tab and actor clicks the MCA type to view the detailed fields of the document
 *  3.3.6.15 	Client users should only be able to view the records in executed MCAs tab irrespective of their read/write privileges
 *  3.3.14.1 	The actor clicks the executed MCAs tab
 			 	System would show the executed MCA records in the executed MCA tab
 *  3.3.14.16 	Pre-executed MCAs uploaded by the dealer should be viewable only by the dealer users on the 
 * 				executed MCAs tab and not by the client users in their executed MCAs tab
 *  3.3.5.13	Actor chooses to view the “Executed MCAs” tab
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
 * @author Nithya R
 * @date Sep 10, 2007
 * @version 1.0
 */
public class DisplayMCAsAction extends MCXBaseAction implements IManagedSession
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(DisplayMCAsAction.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.mcx.action.DtccBaseAction#returnForward(com.dtcc.sharedservices.security.common.CUOUser,
     *      org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse) This method will get the
     *      pending or executed mca details from the delegate.
     */
    public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        final String PENDING_SUCCESS = "pending_success";
        final String PENDING_PRINTSUCCESS = "pending_printsuccess";
        final String EXECUTED_SUCCESS = "executed_success";
        final String EXECUTED_PRINTSUCCESS = "executed_printsuccess";
        boolean errorpage = true;
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();
        DisplayMCAsForm pendingMCAForm = (DisplayMCAsForm) form;
        DisplayMCAsServiceRequest serviceRequest = new DisplayMCAsServiceRequest(user.getAuditInfo());
        DisplayMCAsServiceResponse serviceResponse = new DisplayMCAsServiceResponse();
        try
        {
            log.info("inside display mca action");
            String userId = user.getMCXUserGUID();
            String companyId = user.getCompanyId();
            /*
             * Input details namely the company id, user id and the tab
             * indicator which decides if it is pending or executed MCA's are
             * set in the service request to be used by the delegate.
             */
            serviceRequest.setUserID(userId);
            serviceRequest.setCompanyID(companyId);
            serviceRequest.setPendingExecutedIndicator(pendingMCAForm.getMcaStatus());

            DisplayMCAsDelegate delegate = new DisplayMCAsDelegate();
            /*
             * The serviceRequest is passed as a parameter to the delegate's
             * processRequest method. The processRequest method returns the
             * serviceResponse.
             */
            serviceResponse = (DisplayMCAsServiceResponse) delegate.processRequest(serviceRequest);
            /*
             * The list in the serviceResponse that holds the pending or
             * executed MCA's details is set in the form.
             */
            pendingMCAForm.setDealerDetailsList(serviceResponse.getDealerDetailsList());

            log.info("back to action class");
            if (serviceResponse.hasError())
            {
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, serviceResponse.getSpReturnMessage()));

            }
            /*
             * The boolean variable error page is set to false in case of
             * successful execution of the stored procedure that fetches the
             * pending and executed MCA's details.
             */

            if (serviceResponse.getPendingExecutedStatus() != null && serviceResponse.getPendingExecutedStatus().equals("success"))
                errorpage = false;
        } catch (BusinessException be)
        {
            log.error("business exception in display mcas action class " + be);
            log.error(be);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));

        } catch (Exception e)
        {
            log.error("general exception in display mcas action class : " + e);
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));
        }
        if (errorpage)
        {
            if (!errors.isEmpty())
            {
                saveMessages(request, errors);
            }
            return mapping.findForward(MCXConstants.ERROR_FORWARD);
        } else
        {
            /*
             * On successful return to the action class based on the tab
             * indicator the control is directed to the pending or exected MCA's
             * page.
             */
            if (MCXConstants.MCA_STATUS_PENDING.equalsIgnoreCase(serviceRequest.getPendingExecutedIndicator()))
            {
                /*
                 * This loop decides whether the control is to be forwarded to PendingMCA page or PrintPendingMCA popup
                 */
                if ("Y".equals(pendingMCAForm.getPrintParam()))
                {
                    forward = mapping.findForward(PENDING_PRINTSUCCESS);
                } else
                {
                    forward = mapping.findForward(PENDING_SUCCESS);
                }
            } else
            {
                /*
                 * This loop decides whether the control is to be forwarded to ExecutedMCA page or PrintExecutedMCA popup
                 */
                if ("Y".equals(pendingMCAForm.getPrintParam()))
                {
                    forward = mapping.findForward(EXECUTED_PRINTSUCCESS);
                } else
                {
                    forward = mapping.findForward(EXECUTED_SUCCESS);
                }
            }
            return forward;

        }
    }
}