/*
 * Created on Oct 23, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.action.homepage;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.delegate.home.UserDetailsDelegate;
import com.dtcc.dnv.mcx.delegate.home.UserDetailsServiceRequest;
import com.dtcc.dnv.mcx.delegate.home.UserDetailsServiceResponse;
import com.dtcc.dnv.mcx.form.UserDetailsForm;
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
 * @author Nithya R
 * @date Oct 23, 2007
 * @version 1.0
 * 
 * Action class for displaying the user details.
 */

public class UserDetailsDisplayAction extends MCXBaseAction
{

    private final static MessageLogger log = MessageLogger.getMessageLogger(UserDetailsDisplayAction.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.mcx.action.MCXBaseAction#returnForward(com.dtcc.dnv.mcx.user.MCXCUOUser,
     *      org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse) This execute method will
     *      retrive the firm details from the delegate.
     */
    public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        final String SUCCESS_FORWARD = "success";        
        boolean errorpage = true;
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();
        UserDetailsForm userDetailsForm = (UserDetailsForm) form;
        UserDetailsServiceRequest serviceRequest = new UserDetailsServiceRequest(user.getAuditInfo());
        UserDetailsServiceResponse serviceResponse = new UserDetailsServiceResponse();
        UserDetailsDelegate delegate = new UserDetailsDelegate();
        try
        {
            String userId = userDetailsForm.getUserId();
            /**
             * Input details are set in service request to be used by the
             * delegate.
             */
            serviceRequest.setUserId(userId);

            //calling the delegate's processRequest method

            serviceResponse = (UserDetailsServiceResponse) delegate.processRequest(serviceRequest);

            //details fetched from the delegate are set in the form.
            userDetailsForm.setUserDetails(serviceResponse.getUserDetails());
            if (serviceResponse.hasError())
            {
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, serviceResponse.getSpReturnMessage()));

            }
            if (serviceResponse.getUserDetailsStatus() != null && MCXConstants.SUCCESS_FORWARD.equals(serviceResponse.getUserDetailsStatus()))
                errorpage = false;

        } catch (BusinessException be)
        {
            log.error(be);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));          

        } catch (Exception e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));            
        }
        if (errorpage)
        {

            if (!errors.isEmpty())
            {
                saveMessages(request, errors);
            }
            forward = mapping.findForward(MCXConstants.ERROR_FORWARD);
        } else
        {
            forward = mapping.findForward(SUCCESS_FORWARD);
        }
        return forward;

    }
}
