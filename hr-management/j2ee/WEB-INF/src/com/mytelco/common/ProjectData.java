/*
 * Created on Oct 14, 2005
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
public class ProjectData {

    private String projNo;

    private String projName;

    private String deptNo;

    private String projEmp;

    private float projStaff;

    private Date projStartDate;

    private Date projEndDate;

    private String ctrlProj;

    /**
     * @return Returns the Project Number.
     */
    public String getprojNo() {
        return projNo;
    }

    /**
     * @param projNo
     *            The Project Number to set.
     */
    public void setprojNo(String projNo) {
        this.projNo = projNo;
    }

    
    public String getprojName() {
        return projName;
    }

    public void setprojName(String projName) {
        this.projName = projName;
    }

    
    public String getdeptNo() {
        return deptNo;
    }

    public void setdeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    
    public String getprojEmp() {
        return projEmp;
    }

    public void setprojEmp(String projEmp) {
        this.projEmp = projEmp;
    }

    
    public float getprojStaff() {
        return projStaff;
    }

    public void setprojStaff(float projStaff) {
        this.projStaff = projStaff;
    }

    
    public Date getprojStartDate() {
        return projStartDate;
    }

    public void setprojStartDate(Date projStartDate) {
        this.projStartDate = projStartDate;
    }

    
    public Date getprojEndDate() {
        return projEndDate;
    }

    public void setprojEndDate(Date projEndDate) {
        this.projEndDate = projEndDate;
    }

    
    public String getctrlProj() {
        return ctrlProj;
    }

    public void setctrlProj(String ctrlProj) {
        this.ctrlProj = ctrlProj;
    }



}