package com.dtcc.dnv.mcx.formatters;

import com.dtcc.dnv.mcx.beans.AlertInfo;
import com.dtcc.dnv.mcx.beans.CategoryBean;
import com.dtcc.dnv.mcx.beans.CompanyBean;
import com.dtcc.dnv.mcx.beans.DealerList;
import com.dtcc.dnv.mcx.beans.TemplateDocsBean;
import com.dtcc.dnv.mcx.beans.TermBean;
import com.dtcc.dnv.mcx.beans.UserDetails;
import com.dtcc.dnv.otc.common.exception.BusinessException;

/*
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights
 * reserved.
 * 
 * Depository Trust & Clearing Corporation (DTCC) 55, Water Street, New York, NY
 * 10048 U.S.A All Rights Reserved.
 * 
 * This software may contain (in part or full) confidential/proprietary
 * information of DTCC. ("Confidential Information"). Disclosure of such
 * Confidential Information is prohibited and should be used only for its
 * intended purpose in accordance with rules and regulations of DTCC.
 * 
 * @author Kevin Lake
 * 
 * @version 1.0
 *  
 */

public class TransactionBeanFormatter implements IFormatter
{

    /*
     * (non-Javadoc)
     * 
     * @see com.dtcc.dnv.dsv.formatters.IFormatter#format(com.dtcc.dnv.dsv.formatters.FormatRequest)
     */
    public void format(FormatRequest request) throws BusinessException
    {

        if (request.getFormatType() == FORMAT_TYPE_OUTPUT)
        {
            if (request.getTransaction() instanceof AlertInfo)
            {
                validateAlertInfo(request);
            }
            if (request.getTransaction() instanceof TemplateDocsBean)
            {
                formatCmpnyNames(request);
            }
            if(request.getTransaction() instanceof CategoryBean)
            {
                categyNmLineWrap(request);
            }
            if(request.getTransaction() instanceof DealerList)
            {
                formatDealerName(request);
            }
            if(request.getTransaction() instanceof CompanyBean)
            {
                formatCompanyName(request);
            }
            if(request.getTransaction() instanceof UserDetails)
            {
                formatFirmName(request);
            }
            if(request.getTransaction() instanceof TermBean)
            {
            	formatTermImage(request);
            }

        } else if (request.getFormatType() == FORMAT_TYPE_INPUT)
        {
            if (request.getTransaction() instanceof TermBean)
            {
                formatTermVal(request);
            }
        }
    }

    /**
     * This method is used to format the alert information
     * 
     * @param request
     * @throws BusinessException
     */
    private void validateAlertInfo(FormatRequest request) throws BusinessException
    {
        AlertInfo alertInfo = (AlertInfo) request.getTransaction();
        alertInfo.setUpdatetimestamp(FormatterUtils.formatOutputDate(alertInfo.getUpdatetimestamp(), "yyyy-MM-dd HH:mm:ss", "MM/dd/yyyy HH:mm:ss zzz"));
        alertInfo.setUpdatedDate(FormatterUtils.getDate(alertInfo.getUpdatetimestamp(),"MM/dd/yyyy HH:mm:ss zzz"));
    }
    
    /**
     * This method is used to format the company names
     * 
     * @param request
     * @throws BusinessException
     */
    private void formatCmpnyNames(FormatRequest request) throws BusinessException
    {
        TemplateDocsBean dealerClient = (TemplateDocsBean) request.getTransaction();
        dealerClient.setOrgCltNm(FormatterUtils.formatName(dealerClient.getOrgCltNm()));
    }
    /**
     * This method is used to find the no of lines the category will get wrapped
     * 
     * @param request
     * @throws BusinessException
     */
    private void categyNmLineWrap(FormatRequest request) throws BusinessException
    {
        CategoryBean categyBean = (CategoryBean) request.getTransaction();
        categyBean.setCatgyLineWrap(2);        
    }
    /**
     * This method is used to disable the hyperlink in the term value
     * 
     * @param request
     * @throws BusinessException
     */
    private void formatTermVal(FormatRequest request) throws BusinessException
    {
        TermBean termBean = (TermBean) request.getTransaction();
        termBean.setTermVal(FormatterUtils.formatTermValue(termBean.getTermVal()));
    }
    
    /**
     * This method is used to format the dealer names 
     * 
     * @param request
     * @throws BusinessException
     */
    private void formatDealerName(FormatRequest request) throws BusinessException
    {
        DealerList dealerList = (DealerList) request.getTransaction();
        dealerList.setDealerName(FormatterUtils.formatName(dealerList.getDealerName()));        
    }
     
    /**
     * This method is used to format the company names
     * 
     * @param request
     * @throws BusinessException
     */
    private void formatCompanyName(FormatRequest request)throws BusinessException 
    {
        CompanyBean company = (CompanyBean) request.getTransaction();
        company.setCompanyName(FormatterUtils.formatName(company.getCompanyName()));        
    }
    
    /**
     * This method is used to format the firm names
     * 
     * @param request
     * @throws BusinessException
     */
    private void formatFirmName(FormatRequest request) throws BusinessException
    {
        UserDetails userDetails = (UserDetails)request.getTransaction();
        userDetails.setFirmName(FormatterUtils.formatName(userDetails.getFirmName()));
    }
    /**
     * This method is used to modify the Image source of the Term Value
     * 
     * @param request
     * @throws BusinessException
     */
    private void formatTermImage(FormatRequest request)throws BusinessException 
    {
        TermBean termBean = (TermBean) request.getTransaction();
        FormatterUtils.modifyImageSource(termBean);      
    }
}
