package com.dtcc.dnv.mcx.beans;

import java.util.Date;

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
 * @date Sep 27, 2007
 * @version 1.0
 * 
 * This class is used to store alert info.
 *  
 */
public class AlertInfo extends ABaseTran
{
    private String subject;
    private String message;
    private String updatetimestamp;
    private String userid;
    private String alertid;
    private String usertype;
    private Date updatedDate;
    private String userName = "";

    /**
     * @return alert id
     */
    public String getAlertid()
    {
        return alertid;
    }

    /**
     * @param id
     *            for alert id
     */
    public void setAlertid(String id)
    {
        alertid = id;
    }

    /**
     * @return subject
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * @param s
     *            set subject
     */
    public void setSubject(String s)
    {
        subject = s;
    }

    /**
     * @return message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @param s
     *            set message
     */
    public void setMessage(String s)
    {
        message = s;
    }

    /**
     * @return update time stamp
     */
    public String getUpdatetimestamp()
    {
        return updatetimestamp;
    }

    /**
     * @param s
     *            set update time stamp
     */
    public void setUpdatetimestamp(String s)
    {
        updatetimestamp = s;
    }

    /**
     * @return user id
     */
    public String getUserid()
    {
        return userid;
    }

    /**
     * @param s
     *            set user id
     */
    public void setUserid(String s)
    {
        userid = s;
    }

    /**
     * @return user type
     */
    public String getUsertype()
    {
        return usertype;
    }

    /**
     * @param type
     *            set user type
     */
    public void setUsertype(String type)
    {
        usertype = type;
    }

    /**
     * @return Returns the updatedDate.
     */
    public Date getUpdatedDate()
    {
        return updatedDate;
    }

    /**
     * @param updatedDate
     *            The updatedDate to set.
     */
    public void setUpdatedDate(Date updatedDate)
    {
        this.updatedDate = updatedDate;
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
