/*
 * Created on Sep 20, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.action.mca;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
import com.dtcc.dnv.mcx.delegate.mca.CounterpartyServiceResponse;
import com.dtcc.dnv.mcx.delegate.mca.EnrolledCounterpartyListDelegate;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceRequest;
import com.dtcc.dnv.mcx.form.TemplateForm;
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
public class EnrolledCounterpartyListAction extends MCXBaseAction {

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.mcx.action.DtccBaseAction#returnForward(com.dtcc.sharedservices.security.common.CUOUser, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		MessageLogger log = MessageLogger.getMessageLogger(EnrolledCounterpartyListAction.class.getName());
		ActionMessages errors = new ActionMessages();
		HashMap enrollCPSes = null;
		HashMap enrollCPDb = null;
		String enrollCPId = null;
		try
		{
		HttpSession session = request.getSession();
			TemplateForm templateForm = (TemplateForm) form;			
		String tmpltId = request.getParameter("tmpltID");
		int tmpltID = Integer.parseInt(tmpltId);
		TemplateBean templtBean = new TemplateBean();
		templtBean = (TemplateBean) templateForm.getTransaction();
		templtBean.setTmpltId(tmpltID);
			TemplateServiceRequest serviceRequest = new TemplateServiceRequest(
					user.getAuditInfo());
		
			//Set the TemplateBean in Service Request Transaction
		serviceRequest.setTransaction(templateForm.getTransaction());	
			//Set the User Id and Company Id in Service Request
		serviceRequest.setUserId(user.getMCXUserGUID());
		serviceRequest.setCmpnyId(user.getCompanyId());
		
		EnrolledCounterpartyListDelegate enrollCPListDelegate = new EnrolledCounterpartyListDelegate();
			CounterpartyServiceResponse serviceResponse = (CounterpartyServiceResponse) enrollCPListDelegate
					.processRequest(serviceRequest);
			enrollCPDb = (HashMap)serviceResponse.getEnrollCpMap();
		
			if(serviceResponse.hasError())
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						MCXConstants.GENERAL_BUSINESS_ERROR, serviceResponse
								.getSpReturnMessage()));
			
			if (session.getAttribute(MCXConstants.SLT_IND_ENR_APP) != null
					&& session.getAttribute(MCXConstants.ENROLLEDCPs) != null) {
				enrollCPSes = (HashMap) session
						.getAttribute(MCXConstants.ENROLLEDCPs);

				Iterator iterator = enrollCPSes.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry entry = (Map.Entry) iterator.next();
					enrollCPId = (String) entry.getKey();
					break;
				}
				if(enrollCPDb !=null ){
					if(! enrollCPDb.containsKey(enrollCPId)){
						request.setAttribute("ENROLL_CP", "VALUE");
					}
				}
			}
		
			//Set the Enrolled Clients List in Session
			if (session.getAttribute(MCXConstants.ENROLLEDCPs) == null)
				request.setAttribute(MCXConstants.ENROLLEDCPs, serviceResponse
						.getEnrollCpMap());
		}
		catch (BusinessException e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));
            return mapping.findForward(MCXConstants.ERROR_FORWARD);
        } catch (Exception e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));
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
