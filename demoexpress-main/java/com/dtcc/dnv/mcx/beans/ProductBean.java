/*
 * Created on Oct 9, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.beans;

import java.util.List;

import com.dtcc.dnv.otc.common.layers.ITransactionBean;

/**
 * @author VVaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProductBean extends ABaseTran {
	
	private String prodId	 = "";					//Product Cd
	private String prodNm	 = "";					//Product Name
	private List subProds	 = null;				//List of SubProducts 		

	/**
	 * @return Returns the prodId.
	 */
	public String getProdId() {
		return prodId;
	}
	/**
	 * @param prodId The prodId to set.
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	/**
	 * @return Returns the prodNm.
	 */
	public String getProdNm() {
		return prodNm;
	}
	/**
	 * @param prodNm The prodNm to set.
	 */
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	/**
	 * @return Returns the subProds.
	 */
	public List getSubProds() {
		return subProds;
	}
	/**
	 * @param subProds The subProds to set.
	 */
	public void setSubProds(List subProds) {
		this.subProds = subProds;
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
