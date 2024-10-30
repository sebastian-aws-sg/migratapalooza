package com.dtcc.dnv.mcx.action.admin;


import java.io.Serializable;
import java.sql.Timestamp;
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
import com.dtcc.dnv.mcx.delegate.admin.ViewAdminMCASetupDelegate;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceResponse;
import com.dtcc.dnv.mcx.form.TemplateForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.ApplicationContextHandler;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

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
* @author Elango TR
* @date Sep 12, 2007
* @version 1.0
*/

public  class ViewAdminMCASetupAction extends MCXBaseAction 
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
		HttpSession session = null;
		//default forward value to failure
		final String SUCCESS_FORWARD = "success";
		final String FAILURE_FORWARD = "failure";
		// Create user
		TemplateBean templateBean = null;
		AuditInfo ai = null;
		TemplateServiceRequest serviceRequest = new TemplateServiceRequest(ai);
		TemplateServiceResponse serviceResponse = new TemplateServiceResponse();
		TemplateForm templateForm = null;
		TemplateBean tmpltBean = null;
		MessageLogger log = MessageLogger.getMessageLogger(ViewAdminMCASetupAction.class.getName());
		TermBean propTerm = null;
		
		try {
			session = request.getSession();
			templateForm = (TemplateForm) form;

			//Check if this piece of code can be removed
			String tmpltId =templateForm.getTmpltId();
			String sltInd = templateForm.getSltInd();
			templateBean = (TemplateBean)templateForm.getTransaction();
			if(templateBean == null)
				templateBean = new TemplateBean();
			
			if(tmpltId !=null || tmpltId != "")
			templateBean.setTmpltId(Integer.parseInt(tmpltId));

			serviceRequest.setCmpnyId(user.getCompanyId()); // Set the details from MCXCUOUser Object
			serviceRequest.setCatgyId(templateForm.getCatgyId());
			serviceRequest.setTransaction(templateBean);
			serviceRequest.setViewInd(MCXConstants.VIEW_INDICATOR_COMPLETE);
			serviceRequest.setFunctInd(MCXConstants.FUNCTION_INDICATOR_ADMIN);
			serviceRequest.setUserId(user.getMCXUserGUID());
			//log.debug(" B4 call delegate in  ViewAdminMCASetupAction ");
			ViewAdminMCASetupDelegate delegate = new ViewAdminMCASetupDelegate();
			serviceResponse = (TemplateServiceResponse)delegate.processRequest(serviceRequest);
			if (!serviceResponse.hasError())
			{
				TemplateBean outTmplt = (TemplateBean) serviceResponse.getTransaction();
				//log.debug(" after call delegate in ViewAdminMCASetupAction ");
				templateForm.setCategyBeanList(serviceResponse.getCategyBeanList());
				templateForm.setCategyList(serviceResponse.getCategyList());
				templateForm.setTransaction(outTmplt);
				
				//Set the Enable and Disable of buttons
				if(outTmplt.getLockSt().equalsIgnoreCase("Y") 
						&& outTmplt.getLockUsrInd().equalsIgnoreCase("L") 
						&& outTmplt.isTempValAvl() 
						&& (outTmplt.getMcaStatusCd().equalsIgnoreCase("I") || outTmplt.getMcaStatusCd().equalsIgnoreCase("N")))
					templateForm.setEnableSave(true);
				else
					templateForm.setEnableSave(false);
				
				if(outTmplt.getMcaStatusCd().equalsIgnoreCase("W") && outTmplt.isEligibleForAppr() && outTmplt.isApprEnabled())
					templateForm.setEnableApprRej(true);
				else
					templateForm.setEnableApprRej(false);
				
				if(outTmplt.getMcaStatusCd().equalsIgnoreCase("I") && !outTmplt.getLockSt().equalsIgnoreCase("Y") && outTmplt.isEligibleForAppr())
					templateForm.setEnablePublish(true);
				else
					templateForm.setEnablePublish(false);
				
				propTerm = setPropreitaryTermValue(serviceResponse);
				if(propTerm !=null ){
					session.setAttribute(MCXConstants.PROPREITARY_TERM,propTerm);
				}
				
				//forward = mapping.findForward(SUCCESS_FORWARD);
			} else
		   {
		      errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", serviceResponse.getSpReturnMessage()));
		   }
			
			if(sltInd.equalsIgnoreCase("PT")){
				

			forward = mapping.findForward("printview");
		}else{
		    forward = mapping.findForward(SUCCESS_FORWARD);
		}

				ApplicationContextHandler appContHandler = ApplicationContextHandler.getInstance();
				//Fortify forces objects stored in session to be serializable. Hence ArrayList typecast to Serializable
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
	
	private TermBean  setPropreitaryTermValue(TemplateServiceResponse serviceResponse){
	    
		List categyBeanList = null;
		TermBean termBean = null; 
		int categyBeanListSize= 0;
		CategoryBean catgyBean = null;
		TemplateBean templateBean = null;	
		
		categyBeanList = serviceResponse.getCategyBeanList();
		templateBean = (TemplateBean)serviceResponse.getTransaction();
		if(templateBean !=null &&  ! templateBean.isPropValInsReq()){
			return termBean;
		}
		if(categyBeanList != null) {
			categyBeanListSize = categyBeanList.size();
			for(int index=0; index < categyBeanListSize ; index++){
				catgyBean = (CategoryBean) categyBeanList.get(index);
				if(catgyBean.getCatgyStCd().equalsIgnoreCase(MCXConstants.PROPREITARY_CATEGORY_STATUS)){
					if(catgyBean.getTermList() != null ){
						termBean = (TermBean)catgyBean.getTermList().get(0);
					}
					break;
				}					
			}
		}		
		return termBean;
	}
	
	
}
