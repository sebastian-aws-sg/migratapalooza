/*
 * Created on Oct 24, 2005
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
public class DeveloperData {

    private String empNo;
    private String firstName;
    private String lastName;
	
    private String developerId;

    private String hardwareId;

    private int skillLevel;

    private int salary;

    private int experience;

    private String mouseId;

    private String keyboardId;

    private String cpuId;

    private String monitorId;

    private int mouseInsValue;

    private int keyboardInsValue;

    private int cpuInsValue;
    
    private int monitorInsValue;
    
    
    public void DeveloperData(String theFirstName, String theLastName, int theExperience) {
        this.firstName = theFirstName;
        this.lastName = theLastName;
        this.experience = theExperience;
        this.empNo = "0";
    }
    
     
    public int getSalary() {
        return salary;
    }
    public void setSalary() {
        int week = 7;
        switch (skillLevel) {
            case 1:  
            	if (this.experience < 3) this.salary=1;
            	else this.salary=0;
            	break;
            case 2:              	
            	if (this.experience < 3) this.salary=2;
            	else this.salary=1;
            	break;
            case 3:             	
            	if (this.experience < 3) this.salary=3;
            	else this.salary=2;
            	break;
            case 4:             	
            	if (this.experience < 3) this.salary=4;
            	else this.salary=3;
            	break;          
        }
    }
    
    
    public int getSkillLevel() {
        return skillLevel;
    }
    public void setskillLevel(int SkillLevel) {
         this.skillLevel = SkillLevel;
    }
    
    
    public int isExperiencedDeveloper() {
         if (this.experience > 3) return 1;
         else return 0;         
    }
    public int getComputerInsuredValue() {
    	return  (monitorInsValue 
    		+ mouseInsValue 
    		+ keyboardInsValue 
    		+ cpuInsValue);
    }
    public void assignComputer(String theCPUSN, String theMonitorSN, String theMouseSN, String theKeyboardSN) {
    	     this.monitorId = theMonitorSN;
    	     this.mouseId = theMouseSN;
    	     this.keyboardId = theKeyboardSN;
    	     this.cpuId = theCPUSN;
    }
    public void setComputerInsuredValue(int theCPUInsuredValue, int theMonitorInsuredValue, int theMouseInsuredValue, int theKeyboardInsuredValue) {
    	     this.monitorInsValue = theMonitorInsuredValue;
    	     this.mouseInsValue = theMouseInsuredValue;
    	     this.keyboardInsValue = theKeyboardInsuredValue;
    	     this.cpuInsValue = theCPUInsuredValue;
    }

    
    public String getdeveloperId() {
        return developerId;
    }
    public void setdeveloperId(String developerId) {
         this.developerId = developerId;
    }


    public String gethardwareId() {
        return hardwareId;
    }
    public void sethardwareId(String hardwareId) {
         this.hardwareId = hardwareId;
    }
    
}