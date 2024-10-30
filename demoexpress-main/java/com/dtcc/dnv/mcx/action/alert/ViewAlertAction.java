package com.dtcc.dnv.mcx.action.alert;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.beans.AlertInfo;
import com.dtcc.dnv.mcx.delegate.alert.AlertServiceRequest;
import com.dtcc.dnv.mcx.delegate.alert.AlertServiceResponse;
import com.dtcc.dnv.mcx.delegate.alert.ViewAlertDelegate;
import com.dtcc.dnv.mcx.form.AlertForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.user.UserConstants;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

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
 * @author Peng Zhou
 * @date Sep 20, 2007
 * @version 1.0
 * 
 * This class is used to retrieve the alerts list.
 *  
 */
public class ViewAlertAction extends MCXBaseAction {

	private final static MessageLogger log = MessageLogger.getMessageLogger(ViewAlertAction.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtcc.dnv.mcx.action.MCXBaseAction#returnForward(com.dtcc.dnv.mcx.user.MCXCUOUser,
	 *      org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping, ActionForm form,
			                           HttpServletRequest request, 
									   HttpServletResponse response) throws Exception {
		boolean errorpage = true;
		AlertServiceRequest serviceRequest = new AlertServiceRequest(user.getAuditInfo());
		AlertServiceResponse serviceResponse = new AlertServiceResponse();
		ActionMessages errors = new ActionMessages();
		try {

			AlertForm theform = (AlertForm) form;
			AlertInfo thisalertinfo = new AlertInfo();

			if (user.isTemplateAdmin()) {

				thisalertinfo.setUsertype(UserConstants.ADMINTYPE);
			} else {
				thisalertinfo.setUsertype(UserConstants.USERTYPE);
			}
			serviceRequest.setTransaction(thisalertinfo);

			ViewAlertDelegate delegate = new ViewAlertDelegate();

			serviceResponse = (AlertServiceResponse) delegate.processRequest(serviceRequest);
			if (serviceResponse.hasError()) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("viewalert",
						serviceResponse.getSpReturnMessage()));

			}

			if (serviceResponse.getAlertStatus() != null && 
				serviceResponse.getAlertStatus().equals("success"))
				errorpage = false;
		} catch (BusinessException be) {
			log.error(be);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error",
					be.getMessage()));
		} catch (Exception e) {
			log.error(e);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", e
					.getMessage()));
		}

		if (errorpage) {
			if (!errors.isEmpty()) {
				saveMessages(request, errors);
			}
			return mapping.findForward(MCXConstants.ERROR_FORWARD);
		} else {
			List lalert = serviceResponse.getAlertList();
			if(lalert!=null && lalert.size()>0)
				request.setAttribute("alertlist", serviceResponse.getAlertList());
			return mapping.findForward("showalerts");

		}
	}
}