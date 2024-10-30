/*
 * Created on Aug 30, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dtcc.dnv.mcx.beans;

import java.util.Date;

/**
 * @author kjain
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DealerClientList
{

    private String dealerClientId = "";
    private String dealerClientName = "";
    private String mcaFileName = "";
    private String docType = "";
    private Date executionDate;
    private String cpViewable = "";
    private String uploadedByUser = "";
    private Date uploadedOn;
    private String[] selectedDealers = new String[0];
    private String termDefinition;
    private String mcaStatus;
    private String modifiedUser;
    private Date modifiedTime;
    private String mcaTemplateId;
    private String lockIndicator;
    private String lockIndicatorColour;
    private String executedMcaFileName;
    private String lockedUser = " ";
    private String userId = " ";
    private String docName = "";
    private String organizationIndicator = "";

    //Added for Enrollment/Pending Approval modules
    private String mcaTempId = "";
    private String userEmail = "";
    private String prodCode = "";
    private String prodDesc = "";
    private String subProdCode = "";
    private String subProdDesc = "";
    private String regCode = "";
    private String regDesc = "";
    private String mcaPubYear = "";
    private String mcaStatusMsg = "";
    private String mcaName = "";
    private String action = "";
    private String[] selectedMCAs;
    //private String yes ="";
    private String status = "";
    private String appORDeny = "";
    private String all = "";
    private String pendingEnrollments = "D";

    //Added for Manage documents
    private String mcaTempName = "";
    private String docId;
    private String mcaRegisteredIndicator = "";
    private String documentIndicator = "";
    private String mcaTempShortName = "";

    /**
     * @return Returns the executedMcaFileName.
     */
    public String getExecutedMcaFileName()
    {
        return executedMcaFileName;
    }

    /**
     * @param executedMcaFileName
     *            The executedMcaFileName to set.
     */
    public void setExecutedMcaFileName(String executedMcaFileName)
    {
        this.executedMcaFileName = executedMcaFileName;
    }

    /**
     * @return Returns the selectedDealers.
     */
    public String[] getSelectedDealers()
    {
        return selectedDealers;
    }

    /**
     * @param selectedDealers
     *            The selectedDealers to set.
     */
    public void setSelectedDealers(String[] selectedDealers)
    {
        this.selectedDealers = selectedDealers;
    }

    /**
     * @return
     */
    public String getDealerClientId()
    {
        return dealerClientId;
    }

    /**
     * @return
     */
    public String getDealerClientName()
    {
        return dealerClientName;
    }

    /**
     * @param string
     */
    public void setDealerClientId(String string)
    {
        dealerClientId = string;
    }

    /**
     * @param string
     */
    public void setDealerClientName(String string)
    {
        dealerClientName = string;
    }

    /**
     * @return
     */
    public String getCpViewable()
    {
        return cpViewable;
    }

    /**
     * @return
     */
    public String getDocType()
    {
        return docType;
    }

    /**
     * @return
     */
    public Date getExecutionDate()
    {
        return executionDate;
    }

    /**
     * @return
     */
    public String getMcaFileName()
    {
        return mcaFileName;
    }

    /**
     * @return
     */
    public String getUploadedByUser()
    {
        return uploadedByUser;
    }

    /**
     * @return
     */
    public Date getUploadedOn()
    {
        return uploadedOn;
    }

    /**
     * @param string
     */
    public void setCpViewable(String string)
    {
        cpViewable = string;
    }

    /**
     * @param string
     */
    public void setDocType(String string)
    {
        docType = string;
    }

    /**
     * @param date
     */
    public void setExecutionDate(Date date)
    {
        executionDate = date;
    }

    /**
     * @param string
     */
    public void setMcaFileName(String string)
    {
        mcaFileName = string;
    }

    /**
     * @param string
     */
    public void setUploadedByUser(String string)
    {
        uploadedByUser = string;
    }

    /**
     * @param date
     */
    public void setUploadedOn(Date date)
    {
        uploadedOn = date;
    }

    /**
     * @return Returns the mcaStatus.
     */
    public String getMcaStatus()
    {
        return mcaStatus;
    }

    /**
     * @param mcaStatus
     *            The mcaStatus to set.
     */
    public void setMcaStatus(String mcaStatus)
    {
        this.mcaStatus = mcaStatus;
    }

    /**
     * @return Returns the modifiedUser.
     */
    public String getModifiedUser()
    {
        return modifiedUser;
    }

    /**
     * @param modifiedUser
     *            The modifiedUser to set.
     */
    public void setModifiedUser(String modifiedUser)
    {
        this.modifiedUser = modifiedUser;
    }

    /**
     * @return Returns the termDefinition.
     */
    public String getTermDefinition()
    {
        return termDefinition;
    }

    /**
     * @param termDefinition
     *            The termDefinition to set.
     */
    public void setTermDefinition(String termDefinition)
    {
        this.termDefinition = termDefinition;
    }

    /**
     * @return Returns the modifiedTime.
     */
    public Date getModifiedTime()
    {
        return modifiedTime;
    }

    /**
     * @param modifiedTime
     *            The modifiedTime to set.
     */
    public void setModifiedTime(Date modifiedTime)
    {
        this.modifiedTime = modifiedTime;
    }

    /**
     * @return Returns the mcaTemplateId.
     */
    public String getMcaTemplateId()
    {
        return mcaTemplateId;
    }

    /**
     * @param mcaTemplateId
     *            The mcaTemplateId to set.
     */
    public void setMcaTemplateId(String mcaTemplateId)
    {
        this.mcaTemplateId = mcaTemplateId;
    }

    /**
     * @return Returns the action.
     */
    public String getAction()
    {
        return action;
    }

    /**
     * @param action
     *            The action to set.
     */
    public void setAction(String action)
    {
        this.action = action;
    }

    /**
     * @return Returns the appORDeny.
     */
    public String getAppORDeny()
    {
        return appORDeny;
    }

    /**
     * @param appORDeny
     *            The appORDeny to set.
     */
    public void setAppORDeny(String appORDeny)
    {
        this.appORDeny = appORDeny;
    }

    /**
     * @return Returns the mcaName.
     */
    public String getMcaName()
    {
        return mcaName;
    }

    /**
     * @param mcaName
     *            The mcaName to set.
     */
    public void setMcaName(String mcaName)
    {
        this.mcaName = mcaName;
    }

    /**
     * @return Returns the mcaPubYear.
     */
    public String getMcaPubYear()
    {
        return mcaPubYear;
    }

    /**
     * @param mcaPubYear
     *            The mcaPubYear to set.
     */
    public void setMcaPubYear(String mcaPubYear)
    {
        this.mcaPubYear = mcaPubYear;
    }

    /**
     * @return Returns the mcaStatusMsg.
     */
    public String getMcaStatusMsg()
    {
        return mcaStatusMsg;
    }

    /**
     * @param mcaStatusMsg
     *            The mcaStatusMsg to set.
     */
    public void setMcaStatusMsg(String mcaStatusMsg)
    {
        this.mcaStatusMsg = mcaStatusMsg;
    }

    /**
     * @return Returns the mcaTempId.
     */
    public String getMcaTempId()
    {
        return mcaTempId;
    }

    /**
     * @param mcaTempId
     *            The mcaTempId to set.
     */
    public void setMcaTempId(String mcaTempId)
    {
        this.mcaTempId = mcaTempId;
    }

    /**
     * @return Returns the prodCode.
     */
    public String getProdCode()
    {
        return prodCode;
    }

    /**
     * @param prodCode
     *            The prodCode to set.
     */
    public void setProdCode(String prodCode)
    {
        this.prodCode = prodCode;
    }

    /**
     * @return Returns the prodDesc.
     */
    public String getProdDesc()
    {
        return prodDesc;
    }

    /**
     * @param prodDesc
     *            The prodDesc to set.
     */
    public void setProdDesc(String prodDesc)
    {
        this.prodDesc = prodDesc;
    }

    /**
     * @return Returns the regCode.
     */
    public String getRegCode()
    {
        return regCode;
    }

    /**
     * @param regCode
     *            The regCode to set.
     */
    public void setRegCode(String regCode)
    {
        this.regCode = regCode;
    }

    /**
     * @return Returns the regDesc.
     */
    public String getRegDesc()
    {
        return regDesc;
    }

    /**
     * @param regDesc
     *            The regDesc to set.
     */
    public void setRegDesc(String regDesc)
    {
        this.regDesc = regDesc;
    }

    /**
     * @return Returns the selectedMCAs.
     */
    public String[] getSelectedMCAs()
    {
        return selectedMCAs;
    }

    /**
     * @param selectedMCAs
     *            The selectedMCAs to set.
     */
    public void setSelectedMCAs(String[] selectedMCAs)
    {
        this.selectedMCAs = selectedMCAs;
    }

    /**
     * @return Returns the status.
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            The status to set.
     */
    public void setStatus(String status)
    {
        this.status = status;
    }

    /**
     * @return Returns the subProdCode.
     */
    public String getSubProdCode()
    {
        return subProdCode;
    }

    /**
     * @param subProdCode
     *            The subProdCode to set.
     */
    public void setSubProdCode(String subProdCode)
    {
        this.subProdCode = subProdCode;
    }

    /**
     * @return Returns the subProdDesc.
     */
    public String getSubProdDesc()
    {
        return subProdDesc;
    }

    /**
     * @param subProdDesc
     *            The subProdDesc to set.
     */
    public void setSubProdDesc(String subProdDesc)
    {
        this.subProdDesc = subProdDesc;
    }

    /**
     * @return Returns the userEmail.
     */
    public String getUserEmail()
    {
        return userEmail;
    }

    /**
     * @param userEmail
     *            The userEmail to set.
     */
    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    /**
     * @return Returns the docId.
     */
    public String getDocId()
    {
        return docId;
    }

    /**
     * @param docId
     *            The docId to set.
     */
    public void setDocId(String docId)
    {
        this.docId = docId;
    }

    /**
     * @return Returns the mcaTempName.
     */
    public String getMcaTempName()
    {
        return mcaTempName;
    }

    /**
     * @param mcaTempName
     *            The mcaTempName to set.
     */
    public void setMcaTempName(String mcaTempName)
    {
        this.mcaTempName = mcaTempName;
    }

    /**
     * @return Returns the mcaRegisteredIndicator.
     */
    public String getMcaRegisteredIndicator()
    {
        return mcaRegisteredIndicator;
    }

    /**
     * @param mcaRegisteredIndicator
     *            The mcaRegisteredIndicator to set.
     */
    public void setMcaRegisteredIndicator(String mcaRegisteredIndicator)
    {
        this.mcaRegisteredIndicator = mcaRegisteredIndicator;
    }

    /**
     * @return Returns the lockIndicator.
     */
    public String getLockIndicator()
    {
        return lockIndicator;
    }

    /**
     * @param lockIndicator
     *            The lockIndicator to set.
     */
    public void setLockIndicator(String lockIndicator)
    {
        this.lockIndicator = lockIndicator;
    }

    /**
     * @return Returns the documentIndicator.
     */
    public String getDocumentIndicator()
    {
        return documentIndicator;
    }

    /**
     * @param documentIndicator
     *            The documentIndicator to set.
     */
    public void setDocumentIndicator(String documentIndicator)
    {
        this.documentIndicator = documentIndicator;
    }

    /**
     * @return Returns the lockedUser.
     */
    public String getLockedUser()
    {
        return lockedUser;
    }

    /**
     * @param lockedUser
     *            The lockedUser to set.
     */
    public void setLockedUser(String lockedUser)
    {
        this.lockedUser = lockedUser;
    }

    /**
     * @return Returns the userId.
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     * @param userId
     *            The userId to set.
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    /**
     * @return Returns the mcaTempShortName.
     */
    public String getMcaTempShortName()
    {
        return mcaTempShortName;
    }

    /**
     * @param mcaTempShortName
     *            The mcaTempShortName to set.
     */
    public void setMcaTempShortName(String mcaTempShortName)
    {
        this.mcaTempShortName = mcaTempShortName;
    }

    /**
     * @return Returns the organisationIndicator.
     */
    public String getOrganizationIndicator()
    {
        return organizationIndicator;
    }

    /**
     * @param organisationIndicator
     *            The organisationIndicator to set.
     */
    public void setOrganizationIndicator(String organizationIndicator)
    {
        this.organizationIndicator = organizationIndicator;
    }

    /**
     * @return Returns the all.
     */
    public String getAll()
    {
        return all;
    }

    /**
     * @param all
     *            The all to set.
     */
    public void setAll(String all)
    {
        this.all = all;
    }

    /**
     * @return Returns the lockIndicatorColor.
     */
    public String getLockIndicatorColour()
    {
        return lockIndicatorColour;
    }

    /**
     * @param lockIndicatorColor
     *            The lockIndicatorColor to set.
     */
    public void setLockIndicatorColour(String lockIndicatorColour)
    {
        this.lockIndicatorColour = lockIndicatorColour;
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
     * @return Returns the pendingEnrollments.
     */
    public String getPendingEnrollments()
    {
        return pendingEnrollments;
    }

    /**
     * @param pendingEnrollments
     *            The pendingEnrollments to set.
     */
    public void setPendingEnrollments(String pendingEnrollments)
    {
        this.pendingEnrollments = pendingEnrollments;
    }
}
