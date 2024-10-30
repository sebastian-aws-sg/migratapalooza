package com.dtcc.dnv.otc.common.tags;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * @author dlarin
 *
 * produce machine tag info
 */
public class MachineFullTag implements Tag, Serializable {

	private PageContext pc = null;
	private Tag parent = null;
	private String name = null;
	private static String _machineTag = null;

	public void setPageContext(PageContext p) {
		pc = p;
	}

	public void setParent(Tag t) {
		parent = t;
	}

	public Tag getParent() {
		return parent;
	}

	public void setName(String s) {
		name = s;
	}
	
	public String getName() {
		return name;
	}

	public int doStartTag() throws JspException {
		try {
	
			if(_machineTag == null) {
				pc.getOut().write(getMachineTag());
			} else {
				pc.getOut().write(_machineTag);
			}

		} catch(IOException e) {
			throw new JspTagException("An IOException occurred.");
		}
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public void release() {
		pc = null;
		parent = null;
		name = null;
	}
	
	/**
	 * Custom method to get a machine tag for
	 * presentation
	 */
	    public static String getMachineTag()
	    {
	    	
	
	        if ( _machineTag == null )
	        {
	            // This has not yet been made -- create it and store it for
	            // future use.
	            InetAddress address = null;
	            try
	            {
	               address = InetAddress.getLocalHost();
	            }
	            catch ( UnknownHostException ex )
	            {
	                // Ignore...address will be null.
	            }
	            if ( address != null )
	            {
	                String hostname = address.getHostName();
                    _machineTag = hostname;	                
	            }
	        }
	        return _machineTag;
	}
}
