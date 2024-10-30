/*
 * Created on Sep 12, 2007
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
import com.dtcc.dnv.mcx.beans.TermBean;
import com.dtcc.dnv.mcx.delegate.mca.ModifyTermServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.ModifyTermServiceResponse;
import com.dtcc.dnv.mcx.delegate.mca.ViewAmendmentDelegate;
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
public class ViewAmendmentAction extends MCXBaseAction {

	public ActionForward returnForward(MCXCUOUser user, 
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception
	{
		MessageLogger log = MessageLogger.getMessageLogger(ViewAmendmentAction.class.getName());
		ActionMessages errors = new ActionMessages();

		TermForm termForm = (TermForm) form;
		
		try
		{
			ModifyTermServiceRequest serviceRequest = new ModifyTermServiceRequest(user.getAuditInfo());
			
			TermBean termBean = new TermBean();

			//Get the Template Id, Template Type and Amendment Id from URL
			termBean.setTmpltTyp(termForm.getTmpltType());
			termBean.setTmpltId(Integer.parseInt(termForm.getTmpltID()));
			termBean.setTermValId(Integer.parseInt(termForm.getTermValId()));
			
			serviceRequest.setUserInd(termForm.getLockUsrInd());		
			//Set the TermBean in Service Request Transaction
			serviceRequest.setTransaction(termBean);
			//Set the User Id and Company Id in Service Request
			serviceRequest.setUsrId(user.getMCXUserGUID());
			serviceRequest.setCmpnyCd(user.getCompanyId());
			
			ViewAmendmentDelegate viewAmendmentDelegate = new ViewAmendmentDelegate();
			ModifyTermServiceResponse serviceResponse = (ModifyTermServiceResponse) 
				viewAmendmentDelegate.processRequest(serviceRequest);
			
			TermBean trmBean = (TermBean) serviceResponse.getTransaction();
			
			//For Simultaneous Post Amendment Issue, if the Template Type is changed 
			//because of any execution or Submission, we will not allow to post amendment
			if(!termForm.getTmpltType().equalsIgnoreCase(trmBean.getTmpltTyp()))
			{
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						MCXConstants.GENERAL_BUSINESS_ERROR, "Currently the Template is being worked by another user."));
			}
			
			termForm.setTransaction(serviceResponse.getTransaction());
			
		}
		catch (BusinessException e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
            		MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));
        } catch (Exception e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
            		MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));
        }	
		finally
		{
			if (!errors.isEmpty())
				saveErrors(request, errors);
        }	
		
		return mapping.findForward(MCXConstants.SUCCESS_FORWARD);
	}
	
}
