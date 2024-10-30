/*
 * Created on Aug 18, 2005
 *
 *
 */
package com.mytelco.client;

import org.apache.struts.action.ActionForm;

import com.mytelco.common.EmployeeData;

/**
 *
 * @author myTelco
 * @version 1.0
 */
public class DeptDetailCMForm extends ActionForm implements java.io.Serializable {

    private String deptNo;
    private String location;
    private String ADMRdept;
    private String deptName;
    private String managerNo;

    private String logout = "false";


    /** Creates new form_logon */
    public DeptDetailCMForm() {
    }


    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getManagerNo() {
        return managerNo;
    }

    public void setManagerNo(String managerNo) {
        this.managerNo = managerNo;
    }

    public String getADMRDept() {
        return ADMRdept;
    }

    public void setADMRDept(String ADMRdept) {
        this.ADMRdept = ADMRdept;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLogout() {
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}

}

