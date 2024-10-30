package com.dtcc.dnv.mcx.beans;

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
public class CommentBean extends ABaseTran
{
	
	private int  	cmntId   			= 0;   	// comment Id
	private String  cmntTxt   			= "";   // comment Text		
	private String  rowUpdtName      	= "";  	// row update Id 
	private String  rowUpdtDt      		= "";   // row update dt 

	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer( super.toString());
		sb.append("cmntId=").append(cmntId);
		sb.append("cmntTxt=").append(cmntTxt);
		sb.append("rowUpdtName=").append(rowUpdtName);
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
	 * @return Returns the cmntId.
	 */
	public int getCmntId() {
		return cmntId;
	}
	/**
	 * @param cmntId The cmntId to set.
	 */
	public void setCmntId(int cmntId) {
		this.cmntId = cmntId;
	}
	/**
	 * @return Returns the cmntNm.
	 */
	public String getCmntTxt() {
		return cmntTxt;
	}
	/**
	 * @param cmntNm The cmntNm to set.
	 */
	public void setCmntTxt(String cmntTxt) {
		this.cmntTxt = cmntTxt;
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
	public String getRowUpdtName() {
		return rowUpdtName;
	}
	/**
	 * @param rowUpdtId The rowUpdtId to set.
	 */
	public void setRowUpdtName(String rowUpdtName) {
		this.rowUpdtName = rowUpdtName;
	}
}