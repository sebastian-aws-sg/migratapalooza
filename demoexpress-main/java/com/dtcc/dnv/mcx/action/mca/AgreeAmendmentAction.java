/*
 * Created on Sep 20, 2007
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
import com.dtcc.dnv.mcx.delegate.mca.AgreeAmendmentDelegate;
import com.dtcc.dnv.mcx.delegate.mca.ModifyTermServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.ModifyTermServiceResponse;
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
public class AgreeAmendmentAction extends MCXBaseAction {

	public ActionForward returnForward(MCXCUOUser user, 
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception
	{
		MessageLogger log = MessageLogger.getMessageLogger(AgreeAmendmentAction.class.getName());
        ActionMessages errors = new ActionMessages();

        TermForm termForm = (TermForm) form;
        ModifyTermServiceResponse serviceResponse = null;
        
		try
		{
			TermBean termBean = new TermBean();
			
			//Get the Template Id, Template Type, Amendment Id from URL
			termBean.setTmpltId(Integer.parseInt(termForm.getTmpltID()));
			termBean.setTmpltTyp(termForm.getTmpltType());
			termBean.setTermValId(Integer.parseInt(termForm.getTermValId()));
			
			//Set the Amendment Status Code to 'A'
			termBean.setAmndtStCd(MCXConstants.AGREE_AMENDMENT_STATUS_CD);

			ModifyTermServiceRequest serviceRequest = new ModifyTermServiceRequest(user.getAuditInfo());
				
			//Set the TermBean in Service Request Transaction
			serviceRequest.setTransaction(termBean);	
			//Set the User Id and Company Id in Service Request
			serviceRequest.setUsrId(user.getMCXUserGUID());
			serviceRequest.setCmpnyCd(user.getCompanyId());
			serviceRequest.setActingDealer(termForm.isActingDealer());
			
			if(termForm.getTmpltLocked().equalsIgnoreCase(MCXConstants.CONST_YES))
			{
				serviceRequest.setTmpltLocked(true);
				serviceRequest.setUserInd(MCXConstants.LOCKED_INDICATOR);
			}

			AgreeAmendmentDelegate agreeAmendmentDelegate = new AgreeAmendmentDelegate();
			serviceResponse = (ModifyTermServiceResponse)agreeAmendmentDelegate.processRequest(serviceRequest);
			
			if(serviceResponse.hasError())
			{
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                		MCXConstants.GENERAL_BUSINESS_ERROR, serviceResponse.getSpReturnMessage()));
			}
			
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
			if (!errors.isEmpty())
				saveErrors(request, errors);
        }	
		return mapping.findForward(MCXConstants.SUCCESS_FORWARD);
	}
}
