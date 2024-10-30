
package com.dtcc.dnv.mcx.delegate;

import com.dtcc.dnv.otc.common.layers.AbstractServiceResponse;

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
 * This class is used as mcx abstract service class.
 *  
 */
public abstract class MCXAbstractServiceResponse extends AbstractServiceResponse {

	private boolean hasError = false;
	private String spReturnMessage = "";
	private String spReturnCode	   = "";
		
	/**
	 * @return Returns the hasError.
	 */
	public boolean hasError() {
		return hasError;
	}
	/**
	 * @param hasError The hasError to set.
	 */
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	/**
	 * @return Returns the spReturnMessage.
	 */
	public String getSpReturnMessage() {
		return spReturnMessage;
	}
	/**
	 * @param spReturnMessage The spReturnMessage to set.
	 */
	public void setSpReturnMessage(String spReturnMessage) {
		this.spReturnMessage = spReturnMessage;
	}
	/**
	 * @return Returns the spReturnCode.
	 */
	public String getSpReturnCode() {
		return spReturnCode;
	}
	/**
	 * @param spReturnCode The spReturnCode to set.
	 */
	public void setSpReturnCode(String spReturnCode) {
		this.spReturnCode = spReturnCode;
	}
}
