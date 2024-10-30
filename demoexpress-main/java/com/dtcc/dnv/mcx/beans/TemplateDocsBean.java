package com.dtcc.dnv.mcx.beans;


/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55, Water Street,
 * New York, NY 10048
 * U.S.A
 * All Rights Reserved.
 *
 *     This software may contain (in part or full) confidential/proprietary 
 * information of DTCC.("Confidential Information"). Disclosure of such 
 * Confidential Information is prohibited and should be used only for its 
 * intended purpose in accordance with rules and regulations of DTCC.
 * Form bean for a Struts application.
 *
 * @version 	1.0
 * @author     Narahari Adige
 *  	 
 */
public class TemplateDocsBean extends TemplateBean
{

    private String mcaRegisteredIndicator = ""; //MCA Registered user or   Manually Added
    private String docId = ""; //Document Id 
    private String docName = ""; //Document Name private String executedMcaFileName = "";
    private String mcaTemplateName = "";
    private String executedMcaFileName = "";	
    private String cpViewable = "";
    private String mcaStatusMsg = ""; //Mca Status
    private String lockInd = ""; // Lock Indicator
    private String orgCltEmail = ""; //Orgonization Email*/
    //private String mcaStatusCd = ""; // MCA Status Code
    private String  lockByInd       = "";   // locked by indicator
    private String tmpltLngNm = "";
    private String documentInd      = "";   // Manage Document Indicator
    private String tmpltIdDetails   = "";   // Manage Document Temp Id details
    private String currentCompanyId = "";
	private String currentUserId = "";

    /**
     * @return Returns the cpViewable.
     */
    public String getCpViewable()
    {
        return cpViewable;
    }
    /**
     * @param cpViewable The cpViewable to set.
     */
    public void setCpViewable(String cpViewable)
    {
        this.cpViewable = cpViewable;
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
     * @return Returns the lockByInd.
     */
    public String getLockByInd()
    {
        return lockByInd;
    }
    /**
     * @param lockByInd The lockByInd to set.
     */
    public void setLockByInd(String lockByInd)
    {
        this.lockByInd = lockByInd;
    }
    /**
     * @return Returns the lockInd.
     */
    public String getLockInd()
    {
        return lockInd;
    }
    /**
     * @param lockInd The lockInd to set.
     */
    public void setLockInd(String lockInd)
    {
        this.lockInd = lockInd;
    }
    /**
     * @return Returns the mcaRegisteredIndicator.
     */
    public String getMcaRegisteredIndicator()
    {
        return mcaRegisteredIndicator;
    }
    /**
     * @param mcaRegisteredIndicator The mcaRegisteredIndicator to set.
     */
    public void setMcaRegisteredIndicator(String mcaRegisteredIndicator)
    {
        this.mcaRegisteredIndicator = mcaRegisteredIndicator;
    }
    
    /**
     * @return Returns the mcaStatusMsg.
     */
    public String getMcaStatusMsg()
    {
        return mcaStatusMsg;
    }
    /**
     * @param mcaStatusMsg The mcaStatusMsg to set.
     */
    public void setMcaStatusMsg(String mcaStatusMsg)
    {
        this.mcaStatusMsg = mcaStatusMsg;
    }
    /**
     * @return Returns the mcaTemplateName.
     */
    public String getMcaTemplateName()
    {
        return mcaTemplateName;
    }
    /**
     * @param mcaTemplateName The mcaTemplateName to set.
     */
    public void setMcaTemplateName(String mcaTemplateName)
    {
        this.mcaTemplateName = mcaTemplateName;
    }
    /**
     * @return Returns the orgCltEmail.
     */
    public String getOrgCltEmail()
    {
        return orgCltEmail;
    }
    /**
     * @param orgCltEmail The orgCltEmail to set.
     */
    public void setOrgCltEmail(String orgCltEmail)
    {
        this.orgCltEmail = orgCltEmail;
    }
    /**
     * @return Returns the tmpltIdDetails.
     */
    public String getTmpltIdDetails()
    {
        return tmpltIdDetails;
    }
    /**
     * @param tmpltIdDetails The tmpltIdDetails to set.
     */
    public void setTmpltIdDetails(String tmpltIdDetails)
    {
        this.tmpltIdDetails = tmpltIdDetails;
    }
    /**
     * @return Returns the tmpltLngNm.
     */
    public String getTmpltLngNm()
    {
        return tmpltLngNm;
    }
    /**
     * @param tmpltLngNm The tmpltLngNm to set.
     */
    public void setTmpltLngNm(String tmpltLngNm)
    {
        this.tmpltLngNm = tmpltLngNm;
    }
    
    /**
     * @return Returns the executedMcaFileName.
     */
    public String getExecutedMcaFileName()
    {
        return executedMcaFileName;
    }
    /**
     * @param executedMcaFileName The executedMcaFileName to set.
     */
    public void setExecutedMcaFileName(String executedMcaFileName)
    {
        this.executedMcaFileName = executedMcaFileName;
    }
    /**
     * @return Returns the currentCompanyId.
     */
    public String getCurrentCompanyId()
    {
        return currentCompanyId;
    }
    /**
     * @param currentCompanyId The currentCompanyId to set.
     */
    public void setCurrentCompanyId(String currentCompanyId)
    {
        this.currentCompanyId = currentCompanyId;
    }
    /**
     * @return Returns the currentUserId.
     */
    public String getCurrentUserId()
    {
        return currentUserId;
    }
    /**
     * @param currentUserId The currentUserId to set.
     */
    public void setCurrentUserId(String currentUserId)
    {
        this.currentUserId = currentUserId;
    }
}