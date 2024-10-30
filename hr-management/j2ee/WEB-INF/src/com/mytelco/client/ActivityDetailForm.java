/*
 * Created on Aug 18, 2005
 *
 *
 */
package com.mytelco.client;

import org.apache.struts.action.ActionForm;

import com.mytelco.common.ActivityData;

/**
 *
 * @author myTelco
 * @version 1.0
 */
public class ActivityDetailForm extends ActionForm implements java.io.Serializable {

    private Float actNo;

    private String actDesc;

    private ActivityData activityData;

    private String logout = "false";

    /** Creates new form_logon */
    public ActivityDetailForm() {
    }

    /**
     * @param empNo
     *            The Activity Number to set.
     */
    public Float getactNo() {
        return actNo;
    }
 
    public void setactNo(Float actNo) {
        this.actNo = actNo;
    }
    
    public String getactDesc() {
        return actDesc;
    }
    public void setactDesc(String actDesc) {
        this.actDesc = actDesc;
    }
 

    public ActivityData getActivityData() {
        return activityData;
    }


    public void setActivityData(ActivityData activityData) {
        this.activityData = activityData;
    }

	public String getLogout() {
		return logout;
	}




	public void setLogout(String logout) {
		this.logout = logout;
	}
}

