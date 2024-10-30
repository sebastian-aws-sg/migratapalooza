package com.mytelco.server;

import javax.ejb.EJBHome;

import javax.ejb.CreateException;

import javax.ejb.FinderException;

import java.rmi.RemoteException;
import java.util.Date;



public interface MsgInTrayCMHome extends EJBHome {
    //  
	// @param empNo  
	// @
    public MsgInTrayCMObject create(String empNo, Date ReceivedDate, String Source, String Subject, String NoteText)
	                             throws CreateException, RemoteException; 
		    

    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public MsgInTrayCMObject findByPrimaryKey(String empNo)
	                           throws FinderException, RemoteException; 

	public String findBySource(String source)
                               throws FinderException, RemoteException; 

}
