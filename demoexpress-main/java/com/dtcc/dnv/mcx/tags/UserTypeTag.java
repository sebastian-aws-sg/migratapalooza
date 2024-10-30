package com.dtcc.dnv.mcx.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.user.UserConstants;
import com.dtcc.dnv.otc.common.exception.UserException;
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
 * Date: September 19, 2007
 */

public class UserTypeTag extends TagSupport {

	private String role = "";

	/**
	 * UserTypeTag
	 */
	public UserTypeTag() {
		super();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException {

		boolean userHasRole = false;

		try {
			
			HttpServletRequest request = ((HttpServletRequest) pageContext.getRequest());			

			// Get current IUser object
			IUser user = UserFactory.getUser(request);

			// Check role
			userHasRole = checkRole(user);

			// Include body user has role
			if (userHasRole)
			  return (EVAL_BODY_INCLUDE);
			else
			  return SKIP_BODY;
			
		} catch (UserException e) {
		  throw new JspException(e.getMessage());
		}
		
	}

	/**
	 * Check to see if user has the specified role
	 * @param user
	 * @return boolean
	 */
	private boolean checkRole(IUser user) {
		boolean hasRole = false;
		if(role != null && role.length() > 0) {
			MCXCUOUser mcxUser = (MCXCUOUser)user;
			if(role.equalsIgnoreCase(UserConstants.DEALER_IND)) {
				hasRole = mcxUser.isDealer();
			} else if (role.equalsIgnoreCase(UserConstants.CLIENT_IND)) {
				hasRole = mcxUser.isClient();
			} else if (role.equalsIgnoreCase(UserConstants.TMPLT_ADMIN_IND)) {
				hasRole = mcxUser.isTemplateAdmin();
			} 
		}
		return hasRole;
	}

	/**
	 * @return String
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 */
	public void setRole(String role) {
		this.role = role;
	}

}
