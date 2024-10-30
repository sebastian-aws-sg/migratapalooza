package com.dtcc.dnv.mcx.action.managedocs;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
import com.dtcc.dnv.mcx.delegate.managedocs.ManageDocsDelegate;
import com.dtcc.dnv.mcx.delegate.managedocs.ManageDocsServiceRequest;
import com.dtcc.dnv.mcx.delegate.managedocs.ManageDocsServiceResponse;
import com.dtcc.dnv.mcx.form.ManagedDocumentForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
 *
 * RTM Reference : 3.3.17.1 Listing of PreExisting documents and drop down of
 * CounterParties RTM Reference : 3.3.18.1 Listing of Other documents alone and
 * drop down of CounterParties RTM Reference : 3.3.17.7,3.3.18.15 There is no
 * ALL option in the counterParty dropdown RTM Reference : 3.3.17.34,3.3.18.34
 * It should be possible for a user to differentiate between a manually
 * added/renamed counterparty and the MCA Xpress registered member firm
 *
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
 * @author Ravikanth G
 * @date Sep 12, 2007
 * @version 1.0
 *
 *
 */

public class ManageDocsAction extends MCXBaseAction implements IManagedSession
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(ManageDocsAction.class.getName());

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
        final String DELIMITER = "-";
        List dealerClientDetails = new ArrayList();
        final String TRUE = "true";
        final String FALSE = "false";
        final String DOWNLOAD = "DOWNLOAD";

        log.info("In ManagedocsAction");

        ManagedDocumentForm managedDocumentForm = (ManagedDocumentForm) form;
        ManageDocsTransactionBean transactionBean = new ManageDocsTransactionBean();
        ManageDocsServiceRequest serviceRequest = new ManageDocsServiceRequest(user.getAuditInfo());
        ManageDocsServiceResponse serviceResponse = new ManageDocsServiceResponse();
        transactionBean = (ManageDocsTransactionBean) managedDocumentForm.getTransaction();
        
        transactionBean.setManageCPStatus(MCXConstants.EMPTY_SPACE);

        String manageDocsTab = managedDocumentForm.getManageDocTab();
        
        String selectedDealerClient =(String) request.getAttribute("selectedDealerClient");
        
       if(selectedDealerClient!=null){
           managedDocumentForm.setSelectedDealerClient(selectedDealerClient);
       }
        
        if (transactionBean != null)
        {
            try
            {

                String cmpnyId = user.getCompanyId();
                managedDocumentForm.setCmpnyId(cmpnyId);
                serviceRequest.setCmpnyId(cmpnyId);

                //serviceRequest.setNewCmpnyId(managedDocumentForm.getCounterPartyId());


                if (MCXConstants.PREEXISTINGTAB.equals(manageDocsTab))
                {
                    log.info("In preexisting of ManagedocsAction");
                    transactionBean.setSelectedDocumentType(MCXConstants.PREEXISTINGTAB);
                    managedDocumentForm.setManageDocTab(MCXConstants.PREEXISTING);

                    forward = mapping.findForward(MCXConstants.PREEXISTING);
                } else if (MCXConstants.OTHERTAB.equals(manageDocsTab))
                {
                    log.info("In Other of ManagedocsAction");
                    transactionBean.setSelectedDocumentType(MCXConstants.OTHERTAB);
                    managedDocumentForm.setManageDocTab(MCXConstants.OTHERS);
                    forward = mapping.findForward(MCXConstants.OTHERS);
                }

                transactionBean.setSelectedDealerClient(MCXConstants.EMPTY_SPACE);
                transactionBean.setDocName(MCXConstants.EMPTY_SPACE);
                transactionBean.setDeleteCmpnyId(MCXConstants.EMPTY_SPACE);
                serviceRequest.setTransaction(transactionBean);
                ManageDocsDelegate delegate = new ManageDocsDelegate();
                serviceResponse = (ManageDocsServiceResponse) delegate.processRequest(serviceRequest);

                if (!serviceResponse.hasError())
                {
                    managedDocumentForm.setDealerClient(serviceResponse.getDealerClient());
                    //log.debug("list size" + serviceResponse.getDealerClient().size());
                    dealerClientDetails = serviceResponse.getDealerClientDetails();
                    if (dealerClientDetails != null)
                    {

                        managedDocumentForm.setDealerClientDetails(serviceResponse.getDealerClientDetails());

                        managedDocumentForm.setNumberOfDocuments(dealerClientDetails.size());

                    }


                    managedDocumentForm.setMcaNames(serviceResponse.getIsdaTemplateList());
                    
                    if(DOWNLOAD.equals(managedDocumentForm.getManageDocs())){
                        managedDocumentForm.setCounterPartyId(MCXConstants.EMPTY_SPACE);
                        managedDocumentForm.setManageDocs(MCXConstants.EMPTY_SPACE);
                    }
                    
                    // To set the upload docs success alert flag to false if the docs are not uploaded.
                    if(TRUE.equals(managedDocumentForm.getDocumentsUploaded())){
                        managedDocumentForm.setUploadDocs(true);
                        managedDocumentForm.setDocsUploaded(true);
                        managedDocumentForm.setDocumentsUploaded(FALSE);
                        managedDocumentForm.setCounterPartyId(MCXConstants.EMPTY_SPACE);
                    }else{
                        managedDocumentForm.setUploadDocs(false);
                        managedDocumentForm.setDocsUploaded(false);
                    }
                    
                    
                    // To set the delete docs success alert flag to false if the docs are not deleted.
                    if(TRUE.equals(managedDocumentForm.getDocumentsDeleted())){
                        managedDocumentForm.setSelectedDocsDeleted(true);
                        managedDocumentForm.setDocsDeleted(true);
                        managedDocumentForm.setDocumentsDeleted(FALSE);
                    }else{
                        managedDocumentForm.setSelectedDocsDeleted(false);
                        managedDocumentForm.setDocsDeleted(false);
                    }


                    if (managedDocumentForm.getCounterPartyId()!=null && !MCXConstants.EMPTY_SPACE.equals(managedDocumentForm.getCounterPartyId()) )
                    {
                        //For add and edit after the operation is succesfull

                        transactionBean.setSelectedDealerClient(managedDocumentForm.getCounterPartyId()); /*+ DELIMITER + serviceResponse.getDocName());*/
                    } else if(!MCXConstants.EMPTY_SPACE.equals(managedDocumentForm.getSelectedDealerClient()))
                    {
                      //For edit and delete when the window is closed.For upload when error occurs.The 
                        // value is set in request for edit and delete and in uploadManageDocsAction.java for upload
                        transactionBean.setSelectedDealerClient(managedDocumentForm.getSelectedDealerClient());
                        managedDocumentForm.setSelectedDealerClient(MCXConstants.EMPTY_SPACE);
                    } else
                    {
                        //Setting to default value for other scenarios.
                        transactionBean.setSelectedDealerClient(MCXConstants.DEFAULTVALUE);
                    }
                    
                    //Resetting the values
                    transactionBean.setSelectedMCANames(new String[5]);
                    transactionBean.setSelectedCPView(new String[5]);
                    
                    
                    
                    
                    //To set the delete docs success alert flag to false if the docs are not deleted.
                    /*if (!managedDocumentForm.isSelectedDocsDeleted())
                    {
                        managedDocumentForm.setDocsDeleted(false);
                    } else
                    {
                        managedDocumentForm.setSelectedDocsDeleted(false);
                       
                    }*/
                    
                    
                    
                    //To set the upload docs success alert flag to false if the docs are not uploaded.
                    /*if (!managedDocumentForm.isDocsUploaded())
                    {
                        //managedDocumentForm.setUploadFileSizeError(false);
                        managedDocumentForm.setUploadDocs(false);
                    } else
                    {
                        managedDocumentForm.setDocsUploaded(false);
                    }*/
                    
                    //Resetting the values.
                    managedDocumentForm.setCounterPartyId(null);
                    transactionBean.setDealerClientName(null);
                    
                    //To uncheck the check boxes if the operation is other than sorting
                    if(TRUE.equals(managedDocumentForm.getSorting()))
                    {
                        managedDocumentForm.setSorting(FALSE);
                    }
                    else
                    {
                        transactionBean.setSelectedDocuments(null);
                    }

                    log.info("ManageDocsAction action return");
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
