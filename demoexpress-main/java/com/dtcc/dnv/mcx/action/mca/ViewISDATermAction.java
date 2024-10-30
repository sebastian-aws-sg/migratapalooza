/*
 * Created on Sep 14, 2007
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
import com.dtcc.dnv.mcx.delegate.mca.ViewISDATermDelegate;
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
public class ViewISDATermAction extends MCXBaseAction {

	public ActionForward returnForward(MCXCUOUser user, 
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception
	{
		MessageLogger log = MessageLogger.getMessageLogger(ViewISDATermAction.class.getName());
		ActionMessages errors = new ActionMessages();

		TermForm termForm = (TermForm) form;
				
		try
		{
			ModifyTermServiceRequest serviceRequest = new ModifyTermServiceRequest(user.getAuditInfo());
			
			TermBean termBean = new TermBean();
		
			termBean.setTermValId(Integer.parseInt(termForm.getTermValId()));
			
			serviceRequest.setUserInd(termForm.getLockUsrInd());
			serviceRequest.setTransaction(termBean);
			serviceRequest.setISDA(true);
			
			ViewISDATermDelegate viewISDATermDelegate = new ViewISDATermDelegate();
			ModifyTermServiceResponse serviceResponse = (ModifyTermServiceResponse) 
				viewISDATermDelegate.processRequest(serviceRequest);
			
			if(serviceResponse.hasError())
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						MCXConstants.GENERAL_BUSINESS_ERROR, serviceResponse.getSpReturnMessage()));
			
			termForm.setTransaction(serviceResponse.getTransaction());
		}
		catch (BusinessException e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
            		MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));
            return mapping.findForward(MCXConstants.ERROR_POPUP_FORWARD);
        } catch (Exception e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
            		MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));
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
