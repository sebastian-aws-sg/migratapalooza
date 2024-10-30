package com.dtcc.dnv.mcx.action.admin;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dtcc.dnv.mcx.action.IManagedSession;
import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.util.ApplicationContextHandler;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.delegate.admin.AppRejTemplateSetupDelegate;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceResponse;
import com.dtcc.dnv.mcx.form.TemplateForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/**
* This class is used as a Action to Approve/Reject the ISDA details 
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

public  class AppRejTemplateSetupAction extends MCXBaseAction implements IManagedSession 
{

	public ActionForward returnForward(MCXCUOUser user, 
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception
	{
		AuditInfo ai = null;
		ActionForward forward = new ActionForward(); 
		ActionMessages errors = new ActionMessages();
		ActionMessages messages = new ActionMessages();			
		
		TemplateBean templateBean = null;
		TemplateForm templateForm = null;
		TemplateServiceRequest serviceRequest = new TemplateServiceRequest(ai);
		TemplateServiceResponse serviceResponse = new TemplateServiceResponse();

		final String SUCCESS_FORWARD = "success";
		final String FAILURE_FORWARD = "failure";
		final String ERROR = "error";
		final String APPROVEREJECTERRORCODE = "SP02";

		MessageLogger log = MessageLogger.getMessageLogger(AppRejTemplateSetupAction.class.getName());
		
		try {
			
			templateForm = (TemplateForm) form;			
			templateBean = (TemplateBean)templateForm.getTransaction();
			if(templateBean == null)
				templateBean = new TemplateBean();
			// Add the lock indicator
			templateBean.setPublishDt(templateForm.getPublishDt());
			templateBean.setAppRejStatus(templateForm.getAppRejStatus());
			
			serviceRequest.setTransaction(templateBean);
			serviceRequest.setUserId(user.getMCXUserGUID());			
            AppRejTemplateSetupDelegate delegate = new AppRejTemplateSetupDelegate();
			serviceResponse = (TemplateServiceResponse)delegate.processRequest(serviceRequest);
            
			
			if (!serviceResponse.hasError())
            {
			  
                templateForm.setFlagTemplateApprovedRejected(false);
			    forward = mapping.findForward(SUCCESS_FORWARD);
            } else
            {
                 
                if(APPROVEREJECTERRORCODE.equalsIgnoreCase(serviceResponse.getSpReturnCode())){
                    templateForm.setErrorTemplateApprovedRejected(serviceResponse.getSpReturnMessage());
                   
                    templateForm.setFlagTemplateApprovedRejected(true);
                }
                else
                {
                    
                    templateForm.setFlagTemplateApprovedRejected(false);
                    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", serviceResponse.getSpReturnMessage()));
                }
                
                forward = mapping.findForward(ERROR);
            }
	
			ApplicationContextHandler appContHandler = ApplicationContextHandler.getInstance();
			appContHandler.updateProductList();
	
			request.setAttribute(MCXConstants.PRODUCTS, (Serializable)appContHandler.getProductsList());
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
