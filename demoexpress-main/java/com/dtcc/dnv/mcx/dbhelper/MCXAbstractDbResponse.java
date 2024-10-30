
package com.dtcc.dnv.mcx.dbhelper;

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
 * @author Peng Zhou
 * @date Oct 12, 2007
 * @version 1.0
 * 
 * This class is used as mcx abstracr db helper class.
 *  
 */
public class MCXAbstractDbResponse  extends AbstractDbResponse{

    
	private String spResponseMessage = "";	

	/**
	 * @return Returns the spResponseMessage.
	 */
	public String getSpResponseMessage() {
		return spResponseMessage;
	}
	/**
	 * @param spResponseMessage The spResponseMessage to set.
	 */
	public void setSpResponseMessage(String spResponseMessage) {
		this.spResponseMessage = spResponseMessage;
	}
}
