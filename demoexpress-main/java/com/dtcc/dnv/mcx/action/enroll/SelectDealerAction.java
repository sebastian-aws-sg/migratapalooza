package com.dtcc.dnv.mcx.action.enroll;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.beans.DealerList;
import com.dtcc.dnv.mcx.beans.Enrollment;
import com.dtcc.dnv.mcx.company.CompanyUtil;
import com.dtcc.dnv.mcx.form.EnrollmentForm;
import com.dtcc.dnv.mcx.formatters.FormatterDelegate;
import com.dtcc.dnv.mcx.formatters.IFormatter;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;



/**
 * This class is used as a action to get the selected List of Dealer details
 * 
 * RTM Reference : 3.3.3.1 To create an enrolment request, actor is directed to the enrolment list of dealers, selection of MCAs throuhg enrolment grid and the enrolment confirmation screen
 * RTM Reference : 3.3.3.2 Actor chooses to select a another MCA for enrolment after the previous MCA is approved by dealer
 * RTM Reference : 3.3.3.17 A Client should be able to request firm Enrolment from one or more Dealers at a time
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
 * @author Prabhu K
 * @date Sep 3, 2007
 * @version 1.0
 * 
 * 
 */
public class SelectDealerAction extends MCXBaseAction
{
	/* The returnForward method of this action handles displaying enrollment step-2 select MCA page
	 * @see com.dtcc.dnv.mcx.action.DtccBaseAction#returnForward(com.dtcc.sharedservices.security.common.CUOUser, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
        final String SUCCESS_FORWARD = "success";
        final String FAILURE_FORWARD = "failure";
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();
    	MessageLogger log = MessageLogger.getMessageLogger(SelectDealerAction.class.getName());

	    try
	    {
			EnrollmentForm enrollmentForm = (EnrollmentForm)form;
			
			Enrollment enrollment = (Enrollment) enrollmentForm.getTransaction();
			
            if (enrollment != null)
            {
                String[] selectedDealers = enrollment.getSelectedDealers();
                int selectedDealerCount = selectedDealers.length;

                //To get the Selected Dealer list from the Full list to display
                // in Step 2
                ArrayList selectedDealersList = new ArrayList();
                for (int index = 0; index < selectedDealerCount; index++)
                {
                    String dealerCode = selectedDealers[index];
	                DealerList dealerList = new DealerList();
	                dealerList.setDealerCode(dealerCode);
	                dealerList.setDealerName(CompanyUtil.getCompany(dealerCode).getCompanyName());
	                FormatterDelegate.format(dealerList, null, IFormatter.FORMAT_TYPE_OUTPUT);
                    selectedDealersList.add(dealerList);
                }
                enrollment.setSelectedDealersList(selectedDealersList);
                enrollmentForm.setTransaction(enrollment);
                forward = mapping.findForward(SUCCESS_FORWARD);
            }
			
	    }
	    catch (Exception e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));
            forward = mapping.findForward(FAILURE_FORWARD);
        }
		
		return forward;
	}

}
