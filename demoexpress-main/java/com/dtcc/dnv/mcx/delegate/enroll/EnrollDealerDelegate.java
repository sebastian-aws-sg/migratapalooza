package com.dtcc.dnv.mcx.delegate.enroll;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.dtcc.dnv.mcx.beans.Enrollment;
import com.dtcc.dnv.mcx.dbhelper.enroll.EnrollDealerDbRequest;
import com.dtcc.dnv.mcx.dbhelper.enroll.EnrollDealerDbResponse;
import com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate;
import com.dtcc.dnv.mcx.proxy.enroll.EnrollDealerProxy;
import com.dtcc.dnv.mcx.util.Email;
import com.dtcc.dnv.mcx.util.EmailUtil;
import com.dtcc.dnv.mcx.util.MCXConstants;
import com.dtcc.dnv.mcx.util.MessageLogger;
import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.exception.DBException;
import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.layers.IDbResponse;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.layers.IServiceResponse;

/**
 * This class is used as a delegate to enroll dealer with couterparty
 * 
 * RTM Reference : 3.3.3.1 To create an enrolment request, actor is directed to
 * the enrolment list of dealers, selection of MCAs throuhg enrolment grid and
 * the enrolment confirmation screen RTM Reference : 3.3.3.2 Actor chooses to
 * select a another MCA for enrolment after the previous MCA is approved by
 * dealer
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
 * @author Prabhu K
 * @date Sep 3, 2007
 * @version 1.0
 * 
 *  
 */
public class EnrollDealerDelegate extends MCXAbstractBusinessDelegate
{

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.otc.common.layers.IBusinessDelegate#processRequest(com.dtcc.dnv.otc.common.layers.IServiceRequest)
     */
    public IServiceResponse processRequest(IServiceRequest request) throws BusinessException, UserException
    {
    	MessageLogger log = MessageLogger.getMessageLogger(EnrollDealerDelegate.class.getName());
      //  Logger log = Logger.getLogger(DealerListDelegate.class);
        EnrollDealerServiceResponse serviceResponse = new EnrollDealerServiceResponse();
        EnrollDealerServiceRequest serviceRequest = (EnrollDealerServiceRequest) request;
        EnrollDealerDbResponse dbResponse = null;
        final String REQUEST_ID = "DPMXENRL";
        try
        {
            EnrollDealerDbRequest dbRequest = new EnrollDealerDbRequest(REQUEST_ID, serviceRequest.getAuditInfo());
            prepareDealerMCAList(serviceRequest, serviceResponse, dbRequest, dbResponse);

        } catch (DBException dbe)
        {
            log.error(" DBException in EnrollDealerDelegate.processRequest() method ", dbe);
            throw new BusinessException(dbe.getSqlCode(), dbe.getSqleMessage());

        } catch (Exception e)
        {
            log.error(" Exception in EnrollDealerDelegate.processRequest() method ", e);
            throw new BusinessException("EnrollDealerDelegate", "Error occurred building database request.");
        }
        return serviceResponse;
    }

    /**
     * This method is used to send a mail for the Enrollmen process 
     * 
     * @param serviceRequest
     */
    private void sendMail(String toCompanyID , String ccCompanyID)
    {
        // Getting toAddress & ccAddress for the given company id
        String[] toAddress = EmailUtil.getCompanyContactEmails(toCompanyID); 
        String[] ccAddress = EmailUtil.getCompanyContactEmails(ccCompanyID); 

        // Sending an Email using to & cc
        Email.sendMail(MCXConstants.MCX_EMAIL_APPROVALREQ_SUBJ_KEY,MCXConstants.MCX_EMAIL_APPROVALREQ_FILE_KEY,toAddress, ccAddress, null, MCXConstants.MIME_HTML); 
        
    }
    /**
     * This method is used to get all the dealers and selected mcas and call the
     * proxy for each combination
     * 
     * @param serviceRequest
     * @param dbRequest
     */
    private void prepareDealerMCAList(EnrollDealerServiceRequest serviceRequest , EnrollDealerServiceResponse serviceResponse , EnrollDealerDbRequest dbRequest, EnrollDealerDbResponse dbResponse) throws BusinessException, DBException, UserException
    {
        final String STATUS_IND = "P";
        final String TEMP_ID_DELIM = "~";

        EnrollDealerProxy dbProxy = new EnrollDealerProxy();
        Enrollment enrollment = serviceRequest.getEnrollmentBean();
        String[] selectedDealers = enrollment.getSelectedDealers();
        String selectedMCAs[] = enrollment.getMcaIds();

        dbRequest.setCompanyID(serviceRequest.getCompanyID());
        dbRequest.setUserID(serviceRequest.getUserID());
        dbRequest.setEnrollTimeStamp(new Timestamp(System.currentTimeMillis()).toString());
        dbRequest.setEnrollStatus(STATUS_IND);

        // For each dealer level and for each selected mcas the proxy will be
        // called to update the data base
        for (int index = 0; index < selectedMCAs.length; index++)
        {
            String dealerCode = selectedDealers[index];
            dbRequest.setDealerCode(selectedDealers[index]);
            StringTokenizer mcaTmpltID = new StringTokenizer(selectedMCAs[index], TEMP_ID_DELIM);
            ArrayList tmpltList = new ArrayList();

            while (mcaTmpltID.hasMoreElements())
            {
                String templateID = mcaTmpltID.nextToken();
                if (!templateID.equals(""))
                {
                    dbRequest.setMcaTemplateID(templateID);
                    dbResponse = (EnrollDealerDbResponse) dbProxy.processRequest(dbRequest);
                    processDbRequest(serviceResponse,dbResponse);
                }
                
                if(serviceResponse.hasError())
                {
                    break;
                }
            }
            if(serviceResponse.hasError())
            {
                break;
            }
            sendMail(serviceRequest.getCompanyID() , dealerCode);
        }

    }

    /* (non-Javadoc)
     * @see com.dtcc.dnv.mcx.delegate.MCXAbstractBusinessDelegate#processDbRequest(com.dtcc.dnv.otc.common.layers.IServiceResponse, com.dtcc.dnv.otc.common.layers.IDbResponse)
     */
    public void processDbRequest(IServiceResponse serviceResponse, IDbResponse dbResponse) throws BusinessException
    {
        processSPReturnCode(serviceResponse,dbResponse);
    }

}
