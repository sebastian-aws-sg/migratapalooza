package com.mytelco.server;

import javax.ejb.EJBLocalObject;

public interface EmpResumeCMLocalObject extends EJBLocalObject {
	
    // getter and setter methods for Entity fields   
	/**
	  * @return Returns the Employee Resume.
	  */
	public Object getEmployeeResume();
   /**
     * @param EmployeeResume
	  *        
	  */
	public void setEmployeeResume(Object EmployeeResume);
	/**
	  * @return Returns the Resume Format
	  */
	public String getResumeFormat();
	/**
	  * @param ResumeFormat
	  *        
	  */
	public void setResumeFormat(String ResumeFormat);
   /**
     * @return get Employee No.
	  */
	public String getEmployeeNo();
}

