package com.dtcc.dnv.otc.common.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.dtcc.dnv.otc.common.security.model.IUser;
import com.dtcc.dnv.otc.common.security.model.UserFactory;

/**
 * Copyright © 2004 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55 Water Street,
 * New York, NY 10041
 * 
 * 
 * @author sbrown
 * @date Mar 28, 2005
 * @version 1.0
 * 
 * The Participant's Principal Role Indicator are:
 *  S - Service Bureau,
 *  F - Family,
 *  I - Individual	
 * 
 * @version 1.1 
 * @author Cognizant
 * 09/11/2007
 * Modified to obtain IUser instance from UserFactory.getUser() instead of UserFactory.createUser().
 * 
 * @version	1.2			October 18, 2007		sv
 * Removed unused imports.
 */
public class UserTypeTag extends TagSupport
{
	private final String SERVICE_BUREAU = "S";
	private final String FAMILY = "F";
	private final String INDIVIDUAL = "I";

	private String role = "";

	public UserTypeTag()
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

		boolean userHasRole = false;

		try
		{
			HttpServletRequest request = ((HttpServletRequest) pageContext.getRequest());
			HttpServletResponse response = ((HttpServletResponse) pageContext.getResponse());

			// replacing UserFactory.createUser() to UserFactory.getUser()
			IUser user = UserFactory.getUser(request);
			
			/* 1. privilege is required
			 * 2. product is optional.  if there, check access to product. 
			 * 3. source is optional.  we take source from the tag before we take the body.  
			 * 4. if there is a body, we ONLY use it if there is no source. 
			 * */

			//check role
			userHasRole = checkRole(user);

			//we only want to look at the body if we have the role we said 
			if (userHasRole)
			{
				return (EVAL_BODY_INCLUDE);

			}
			else //we don't have the role, so skip everything
				return SKIP_BODY;
		}
		//				catch (JspException je) {
		//					throw je;
		//				}
		catch (Exception e)
		{
			throw new JspException(e.getMessage());
		}

	}

	/**
	 * Method checkRole.
	 * Returns true if the user has the specified role
	 * @param user
	 * @return boolean
	 */
	private boolean checkRole(IUser user)
	{
		return user.getParticipantType().equalsIgnoreCase(role);
	}

	/**
	 * Returns the role.
	 * @return String
	 */
	public String getRole()
	{
		return role;
	}

	/**
	 * Sets the role.
	 * @param role The role to set
	 */
	public void setRole(String role)
	{
		this.role = role;
	}

}
