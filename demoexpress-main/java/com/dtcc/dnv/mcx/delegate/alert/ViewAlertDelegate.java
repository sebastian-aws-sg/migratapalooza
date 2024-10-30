
package com.dtcc.dnv.mcx.delegate.alert;

import java.util.List;

import com.dtcc.dnv.mcx.beans.AlertInfo;
import com.dtcc.dnv.mcx.dbhelper.alert.AlertDbRequest;
import com.dtcc.dnv.mcx.dbhelper.alert.AlertDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.formatters.FormatterDelegate;
import com.dtcc.dnv.mcx.formatters.IFormatter;
import com.dtcc.dnv.mcx.proxy.alert.ViewAlertProxy;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
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
 * @author pzhou
 * @date Oct 1, 2007
 * @version 1.0
 *
 * This class is used to delegate the service request to the db request for
 * getting the alert list.
 *
 *
 */
public class ViewAlertDelegate extends MCXAbstractBusinessDelegate
{
	private final static MessageLogger log = MessageLogger.getMessageLogger(ViewAlertDelegate.class.getName());
    /*
     * (non-Javadoc)
     *
     * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
     */
    public IServiceResponse processRequest(IServiceRequest request) throws BusinessException, UserException
    {
        log.debug("inside view alert delegate");
        AlertServiceRequest serviceRequest = (AlertServiceRequest) request;
        AlertServiceResponse serviceResponse = new AlertServiceResponse();
        AlertDbResponse dbResponse = new AlertDbResponse();
        	try {
				AlertDbRequest dbRequest = new AlertDbRequest("DPMXAVAL", serviceRequest.getAuditInfo());
				dbRequest.setTransaction(serviceRequest.getTransaction());

				//calling proxy's processrequest method.
				ViewAlertProxy proxy = new ViewAlertProxy();
				dbResponse = (AlertDbResponse)proxy.processRequest(dbRequest);
				processDbRequest(serviceResponse,dbResponse);
				serviceResponse.setAlertStatus(dbResponse.getAlertStatus());
				serviceResponse.setAlertList(dbResponse.getAlertList());
	            List alertList = serviceResponse.getAlertList();
	            for (int i = 0; i < alertList.size(); i++)
	            {
	                AlertInfo alertInfo = (AlertInfo) alertList.get(i);
	                FormatterDelegate.format(alertInfo, null, IFormatter.FORMAT_TYPE_OUTPUT);
	            }
			} 
		    catch (DBException dbe) {
	            log.error(dbe);
	            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());
			}

        return serviceResponse;
    }

	 public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse)
	   throws BusinessException
	   {
	 	processSPReturnCode(serviceResponse,dbResponse);
	   }
    
}
