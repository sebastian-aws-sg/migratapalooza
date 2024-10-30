package com.dtcc.dnv.mcx.delegate.user;

import com.dtcc.dnv.mcx.dbhelper.user.UserGUIDDbRequest;
import com.dtcc.dnv.mcx.dbhelper.user.UserGUIDDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.proxy.user.UserGUIDDbProxy;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.builders.DbRequestBuilder;
import com.dtcc.dnv.otc.common.builders.exception.BuilderException;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;


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
public class UserGUIDSignonDelegate  extends MCXAbstractBusinessDelegate {
	
	private static final MessageLogger log = MessageLogger.getMessageLogger(UserGUIDSignonDelegate.class.getName());

	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
	 */
	public IServiceResponse processRequest(IServiceRequest serviceRequest) throws BusinessException, UserException {
		UserGUIDSignonResponse response = new UserGUIDSignonResponse();
		UserGUIDSignonRequest request = (UserGUIDSignonRequest)serviceRequest;
		
		UserGUIDDbRequest dbRequest = new UserGUIDDbRequest("DPMXHUSR", request.getAuditInfo());
		try {
			DbRequestBuilder.copyObject(serviceRequest, dbRequest);
	
	        UserGUIDDbProxy proxy = new UserGUIDDbProxy();
	        UserGUIDDbResponse dbResponse = (UserGUIDDbResponse)proxy.processRequest(dbRequest);
	        
	        processDbRequest(response, dbResponse);
	        
			response.setMcxGuid(dbResponse.getMcxGuid());
			
		} catch (DBException dbe) {
		  throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());
		} catch (BuilderException ex) {
			throw new BusinessException(
					"UserGUIDSignonDelegate",
					"Error occured building database request.");
		}         
		
		return response;
	}
	
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate#processDbRequest(com.dtcc.dnv.otc.common.layers.IServiceResponse, com.dtcc.dnv.otc.common.layers.IDbResponse)
	 */
	public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse)
	  throws BusinessException {
  	  processSPReturnCode(serviceResponse, dbResponse);
    }

}
