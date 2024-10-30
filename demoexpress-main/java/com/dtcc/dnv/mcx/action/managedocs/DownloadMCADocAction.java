package com.dtcc.dnv.mcx.action.managedocs;

import java.io.IOException;
import java.io.OutputStream;
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
import com.dtcc.dnv.mcx.delegate.managedocs.DownloadMCADocDelegate;
import com.dtcc.dnv.mcx.delegate.managedocs.ManageDocsServiceRequest;
import com.dtcc.dnv.mcx.delegate.managedocs.ManageDocsServiceResponse;
import com.dtcc.dnv.mcx.form.ManagedDocumentForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.DBException;

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

public class DownloadMCADocAction extends MCXBaseAction implements IManagedSession
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(DownloadMCADocAction.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.mcx.action.DtccBaseAction#returnForward(com.dtcc.sharedservices.security.common.CUOUser,
     *      org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException, DBException,
            Exception
    {
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();
        final String SEARCH = "Search";

        log.info("In DownloadMCADocAction");

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
            
            String docId = managedDocumentForm.getDocId();
            String manageDocTabId = managedDocumentForm.getManageDocs();
            String counterPartyId = managedDocumentForm.getCounterPartyId();
            try
            {
                transactionBean.setDocId(docId);
                serviceRequest.setManageDocs(manageDocTabId);
                transactionBean.setCounterPartyId(counterPartyId);
                serviceRequest.setTransaction(transactionBean);

                DownloadMCADocDelegate delegate = new DownloadMCADocDelegate();
                serviceResponse = (ManageDocsServiceResponse) delegate.processRequest(serviceRequest);
                
                
                if (!serviceResponse.hasError())
                {

                    managedDocumentForm.setManageDocs(serviceRequest.getManageDocs());
                    managedDocumentForm.setReassignList(serviceResponse.getDealerClient());

                    log.info("DownloadMCADocAction action return");
                    forward = null;
                    // mapping.findForward(MCXConstants.SUCCESS_FORWARD);
                    downloadMCADocsDetails(request, response, serviceResponse.getDocFile(), serviceResponse.getDocName());
                } else
                {
                    if (MCXConstants.PREEXISTING.equals(managedDocumentForm.getManageDocTab()))
                    {
                        forward = mapping.findForward(MCXConstants.PREEXISTING);
                    } else if (MCXConstants.OTHERS.equals(managedDocumentForm.getManageDocTab()))
                    {
                        forward = mapping.findForward(MCXConstants.OTHERS);
                    } else if (SEARCH.equals(managedDocumentForm.getManageDocTab()))
                    {
                        forward = mapping.findForward(SEARCH);
                    }
                    else
                    {
                        forward = mapping.findForward("ExecutedMCAs");
                    }
                    
                    if (SEARCH.equals(managedDocumentForm.getManageDocTab()))
                    {
                        
                        request.setAttribute("selectedDealerClient",transactionBean.getSelectedDealerClient());
                        request.setAttribute("selectedDocumentType",transactionBean.getSelectedDocumentType());
                        request.setAttribute("docName",transactionBean.getDocName());
                    }
                    
                    
                    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error.highpriority", serviceResponse.getSpReturnMessage()));
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

    private void downloadMCADocsDetails(HttpServletRequest request, HttpServletResponse response, byte[] docBlob, String fileName) throws IOException
    {
        String ATTACHMENT_TYPE = "attachment; filename=";
        String extension = "";
        String CONTENT_TYPE = "";
        String CONTENT_DISPOSITION = "Content-Disposition";
        extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());

        if (MCXConstants.MANAGE_DOC_TYPE_DOC.equalsIgnoreCase(extension))
        {
            CONTENT_TYPE = MCXConstants.MANAGE_DOC_CONTENTTYPE_DOC;
        } else if (MCXConstants.MANAGE_DOC_TYPE_XLS.equalsIgnoreCase(extension))
        {
            CONTENT_TYPE = MCXConstants.MANAGE_DOC_CONTENTTYPE_XLS;
        } else if (MCXConstants.MANAGE_DOC_TYPE_PDF.equalsIgnoreCase(extension))
        {
            CONTENT_TYPE = MCXConstants.MANAGE_DOC_CONTENTTYPE_PDF;
        } else if (MCXConstants.MANAGE_DOC_TYPE_ZIP.equalsIgnoreCase(extension))
        {
            CONTENT_TYPE = MCXConstants.MANAGE_DOC_CONTENTTYPE_ZIP;
        }

        String contentDisposition = ATTACHMENT_TYPE + fileName;

        response.setContentType(CONTENT_TYPE);
        response.setHeader(CONTENT_DISPOSITION, contentDisposition);

        OutputStream outStream = response.getOutputStream();
        outStream.write(docBlob);
        outStream.flush();
    }
}