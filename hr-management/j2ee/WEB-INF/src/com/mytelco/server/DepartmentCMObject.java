package com.mytelco.server;

import javax.ejb.*;
import java.rmi.*;


public interface DepartmentCMObject extends EJBObject {

    // getter and setter methods for Entity fields   

	/**
	  * @return Returns the Department Name.
	  */
	public String getDepartmentName() throws RemoteException;
    /**
      * @param DepartmentName
	  *        The Department Name to set.
	  */
	public void setDepartmentName(String DepartmentName) throws RemoteException;
	/**
	  * @return Returns the Manager No.
	  */
	public String getManagerNo() throws RemoteException;
	/**
	  * @param ManagerNo
	  *        The ManagerNo to set.
	  */
	public void setManagerNo(String ManagerNo) throws RemoteException;
	/**
	  * @return Returns the ADMRdepartment.
	  */
	public String getADMRDepartment() throws RemoteException;
	/**
	  * @param ADMRdepartment
	  *        The ADMRdepartment to set.
      */
    public void setADMRDepartment(String ADMRDepartment) throws RemoteException;
    /**
      * @return get Location.
	  */
	public String getLocation() throws RemoteException;
	/**
	  * @param Location
	  *        The Location to set.
      */
    public void setLocation(String location) throws RemoteException;
    /**
      * @return get Department No.
	  */
	public String getDeptNo() throws RemoteException;
}
