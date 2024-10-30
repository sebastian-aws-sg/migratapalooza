package com.dtcc.dnv.mcx.action.enroll;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.delegate.enroll.FirmDetailsDelegate;
import com.dtcc.dnv.mcx.delegate.enroll.FirmDetailsServiceRequest;
import com.dtcc.dnv.mcx.delegate.enroll.FirmDetailsServiceResponse;
import com.dtcc.dnv.mcx.form.FirmDetailsForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
 * This class is used to retrive the firm details when a user clicks on the CP name link.
 * 
 * RTM Reference : 3.3.3.26 The Relationship Management and the User management
 * group should provide the user email IDs and the key contact email IDs of the
 * dealer and the client firm
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
 * @author Nithya R
 * @date Sep 4, 2007
 * @version 1.0
 * 
 */
public class FirmDetailsDisplayAction extends MCXBaseAction
{
   
    private final static MessageLogger log = MessageLogger.getMessageLogger(FirmDetailsDisplayAction.class.getName());
   

    /*
     * The returnForward method of this action handles displaying firm details.
     * @see com.dtcc.dnv.mcx.action.MCXBaseAction#returnForward(com.dtcc.dnv.mcx.user.MCXCUOUser, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        final String SUCCESS_FORWARD = "success";
        final String FAILURE_FORWARD = "failure";
        final String DEALER = "Dealer";
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();
        FirmDetailsForm firmDetailsForm = (FirmDetailsForm) form;
        FirmDetailsServiceRequest serviceRequest = new FirmDetailsServiceRequest(user.getAuditInfo());
        FirmDetailsServiceResponse serviceResponse = new FirmDetailsServiceResponse();
        FirmDetailsDelegate delegate = new FirmDetailsDelegate();
        try
        {
            log.info("entering the firm details action class");            
            String dealerId = firmDetailsForm.getDealer();            
            /**
             * Input details are set in service request to be used by the
             * delegate.
             */
            serviceRequest.setId(dealerId);

            //calling the delegate's processRequest method
            serviceResponse = (FirmDetailsServiceResponse) delegate.processRequest(serviceRequest);

            //details fetched from the delegate are set in the form.
            firmDetailsForm.setFirmDetails(serviceResponse.getFirmDetails());
            firmDetailsForm.setDealerName(serviceResponse.getDealerName());
            log.info("back to action class");
            forward = mapping.findForward(SUCCESS_FORWARD);
        } catch (BusinessException be)
        {
            log.error(be);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));
            forward = mapping.findForward(FAILURE_FORWARD);

        } catch (Exception e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));
            forward = mapping.findForward(FAILURE_FORWARD);
        }
        return forward;

    }
}
