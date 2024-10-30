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
import com.dtcc.dnv.mcx.delegate.managedocs.DealerCLientListDelegate;
import com.dtcc.dnv.mcx.delegate.managedocs.DealerClientListServiceRequest;
import com.dtcc.dnv.mcx.delegate.managedocs.DealerClientListServiceResponse;
import com.dtcc.dnv.mcx.form.ManagedDocumentForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
 * The Action class for getting the dealers list.
 * 
 * RTM Reference : 3.3.17.34,3.3.18.34 It should be possible for a user to
 * differentiate between a manually added/renamed counterparty and the MCA
 * Xpress registered member firm Copyright © 2003 The Depository Trust &
 * Clearing Company. All rights reserved.
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
 * @date Sep 5, 2007
 * @version 1.0
 * 
 *  
 */
public class DealerClientListAction extends MCXBaseAction implements IManagedSession
{
    
    private final static MessageLogger log = MessageLogger.getMessageLogger(DealerClientListAction.class.getName());

    /*
     * The Actionforward method.
     * 
     * @see com.dtcc.dnv.mcx.action.DtccBaseAction#returnForward(com.dtcc.sharedservices.security.common.CUOUser,
     *      org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        
        final String SEARCH = "Search";

        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();
        
        log.info("In action of dealerclientlist");

        try
        {

            ManagedDocumentForm managedDocumentForm = (ManagedDocumentForm) form;
            ManageDocsTransactionBean transactionBean = new ManageDocsTransactionBean();
            transactionBean = (ManageDocsTransactionBean) managedDocumentForm.getTransaction();

            if(transactionBean !=null){
                managedDocumentForm.setUploadFileSizeError(false);
                managedDocumentForm.setUploadDocs(false);
                managedDocumentForm.setDocsDeleted(false);
                DealerClientListServiceRequest serviceRequest = new DealerClientListServiceRequest(user.getAuditInfo());
                DealerClientListServiceResponse serviceResponse = new DealerClientListServiceResponse();

                String cmpnyId = user.getCompanyId();
                serviceRequest.setCmpnyId(cmpnyId);

                transactionBean.setDeleteCmpnyId(MCXConstants.EMPTY_SPACE);
                
                serviceRequest.setTransaction(transactionBean);

                DealerCLientListDelegate delegate = new DealerCLientListDelegate();
                serviceResponse = (DealerClientListServiceResponse) delegate.processRequest(serviceRequest);
                
                if(!serviceResponse.hasError())
                {
                    managedDocumentForm.setDealerClient(serviceResponse.getDealerClientList());
                    managedDocumentForm.setManageDocTab(SEARCH);
                    transactionBean.setSelectedDealerClient(MCXConstants.ALL);
                    transactionBean.setSelectedDocumentType(MCXConstants.ALL);
                    transactionBean.setDocName(MCXConstants.EMPTY_SPACE);

                    //Used for deciding whether to display the document library or not.
                    managedDocumentForm.setNumberOfDocuments(-1);
                    log.info("Returning from action of dealerclientlist");
                    forward = mapping.findForward(MCXConstants.SUCCESS_FORWARD);
                }
                else
                {
                    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", serviceResponse.getSpReturnMessage()));
                }
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

        return forward;
    }

}
