/*
 * Created on Oct 17, 2005
 *
 *  
 */
package com.mytelco.common;

import java.util.Date;

/**
 * @author myTelco 
 * 
 * 
 */
public class ActivityData {

    private Float actNo;

    private String actKeyword;

    private String actDesc;

    /**
     * @return Returns the Activity Number.
     */
    public Float getactNo() {
        return actNo;
    }
    /**
     * @param empNo
     *            The Activity Number to set.
     */
    public void setactNo(Float actNo) {
        this.actNo = actNo;
    }

    
    public String getactKeyword() {
        return actKeyword;
    }
    public void setactKeyword(String actKeyword) {
        this.actKeyword = actKeyword;
    }

    public String getactDesc() {
        return actDesc;
    }
    public void setactDesc(String actDesc) {
        this.actDesc = actDesc;
    }
    
}