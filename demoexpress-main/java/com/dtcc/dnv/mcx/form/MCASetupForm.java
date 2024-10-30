package com.dtcc.dnv.mcx.form;

import java.util.ArrayList;
import java.util.List;

/**
 * RTM Reference : 1.21.2
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

public class MCASetupForm extends MCXActionForm
{
    private List mcaTypeList = new ArrayList();
    private int mcaTypeCount = 0;
    private String adminDetailsStatus = "";

    /**
     * @return Returns the mcaTypeCount.
     */
    public int getMcaTypeCount()
    {
        return mcaTypeCount;
    }

    /**
     * @param mcaTypeCount
     *            The mcaTypeCount to set.
     */
    public void setMcaTypeCount(int mcaTypeCount)
    {
        this.mcaTypeCount = mcaTypeCount;
    }

    /**
     * @return Returns the mcaTypeList.
     */
    public List getMcaTypeList()
    {
        return mcaTypeList;
    }

    /**
     * @param mcaTypeList
     *            The mcaTypeList to set.
     */
    public void setMcaTypeList(List mcaTypeList)
    {
        this.mcaTypeList = mcaTypeList;
    }
    /**
     * @return Returns the adminDetailsStatus.
     */
    public String getAdminDetailsStatus()
    {
        return adminDetailsStatus;
    }
    /**
     * @param adminDetailsStatus The adminDetailsStatus to set.
     */
    public void setAdminDetailsStatus(String adminDetailsStatus)
    {
        this.adminDetailsStatus = adminDetailsStatus;
    }
}
