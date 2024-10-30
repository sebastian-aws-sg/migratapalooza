/*
 * Created on Sep 12, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.delegate.admin;

import com.dtcc.dnv.mcx.beans.TermBean;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbRequest;
import com.dtcc.dnv.mcx.dbhelper.mca.ModifyTermDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.delegate.mca.ModifyTermServiceRequest;
import com.dtcc.dnv.mcx.delegate.mca.ModifyTermServiceResponse;
import com.dtcc.dnv.mcx.proxy.mca.TermDetailsProxy;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.builders.DbRequestBuilder;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;

/**
* This class is used as a Delegate to View the ISDA Term Details
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
public class ViewISDATermDetailDelegate extends MCXAbstractBusinessDelegate {

	private final static MessageLogger log = MessageLogger.getMessageLogger(ViewISDATermDetailDelegate.class.getName());
	//Logger log = Logger.getLogger(ViewISDATermDetailDelegate.class);
	
	/* (non-Javadoc)
	 * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
	 */
	public IServiceResponse processRequest(IServiceRequest request)
			throws BusinessException, UserException {
		
		final String REQUEST_ID = "ViewISDATermDetailDelegate";
		ModifyTermServiceResponse serviceResponse = new ModifyTermServiceResponse();
		
		ModifyTermServiceRequest serviceRequest = (ModifyTermServiceRequest) request;
		ModifyTermDbResponse dbResponse = null;
		try
		{
			log.debug("1. Inside the ViewISDATermDetailDelegate");
			ModifyTermDbRequest dbRequest = new ModifyTermDbRequest(REQUEST_ID, serviceRequest.getAuditInfo());
			DbRequestBuilder.copyObject(serviceRequest, dbRequest);
			dbRequest.setFunctionalIndicator("A");
			//Locked By Other User will not come to this point
			TermBean tempBean = (TermBean) serviceRequest.getTransaction();
			//log.debug("2. serviceRequest.isDbCallReq() in ViewISDATermDetailDelegate is >>>"+ serviceRequest.isDbCallReq());
			if(serviceRequest.isDbCallReq()) {
				TermDetailsProxy fetchTermProxy = new TermDetailsProxy();
				log.debug("3. Before the Proxy call in ViewISDATermDetailDelegate >>>");
				dbResponse = (ModifyTermDbResponse)fetchTermProxy.processRequest(dbRequest);
				serviceResponse.setTransaction(dbResponse.getTransaction());
			}
		} catch (DBException dbe)
        {
		    //log.debug("4. The DB Exception in ViewISDATermDetailDelegate is >>>" + dbe);
            log.error(" DBException in ViewISDATermDetailDelegate.processRequest() method ", dbe);
            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());

        } catch (Exception e)
        {
            //log.debug("5. The General Exception in ViewISDATermDetailDelegate is >>>" + e);
            log.error(" Exception in ViewISDATermDetailDelegate.processRequest() method ", e);
            throw new BusinessException("ViewAdminMCASetupDelegate", "Error occurred building database request.");
        }
		return serviceResponse;
	}
	
	/* (non-Javadoc)
     * @see com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate#processDbRequest(com.dtcc.dnv.otc.common.layers.IServiceResponse, com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse) throws BusinessException
    {
        processSPReturnCode(serviceResponse,dbResponse);
        
    }

}
