package com.dtcc.dnv.mcx.action.alert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.beans.AlertInfo;
import com.dtcc.dnv.mcx.delegate.alert.AlertServiceRequest;
import com.dtcc.dnv.mcx.delegate.alert.AlertServiceResponse;
import com.dtcc.dnv.mcx.delegate.alert.ViewAlertDetailDelegate;
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
 * This class is used to retrieve the alert details.
 *  
 */
public class ViewAlertDetailAction  extends MCXBaseAction 
{

	private final static MessageLogger log = MessageLogger.getMessageLogger(ViewAlertDetailAction.class.getName());
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.mcx.action.MCXBaseAction#returnForward(com.dtcc.dnv.mcx.user.MCXCUOUser, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        log.debug("inside admin view alert detail action");
        boolean errorpage=true;
        AlertServiceRequest serviceRequest = new AlertServiceRequest(user.getAuditInfo());
        AlertServiceResponse serviceResponse = new AlertServiceResponse();
        ActionMessages errors = new ActionMessages();
        try
        {
            
            //Input details are set in service request to be used by the
            // delegate.
        	Object oinfo = request.getParameter("alertid");
        	String alertid = null;
        	if(oinfo!=null)
        	{
        		alertid = (String)oinfo;
        	}
        	if(alertid==null || alertid.trim().length()==0)
        	{
        		throw new Exception("no alert id");
        	}
        //	log.debug("alert id is " + alertid);
            
        	AlertInfo thealertinfo = new AlertInfo();
        	thealertinfo.setAlertid(alertid);
        	//thealertinfo.setAlertid("19999");
        	
        	serviceRequest.setTransaction(thealertinfo);
            ViewAlertDetailDelegate delegate = new ViewAlertDetailDelegate();
            
            //calling the delegate's processRequest method.
            serviceResponse = (AlertServiceResponse) delegate.processRequest(serviceRequest);
            log.debug("get service response " + serviceResponse);
            if(serviceResponse.hasError())
            {
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("viewalert", serviceResponse.getSpReturnMessage()));
           	
            }
            
            if(serviceResponse.getAlertStatus()!=null && serviceResponse.getAlertStatus().equals("success"))
            	errorpage = false;
            
            AlertInfo theresinfo = (AlertInfo)serviceResponse.getTransaction();
            String msg = theresinfo.getMessage();
            msg = msg.replaceAll("\r\n",
                    "<br/>");
            msg = msg.replaceAll(" ","&nbsp;");
            log.debug("message " + msg);
            theresinfo.setMessage(msg);
            
            
        } catch (BusinessException be)
        {
            log.error("business exception in view alert detail action class " + be);
            log.error(be);
        } catch (Exception e)
        {
            log.error("general exception in view alert detail action class : " + e);
            log.error(e);
        }
        
        if(errorpage)
        {
            if (!errors.isEmpty())
            {
                saveMessages(request, errors);
            }
        	return mapping.findForward(MCXConstants.ERROR_FORWARD);
        }
        else
        {
        	request.setAttribute("alertinfo",(AlertInfo)serviceResponse.getTransaction());
        	return mapping.findForward("showalertdetail");
        	
        }
    }
}