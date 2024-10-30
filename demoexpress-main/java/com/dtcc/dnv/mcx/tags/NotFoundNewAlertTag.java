package com.dtcc.dnv.mcx.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.dtcc.dnv.mcx.util.MCXConstants;

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
 * @date Oct 03, 2007
 * @version 1.0
 * 
 * This class is used as tag to check if new alerts has been posted when user
 * logs in
 *  
 */
public class NotFoundNewAlertTag extends TagSupport {
	
	public NotFoundNewAlertTag() {
		super();
	}
	
	/**
	 * 
	 * @throws JspException
	 *             thrown if there is a JspException or there are issues
	 *             attaining user information.
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException	{		
		Object o = pageContext.getSession().getAttribute(MCXConstants.FINDNEWALERT);
		if(o==null)
			return SKIP_BODY;
		else {	
			if(((String)o).equalsIgnoreCase("false"))
			{
				return EVAL_BODY_INCLUDE;				
			}
			else
			{
				return SKIP_BODY;
			}
		}			
				
	}

}
