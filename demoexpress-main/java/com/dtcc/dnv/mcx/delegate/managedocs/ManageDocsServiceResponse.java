/*
 * Created on Sep 14, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.delegate.managedocs;

import java.util.ArrayList;
import java.util.List;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceResponse;

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
 * @date Sep 14, 2007
 * @version 1.0
 * 
 * 
 */

public class ManageDocsServiceResponse extends MCXAbstractServiceResponse
{
    
    private List dealerClient = new ArrayList();
    private List dealerClientDetails = new ArrayList();
    private List isdaTemplateList = new ArrayList();
    
    private String docName = "";
    private byte[] docFile = null;
    private boolean deleteCPWindow = false;
    private String documentInd = "";

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
     * @return Returns the docFile.
     */
    public byte[] getDocFile()
    {
        return docFile;
    }
    /**
     * @param docFile The docFile to set.
     */
    public void setDocFile(byte[] docFile)
    {
        this.docFile = docFile;
    }
    /**
     * @return Returns the docName.
     */
    public String getDocName()
    {
        return docName;
    }
    /**
     * @param docName The docName to set.
     */
    public void setDocName(String docName)
    {
        this.docName = docName;
    }    
    /**
     * @return Returns the isdaTemplateList.
     */
    public List getIsdaTemplateList()
    {
        return isdaTemplateList;
    }
    /**
     * @param isdaTemplateList The isdaTemplateList to set.
     */
    public void setIsdaTemplateList(List isdaTemplateList)
    {
        this.isdaTemplateList = isdaTemplateList;
    }
    
    
    /**
     * @return Returns the deleteCPWindow.
     */
    public boolean isDeleteCPWindow()
    {
        return deleteCPWindow;
    }
    /**
     * @param deleteCPWindow The deleteCPWindow to set.
     */
    public void setDeleteCPWindow(boolean deleteCPWindow)
    {
        this.deleteCPWindow = deleteCPWindow;
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
}
