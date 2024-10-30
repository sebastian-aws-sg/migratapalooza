package com.mytelco.server;

import javax.ejb.*;
import java.rmi.*;


public interface ActivityCMObject extends EJBObject {

    // getter and setter methods for Entity fields   

	/**
	  * @return Returns the activity keyword.
	  */
	public String getActivityKeyword() throws RemoteException;
    /**
      * @param ActivityKeyword
	  *        The ActivityKeyword to set.
	  */
	public void setActivityKeyword(String ActivityKeyword) throws RemoteException;
	/**
	  * @return Returns the ActivityDescription.
	  */
	public String getActivityDescription() throws RemoteException;
	/**
	  * @param ActivityDescription
	  */
	public void setActivityDescription(String ActivityDescription) throws RemoteException;
    /**
      * @return get ActivityNo.
	  */
	public String getActivityNo() throws RemoteException;
}
