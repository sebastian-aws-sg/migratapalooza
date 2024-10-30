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
import com.dtcc.dnv.mcx.delegate.mca.GenerateTemplateNameDelegate;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceResponse;
import com.dtcc.dnv.mcx.form.TemplateForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
 * This class is used as a Action to the Generic Template Name
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

public class GenerateTemplateNameAction extends MCXBaseAction {

	public ActionForward returnForward(MCXCUOUser user, 
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception
	{
		MessageLogger log = MessageLogger.getMessageLogger(GenerateTemplateNameAction.class.getName());
		ActionMessages errors = new ActionMessages();
		
		TemplateForm templateForm = (TemplateForm) form;
		
		try
		{
			TemplateServiceRequest serviceRequest = new TemplateServiceRequest(user.getAuditInfo());

			//If the Template Id is present in Form Object, get it from there
			if(!templateForm.getTmpltId().equalsIgnoreCase(""))
				((TemplateBean)templateForm.getTransaction()).setTmpltId(Integer.parseInt(templateForm.getTmpltId()));

			//Set the TemplateBean in Service Request Transaction
		serviceRequest.setTransaction(templateForm.getTransaction());	
		
			//Set the User Id and Company Id in Service Request
		serviceRequest.setUserId(user.getMCXUserGUID());
		serviceRequest.setCmpnyId(user.getCompanyId());
		
		GenerateTemplateNameDelegate generateTemplateNameDelegate = new GenerateTemplateNameDelegate();
		TemplateServiceResponse serviceResponse = (TemplateServiceResponse)generateTemplateNameDelegate.processRequest(serviceRequest);
		
			if(serviceResponse.hasError())
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						MCXConstants.GENERAL_BUSINESS_ERROR, serviceResponse.getSpReturnMessage()));

		templateForm.setTransaction(serviceResponse.getTransaction());
		}
		catch (BusinessException e)
		{
	        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
	        		MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));
	        return mapping.findForward(MCXConstants.ERROR_FORWARD);
	    }
		catch (Exception e)
	    {
	        log.error(e);
	        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
	        		MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));
	        return mapping.findForward(MCXConstants.ERROR_FORWARD);
	    }
		finally
		{
			if (!errors.isEmpty())
				saveErrors(request, errors);
		}
		
		return mapping.findForward(MCXConstants.SUCCESS_FORWARD);
	}

}
