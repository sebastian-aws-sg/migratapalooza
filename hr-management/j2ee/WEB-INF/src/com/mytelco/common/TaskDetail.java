/*
 * Created on Aug 18, 2005
 *
 *  
 */
package com.mytelco.common;

import java.util.Date;

/**
 * @author myTelco 
 *
 *  This class is being used for holding the data transfer job information
 *  both for transferring employee data as well as project data of a department. 
 *  Both the transfers can be scheduled separately.
 *  
 */
public class TaskDetail {

    private String department;
    private Date scheduleTime;
    private String subsidary;
   
    
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    
    /**
     * @return Returns the scheduleTime.
     */
    public Date getScheduleTime() {
        return scheduleTime;
    }
    /**
     * @param scheduleTime The scheduleTime to set.
     */
    public void setScheduleTime(Date scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getSubsidary() {
        return subsidary;
    }

    public void setSubsidary(String subsidary) {
        this.subsidary = subsidary;
    }
}