/*
 * Created on Oct 23, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.delegate.home;


import com.dtcc.dnv.mcx.beans.UserDetails;
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
 * @author Nithya R
 * @date Oct 23, 2007
 * @version 1.0
 * 
 * 
 * ServiceResponse class for fetching the user details. 
 */

public class UserDetailsServiceResponse extends MCXAbstractServiceResponse
{
    private UserDetails userDetails;
    private String firmName;
    private String userDetailsStatus;

    

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
     * @return Returns the userDetailsStatus.
     */
    public String getUserDetailsStatus()
    {
        return userDetailsStatus;
    }

    /**
     * @param userDetailsStatus The userDetailsStatus to set.
     */
    public void setUserDetailsStatus(String userDetailsStatus)
    {
        this.userDetailsStatus = userDetailsStatus;
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
