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
import com.dtcc.dnv.mcx.beans.Enrollment;
import com.dtcc.dnv.mcx.delegate.enroll.EnrollDealerDelegate;
import com.dtcc.dnv.mcx.delegate.enroll.EnrollDealerServiceRequest;
import com.dtcc.dnv.mcx.delegate.enroll.EnrollDealerServiceResponse;
import com.dtcc.dnv.mcx.form.EnrollmentForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
 * This class is used as a action to enroll the dealer with couterparty
 * 
 * RTM Reference : 3.3.3.1 To create an enrolment request, actor is directed to the enrolment list of dealers, selection of MCAs throuhg enrolment grid and the enrolment confirmation screen
 * RTM Reference : 3.3.3.2 Actor chooses to select a another MCA for enrolment after the previous MCA is approved by dealer
 *
 * Copyright � 2003 The Depository Trust & Clearing Company. All rights
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
 * @author Prabhu K
 * @date Sep 3, 2007
 * @version 1.0
 * 
 *  
 */
public class EnrollDealerAction extends MCXBaseAction
{
    /*
     * The returnForward method of this class handles enrolling with dealer.
     * @see com.dtcc.dnv.mcx.action.MCXBaseAction#returnForward(com.dtcc.dnv.mcx.user.MCXCUOUser, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
	public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        final String SUCCESS_FORWARD = "success";
        final String FAILURE_FORWARD = "failure";
        final String ENROLL_FAILURE_FORWARD = "enrollFailure";
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();
    	MessageLogger log = MessageLogger.getMessageLogger(EnrollDealerAction.class.getName());

        try
        {
            EnrollmentForm enrollmentForm = (EnrollmentForm) form;
            EnrollDealerServiceRequest serviceRequest = new EnrollDealerServiceRequest(user.getAuditInfo());
            Enrollment enrollment = (Enrollment) enrollmentForm.getTransaction();
            
            //set company ID , user ID and transaction from enrollment form is set to service request
            serviceRequest.setCompanyID(user.getCompanyId());
            serviceRequest.setUserID(user.getMCXUserGUID());
            serviceRequest.setEnrollmentBean(enrollment);
            
            EnrollDealerServiceResponse serviceResponse = new EnrollDealerServiceResponse();
            EnrollDealerDelegate delegate = new EnrollDealerDelegate();
            serviceResponse = (EnrollDealerServiceResponse) delegate.processRequest(serviceRequest);

            if(!serviceResponse.hasError())
            {
                forward = mapping.findForward(SUCCESS_FORWARD);
            }
            else
            {
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, serviceResponse.getSpReturnMessage()));
                saveMessages(request, errors);
                forward = mapping.findForward(ENROLL_FAILURE_FORWARD);
                return forward;
            }

        } catch (BusinessException e)
        {
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));
        } catch (Exception e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));
        }
        
        if (!errors.isEmpty())
        {
            saveMessages(request, errors);
            forward = mapping.findForward(FAILURE_FORWARD);
        }


        return forward;
    }

}
