package com.dtcc.dnv.otc.common.tags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;


/**
 * Copyright © 2004 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55 Water Street,
 * New York, NY 10041
 * 
 * @author dlarin
 * @date Jan 21, 2004
 * @version 1.0
 */
public class SubstringTag extends ExBodyTagSupport 
{

	
	/**
	 * @see javax.servlet.jsp.tagext.BodyTag#doAfterBody()
	 */
	public int doAfterBody() throws JspException 
	{
		//return super.doAfterBody();
		if(splitLength > -1) {
			split();
		}
		else {
			substring();
		}
		return SKIP_BODY;
	}
	
	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release() {
		super.release();
		beginIndex = 0;
		maxLength = -1;
		endIndex = -1;	
		delimeter = "<br>";
		splitLength = -1;				
	}


	private int beginIndex = 0;
	private int maxLength = -1;
	private int endIndex = -1;
	private int splitLength = -1;
	private String delimeter = "<br>";	
	

	/**
	 * @see javax.servlet.jsp.tagext.BodyTag#doAfterBody()
	 */
	public void substring()
		throws JspException
	{
		//return super.doAfterBody();
		String _bodyContent = bodyContent.getString();
		JspWriter writer = bodyContent.getEnclosingWriter();
					
		try {
			if(_bodyContent != null) {
				int size = _bodyContent.length();
				if(endIndex == -1 && maxLength == -1)
				    writer.print(_bodyContent);
				else if(maxLength != -1 && (beginIndex+maxLength < size))
					writer.print( _bodyContent.substring(beginIndex,beginIndex+maxLength) );
				else if(endIndex != -1 && (endIndex < size))
					writer.print( _bodyContent.substring(beginIndex,endIndex) );
				else
					writer.print( _bodyContent );
			}
		}
		catch(IOException e) {
			throw new JspTagException(e.getMessage());
		}
	}

	/**
	 * @see javax.servlet.jsp.tagext.BodyTag#doAfterBody()
	 */
	public int split() throws JspException 
	{
		//return super.doAfterBody();
		String _bodyContent = bodyContent.getString();
		JspWriter writer = bodyContent.getEnclosingWriter();

		try 
		{
			int index=0;
			int totalSize= _bodyContent.length();
			int endIndex=0;
			do
			{
				endIndex = index + splitLength;
				if(endIndex < totalSize)
					writer.print( _bodyContent.substring(index,endIndex) );
				else
					writer.print( _bodyContent.substring(index) );
				writer.print(delimeter);
				index = index + splitLength;
			}while( totalSize > index );

		}
		catch(IOException e) {
			throw new JspTagException(e.getMessage());
		}
		return SKIP_BODY;
	}



	/**
	 * Sets the delimeter.
	 * @param delimeter The delimeter to set
	 */
	public void setDelimeter(String delimeter) {
		this.delimeter=delimeter;
	}

	/**
	 * Sets the maxLength.
	 * @param maxLength The maxLength to set
	 */
	public void setSplitLength(int splitLength) {
		this.splitLength=splitLength;
	}

	/**
	 * Sets the beginIndex.
	 * @param beginIndex The beginIndex to set
	 */
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	/**
	 * Sets the endIndex.
	 * @param endIndex The endIndex to set
	 */
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	/**
	 * Sets the maxLength.
	 * @param maxLength The maxLength to set
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	
}
