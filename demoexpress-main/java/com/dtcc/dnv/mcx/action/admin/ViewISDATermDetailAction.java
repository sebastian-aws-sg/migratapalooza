/*
 * Created on Sep 12, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.action.admin;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.beans.TermBean;
import com.dtcc.dnv.mcx.delegate.admin.ViewISDATermDetailDelegate;
import com.dtcc.dnv.mcx.delegate.mca.ModifyTermServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.ModifyTermServiceResponse;
import com.dtcc.dnv.mcx.form.TermForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/**
* This class is used as a Action to View the ISDA Term Details
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
* @author Elango TR
* @date Sep 21, 2007
* @version 1.0
*/

public class ViewISDATermDetailAction extends MCXBaseAction {

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.mcx.action.DtccBaseAction#returnForward(com.dtcc.sharedservices.security.common.CUOUser, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward returnForward(MCXCUOUser user, 
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception
	{		
		AuditInfo ai = null;
		ActionForward forward = new ActionForward(); 
		ActionMessages errors = new ActionMessages();
		ActionMessages messages = new ActionMessages();	
		String SUCCESS_FORWARD = "";
		String FAILURE_FORWARD = "failure";
		MessageLogger log = MessageLogger.getMessageLogger(ViewISDATermDetailAction.class.getName());
		ModifyTermServiceResponse serviceResponse = null;
		int termValId = 0;
		TermBean termBean = null;
		
		try
		{		    
		    //log.debug("1. Inside the try block of ViewISDATermDetailAction >>>");
			ViewISDATermDetailDelegate viewISDATermDetailDelegate = new ViewISDATermDetailDelegate();
			ModifyTermServiceRequest serviceRequest = new ModifyTermServiceRequest(ai);				
			TermForm termForm = (TermForm) form;			
			//log.debug("2. The TermForm in ViewISDATermDetailAction is >>>" + termForm);
			if(termForm.getTermValId() !=null )
			 termValId = Integer.parseInt(termForm.getTermValId());
			//log.debug("3. The termValId in ViewISDATermDetailAction is >>>" + termValId);
			if(termValId > 0)
				serviceRequest.setDbCallReq(true);			
	
				termBean = (TermBean) termForm.getTransaction();
				termBean.setTmpltTyp(termForm.getTmpltType());		
				termBean.setTermValId(Integer.parseInt(termForm.getTermValId()));				
				serviceRequest.setUsrId(user.getMCXUserGUID());
				serviceRequest.setCmpnyCd(user.getCompanyId());
				serviceRequest.setUserInd("L");//Replace this with value from termform
				
				//log.debug("4. The termForm.getTmpltType() in ViewISDATermDetailAction is >>>" + termForm.getTmpltType());
				//log.debug("5. The termValId in ViewISDATermDetailAction is >>>" + termValId);
				//log.debug("6. The user.getMCXUserGUID() in ViewISDATermDetailAction is >>>" + user.getMCXUserGUID());
				//log.debug("7. The user.getCompanyId() in ViewISDATermDetailAction is >>>" + user.getCompanyId());
				
				serviceRequest.setTransaction(termBean);
				//Template need not be locked if already locked or if the template status is published or waiting for approval
				if(termForm.getTmpltLocked().equalsIgnoreCase("Y") || (termForm.getMcaStatusCd().equalsIgnoreCase("P") || termForm.getMcaStatusCd().equalsIgnoreCase("W")))
					serviceRequest.setTmpltLocked(true);
				else
					serviceRequest.setTmpltLocked(false);
				
				//log.debug("8. The serviceRequest.isTmpltLocked() in ViewISDATermDetailAction is >>>" + serviceRequest.isTmpltLocked());
				
				serviceResponse = (ModifyTermServiceResponse) viewISDATermDetailDelegate.processRequest(serviceRequest);
				//log.debug("9. The serviceResponse.hasError() in ViewISDATermDetailAction is >>>" + serviceResponse.hasError());
				if (!serviceResponse.hasError())
				{
				    //log.debug("10. Inside if(!serviceResponse.hasError()) The termValId is in ViewISDATermDetailAction is >>>" + termValId);
					if(termValId > 0)	{							    
						TermBean respTermBean = (TermBean) serviceResponse.getTransaction();						
						termForm.setTransaction(respTermBean);
					}else {						
						termBean.setTermVal("");
						termForm.setTransaction(termBean);	
					}	
					
					if ( (((!(termForm.getMcaStatusCd().equalsIgnoreCase("P")))&& (!(termForm.getMcaStatusCd().equalsIgnoreCase("W"))))&& (termForm.getTmpltLocked().equalsIgnoreCase("N")))
							||						
							(termForm.getTmpltLocked().equalsIgnoreCase("Y") && termForm.getLockUsrInd().equalsIgnoreCase("L"))	
						)		
					{
					    log.debug("The Success_forward is unlocked in ViewISDATermDetailAction");
						SUCCESS_FORWARD ="unlocked";
					}else {
					    log.debug("The Success_forward is locked in ViewISDATermDetailAction");
						SUCCESS_FORWARD ="locked";
					}
					forward =  mapping.findForward(SUCCESS_FORWARD);
			    } else
			    {
			       //log.debug("13. The SP Return Message in ViewISDATermDetailAction is >>>" + serviceResponse.getSpReturnMessage()); 
			       errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", serviceResponse.getSpReturnMessage()));
			       forward =  mapping.findForward(MCXConstants.ERROR_POPUP_FORWARD);
			    }
				
		} catch (BusinessException e)
		{
	        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", new Timestamp(System.currentTimeMillis())));
	        forward = mapping.findForward(MCXConstants.ERROR_POPUP_FORWARD);
	        //log.debug("14. The BusinessException in ViewISDATermDetailActionis >>>"+ e.getMessage());
	    } catch (Exception e)
	    {
	        log.error(e);
	        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.internal.error", new Timestamp(System.currentTimeMillis())));
	        forward = mapping.findForward(MCXConstants.ERROR_POPUP_FORWARD);
	        //log.debug("15. The General Exception in ViewISDATermDetailAction is >>>"+ e.getMessage());
	    }
		finally
		{
		    log.debug("Inside the finally block of VIewISDATermDetailAction");
			if (!errors.isEmpty())
				saveErrors(request, errors);
			if (!messages.isEmpty())
				saveMessages(request, messages);
		}
		//return mapping.findForward(SUCCESS_FORWARD);
		return forward;
	}	

}
