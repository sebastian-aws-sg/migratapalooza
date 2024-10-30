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
public class EmpProjActData {

    private String empNo;

    private String projNo;

    private float actNo;

    private float emptime;

    private Date estartdate;

    private Date eenddate;

    /**
     * @return Returns the Employee Number.
     */
    public String getempNo() {
        return empNo;
    }

    /**
     * @param empNo
     *            The Employee Number to set.
     */
    public void setempNo(String empNo) {
        this.empNo = empNo;
    }

    
    public String getprojNo() {
        return projNo;
    }

    public void setprojNo(String projNo) {
        this.projNo = projNo;
    }

    public float getactNo() {
        return actNo;
    }

    public void setactNo(float actNo) {
        this.actNo = actNo;
    }

    public float getemptime() {
        return emptime;
    }

    public void setemptime(float emptime) {
        this.emptime = emptime;
    }
    
    public Date getestartdate() {
        return estartdate;
    }

    public void setestartdate(Date estartdate) {
        this.estartdate = estartdate;
    }

    public Date eenddate() {
        return eenddate;
    }

    public void seteenddate(Date eenddate) {
        this.eenddate = eenddate;
    }


}