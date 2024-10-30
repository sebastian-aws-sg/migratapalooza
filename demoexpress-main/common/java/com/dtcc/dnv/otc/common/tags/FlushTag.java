package com.dtcc.dnv.otc.common.tags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * @author dlarin
 *
 * Tag had been implemented for testing error-redirect logic of applicaiton.
 * Does flash output stream at the point where implemented
 */
public class FlushTag extends ExTagSupport {
	

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException {
		try 
		{
			JspWriter out = pageContext.getOut();
			if(!(out instanceof BodyContent) ) 
			{
				out.flush();
			}
		}
		catch(IOException e)  {
			throw new JspException("FlushTag: can not flash buffer, "+e.toString());
		}
		return super.SKIP_BODY;
	}

}
