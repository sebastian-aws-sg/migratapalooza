package com.mytelco.server;

import javax.ejb.EJBHome;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.Date;



public interface PActivityCMHome extends EJBHome {
    //  
	// @param  
	// @
    public ProjectCMObject create(String projNo, String actNo, double actStaff, 
 		   				Date actStartDate, Date actEndDate)
	                             throws CreateException, RemoteException; 
		    

    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public PActivityCMObject findByPrimaryKey(String projNo)
	                           throws FinderException, RemoteException; 

	public PActivityCMObject findByActNo(String actNo)
                               throws FinderException, RemoteException; 

	public PActivityCMObject findByActStartDate(Date actStartDate)
	                           throws FinderException, RemoteException;  
}
