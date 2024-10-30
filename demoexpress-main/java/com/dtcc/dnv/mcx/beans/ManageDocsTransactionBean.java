package com.dtcc.dnv.mcx.beans;

import org.apache.struts.upload.FormFile;

import com.dtcc.dnv.otc.common.layers.ITransactionBean;


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
 * @author G Ravikanth
 * @date Oct 5, 2007
 * @version 1.0
 *
 *
 */

public class ManageDocsTransactionBean implements ITransactionBean
{
    private String selectedDealerClient = "";
    private String reassignedDealerClient = "";
    private String selectedDocumentType = "";
    private String docName = "";
    private String dealerClientName = "";
    private String action = "";
    private String[] selectedDocuments;
    private String counterPartyId = "";

    private String manageDocs = "";
    private String deleteCmpnyId = "";


    private String tempNm = "";
    private String fileName = "";


    private String addRenameFlag = "";

    private FormFile[] formFiles = new FormFile[5];
    private String[] selectedMCANames = new String[5];
    private String[] executedDate = new String[5];

    private String[] selectedCPView  = new String[5];

    private String docId = "";
    private String manageCPStatus = "";
    
    private String[] filesPath = new String[5];
    
    private String duplicateDocsCheck = "";

    /**
     * @return Returns the counterPartyId.
     */
    public String getCounterPartyId()
    {
        return counterPartyId;
    }

    /**
     * @param counterPartyId
     *            The counterPartyId to set.
     */
    public void setCounterPartyId(String counterPartyId)
    {
        this.counterPartyId = counterPartyId;
    }

    /**
     * @return Returns the dealerClientName.
     */
    public String getDealerClientName()
    {
        return dealerClientName;
    }

    /**
     * @param dealerClientName
     *            The dealerClientName to set.
     */
    public void setDealerClientName(String dealerClientName)
    {
        this.dealerClientName = dealerClientName;
    }

    /**
     * @return Returns the docName.
     */
    public String getDocName()
    {
        return docName;
    }

    /**
     * @param docName
     *            The docName to set.
     */
    public void setDocName(String docName)
    {
        this.docName = docName;
    }

    /**
     * @return Returns the selectedDealerClient.
     */
    public String getSelectedDealerClient()
    {
        return selectedDealerClient;
    }

    /**
     * @param selectedDealerClient
     *            The selectedDealerClient to set.
     */
    public void setSelectedDealerClient(String selectedDealerClient)
    {
        this.selectedDealerClient = selectedDealerClient;
    }

    /**
     * @return Returns the selectedDocuments.
     */
    public String[] getSelectedDocuments()
    {
        return selectedDocuments;
    }

    /**
     * @param selectedDocuments
     *            The selectedDocuments to set.
     */
    public void setSelectedDocuments(String[] selectedDocuments)
    {
        this.selectedDocuments = selectedDocuments;
    }

    /**
     * @return Returns the selectedDocumentType.
     */
    public String getSelectedDocumentType()
    {
        return selectedDocumentType;
    }

    /**
     * @param selectedDocumentType
     *            The selectedDocumentType to set.
     */
    public void setSelectedDocumentType(String selectedDocumentType)
    {
        this.selectedDocumentType = selectedDocumentType;
    }

    /**
     * @return Returns the action.
     */
    public String getAction()
    {
        return action;
    }
    /**
     * @param action The action to set.
     */
    public void setAction(String action)
    {
        this.action = action;
    }
    /* (non-Javadoc)
     * @see com.dtcc.dnv.otc.common.layers.ITransactionBean#formatCurrencies()
     */
    public void formatCurrencies()
    {


    }

    /* (non-Javadoc)
     * @see com.dtcc.dnv.otc.common.layers.ITransactionBean#getClone()
     */
    public ITransactionBean getClone() throws CloneNotSupportedException
    {

        return null;
    }

    /**
     * @return Returns the reassignedDealerClient.
     */
    public String getReassignedDealerClient()
    {
        return reassignedDealerClient;
    }
    /**
     * @param reassignedDealerClient The reassignedDealerClient to set.
     */
    public void setReassignedDealerClient(String reassignedDealerClient)
    {
        this.reassignedDealerClient = reassignedDealerClient;
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
     * @return Returns the fileName.
     */
    public String getFileName()
    {
        return fileName;
    }
    /**
     * @param fileName The fileName to set.
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    /**
     * @return Returns the tempId.
     */
    public String getTempNm()
    {
        return tempNm;
    }
    /**
     * @param tempId The tempId to set.
     */
    public void setTempNm(String tempNm)
    {
        this.tempNm = tempNm;
    }



    /**
     * @return Returns the addRenameFlag.
     */
    public String getAddRenameFlag()
    {
        return addRenameFlag;
    }
    /**
     * @param addRenameFlag The addRenameFlag to set.
     */
    public void setAddRenameFlag(String addRenameFlag)
    {
        this.addRenameFlag = addRenameFlag;
    }


    /**
     * @return Returns the formFiles.
     */
    public FormFile[] getUploads()
    {
        return this.formFiles;
    }
    /**
     * @return Returns the formFiles.
     */
    public FormFile getUploads(int iIndex)
    {
        return this.formFiles[iIndex];
    }
    /**
     * @param formFiles The formFiles to set.
     */
    public void setUploads(int iIndex, FormFile formFile)
    {
        this.formFiles[iIndex] = formFile;
    }

    /**
     * @return Returns the executedDate.
     */
    public String[] getExecutedDate()
    {
        return this.executedDate;
    }
    /**
     * @return Returns the executedDate.
     */
    public String getExecutedDate(int iIndex)
    {
        return this.executedDate[iIndex];
    }
    /**
     * @param executedDate The executedDate to set.
     */
    public void setExecutedDate(int iIndex, String executedDate)
    {
        this.executedDate[iIndex] = executedDate;
    }

    /**
     * @return Returns the selectedMCANames.
     */
    public String[] getSelectedMCANames()
    {
        return this.selectedMCANames;
    }
    /**
     * @return Returns the selectedMCANames.
     */
    public String getSelectedMCANames(int iIndex)
    {
        return this.selectedMCANames[iIndex];
    }
    /**
     * @param selectedMCANames The selectedMCANames to set.
     */
    public void setSelectedMCANames(int iIndex, String selectedMCANames)
    {
        this.selectedMCANames[iIndex] = selectedMCANames;
    }


    /**
     * @param selectedMCANames The selectedMCANames to set.
     */
    public void setSelectedMCANames(String[] selectedMCANames)
    {
        this.selectedMCANames = selectedMCANames;
    }

    /**
     * @return Returns the selectedCPView.
     */
    public String[] getSelectedCPView()
    {
        return this.selectedCPView;
    }
    /**
     * @param selectedCPView The selectedCPView to get.
     */
    public String getSelectedCPView(int iIndex)
    {
        return this.selectedCPView[iIndex];
    }
    /**
     * @param selectedCPView The selectedCPView to set.
     */
    public void setSelectedCPView(int iIndex, String selectedCPView)
    {
        this.selectedCPView[iIndex] = selectedCPView;
    }


    /**
     * @param selectedCPView The selectedCPView to set.
     */
    public void setSelectedCPView(String[] selectedCPView)
    {
        this.selectedCPView = selectedCPView;
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
     * @return Returns the manageCPStatus.
     */
    public String getManageCPStatus()
    {
        return manageCPStatus;
    }
    /**
     * @param manageCPStatus The manageCPStatus to set.
     */
    public void setManageCPStatus(String manageCPStatus)
    {
        this.manageCPStatus = manageCPStatus;
    }
    
    
    
    
    /**
     * @return Returns the filesPaths.
     */
    public String[] getFilesPath()
    {
        return filesPath;
    }
    /**
     * @param filesPaths The filesPaths to set.
     */
    public void setFilesPath(String[] filesPath)
    {
        this.filesPath = filesPath;
    }
    
    /**
     * @param selectedCPView The selectedCPView to get.
     */
    public String getFilesPath(int iIndex)
    {
        return this.filesPath[iIndex];
    }
    /**
     * @param selectedCPView The selectedCPView to set.
     */
    public void setFilesPath(int iIndex, String filesPath)
    {
        this.filesPath[iIndex] = filesPath;
    }
    
    
    
    /**
     * @return Returns the duplicateDocsCheck.
     */
    public String getDuplicateDocsCheck()
    {
        return duplicateDocsCheck;
    }
    /**
     * @param duplicateDocsCheck The duplicateDocsCheck to set.
     */
    public void setDuplicateDocsCheck(String duplicateDocsCheck)
    {
        this.duplicateDocsCheck = duplicateDocsCheck;
    }
}
