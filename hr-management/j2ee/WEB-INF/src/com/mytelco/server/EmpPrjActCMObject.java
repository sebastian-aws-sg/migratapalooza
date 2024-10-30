package com.mytelco.server;

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;

public interface EmpPrjActCMObject extends EJBObject {

    // getter and setter methods for Entity fields   

	/**
	  * @return Returns the EmpNo.
	  */
	public String getEmpNo() throws RemoteException;
    /**
      * @param EmpNo
	  *        
	  */
	public void setEmpNo(String empNo) throws RemoteException;
	/**
	  * @return Returns the ProjNo.
	  */
	public String getProjNo() throws RemoteException;
	/**
	  * @param ProjNo
	  *        
      */
    public void setProjNo(String projNo) throws RemoteException;
    /**
      * @return get EmpTime
	  */
	public double getEmpTime() throws RemoteException;
	/**
	  * @param EmpTime
	  *        
      */
    public void setEmpTime(double empTime) throws RemoteException;
    /**
     * @return get EStartDate.
	  */
	public Date getEStartDate() throws RemoteException;
	/**
	  * @param EStartDate
	  *        
     */
   public void setEStartDate(Date eStartDate) throws RemoteException;
   /**
    * @return get eEndDate.
	  */
	public Date getEEndDate() throws RemoteException;
	/**
	  * @param eEndDate
	  *        
    */
  public void setEEndDate(Date eEndDate) throws RemoteException;
  		/**
  		 * @return get ProjNo
  		 */
  		public String getActNo() throws RemoteException;
}
