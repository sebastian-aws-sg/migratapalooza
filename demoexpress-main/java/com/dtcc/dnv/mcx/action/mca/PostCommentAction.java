/*
 * Created on Sep 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
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
import com.dtcc.dnv.mcx.beans.TermBean;
import com.dtcc.dnv.mcx.delegate.mca.ModifyTermServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.ModifyTermServiceResponse;
import com.dtcc.dnv.mcx.delegate.mca.PostCommentDelegate;
import com.dtcc.dnv.mcx.form.TermForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
 * @author VVaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PostCommentAction extends MCXBaseAction {

	public ActionForward returnForward(MCXCUOUser user, 
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception
	{
		MessageLogger log = MessageLogger.getMessageLogger(PostCommentAction.class.getName());
		ActionMessages errors = new ActionMessages();
		ActionForward forward = mapping.findForward(MCXConstants.SUCCESS_FORWARD);
		HttpSession session = null;

		TermForm termForm = (TermForm) form;
		try
		{
		    session = request.getSession();
			TermBean termBean = (TermBean) termForm.getTransaction();
			
			//Set the Comment text from Form object into TermBean
			termBean.setCmntTxt(termForm.getCommentTxt());
			//Get the Template Id, Template Type from URL
			termBean.setTmpltId(Integer.parseInt(termForm.getTmpltID()));
			termBean.setTmpltTyp(termForm.getTmpltType());
			if(termForm.getTmpltType().equalsIgnoreCase(MCXConstants.ISDA_TEMPLATE_TYPE) ||
			   termForm.getTmpltType().equalsIgnoreCase(MCXConstants.EXECUTED_TEMPLATE_TYPE) ||
			   termForm.getTmpltType().equalsIgnoreCase(MCXConstants.CP_FINAL_TEMPLATE_TYPE) ){
			    session.setAttribute(MCXConstants.BASE_TMPLT_IS_FI_EX, MCXConstants.CONST_YES);
			}
			
			PostCommentDelegate postCommentDelegate = new PostCommentDelegate();
			ModifyTermServiceRequest serviceRequest = new ModifyTermServiceRequest(user.getAuditInfo());
			
			if(termForm.getTmpltLocked().equalsIgnoreCase(MCXConstants.CONST_YES))
				serviceRequest.setTmpltLocked(true);

			//Set the TermBean in Service Request Transaction
			serviceRequest.setTransaction(termBean);
			//Set the User Id and Company Id in Service Request
			serviceRequest.setUsrId(user.getMCXUserGUID());
			serviceRequest.setCmpnyCd(user.getCompanyId());
			serviceRequest.setActingDealer(termForm.isActingDealer());
			
			ModifyTermServiceResponse serviceResponse = (ModifyTermServiceResponse) 
				postCommentDelegate.processRequest(serviceRequest);
			
			if(serviceResponse.hasError())
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						MCXConstants.GENERAL_BUSINESS_ERROR, serviceResponse.getSpReturnMessage()));

			if(serviceResponse.getTransaction() != null)
			termForm.setTransaction(serviceResponse.getTransaction());
			request.setAttribute(MCXConstants.TERMFORM, termForm);
		}
		catch (BusinessException e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));
            return mapping.findForward(MCXConstants.ERROR_POPUP_FORWARD);
        } catch (Exception e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));
            return mapping.findForward(MCXConstants.ERROR_POPUP_FORWARD);
        }	
        finally
		{
			if (!errors.isEmpty()){
				saveErrors(request, errors);
				forward = mapping.findForward(MCXConstants.FAILURE_FORWARD);
			}
        }	
		
        return forward;
	}

}
