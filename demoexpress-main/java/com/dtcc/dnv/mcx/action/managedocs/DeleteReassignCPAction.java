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
import com.dtcc.dnv.mcx.delegate.managedocs.DeleteReassignCPDelegate;
import com.dtcc.dnv.mcx.delegate.managedocs.ManageDocsServiceRequest;
import com.dtcc.dnv.mcx.delegate.managedocs.ManageDocsServiceResponse;
import com.dtcc.dnv.mcx.form.ManagedDocumentForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
 * 
 * The action class to delete the CP's and reassign the documents to another Cp if present.
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

public class DeleteReassignCPAction extends MCXBaseAction implements IManagedSession
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(DeleteReassignCPAction.class.getName());
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

        final String DELETECP = "deleteCP";
        final String DUPLICATE = "D";
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();
        log.info("In DeleteReassignCPAction");
        ManagedDocumentForm managedDocumentForm = (ManagedDocumentForm) form;
        ManageDocsTransactionBean transactionBean = new ManageDocsTransactionBean();
        ManageDocsServiceRequest serviceRequest = new ManageDocsServiceRequest(user.getAuditInfo());
        ManageDocsServiceResponse serviceResponse = new ManageDocsServiceResponse();
        transactionBean = (ManageDocsTransactionBean) managedDocumentForm.getTransaction();
        try
        {
            if (transactionBean != null)
            {
                
                String cmpnyId = user.getCompanyId();
               
                serviceRequest.setCmpnyId(cmpnyId);
                serviceRequest.setUserId(user.getMCXUserGUID());
                serviceRequest.setDocumentInd(managedDocumentForm.getDocumentInd());
              
                serviceRequest.setTransaction(transactionBean);
                
                DeleteReassignCPDelegate delegate = new DeleteReassignCPDelegate();
                serviceResponse = (ManageDocsServiceResponse) delegate.processRequest(serviceRequest);
                
                if(!serviceResponse.hasError())
                {
                    //managedDocumentForm.setDocumentInd(serviceResponse.getDocumentInd());
                   //To show the confirmation to the user to allow duplicates or not 
                    if(DUPLICATE.equals(serviceResponse.getDocumentInd())){
                        transactionBean.setManageCPStatus(DUPLICATE);
                        managedDocumentForm.setReassignList(serviceResponse.getDealerClient());
                    }
                    else{
                        transactionBean.setManageCPStatus(MCXConstants.SUCCESS_FORWARD);
                        //transactionBean.setReassignedDealerClient(MCXConstants.EMPTY_SPACE);
                    }
                   

                    forward = mapping.findForward(MCXConstants.SUCCESS_FORWARD);

                }else
                {
                    transactionBean.setManageCPStatus(MCXConstants.FAILURE_FORWARD);
                    managedDocumentForm.setReassignList(serviceResponse.getDealerClient());
                    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error.highpriority", serviceResponse.getSpReturnMessage()));
                }
            }
        } catch (BusinessException e)
        {
            managedDocumentForm.setReassignList(serviceRequest.getDealerClient());
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", new Timestamp(System.currentTimeMillis())));

        } catch (Exception e)
        {
            //managedDocumentForm.setReassignList(serviceResponse.getDealerClient());
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.internal.error", new Timestamp(System.currentTimeMillis())));
        }
        if (!errors.isEmpty())
        {
            forward = mapping.findForward(MCXConstants.FAILURE_FORWARD);
            saveMessages(request, errors);
        }
        //request.setAttribute("ManagedDocumentForm", managedDocumentForm);
        return forward;
    }
}