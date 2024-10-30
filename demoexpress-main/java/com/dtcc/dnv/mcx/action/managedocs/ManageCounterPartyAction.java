package com.dtcc.dnv.mcx.action.managedocs;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.beans.ManageDocsTransactionBean;
import com.dtcc.dnv.mcx.delegate.managedocs.ManageCounterPartyDelegate;
import com.dtcc.dnv.mcx.delegate.managedocs.ManageCounterPartyServiceRequest;
import com.dtcc.dnv.mcx.delegate.managedocs.ManageCounterPartyServiceResponse;
import com.dtcc.dnv.mcx.form.ManagedDocumentForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights
 * reserved.
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

public class ManageCounterPartyAction extends MCXBaseAction
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(ManageCounterPartyAction.class.getName());

    /*
     * (non-Javadoc)
     *
     * @see com.dtcc.dnv.mcx.action.DtccBaseAction#returnForward(com.dtcc.sharedservices.security.common.CUOUser,
     *      org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();

        String addEditIndicator = null;

        ManagedDocumentForm managedDocumentForm = (ManagedDocumentForm) form;
        ManageDocsTransactionBean transactionBean = new ManageDocsTransactionBean();

        transactionBean = (ManageDocsTransactionBean) managedDocumentForm.getTransaction();

        ManageCounterPartyServiceRequest serviceRequest = new ManageCounterPartyServiceRequest(user.getAuditInfo());
        ManageCounterPartyServiceResponse serviceResponse = new ManageCounterPartyServiceResponse();

        if (transactionBean != null)
        {

            try
            {
                log.info("In ManageCounterPartyAction");
                managedDocumentForm.setUploadFileSizeError(false);
                managedDocumentForm.setUploadDocs(false);
                managedDocumentForm.setDocsDeleted(false);
                String cmpnyId = user.getCompanyId();
                serviceRequest.setTransaction(transactionBean);
                transactionBean.setManageCPStatus(MCXConstants.EMPTY_SPACE);


                if (MCXConstants.ADDCPFLAG.equals(transactionBean.getAddRenameFlag()))
                {//For add cp
                    log.info("Add CounterParty");

                    addEditIndicator = MCXConstants.ADDCP;
                } else if (MCXConstants.RENAMECPFLAG.equals(transactionBean.getAddRenameFlag()))
                {// for edit cp
                    log.info("Edit CounterParty");

                    addEditIndicator = MCXConstants.EDITCP;
                }

                serviceRequest.setLoginDealerClientId(cmpnyId);
                serviceRequest.setUpdatedUserId(user.getMCXUserGUID());

                ManageCounterPartyDelegate delegate = new ManageCounterPartyDelegate();
                serviceResponse = (ManageCounterPartyServiceResponse) delegate.processRequest(serviceRequest);
                if(!serviceResponse.hasError())
                {
                    managedDocumentForm.setCounterPartyId(serviceResponse.getCmpny_Id());
                    transactionBean.setManageCPStatus(MCXConstants.ADDCPSUCCESS);

                    log.info("Returning from ManageCounterPartyAction");
                    forward = mapping.findForward(MCXConstants.SUCCESS_FORWARD + addEditIndicator);
                }
                else
                {
                    forward = mapping.findForward(MCXConstants.FAILURE_FORWARD + addEditIndicator);
                    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error.highpriority", serviceResponse.getSpReturnMessage()));
                }
            } catch (BusinessException e)
            {
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", new Timestamp(System.currentTimeMillis())));
                forward = mapping.findForward(MCXConstants.FAILURE_FORWARD + addEditIndicator);

            } catch (Exception e)
            {
                log.error(e);
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.internal.error", new Timestamp(System.currentTimeMillis())));
                forward = mapping.findForward(MCXConstants.FAILURE_FORWARD);
            }
            if (!errors.isEmpty())
            {
                transactionBean.setManageCPStatus(MCXConstants.ADDCPFAILURE);
                saveMessages(request, errors);
            }
        }
        return forward;
    }
}
