package com.mytelco.server;

import javax.ejb.EJBHome;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.Date;



public interface ProjectCMHome extends EJBHome {
    //  
	// @param  
	// @
    public ProjectCMObject create(String projNo, String projName,
                                 String deptName, String projEmp, double projStaff, 
                                 Date projStartDate, Date projEndDate, String ctrlProject)
	                             throws CreateException, RemoteException; 
		    

    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public ProjectCMObject findByPrimaryKey(String projNo)
	                           throws FinderException, RemoteException; 

	public ProjectCMObject findByProjName(String projName)
                               throws FinderException, RemoteException; 

	public ProjectCMObject findByProjEmp(String projEmp)
	                           throws FinderException, RemoteException; 

	public ProjectCMObject findByProjStartDate(Date projStartDate)
	                           throws FinderException, RemoteException;  
}
