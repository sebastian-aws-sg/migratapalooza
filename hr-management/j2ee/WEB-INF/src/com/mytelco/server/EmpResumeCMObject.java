package com.mytelco.server;

import javax.ejb.*;
import java.rmi.*;


public interface EmpResumeCMObject extends EJBObject {

    // getter and setter methods for Entity fields   

	/**
	  * @return Returns the employee resume.
	  */
	public Object getEmployeeResume() throws RemoteException;
    /**
      * @param EmployeeResume
	  *        
	  */
	public void setEmployeeResume(Object EmployeeResume) throws RemoteException;
	/**
	  * @return Returns the resume format
	  */
	public String getResumeFormat() throws RemoteException;
	/**
	  * @param ResumeFormat
	  *        
	  */
	public void setResumeFormat(String ResumeFormat) throws RemoteException;
    /**
      * @return get Employee No.
	  */
	public String getEmployeeNo() throws RemoteException;
}
