
package com.dtcc.dnv.mcx.delegate.alert;
import java.util.ArrayList;
import java.util.List;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceResponse;

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
 * @date Oct 8, 2007
 * @version 1.0
 *
 * This class is used to provide service response to delegate for
 * posting alert.
 *
 *
 */
public class AlertServiceResponse extends MCXAbstractServiceResponse {
	private String alertstatus;
	
    /**
     * @param s  alert status success for success status
     */
    public void setAlertStatus(String s)
    {
    	alertstatus = s;
    }
    /**
     * @return alert status
     */
    public String getAlertStatus()
    {
    	return alertstatus;
    }
    
    List alertlist = new ArrayList();
    
	
    /**
     * @return alert list
     */
    public List getAlertList()
    {
    	return alertlist;
    }
    
    /**
     * @param al alert list
     */
    public void setAlertList(List al)
    {
    	alertlist = al;
    }


}
