package com.dtcc.dnv.mcx.action.managedocs;

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
import com.dtcc.dnv.mcx.beans.ManageDocsTransactionBean;
import com.dtcc.dnv.mcx.delegate.managedocs.ManageDocsServiceRequest;
import com.dtcc.dnv.mcx.delegate.managedocs.ManageDocsServiceResponse;
import com.dtcc.dnv.mcx.delegate.managedocs.ReassignListDelegate;
import com.dtcc.dnv.mcx.form.ManagedDocumentForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
 * The Action class to fetch the list of dealers for which the users can reassign documents
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
 * @author Elango TR
 * @date Sep 12, 2007
 * @version 1.0
 * 
 *  
 */

public class ReassignListAction extends MCXBaseAction implements IManagedSession
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(ReassignListAction.class.getName());

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
        //String documentInd = "";
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();
        final String DELETE = "D";
        final String INITIALCHECK = "I";
        log.info("In ReassignListAction");

        ManagedDocumentForm managedDocumentForm = (ManagedDocumentForm) form;
        ManageDocsTransactionBean transactionBean = new ManageDocsTransactionBean();
        ManageDocsServiceRequest serviceRequest = new ManageDocsServiceRequest(user.getAuditInfo());
        ManageDocsServiceResponse serviceResponse = new ManageDocsServiceResponse();

        transactionBean = (ManageDocsTransactionBean) managedDocumentForm.getTransaction();
        
        
        String manageDocs = managedDocumentForm.getManageDocs();
        String deleteCmpnyId = managedDocumentForm.getDeleteCmpnyId();

        if (transactionBean != null)
        {

            try
            {
                //When there is action call to load the DeleteCpRouter.jsp which 
                //contains the deleteCp.jsp in its iframe 
                if(DELETE.equalsIgnoreCase(manageDocs))
                {
                    transactionBean.setDeleteCmpnyId(deleteCmpnyId);
                    forward = mapping.findForward(MCXConstants.SUCCESS_FORWARD);
                    managedDocumentForm.setManageDocs(MCXConstants.EMPTY_SPACE);
                    return forward;
                   
                }
                managedDocumentForm.setUploadFileSizeError(false);
                managedDocumentForm.setUploadDocs(false);
                managedDocumentForm.setDocsDeleted(false);
                String cmpnyId = user.getCompanyId();
                //documentInd = managedDocumentForm.getDocumentInd();

                serviceRequest.setCmpnyId(cmpnyId);
                serviceRequest.setManageDocs(manageDocs);
                transactionBean.setDeleteCmpnyId(deleteCmpnyId);
                transactionBean.setReassignedDealerClient(MCXConstants.EMPTY_SPACE);
                serviceRequest.setTransaction(transactionBean);

                //serviceRequest.setDocumentInd(managedDocumentForm.getDocumentInd());

                ReassignListDelegate delegate = new ReassignListDelegate();
                serviceResponse = (ManageDocsServiceResponse) delegate.processRequest(serviceRequest);


                if (!serviceResponse.hasError())
                {
                    managedDocumentForm.setManageDocs(serviceRequest.getManageDocs());
                    managedDocumentForm.setReassignList(serviceResponse.getDealerClient());
                    managedDocumentForm.setDocumentInd(serviceResponse.getDocumentInd());
                    transactionBean.setDuplicateDocsCheck(INITIALCHECK);// Used to indicate that the Sp has to check for duplicates in case of reassignment
                    
                    log.info("ReassignListAction action return");
                    forward = mapping.findForward(MCXConstants.SUCCESS_FORWARD);
                } else
                {
                    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", serviceResponse.getSpReturnMessage()));
                }
            } catch (BusinessException e)
            {
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", new Timestamp(System.currentTimeMillis())));

            } catch (Exception e)
            {
                log.error(e);
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.internal.error", new Timestamp(System.currentTimeMillis())));
            }
            if (!errors.isEmpty())
            {
                forward = mapping.findForward(MCXConstants.FAILURE_FORWARD);
                saveMessages(request, errors);
            }

        }

        return forward;
    }
}