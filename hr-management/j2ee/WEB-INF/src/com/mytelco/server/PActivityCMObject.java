package com.mytelco.server;

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;

public interface PActivityCMObject extends EJBObject {

    // getter and setter methods for Entity fields   

	/**
	  * @return Returns the ActNo.
	  */
	public String getActNo() throws RemoteException;
    /**
      * @param ActNo
	  *        
	  */
	public void setActNo(String ActNo) throws RemoteException;
	/**
	  * @return Returns the ActStaff
	  */
	public double getActStaff() throws RemoteException;
	/**
	  * @param ActStaff
	  *        
      */
    public void setActStaff(double actStaff) throws RemoteException;
    /**
      * @return get act start date
	  */
	public Date getActStartDate() throws RemoteException;
	/**
	  * @param act start date
	  *        
      */
    public void setActStartDate(Date actStartDate) throws RemoteException;
    /**
     * @return get act end date
	  */
	public Date getActEndDate() throws RemoteException;
	/**
	  * @param act end date
	  *        
     */
   public void setActEndDate(Date actEndDate) throws RemoteException;
   /**
     * @return get ProjNo
	  */
	public String getProjNo() throws RemoteException;
}
