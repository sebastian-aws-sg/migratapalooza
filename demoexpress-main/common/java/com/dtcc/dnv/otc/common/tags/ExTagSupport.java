package com.dtcc.dnv.otc.common.tags;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author dlarin
 *
 *  This is base super class for simple tags that do not 
 * manipulate with body of the tag
 */
public class ExTagSupport extends TagSupport {
	
	/**
	 * method to print body of the tag to output stream
	 */
	protected void writeHtml(JspWriter out, String html) 
		throws IOException
		{
			if( 
			(html.indexOf('<') == -1) &&
			(html.indexOf('>') == -1) &&
			(html.indexOf('&') == -1)) 
			{
				out.print(html);
			}
			else 
			{
				int len = html.length();
				for(int ii=0; ii<len; ii++) {
					char ch = html.charAt(ii);
					if(ch == '<') 
						out.print("<");
					else if(ch == '>') 
						out.print(">");
					else if(ch == '&') 
						out.print("&");
					else
						out.print(ch);
				}
			}
		}
	
	/**
	 * methods to log message 
	 */
	protected void log(String msg) {
		getServletContext().log(msg);
	}		
	
	/**
	 * method to log message and exception
	 */
	protected void log(String msg, Throwable e) {
		getServletContext().log(msg,e);
	}
			
	/**
	 * method to get a ServletContext out
	 */
	protected ServletContext getServletContext() {
		return pageContext.getServletContext();
	}
	
	/**
	 * method to get a ServletConfig out
	 */
	protected ServletConfig getServletConfig() {
		return pageContext.getServletConfig();
	}
	
	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException {
		clearServiceState();
		return super.doEndTag();
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release() {
		clearServiceState();
		clearProperties();
		super.release();
	}

	/**
	 * custom method to clear properties
	 */
	protected void clearProperties() {
	}
	
	/**
	 * custom method to clear service state
	 */
	protected void clearServiceState() {
	}
}
