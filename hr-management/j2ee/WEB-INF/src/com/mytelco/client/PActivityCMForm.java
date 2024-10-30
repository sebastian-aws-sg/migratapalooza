/*
 * Created on Aug 18, 2005
 *
 *
 */
package com.mytelco.client;

import java.util.Date;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author myTelco
 * @version 1.0
 */
public class PActivityCMForm extends ActionForm implements java.io.Serializable {

	private String projNo;
	private Integer actNo;
	private double actStaff;
	private Date actStartDate;
	private Date actEndDate;
	
   private String logout = "false";


    /** Creates new form_logon */
    public PActivityCMForm() {
    }


    public String getProjNo() {
        return projNo;
    }

    public void setProjNo(String projNo) {
        this.projNo = projNo;
    }

    public Integer getactNo() {
        return actNo;
    }

    public void setActNo(Integer actNo) {
        this.actNo = actNo;
    }

    public double getActStaff() {
        return actStaff;
    }

    public void setActStaff(double actStaff) {
        this.actStaff = actStaff;
    }

    public Date getActStartDate() {
        return actStartDate;
    }

    public void setActStartDate(Date actStartDate) {
        this.actStartDate = actStartDate;
    }

    public Date getActEndDate() {
        return actEndDate;
    }

    public void setActEndDate(Date actEndDate) {
        this.actEndDate = actEndDate;
    }

	public String getLogout() {
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}

}

