package com.dtcc.dnv.mcx.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.otc.common.security.model.IUser;
import com.dtcc.dnv.otc.common.security.model.UserFactory;

/*
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
 * 
 * @author Kevin Lake
 * @version 1.0
 * Date: October 23, 2007
 *
 */
public class UserNameTag extends TagSupport {

	public UserNameTag() {
		super();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.BodyTag#doAfterBody()
	 */
	public int doEndTag() throws JspException {
		
		try {
			HttpServletRequest request = ((HttpServletRequest) pageContext.getRequest());
			
			IUser user = UserFactory.getUser(request);
			
			super.pageContext.getOut().write(getMessageBody(user));

		} catch (Exception e) {
			throw new JspException(e.getMessage());
		}
		
		return EVAL_PAGE;
	}

	/**
	 * @param user
	 * @return boolean
	 */
	private String getMessageBody(IUser user) {
	  String userName = "";	
    
	  MCXCUOUser mcxCUOUser =  (MCXCUOUser)user;
	  String lastName = mcxCUOUser.getUserLastName();
      String firstName = mcxCUOUser.getUserFirstName();
      String userId = mcxCUOUser.getMCXUserGUID();
      
      if(lastName != null && lastName.trim().length() > 0)
        userName =  lastName;
      
      if(firstName != null && firstName.trim().length() > 0)
        userName +=  ", " + firstName;
      
      String userLink = "<a href='#' onclick=\"javascript:userDetailsPopUp('" + userId + "')\">" + userName + "</a>"; 
	  
	  return userLink;
	}

}
