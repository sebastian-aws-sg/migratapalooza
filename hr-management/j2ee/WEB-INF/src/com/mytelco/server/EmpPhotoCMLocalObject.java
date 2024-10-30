package com.mytelco.server;

import javax.ejb.EJBLocalObject;

public interface EmpPhotoCMLocalObject extends EJBLocalObject {
	
    // getter and setter methods for Entity fields   
	/**
	  * @return Returns the Employee Photo.
	  */
	public Object getEmployeePhoto();
   /**
     * @param EmployeePhoto
	  *        
	  */
	public void setEmployeePhoto(Object EmployeePhoto);
	/**
	  * @return Returns the Photo Format
	  */
	public String getPhotoFormat();
	/**
	  * @param PhotoFormat
	  *        
	  */
	public void setPhotoFormat(String PhotoFormat);
   /**
     * @return get Employee No.
	  */
	public String getEmployeeNo();
}

