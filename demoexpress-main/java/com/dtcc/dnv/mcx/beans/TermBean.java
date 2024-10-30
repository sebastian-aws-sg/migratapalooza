package com.dtcc.dnv.mcx.beans;

import java.util.List;

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
public class TermBean extends ABaseTran
{
	
	private String  termId  		= "";   	// term Id
	private String  termNm  	 	= "";  		// term name
	private String  termVal   		= "";   	// term value
	private int  termSqId   		= 0;   		// term sequence Id
	private int tmpltId				= 0;    	//template Id
	private String  catgyId   		= "";   	// category Id
	private String  catgyNm   		= "";   	// category name	
	private String  ISDATmpltNm		= "";   	// template name
	private String  amndtStCd  		= "";   	// product code
	private List 	cmntList		= null; 	// comment list
	private String  editFlg   		= "";   	// edit flag
	private String  menuInd   		= "";   	// menu indicator
	private String  tmpltTyp		= "";  		//Template Type
	private byte[] imageObj 		= null;		//Image Object (Blob)
	private String  termStCd   		= "";   	// term status code to indicate if the term is editable or not
	private int 	termValId		= 0;		// term Val Id - Amendment ID
	private int		docId		    = 0;  		//Doc ID
	private int 	termTextId 		= 0; 		//Text Value ID
	private int catgySqId			= 0;  		//Category Sequence ID
	private String docName			= ""; 		//Document Name
	private boolean cmntInd			= false;	//Comment Indicator
	private String cmntTxt			= "";		//Comment Text
	
	/**
	 * @return Returns the catgySqId.
	 */
	public int getCatgySqId() {
		return catgySqId;
	}
	/**
	 * @param catgySqId The catgySqId to set.
	 */
	public void setCatgySqId(int categorySeq) {
		this.catgySqId = categorySeq;
	}

	/**
	 * @return Returns the docId.
	 */
	public int getDocId() {
		return docId;
	}
	/**
	 * @param docId The docId to set.
	 */
	public void setDocId(int docId) {
		this.docId = docId;
	}
	/**
	 * @return Returns the termValId.
	 */
	public int getTermValId() {
		return termValId;
	}
	/**
	 * @param termValId The termValId to set.
	 */
	public void setTermValId(int termValId) {
		this.termValId = termValId;
	}
	/**
	 * @return Returns the termStCd.
	 */
	public String getTermStCd() {
		return termStCd;
	}
	/**
	 * @return Returns the cmntTxt.
	 */
	public String getCmntTxt() {
		return cmntTxt;
	}
	/**
	 * @param cmntTxt The cmntTxt to set.
	 */
	public void setCmntTxt(String cmntTxt) {
		this.cmntTxt = cmntTxt;
	}
	/**
	 * @param termStCd The termStCd to set.
	 */
	public void setTermStCd(String termStCd) {
		this.termStCd = termStCd;
	}
	/**
	 * @return Returns the imageObj.
	 */
	public byte[] getImageObj() {
		return imageObj;
	}
	/**
	 * @param imageObj The imageObj to set.
	 */
	public void setImageObj(byte[] imageObj) {
		this.imageObj = imageObj;
	}
 
	/**
	 * @return Returns the cmntInd.
	 */
	public boolean isCmntInd() {
		return cmntInd;
	}
	/**
	 * @param cmntInd The cmntInd to set.
	 */
	public void setCmntInd(boolean cmntInd) {
		this.cmntInd = cmntInd;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer( super.toString());
		sb.append("termId=").append(termId);
		sb.append("termNm=").append(termNm);
		sb.append("termVal=").append(termVal);
		sb.append("termSqId=").append(termSqId);
		sb.append("categId=").append(catgyId);
		sb.append("categNm=").append(catgyNm);
		sb.append("ISDA tmpltNm=").append(ISDATmpltNm);
		sb.append("amndtStCd=").append(amndtStCd);
		sb.append("templateType=").append(tmpltTyp);
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
	 * @return Returns the amndtStCd.
	 */
	public String getAmndtStCd() {
		return amndtStCd;
	}
	/**
	 * @param amndtStCd The amndtStCd to set.
	 */
	public void setAmndtStCd(String amndtStCd) {
		this.amndtStCd = amndtStCd;
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
	 * @return Returns the termId.
	 */
	public String getTermId() {
		return termId;
	}
	/**
	 * @param termId The termId to set.
	 */
	public void setTermId(String termId) {
		this.termId = termId;
	}
	/**
	 * @return Returns the termNm.
	 */
	public String getTermNm() {
		return termNm;
	}
	/**
	 * @param termNm The termNm to set.
	 */
	public void setTermNm(String termNm) {
		this.termNm = termNm;
	}
	/**
	 * @return Returns the termSqId.
	 */
	public int getTermSqId() {
		return termSqId;
	}
	/**
	 * @param termSqId The termSqId to set.
	 */
	public void setTermSqId(int termSqId) {
		this.termSqId = termSqId;
	}
	/**
	 * @return Returns the termVal.
	 */
	public String getTermVal() {
		return termVal;
	}
	/**
	 * @param termVal The termVal to set.
	 */
	public void setTermVal(String termVal) {
		this.termVal = termVal;
	}
	
	/**
	 * @return Returns the cmntList.
	 */
	public List getCmntList() {
		return cmntList;
	}
	/**
	 * @param cmntList The cmntList to set.
	 */
	public void setCmntList(List cmntList) {
		this.cmntList = cmntList;
	}
	/**
	 * @return Returns the editFlg.
	 */
	public String getEditFlg() {
		return editFlg;
	}
	/**
	 * @param editFlg The editFlg to set.
	 */
	public void setEditFlg(String editFlg) {
		this.editFlg = editFlg;
	}
	/**
	 * @return Returns the menuInd.
	 */
	public String getMenuInd() {
		return menuInd;
	}
	/**
	 * @param menuInd The menuInd to set.
	 */
	public void setMenuInd(String menuInd) {
		this.menuInd = menuInd;
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
	/**
	 * @return Returns the catgyNm.
	 */
	public String getCatgyNm() {
		return catgyNm;
	}
	/**
	 * @param catgyNm The catgyNm to set.
	 */
	public void setCatgyNm(String catgyNm) {
		this.catgyNm = catgyNm;
	}
	/**
	 * @return Returns the docName.
	 */
	public String getDocName() {
		return docName;
	}
	/**
	 * @param docName The docName to set.
	 */
	public void setDocName(String docName) {
		this.docName = docName;
	}
	/**
	 * @return Returns the termTextId.
	 */
	public int getTermTextId() {
		return termTextId;
	}
	/**
	 * @param termTextId The termTextId to set.
	 */
	public void setTermTextId(int termTextId) {
		this.termTextId = termTextId;
	}

	/**
	 * Check if there is an Image Object present in the Amendment Text
	 * @param termBean
	 * @return
	 */
	public boolean isImagePresent()
	{
		if(termVal.indexOf("src") != -1)
		{
			return true;
		}
		return false;
	}

}