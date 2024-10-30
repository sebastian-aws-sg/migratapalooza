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
 * This class is used as an action to view the enrollment confirmation page.
 * 
 * RTM Reference : 3.3.3.1 To create an enrolment request, actor is directed to the enrolment list of dealers, selection of MCAs throuhg enrolment grid and the enrolment confirmation screen
 * RTM Reference : 3.3.3.2 Actor chooses to select a another MCA for enrolment after the previous MCA is approved by dealer
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
 * @author Maheswaran N
 * @date Nov 20, 2007
 * @version 1.0 
 *  
 */

public class ViewEnrollConfirmAction extends MCXBaseAction
{
    /*
     * The returnForward method of this class handles display of enrollment confirmation details
     * @see com.dtcc.dnv.mcx.action.MCXBaseAction#returnForward(com.dtcc.dnv.mcx.user.MCXCUOUser, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        final String SUCCESS_FORWARD = "success";
        final String FAILURE_FORWARD = "failure";
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();
    	MessageLogger log = MessageLogger.getMessageLogger(ViewEnrollConfirmAction.class.getName());

        try
        {
            EnrollmentForm enrollmentForm = (EnrollmentForm) form;
            Enrollment enrollment = (Enrollment) enrollmentForm.getTransaction();
            
                if (enrollment != null)
                {
                    ArrayList selectedDealersList = new ArrayList();
		            String[] selectedDealers = enrollment.getSelectedDealers();
                	String[] selectedMCAs = enrollment.getMcaNames();
		            //To set the Selected MCA names for each dealer to show in Enrollment confirmation 
		            int count = selectedDealers.length;
		            for(int index = 0; index < count ; index++)
		            {
		                String dealerCode = selectedDealers[index];
		                DealerList dealerList = new DealerList();
		                dealerList.setDealerCode(dealerCode);
		                dealerList.setDealerName(CompanyUtil.getCompany(dealerCode).getCompanyName());
		                dealerList.setMcaNames(selectedMCAs[index]);
		                FormatterDelegate.format(dealerList, null, IFormatter.FORMAT_TYPE_OUTPUT);
		                selectedDealersList.add(dealerList);
		            }
	                enrollment.setSelectedDealersList(selectedDealersList);
		            enrollmentForm.setTransaction(enrollment);
		            enrollmentForm.setFedDealerCount(1);
                }
	            forward = mapping.findForward(SUCCESS_FORWARD);

        } catch (Exception e)
        {
            log.error(e);
            e.printStackTrace();
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
