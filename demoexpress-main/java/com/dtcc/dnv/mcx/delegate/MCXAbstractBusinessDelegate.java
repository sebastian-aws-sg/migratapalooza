
package com.dtcc.dnv.mcx.delegate;

import com.dtcc.dnv.mcx.util.MessageResources;

import com.dtcc.dnv.mcx.dbhelper.MCXAbstractDbResponse;
import com.dtcc.dnv.mcx.util.InputReturnCodeMapping;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.layers.AbstractBusinessDelegate;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;

/**
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
 * @author Peng Zhou
 * @date Oct 15, 2007
 * @version 1.0
 * 
 * This class is used to provide constants of return code.
 *  
 */
public abstract class MCXAbstractBusinessDelegate extends AbstractBusinessDelegate{
	private final static MessageLogger log = MessageLogger.getMessageLogger(MCXAbstractBusinessDelegate.class.getName());
	/**
	 * @param serviceResponse
	 * @param dbResponse
	 * @throws BusinessValidationException
	 */
	public final void processSPReturnCode(IServiceResponse serviceResponse, IDbResponse dbResponse) 
	  throws BusinessException {
		
		MCXAbstractDbResponse dbresponse = (MCXAbstractDbResponse)dbResponse;
		MCXAbstractServiceResponse srResponse = (MCXAbstractServiceResponse)serviceResponse;
		
		// Check if error condition occurred
		if(dbresponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP50)) {
			
			// Treat SP50 as standard SP error and throw BusinessException with standard message.
			throw new BusinessException (dbresponse.getSpReturnCode(), MessageResources.getMessage(MCXConstants.GENERAL_BUSINESS_ERROR));

		} else if(!dbresponse.getSpReturnCode().equalsIgnoreCase(InputReturnCodeMapping.SP00)) {
			
			// Get return message from SP
			String responseMessage = dbresponse.getSpResponseMessage().trim();
			
			if(responseMessage.length() > 0) {
				// If SP returns message set service properties for action to handle
				srResponse.setHasError(true);
				srResponse.setSpReturnMessage(responseMessage);
				srResponse.setSpReturnCode(dbresponse.getSpReturnCode());
			} else {
			  /* 
			   * If no SP message is returned, treat it as a standard SP error and throw 
			   * a BusinessException with standard message.
			   */		      
			  responseMessage = MessageResources.getMessage(MCXConstants.GENERAL_BUSINESS_ERROR);
			  throw new BusinessException (dbresponse.getSpReturnCode(), responseMessage);
			}
		}
		
	}	
	/**
	  * @param serviceResponse
	  * @param dbResponse
	  * @throws BusinessException
	  */
	 public abstract void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse)
	   throws BusinessException;	
}
