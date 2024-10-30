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
public class EmpPrjActCMForm extends ActionForm implements java.io.Serializable {

	private String empNo;
	private String projNo;
	private String actNo;
	private double empTime;
	private Date eStartDate;
	private Date eEndDate;
	
   private String logout = "false";


    /** Creates new form_logon */
    public EmpPrjActCMForm() {
    }


    public String getProjNo() {
        return projNo;
    }

    public void setProjNo(String projNo) {
        this.projNo = projNo;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getActNo() {
        return actNo;
    }

    public void setActNo(String actNo) {
        this.actNo = actNo;
    }

    public double getEmpTime() {
        return empTime;
    }

    public void setEmpTime(double empTime) {
        this.empTime = empTime;
    }

    public Date getEStartDate() {
        return eStartDate;
    }

    public void setEStartDate(Date eStartDate) {
        this.eStartDate = eStartDate;
    }

    public Date getEEndDate() {
        return eEndDate;
    }

    public void setEEndDate(Date eEndDate) {
        this.eEndDate = eEndDate;
    }

	public String getLogout() {
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}

}

