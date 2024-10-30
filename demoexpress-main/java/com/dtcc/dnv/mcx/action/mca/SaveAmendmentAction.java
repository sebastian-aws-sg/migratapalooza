/*
 * Created on Sep 6, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.action.mca;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.beans.TermBean;
import com.dtcc.dnv.mcx.delegate.mca.ModifyTermServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.ModifyTermServiceResponse;
import com.dtcc.dnv.mcx.delegate.mca.SaveAmendmentDelegate;
import com.dtcc.dnv.mcx.form.TermForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/**
 * @author VVaradac
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SaveAmendmentAction extends MCXBaseAction {

	public ActionForward returnForward(MCXCUOUser user, 
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception
	{
		MessageLogger log = MessageLogger.getMessageLogger(SaveAmendmentAction.class.getName());
		ActionMessages errors = new ActionMessages();
		ActionForward forward = mapping.findForward(MCXConstants.SUCCESS_FORWARD);

		
		TermForm termForm = (TermForm) form;
		HttpSession session = null;
		FormFile file = termForm.getImageFile();
		String fileName = file.getFileName();
		//Image Object Validation - Image type should be only gif or jpg
		//And Image size cannot be greater than 5 Kb
		if(termForm.isImgPrsnt()){
		if(file != null && file.getFileData().length > 0 )
		{
			int len = fileName.length();
			boolean errPrsnt = false;
			String fileType = fileName.substring(len -3, len);
			if(!fileType.equalsIgnoreCase(MCXConstants.IMAGE_TYPE_GIF))
			{
				if(!fileType.equalsIgnoreCase(MCXConstants.IMAGE_TYPE_JPG))
				{
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, 
							"File Type can be either gif or jpg"));
					errPrsnt = true;
				}
			}
			if(file.getFileSize() > 5000)
			{
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, 
					"File Size cannot be greater than 5 KB"));
				errPrsnt = true;
			}
			if(errPrsnt)
			{
				saveErrors(request, errors);
				return mapping.findForward(MCXConstants.FAILURE_FORWARD);
			}
		}
		}

		
		try
		{
			TermBean termBean = (TermBean) termForm.getTransaction();
			session = request.getSession();
			if(termForm.getTmpltType().equalsIgnoreCase(MCXConstants.ISDA_TEMPLATE_TYPE) ||
					   termForm.getTmpltType().equalsIgnoreCase(MCXConstants.EXECUTED_TEMPLATE_TYPE)  ||
					   termForm.getTmpltType().equalsIgnoreCase(MCXConstants.CP_FINAL_TEMPLATE_TYPE)  ){
					    session.setAttribute(MCXConstants.BASE_TMPLT_IS_FI_EX, MCXConstants.CONST_YES);
					}
			termBean.setTmpltTyp(termForm.getTmpltType());
			//Set the Amendment Text which is in HTML format
			termBean.setTermVal(termForm.getAmendmentValue());
			//Set the Image object as Byte Array
			termBean.setImageObj(termForm.getImageFile().getFileData());
			//Set the Image File Name
			termBean.setDocName(fileName);
			
			ModifyTermServiceRequest serviceRequest = new ModifyTermServiceRequest(user.getAuditInfo());
			
			if(termForm.getTmpltLocked().equalsIgnoreCase(MCXConstants.CONST_YES))
				serviceRequest.setTmpltLocked(true);
			
			serviceRequest.setActingDealer(termForm.isActingDealer());			
			//Set the TemplateBean in Service Request Transaction
			serviceRequest.setTransaction(termBean);
			//Set the User Id and Company Id in Service Request
			serviceRequest.setUsrId(user.getMCXUserGUID());
			serviceRequest.setCmpnyCd(user.getCompanyId());
						
			SaveAmendmentDelegate saveAmendmentDelegate = new SaveAmendmentDelegate();
			ModifyTermServiceResponse serviceResponse = (ModifyTermServiceResponse) 
				saveAmendmentDelegate.processRequest(serviceRequest);
				
			if(serviceResponse.hasError())
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						MCXConstants.GENERAL_BUSINESS_ERROR, serviceResponse.getSpReturnMessage()));

			if(serviceResponse.getTransaction() != null)
				termForm.setTransaction(serviceResponse.getTransaction());
			
			request.setAttribute(MCXConstants.TERMFORM, termForm);
		}
		catch (BusinessException e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));
            return mapping.findForward(MCXConstants.ERROR_POPUP_FORWARD);
        } catch (Exception e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));
            return mapping.findForward(MCXConstants.ERROR_POPUP_FORWARD);
        }	
        finally
		{
			if (!errors.isEmpty())
			{
				saveErrors(request, errors);
				forward = mapping.findForward(MCXConstants.FAILURE_FORWARD);
			}
		}
        
		return forward;
	}

}
