package com.dtcc.dnv.mcx.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.action.DSBaseAction;
import com.dtcc.dnv.otc.common.security.model.IUser;

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
public abstract class MCXBaseAction extends DSBaseAction {

	private final static MessageLogger log = MessageLogger.getMessageLogger(MCXBaseAction.class.getName());

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.action.DSBaseAction#returnForward(com.dtcc.dnv.otc.common.security.model.IUser, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward returnForward(IUser user,
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

		ActionForward forward = mapping.findForward(MCXConstants.SUCCESS_FORWARD);

		MCXCUOUser mcxCUOUser = null;
        if (user instanceof MCXCUOUser ) {
        	mcxCUOUser = (MCXCUOUser)user;

        	if(this instanceof IManagedSession)
        	{
    			HttpSession session = request.getSession();
    			Enumeration enum = session.getAttributeNames();
    			while (enum.hasMoreElements()) {
    				String element = (String) enum.nextElement();
    				Object obj = session.getAttribute(element);
    				if(obj instanceof ActionForm && !form.equals(session.getAttribute(element))) {
    					session.removeAttribute(element);
    				}
    			}
        	}
        } else {
        	log.error("Unsupported user type");
        	forward = mapping.findForward(MCXConstants.FAILURE_FORWARD);
        }

        forward = returnForward(mcxCUOUser, mapping, form, request, response);

		return forward;
	}

	/**
	 * @param user
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public abstract ActionForward returnForward(MCXCUOUser user,
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception;

}
