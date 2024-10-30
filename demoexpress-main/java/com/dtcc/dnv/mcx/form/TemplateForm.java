package com.dtcc.dnv.mcx.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dtcc.dnv.mcx.beans.TemplateBean;
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
 * @author Narahari A
 * @date Sep 4, 2007
 * @version 1.0
 * 
 */
public class TemplateForm extends MCXActionForm

{	
	private List categyList = null;
	private List tmpltList = null;
	private List categyBeanList = null;
	private List rgnList = null;		
	private HashMap  viewMap = null;
	private String catgyId = "";
	private String tmpltId = "";	
	private String rgnId = "";	
	private String viewInd ="";
	private String frmScr = "";
	private String sltInd ="";

	private String mcaStatusCd = "";
	private String lockInd = "";
	private String appRejStatus = "";
	private String publishDt = "";
	
	private String tmpltNm = "";
	private String tmpltTyp = "";	
	private String opnInd	= "";		//Operation Indicator
	private String applyToCP	= "";
	private boolean enableCustomize		= true;		//To enable further Customization in MCA Wizard Step2
	private String subProdCd		= "";
	private String prodCd	 = "";			
	private boolean actingDealer 		= false;	//Is current user acting as Dealer
	private String cltCd = "";
	private String cltNm = "";
	private String scrollPosition = "0";
	private boolean tmpltPresent = true;	
	private String displayNm = "";
	private Map rgnMap 				 = new HashMap(); 
	private boolean enableSubmitToCP	 = true;
	private boolean enableExecute		 = true;
	
	private boolean enableSave			 = true;
	private boolean enablePublish		 = true;
	private boolean enableApprRej		 = true;
	
	private boolean flagTemplateApprovedRejected = false;	
	private String errorTemplateApprovedRejected = "";

	public TemplateForm()
	{
		this.setTransaction(new TemplateBean());
	}

	/**
	 * @return Returns the appRejStatus.
	 */
	public String getAppRejStatus() {
		return appRejStatus;
	}
	/**
	 * @param appRejStatus The appRejStatus to set.
	 */
	public void setAppRejStatus(String appRejStatus) {
		this.appRejStatus = appRejStatus;
	}
	/**
	 * @return Returns the lockInd.
	 */
	public String getLockInd() {
		return lockInd;
	}
	/**
	 * @return Returns the viewMap.
	 */
	public HashMap getViewMap() {
		return viewMap;
	}
	/**
	 * @param viewMap The viewMap to set.
	 */
	public void setViewMap(HashMap viewMap) {
		this.viewMap = viewMap;
	}
	/**
	 * @param lockInd The lockInd to set.
	 */
	public void setLockInd(String lockInd) {
		this.lockInd = lockInd;
	}
	/**
	 * @return Returns the mcaStatusCd.
	 */
	public String getMcaStatusCd() {
		return mcaStatusCd;
	}
	/**
	 * @param mcaStatusCd The mcaStatusCd to set.
	 */
	public void setMcaStatusCd(String mcaStatusCd) {
		this.mcaStatusCd = mcaStatusCd;
	}
	/**
	 * @return Returns the publishDt.
	 */
	public String getPublishDt() {
		return publishDt;
	}
	/**
	 * @param publishDt The publishDt to set.
	 */
	public void setPublishDt(String publishDt) {
		this.publishDt = publishDt;
	}
	/**
	 * @return Returns the categyList.
	 */
	public List getCategyList() {
		return categyList;
	}
	/**
	 * @param categyList The categyList to set.
	 */
	public void setCategyList(List categyList) {
		this.categyList = categyList;
	}

	/**
	 * @return Returns the tmpltList.
	 */
	public List getTmpltList() {
		return tmpltList;
	}
	/**
	 * @param tmpltList The tmpltList to set.
	 */
	public void setTmpltList(List tmpltList) {
		this.tmpltList = tmpltList;
	}

	/**
	 * @return Returns the categyBeanList.
	 */
	public List getCategyBeanList() {
		return categyBeanList;
	}
	/**
	 * @param categyBeanList The categyBeanList to set.
	 */
	public void setCategyBeanList(List categyBeanList) {
		this.categyBeanList = categyBeanList;
	}

	/**
	 * @return Returns the rgnId.
	 */
	public String getRgnId() {
		return rgnId;
	}
	/**
	 * @param rgnId The rgnId to set.
	 */
	public void setRgnId(String rgnId) {
		this.rgnId = rgnId;
	}
	/**
	 * @return Returns the tmpltId.
	 */
	public String getTmpltId() {
		return tmpltId;
	}
	/**
	 * @param tmpltId The tmpltId to set.
	 */
	public void setTmpltId(String tmpltId) {
		this.tmpltId = tmpltId;
	}

	/**
	 * @return Returns the viewInd.
	 */
	public String getViewInd() {
		return viewInd;
	}
	/**
	 * @param viewInd The viewInd to set.
	 */
	public void setViewInd(String viewInd) {
		this.viewInd = viewInd;
	}
	
	/**
	 * @return Returns the frmScr.
	 */
	public String getFrmScr() {
		return frmScr;
	}
	/**
	 * @param frmScr The frmScr to set.
	 */
	public void setFrmScr(String frmScr) {
		this.frmScr = frmScr;
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
	 * @return Returns the catgyId.
	 */
	public String getCatgyId() {
		return catgyId;
	}
	/**
	 * @param catgyId The catgyId to set.
	 */
	public void setCatgyId(String catgyId) {
		this.catgyId = catgyId;
	}
	/**
	 * @return Returns the sltInd.
	 */
	public String getSltInd() {
		return sltInd;
	}
	/**
	 * @param sltInd The sltInd to set.
	 */
	public void setSltInd(String sltInd) {
		this.sltInd = sltInd;
	}

	/**
	 * @return Returns the applyToCP.
	 */
	public String getApplyToCP() {
		return applyToCP;
	}

	/**
	 * @param applyToCP The applyToCP to set.
	 */
	public void setApplyToCP(String applyToCP) {
		this.applyToCP = applyToCP;
	}
	/**
	 * @return Returns the opnInd.
	 */
	public String getOpnInd() {
		return opnInd;
	}
	/**
	 * @param opnInd The opnInd to set.
	 */
	public void setOpnInd(String opnInd) {
		this.opnInd = opnInd;
	}
	/**
	 * @return Returns the enableCustomize.
	 */
	public boolean getEnableCustomize() {
		return enableCustomize;
	}
	/**
	 * @param enableCustomize The enableCustomize to set.
	 */
	public void setEnableCustomize(boolean enableCustomize) {
		this.enableCustomize = enableCustomize;
	}
	/**
	 * @return Returns the prodCd.
	 */
	public String getProdCd() {
		return prodCd;
	}
	/**
	 * @param prodCd The prodCd to set.
	 */
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	/**
	 * @return Returns the subProdCd.
	 */
	public String getSubProdCd() {
		return subProdCd;
	}
	/**
	 * @param subProdCd The subProdCd to set.
	 */
	public void setSubProdCd(String subProdCd) {
		this.subProdCd = subProdCd;
	}	
	/**
	 * @return Returns the actingDealer.
	 */
	public boolean getActingDealer() {
		return actingDealer;
	}
	/**
	 * @param actingDealer The actingDealer to set.
	 */
	public void setActingDealer(boolean actingDealer) {
		this.actingDealer = actingDealer;
	}
	/**
	 * @return Returns the cltCd.
	 */
	public String getCltCd() {
		return cltCd;
	}
	/**
	 * @param cltCd The cltCd to set.
	 */
	public void setCltCd(String cltCd) {
		this.cltCd = cltCd;
	}
	/**
	 * @return Returns the cltNm.
	 */
	public String getCltNm() {
		return cltNm;
	}
	/**
	 * @param cltNm The cltNm to set.
	 */
	public void setCltNm(String cltNm) {
		this.cltNm = cltNm;
	}	
	/**
	 * @return Returns the scrollPosition.
	 */
	public String getScrollPosition() {
		return scrollPosition;
	}
	/**
	 * @param scrollPosition The scrollPosition to set.
	 */
	public void setScrollPosition(String scrollPosition) {
		this.scrollPosition = scrollPosition;
	}
	/**
	 * @return Returns the tmpltPresent.
	 */
	public boolean isTmpltPresent() {
		return tmpltPresent;
	}
	/**
	 * @param tmpltPresent The tmpltPresent to set.
	 */
	public void setTmpltPresent(boolean tmpltPresent) {
		this.tmpltPresent = tmpltPresent;
	}
	/**
	 * @return Returns the rgnList.
	 */
	public List getRgnList() {
		return rgnList;
	}
	/**
	 * @param rgnList The rgnList to set.
	 */
	public void setRgnList(List rgnList) {
		this.rgnList = rgnList;
	}

	/**
	 * @return Returns the displayNm.
	 */
	public String getDisplayNm() {
		return displayNm;
	}
	/**
	 * @param displayNm The displayNm to set.
	 */
	public void setDisplayNm(String displayNm) {
		this.displayNm = displayNm;
	}
	
	
	
       
    /**
     * @return Returns the errorTemplateApprovedRejected.
     */
    public String getErrorTemplateApprovedRejected()
    {
        return errorTemplateApprovedRejected;
    }
    /**
     * @param errorTemplateApprovedRejected The errorTemplateApprovedRejected to set.
     */
    public void setErrorTemplateApprovedRejected(String errorTemplateApprovedRejected)
    {
        this.errorTemplateApprovedRejected = errorTemplateApprovedRejected;
    }
    /**
     * @return Returns the flagTemplateApprovedRejected.
     */
    public boolean isFlagTemplateApprovedRejected()
    {
        return flagTemplateApprovedRejected;
    }
    /**
     * @param flagTemplateApprovedRejected The flagTemplateApprovedRejected to set.
     */
    public void setFlagTemplateApprovedRejected(boolean flagTemplateApprovedRejected)
    {
        this.flagTemplateApprovedRejected = flagTemplateApprovedRejected;
    }
    /**
     * @return Returns the rgnMap.
     */
    public Map getRgnMap()
    {
        return rgnMap;
    }
    /**
     * @param rgnMap The rgnMap to set.
     */
    public void setRgnMap(Map rgnMap)
    {
        this.rgnMap = rgnMap;
    }

    /**
	 * @return Returns the enableExecute.
	 */
	public boolean getEnableExecute() {
		return enableExecute;
	}
	/**
	 * @param enableExecute The enableExecute to set.
	 */
	public void setEnableExecute(boolean enableExecute) {
		this.enableExecute = enableExecute;
	}
	/**
	 * @return Returns the enableSubmitToCP.
	 */
	public boolean getEnableSubmitToCP() {
		return enableSubmitToCP;
	}
	/**
	 * @param enableSubmitToCP The enableSubmitToCP to set.
	 */
	public void setEnableSubmitToCP(boolean enableSubmitToCP) {
		this.enableSubmitToCP = enableSubmitToCP;
	}
	/**
	 * @return Returns the enableApprRej.
	 */
	public boolean getEnableApprRej() {
		return enableApprRej;
	}
	/**
	 * @param enableApprRej The enableApprRej to set.
	 */
	public void setEnableApprRej(boolean enableApprRej) {
		this.enableApprRej = enableApprRej;
	}
	/**
	 * @return Returns the enablePublish.
	 */
	public boolean getEnablePublish() {
		return enablePublish;
	}
	/**
	 * @param enablePublish The enablePublish to set.
	 */
	public void setEnablePublish(boolean enablePublish) {
		this.enablePublish = enablePublish;
	}
	/**
	 * @return Returns the enableSave.
	 */
	public boolean getEnableSave() {
		return enableSave;
	}
	/**
	 * @param enableSave The enableSave to set.
	 */
	public void setEnableSave(boolean enableSave) {
		this.enableSave = enableSave;
	}

}