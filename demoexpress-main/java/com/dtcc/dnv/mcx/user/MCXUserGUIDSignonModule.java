package com.dtcc.dnv.mcx.user;

import com.dtcc.dnv.mcx.delegate.user.UserGUIDSignonDelegate;
import com.dtcc.dnv.mcx.delegate.user.UserGUIDSignonRequest;
import com.dtcc.dnv.mcx.delegate.user.UserGUIDSignonResponse;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.mcx.util.MessageResources;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.UserException;
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
 * Date: October 12, 2007
 */
public class MCXUserGUIDSignonModule extends AbstractSignonModule {

	// Holds the logger instance
	private final static MessageLogger log = MessageLogger.getMessageLogger(MCXUserGUIDSignonModule.class.getName());

	public static final String EX_MCXUSERID_INV_ID = "MCXMDL003";
	public static final String EX_MCXUSERID_INV_ID_KEY = "signon.mcxuserguid.MCXMDL003";

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.signon.modules.ISignonModule#process(com.dtcc.dnv.otc.common.signon.modules.ISignonModuleRequest)
	 */
	public ISignonModuleResponse process(ISignonModuleRequest request) throws SignonModuleException{
		DSSignonConfiguration config = null;

		// Instantiate response object.
		SignonModuleResponse response = new SignonModuleResponse();

		// Get signon transfer object
		DSSignOnTO signTO = request.getSignonTo();

        // Gets the reference for the ExUser Object
		ExUser user = request.getUser();

		// Get user's GUID
		String userGuid = user.getGUID();

        // Get user company bean from transfer object
		UserCompanyBean userCompany = (UserCompanyBean)signTO.getAttribute(UserConstants.MCX_USER_COMPANY);

		// Validate transfer object
		if(userCompany == null || userCompany.getUserCompanyId().trim().length() == 0) {
			throw new SignonModuleException(MCXSuperUserSignonModule.EX_MCXUSERTYPE_INVALID,
                    request.getModuleName(),
					MessageResources.getMessage(MCXSuperUserSignonModule.EX_MCXUSERTYPE_INVALID_KEY));
		}

		// MCA-Xpress specific GUID, based off of user's GUID
		String mcxUserGuid = "";

		// Set user information required to gather MCX GUID
		UserGUIDSignonDelegate delegate = new UserGUIDSignonDelegate();

		// TODO AuditInfo
		UserGUIDSignonRequest serviceRequest = new UserGUIDSignonRequest(null);
		serviceRequest.setUserCompanyId(userCompany.getUserCompanyId());
		serviceRequest.setUserGUID(user.getGUID().trim());
		serviceRequest.setUserId(user.getUserId().trim());
		serviceRequest.setUserName(user.getUserLastName().trim() + "," + user.getUserFirstName().trim());
		serviceRequest.setPhoneNumber(user.getPhoneNumber());
		serviceRequest.setUserCompanyTypeInd(userCompany.getUserCompanyTypeInd());

		UserGUIDSignonResponse serviceResponse = null;
		try {
			// Get user's MCX GUID
			serviceResponse = (UserGUIDSignonResponse)delegate.processRequest(serviceRequest);
			mcxUserGuid = serviceResponse.getMcxGuid();

		} catch (BusinessException e) {
			throw new SignonModuleException(DSSignonConstants.EX_BUSINESS, request.getModuleName(),
					                        e.getMessage() + request.getModuleName(),e);
		} catch (UserException e) {
			throw new SignonModuleException(DSSignonConstants.EX_BUSINESS, request.getModuleName(),
					                        e.getMessage() + request.getModuleName(),e);
		}

		// Throw exception is MCX GUID cannot be determined
		if(mcxUserGuid == null || mcxUserGuid.trim().length() == 0)
			throw new SignonModuleException(EX_MCXUSERID_INV_ID,
					                        request.getModuleName(),
											MessageResources.getMessage(EX_MCXUSERID_INV_ID_KEY));

		// Set response and bypass
		response.setSignonResponse(mcxUserGuid);
		response.setBypass(true);

		return response;
	}

}
