package com.dtcc.dnv.mcx.delegate.admin;

import java.util.ArrayList;
import java.util.List;

import com.dtcc.dnv.mcx.beans.TemplateBean;
import com.dtcc.dnv.mcx.dbhelper.home.DisplayMCAsDbRequest;
import com.dtcc.dnv.mcx.dbhelper.home.DisplayMCAsDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.proxy.home.DisplayMCAsProxy;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.builders.DbRequestBuilder;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.exception.UserException;

import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;

/**
 * This class is used as to delegate the service request to the proxy class to
 * retrive MCA Type List to load the MCA Setup Posting screen(Admin Module). The
 * Fields of this list are "MCA Type", "status", "Date Posted", "Last Modified By",
 * "Last Modified on" and "MCA Agreement Date".
 * 
 * RTM Reference : 1.22.2 Clicking on MCA Setup or Home, would display the list
 * of MCA's in a summary list. The fields of this list view are “MCA Type”,
 * ”Status”, “Date Posted”, “Last modified by” , “last modified on” and "MCA
 * Agreement Date".
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
 * @author Seeni Mohammad Azharudin S
 * @date Sep 17, 2007
 * @version 1.0
 * 
 *  
 */
public class MCATypeListDelegate extends MCXAbstractBusinessDelegate
{

    private final static MessageLogger log = MessageLogger.getMessageLogger(MCATypeListDelegate.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
     *      This method is used to delegate the service request to the proxy
     *      class to retrieve the mca details for loading the MCA Setup And
     *      Posting screen(Admin Home Page)
     */
    public IServiceResponse processRequest(IServiceRequest request) throws BusinessException, UserException
    {
        final String REQUEST_ID = "DPMXHEXP";
        MCATypeListServiceResponse serviceResponse = new MCATypeListServiceResponse();
        MCATypeListServiceRequest serviceRequest = (MCATypeListServiceRequest) request;
        try
        {
            log.info("inside MCATypeListDelegate");
            DisplayMCAsDbRequest dbRequest = new DisplayMCAsDbRequest(REQUEST_ID, serviceRequest.getAuditInfo());
            DbRequestBuilder.copyObject(serviceRequest, dbRequest);
            DisplayMCAsProxy dbProxy = new DisplayMCAsProxy();
            DisplayMCAsDbResponse dbResponse = (DisplayMCAsDbResponse) dbProxy.processRequest(dbRequest);
            processDbRequest(serviceResponse, dbResponse);

            List mcaTypeList = new ArrayList();
            mcaTypeList = dbResponse.getDealerDetailsList();
            /*
             * The values coming from the Dao as default values are set to null(for eg: MCA Agreement Date
             * and Date Posted will be returned as some default values from the Dao if the status is New
             * or Pending Approval or In-Progress. So those two values are set to null so that in the screen
             * those fields are blank.)
             */
            if (mcaTypeList == null)
            {
                serviceResponse.setMcaTypeList(mcaTypeList);
                serviceResponse.setPendingExecutedStatus(dbResponse.getPendingExecutedStatus());
            } else
            {

                for (int count = 0; count < mcaTypeList.size(); count++)
                {
                    String mcaStatusCD = ((TemplateBean) mcaTypeList.get(count)).getMcaStatusCd();
                    if (MCXConstants.MCA_ADMIN_STATUSCD.equals(mcaStatusCD))
                    {
                        ((TemplateBean) mcaTypeList.get(count)).setPostedDate(null);
                        ((TemplateBean) mcaTypeList.get(count)).setRowUpdtName("");
                        ((TemplateBean) mcaTypeList.get(count)).setModifiedTime(null);
                        ((TemplateBean) mcaTypeList.get(count)).setMcaAgreementDate(null);

                    } else if (mcaStatusCD.equals(MCXConstants.MCA_ADMIN_STATCD) || mcaStatusCD.equals(MCXConstants.MCA_ADMIN_STATUSCODE))
                    {
                        ((TemplateBean) mcaTypeList.get(count)).setPostedDate(null);
                        ((TemplateBean) mcaTypeList.get(count)).setMcaAgreementDate(null);
                    }
                }
                serviceResponse.setMcaTypeList(mcaTypeList); 
                serviceResponse.setPendingExecutedStatus(dbResponse.getPendingExecutedStatus());
            }

        } catch (DBException dbe)
        {
            log.error(" DBException in MCATypeListDelegate.processRequest() method ", dbe);
            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());

        } catch (Exception e)
        {
            log.error(" Exception in MCATypeListDelegate.processRequest() method ", e);
            throw new BusinessException("MCATypeListDelegate", "Error occurred building database request.");
        }
        return serviceResponse;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate#processDbRequest(com.dtcc.dnv.otc.common.layers.IServiceResponse,
     *      com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse) throws BusinessException
    {
        processSPReturnCode(serviceResponse, dbResponse);

    }

}
