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

import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.beans.ManageDocsTransactionBean;
import com.dtcc.dnv.mcx.delegate.managedocs.SearchDelegate;
import com.dtcc.dnv.mcx.delegate.managedocs.SearchServiceRequest;
import com.dtcc.dnv.mcx.delegate.managedocs.SearchServiceResponse;
import com.dtcc.dnv.mcx.form.ManagedDocumentForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
 * The action method for search functionality.
 * 
 * RTM Reference : 3.3.16 Basic Flow (Except delete documents) Searching for the
 * 	documents based on the parameters Dealer/Client Id,Document type, Document
 * 	Name. Displays Dealer/ClientId MCA/File name,Document type, Execution date,CP
 * 	Viewable,uploaded by user,uploaded on date that satisfy the search criteria.
 * RTM Reference : 3.3.16.1 Actor wants to search for “Other documents” RTM
 * Reference : 3.3.16.2 Actor wants to search for “All” documents against “All”
 * 	counterparties 
 * RTM Reference : 3.3.16.3 Actor wants to search for “All” documents against 
 * 	specific counterparty 
 * RTM Reference : 3.3.16.4 Actor wants to clear search 
 * RTM Reference : 3.3.16.11 Actor searches for a specific document that does 
 * 	not exist in the system 
 * RTM Reference : 3.3.16.12 The document library tab under the “search documents”
 * 	 should display the file name if the documents uploaded is anything other 
 * 	than pre-executed MCA RTM
 * Reference : 3.3.16.13 The document library tab under the “search documents”
 * 	should display the MCA name if the documents uploaded is a pre-executed MCA
 * RTM Reference : 3.3.16.15 Execution Date should be available only in case of
 *	Pre-Exec documents and it should be blank for “other documents” 
 *RTM Reference : 3.3.16.16 The “MCA name” should not have a link if it is just
 *	 a placeholder and there is no document uploaded for a pre-existing MCA 
 *
 *Copyright © 2003 The Depository Trust & Clearing Company. All rights reserved.
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
public class SearchAction extends MCXBaseAction
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(SearchAction.class.getName());

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
        List dealerClientDetails = new ArrayList();

        String cmpnyId;
        String parameter = new String();
        final String SEARCH = "Search";
        final String TRUE = "true";
        final String FALSE = "false";

        try
        {
            
            log.info("In search action");
            ManagedDocumentForm managedDocumentForm = new ManagedDocumentForm();
            managedDocumentForm = (ManagedDocumentForm) form;
            ManageDocsTransactionBean transactionBean = new ManageDocsTransactionBean();
            transactionBean = (ManageDocsTransactionBean) managedDocumentForm.getTransaction();
            
            String selectedDealerClient = (String)request.getAttribute("selectedDealerClient");
            String selectedDocumentType = (String)request.getAttribute("selectedDocumentType");
            String docName = (String)request.getAttribute("docName");

            if (transactionBean != null)
            {
                
                //parameter = transactionBean.getAction();
                parameter = managedDocumentForm.getManageDocs();
                cmpnyId = user.getCompanyId();
                if(selectedDealerClient!=null){
                    transactionBean.setSelectedDealerClient(selectedDealerClient);
                    transactionBean.setSelectedDocumentType(selectedDocumentType);
                    transactionBean.setDocName(docName);
                }

                if (MCXConstants.SEARCHCONST.equals(parameter))
                {
                    log.info("In search of search action");
                    SearchServiceRequest serviceRequest = new SearchServiceRequest(user.getAuditInfo());

                    SearchServiceResponse serviceResponse = new SearchServiceResponse();
                    SearchDelegate delegate = new SearchDelegate();
                    
                    serviceRequest.setCompanyId(cmpnyId);
                    serviceRequest.setTransaction(transactionBean);

                    serviceResponse = (SearchServiceResponse) delegate.processRequest(serviceRequest);
                    if (!serviceResponse.hasError())
                    {
                        managedDocumentForm.setDealerClient(serviceResponse.getDealerClient());
                        dealerClientDetails = serviceResponse.getDealerClientDetails();
                        if (dealerClientDetails != null)
                        {
                            managedDocumentForm.setDealerClientDetails(dealerClientDetails);
                            managedDocumentForm.setNumberOfDocuments(dealerClientDetails.size());
                        }
                        managedDocumentForm.setManageDocTab(SEARCH);
                        
                        //  To set the delete docs success alert flag to false if the docs are not deleted.
                        if(TRUE.equals(managedDocumentForm.getDocumentsDeleted())){
                            managedDocumentForm.setSelectedDocsDeleted(true);
                            managedDocumentForm.setDocsDeleted(true);
                            managedDocumentForm.setDocumentsDeleted(MCXConstants.EMPTY);
                        }else{
                            managedDocumentForm.setSelectedDocsDeleted(false);
                            managedDocumentForm.setDocsDeleted(false);
                            managedDocumentForm.setDocumentsDeleted(MCXConstants.EMPTY);
                        }
                        
                        
                        // To set the delete docs success alert flag to false if the docs are not deleted.
                        /*if (!managedDocumentForm.isSelectedDocsDeleted())
                        {
                            managedDocumentForm.setDocsDeleted(false);
                        } else
                        {
                            managedDocumentForm.setSelectedDocsDeleted(false);
                        }*/
                        
                        //To uncheck the check boxes if the operation is other than sorting
                        if(TRUE.equals(managedDocumentForm.getSorting()))
                        {
                            managedDocumentForm.setSorting(FALSE);
                        }
                        else
                        {
                            transactionBean.setSelectedDocuments(null);
                        }
                        
                    } else
                    {
                        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", serviceResponse.getSpReturnMessage()));
                    }

                } else if (MCXConstants.CLEARCONST.equals(parameter))
                {
                    log.info("In clear of search action");

                    transactionBean.setSelectedDealerClient(MCXConstants.ALL);
                    transactionBean.setSelectedDocumentType(MCXConstants.ALL);
                    transactionBean.setDocName(MCXConstants.EMPTY_SPACE);
                    managedDocumentForm.setNumberOfDocuments(-1);
                    managedDocumentForm.setDocsDeleted(false);
                    //transactionBean.setSelectedDocumentType(MCXConstants.EMPTY_SPACE);
                }
                log.info("Returning from Search action");
                forward = mapping.findForward(MCXConstants.SUCCESS_FORWARD);
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
