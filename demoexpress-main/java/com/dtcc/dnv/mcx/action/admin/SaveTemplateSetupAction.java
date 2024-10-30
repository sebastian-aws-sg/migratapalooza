package com.dtcc.dnv.mcx.action.admin;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.beans.CategoryBean;
import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.beans.TermBean;
import com.dtcc.dnv.mcx.delegate.admin.SaveTemplateSetupDelegate;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceResponse;
import com.dtcc.dnv.mcx.form.TemplateForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/**
* This class is used as a Action to save the ISDA details 
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
* @date Sep 12, 2007
* @version 1.0
*/

public  class SaveTemplateSetupAction extends MCXBaseAction 
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
		HttpSession session = null;
		AuditInfo ai = null;
		TemplateBean templateBean = null;
		TermBean propTermBean = null;
		TemplateForm templateForm = null;
		TemplateServiceRequest serviceRequest = new TemplateServiceRequest(ai);
		TemplateServiceResponse serviceResponse = new TemplateServiceResponse();
		MessageLogger log = MessageLogger.getMessageLogger(SaveTemplateSetupAction.class.getName());
				
		try {
			session = request.getSession();
			templateForm = (TemplateForm) form;
			templateBean = (TemplateBean)templateForm.getTransaction();
			if(templateBean == null)
				templateBean = new TemplateBean();			
			
			templateBean.setCategBeanList(templateForm.getCategyBeanList());
			if(templateBean !=null &&   templateBean.isPropValInsReq()){
				propTermBean = (TermBean)session.getAttribute(MCXConstants.PROPREITARY_TERM);
				setPropreitaryTermValue(templateBean,propTermBean);
			}
			
			serviceRequest.setTransaction(templateBean);
			serviceRequest.setOpnInd(templateForm.getOpnInd());	
			serviceRequest.setUserId(user.getMCXUserGUID());
			serviceRequest.setCmpnyId(user.getCompanyId());
			
			SaveTemplateSetupDelegate delegate = new SaveTemplateSetupDelegate();
			serviceResponse = (TemplateServiceResponse)delegate.processRequest(serviceRequest);
			if (!serviceResponse.hasError())
			{
				session.removeAttribute(MCXConstants.PROPREITARY_TERM);
			    forward = mapping.findForward(SUCCESS_FORWARD);
			} else
			{
			    
			    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", serviceResponse.getSpReturnMessage()));
			    //forward = mapping.findForward(MCXConstants.ERROR_FORWARD);
			    forward = mapping.findForward(SUCCESS_FORWARD);
			}
//			forward = mapping.findForward(SUCCESS_FORWARD);
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
		return forward;
	}
		
	
	private void  setPropreitaryTermValue(TemplateBean templateBean,TermBean propTermBean){
		List categyBeanList = null;
		List termList = new ArrayList();
		TermBean termBean = null; 
		int categyBeanListSize= 0;
		CategoryBean catgyBean = null;		

		catgyBean = new CategoryBean();
		catgyBean.setCatgyStCd(MCXConstants.PROPREITARY_CATEGORY_STATUS);
		termList.add(propTermBean);
		catgyBean.setTermList(termList);
		
		categyBeanList = templateBean.getCategBeanList();
		categyBeanList.add(catgyBean);	
	}
		
}
