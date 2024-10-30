/*
 * Created on Sep 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.action.mca;

import java.sql.Timestamp;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.ApplicationContextHandler;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;

/**
 * This Action is used to get the Image object for the given Doc Id
 * @author VVaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GetImageDocumentAction extends MCXBaseAction {

	public ActionForward returnForward(MCXCUOUser user, 
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception
	{
		MessageLogger log = MessageLogger.getMessageLogger(GetImageDocumentAction.class.getName());		
		ActionErrors errors = new ActionErrors();
		String docId = request.getParameter("docId");
		try
		{
			//Set the Image Content Type as gif as it takes both Gif and jpg
			response.setContentType("image/gif");			
			ApplicationContextHandler appContHandler = ApplicationContextHandler.getInstance();
			
			ServletOutputStream sop = response.getOutputStream();
			//Get the Document Image Object from the Application Context Handler and Write to Output
			sop.write(appContHandler.getDocImage(docId));
			sop.flush();
		}
		catch (Exception e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
            		MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));
        }	
		
		return null;
	}
}
