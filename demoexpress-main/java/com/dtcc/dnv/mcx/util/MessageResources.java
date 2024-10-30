package com.dtcc.dnv.mcx.util;

/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55, Water Street,
 * New York, NY 10048
 * U.S.A
 * All Rights Reserved.
 *
 * This software may contain (in part or full) confidential/proprietary information of DTCC.
 * ("Confidential Information"). Disclosure of such Confidential
 * Information is prohibited and should be used only for its intended purpose
 * in accordance with rules and regulations of DTCC.
 * Form bean for a Struts application.
 *
 */
public class MessageResources 
{
    private static String APPLICATION_RESOURCES      = "com.dtcc.dnv.mcx.resources.ApplicationResources";
    private static org.apache.struts.util.MessageResources messageResources = null;

	/**
	 * Method to constructor MessageResources.
	 * @param arg0
	 * @param arg1
	 * 
	 * Let's prevent user from directly using this class
	 */
	private static org.apache.struts.util.MessageResources getInstance() 
	{
		if(messageResources == null)
            messageResources = org.apache.struts.util.MessageResources.getMessageResources(APPLICATION_RESOURCES);            
        return messageResources;
	}


	/**
	 * @see org.apache.struts.util.MessageResources#getMessage(String)
	 */
	public static String getMessage(String key) 
	{
		getInstance();
		return messageResources.getMessage(key);
	}
}
