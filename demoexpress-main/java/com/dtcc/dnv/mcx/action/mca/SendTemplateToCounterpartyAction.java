package com.dtcc.dnv.mcx.action.mca;

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
import com.dtcc.dnv.mcx.delegate.mca.SendTemplateToCounterpartyDelegate;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceResponse;
import com.dtcc.dnv.mcx.form.TemplateForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.ApplicationContextHandler;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
 * This class is used as a Action to fetch details of a ISDA published MCA template
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
 * @author Narahari A
 * @date Sep 3, 2007
 * @version 1.0
 */

public class SendTemplateToCounterpartyAction extends MCXBaseAction {

	public ActionForward returnForward(MCXCUOUser user, 
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception
	{
		MessageLogger log = MessageLogger.getMessageLogger(SendTemplateToCounterpartyAction.class.getName());
		ActionMessages errors = new ActionMessages();
		ActionForward forward = mapping.findForward(MCXConstants.SUCCESS_FORWARD);
		
		TemplateForm templateForm = (TemplateForm) form;

		//To show the Confirmation Pop-up
		if(templateForm.getOpnInd().equalsIgnoreCase(MCXConstants.CHECK_SAVE))
		{
			return mapping.findForward("confirm");
		}

		try
		{
			TemplateServiceRequest serviceRequest = new TemplateServiceRequest(user.getAuditInfo());
			TemplateBean templateBean = (TemplateBean) templateForm.getTransaction();
			
			if(!templateForm.getTmpltId().equalsIgnoreCase(""))
				templateBean.setTmpltId(Integer.parseInt(templateForm.getTmpltId()));
			
			//Set the TemplateBean in Service Request Transaction			
			serviceRequest.setTransaction(templateBean);	
			//Set the User Id and Company Id in Service Request
			serviceRequest.setUserId(user.getMCXUserGUID());
			serviceRequest.setCmpnyId(user.getCompanyId());
		
			SendTemplateToCounterpartyDelegate delegate = new SendTemplateToCounterpartyDelegate();
			TemplateServiceResponse serviceResponse = (TemplateServiceResponse) delegate.processRequest(serviceRequest);
			if (serviceResponse.hasError() && !templateForm.getOpnInd().equalsIgnoreCase(MCXConstants.OPERATION_IND_S))
            {                
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, serviceResponse.getSpReturnMessage()));
                forward = mapping.findForward("businesserr");
            } else if (serviceResponse.hasError() && templateForm.getOpnInd().equalsIgnoreCase(MCXConstants.OPERATION_IND_S))
            {              
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, serviceResponse.getSpReturnMessage()));
                forward = mapping.findForward("popupbusinesserr");
                templateForm.setTransaction(serviceRequest.getTransaction());
            } else
            {                
                templateForm.setTransaction(serviceResponse.getTransaction());
            }

			//If it is from Popup page forward to Router page

			if(templateForm.getOpnInd().equalsIgnoreCase(MCXConstants.OPERATION_IND_S) && ! serviceResponse.hasError())
			{
				forward = mapping.findForward("submit");
				request.setAttribute("SUBMIT_SUCCESS", templateForm);
			}
			
			//Update Pending MCA Menu
			ApplicationContextHandler appCont = ApplicationContextHandler.getInstance();
			if(!user.getCompanyId().equalsIgnoreCase(templateBean.getOrgCltCd()))
			{
				appCont.loadUpdatePendingMCAs(user.getCompanyId());
				appCont.loadUpdatePendingMCAs(templateBean.getOrgCltCd());
			}
			else if(templateBean.getOrgCltCd().equalsIgnoreCase(templateBean.getOrgDlrCd()))
			{
				appCont.loadUpdatePendingMCAs(user.getCompanyId());
			}
		}
		catch (BusinessException e)
		{		    
	        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));
	        if(! templateForm.getOpnInd().equalsIgnoreCase(MCXConstants.OPERATION_IND_S))
	        return mapping.findForward(MCXConstants.ERROR_FORWARD);
			else 
	        return mapping.findForward(MCXConstants.ERROR_POPUP_FORWARD);
				
	    } catch (Exception e)
	    {
	        log.error(e);
	        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));
	        if(! templateForm.getOpnInd().equalsIgnoreCase(MCXConstants.OPERATION_IND_S))
	        return mapping.findForward(MCXConstants.ERROR_FORWARD);
			else 
		        return mapping.findForward(MCXConstants.ERROR_POPUP_FORWARD);
	    }
		finally
		{
			if (!errors.isEmpty())
				saveErrors(request, errors);
		}
		return forward;
	}

}
