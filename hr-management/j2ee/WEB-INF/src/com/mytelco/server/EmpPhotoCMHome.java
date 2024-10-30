package com.mytelco.server;

import javax.ejb.EJBHome;

import javax.ejb.CreateException;

import javax.ejb.FinderException;

import java.rmi.RemoteException;



public interface EmpPhotoCMHome extends EJBHome {
    // Creates employee photo
	// @
    public EmpPhotoCMObject create(String employeeNo, Object employeePhoto,
                                 String photoFormat) throws CreateException, RemoteException; 
		    

    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public EmpPhotoCMObject findByPrimaryKey(String EmployeeNo)
	                           throws FinderException, RemoteException; 

}
