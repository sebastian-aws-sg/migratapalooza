package com.dtcc.dnv.mcx.form;

import java.util.ArrayList;
import java.util.List;

import com.dtcc.dnv.mcx.beans.ManageDocsTransactionBean;

/**
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
 * @date Sep 5, 2007
 * @version 1.0
 * 
 *  
 */
public class ManagedDocumentForm extends MCXActionForm
{

    public ManagedDocumentForm()
    {
        ManageDocsTransactionBean bean = new ManageDocsTransactionBean();
        super.setTransaction(bean);
    }
    
    
    private List dealerClient = new ArrayList();
    private List dealerClientDetails = new ArrayList();
    private List reassignList = new ArrayList();
    private int numberOfDocuments;
   
    private String deleteCmpnyId = "";
    private String manageDocs = "";
   
    private String documentInd = "";
    private List mcaNames = new ArrayList();
   
    private String manageDocTab = "";
    private String selectedDealerClient = "";

   
    private boolean docsDeleted = false;
    
    private String counterPartyId = "";
    private String docId = "";
    private String cmpnyId = "";
    private boolean uploadFileSizeError = false;
    
    private boolean uploadDocs = false;
    
    private boolean selectedDocsDeleted = false;
    private boolean docsUploaded = false;
    
    private String sorting = "false";
    
    private String documentsDeleted = "";
    private String documentsUploaded = "";
    
   
    /**
     * @return Returns the dealerClient.
     */
    public List getDealerClient()
    {
        return dealerClient;
    }
    /**
     * @param dealerClient The dealerClient to set.
     */
    public void setDealerClient(List dealerClient)
    {
        this.dealerClient = dealerClient;
    }
    /**
     * @return Returns the dealerClientDetails.
     */
    public List getDealerClientDetails()
    {
        return dealerClientDetails;
    }
    /**
     * @param dealerClientDetails The dealerClientDetails to set.
     */
    public void setDealerClientDetails(List dealerClientDetails)
    {
        this.dealerClientDetails = dealerClientDetails;
    }
    /**
     * @return Returns the numberOfDocuments.
     */
    public int getNumberOfDocuments()
    {
        return numberOfDocuments;
    }
    /**
     * @param numberOfDocuments The numberOfDocuments to set.
     */
    public void setNumberOfDocuments(int numberOfDocuments)
    {
        this.numberOfDocuments = numberOfDocuments;
    }
    
    
    /**
     * @return Returns the reassignList.
     */
    public List getReassignList()
    {
        return reassignList;
    }
    /**
     * @param reassignList The reassignList to set.
     */
    public void setReassignList(List reassignList)
    {
        this.reassignList = reassignList;
    }
    /**
     * @return Returns the deleteCmpnyId.
     */
    public String getDeleteCmpnyId()
    {
        return deleteCmpnyId;
    }
    /**
     * @param deleteCmpnyId The deleteCmpnyId to set.
     */
    public void setDeleteCmpnyId(String deleteCmpnyId)
    {
        this.deleteCmpnyId = deleteCmpnyId;
    }
    /**
     * @return Returns the manageDocs.
     */
    public String getManageDocs()
    {
        return manageDocs;
    }
    /**
     * @param manageDocs The manageDocs to set.
     */
    public void setManageDocs(String manageDocs)
    {
        this.manageDocs = manageDocs;
    }    
   
    
    /**
     * @return Returns the documentInd.
     */
    public String getDocumentInd()
    {
        return documentInd;
    }
    /**
     * @param documentInd The documentInd to set.
     */
    public void setDocumentInd(String documentInd)
    {
        this.documentInd = documentInd;
    }
    
    
    /**
     * @return Returns the mcaNames.
     */
    public List getMcaNames()
    {
        return mcaNames;
    }
    /**
     * @param mcaNames The mcaNames to set.
     */
    public void setMcaNames(List mcaNames)
    {
        this.mcaNames = mcaNames;
    }
    
    
    /**
     * @return Returns the manageDocTab.
     */
    public String getManageDocTab()
    {
        return manageDocTab;
    }
    /**
     * @param manageDocTab The manageDocTab to set.
     */
    public void setManageDocTab(String manageDocTab)
    {
        this.manageDocTab = manageDocTab;
    }
    
    /**
     * @return Returns the docsDeleted.
     */
    public boolean isDocsDeleted()
    {
        return docsDeleted;
    }
    /**
     * @param docsDeleted The docsDeleted to set.
     */
    public void setDocsDeleted(boolean docsDeleted)
    {
        this.docsDeleted = docsDeleted;
    }
    
    
    /**
     * @return Returns the counterPartyId.
     */
    public String getCounterPartyId()
    {
        return counterPartyId;
    }
    /**
     * @param counterPartyId The counterPartyId to set.
     */
    public void setCounterPartyId(String counterPartyId)
    {
        this.counterPartyId = counterPartyId;
    }
    
    
    /**
     * @return Returns the docId.
     */
    public String getDocId()
    {
        return docId;
    }
    /**
     * @param docId The docId to set.
     */
    public void setDocId(String docId)
    {
        this.docId = docId;
    }
    
    
    /**
     * @return Returns the cmpnyId.
     */
    public String getCmpnyId()
    {
        return cmpnyId;
    }
    /**
     * @param cmpnyId The cmpnyId to set.
     */
    public void setCmpnyId(String cmpnyId)
    {
        this.cmpnyId = cmpnyId;
    }
    
    /**
     * @return Returns the uploadFileSizeError.
     */
    public boolean isUploadFileSizeError()
    {
        return uploadFileSizeError;
    }
    /**
     * @param uploadFileSizeError The uploadFileSizeError to set.
     */
    public void setUploadFileSizeError(boolean uploadFileSizeError)
    {
        this.uploadFileSizeError = uploadFileSizeError;
    }
    
    
    /**
     * @return Returns the selectedDealerClient.
     */
    public String getSelectedDealerClient()
    {
        return selectedDealerClient;
    }
    /**
     * @param selectedDealerClient The selectedDealerClient to set.
     */
    public void setSelectedDealerClient(String selectedDealerClient)
    {
        this.selectedDealerClient = selectedDealerClient;
    }
    
    
    
    /**
     * @return Returns the uploadDocs.
     */
    public boolean isUploadDocs()
    {
        return uploadDocs;
    }
    /**
     * @param uploadDocs The uploadDocs to set.
     */
    public void setUploadDocs(boolean uploadDocs)
    {
        this.uploadDocs = uploadDocs;
    }
    
    
    /**
     * @return Returns the selectedDocsDeleted.
     */
    public boolean isSelectedDocsDeleted()
    {
        return selectedDocsDeleted;
    }
    /**
     * @param selectedDocsDeleted The selectedDocsDeleted to set.
     */
    public void setSelectedDocsDeleted(boolean selectedDocsDeleted)
    {
        this.selectedDocsDeleted = selectedDocsDeleted;
    }
    
    /**
     * @return Returns the docsUploaded.
     */
    public boolean isDocsUploaded()
    {
        return docsUploaded;
    }
    /**
     * @param docsUploaded The docsUploaded to set.
     */
    public void setDocsUploaded(boolean docsUploaded)
    {
        this.docsUploaded = docsUploaded;
    }
    /**
     * @return Returns the sorting.
     */
    public String getSorting()
    {
        return sorting;
    }
    /**
     * @param sorting The sorting to set.
     */
    public void setSorting(String sorting)
    {
        this.sorting = sorting;
    }
    
    
    /**
     * @return Returns the documentsDeleted.
     */
    public String getDocumentsDeleted()
    {
        return documentsDeleted;
    }
    /**
     * @param documentsDeleted The documentsDeleted to set.
     */
    public void setDocumentsDeleted(String documentsDeleted)
    {
        this.documentsDeleted = documentsDeleted;
    }
    
    
    
    /**
     * @return Returns the documentsUploaded.
     */
    public String getDocumentsUploaded()
    {
        return documentsUploaded;
    }
    /**
     * @param documentsUploaded The documentsUploaded to set.
     */
    public void setDocumentsUploaded(String documentsUploaded)
    {
        this.documentsUploaded = documentsUploaded;
    }
    
    
    
}