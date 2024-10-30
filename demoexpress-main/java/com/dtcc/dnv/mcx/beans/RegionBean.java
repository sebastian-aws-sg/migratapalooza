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
public class RegionBean implements ITransactionBean, Serializable {
	

	private String regionCd		= "";
	private String regionNm		= "";
	private boolean tmpltPresent	= false;



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
	/**
	 * @return Returns the regionCd.
	 */
	public String getRegionCd() {
		return regionCd;
	}
	/**
	 * @param regionCd The regionCd to set.
	 */
	public void setRegionCd(String regionCd) {
		this.regionCd = regionCd;
	}
	/**
	 * @return Returns the regionNm.
	 */
	public String getRegionNm() {
		return regionNm;
	}
	/**
	 * @param regionNm The regionNm to set.
	 */
	public void setRegionNm(String regionNm) {
		this.regionNm = regionNm;
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
}
