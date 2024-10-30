package com.dtcc.dnv.mcx.tags;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.ApplicationContextHandler;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.security.model.IUser;
import com.dtcc.dnv.otc.common.security.model.UserFactory;
import com.dtcc.sharedservices.cwf.exceptions.CwfException;


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
 * @author VVaradac
 * @date Nov 02, 2007
 * @version 1.0
 *
 * This class is used as tag to check if new alerts has been posted when user logs in
 *
 */
public class FormMCAMenuTag extends TagSupport
{

	private final static MessageLogger log = MessageLogger.getMessageLogger(FormMCAMenuTag.class.getName());

	public FormMCAMenuTag()
	{
		super();
	}


	/**
	 *
	 * @throws JspException thrown if there is a JspException or there are issues attaining
	 * user information.
	 *
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException
	{
		try
		{
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
			IUser user = UserFactory.getUser(request);
			MCXCUOUser mcxUser = (MCXCUOUser) user;
			
			ApplicationContextHandler appCont = ApplicationContextHandler.getInstance();
			Object o = appCont.getPendingMCAs(mcxUser.getCompanyId()); 
			String menu = (String) o;
			
			if(o !=null && !menu.equalsIgnoreCase(""))
			{
				request.setAttribute(MCXConstants.MCA_SIDE_MENU, menu);
				return EVAL_BODY_INCLUDE;
			}
			else
			{
				return SKIP_BODY;
			}

		}
		catch (CwfException e)
		{
			log.error(e);
			throw new JspException(e.getMessage());
		}
		catch (Exception e)
		{
			log.error(e);
			throw new JspException(e.getMessage());
		}

	}

}
