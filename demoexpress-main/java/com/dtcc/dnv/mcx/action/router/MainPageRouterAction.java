package com.dtcc.dnv.mcx.action.router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.mcx.util.MessageResources;
/*
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55, Water Street,
 * New York, NY 10048
 * U.S.A
 * All Rights Reserved.
 *
 * This software may contain (in part or full) confidential/proprietary information of DTCC.
 * ("Confidential Information"). Disclosure of such Confidential
 * Information is prohibited and should be used only for its intended purpose
 * in accordance with rules and regulations of DTCC.
 * 
 * @author Kevin Lake
 * @version 1.0
 * Date: September 05, 2007
 */

public class MainPageRouterAction extends MCXBaseAction {
	
	private static final String FORWARD_DEALER = "dealer";
	private static final String FORWARD_CLIENT = "client";
	private static final String FORWARD_TADMIN = "tmpltAdmin";	
	
	private static final MessageLogger log = MessageLogger.getMessageLogger(MCXBaseAction.class.getName());
	
	private static final String MCX_UNSUPPORTED_USER_MSGKEY = "mcx.user.unsupported.type";
	
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.mcx.action.MCXBaseAction#returnForward(com.dtcc.dnv.mcx.user.MCXCUOUser, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward returnForward(MCXCUOUser user,
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		
		ActionMessages errors = new ActionMessages();		
		ActionForward forward = new ActionForward();
		
		if(user.isDealer()) {
		  forward = mapping.findForward(FORWARD_DEALER);
		} else if (user.isClient()) {
		  forward = mapping.findForward(FORWARD_CLIENT);
		} else if  (user.isTemplateAdmin()) {
		  forward = mapping.findForward(FORWARD_TADMIN);
		} else {			
		
          // Log error message	
		  log.error(MessageResources.getMessage(MCX_UNSUPPORTED_USER_MSGKEY));
		  
		  // Set error message
		  errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCX_UNSUPPORTED_USER_MSGKEY));
		  
		  // Failure forward
		  forward = mapping.findForward(MCXConstants.FAILURE_FORWARD); 
		}
		
		if(!errors.isEmpty())
			saveErrors(request, errors);
		
		return forward;
	}
}
