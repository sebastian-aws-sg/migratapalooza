package com.dtcc.dnv.otc.common.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.security.model.IUser;
import com.dtcc.dnv.otc.common.security.model.UserFactory;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation 55 Water Street,
 * New York, New York, 10041 All Rights Reserved
 * 
 * @author Cognizant
 * @date Aug 28, 2007
 * @version 1.0
 * 
 * 
 * UserDisplayTag serves the derivserv.tld file. It prints a 
 * users attribute to the screen based on the value attribute of the Tag.
 * 
 * @version	1.1				October 18, 2007			sv
 * Updated logger to be static final.
 */

public class UserDisplayTag extends TagSupport {
	
	// Instance members
	private String userObjectKey= null;  // holds the logical name for the user attribute to be displayed
	
	//	 Static members
	private static final Logger logger = Logger.getLogger(UserDisplayTag.class);
	
	public UserDisplayTag() {
		super();
	}

	/**
	 * @throws JspException
	 *           thrown if there is a JspException or there are issues
	 *             attaining user information.
	 * @see javax.servlet.jsp.tagext.TagSupport@doStartTag()
	 * @return int
	 */

	public int doStartTag() throws JspException {
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			//retrieve the user
			IUser user = UserFactory.getUser(request);
			//check the value attribute set in the tag
			if(userObjectKey != null && userObjectKey.equals("ocode"))
				//if ocode, then print originatorcode from the user object
				pageContext.getOut().print(user.getOriginatorCode());
			return SKIP_BODY;  // skips the body of the tag in any
		}catch(UserException userExp)
		{
			logger.error("Cannot retrieve user from session");
			throw new JspException(userExp.getMessage());
		}
		catch(IOException IOExp)
		{	
			logger.error("Output Stream cannot be obtained");
			throw new JspException("Output Stream cannot be obtained : " + IOExp.getMessage());
		}
	}
	
	/**
	 * sets the logical name of the user attribute to be displayed 
	 * from the value attribute of the user Tag
	 * @param value
	 */
	public void setUserObjectKey(String userObjectKey) {
		this.userObjectKey = userObjectKey;
	}
}//End of UserDisplayTag class
