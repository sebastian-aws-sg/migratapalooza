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
public class CategoryBean extends ABaseTran
{
	
	private String  catgyId   		= "";   // category Id
	private String  catgyNm   		= "";   // category name	
	private String  catgyStCd  		= "";   // category status code		
	private String  tmpltId  		= "";   // template Id
	private String  tmpltNm   		= "";   // template name
	private List	termList   		= null; // term list
	private int  catgySqId   	    = 0;   // category sequence Id
	private TermBean catgyTrm		= null;	//Category containing special values
	private int catgyLineWrap = 0;         //number of lines for the category

	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer( super.toString());
		sb.append("categId=").append(catgyId);
		sb.append("categNm=").append(catgyNm);
		sb.append("tmpltId=").append(tmpltId);
		sb.append("tmpltNm=").append(tmpltNm);
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
	 * @return Returns the termList.
	 */
	public List getTermList() {
		return termList;
	}
	/**
	 * @param termList The termList to set.
	 */
	public void setTermList(List termList) {
		this.termList = termList;
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
	 * @return Returns the catgyStCd.
	 */
	public String getCatgyStCd() {
		return catgyStCd;
	}
	/**
	 * @param catgyStCd The catgyStCd to set.
	 */
	public void setCatgyStCd(String catgyStCd) {
		this.catgyStCd = catgyStCd;
	}
	/**
	 * @return Returns the catgySqId.
	 */
	public int getCatgySqId() {
		return catgySqId;
	}
	/**
	 * @param catgySqId The catgySqId to set.
	 */
	public void setCatgySqId(int catgySqId) {
		this.catgySqId = catgySqId;
	}
	/**
	 * @return Returns the catgyTrm.
	 */
	public TermBean getCatgyTrm() {
		return catgyTrm;
	}
	/**
	 * @param catgyTrm The catgyTrm to set.
	 */
	public void setCatgyTrm(TermBean catgyTrm) {
		this.catgyTrm = catgyTrm;
	}
    /**
     * @return Returns the catgyLineWrap.
     */
    public int getCatgyLineWrap()
    {
        return catgyLineWrap;
    }
    /**
     * @param catgyLineWrap The catgyLineWrap to set.
     */
    public void setCatgyLineWrap(int catgyLineWrap)
    {
        this.catgyLineWrap = catgyLineWrap;
    }
}