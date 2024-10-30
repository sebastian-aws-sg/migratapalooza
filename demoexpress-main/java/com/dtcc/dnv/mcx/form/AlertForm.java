
package com.dtcc.dnv.mcx.form;

import com.dtcc.dnv.mcx.beans.AlertInfo;


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
 * @date Oct 01, 2007
 * @version 1.0
 * 
 * This class is used to provide action form for user input of 
 * alert information.
 *  
 */
public class AlertForm extends MCXActionForm {

	public AlertForm()
	{
		AlertInfo thealertinfo  = new AlertInfo();
		super.setTransaction(thealertinfo);
	}
	
	
	private String postaction;
	
	/**
	 * @return post action cancel for cancel
	 */
	public String getPostaction()
	{
		return postaction;
	}
	
	/**
	 * @param act set post action
	 */
	public void setPostaction(String act)
	{
	   postaction = act;			
	}
	
}
