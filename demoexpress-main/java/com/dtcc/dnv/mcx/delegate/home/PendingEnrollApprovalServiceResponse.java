/*
 * Created on Sep 3, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dtcc.dnv.mcx.delegate.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dtcc.dnv.mcx.delegate.MCXAbstractServiceResponse;

/**
 * Service request class for getting the data for loading the Pending Enrollments Approval
 * and Approved Firms Screen
 * RTM Reference : 3.3.11.1 The actor views the pending Enrolment approval tab for the respective MCA types against the clients awaiting approval, approves a client and all the MCAs requested for by him
 * RTM Reference : 3.3.11.2 Actor selects to approve only one or more but not all of the MCA Enrolment requests for the client  
 * RTM Reference : 3.3.11.7 A Dealer should only be able to approve or deny Enrolment of a Client that has initiated firm Enrolment
 * RTM Reference : 3.3.11.12 Once a Client firm is approved with at least one associated MCA(s), that Client should never be removed from the Dealer’s ‘Approved Firms’ tab
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
 * @author Vigneshwari R
 * @date Sep 7, 2007
 * @version 1.0
 * 
 *  
 */
public class PendingEnrollApprovalServiceResponse extends MCXAbstractServiceResponse
{
    private List dealerCodeList = new ArrayList();

    private List dealerNameList = new ArrayList();

    private List dealerUsrList = new ArrayList();

    private List reqDateList = new ArrayList();

    private Map mcaDetailsMap = new HashMap();
    
    private String status = "";

    /**
     * @return Returns the dealerDetailsList.
     */
    public List getDealerDetailsList()
    {
        return dealerDetailsList;
    }

    /**
     * @param dealerDetailsList
     *            The dealerDetailsList to set.
     */
    public void setDealerDetailsList(List dealerDetailsList)
    {
        this.dealerDetailsList = dealerDetailsList;
    }

    private List dealerDetailsList = new ArrayList();

    /**
     * @return
     */
    public List getDealerCodeList()
    {
        return dealerCodeList;
    }

    /**
     * @return
     */
    public List getDealerNameList()
    {
        return dealerNameList;
    }

    /**
     * @return
     */
    public List getDealerUsrList()
    {
        return dealerUsrList;
    }

    /**
     * @return
     */
    public Map getMcaDetailsMap()
    {
        return mcaDetailsMap;
    }

    /**
     * @param list
     */
    public void setDealerCodeList(List list)
    {
        dealerCodeList = list;
    }

    /**
     * @param list
     */
    public void setDealerNameList(List list)
    {
        dealerNameList = list;
    }

    /**
     * @param list
     */
    public void setDealerUsrList(List list)
    {
        dealerUsrList = list;
    }

    /**
     * @param map
     */
    public void setMcaDetailsMap(Map map)
    {
        mcaDetailsMap = map;
    }

    /**
     * @return
     */
    public List getReqDateList()
    {
        return reqDateList;
    }

    /**
     * @param list
     */
    public void setReqDateList(List list)
    {
        reqDateList = list;
    }

    /**
     * @return Returns the status.
     */
    public String getStatus()
    {
        return status;
    }
    /**
     * @param status The status to set.
     */
    public void setStatus(String status)
    {
        this.status = status;
    }
}
