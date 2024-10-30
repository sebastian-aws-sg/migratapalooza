package com.mytelco.server;


import java.util.Date;

import javax.ejb.EJBLocalObject;
//TODO: need to complete this CMBean
public interface PActivityCMLocalObject extends EJBLocalObject {
	
    // getter and setter methods for Entity fields   
    /**
      * @return Returns the ActNo.
      */
    public String getActNo();
    /**
      * @param ActNo
      *        
      */
    public void setActNo(String actNo);
    /**
     * @return Returns the actStaff
     */
   public double getActStaff();
   /**
     * @param Act Staff
     *        
     */
   public void setActStaff(double actStaff);
  /**
   * @return Returns the StartDate.
   */
	 public Date getActStartDate();
	 /**
	   * @param StartDate
	   *        
	   */
	 public void setActStartDate(Date actStartDate);
	
	 /**
	  * @return Returns the ActEndDate
	  */
	public Date getActEndDate();
	/**
	  * @param ActEndDate
	  *        
	  */
	public void setActEndDate(Date actEndDate);
	
    public String getProjNo();
}

