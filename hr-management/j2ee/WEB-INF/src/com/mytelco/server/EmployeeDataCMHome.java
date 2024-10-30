package com.mytelco.server;

import javax.ejb.EJBHome;

import javax.ejb.CreateException;

import javax.ejb.FinderException;

import java.rmi.RemoteException;



public interface EmployeeDataCMHome extends EJBHome {
    // Creates a Employee
	// @param empNo the employee number (unique)
	// @
    public EmployeeDataCMObject create(String empNo, String firstName,
                                 String lastName, String job, double salary)
	                             throws CreateException, RemoteException; 
		    

    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public EmployeeDataCMObject findByPrimaryKey(EmployeeNO empNo)
	                           throws FinderException, RemoteException; 

	public String findByFirstName(String firstName)
                               throws FinderException, RemoteException; 

	public String findByJob(String job)
	                           throws FinderException, RemoteException; 

	public String findBySalary(double salary)
	                           throws FinderException, RemoteException;  
}
