package com.mytelco.server;

import javax.ejb.EJBHome;

import javax.ejb.CreateException;

import javax.ejb.FinderException;

import java.rmi.RemoteException;



public interface ActivityCMHome extends EJBHome {
    // Creates a Employee
	// @param empNo the employee number (unique)
	// @
    public ActivityCMObject create(String activityNo, String activityDescription,
            					String activityKeyword)  throws CreateException, RemoteException; 
		    

    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public ActivityCMObject findByPrimaryKey(String ActivityNo)
	                           throws FinderException, RemoteException; 

	public String findByActivityKeyword(String ActivityKeyword)
                               throws FinderException, RemoteException; 

}
