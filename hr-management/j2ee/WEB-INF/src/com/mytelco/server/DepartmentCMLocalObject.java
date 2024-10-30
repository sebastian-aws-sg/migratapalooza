package com.mytelco.server;

import javax.ejb.EJBLocalObject;

public interface DepartmentCMLocalObject extends EJBLocalObject {
	
    // getter and setter methods for Entity fields   
	/**
	  * @return Returns the Department Name.
	  */
	public String getDepartmentName();
   /**
     * @param DepartmentName
	  *        The Department Name to set.
	  */
	public void setDepartmentName(String DepartmentName);
	/**
	  * @return Returns the Manager No.
	  */
	public String getManagerNo();
	/**
	  * @param ManagerNo
	  *        The ManagerNo to set.
	  */
	public void setManagerNo(String ManagerNo);
	/**
	  * @return Returns the ADMRdepartment.
	  */
	public String getADMRDepartment();
	/**
	  * @param ADMRdepartment
	  *        The ADMRdepartment to set.
     */
   public void setADMRDepartment(String ADMRDepartment);
   /**
     * @return get Location.
	  */
	public String getLocation();
	/**
	  * @param Location
	  *        The Location to set.
     */
   public void setLocation(String location);
   /**
     * @return get Department No.
	  */
	public String getDeptNo();
}

