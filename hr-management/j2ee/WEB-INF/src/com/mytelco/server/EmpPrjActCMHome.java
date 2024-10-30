package com.mytelco.server;

import javax.ejb.EJBHome;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.Date;



public interface EmpPrjActCMHome extends EJBHome {
    //  
	// @param  
	// @
    public EmpPrjActCMObject create(String empNo, String projNo, String actNo, double empTime, 
 		   Date EStartDate, Date EEndDate) throws CreateException, RemoteException; 
		    

    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public EmpPrjActCMObject findByPrimaryKey(EmpPrjActPKey epaPK)
	                           throws FinderException, RemoteException; 

	public EmpPrjActCMObject findByProjNo(String projNo)
                               throws FinderException, RemoteException; 

	public EmpPrjActCMObject findByEmpNo(String empNo)
	                           throws FinderException, RemoteException; 

	public EmpPrjActCMObject findByActNo(String actNo)
	                           throws FinderException, RemoteException;  
}
