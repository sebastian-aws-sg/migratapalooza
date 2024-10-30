/*
 * Created on Oct 23, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.form;


import com.dtcc.dnv.mcx.beans.UserDetails;

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
 * @date Oct 23, 2007
 * @version 1.0
 * 
 * Form that holds the input values for the user details popup page.
 * 
 */

public class UserDetailsForm extends MCXActionForm
{
    private String userId;
    private UserDetails userDetails;
    private String firmName;

    

    /**
     * @return Returns the userId.
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     * @param userId The userId to set.
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    /**
     * @return Returns the firmName.
     */
    public String getFirmName()
    {
        return firmName;
    }

    /**
     * @param firmName The firmName to set.
     */
    public void setFirmName(String firmName)
    {
        this.firmName = firmName;
    }
    /**
     * @return Returns the userDetails.
     */
    public UserDetails getUserDetails()
    {
        return userDetails;
    }
    /**
     * @param userDetails The userDetails to set.
     */
    public void setUserDetails(UserDetails userDetails)
    {
        this.userDetails = userDetails;
    }
}
