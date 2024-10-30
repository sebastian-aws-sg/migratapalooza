/*
 * Created on Oct 23, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtcc.dnv.mcx.beans;

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
 * The bean class for the user details popup.
 * 
 */

public class UserDetails extends ABaseTran
{
    private String firmName;
    private String userName;
    private String userEmail;
    private String phoneNumber;

    /**
     * @return Returns the firmName.
     */
    public String getFirmName()
    {
        return firmName;
    }

    /**
     * @param firmName
     *            The firmName to set.
     */
    public void setFirmName(String firmName)
    {
        this.firmName = firmName;
    }

    /**
     * @return Returns the phoneNumber.
     */
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * @param phoneNumber
     *            The phoneNumber to set.
     */
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return Returns the userEmail.
     */
    public String getUserEmail()
    {
        return userEmail;
    }

    /**
     * @param userEmail
     *            The userEmail to set.
     */
    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    /**
     * @return Returns the userName.
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * @param userName
     *            The userName to set.
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
}
