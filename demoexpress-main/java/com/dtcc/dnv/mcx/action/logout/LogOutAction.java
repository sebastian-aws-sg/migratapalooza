package com.dtcc.dnv.mcx.action.logout;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
/**
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
 * @author Kevin Lake
 * @date Sep 20, 2007
 * @version 1.0
 *  
 */
public class LogOutAction extends DispatchAction {
	
	private final static MessageLogger log = MessageLogger.getMessageLogger(LogOutAction.class.getName());
	
	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			                     HttpServletResponse response) throws Exception {
		
		ActionForward forward = mapping.findForward(MCXConstants.SUCCESS_FORWARD);		
		try {
            HttpSession session = request.getSession(false);
            if(session != null)
              session.invalidate();			
        } catch (Exception e) {
        	log.error(e.getMessage());
        	forward = mapping.findForward(MCXConstants.ERROR_FORWARD);
        }
       	return forward;
    }

}
