/*
 * Created on Sep 13, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.form;

import org.apache.struts.upload.FormFile;

import com.dtcc.dnv.mcx.beans.TermBean;

/**
 * @author VVaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TermForm extends MCXActionForm {
	
	private String termValId = "";
	private String tmpltType = "";
	private FormFile imageFile = null;
	private String amendmentValue = "";
	private String commentTxt = "";
	private String tmpltID				= "";
	private String ISDATmpltNm			= "";
	private String tmpltLocked		    = "";
	private String userInd 			= "";	 	//User Indicator
	private String lockUsrInd = "";
	private String mcaStatusCd = "";
	private String userCompany			= "";
	private String frmScr = "";
	private boolean actingDealer 		= false;	//Is current user acting as Dealer
	private boolean imgPrsnt 		= false;	

	
	public TermForm() {
		this.setTransaction(new TermBean());
	}


	
	/**
	 * @return Returns the imageFile.
	 */
	public FormFile getImageFile() {
		return imageFile;
	}
	/**
	 * @param imageFile The imageFile to set.
	 */
	public void setImageFile(FormFile imageFile) {
		this.imageFile = imageFile;
	}

	/**
	 * @return Returns the amendmentValue.
	 */
	public String getAmendmentValue() {
		return amendmentValue;
	}
	/**
	 * @param amendmentValue The amendmentValue to set.
	 */
	public void setAmendmentValue(String amendmentValue) {
		this.amendmentValue = amendmentValue;
	}

	/**
	 * @return Returns the termValId.
	 */
	public String getTermValId() {
		return termValId;
	}
	/**
	 * @param termValId The termValId to set.
	 */
	public void setTermValId(String termValId) {
		this.termValId = termValId;
	}
	/**
	 * @return Returns the tmpltType.
	 */
	public String getTmpltType() {
		return tmpltType;
	}
	/**
	 * @param tmpltType The tmpltType to set.
	 */
	public void setTmpltType(String tmpltType) {
		this.tmpltType = tmpltType;
	}
	/**
	 * @return Returns the commentTxt.
	 */
	public String getCommentTxt() {
		return commentTxt;
	}
	/**
	 * @param commentTxt The commentTxt to set.
	 */
	public void setCommentTxt(String commentTxt) {
		this.commentTxt = commentTxt;
	}
	/**
	 * @return Returns the tmpltID.
	 */
	public String getTmpltID() {
		return tmpltID;
	}
	/**
	 * @param tmpltID The tmpltID to set.
	 */
	public void setTmpltID(String tmpltID) {
		this.tmpltID = tmpltID;
	}
	/**
	 * @return Returns the iSDATmpltNm.
	 */
	public String getISDATmpltNm() {
		return ISDATmpltNm;
	}
	/**
	 * @param tmpltNm The iSDATmpltNm to set.
	 */
	public void setISDATmpltNm(String tmpltNm) {
		ISDATmpltNm = tmpltNm;
	}
	
	public void reset()
	{
		this.imageFile = null;
		this.commentTxt = "";
	}
	/**
	 * @return Returns the tmpltLocked.
	 */
	public String getTmpltLocked() {
		return tmpltLocked;
	}
	/**
	 * @param tmpltLocked The tmpltLocked to set.
	 */
	public void setTmpltLocked(String tmpltLocked) {
		this.tmpltLocked = tmpltLocked;
	}
	/**
	 * @return Returns the userInd.
	 */
	public String getUserInd() {
		return userInd;
	}
	/**
	 * @param userInd The userInd to set.
	 */
	public void setUserInd(String userInd) {
		this.userInd = userInd;
	}	
	/**
	 * @return Returns the lockUsrInd.
	 */
	public String getLockUsrInd() {
		return lockUsrInd;
	}
	/**
	 * @param lockUsrInd The lockUsrInd to set.
	 */
	public void setLockUsrInd(String lockUsrInd) {
		this.lockUsrInd = lockUsrInd;
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
	 * @return Returns the userCompany.
	 */
	public String getUserCompany() {
		return userCompany;
	}
	/**
	 * @param userCompany The userCompany to set.
	 */
	public void setUserCompany(String userCompany) {
		this.userCompany = userCompany;
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
	 * @return Returns the actingDealer.
	 */
	public boolean isActingDealer() {
		return actingDealer;
	}
	/**
	 * @param actingDealer The actingDealer to set.
	 */
	public void setActingDealer(boolean actingDealer) {
		this.actingDealer = actingDealer;
	}
	/**
	 * @return Returns the imgPrsnt.
	 */
	public boolean isImgPrsnt() {
		return imgPrsnt;
	}
	/**
	 * @param imgPrsnt The imgPrsnt to set.
	 */
	public void setImgPrsnt(boolean imgPrsnt) {
		this.imgPrsnt = imgPrsnt;
	}
}
