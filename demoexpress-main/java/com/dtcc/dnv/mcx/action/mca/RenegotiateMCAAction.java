/*
 * Created on Oct 24, 2007
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
import com.dtcc.dnv.mcx.delegate.mca.RenegotiateMCADelegate;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceResponse;
import com.dtcc.dnv.mcx.form.TemplateForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.ApplicationContextHandler;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
 * This Action is called when the dealer clicks on the Re-Negotiate button to renegotiate the Executed MCA
 * @author vvaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RenegotiateMCAAction extends MCXBaseAction {

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.mcx.action.MCXBaseAction#returnForward(com.dtcc.dnv.mcx.user.MCXCUOUser, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		MessageLogger log = MessageLogger.getMessageLogger(RenegotiateMCAAction.class.getName());
        ActionMessages errors = new ActionMessages();
        ActionForward forward = mapping.findForward(MCXConstants.SUCCESS_FORWARD);
        TemplateForm templateForm = (TemplateForm) form;
        
        try
		{
        	TemplateServiceRequest serviceRequest = new TemplateServiceRequest(user.getAuditInfo());
        	TemplateBean tmpltBean = (TemplateBean) templateForm.getTransaction();
        	
			//Set the TemplateBean in Service Request Transaction
        	serviceRequest.setTransaction(templateForm.getTransaction());
        	
			//Set the User Id and Company Id in Service Request
        	serviceRequest.setUserId(user.getMCXUserGUID());
        	serviceRequest.setCmpnyId(user.getCompanyId());
        	
            RenegotiateMCADelegate delegate = new RenegotiateMCADelegate();
        	TemplateServiceResponse serviceResponse = (TemplateServiceResponse) delegate.processRequest(serviceRequest);
        	
        	if(serviceResponse.hasError())
        		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
        				MCXConstants.GENERAL_BUSINESS_ERROR, serviceResponse.getSpReturnMessage()));
        	if(serviceResponse.getTransaction() != null)
        	{
        		TemplateBean outTmplt = (TemplateBean) serviceResponse.getTransaction();
        		String tmpltID = Integer.toString(outTmplt.getTmpltId());
        		templateForm.setTmpltId(tmpltID);
        		request.setAttribute("tmpltId", tmpltID);
        	}
        	//Update Pending MCA Menu
			ApplicationContextHandler appCont = ApplicationContextHandler.getInstance();
			appCont.loadUpdatePendingMCAs(user.getCompanyId());
			appCont.loadUpdatePendingMCAs(tmpltBean.getOrgCltCd());
		}
		catch (BusinessException e)
        {            
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));
            return mapping.findForward(MCXConstants.ERROR_FORWARD);
        } catch (Exception e)
        {         
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));
            return mapping.findForward(MCXConstants.ERROR_FORWARD);
        }
        finally 
		{
			if (!errors.isEmpty())
				saveErrors(request, errors);
		}

    	return forward;
	}

}
