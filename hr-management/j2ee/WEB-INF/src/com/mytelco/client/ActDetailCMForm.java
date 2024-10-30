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
public class ActDetailCMForm extends ActionForm implements java.io.Serializable {

    private float actNo;
    private String actKeyword;
    private String actDescription;

    private String logout = "false";


    /** Creates new form_logon */
    public ActDetailCMForm() {
    }


    public float getActNo() {
        return actNo;
    }

    public void setActNo(float actNo) {
        this.actNo = actNo;
    }

    public String getActKeyword() {
        return actKeyword;
    }

    public void setActKeyword(String actKeyword) {
        this.actKeyword = actKeyword;
    }

    public String getActDescription() {
        return actDescription;
    }

    public void setActDescription(String actDescription) {
        this.actDescription = actDescription;
    }

    public String getLogout() {
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}

}

