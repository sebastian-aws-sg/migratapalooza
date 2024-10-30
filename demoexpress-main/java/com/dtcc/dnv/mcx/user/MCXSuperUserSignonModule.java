package com.dtcc.dnv.mcx.user;

import java.util.Vector;

import org.apache.struts.util.LabelValueBean;

import com.dtcc.dnv.mcx.beans.CompanyBean;
import com.dtcc.dnv.mcx.company.CompanyUtil;
import com.dtcc.dnv.mcx.formatters.FormatterDelegate;
import com.dtcc.dnv.mcx.formatters.IFormatter;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.mcx.util.MessageResources;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.security.model.ExUser;
import com.dtcc.dnv.otc.common.signon.DSSignOnTO;
import com.dtcc.dnv.otc.common.signon.DSSignonConstants;
import com.dtcc.dnv.otc.common.signon.config.DSSignonConfiguration;
import com.dtcc.dnv.otc.common.signon.modules.AbstractSignonModule;
import com.dtcc.dnv.otc.common.signon.modules.ISignonModuleRequest;
import com.dtcc.dnv.otc.common.signon.modules.ISignonModuleResponse;
import com.dtcc.dnv.otc.common.signon.modules.SignonModuleException;
import com.dtcc.dnv.otc.common.signon.modules.SignonModuleResponse;

/*
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55, Water Street,
 * New York, NY 10048
 * U.S.A
 * All Rights Reserved.
 *
 * This software may contain (in part or full) confidential/proprietary information of DTCC.
 * ("Confidential Information"). Disclosure of such Confidential
 * Information is prohibited and should be used only for its intended purpose
 * in accordance with rules and regulations of DTCC.
 * 
 * @author Kevin Lake
 * @version 1.0
 * Date: September 05, 2007
 */
public class MCXSuperUserSignonModule extends AbstractSignonModule {
	
	// Holds the logger instance
	private final static MessageLogger log = MessageLogger.getMessageLogger(MCXSuperUserSignonModule.class.getName());

	private static final String SUPER_USER_IND = "S";
	private static final String NOT_SUPER_USER = "false";
	
	public static final String EX_MCXUSERTYPE_INVALID = "MCXMDL001";
	public static final String EX_MCXUSERTYPE_INVALID_KEY = "signon.mcxsuperuser.MCXMDL001";
	
	public static final String EX_MCXUSERTYPE_INV_PROV = "MCXMDL002";
	public static final String EX_MCXUSERTYPE_INV_PROV_KEY = "signon.mcxsuperuser.MCXMDL002";
	
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.signon.modules.ISignonModule#process(com.dtcc.dnv.otc.common.signon.modules.ISignonModuleRequest)
	 */
	public ISignonModuleResponse process(ISignonModuleRequest request) throws SignonModuleException{
		DSSignonConfiguration config = null;
		
		// Instantiate response object.
		SignonModuleResponse response = new SignonModuleResponse();
		
		// Get signon transfer object
		DSSignOnTO signTO = request.getSignonTo();
		
        // Remove any prior user company object in transfer object
		signTO.removeAttribute(UserConstants.MCX_USER_COMPANY);
		
		// If the user is  "Super User", then it is holds "S"
		String userType = (String)signTO.getValue(DSSignonConstants.USERTYPE_WORKFLOW);
		
		if(userType == null)
			throw new SignonModuleException(EX_MCXUSERTYPE_INVALID,
	                request.getModuleName(),
					MessageResources.getMessage(EX_MCXUSERTYPE_INVALID_KEY));		

		// If the user is a super user only, then do not bypass the step and return true in the response
		if(userType.equalsIgnoreCase(SUPER_USER_IND)) {
			
			// For internal super user provide list of all firms for user to select
			Vector companyList = new Vector();
			companyList.add(new LabelValueBean("",""));
			try
            {
			// Get all available firms and provide list
			CompanyBean[] companies = CompanyUtil.getCompanys();
			for (int i = 0; i < companies.length; i++) {
				CompanyBean bean = companies[i];
				FormatterDelegate.format(bean,null,IFormatter.FORMAT_TYPE_OUTPUT);
                companyList.add(new LabelValueBean(bean.getCompanyName(),bean.getCompanyId()));
			}
            } catch (BusinessException e)
            {
                throw new SignonModuleException(DSSignonConstants.EX_BUSINESS, request.getModuleName(),
                        e.getMessage() + request.getModuleName(),e);
            }
			response.setSignonResponse((Vector)companyList);
			
			// Set bypass
			response.setBypass(false);
			
		} else {
			// If the user is not a super user, then bypass the step and return false in the response.
			response.setSignonResponse(NOT_SUPER_USER);
			response.setBypass(true);
		}		
		return response;
	}
	
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.signon.modules.ISignonModule#postProcess(com.dtcc.dnv.otc.common.signon.modules.ISignonModuleRequest)
	 */
	public void postProcess(ISignonModuleRequest request) throws SignonModuleException {
		
		DSSignOnTO signTO = request.getSignonTo();
		
		// Get authenticated user object
		ExUser user = request.getUser();
		
        // If the user is  "Super User", then it is holds "S"
		String userType = (String)signTO.getValue(DSSignonConstants.USERTYPE_WORKFLOW);
		
		// Company ID
		String companyId = null;				
		CompanyBean companyBean = null;		
		
		// If the user is a super user only, then do not bypass the step and return true in the response		
		if(userType.equalsIgnoreCase(UserConstants.SUPER_IND)) {
			
			// Get company id from transfer object
			companyId = (String)signTO.getValue(UserConstants.MCX_SUPERUSER_WORKFLOW);			
			
			// Get company bean based off of selected company id
			companyBean = CompanyUtil.getCompany(companyId);
						
            // Set user company properties 
			UserCompanyBean userCompany = new UserCompanyBean();
			userCompany.setUserCompanyId(companyId);
			userCompany.setUserCompanyName(companyBean.getCompanyName());
			userCompany.setUserCompanyEnvInd(companyBean.getCompanyEnvInd());			
			userCompany.setUserCompanyTypeInd(companyBean.getCompanyTypeInd());			
			
            // Set user company object in transfer object
			signTO.setAttribute(UserConstants.MCX_USER_COMPANY, userCompany);								
			
		} else if (userType.equalsIgnoreCase(UserConstants.DEALER_IND) || 
				   userType.equalsIgnoreCase(UserConstants.CLIENT_IND)) {
			
			// Get company id from user object 
			companyId = user.getCompanyId().trim();
			
			// Get company bean based off of users company id
			companyBean = CompanyUtil.getCompany(companyId);

			// Route to error page if company cannot be determined
			if(companyBean == null)
				throw new SignonModuleException(EX_MCXUSERTYPE_INVALID,
						                        request.getModuleName(),
												MessageResources.getMessage(EX_MCXUSERTYPE_INVALID_KEY));		  
			
			// Set user company properties
			UserCompanyBean userCompany = new UserCompanyBean();
			userCompany.setUserCompanyId(companyId);
			userCompany.setUserCompanyName(companyBean.getCompanyName());
			userCompany.setUserCompanyEnvInd(companyBean.getCompanyEnvInd());	
			userCompany.setUserCompanyTypeInd(companyBean.getCompanyTypeInd());
			
  		    // Validate dealer user is provisioned equally to company type
  		    if(userType.equalsIgnoreCase(UserConstants.DEALER_IND) && 
  		       !companyBean.getCompanyTypeInd().equalsIgnoreCase(MCXConstants.COMPANY_TYPE_DEALER_CD))
  		      throw new SignonModuleException(EX_MCXUSERTYPE_INV_PROV,
  		      	                              request.getModuleName(),
  		      	                              MessageResources.getMessage(EX_MCXUSERTYPE_INV_PROV_KEY));  		    
  		    
            // Validate client user is provisioned equally to company type
  		    if(userType.equalsIgnoreCase(UserConstants.CLIENT_IND) && 
  		       !companyBean.getCompanyTypeInd().equalsIgnoreCase(MCXConstants.COMPANY_TYPE_CLIENT_CD))
  		    	throw new SignonModuleException(EX_MCXUSERTYPE_INV_PROV,
                                                request.getModuleName(),
                                                MessageResources.getMessage(EX_MCXUSERTYPE_INV_PROV_KEY));
			
			// Set user company object in transfer object
			signTO.setAttribute(UserConstants.MCX_USER_COMPANY, userCompany);
		} else if(userType.equalsIgnoreCase(UserConstants.TMPLT_ADMIN_IND)) {			
            
			UserCompanyBean userCompany = new UserCompanyBean();
			/* 
			 * Default internal company id for internal users. This was done
			 * because internal id's are different in every region, so instead
			 * of having representations of every company id in the company 
			 * table for every region a default company was created referred 
			 * to by all true internal users.
			 */
			userCompany.setUserCompanyId(UserConstants.DTCC_INTERNAL_COMPANYID);
			userCompany.setUserCompanyTypeInd(UserConstants.USER_INTERNAL);
			
            // Set user company object in transfer object
			signTO.setAttribute(UserConstants.MCX_USER_COMPANY, userCompany);
		}
	}
}
