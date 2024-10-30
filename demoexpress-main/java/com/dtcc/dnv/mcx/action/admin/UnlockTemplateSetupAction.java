package com.dtcc.dnv.mcx.action.admin;


import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.delegate.admin.UnlockTemplateSetupDelegate;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceResponse;
import com.dtcc.dnv.mcx.form.TemplateForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/**
* This class is used as a Action to Unlock the ISDA details 
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
* @date Sep 19, 2007
* @version 1.0
*/

public  class UnlockTemplateSetupAction extends MCXBaseAction 
{

	public ActionForward returnForward(MCXCUOUser user, 
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception
	{
		
		ActionForward forward = new ActionForward(); 
		ActionMessages errors = new ActionMessages();
		ActionMessages messages = new ActionMessages();			
		final String SUCCESS_FORWARD = "success";
		final String FAILURE_FORWARD = "failure";
		// Create user
		AuditInfo ai = null;		
		TemplateForm templateForm = null;
		TemplateServiceRequest serviceRequest = new TemplateServiceRequest(ai);
		TemplateServiceResponse serviceResponse = new TemplateServiceResponse();
		MessageLogger log = MessageLogger.getMessageLogger(UnlockTemplateSetupAction.class.getName());
				
		try {			
			templateForm = (TemplateForm) form;		

			serviceRequest.setCmpnyId(user.getCompanyId());			
			serviceRequest.setUserId(user.getMCXUserGUID());
			serviceRequest.setOpnInd(templateForm.getOpnInd());
			if(templateForm.getTmpltId() != "")
				((TemplateBean)templateForm.getTransaction()).setTmpltId(Integer.parseInt(templateForm.getTmpltId()));

			serviceRequest.setTransaction(templateForm.getTransaction());
			
			UnlockTemplateSetupDelegate delegate = new UnlockTemplateSetupDelegate();
			serviceResponse = (TemplateServiceResponse)delegate.processRequest(serviceRequest);
			/*if (!serviceResponse.hasError())
            {
				String frmSrc = templateForm.getFrmScr();
				if(frmSrc != null && (frmSrc.equalsIgnoreCase("MW1") || frmSrc.equalsIgnoreCase("NEG")))
				{
					forward = mapping.findForward("MCA");
					return forward;
				}					
				forward = mapping.findForward(SUCCESS_FORWARD);
            }
			else
		    {
			    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", serviceResponse.getSpReturnMessage()));
		    }*/
			String frmSrc = templateForm.getFrmScr();
			if(frmSrc != null && (frmSrc.equalsIgnoreCase("MW1") || frmSrc.equalsIgnoreCase("NEG")))
			{
				forward = mapping.findForward("MCA");
				return forward;
			}					
			forward = mapping.findForward(SUCCESS_FORWARD);
			
			if (serviceResponse.hasError())
			{
			    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", serviceResponse.getSpReturnMessage()));
			}
			
		} catch (BusinessException e)
		{
	        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", new Timestamp(System.currentTimeMillis())));
	        forward = mapping.findForward(MCXConstants.ERROR_FORWARD);

	    } catch (Exception e)
	    {
	        log.error(e);
	        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.internal.error", new Timestamp(System.currentTimeMillis())));
	        forward = mapping.findForward(MCXConstants.ERROR_FORWARD);
	    }
		finally
		{
			if (!errors.isEmpty())
				saveErrors(request, errors);
			if (!messages.isEmpty())
				saveMessages(request, messages);
		}
		// Finish with
		return forward;
	}
}
