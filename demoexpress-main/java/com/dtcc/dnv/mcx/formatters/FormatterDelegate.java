package com.dtcc.dnv.mcx.formatters;

import com.dtcc.dnv.otc.common.exception.BusinessException;
import com.dtcc.dnv.otc.common.layers.ITransactionBean;
import com.dtcc.dnv.otc.common.security.model.IUser;

/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights
 * reserved.
 * 
 * Depository Trust & Clearing Corporation (DTCC) 55, Water Street, New York, NY
 * 10048 U.S.A All Rights Reserved.
 * 
 * This software may contain (in part or full) confidential/proprietary
 * information of DTCC. ("Confidential Information"). Disclosure of such
 * Confidential Information is prohibited and should be used only for its
 * intended purpose in accordance with rules and regulations of DTCC. Form bean
 * for a Struts application.
 * 
 * @version 1.0
 * @author Kevin Lake
 *  
 */
public class FormatterDelegate {

    /**
     * @param transaction
     * @param user
     * @param formatType
     * @throws BusinessException
     */
    public static void format(ITransactionBean transaction, IUser user, int formatType) throws BusinessException {
        FormatRequest request = new FormatRequest(transaction, user, formatType);        
		TransactionBeanFormatter transactionBeanFormatter = new TransactionBeanFormatter();
		transactionBeanFormatter.format(request);
    }   
   
}
