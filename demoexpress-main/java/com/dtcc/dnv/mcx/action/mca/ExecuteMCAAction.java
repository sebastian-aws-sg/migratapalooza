/*
 * Created on Oct 19, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
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
import com.dtcc.dnv.mcx.delegate.mca.ExecuteMCADelegate;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceResponse;
import com.dtcc.dnv.mcx.form.TemplateForm;
import com.dtcc.dnv.mcx.formatters.FormatterUtils;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.ApplicationContextHandler;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
 * @author VVaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExecuteMCAAction extends MCXBaseAction {
	
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.mcx.action.MCXBaseAction#returnForward(com.dtcc.dnv.mcx.user.MCXCUOUser, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		MessageLogger log = MessageLogger.getMessageLogger(ExecuteMCAAction.class.getName());
		
		TemplateForm templateForm = (TemplateForm) form;

		ActionMessages errors = new ActionMessages();
		ActionForward forward = mapping.findForward(MCXConstants.SUCCESS_FORWARD);
		
		//To Show execute pop-up 
		if(templateForm.getOpnInd().equalsIgnoreCase(MCXConstants.EXECUTED_TEMPLATE_TYPE))
		{
			templateForm.setPublishDt("");			
			return mapping.findForward(MCXConstants.EXECUTE_POPUP);
		}

		try 
		{
			TemplateServiceRequest serviceRequest = new TemplateServiceRequest(user.getAuditInfo());
			boolean clearPendingMCA = false;
			TemplateBean tmpltBean = (TemplateBean)templateForm.getTransaction();
			
			if(templateForm.getTmpltId() != "")
				tmpltBean.setTmpltId(Integer.parseInt(templateForm.getTmpltId()));
			
			//Format the Execution Date in the format DB expects
			if(!templateForm.getPublishDt().equalsIgnoreCase(""))
			{
				String execDt = FormatterUtils.formatOutputDate(templateForm.getPublishDt(), 
						"mm/dd/yyyy", "yyyy-mm-dd");
				tmpltBean.setPublishDt(execDt + MCXConstants.DEFAULT_TIMESTAMP);
				clearPendingMCA = true;
			}

			//Set the TemplateBean in Service Request Transaction
			serviceRequest.setTransaction(tmpltBean);	
			
			//Set the User Id and Company Id in Service Request
			serviceRequest.setUserId(user.getMCXUserGUID());
			serviceRequest.setCmpnyId(user.getCompanyId());
						
			ExecuteMCADelegate delegate = new ExecuteMCADelegate();
			TemplateServiceResponse serviceResponse = (TemplateServiceResponse)delegate.processRequest(serviceRequest);
			
			if(serviceResponse.hasError())
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						MCXConstants.GENERAL_BUSINESS_ERROR, serviceResponse.getSpReturnMessage()));
			
			//Clear Pending MCA Menu data as Pending MCA's will be updated for that Counterparty
			if(clearPendingMCA)
			{
				ApplicationContextHandler appCont = ApplicationContextHandler.getInstance();
				appCont.loadUpdatePendingMCAs(user.getCompanyId());
				appCont.loadUpdatePendingMCAs(tmpltBean.getOrgCltCd());
			}
			
			request.setAttribute("EXECUTE_MCA_SUCCESS", templateForm);
		} 
		catch (BusinessException e)
		{
	        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));
	        forward = mapping.findForward(MCXConstants.ERROR_FORWARD);
	    }
		catch (Exception e)
	    {
	        log.error(e);
	        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));
	        forward = mapping.findForward(MCXConstants.ERROR_FORWARD);
	    }
		finally
		{
			if (!errors.isEmpty())
				saveErrors(request, errors);
		}
		return forward;
	}

}
