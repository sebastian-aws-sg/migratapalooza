package com.dtcc.dnv.mcx.action.mca;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.dtcc.dnv.mcx.action.MCXBaseAction;
import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.TemplateServiceResponse;
import com.dtcc.dnv.mcx.delegate.mca.ViewMCADelegate;
import com.dtcc.dnv.mcx.form.TemplateForm;
import com.dtcc.dnv.mcx.form.TermForm;
import com.dtcc.dnv.mcx.user.MCXCUOUser;
import com.dtcc.dnv.mcx.util.ApplicationContextHandler;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/**
 * This class is used as a Action to fetch details of a ISDA published MCA
 * template
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
 * @author Narahari A
 * @date Sep 3, 2007
 * @version 1.0
 */

public class ViewMCAAction extends MCXBaseAction {

	public ActionForward returnForward(MCXCUOUser user, ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		MessageLogger log = MessageLogger.getMessageLogger(ViewMCAAction.class.getName());
	
		ActionForward forward = new ActionForward();
		ActionMessages errors = new ActionMessages();
		
		//default forward value to failure
		String forwardValue = MCXConstants.SUCCESS_FORWARD;
		String frmScr = "";
		String sltInd = "";
		String viewInd = "";	
		String cltCd= "";
		String cltNm = "";
		HashMap enrollCltMap = null;
		TemplateBean templateBean = null;
		AuditInfo ai = user.getAuditInfo();

		TemplateServiceRequest serviceRequest = new TemplateServiceRequest(ai);
		
		TemplateServiceResponse serviceResponse = null;
		TemplateForm templateForm = (TemplateForm) form;
		HttpSession session = null;
		ViewMCADelegate delegate = new ViewMCADelegate();
		//IF THE LOGGED IN USER IS A DEALER THEN HAVE TO PASS THE DEALER ID TO
		// FETCH TEMPLATE LIST
		try {
			 session = request.getSession();
			/*
			 * The action class may be triggered from the following menu options
			 * 1) View Industry Published 2) MCA Wizard 3) Selected specific
			 * template from menu 4) Pending/Executed tabs of the dealer/cp home
			 * page 5) Approved firms tab of the Dealer screen, if the user
			 * navigates from this screen all the select option drop downs
			 * should be disabled and only the selected template should be
			 * displayed to the user 5) Clicking on the Product/Sub-Product
			 * option in View Industry Published/MCA Wizard Pages 6) Selecting a
			 * region from the Region dropdown in the View Industry
			 * Published/MCA Wizard screen 7) Selecting a template from the
			 * Template dropdown in the View Industry Published/MCA Wizard
			 * screen 8) Selecting a category from the Category dropdown in the
			 * View Industry Published/MCA Wizard screen 9) Selecting a view
			 * type from the View dropdown in the MCA Wizard screen When the
			 * user logins from the isda menu the following details should be
			 * available From frmScr - to indicate the screen from which the
			 * request has come
			 *
			 * Possible Values 1) frmScr - To forward to the appropriate jsp
			 * page i.e. Industry Published, MCA Wizard Step 1/2,
			 * dealer/counterparty negogiation page ISDA - Industry Published
			 * MW1 - MCA Wizard Step 1 MW2 - MCA wIzard Step 2 NEG - Dealer/CP
			 * Negogiation Home page
			 *
			 * 2) sltInd - To indicate the selection type i.e
			 * Menu/Region/Template/Category/Product(Tabs) ME - Menu PR -
			 * Products RG - Region TE - Template CG - Category
			 *
			 *
			 * 3) viewInd - To indicate the type of view requested i.e.
			 * Complete/Amendment/Comment F - Complete View A - Amendment View C -
			 * Comment View
			 *
			 *
			 */

			templateBean = (TemplateBean) templateForm.getTransaction();

			if (templateBean == null) {
				templateBean = new TemplateBean();
			}

			//	DETAILS FROM MCXCUOUSER OBJECT

			frmScr = templateForm.getFrmScr();
			sltInd = templateForm.getSltInd();
			viewInd = templateForm.getViewInd();

			serviceRequest.setCmpnyId(user.getCompanyId());
			serviceRequest.setUserId(user.getMCXUserGUID());

			//If the page is forwarded from Renegotiation page, get the new template Id from Request
			if(request.getAttribute("tmpltId") != null)
				templateForm.setTmpltId((String) request.getAttribute("tmpltId"));

			//While posting the amendment/comments the template id from request
			// wil be used
			if(templateForm.getTmpltId()!= null && !templateForm.getTmpltId().equalsIgnoreCase("")){
				templateBean.setTmpltId(Integer.parseInt(templateForm.getTmpltId()));
				templateForm.setTmpltId(MCXConstants.EMPTY);
			}else if(templateForm.getTransaction() != null) {
				templateBean.setTmpltId(((TemplateBean)templateForm.getTransaction()).getTmpltId());
			} else if(request.getAttribute(MCXConstants.TERMFORM) != null) {
				TermForm termForm = (TermForm) request.getAttribute(MCXConstants.TERMFORM);
				templateBean.setTmpltId(Integer.parseInt(termForm.getTmpltID()));
				sltInd = "AC";
			}

  		   	if(sltInd.equalsIgnoreCase(MCXConstants.SLT_IND_PRODUCT) 
  		   			|| sltInd.equalsIgnoreCase(MCXConstants.SLT_IND_REGION))
  		   	{
			  	templateBean.setDerPrdCd(templateForm.getProdCd());
			  	templateBean.setDerSubPrdCd(templateForm.getSubProdCd());
			  }
  		   	
  		   	if(sltInd.equalsIgnoreCase(MCXConstants.SLT_IND_ENR_APP))
  		   	{
  				cltCd= templateForm.getCltCd();
  				cltNm =templateForm.getCltNm();
  				enrollCltMap = new HashMap();
  				enrollCltMap.put(cltCd,cltNm);
  				session.setAttribute(MCXConstants.ENROLLEDCPs, enrollCltMap);			  
  				session.setAttribute(MCXConstants.SLT_IND_ENR_APP,MCXConstants.CONST_YES);
			 }  		   	
  		   	else if(sltInd.equalsIgnoreCase(MCXConstants.SLT_IND_REGION) || 
  		   			sltInd.equalsIgnoreCase(MCXConstants.SLT_IND_PRODUCT) || 
					sltInd.equalsIgnoreCase(MCXConstants.SLT_IND_MENU))
  		   	{
  		   		if(session.getAttribute(MCXConstants.ENROLLEDCPs) != null)
  		   			session.removeAttribute(MCXConstants.ENROLLEDCPs);
  		   		if(session.getAttribute(MCXConstants.SLT_IND_ENR_APP) != null)
  		   			session.removeAttribute(MCXConstants.SLT_IND_ENR_APP); 		   	
  		   	}

  		   	if (sltInd.equalsIgnoreCase(MCXConstants.SLT_IND_REGION))
  		   		serviceRequest.setRgnCd(templateBean.getRgnCd());

			if (frmScr.equalsIgnoreCase(MCXConstants.FRM_SCR_ISDA))
			{
				serviceRequest.setTmpltTypInd(MCXConstants.REQ_ISDA_TERM);
			} else {
				serviceRequest.setTmpltTypInd(MCXConstants.GENERIC_TEMPLATE_TYPE);
			}
			
			serviceRequest.setCatgyId(templateForm.getCatgyId());

			serviceRequest.setFunctInd(MCXConstants.FUNCTION_INDICATOR_MCA);
			serviceRequest.setSltInd(sltInd);
			serviceRequest.setViewInd(viewInd);

			/*
			 * Only if the request is from menu then set the value for the
			 * Default flag to indicate that the most recent published is to be
			 * displayed to the user
			 */
			if (sltInd.equalsIgnoreCase(MCXConstants.SLT_IND_MENU)
					&& !frmScr.equalsIgnoreCase(MCXConstants.FRM_SCR_NEGOGIATION))
				serviceRequest.setDfltFlg(MCXConstants.CONST_YES);

			serviceRequest.setTransaction(templateBean);
			serviceResponse = (TemplateServiceResponse) delegate.processRequest(serviceRequest);
			
			if(serviceResponse.hasError())
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						MCXConstants.GENERAL_BUSINESS_ERROR, serviceResponse.getSpReturnMessage()));


			if (frmScr != null
					&& !(frmScr.equalsIgnoreCase(MCXConstants.FRM_SCR_ISDA)))
			{
				HashMap viewMap = new HashMap();
				viewMap.put("F", "Complete MCA");
				viewMap.put("A", "Amendments Only");
				viewMap.put("C", "Comments Only");
				templateForm.setViewMap(viewMap);
			}
			templateForm.setSltInd(sltInd);
			templateForm.setViewInd(viewInd);

			

			buildTemplateForm(session, serviceResponse, templateForm);			
			TemplateBean tempBean = (TemplateBean) templateForm.getTransaction();
			templateForm.setTmpltPresent(serviceResponse.isTmpltPresent());

			if(tempBean.getOrgDlrCd().equalsIgnoreCase(user.getCompanyId()))
			{
				templateForm.setActingDealer(true);
			}

			if(tempBean.getOrgDlrCd().equals(tempBean.getOrgCltCd()) && tempBean.getDlrStCd().equalsIgnoreCase("P"))
			{
				templateForm.setActingDealer(true);
			}
			else if(tempBean.getOrgDlrCd().equals(tempBean.getOrgCltCd()) && !tempBean.getDlrStCd().equalsIgnoreCase("P"))
			{
				templateForm.setActingDealer(false);
			}

			if(tempBean.getOrgDlrCd().equals(tempBean.getOrgCltCd()) 
					&& tempBean.getDlrStCd().equalsIgnoreCase("A") 
					&& tempBean.getCltStCd().equalsIgnoreCase("A"))
			{
				templateForm.setActingDealer(true);
			}

			if(frmScr.equalsIgnoreCase(MCXConstants.FRM_SCR_WIZARD_1)){
				templateForm.setDisplayNm(user.getCompanyName());				
			}else if(frmScr.equalsIgnoreCase(MCXConstants.FRM_SCR_WIZARD_2)){
				templateForm.setDisplayNm(tempBean.getOrgCltNm());				
			}else if(frmScr.equalsIgnoreCase(MCXConstants.FRM_SCR_NEGOGIATION) && templateForm.getActingDealer()){
				templateForm.setDisplayNm(tempBean.getOrgCltNm());
			}else{
				templateForm.setDisplayNm(tempBean.getOrgDlrNm());
			}
				
			templateForm.setEnableExecute(true);
			templateForm.setEnableSubmitToCP(true);

			boolean isDealer = templateForm.getActingDealer();
			if(frmScr.equalsIgnoreCase(MCXConstants.FRM_SCR_NEGOGIATION))
			{
				if(!tempBean.isEligibleForExc())
					templateForm.setEnableExecute(false);
				
				if(!tempBean.isEligibleForSub())
					templateForm.setEnableSubmitToCP(false);
				
				if(tempBean.getLockSt().equalsIgnoreCase("Y"))
				{
					templateForm.setEnableSubmitToCP(false);
					templateForm.setEnableExecute(false);
				}
				if(isDealer && !tempBean.getDlrStCd().equalsIgnoreCase("P"))
				{
					templateForm.setEnableSubmitToCP(false);
					templateForm.setEnableExecute(false);
				}
				else if(!isDealer && !tempBean.getCltStCd().equalsIgnoreCase("P"))
				{
					templateForm.setEnableSubmitToCP(false);
					templateForm.setEnableExecute(false);
				}
				if(isDealer && !tempBean.getCltStCd().equalsIgnoreCase("A"))
					templateForm.setEnableExecute(false);
				
				if(isDealer && templateForm.getEnableExecute())
					templateForm.setEnableSubmitToCP(false);
			}
				
				ApplicationContextHandler appContHandler = ApplicationContextHandler.getInstance();
				//Fortify forces objects stored in session to be serializable. Hence ArrayList typecast to Serializable
			request.setAttribute(MCXConstants.PRODUCTS, (Serializable)appContHandler.getProductsList());

			if(sltInd.equalsIgnoreCase("PT"))
				forwardValue = "printview";

			forward = mapping.findForward(forwardValue);
		}
		catch (BusinessException e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
            		MCXConstants.GENERAL_BUSINESS_ERROR, new Timestamp(System.currentTimeMillis())));
            forward = mapping.findForward(MCXConstants.ERROR_FORWARD);
        } catch (Exception e)
        {
            log.error(e);
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
            		MCXConstants.GENERAL_INTERNAL_ERROR, new Timestamp(System.currentTimeMillis())));
            forward = mapping.findForward(MCXConstants.ERROR_FORWARD);
        }	
        finally
		{
			if (!errors.isEmpty())
				saveErrors(request, errors);
		}

		return forward;
	}

	/**
	 * Build the Template Form to Show MCA Page
	 * @param session
	 * @param serviceResponse
	 * @param templateForm
	 * @return
	 */
	private TemplateForm buildTemplateForm(HttpSession session,
			TemplateServiceResponse serviceResponse, TemplateForm templateForm) 
	{
		TemplateBean templateBean = (TemplateBean) serviceResponse.getTransaction();
		//Set the Template Bean
		if(templateBean != null)
		{
			templateForm.setTransaction(templateBean);
			session.setAttribute("TEMPLATE_BEAN", (TemplateBean) templateBean);
		}
		else
		{
			templateForm.setTransaction((TemplateBean)session.getAttribute("TEMPLATE_BEAN"));
		}		

		//Set the List of Regions
		if(serviceResponse.getRgnList() != null)
		{
			templateForm.setRgnList((ArrayList)serviceResponse.getRgnList());
			//Fortify forces objects stored in session to be serializable. Hence HashMap typecast to Serializable			
			session.setAttribute("REGION_LIST",	(Serializable)serviceResponse.getRgnList());
	}
		else
	{
			templateForm.setRgnList((ArrayList)session.getAttribute("REGION_LIST"));
		}

		//Set the List of Templates
		if(serviceResponse.getTmpltList() != null)
		{
			templateForm.setTmpltList(serviceResponse.getTmpltList());
			//Fortify forces objects stored in session to be serializable. Hence ArrayList typecast to Serializable			
			session.setAttribute("TEMPLATE_LIST", (Serializable) serviceResponse.getTmpltList());
		}
		else
		{
			templateForm.setTmpltList((ArrayList)session.getAttribute("TEMPLATE_LIST"));
		}

		//Set the List of Category
		if(serviceResponse.getCategyList() != null)
		{
			templateForm.setCategyList(serviceResponse.getCategyList());
			session.setAttribute("CATEGORY_LIST",(Serializable) serviceResponse.getCategyList());
		}
		else
		{
			templateForm.setCategyList((ArrayList) session.getAttribute("CATEGORY_LIST"));
		}

		//Set the Category Bean List
		templateForm.setCategyBeanList(serviceResponse.getCategyBeanList());

		if(templateForm.getFrmScr().equalsIgnoreCase(MCXConstants.FRM_SCR_WIZARD_1) 
				&& templateForm.getViewInd().equalsIgnoreCase(MCXConstants.VIEW_INDICATOR_COMMENTS))
		{
			if(templateBean.getTmpltTyp().equalsIgnoreCase("C") || templateBean.getTmpltTyp().equalsIgnoreCase("E"))
			{
				templateForm.setCategyBeanList(new ArrayList(0));
			}
		}

		return templateForm;
	}

}
