package com.dtcc.dnv.mcx.beans;

import java.util.List;
import java.util.Date;

import com.dtcc.dnv.otc.common.layers.ITransactionBean;

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
public class TemplateBean extends ABaseTran
{
	private int  tmpltId   			= 0;   // template Id
	private String  tmpltNm  	 	= "";   // template Name	
	private String  tmpltTyp   		= "";   // template Type
	private String  tmpltShrtNm  	= "";   // template Short Name
	private String  orgDlrCd   		= "";   // dealer Id
	private String  orgDlrNm  		= "";   // dealer name
	private String  orgCltCd   		= "";   // client Id
	private String  orgCltNm   		= "";   // client name
	private String  derPrdCd  		= "";   // product code
	private String  derPrdNm  		= "";   // product name
	private String  derSubPrdCd  	= "";   // sub-product code
	private String  derSubPrdNm   	= "";   // sub-product name
	private String  rgnCd   		= "";   // region code
	private String  rgnNm   		= "";   // region Name
	private String  tmpltPubDt 		= "";   // template publication date 
	private String  mcaExeDt   		= "";   // mca execution date
	private String  dlrStCd   		= "";   //  dealer status code
	private String  cltStCd  		= "";   //  client status code
	private List    categBeanList   = null; // category bean list	
	private String  rowUpdtId       = "";  	// row update Id 
	private String  rowUpdtDt       = "";   // row update dt
	private Date modifiedTime 		= null;  // row update dt as TS
	private Date postedDate			= null;
	private Date mcaAgreementDate	= null;		//mcaAgreement date or MCA Executed Date
	private String rowUpdtName = "";	
	private String mcaStatusCd		= "";	 // MCA Status Code
	private String appRejStatus 	= "";	 // Approve/Reject Status
	private String publishDt 		= "";	 // Publish Date
	private String  lockSt      	= "";   // locked status
	private String 	lockCmpnyId		= "";	//locked organisation id
	private String 	lockByUsrId		= "";	// locked by user name
	private String 	lockByUsrNm		= "";	// locked by user name
	private String 	lockUsrInd		="";	// locked by user indicator
	private String nxtTmpltNm 		= ""; 		// next tmplate name
	private boolean tempValAvl		= false;	//If Temporary Value available for this template
	private String ISDATmpltNm		= "";		//ISDA Template Name
	
	
    private boolean eligibleForAppr = false;	//Admin - Eligible for Approval
    private boolean apprEnabled		= false;	//Admin - If Requester Id is not equal to Current UserId
    private boolean eligibleForSub	= false;	//Eligible for Submission, checks history table and returns 'Y' or 'N'
    private boolean eligibleForExc  = false;	//Eligible for Execution, if no Amendments are pending
    private boolean propValInsReq  = false;	//Proprietary value insert required
    private boolean eligibleForReNeg = false;
	
	/**
	 * @return Returns the rgnNm.
	 */
	public String getRgnNm() {
		return rgnNm;
	}
	/**
	 * @param rgnNm The rgnNm to set.
	 */
	public void setRgnNm(String rgnNm) {
		this.rgnNm = rgnNm;
	}

	
	/**
	 * @return Returns the tmpltShrtNm.
	 */
	public String getTmpltShrtNm() {
		return tmpltShrtNm;
	}
	/**
	 * @param tmpltShrtNm The tmpltShrtNm to set.
	 */
	public void setTmpltShrtNm(String tmpltShrtNm) {
		this.tmpltShrtNm = tmpltShrtNm;
	}
	/**
	 * @return Returns the lockCmpnyId.
	 */
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer( super.toString());
		sb.append("tmpltId=").append(tmpltId);
		sb.append("tmpltNm=").append(tmpltNm);		
		sb.append("tmpltTyp=").append(tmpltTyp);
		sb.append("orgDlrCd=").append(orgDlrCd);
		sb.append("orgDlrNm=").append(orgDlrNm);
		sb.append("orgCltCd=").append(orgCltCd);
		sb.append("orgCltNm=").append(orgCltNm);
		sb.append("derPrdCd=").append(derPrdCd);
		sb.append("derPrdNm=").append(derPrdNm);
		sb.append("derSubPrdCd=").append(derSubPrdCd);
		sb.append("derSubPrdNm=").append(derSubPrdNm);
		sb.append("rgnCd=").append(rgnCd);
		sb.append("tmpltPubDt=").append(tmpltPubDt);
		sb.append("mcaExeDt=").append(mcaExeDt);
		sb.append("dlrStCd=").append(dlrStCd);
		sb.append("cltStCd=").append(cltStCd);
		sb.append("rowUpdtId=").append(rowUpdtId);
		sb.append("rowUpdtDt=").append(rowUpdtDt);		
		return sb.toString();
	}

	
	/**
	 * @see com.dtcc.dnv.otc.common.layers.ITransactionBean#formatCurrencies()
	 */
	public void formatCurrencies()
	{
		
	}

	/**
	 * @see com.dtcc.dnv.otc.common.layers.ITransactionBean#getClone()
	 */
	public ITransactionBean getClone() throws CloneNotSupportedException {
		return (ITransactionBean) super.clone();
	}
	/**
	 * @return Returns the categBeanList.
	 */
	public List getCategBeanList() {
		return categBeanList;
	}
	/**
	 * @param categBeanList The categBeanList to set.
	 */
	public void setCategBeanList(List categBeanList) {
		this.categBeanList = categBeanList;
	}
	/**
	 * @return Returns the derPrdCd.
	 */
	public String getDerPrdCd() {
		return derPrdCd;
	}
	/**
	 * @param derPrdCd The derPrdCd to set.
	 */
	public void setDerPrdCd(String derPrdCd) {
		this.derPrdCd = derPrdCd;
	}
	/**
	 * @return Returns the derPrdNm.
	 */
	public String getDerPrdNm() {
		return derPrdNm;
	}
	/**
	 * @param derPrdNm The derPrdNm to set.
	 */
	public void setDerPrdNm(String derPrdNm) {
		this.derPrdNm = derPrdNm;
	}
	/**
	 * @return Returns the derSubPrdCd.
	 */
	public String getDerSubPrdCd() {
		return derSubPrdCd;
	}
	/**
	 * @param derSubPrdCd The derSubPrdCd to set.
	 */
	public void setDerSubPrdCd(String derSubPrdCd) {
		this.derSubPrdCd = derSubPrdCd;
	}
	/**
	 * @return Returns the derSubPrdNm.
	 */
	public String getDerSubPrdNm() {
		return derSubPrdNm;
	}
	/**
	 * @param derSubPrdNm The derSubPrdNm to set.
	 */
	public void setDerSubPrdNm(String derSubPrdNm) {
		this.derSubPrdNm = derSubPrdNm;
	}
	/**
	 * @return Returns the mcaExeDt.
	 */
	public String getMcaExeDt() {
		return mcaExeDt;
	}
	/**
	 * @param mcaExeDt The mcaExeDt to set.
	 */
	public void setMcaExeDt(String mcaExeDt) {
		this.mcaExeDt = mcaExeDt;
	}
	/**
	 * @return Returns the cltStCd.
	 */
	public String getCltStCd() {
		return cltStCd;
	}
	/**
	 * @param cltStCd The cltStCd to set.
	 */
	public void setCltStCd(String cltStCd) {
		this.cltStCd = cltStCd;
	}
	/**
	 * @return Returns the dlrStCd.
	 */
	public String getDlrStCd() {
		return dlrStCd;
	}
	/**
	 * @param dlrStCd The dlrStCd to set.
	 */
	public void setDlrStCd(String dlrStCd) {
		this.dlrStCd = dlrStCd;
	}	
	/**
	 * @return Returns the orgCltCd.
	 */
	public String getOrgCltCd() {
		return orgCltCd;
	}
	/**
	 * @param orgCltCd The orgCltCd to set.
	 */
	public void setOrgCltCd(String orgCltCd) {
		this.orgCltCd = orgCltCd;
	}
	/**
	 * @return Returns the orgCltNm.
	 */
	public String getOrgCltNm() {
		return orgCltNm;
	}
	/**
	 * @param orgCltNm The orgCltNm to set.
	 */
	public void setOrgCltNm(String orgCltNm) {
		this.orgCltNm = orgCltNm;
	}
	/**
	 * @return Returns the orgDlrCd.
	 */
	public String getOrgDlrCd() {
		return orgDlrCd;
	}
	/**
	 * @param orgDlrCd The orgDlrCd to set.
	 */
	public void setOrgDlrCd(String orgDlrCd) {
		this.orgDlrCd = orgDlrCd;
	}
	/**
	 * @return Returns the orgDlrNm.
	 */
	public String getOrgDlrNm() {
		return orgDlrNm;
	}
	/**
	 * @param orgDlrNm The orgDlrNm to set.
	 */
	public void setOrgDlrNm(String orgDlrNm) {
		this.orgDlrNm = orgDlrNm;
	}
	/**
	 * @return Returns the rgnCd.
	 */
	public String getRgnCd() {
		return rgnCd;
	}
	/**
	 * @param rgnCd The rgnCd to set.
	 */
	public void setRgnCd(String rgnCd) {
		this.rgnCd = rgnCd;
	}
	/**
	 * @return Returns the rowUpdtDt.
	 */
	public String getRowUpdtDt() {
		return rowUpdtDt;
	}
	/**
	 * @param rowUpdtDt The rowUpdtDt to set.
	 */
	public void setRowUpdtDt(String rowUpdtDt) {
		this.rowUpdtDt = rowUpdtDt;
	}
	/**
	 * @return Returns the rowUpdtId.
	 */
	public String getRowUpdtId() {
		return rowUpdtId;
	}
	/**
	 * @param rowUpdtId The rowUpdtId to set.
	 */
	public void setRowUpdtId(String rowUpdtId) {
		this.rowUpdtId = rowUpdtId;
	}
	/**
	 * @return Returns the tmpltId.
	 */
	public int getTmpltId() {
		return tmpltId;
	}
	/**
	 * @param tmpltId The tmpltId to set.
	 */
	public void setTmpltId(int tmpltId) {
		this.tmpltId = tmpltId;
	}
	/**
	 * @return Returns the tmpltNm.
	 */
	public String getTmpltNm() {
		return tmpltNm;
	}
	/**
	 * @param tmpltNm The tmpltNm to set.
	 */
	public void setTmpltNm(String tmpltNm) {
		this.tmpltNm = tmpltNm;
	}
	/**
	 * @return Returns the tmpltPubDt.
	 */
	public String getTmpltPubDt() {
		return tmpltPubDt;
	}
	/**
	 * @param tmpltPubDt The tmpltPubDt to set.
	 */
	public void setTmpltPubDt(String tmpltPubDt) {
		this.tmpltPubDt = tmpltPubDt;
	}
	/**
	 * @return Returns the tmpltTyp.
	 */
	public String getTmpltTyp() {
		return tmpltTyp;
	}
	/**
	 * @param tmpltTyp The tmpltTyp to set.
	 */
	public void setTmpltTyp(String tmpltTyp) {
		this.tmpltTyp = tmpltTyp;
	}
	
	
	
    /**
     * @return Returns the modifiedTime.
     */
    public Date getModifiedTime()
    {
        return modifiedTime;
    }
    /**
     * @param modifiedTime The modifiedTime to set.
     */
    public void setModifiedTime(Date modifiedTime)
    {
        this.modifiedTime = modifiedTime;
    }
    
    
	/**
	 * @return Returns the tempValAvl.
	 */
	public boolean isTempValAvl() {
		return tempValAvl;
	}
	/**
	 * @param tempValAvl The tempValAvl to set.
	 */
	public void setTempValAvl(boolean tempValAvl) {
		this.tempValAvl = tempValAvl;
	}
	/**
	 * @return Returns the iSDATmpltNm.
	 */
	public String getISDATmpltNm() {
		return this.ISDATmpltNm;
	}
	/**
	 * @param tmpltNm The iSDATmpltNm to set.
	 */
	public void setISDATmpltNm(String tmpltNm) {
		this.ISDATmpltNm = tmpltNm;
	}
	
    
   
    
    
   
	/**
	 * @return Returns the apprEnabled.
	 */
	public boolean isApprEnabled() {
		return apprEnabled;
	}
	/**
	 * @param apprEnabled The apprEnabled to set.
	 */
	public void setApprEnabled(boolean apprEnabled) {
		this.apprEnabled = apprEnabled;
	}
	/**
	 * @return Returns the eligibleForAppr.
	 */
	public boolean isEligibleForAppr() {
		return eligibleForAppr;
	}
	/**
	 * @param eligibleForAppr The eligibleForAppr to set.
	 */
	public void setEligibleForAppr(boolean eligibleForAppr) {
		this.eligibleForAppr = eligibleForAppr;
	}
	/**
	 * @return Returns the eligibleForExc.
	 */
	public boolean isEligibleForExc() {
		return eligibleForExc;
	}
	/**
	 * @param eligibleForExc The eligibleForExc to set.
	 */
	public void setEligibleForExc(boolean eligibleForExc) {
		this.eligibleForExc = eligibleForExc;
	}
	/**
	 * @return Returns the eligibleForSub.
	 */
	public boolean isEligibleForSub() {
		return eligibleForSub;
	}
	/**
	 * @param eligibleForSub The eligibleForSub to set.
	 */
	public void setEligibleForSub(boolean eligibleForSub) {
		this.eligibleForSub = eligibleForSub;
	}
	/**
	 * @return Returns the propValInsReq.
	 */
	public boolean isPropValInsReq() {
		return propValInsReq;
	}
	/**
	 * @param propValInsReq The propValInsReq to set.
	 */
	public void setPropValInsReq(boolean propValInsReq) {
		this.propValInsReq = propValInsReq;
	}
	
    

	/**
	 * @return Returns the eligibleForReNeg.
	 */
	public boolean isEligibleForReNeg() {
		return eligibleForReNeg;
	}
	/**
	 * @param eligibleForReNeg The eligibleForReNeg to set.
	 */
	public void setEligibleForReNeg(boolean eligibleForReNeg) {
		this.eligibleForReNeg = eligibleForReNeg;
	}
	
	
    /**
     * @return Returns the lockUsrInd.
     */
    public String getLockUsrInd()
    {
        return lockUsrInd;
    }
    /**
     * @param lockUsrInd The lockUsrInd to set.
     */
    public void setLockUsrInd(String lockUsrInd)
    {
        this.lockUsrInd = lockUsrInd;
    }
    /**
     * @return Returns the nxtTmpltNm.
     */
    public String getNxtTmpltNm()
    {
        return nxtTmpltNm;
    }
    /**
     * @param nxtTmpltNm The nxtTmpltNm to set.
     */
    public void setNxtTmpltNm(String nxtTmpltNm)
    {
        this.nxtTmpltNm = nxtTmpltNm;
    }
    /**
     * @return Returns the appRejStatus.
     */
    public String getAppRejStatus()
    {
        return appRejStatus;
    }
    /**
     * @param appRejStatus The appRejStatus to set.
     */
    public void setAppRejStatus(String appRejStatus)
    {
        this.appRejStatus = appRejStatus;
    }
    /**
     * @return Returns the lockByUsrId.
     */
    public String getLockByUsrId()
    {
        return lockByUsrId;
    }
    /**
     * @param lockByUsrId The lockByUsrId to set.
     */
    public void setLockByUsrId(String lockByUsrId)
    {
        this.lockByUsrId = lockByUsrId;
    }
    /**
     * @return Returns the lockByUsrNm.
     */
    public String getLockByUsrNm()
    {
        return lockByUsrNm;
    }
    /**
     * @param lockByUsrNm The lockByUsrNm to set.
     */
    public void setLockByUsrNm(String lockByUsrNm)
    {
        this.lockByUsrNm = lockByUsrNm;
    }
    /**
     * @return Returns the lockCmpnyId.
     */
    public String getLockCmpnyId()
    {
        return lockCmpnyId;
    }
    /**
     * @param lockCmpnyId The lockCmpnyId to set.
     */
    public void setLockCmpnyId(String lockCmpnyId)
    {
        this.lockCmpnyId = lockCmpnyId;
    }
    /**
     * @return Returns the lockSt.
     */
    public String getLockSt()
    {
        return lockSt;
    }
    /**
     * @param lockSt The lockSt to set.
     */
    public void setLockSt(String lockSt)
    {
        this.lockSt = lockSt;
    }
    /**
     * @return Returns the mcaStatusCd.
     */
    public String getMcaStatusCd()
    {
        return mcaStatusCd;
    }
    /**
     * @param mcaStatusCd The mcaStatusCd to set.
     */
    public void setMcaStatusCd(String mcaStatusCd)
    {
        this.mcaStatusCd = mcaStatusCd;
    }
    /**
     * @return Returns the publishDt.
     */
    public String getPublishDt()
    {
        return publishDt;
    }
    /**
     * @param publishDt The publishDt to set.
     */
    public void setPublishDt(String publishDt)
    {
        this.publishDt = publishDt;
    }
    
    
    /**
     * @return Returns the mcaAgreementDate.
     */
    public Date getMcaAgreementDate()
    {
        return mcaAgreementDate;
    }
    /**
     * @param mcaAgreementDate The mcaAgreementDate to set.
     */
    public void setMcaAgreementDate(Date mcaAgreementDate)
    {
        this.mcaAgreementDate = mcaAgreementDate;
    }
    /**
     * @return Returns the postedDate.
     */
    public Date getPostedDate()
    {
        return postedDate;
    }
    /**
     * @param postedDate The postedDate to set.
     */
    public void setPostedDate(Date postedDate)
    {
        this.postedDate = postedDate;
    }
    /**
     * @return Returns the rowUpdtName.
     */
    public String getRowUpdtName()
    {
        return rowUpdtName;
    }
    /**
     * @param rowUpdtName The rowUpdtName to set.
     */
    public void setRowUpdtName(String rowUpdtName)
    {
        this.rowUpdtName = rowUpdtName;
    }
}