package com.mytelco.server;


import javax.ejb.EJBLocalObject;

public interface EmployeeDataCMLocalObject extends EJBLocalObject {
	
    // getter and setter methods for Entity fields   
    /**
      * @return Returns the firstName.
      */
    public String getFirstName();
    /**
      * @param firstName
      *        The firstName to set.
      */
    public void setFirstName(String firstName);
    /**
      * @return Returns the job.
      */
    public String getJob();
    /**
      * @param job 
      * 	         The job to set.
      */
    public void setJob(String job);
    /**
      * @return Returns the salary.
      */
    public double getSalary();
    /**
      * @param salary
      *        The salary to set.
      */
    public void setSalary(double salary);
    /**
      * @return get employee number.
      */
    public String getEmpNo();
}

