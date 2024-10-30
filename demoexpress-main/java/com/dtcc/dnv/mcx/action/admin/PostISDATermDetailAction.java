package com.dtcc.dnv.mcx.action.admin;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.beans.TermBean;
import com.dtcc.dnv.mcx.delegate.admin.PostISDATermDetailDelegate;
import com.dtcc.dnv.mcx.delegate.mca.ModifyTermServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.ModifyTermServiceResponse;
import com.dtcc.dnv.mcx.form.TermForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/**
* This class is used as a Action to Post the ISDA Term Details by the admin
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
public class PostISDATermDetailAction extends MCXBaseAction {

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
		TermForm termForm = (TermForm) form;
		ActionForward forward = new ActionForward(); 
		ActionMessages errors = new ActionMessages();
		ActionMessages messages = new ActionMessages();			
		final String SUCCESS_FORWARD = "success";
		final String FAILURE_FORWARD = "failure";
		MessageLogger log = MessageLogger.getMessageLogger(PostISDATermDetailAction.class.getName());
		
		log.debug("Entering the PostISDATermDetailAction");
		
		
		FormFile file = termForm.getImageFile();
		String fileName = file.getFileName();
		
//		log.debug("2. The Formfile in PostISDATermDetailAction is >>>" + file);
//		log.debug("3. The fileName in PostISDATermDetailAction is >>>" + fileName);
//		log.debug("4. The termForm.isImgPrsnt() in PostISDATermDetailAction is >>>" + termForm.isImgPrsnt());
		if(termForm.isImgPrsnt()){
//		    log.debug("5. The file.getFileData().length in PostISDATermDetailAction is >>>" + file.getFileData().length);
			if(file != null && file.getFileData().length > 0 )
			{
				int len = fileName.length();
				boolean errPrsnt = false;
				String fileType = fileName.substring(len -3, len);
//				log.debug("6. The fileType in PostISDATermDetailAction is >>>" + fileType);
				if(!fileType.equalsIgnoreCase("gif"))
				{
					if(!fileType.equalsIgnoreCase("jpg"))
					{
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, 
								"File Type can be either gif or jpg"));						
						errPrsnt = true;
						log.debug("The error in PostISDATermDetailAction is File Type can be either gif or jpg");
					}
				}
				if(file.getFileSize() > 5000)
				{
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, 
						"File Size cannot be greater than 5 KB"));
					errPrsnt = true;
					log.debug("The error in PostISDATermDetailAction is File Size cannot be greater than 5 KB");
				}
				//log.debug("8. Is Err present in PostISDATermDetailAction >>>" + errPrsnt);
				if(errPrsnt)
				{
					saveErrors(request, errors);
					return mapping.findForward("failure");
				}
			}
		}
		try {
		    //log.debug("9. The termForm.getAmendmentValue() in PostISDATermDetailAction is >>>" + termForm.getAmendmentValue());
		    //log.debug("10. The termForm.getImageFile().getFileData() in PostISDATermDetailAction is >>>" + termForm.getImageFile().getFileData());
			((TermBean)termForm.getTransaction()).setTermVal(termForm.getAmendmentValue());
			((TermBean)termForm.getTransaction()).setImageObj(termForm.getImageFile().getFileData());
			((TermBean)termForm.getTransaction()).setDocName(termForm.getImageFile().getFileName());
		
			PostISDATermDetailDelegate postISDATermDetailDelegate = new PostISDATermDetailDelegate();
			
			
			ModifyTermServiceRequest serviceRequest = new ModifyTermServiceRequest(ai);
			ModifyTermServiceResponse serviceResponse = null;
			
			if(termForm.getTmpltLocked().equalsIgnoreCase(MCXConstants.CONST_YES))
				serviceRequest.setTmpltLocked(true);			
			
			serviceRequest.setTransaction(termForm.getTransaction());
			serviceRequest.setUsrId(user.getMCXUserGUID());
			serviceRequest.setCmpnyCd(user.getCompanyId());
			
			//log.debug("11. termForm.getTransaction() in PostISDATermDetailAction >>>" + termForm.getTransaction());
			//log.debug("12. user.getMCXUserGUID() in PostISDATermDetailAction >>>" + user.getMCXUserGUID());
			//log.debug("13. user.getCompanyId()in PostISDATermDetailAction >>>" + user.getCompanyId());
			
			serviceResponse = (ModifyTermServiceResponse) postISDATermDetailDelegate.processRequest(serviceRequest);
			//log.debug("14. serviceResponse.hasError() in PostISDATermDetailAction >>>" + serviceResponse.hasError());
			if (!serviceResponse.hasError())
			{
				TermBean termBean = (TermBean) serviceResponse.getTransaction();
		
				termForm.setTransaction(termBean);
				request.setAttribute("TermForm", termForm);
				forward =  mapping.findForward(SUCCESS_FORWARD);
			} else
		    {
			    //log.debug("15. serviceResponse.getSpReturnMessage() in PostISDATermDetailAction >>>" + serviceResponse.getSpReturnMessage());
			    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", serviceResponse.getSpReturnMessage()));
			    forward =  mapping.findForward(FAILURE_FORWARD);
		    }	
		} catch (BusinessException e)
		{
		    //log.debug("16. BusinessException in PostISDATermDetailAction >>>" + e);
	        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.business.error", new Timestamp(System.currentTimeMillis())));
	        forward = mapping.findForward(MCXConstants.ERROR_POPUP_FORWARD);
	        return forward;

	    } catch (Exception e)
	    {
	        //log.debug("17. General Exception in PostISDATermDetailAction >>>" + e);
	        log.error(e);
	        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.internal.error", new Timestamp(System.currentTimeMillis())));
	        forward = mapping.findForward(MCXConstants.ERROR_POPUP_FORWARD);
	        return forward;
	    }
		finally
		{
		    //log.debug("18. insid ethe finally block of PostISDATermDetailAction >>>");
			if (!errors.isEmpty())
				saveErrors(request, errors);
			if (!messages.isEmpty())
				saveMessages(request, messages);
		}
		//return mapping.findForward("success");
		return forward;
	}

}
