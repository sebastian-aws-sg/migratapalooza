package com.dtcc.dnv.mcx.action.admin;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.form.TemplateForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;

/**
* This class is used as a Action to Post the ISDA Term Details by the admin
* 
* Copyright © 2003 The Depository Trust & Clearing Company. All rights
* reserved.
* 
* Depository Trust & Clearing Corporation (DTCC) 55, Water Street, New York, NY
* 10048, U.S.A All Rights Reserved.
* 
* This software may contain (in part or full) confidential/proprietary
* information of DTCC. ("Confidential Information"). Disclosure of such         
* Confidential Information is prohibited and should be used only for its
* intended purpose in accordance with rules and regulations of DTCC.
* 
* @author Elango TR
* @date Sep 21, 2007
* @version 1.0
*/

public class ApprovalDateAction extends MCXBaseAction  
{
	public ActionForward returnForward(MCXCUOUser user, 
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception
	{
	    MessageLogger log = MessageLogger.getMessageLogger(ApprovalDateAction.class.getName());
		ActionForward forward = new ActionForward();
		ActionMessages errors = new ActionMessages();
		final String FAILURE_FORWARD = "failure";
		final String SUCCESS_FORWARD = "success";
		try {
		log.info("Inside the com.dtcc.dnv.mcx.action.admin.ApprovalDateAction");
		TemplateForm templateForm = null;
		templateForm = (TemplateForm) form;
		forward = mapping.findForward(SUCCESS_FORWARD);
		} catch (Exception e)
	    {
	        log.error(e);
	        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.internal.error", new Timestamp(System.currentTimeMillis())));
	        forward = mapping.findForward(MCXConstants.ERROR_FORWARD);
	    }
		log.info("Exiting the com.dtcc.dnv.mcx.action.admin.ApprovalDateAction");
		return forward;
	}

}
