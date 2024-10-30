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
import com.dtcc.dnv.mcx.delegate.managedocs.DeleteCPDocDelegate;
import com.dtcc.dnv.mcx.delegate.managedocs.ManageDocsServiceRequest;
import com.dtcc.dnv.mcx.delegate.managedocs.ManageDocsServiceResponse;
import com.dtcc.dnv.mcx.form.ManagedDocumentForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
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

public class DeleteCPDocAction extends MCXBaseAction implements IManagedSession
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(DeleteCPDocAction.class.getName());

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
        
        final String SEARCHTAB = "S";
        final String SEARCH = "Search";
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();

        log.info("In DeleteCPDocAction");

        ManagedDocumentForm managedDocumentForm = (ManagedDocumentForm) form;
        ManageDocsTransactionBean transactionBean = new ManageDocsTransactionBean();
        ManageDocsServiceRequest serviceRequest = new ManageDocsServiceRequest(user.getAuditInfo());
        ManageDocsServiceResponse serviceResponse = new ManageDocsServiceResponse();
        transactionBean = (ManageDocsTransactionBean) managedDocumentForm.getTransaction();

        if (transactionBean != null)
        {
            managedDocumentForm.setUploadFileSizeError(false);
            managedDocumentForm.setUploadDocs(false);
            managedDocumentForm.setDocsDeleted(false);
            serviceRequest.setTransaction(transactionBean);
            String selected[] = transactionBean.getSelectedDocuments();

            try
            {
                managedDocumentForm.setUploadFileSizeError(false);
                String cmpnyId = user.getCompanyId();
                serviceRequest.setUserId(user.getMCXUserGUID());
                DeleteCPDocDelegate delegate = new DeleteCPDocDelegate();
                serviceResponse = (ManageDocsServiceResponse) delegate.processRequest(serviceRequest);
                
                /*if (MCXConstants.PREEXISTINGTAB.equals(managedDocumentForm.getManageDocs()))
                {
                    forward = mapping.findForward(MCXConstants.PREEXISTING);
                } else if (MCXConstants.OTHERTAB.equals(managedDocumentForm.getManageDocs()))
                {
                    forward = mapping.findForward(MCXConstants.OTHERS);
                } else if (SEARCHTAB.equals(managedDocumentForm.getManageDocs()))
                {
                    forward = mapping.findForward(SEARCH);
                }*/
                
                if (!serviceResponse.hasError())
                {
                    if (MCXConstants.PREEXISTINGTAB.equals(managedDocumentForm.getManageDocs()))
                    {
                        forward = mapping.findForward(MCXConstants.PREEXISTING);
                    } else if (MCXConstants.OTHERTAB.equals(managedDocumentForm.getManageDocs()))
                    {
                        forward = mapping.findForward(MCXConstants.OTHERS);
                    } else if (SEARCHTAB.equals(managedDocumentForm.getManageDocs()))
                    {
                        forward = mapping.findForward(SEARCH);
                    }
                    //Used for displaying the success popup.
                    managedDocumentForm.setDocsDeleted(true);
                    managedDocumentForm.setSelectedDocsDeleted(true);
                                      
                    
                } else
                {
                    if (MCXConstants.PREEXISTINGTAB.equals(managedDocumentForm.getManageDocs()))
                    {
                        forward = mapping.findForward(MCXConstants.PREEXISTING+MCXConstants.FAILURE_FORWARD);
                    } else if (MCXConstants.OTHERTAB.equals(managedDocumentForm.getManageDocs()))
                    {
                        forward = mapping.findForward(MCXConstants.OTHERS+MCXConstants.FAILURE_FORWARD);
                    } else if (SEARCHTAB.equals(managedDocumentForm.getManageDocs()))
                    {
                        forward = mapping.findForward(SEARCH+MCXConstants.FAILURE_FORWARD);
                    }
                    
                    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error.highpriority", serviceResponse.getSpReturnMessage()));
                }
                if (SEARCHTAB.equals(managedDocumentForm.getManageDocs()))
                {
                    
                    request.setAttribute("selectedDealerClient",transactionBean.getSelectedDealerClient());
                    request.setAttribute("selectedDocumentType",transactionBean.getSelectedDocumentType());
                    request.setAttribute("docName",transactionBean.getDocName());
                }
            } catch (BusinessException e)
            {
                forward = mapping.findForward(MCXConstants.FAILURE_FORWARD);
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", new Timestamp(System.currentTimeMillis())));

            } catch (Exception e)
            {
                forward = mapping.findForward(MCXConstants.FAILURE_FORWARD);
                log.error(e);
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.internal.error", new Timestamp(System.currentTimeMillis())));
            }
            if (!errors.isEmpty())
            {
                
                saveMessages(request, errors);
            }
        }
        return forward;
    }
}