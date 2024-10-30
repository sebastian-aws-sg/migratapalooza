package com.dtcc.dnv.otc.common.tags;

import java.util.StringTokenizer;

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
 * @date Dec 16, 2004
 * @version 1.0
 * 
 * ************************************************************************
 * PROJECT MOVED TO A NEW REPOSITORY.
 * 
 * @version 1.3
 * @author Cognizant
 * 09/11/2007
 * Modified to obtain IUser instance from UserFactory.getUser() instead of UserFactory.createUser(). 
 * 
 * @version	1.4				October 18, 2007			sv
 * Removed unused imports.
 */
public class AccessTag extends TagSupport
{
	private final String INQUIRY = "INQUIRY";
	private final String UPDATE = "UPDATE";
	private String privilege = "";
	private String product = "";
	private String module = "";

	
	public AccessTag()
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
		boolean userHasPrivilege = false;
		boolean userHasProduct = false;
		boolean userHasModule = false;

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

			//check access
			userHasPrivilege = checkPrivilege(user);
			userHasProduct = checkProduct(user);
			userHasModule = checkModule(user);

			if(userHasPrivilege && userHasProduct && userHasModule)
				return (EVAL_BODY_INCLUDE);
			else
				return SKIP_BODY;
		}
		catch (Exception e)
		{
			throw new JspException(e.getMessage());
		}
	}

	//	/**
	//	 * Overide parent method to perform end tag functionality for this tag.  This method
	//	 * closes a select tag (already created in startTag() if the user is in a family 
	//	 * (more than one member).  Otherwise it will created static html to display the
	//	 * participant information and create a hidden tag to store the appropriate values
	//	 * of the participant.
	//	 * 
	//	 * @throws JspException thrown if there is a JspException or there are issues attaining
	//	 * user information.
	//	 * 
	//	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	//	 */
	//	public int doEndTag() throws JspException
	//	{
	//
	//		try
	//		{
	//		
	//		}
	//		//		catch (JspException je) {
	//		//			throw je;
	//		//		}
	//		catch (Exception e)
	//		{
	//			throw new JspException(e.getMessage());
	//		}
	//		return EVAL_PAGE;
	//	}

	/**
	 * Method checkPrivilege.
	 * Returns true if the user has the specified privilege
	 * @param user
	 * @return boolean
	 */
	private boolean checkPrivilege(IUser user)
	{
		return user.hasModuleAccess(privilege);
	}

	/**
	 * Method checkProduct.
	 * If the product exists in the user's list of products, return true.
	 * Product could be comma separated list of products.  tokenize on comma and make sure one of products given are ok?
	 * 
	 * But product is not required, so if product is "", return true
	 * @param user
	 * @return boolean
	 */
	private boolean checkProduct(IUser user)
	{
		if (product.equals(""))
			return true;

		StringTokenizer st = new StringTokenizer(product, ",");

		//if the user can access one of the products, return true
		while(st.hasMoreTokens())
		{
			if( user.getProducts().contains( st.nextToken().toUpperCase() ) )
				return true;
		}

		//if we got this far, user has access to none of the products
		return false;
	}

	
	private boolean checkModule(IUser user)
	{
		boolean returnValue = true;
		
		if( !module.trim().equals("") )
		{
			returnValue = false;			
			StringTokenizer st = new StringTokenizer(module, ",");

			while( st.hasMoreElements() )
			{
				String mod = st.nextToken();
				if( user.getModules().contains(mod) )
				{
					returnValue = true;
					break;
				}
			}
		}
		
		return returnValue;
	}
	
	
	/**
	 * Returns the privilege.
	 * @return String
	 */
	public String getPrivilege()
	{
		return privilege;
	}

	
	/**
	 * Sets the privilege.
	 * @param privilege The privilege to set
	 */
	public void setPrivilege(String privilege)
	{
		this.privilege = privilege;
	}

	
	/**
	 * Returns the product.
	 * @return String
	 */
	public String getProduct()
	{
		return product;
	}

	
	/**
	 * Sets the product.
	 * @param product The product to set
	 */
	public void setProduct(String product)
	{
		this.product = product;
	}

	
	/**
	 * @return Returns the module.
	 */
	public String getModule()
	{
		return module;
	}
	
	
	/**
	 * @param module The module to set.
	 */
	public void setModule(String module)
	{
		this.module = module;
	}
}
