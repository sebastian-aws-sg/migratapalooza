package com.dtcc.dnv.mcx.tags;


import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.dtcc.dnv.mcx.beans.AlertInfo;
import com.dtcc.dnv.mcx.delegate.alert.AlertServiceRequest;
import com.dtcc.dnv.mcx.delegate.alert.AlertServiceResponse;
import com.dtcc.dnv.mcx.delegate.alert.ViewAlertDelegate;
import com.dtcc.dnv.mcx.user.UserConstants;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.UserException;
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
 * @author Peng Zhou
 * @date Oct 03, 2007
 * @version 1.0
 * 
 * This class is used as tag to check if new alerts has been posted when user logs in
 *  
 */
public class FoundNewAlertTag extends TagSupport
{

	private final static MessageLogger log = MessageLogger.getMessageLogger(FoundNewAlertTag.class.getName());

	public FoundNewAlertTag()
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
        boolean errorpage=true;

		try
		{
			Object o = pageContext.getSession().getAttribute(MCXConstants.FINDNEWALERT);
			if(o==null)
			{
				IUser user = UserFactory.getUser((HttpServletRequest)pageContext.getRequest());
		        AlertServiceRequest serviceRequest = new AlertServiceRequest(user.getAuditInfo());
		        AlertServiceResponse serviceResponse = new AlertServiceResponse();
		        AlertInfo thealertinfo = new AlertInfo();
                thealertinfo.setUsertype(UserConstants.ADMINTYPE);
                serviceRequest.setTransaction(thealertinfo);
                ViewAlertDelegate delegate = new ViewAlertDelegate();
                serviceResponse = (AlertServiceResponse) delegate.processRequest(serviceRequest);
                if(serviceResponse.getAlertStatus()!=null && serviceResponse.getAlertStatus().equals("success"))
               	   errorpage = false;
               	if(!errorpage)
               	{
					pageContext.getSession().setAttribute(MCXConstants.FINDNEWALERT,"false");
					List al = serviceResponse.getAlertList();
					if(al!=null && al.size()>0)
					{
						AlertInfo ai = (AlertInfo)al.get(0);
						Calendar cal = Calendar.getInstance();
						cal.add(Calendar.HOUR_OF_DAY,-120);
						if(cal.getTime().before(ai.getUpdatedDate()))
						{
							pageContext.getSession().setAttribute(MCXConstants.FINDNEWALERT,"true");
							log.debug("Session Attribute findnewalert set to true");
						}
					}
				}


			}
			o = pageContext.getSession().getAttribute(MCXConstants.FINDNEWALERT);

			if(o!=null && ((String)o).equalsIgnoreCase("true"))
			{
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
		catch (BusinessException e)	
		{
			log.error(e);
			throw new JspException(e.getMessage());
		}
		catch (UserException e)	
		{
			log.error(e);
			throw new JspException(e.getMessage());
		}
		
	}

}
