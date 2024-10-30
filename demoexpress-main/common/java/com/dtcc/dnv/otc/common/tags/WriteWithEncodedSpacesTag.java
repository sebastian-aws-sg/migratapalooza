package com.dtcc.dnv.otc.common.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Copyright © 2004 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55 Water Street,
 * New York, NY 10041
 * 
 * 
 * @author sbrown
 * @date Feb 22, 2005
 * @version 1.0
 * 
 * rev 1.1 scb 3/4/2005
 * made into body tag
 * 
 * *************************************************************************
 * PROJECT MOVED TO A NEW REPOSITORY
 * 
 * @version	1.1			October 18, 2007		sv
 * Removed unused imports.
 */
public class WriteWithEncodedSpacesTag extends BodyTagSupport
{

	/**
	 * @see javax.servlet.jsp.tagext.BodyTag#doAfterBody()
	 */
	public int doAfterBody() throws JspException
	{
		String _bodyContent = encodeSpaces(bodyContent.getString());
		JspWriter writer = bodyContent.getEnclosingWriter();

		try
		{
			writer.print(_bodyContent);
		}
		catch (IOException e)
		{
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}

	/**
	 * Method encodeSpaces.
	 * @param s
	 * @return String
	 */
	private String encodeSpaces(String s)
	{
		String encodedString = "";
		for (int i = 0; i < s.length(); i++)
		{
			if (s.charAt(i) == ' ')
				encodedString += "&nbsp;";
			else
				encodedString += s.charAt(i);
		}

		return encodedString;
	}

}
