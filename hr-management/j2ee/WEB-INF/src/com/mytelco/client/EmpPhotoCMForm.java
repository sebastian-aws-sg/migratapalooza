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
public class EmpPhotoCMForm extends ActionForm implements java.io.Serializable {

    private String empNo;
    private Object empPhoto;
    private String photoFormat;

    private String logout = "false";


    /** Creates new form_logon */
    public EmpPhotoCMForm() {
    }


    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Object getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhotoName(Object empPhoto) {
        this.empPhoto = empPhoto;
    }

    public String getPhotoFormat() {
        return photoFormat;
    }

    public void setPhotoFormat(String photoFormat) {
        this.photoFormat = photoFormat;
    }

    public String getLogout() {
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}

}

