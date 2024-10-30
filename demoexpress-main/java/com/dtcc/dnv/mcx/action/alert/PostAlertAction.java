package com.dtcc.dnv.mcx.action.alert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.delegate.alert.AlertServiceRequest;
import com.dtcc.dnv.mcx.delegate.alert.AlertServiceResponse;
import com.dtcc.dnv.mcx.delegate.alert.PostAlertDelegate;
import com.dtcc.dnv.mcx.form.AlertForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
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
 * @author Peng Zhou
 * @date Sep 20, 2007
 * @version 1.0
 * 
 * This class is used to post alert.
 *  
 */
public class PostAlertAction  extends MCXBaseAction 
{

	private final static MessageLogger log = MessageLogger.getMessageLogger(PostAlertAction.class.getName());
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.mcx.action.MCXBaseAction#returnForward(com.dtcc.dnv.mcx.user.MCXCUOUser, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        log.debug("inside post alert action");
        if(request.getMethod().equalsIgnoreCase("get"))
        {
			return mapping.findForward("topostalert");
        	
        }
        AlertForm  theform= (AlertForm) form;

        if(theform.getPostaction()!=null && theform.getPostaction().trim().equalsIgnoreCase("cancel"))
        {
        	return mapping.findForward("postalertsuccess");        	
        }
        

        boolean errorpage = true;
        AlertServiceRequest serviceRequest = new AlertServiceRequest(user.getAuditInfo());
        AlertServiceResponse serviceResponse = new AlertServiceResponse();
        try
        {

            //Input details are set in service request to be used by the
            // delegate.
        	serviceRequest.setUser(user);
            log.debug("set user.getUserId( "+user.getMCXUserGUID());
            serviceRequest.setTransaction(theform.getTransaction());
            PostAlertDelegate delegate = new PostAlertDelegate();
            
            //calling the delegate's processRequest method.
            serviceResponse = (AlertServiceResponse) delegate.processRequest(serviceRequest);
            log.debug("get service response " + serviceResponse);
            if(serviceResponse.getAlertStatus()!=null && serviceResponse.getAlertStatus().equals("success"))
            	errorpage = false;

            log.debug("back to action class");
        } catch (BusinessException be)
        {
            log.error("business exception in post alert action class " + be);
            log.error(be);
        } catch (Exception e)
        {
            log.error("general exception in post alert action class : " + e);
            log.error(e);
        }
        
        if(errorpage)
        {
        	return mapping.findForward(MCXConstants.ERROR_FORWARD);
        }
        else
        {
        	return mapping.findForward("postalertsuccess");
        	
        }
    }
}