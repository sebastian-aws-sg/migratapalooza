
package com.dtcc.dnv.mcx.delegate.enroll;

import java.util.ArrayList;
import java.util.HashMap;

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
 * @author Prabhu K
 * @date Sep 4, 2007
 * @version 1.0
 * 
 * 
 */
public class DealerListServiceResponse extends MCXAbstractServiceResponse
{
	private ArrayList fedDealerList = new ArrayList();
	private ArrayList OtherdealerList = new ArrayList();
	private HashMap DealerMap = new HashMap();

	/**
	 * @return Returns the dealerMap.
	 */
	public HashMap getDealerMap() {
		return DealerMap;
	}
	/**
	 * @param dealerMap The dealerMap to set.
	 */
	public void setDealerMap(HashMap dealerMap) {
		DealerMap = dealerMap;
	}
	/**
	 * @return Returns the fedDealerList.
	 */
	public ArrayList getFedDealerList() {
		return fedDealerList;
	}
	/**
	 * @param fedDealerList The fedDealerList to set.
	 */
	public void setFedDealerList(ArrayList fedDealerList) {
		this.fedDealerList = fedDealerList;
	}
	/**
	 * @return Returns the otherdealerList.
	 */
	public ArrayList getOtherdealerList() {
		return OtherdealerList;
	}
	/**
	 * @param otherdealerList The otherdealerList to set.
	 */
	public void setOtherdealerList(ArrayList otherdealerList) {
		OtherdealerList = otherdealerList;
	}

}
