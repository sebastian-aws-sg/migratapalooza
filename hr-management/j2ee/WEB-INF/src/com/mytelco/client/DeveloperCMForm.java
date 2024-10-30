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
public class DeveloperCMForm extends ActionForm implements java.io.Serializable {

	private String developerId;
	private String hardwareId;
	private Integer skillLevel;
	private Integer salary;
	private Integer experience;
	
   private String logout = "false";


    /** Creates new form_logon */
    public DeveloperCMForm() {
    }


    public String getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }

    public String getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    public Integer getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(Integer skillLevel) {
        this.skillLevel = skillLevel;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }


	public String getLogout() {
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}
}

