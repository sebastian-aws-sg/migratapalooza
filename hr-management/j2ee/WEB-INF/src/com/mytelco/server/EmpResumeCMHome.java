package com.mytelco.server;

import javax.ejb.EJBHome;

import javax.ejb.CreateException;

import javax.ejb.FinderException;

import java.rmi.RemoteException;

public interface EmpResumeCMHome extends EJBHome {
    // Creates employee resume
	// @
    public EmpResumeCMObject create(String employeeNo, Object employeeResume,
                                 String resumeFormat) throws CreateException, RemoteException; 
		    

    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public EmpResumeCMObject findByPrimaryKey(String EmployeeNo)
	                           throws FinderException, RemoteException; 

}
