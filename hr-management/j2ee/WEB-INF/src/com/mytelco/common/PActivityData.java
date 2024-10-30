/*
 * Created on Oct 17, 2005
 *
 *  
 */
package com.mytelco.common;

import java.util.Date;

/**
 * @author myTelco 
 * 
 * 
 */
public class PActivityData {

	private String projNo;
	
    private Float actNo;
    
    private Float actStaff;

    private Date actStartDate;

    private Date actEndDate;

    /**
     * @return Returns the Project Number.
     */
    public String getprojNo() {
        return projNo;
    }
    /**
     * @param empNo
     *            The Project Number to set.
     */
    public void setprojNo(String projNo) {
        this.projNo = projNo;
    }

    public Float getactNo() {
        return actNo;
    }
    public void setactNo(Float actNo) {
        this.actNo = actNo;
    }
    
    public Float getactStaff() {
        return actStaff;
    }
    public void setactStaff(Float actStaff) {
        this.actStaff = actStaff;
    }
    
    public Date getactStartDate() {
        return actStartDate;
    }
    public void setactStartDate(Date actStartDate) {
        this.actStartDate = actStartDate;
    }

    public Date getactEndDate() {
        return actEndDate;
    }
    public void setactEndDate(Date actEndDate) {
        this.actEndDate = actEndDate;
    }
    
}