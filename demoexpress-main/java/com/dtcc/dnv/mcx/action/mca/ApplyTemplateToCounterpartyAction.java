package com.dtcc.dnv.mcx.action.mca;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.delegate.mca.ApplyTemplateToCounterpartyDelegate;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceResponse;
import com.dtcc.dnv.mcx.form.TemplateForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
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

public class ApplyTemplateToCounterpartyAction extends MCXBaseAction {

	public ActionForward returnForward(MCXCUOUser user, 
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception
	{
		MessageLogger log = MessageLogger.getMessageLogger(ApplyTemplateToCounterpartyAction.class.getName());
        ActionMessages errors = new ActionMessages();

		TemplateForm templateForm = (TemplateForm) form;
		HttpSession session = request.getSession();
		try
		{
		TemplateBean templateBean = (TemplateBean) templateForm.getTransaction();
			//Set the Client Code and Client Name
		templateBean.setOrgCltCd(templateForm.getCltCd());
		templateBean.setOrgCltNm(templateForm.getCltNm());
	
			TemplateServiceRequest serviceRequest = new TemplateServiceRequest(user.getAuditInfo());

			//Set the TemplateBean in Service Request Transaction
		serviceRequest.setTransaction(templateBean);	
			//Set the User Id and Company Id in Service Request
		serviceRequest.setUserId(user.getMCXUserGUID());
		serviceRequest.setCmpnyId(user.getCompanyId());		

		ApplyTemplateToCounterpartyDelegate delegate = new ApplyTemplateToCounterpartyDelegate();
		TemplateServiceResponse serviceResponse = (TemplateServiceResponse) delegate.processRequest(serviceRequest);
			if(serviceResponse.hasError())
			{
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						MCXConstants.GENERAL_BUSINESS_ERROR, serviceResponse.getSpReturnMessage()));
			}
		
			request.setAttribute("APPLY_TEMPLATE_SUCCESS", templateForm);
			//Remove the Enrolled CP's list from Session, bcos next time it can be fetched from DB
			session.removeAttribute(MCXConstants.ENROLLEDCPs);
		}
		catch (BusinessException e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, 
            		new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));
            return mapping.findForward(MCXConstants.ERROR_POPUP_FORWARD);
        } catch (Exception e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, 
            		new ActionMessage(MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));
            return mapping.findForward(MCXConstants.ERROR_POPUP_FORWARD);
        }
        finally 
		{
			if (!errors.isEmpty())
				saveErrors(request, errors);
		}
		return mapping.findForward(MCXConstants.SUCCESS_FORWARD);
	}

}
