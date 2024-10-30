/**
 * @author Dmitriy Larin
 * @date May 26, 2004
 * @version 1.0
 */
package com.dtcc.dnv.otc.common.tags;

import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

/**
 * @author dlarin
 *
 * highlight key on a page with red when required
 *
 * @version	1.1			Oct 18, 2007		sv
 * Removed unused imports.  Checked in for Cognizant.  Modified the isError() 
 * method to replace ReplaceUtils.getActionErrors() with 
 * RequestUtils.getActionMessages()
 */

public class RedIfErrorTag extends BodyTagSupport
{

    private String property;
    private String color;

    public RedIfErrorTag()
    {
        color = "red";
    }

    private boolean isError()
        throws JspException
    {
        /*
		 * Modified to replace RequestUtils.getActionErrors() with 
    	 * RequestUtils.getActionMessages. The variable name also
		 * is changed from actionErrors to actionMessages
		 */
    	ActionMessages actionMessages = RequestUtils.getActionMessages(pageContext, "org.apache.struts.action.ERROR");
        if(actionMessages == null)
            return false;
        Iterator iter = actionMessages.get(getProperty());
        if(iter == null)
            return false;
        return iter.hasNext();
    }

    public String getProperty()
    {
        return property;
    }

    public void setProperty(String property)
    {
        this.property = property;
    }

    public String getColor()
    {
        if(color == null)
            color = "red";
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public int doAfterBody()
        throws JspException
    {
        boolean wasErrors = isError();
        StringBuffer buffer = new StringBuffer();
        if(wasErrors)
            buffer.append("<font color=\"" + getColor() + "\">");
        buffer.append(bodyContent.getString());
        try
        {
            bodyContent.clear();
        }
        catch(Exception e)
        {
            throw new JspException(e.toString());
        }
        if(wasErrors)
            buffer.append("</font>");
        ResponseUtils.writePrevious(pageContext, buffer.toString());
        return 0;
    }
}
