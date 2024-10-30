/*
 * Created on Aug 18, 2005
 *
 *
 */
package com.mytelco.client;

import java.util.Date;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author myTelco
 * @version 1.0
 */
public class ProjectCMForm extends ActionForm implements java.io.Serializable {

	private String projNo;
	private String projName;
	private String deptNo;
	private String projEmp;
	private double projStaff;
	private Date projStartDate;
	private Date projEndDate;
	private String ctrlProject;
	
   private String logout = "false";


    /** Creates new form_logon */
    public ProjectCMForm() {
    }


    public String getProjNo() {
        return projNo;
    }

    public void setProjNo(String projNo) {
        this.projNo = projNo;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getProjEmp() {
        return projEmp;
    }

    public void setProjEmp(String projEmp) {
        this.projEmp = projEmp;
    }

    public double getProjStaff() {
        return projStaff;
    }

    public void setProjStaff(double projStaff) {
        this.projStaff = projStaff;
    }

    public Date getProjStartDate() {
        return projStartDate;
    }

    public void setProjStartDate(Date projStartDate) {
        this.projStartDate = projStartDate;
    }

    public Date getProjEndDate() {
        return projEndDate;
    }

    public void setProjEndDate(Date projEndDate) {
        this.projEndDate = projEndDate;
    }

    public String getCtrlProject() {
        return ctrlProject;
    }

    public void setCtrlProject(String ctrlProject) {
        this.ctrlProject = ctrlProject;
    }


	public String getLogout() {
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}

}

