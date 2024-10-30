/**
 * @author Dmitriy Larin
 * @date May 26, 2004
 * @version 1.0
 */
package com.dtcc.dnv.otc.common.tags;

import java.util.Iterator;
//import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
//import javax.servlet.jsp.PageContext;
//import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.action.*;
//import org.apache.struts.taglib.html.BaseInputTag;
import org.apache.struts.taglib.html.TextTag;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

/**
 * @author dlarin
 *
 * loops through error info and write down a message
 */

public class TextTagExt extends TextTag
{

    public TextTagExt()
    {
    }

    public int doAfterBody()
        throws JspException
    {
        ActionErrors actionErrors = (ActionErrors)pageContext.getRequest().getAttribute("org.apache.struts.action.ERROR");
        if(actionErrors != null)
        {
            String message;
            for(Iterator iter = actionErrors.get(getProperty()); iter.hasNext(); ResponseUtils.write(pageContext, message + "    "))
            {
                ActionError error = (ActionError)iter.next();
                String key = error.getKey();
                Object values[] = error.getValues();
                message = RequestUtils.message(pageContext, null, null, key, values);
            }

        }
        return 0;
    }
}
