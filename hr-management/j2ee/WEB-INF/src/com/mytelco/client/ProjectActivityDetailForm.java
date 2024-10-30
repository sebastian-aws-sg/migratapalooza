/*
 * Created on Aug 18, 2005
 *
 *
 */
package com.mytelco.client;

import java.util.Date;

import org.apache.struts.action.ActionForm;

import com.mytelco.common.PActivityData;

/**
 *
 * @author myTelco
 * @version 1.0
 */
public class ProjectActivityDetailForm extends ActionForm implements java.io.Serializable {

	private String projNo;
	
    private Float actNo;
    
    private Float actStaff;

    private Date actStartDate;

    private Date actEndDate;

    private PActivityData pActivityData;

    private String logout = "false";

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
 
    public PActivityData getProjectActivityData() {
        return pActivityData;
    }

    public void setProjectActivityData(PActivityData pActivityData) {
        this.pActivityData = pActivityData;
    }

    public String getLogout() {
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}
}

