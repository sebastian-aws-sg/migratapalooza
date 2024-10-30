package com.dtcc.dnv.mcx.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dtcc.dnv.mcx.beans.PendingEnrollmentsTransaction;

/**
 * This class is the Form class for the Pending Enrollment Approval and Approved
 * Firms screen Copyright © 2003 The Depository Trust & Clearing Company. All
 * rights reserved.
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
public class PendingApprovalForm extends MCXActionForm
{

    public PendingApprovalForm()
    {
        PendingEnrollmentsTransaction pendingEnrollTrans = new PendingEnrollmentsTransaction();
        super.setTransaction(pendingEnrollTrans);
    }

    private List dealerNameList = new ArrayList();

    private List mcaDetailsList = new ArrayList();

    private Map mcaDetailsMap = new HashMap();

    private String status = "";

    private List dealerDetailsList = new ArrayList();

    private String pendingStatus = "";

    private String selectedTab = "";

    private String all = "";

    private String dealer = "";

    private String user = "";

    private String uploadtime = "";
    
    private String mcaDetailsStatus = "";
    
    private int listSize;
    private String printParam = "N";

    /**
     * @return List dealerNameList
     */
    public List getDealerNameList()
    {
        return dealerNameList;
    }

    /**
     * @return Map mcaDetailsMap
     */
    public Map getMcaDetailsMap()
    {
        return mcaDetailsMap;
    }

    /**
     * @param list
     */
    public void setDealerNameList(List list)
    {
        dealerNameList = list;
    }

    /**
     * @param map
     */
    public void setMcaDetailsMap(Map map)
    {
        mcaDetailsMap = map;
    }

    /**
     * @return Returns the mcaDetailsList.
     */
    public List getMcaDetailsList()
    {
        return mcaDetailsList;
    }

    /**
     * @param mcaDetailsList
     *            The mcaDetailsList to set.
     */
    public void setMcaDetailsList(List mcaDetailsList)
    {
        this.mcaDetailsList = mcaDetailsList;
    }

    /**
     * @return Returns the dealetDetailsList.
     */
    public List getDealerDetailsList()
    {
        return dealerDetailsList;
    }

    /**
     * @param dealetDetailsList
     *            The dealetDetailsList to set.
     */
    public void setDealerDetailsList(List dealerDetailsList)
    {
        this.dealerDetailsList = dealerDetailsList;
    }

    /**
     * @return Returns the pendingStatus.
     */
    public String getPendingStatus()
    {
        return pendingStatus;
    }

    /**
     * @param pendingStatus
     *            The pendingStatus to set.
     */
    public void setPendingStatus(String pendingStatus)
    {
        this.pendingStatus = pendingStatus;
    }

    /**
     * @return Returns the selectedTab.
     */
    public String getSelectedTab()
    {
        return selectedTab;
    }

    /**
     * @param selectedTab
     *            The selectedTab to set.
     */
    public void setSelectedTab(String selectedTab)
    {
        this.selectedTab = selectedTab;
    }

    /**
     * @return Returns the all.
     */
    public String getAll()
    {
        return all;
    }

    /**
     * @param all
     *            The all to set.
     */
    public void setAll(String all)
    {
        this.all = all;
    }

    /**
     * @return Returns the status.
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            The status to set.
     */
    public void setStatus(String status)
    {
        this.status = status;
    }

    /**
     * @return Returns the dealer.
     */
    public String getDealer()
    {
        return dealer;
    }

    /**
     * @param dealer
     *            The dealer to set.
     */
    public void setDealer(String dealer)
    {
        this.dealer = dealer;
    }

    /**
     * @return Returns the user.
     */
    public String getUser()
    {
        return user;
    }

    /**
     * @param user
     *            The user to set.
     */
    public void setUser(String user)
    {
        this.user = user;
    }

    /**
     * @return Returns the uploadtime.
     */
    public String getUploadtime()
    {
        return uploadtime;
    }

    /**
     * @param uploadtime
     *            The uploadtime to set.
     */
    public void setUploadtime(String uploadtime)
    {
        this.uploadtime = uploadtime;
    }
    /**
     * @return Returns the mcaDetailsStatus.
     */
    public String getMcaDetailsStatus()
    {
        return mcaDetailsStatus;
    }
    /**
     * @param mcaDetailsStatus The mcaDetailsStatus to set.
     */
    public void setMcaDetailsStatus(String mcaDetailsStatus)
    {
        this.mcaDetailsStatus = mcaDetailsStatus;
    }
    /**
     * @return Returns the listSize.
     */
    public int getListSize()
    {
        return listSize;
    }
    /**
     * @param listSize The listSize to set.
     */
    public void setListSize(int listSize)
    {
        this.listSize = listSize;
    }
    
    /**
     * @return Returns the printParam.
     */
    public String getPrintParam()
    {
        return printParam;
    }
    /**
     * @param printParam The printParam to set.
     */
    public void setPrintParam(String printParam)
    {
        this.printParam = printParam;
    }
}