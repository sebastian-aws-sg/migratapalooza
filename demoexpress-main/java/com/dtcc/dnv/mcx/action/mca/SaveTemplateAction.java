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
import com.dtcc.dnv.mcx.delegate.mca.SaveTemplateDelegate;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceResponse;
import com.dtcc.dnv.mcx.form.TemplateForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
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

public class SaveTemplateAction extends MCXBaseAction {

	public ActionForward returnForward(MCXCUOUser user, 
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception
	{
		MessageLogger log = MessageLogger.getMessageLogger(SaveTemplateAction.class.getName());
		
		TemplateForm templateForm = (TemplateForm) form;

		ActionMessages errors = new ActionMessages();
		ActionForward forward = mapping.findForward(MCXConstants.SUCCESS_FORWARD);
		HttpSession session = null;

		try 
		{
		    session = request.getSession();
			TemplateServiceRequest serviceRequest = new TemplateServiceRequest(user.getAuditInfo());
			TemplateBean tempBean = (TemplateBean)templateForm.getTransaction();
			
			//If the Template Id is present in Form Object, get it from there
			if(!templateForm.getTmpltId().equalsIgnoreCase(""))
				tempBean.setTmpltId(Integer.parseInt(templateForm.getTmpltId()));
			
			//Set the TemplateBean in Service Request Transaction
			serviceRequest.setTransaction(tempBean);		
			//The Operation Indicator can be 'C' or 'S' or 'U' or 'D'
			serviceRequest.setOpnInd(templateForm.getOpnInd());
			
			//If Unlock being tried for saving as different Template, send Operation as 'C' instead of 'U'
			if(templateForm.getOpnInd().equalsIgnoreCase(MCXConstants.UNLOCK) && 
				(!tempBean.getNxtTmpltNm().equalsIgnoreCase("") 
				&& !tempBean.getTmpltNm().equalsIgnoreCase(tempBean.getISDATmpltNm() + " " + tempBean.getNxtTmpltNm())))
			{
				serviceRequest.setOpnInd(MCXConstants.CHECK_SAVE);
			}
						
			//Set the User Id and Company Id in Service Request
			serviceRequest.setUserId(user.getMCXUserGUID());
			serviceRequest.setCmpnyId(user.getCompanyId());
			
			SaveTemplateDelegate delegate = new SaveTemplateDelegate();
			TemplateServiceResponse serviceResponse = (TemplateServiceResponse)delegate.processRequest(serviceRequest);
			
			if(serviceResponse.hasError())
			{
				//There are different Alert messages to be thrown for different SP return code
				if(serviceResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP02))
			{				
					request.setAttribute("SHOW_ALERT", "LOCKED");
				}
				else if(serviceResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP04))
				{
					request.setAttribute("SHOW_ALERT", "TMPLTEXISTS-OVERWRITE");
				}
				else if(serviceResponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP01))
				{
					request.setAttribute("SHOW_ALERT", "TMPLTEXISTS");
				}
				else
				{
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							MCXConstants.GENERAL_BUSINESS_ERROR, serviceResponse.getSpReturnMessage()));
					forward = mapping.findForward(MCXConstants.ERROR_FORWARD);
				}
				forward = mapping.findForward(MCXConstants.FAILURE_FORWARD);
			}
			TemplateBean outTmplt = (TemplateBean)templateForm.getTransaction();
			
			//If the template is deleted forward to MCA Wizard Step1
			if(templateForm.getOpnInd().equalsIgnoreCase(MCXConstants.DELETE_INDICATOR))
			{
				outTmplt.setDerPrdCd(templateForm.getProdCd());
				outTmplt.setDerSubPrdCd(templateForm.getSubProdCd());
			    if (session.getAttribute(MCXConstants.BASE_TMPLT_IS_FI_EX) != null
						&& ((String) session.getAttribute(MCXConstants.BASE_TMPLT_IS_FI_EX))
								.equalsIgnoreCase(MCXConstants.CONST_YES)) 
			        forward = mapping.findForward("default");
			    else
				forward = mapping.findForward("delete");
			}
			if(! serviceResponse.hasError())
			{
			session.removeAttribute(MCXConstants.BASE_TMPLT_IS_FI_EX);
			}
			//If Save Template called from Negotiation page forward to Negotiation MCA
			String tmpltType = outTmplt.getTmpltTyp();
			if(tmpltType.equalsIgnoreCase(MCXConstants.WORKING_TEMPLATE_TYPE) || 
					tmpltType.equalsIgnoreCase(MCXConstants.REEXECUTED_TEMPLATE_TYPE))
			{
				forward = mapping.findForward("negotiation");
			}			

			//Default the operation indicator as 'C'
			templateForm.setOpnInd(MCXConstants.CHECK_SAVE);
			
			request.setAttribute("SAVE_TEMPLATE_SUCCESS", templateForm);
		}
		catch (BusinessException e)
		{
	        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));
	        forward = mapping.findForward(MCXConstants.ERROR_FORWARD);

	    } catch (Exception e)
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
