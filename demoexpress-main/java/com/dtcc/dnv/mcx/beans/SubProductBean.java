/*
 * Created on Oct 9, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.beans;

import java.io.Serializable;

import com.dtcc.dnv.otc.common.layers.ITransactionBean;

/**
 * @author VVaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SubProductBean implements ITransactionBean, Serializable {
	
	private String subProdCd		= "";
	private String subProdNm		= "";
	private boolean tmpltPresent	= false;

	/**
	 * @return Returns the isTmpltPresent.
	 */
	public boolean getTmpltPresent() {
		return tmpltPresent;
	}
	/**
	 * @param isTmpltPresent The isTmpltPresent to set.
	 */
	public void setTmpltPresent(boolean isTmpltPresent) {
		this.tmpltPresent = isTmpltPresent;
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
	 * @return Returns the subProdNm.
	 */
	public String getSubProdNm() {
		return subProdNm;
	}
	/**
	 * @param subProdNm The subProdNm to set.
	 */
	public void setSubProdNm(String subProdNm) {
		this.subProdNm = subProdNm;
	}
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.layers.ITransactionBean#formatCurrencies()
	 */
	public void formatCurrencies() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.layers.ITransactionBean#getClone()
	 */
	public ITransactionBean getClone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

}
