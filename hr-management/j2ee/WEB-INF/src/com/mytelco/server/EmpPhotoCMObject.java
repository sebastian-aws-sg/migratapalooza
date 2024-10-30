package com.mytelco.server;

import javax.ejb.*;
import java.rmi.*;


public interface EmpPhotoCMObject extends EJBObject {

    // getter and setter methods for Entity fields   

	/**
	  * @return Returns the employee photo.
	  */
	public Object getEmployeePhoto() throws RemoteException;
    /**
      * @param EmployeePhoto
	  *        
	  */
	public void setEmployeePhoto(Object EmployeePhoto) throws RemoteException;
	/**
	  * @return Returns the photo format
	  */
	public String getPhotoFormat() throws RemoteException;
	/**
	  * @param PhotoFormat
	  *        
	  */
	public void setPhotoFormat(String PhotoFormat) throws RemoteException;
    /**
      * @return get Employee No.
	  */
	public String getEmployeeNo() throws RemoteException;
}
