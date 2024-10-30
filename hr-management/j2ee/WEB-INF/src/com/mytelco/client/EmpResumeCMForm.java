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
public class EmpResumeCMForm extends ActionForm implements java.io.Serializable {

    private String empNo;
    private Object empResume;
    private String resumeFormat;

    private String logout = "false";


    /** Creates new form_logon */
    public EmpResumeCMForm() {
    }


    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Object getEmpResume() {
        return empResume;
    }

    public void setEmpResumeName(Object empResume) {
        this.empResume = empResume;
    }

    public String getResumeFormat() {
        return resumeFormat;
    }

    public void setResumeFormat(String resumeFormat) {
        this.resumeFormat = resumeFormat;
    }

    public String getLogout() {
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}

}

