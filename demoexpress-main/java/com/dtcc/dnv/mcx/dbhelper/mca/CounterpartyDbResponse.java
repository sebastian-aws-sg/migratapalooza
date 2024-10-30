package com.dtcc.dnv.mcx.dbhelper.mca;

import java.util.Map;

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

public class CounterpartyDbResponse extends MCXAbstractDbResponse {
	
	private Map enrollCpMap = null;


	/**
	 * @return Returns the enrollCpMap.
	 */
	public Map getEnrollCpMap() {
		return enrollCpMap;
	}
	/**
	 * @param enrollCpMap The enrollCpMap to set.
	 */
	public void setEnrollCpMap(Map enrollCpMap) {
		this.enrollCpMap = enrollCpMap;
	}
}

