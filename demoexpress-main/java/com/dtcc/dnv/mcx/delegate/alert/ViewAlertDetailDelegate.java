
package com.dtcc.dnv.mcx.delegate.alert;

import com.dtcc.dnv.mcx.dbhelper.alert.AlertDbRequest;
import com.dtcc.dnv.mcx.dbhelper.alert.AlertDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.proxy.alert.ViewAlertDetailProxy;
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
 * getting the alert details.
 *
 *
 */
public class ViewAlertDetailDelegate extends MCXAbstractBusinessDelegate
{

	private final static MessageLogger log = MessageLogger.getMessageLogger(ViewAlertDetailDelegate.class.getName());
    /*
     * (non-Javadoc)
     *
     * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
     */
    public IServiceResponse processRequest(IServiceRequest request) throws BusinessException, UserException
    {
        log.debug("inside view alert detail delegate");
        AlertServiceRequest serviceRequest = (AlertServiceRequest) request;
        AlertServiceResponse serviceResponse = new AlertServiceResponse();
        AlertDbResponse dbResponse = new AlertDbResponse();
        try
        {

        	AlertDbRequest dbRequest = new AlertDbRequest("DPMXAADL",serviceRequest.getAuditInfo());
            dbRequest.setTransaction(serviceRequest.getTransaction());

            //calling proxy's processrequest method.
            ViewAlertDetailProxy proxy = new ViewAlertDetailProxy();
            dbResponse = (AlertDbResponse)proxy.processRequest(dbRequest);
			processDbRequest(serviceResponse,dbResponse);
            serviceResponse.setAlertStatus(dbResponse.getAlertStatus());

            serviceResponse.setTransaction(dbResponse.getTransaction());
        } catch (DBException dbe)
        {
            log.error("DB exception in view alert detail delegate: SPRC Code is "+dbe.getSPRC());
            log.error(dbe);

        } catch (Exception e)
        {
            log.error("general exception in view alert detail delegate :", e);
            log.error(e);
        }
        return serviceResponse;
    }
    
	 public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse)
	   throws BusinessException
	   {
	 	processSPReturnCode(serviceResponse,dbResponse);
	   }    
}
