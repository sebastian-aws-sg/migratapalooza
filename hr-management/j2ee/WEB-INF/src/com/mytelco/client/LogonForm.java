/*
 * Created on Aug 18, 2005
 *
 *  
 */
package com.mytelco.client;

import org.apache.struts.action.ActionForm;

/**
 * 
 * @author myTelco
 * @version 2.0.1
 */
public class LogonForm extends ActionForm implements java.io.Serializable {

    private String userId;

    private String password = null;

    /** Creates new form_logon */
    public LogonForm() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

