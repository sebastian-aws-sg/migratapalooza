package com.mytelco.server;

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;

//TODO: need to complete this CMBean

public interface DeveloperCMObject extends EJBObject {

    // getter and setter methods for Entity fields   

	/**
	  * @return Returns the hardware Id.
	  */
	public String getHardwareId() throws RemoteException;
    /**
      * @param Hardware Id
	  *        
	  */
	public void setHardwareId(String hardwareId) throws RemoteException;
	/**
	  * @return Returns the skill level.
	  */
	public Integer getSkillLevel() throws RemoteException;
	/**
	  * @param skill level
	  *        
      */
    public void setSkillLevel(Integer skillLevel) throws RemoteException;
    /**
      * @return get salary
	  */
	public Integer getSalary() throws RemoteException;
	/**
	  * @param Salary
	  *        
      */
    public void setSalary(Integer salary) throws RemoteException;
    /**
     * @return get Experience
	  */
	public Integer getExperience() throws RemoteException;
	/**
	  * @param Experience
	  *        
     */
   public void setExperience(Integer Experience) throws RemoteException;
  /**
     * @return get ProjNo
	  */
	public String getDeveloperId() throws RemoteException;
}
