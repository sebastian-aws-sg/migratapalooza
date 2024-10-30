package com.mytelco.server;


import java.util.Date;

import javax.ejb.EJBLocalObject;

public interface EmpPrjActCMLocalObject extends EJBLocalObject {
	
    // getter and setter methods for Entity fields   
    /**
      * @return Returns the Employee number.
      */
    public String getEmpNo();
    /**
      * @param Emp No
      *        
      */
    public void setEmpNo(String empNo);
    /**
     * @return Returns the proj no
     */
   public String getProjNo();
   /**
     * @param Proj No
     *        
     */
   public void setProjNo(String projNo);
    /**
     * @return Returns the EMP TIME
     */
   public double getEmpTime();
   /**
     * @param emp time
     *        
     */
   public void setEmpTime(double empTime);

   /**
    * @return Returns the E start Date
    */
  public Date getEStartDate();
  /**
    * @param E Start Date
    *        
    */
  public void setEStartDate(Date eStartDate);

  /**
   * @return E end date.
   */
 public Date getEEndDate();
 /**
   * @param E End date
   *        
   */
 public void setEEndDate(Date eEndDate);
   /**
      * @return get Act number
      */
    public String getActNo();
}

