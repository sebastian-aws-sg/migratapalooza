package com.dtcc.dnv.mcx.dbhelper.managedocs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.dbhelper.MCXAbstractDbResponse;

/**
 * The dbResponse to get dealers list.
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
 * @date Sep 6, 2007
 * @version 1.0
 * 
 *  
 */
public class DealerClientListDbResponse extends MCXAbstractDbResponse
{

    private List dealerClientList = new ArrayList();
    private List isdaTemplateList = new ArrayList();
    
    private String docName = "";
    private byte[] docFile = null;
    private Map images 	   = null;
    private String documentInd = "";

    /**
     * @return Returns the dealerClientList
     */
    public List getDealerClientList()
    {
        return dealerClientList;
    }

    /**
     * @param list
     */
    public void addDealerClientList(TemplateBean dealerClient)
    {
        this.dealerClientList.add(dealerClient);
    }

    public void addDealerClientList(int index, TemplateBean dealerClient)
    {
        this.dealerClientList.add(index, dealerClient);
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
     * @param list
     */
    public void addIsdaTemplateList(TemplateBean dealerClient)
    {
        this.isdaTemplateList.add(dealerClient);
    }

    public void addIsdaTemplateList(int index, TemplateBean dealerClient)
    {
        this.isdaTemplateList.add(index, dealerClient);
    }    
	/**
	 * @return Returns the images.
	 */
	public Map getImages() {
		return images;
	}
	/**
	 * @param images The images to set.
	 */
	public void setImages(Map images) {
		this.images = images;
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
