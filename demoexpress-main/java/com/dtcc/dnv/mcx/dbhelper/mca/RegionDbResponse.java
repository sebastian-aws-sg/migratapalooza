package com.dtcc.dnv.mcx.dbhelper.mca;

import java.util.List;

import com.dtcc.dnv.mcx.dbhelper.MCXAbstractDbResponse;


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

public class RegionDbResponse extends MCXAbstractDbResponse {
	

	private List rgnList = null;



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

