package com.dtcc.dnv.mcx.action.admin;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dtcc.dnv.mcx.action.IManagedSession;
import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.delegate.admin.MCATypeListDelegate;
import com.dtcc.dnv.mcx.delegate.admin.MCATypeListServiceRequest;
import com.dtcc.dnv.mcx.delegate.admin.MCATypeListServiceResponse;
import com.dtcc.dnv.mcx.form.MCASetupForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
 * This class is used as a action to retrive List of MCA Types for the Admin
 * homepage(MCA Setup and Posting).The fields of this list view are "MCA Type",
 * "Status", "Date Posted", "Last modified by", "Last Modified on" and 
 * "MCA Agreement Date".
 * 
 * RTM Reference : 1.22.2 Clicking on MCA Setup or Home, would display the list
 * of MCA's in a summary list. The fields of this list view are “MCA Type”,
 * ”Status”, “Date Posted”, “Last modified by” , “last modified on” and "MCA
 * Agreement Date".
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
 * @author Seeni Mohammad Azharudin S
 * @date Sep 17, 2007
 * @version 1.0
 *  
 */

public class MCATypeListAction extends MCXBaseAction implements IManagedSession
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(MCATypeListAction.class.getName());

    /* (non-Javadoc)
     * @see com.dtcc.dnv.mcx.action.MCXBaseAction#returnForward(com.dtcc.dnv.mcx.user.MCXCUOUser, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     * This action class is used to send the service request to the delegate to retrive the list needed to populate the MCA Setup & Posting screen
     */
    public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        final String SUCCESS = "success";
        final String FAILURE = "failure";
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();
        boolean errorPage = true;
        try
        {
            log.info("inside mcatypelist action");
            MCASetupForm mcaSetupForm = (MCASetupForm) form;

            MCATypeListServiceRequest serviceRequest = new MCATypeListServiceRequest(user.getAuditInfo());
            MCATypeListServiceResponse serviceResponse = new MCATypeListServiceResponse();

            String companyID = user.getCompanyId();
            String userID = user.getMCXUserGUID();
            String tabIndicator = MCXConstants.MCA_ADMIN_ACTION;
            
            /*
             * Setting the values needed to passed to the dao to retrieve the values in the request object
             */
            serviceRequest.setCompanyID(companyID);
            serviceRequest.setUserID(userID);
            serviceRequest.setPendingExecutedIndicator(tabIndicator);

            MCATypeListDelegate delegate = new MCATypeListDelegate();

            //delegate request
            serviceResponse = (MCATypeListServiceResponse) delegate.processRequest(serviceRequest);
            if (serviceResponse.hasError())
            {
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", serviceResponse.getSpReturnMessage()));

            }
            mcaSetupForm.setMcaTypeList(serviceResponse.getMcaTypeList());
            mcaSetupForm.setMcaTypeCount(serviceResponse.getMcaTypeList().size());
            mcaSetupForm.setAdminDetailsStatus(serviceResponse.getPendingExecutedStatus());
            if (mcaSetupForm.getAdminDetailsStatus() != null && MCXConstants.SUCCESS_FORWARD.equals(mcaSetupForm.getAdminDetailsStatus()))
            {
                errorPage = false;
            }
        } catch (BusinessException be)
        {
            log.error("business exception in MCA Type List action class " + be);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));
            forward = mapping.findForward(MCXConstants.ERROR_FORWARD);
        } catch (Exception e)
        {
            log.error("general exception in MCA type List action class " + e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));
            forward = mapping.findForward(MCXConstants.ERROR_FORWARD);
        }
        if (errorPage)
        {
            if (!errors.isEmpty())
            {
                saveMessages(request, errors);
            }
            forward = mapping.findForward(MCXConstants.ERROR_FORWARD);
        } else
        {
            forward = mapping.findForward(SUCCESS);
        }
        return forward;
    }
}
