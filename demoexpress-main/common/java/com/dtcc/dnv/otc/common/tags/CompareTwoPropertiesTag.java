package com.dtcc.dnv.otc.common.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.util.RequestUtils;

/**
 * Copyright © 2004 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55 Water Street,
 * New York, NY 10041
 * 
 * 
 * @author sbrown
 * @date Mar 18, 2005
 * @version 1.0
 * 
 * tag to compare 2 properties.  
 * similar to the logic:equal and logic:notEqual tags in struts. 
 * if you want to compare 2 to see if equal, set "operation" to "equal."
 * if you want notEqual, set "operation" to "notEqual"
 * 
 * **********************************************************
 * PROJECT MOVED TO A NEW REPOSITORY
 * @version	1.2			October 18, 2007		sv
 * Removed unused imports.
 */
public class CompareTwoPropertiesTag extends TagSupport
{

	private String name1 = "";
	private String name2 = "";
	private String property1 = "";
	private String property2 = "";
	private String operation = "";
	
	private String scope1 = null;
	private String scope2 = null;

	private static String EQUAL = "EQUAL";
	private static String NOTEQUAL = "NOTEQUAL";

	public CompareTwoPropertiesTag()
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
			boolean evalBody = false;

			if (RequestUtils.lookup(pageContext, name1, scope1) == null)
				return (SKIP_BODY); // Nothing to output

			// Look up the requested property value
			String value1 = String.valueOf(RequestUtils.lookup(pageContext, name1, property1, scope1));
			if (value1 == null)
				return (SKIP_BODY); // Nothing to output
				
			//now get second value
			if (RequestUtils.lookup(pageContext, name2, scope2) == null)
				return (SKIP_BODY); // Nothing to output

			// Look up the requested property value
			String value2 = String.valueOf(RequestUtils.lookup(pageContext, name2, property2, scope2));
			if (value2 == null)
				return (SKIP_BODY); // Nothing to output


				

			if (operation.toUpperCase().equals(EQUAL))
			{
				if (value1.equals(value2))
					evalBody = true;
			}
			else if (operation.toUpperCase().equals(NOTEQUAL))
			{
				if (!value1.equals(value2))
					evalBody = true;
			}

			if (evalBody)
			{
				return (EVAL_BODY_INCLUDE);

			}
			else //do not evaluate body
				return SKIP_BODY;
		}

		catch (Exception e)
		{
			throw new JspException(e.getMessage());
		}

	}

	/**
	 * Returns the operation.
	 * @return String
	 */
	public String getOperation()
	{
		return operation;
	}

	/**
	 * Sets the operation.
	 * @param operation The operation to set
	 */
	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	/**
	 * Returns the name1.
	 * @return String
	 */
	public String getName1()
	{
		return name1;
	}

	/**
	 * Returns the name2.
	 * @return String
	 */
	public String getName2()
	{
		return name2;
	}

	/**
	 * Returns the property1.
	 * @return String
	 */
	public String getProperty1()
	{
		return property1;
	}

	/**
	 * Returns the property2.
	 * @return String
	 */
	public String getProperty2()
	{
		return property2;
	}

	/**
	 * Sets the name1.
	 * @param name1 The name1 to set
	 */
	public void setName1(String name1)
	{
		this.name1 = name1;
	}

	/**
	 * Sets the name2.
	 * @param name2 The name2 to set
	 */
	public void setName2(String name2)
	{
		this.name2 = name2;
	}

	/**
	 * Sets the property1.
	 * @param property1 The property1 to set
	 */
	public void setProperty1(String property1)
	{
		this.property1 = property1;
	}

	/**
	 * Sets the property2.
	 * @param property2 The property2 to set
	 */
	public void setProperty2(String property2)
	{
		this.property2 = property2;
	}



	/**
	 * Returns the scope1.
	 * @return String
	 */
	public String getScope1()
	{
		return scope1;
	}

	/**
	 * Returns the scope2.
	 * @return String
	 */
	public String getScope2()
	{
		return scope2;
	}

	/**
	 * Sets the scope1.
	 * @param scope1 The scope1 to set
	 */
	public void setScope1(String scope1)
	{
		this.scope1 = scope1;
	}

	/**
	 * Sets the scope2.
	 * @param scope2 The scope2 to set
	 */
	public void setScope2(String scope2)
	{
		this.scope2 = scope2;
	}

}
