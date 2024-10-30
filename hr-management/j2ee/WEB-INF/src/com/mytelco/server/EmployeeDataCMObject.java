package com.mytelco.server;

import javax.ejb.*;
import java.rmi.*;


public interface EmployeeDataCMObject extends EJBObject {

    // getter and setter methods for Entity fields   

	/**
	  * @return Returns the firstName.
	  */
	public String getFirstName() throws RemoteException;
    /**
      * @param firstName
	  *        The firstName to set.
	  */
	public void setFirstName(String firstName) throws RemoteException;
	/**
	  * @return Returns the hireDate.
	  */
	public String getJob() throws RemoteException;
	/**
	  * @param job
	  *        The job to set.
	  */
	public void setJob(String job) throws RemoteException;
	/**
	  * @return Returns the lastName.
	  */
	public double getSalary() throws RemoteException;
	/**
	  * @param salary
	  *        The salary to set.
      */
    public void setSalary(double salary) throws RemoteException;
    /**
      * @return get employee number.
	  */
	public String getEmpNo() throws RemoteException;
}
