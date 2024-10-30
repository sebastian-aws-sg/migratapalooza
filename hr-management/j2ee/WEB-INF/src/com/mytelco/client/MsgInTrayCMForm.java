/*
 * Created on Aug 18, 2005
 *
 *
 */
package com.mytelco.client;

import java.util.Date;

import org.apache.struts.action.ActionForm;

import com.mytelco.common.EmployeeData;

/**
 *
 * @author myTelco
 * @version 1.0
 */
public class MsgInTrayCMForm extends ActionForm implements java.io.Serializable {

    private String empNo;
    private Date receivedDate;
    private String source;
    private String subject;
    private String noteText;

    private String logout = "false";


    /** Creates new form_logon */
    public MsgInTrayCMForm() {
    }


    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getLogout() {
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}

}

