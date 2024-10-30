
package com.dtcc.dnv.mcx.dbhelper.managedocs;

import java.util.ArrayList;
import java.util.HashMap;

import com.dtcc.dnv.mcx.beans.DealerList;
import com.dtcc.dnv.otc.common.layers.AbstractDbResponse;

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
 * @author Prabhu K
 * @date Sep 4, 2007
 * @version 1.0
 * 
 * 
 */
public class DealerListDbResponse extends AbstractDbResponse
{
	private ArrayList fedDealerList = new ArrayList();
	private ArrayList otherDealerList = new ArrayList();
	private HashMap DealerMap = new HashMap();

	/**
	 * @return Returns the fedDealerList.
	 */
	public ArrayList getFedDealerList() {
		return fedDealerList;
	}
	/**
	 * @param fedDealerList The fedDealerList to set.
	 */
	public void addFedDealerList(DealerList fedDealerList) {
		this.fedDealerList.add(fedDealerList);
	}
	/**
	 * @return Returns the otherdealerList.
	 */
	public ArrayList getOtherDealerList() {
		return otherDealerList;
	}
	/**
	 * @param otherdealerList The otherdealerList to set.
	 */
	public void addOtherDealerList(DealerList otherDealerList) {
		this.otherDealerList.add(otherDealerList);
	}
	/**
	 * @return Returns the dealerMap.
	 */
	public HashMap getDealerMap() {
		return DealerMap;
	}
	/**
	 * @param dealerMap The dealerMap to set.
	 */
	public void addDealerMap(DealerList dealerList,String dealerKey) {
		DealerMap.put(dealerKey,dealerList);
	}
}

