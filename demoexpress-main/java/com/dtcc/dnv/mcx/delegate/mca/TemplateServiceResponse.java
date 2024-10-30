package com.dtcc.dnv.mcx.delegate.mca;

import java.util.List;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceResponse;




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
public class TemplateServiceResponse extends MCXAbstractServiceResponse
{
	
	private List tmpltList = null;	
	private List categyBeanList = null;
	private List categyList = null;
	private boolean tmpltPresent = true;
	private List rgnList = null;
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
}
