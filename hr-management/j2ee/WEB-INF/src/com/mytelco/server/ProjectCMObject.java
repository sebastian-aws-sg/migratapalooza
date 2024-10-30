package com.mytelco.server;

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;

//TODO: need to complete this CMBean

public interface ProjectCMObject extends EJBObject {

    // getter and setter methods for Entity fields   

	/**
	  * @return Returns the ProjName.
	  */
	public String getProjName() throws RemoteException;
    /**
      * @param ProjName
	  *        
	  */
	public void setProjName(String projName) throws RemoteException;
	/**
	  * @return Returns the deptNo.
	  */
	public String getDeptNo() throws RemoteException;
	/**
	  * @param DeptNo
	  *        
      */
    public void setDeptNo(String deptNo) throws RemoteException;
    /**
      * @return get projEmp
	  */
	public String getProjEmp() throws RemoteException;
	/**
	  * @param projEmp
	  *        
      */
    public void setProjEmp(String projEmp) throws RemoteException;
    /**
     * @return get ProjStaff.
	  */
	public double getProjStaff() throws RemoteException;
	/**
	  * @param ProjStaff
	  *        
     */
   public void setProjStaff(double projStaff) throws RemoteException;
   /**
    * @return get projStartDate.
	  */
	public Date getProjStartDate() throws RemoteException;
	/**
	  * @param projStartDate
	  *        
    */
  public void setProjStartDate(Date projStartDate) throws RemoteException;
  /**
   * @return get projEndDate.
	  */
	public Date getProjEndDate() throws RemoteException;
	/**
	  * @param projEndDate
	  *        
   */
 public void setProjEndDate(Date projEndDate) throws RemoteException;
 /**
  * @return get ctrlProject.
	  */
	public String getCtrlProject() throws RemoteException;
	/**
	  * @param ctrlProject
	  *        
  */
public void setCtrlProject(String ctrlProject) throws RemoteException;
  /**
     * @return get ProjNo
	  */
	public String getProjNo() throws RemoteException;
}
