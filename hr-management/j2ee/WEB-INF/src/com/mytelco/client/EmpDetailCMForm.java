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
public class EmpDetailCMForm extends ActionForm implements java.io.Serializable {

    private String empNo;

    private EmployeeData employeeData;

    private String logout = "false";


    /** Creates new form_logon */
    public EmpDetailCMForm() {
    }


    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public EmployeeData getEmployeeData() {
        return employeeData;
    }

    public void setEmployeeData(EmployeeData employeeData) {
        this.employeeData = employeeData;
    }

	public String getLogout() {
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}

}

