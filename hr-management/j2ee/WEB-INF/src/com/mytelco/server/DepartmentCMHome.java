package com.mytelco.server;

import javax.ejb.EJBHome;

import javax.ejb.CreateException;

import javax.ejb.FinderException;

import java.rmi.RemoteException;



public interface DepartmentCMHome extends EJBHome {
    // Creates a department
	// @param empNo the department number (unique)
	// @
    public DepartmentCMObject create(String departmentNo, String DepartmentName,
                                 String ManagerNO, String ADMRDepartment, String Location)
	                             throws CreateException, RemoteException; 
		    

    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public DepartmentCMObject findByPrimaryKey(String DepartmentNo)
	                           throws FinderException, RemoteException; 

	public String findByDepartmentName(String DepartmentName)
                               throws FinderException, RemoteException; 

}
