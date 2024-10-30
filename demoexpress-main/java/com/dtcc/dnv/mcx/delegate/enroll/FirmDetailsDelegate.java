/*
 * Created on Aug 29, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dtcc.dnv.mcx.delegate.enroll;

import java.util.ArrayList;
import java.util.List;

import com.dtcc.dnv.mcx.beans.CompanyBean;
import com.dtcc.dnv.mcx.beans.CompanyContactBean;
import com.dtcc.dnv.mcx.beans.FirmDetails;
import com.dtcc.dnv.mcx.company.CompanyUtil;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.formatters.FormatterDelegate;
import com.dtcc.dnv.mcx.formatters.IFormatter;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;
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
 * @author Nithya R
 * @date Sep 4, 2007
 * @version 1.0
 * 
 * This class is used to delegate the servicerequest to the dbrequest for
 * retrieving the firm details.
 * RTM Reference : 3.3.3.26 The Relationship Management and the User management group should provide
 * 						 	the user email IDs and the key contact email IDs of the dealer and the client firm
 */
public class FirmDetailsDelegate extends MCXAbstractBusinessDelegate
{
    private final static MessageLogger log = MessageLogger.getMessageLogger(FirmDetailsDelegate.class.getName());
   

    /*
     * This method is used to process the service request to retrieve the firm details.
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
     */
    public IServiceResponse processRequest(IServiceRequest request) throws BusinessException, UserException
    {

        FirmDetailsServiceResponse serviceResponse = new FirmDetailsServiceResponse();
        FirmDetailsServiceRequest serviceRequest = (FirmDetailsServiceRequest) request;        
        FirmDetails firmDetails = new FirmDetails();
        final String REQUEST_ID = "DPMXHORG";
        try
        {
            log.info("entering firm details delegate");            
            //Retrieving the company details form the company util class
            
            CompanyBean companyBean = CompanyUtil.getCompany(serviceRequest.getId());
            List companyContacts = companyBean.getCompanyContacts();
            List firmDetailsList = new ArrayList();
            for (int size = 0; size < companyContacts.size(); size++)
            {
                if (((CompanyContactBean) companyContacts.get(size)).isPrimary())
                {
                    firmDetails.setPrimaryName(((CompanyContactBean) companyContacts.get(size)).getContactName());
                    firmDetails.setPrimaryEmail(((CompanyContactBean) companyContacts.get(size)).getContactEmail());
                    firmDetails.setPrimaryPhone(((CompanyContactBean) companyContacts.get(size)).getContactPhone());
                } else
                {
                    firmDetails.setSecondaryName(((CompanyContactBean) companyContacts.get(size)).getContactName());
                    firmDetails.setSecondaryEmail(((CompanyContactBean) companyContacts.get(size)).getContactEmail());
                    firmDetails.setSecondaryPhone(((CompanyContactBean) companyContacts.get(size)).getContactPhone());
                }

            }
            firmDetailsList.add(firmDetails);
            /**
             * The firm details are set in the service response.
             */
            serviceResponse.setFirmDetails(firmDetailsList);
            FormatterDelegate.format(companyBean , null , IFormatter.FORMAT_TYPE_OUTPUT);
            serviceResponse.setDealerName(companyBean.getCompanyName());            
            log.info("back to delegate");
        } catch (Exception e)
        {
            log.error(" Exception in FirmdetailsDelegate.processRequest() method ", e);
            throw new BusinessException("FirmdetailsDelegate", "Error occurred building database request.");
        }
        return serviceResponse;
    }


    /* (non-Javadoc)
     * @see com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate#processDbRequest(com.dtcc.dnv.otc.common.layers.IServiceResponse, com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse) throws BusinessException
    {        
        
    }

}
