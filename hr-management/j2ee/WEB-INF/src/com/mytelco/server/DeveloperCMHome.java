package com.mytelco.server;

import javax.ejb.EJBHome;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.Date;



public interface DeveloperCMHome extends EJBHome {
    //  
	// @param  
	// @
    public DeveloperCMObject create(String developerId, String hardwareId, Integer skillLevel, 
    		Integer salary, Integer experience) throws CreateException, RemoteException; 
		    

    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public DeveloperCMObject findByPrimaryKey(String developerId)
	                           throws FinderException, RemoteException; 

	public DeveloperCMObject findByHardwareId(String hardwareId)
                               throws FinderException, RemoteException; 

}
