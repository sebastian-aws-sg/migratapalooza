
package com.dtcc.dnv.mcx.dbhelper.alert;
import java.util.ArrayList;
import java.util.List;

import com.dtcc.dnv.mcx.beans.AlertInfo;
import com.dtcc.dnv.mcx.dbhelper.MCXAbstractDbResponse;
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
 * @date Oct 01, 2007
 * @version 1.0
 * 
 * This class is used as db helper to alert response.
 *  
 */
public class AlertDbResponse  extends MCXAbstractDbResponse {
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
     * @param theinfo add alertinfo to response alert list
     */
    public void addAlert(AlertInfo theinfo)
    {
    	alertlist.add(theinfo);
    }
	
    /**
     * @return alert list
     */
    public List getAlertList()
    {
    	return alertlist;
    }
    

}
