package com.mytelco.server;


import java.util.Date;

import javax.ejb.EJBLocalObject;
//TODO: need to complete this CMBean
public interface ProjectCMLocalObject extends EJBLocalObject {
	
    // getter and setter methods for Entity fields   
    /**
      * @return Returns the projName.
      */
    public String getProjName();
    /**
      * @param projName
      *        
      */
    public void setProjName(String projName);
    /**
     * @return Returns the deptNo
     */
   public String getDeptNo();
   /**
     * @param Dept No
     *        
     */
   public void setDeptNo(String deptNo);
    /**
     * @return Returns the ProjEmp
     */
   public String getProjEmp();
   /**
     * @param ProjEmp
     *        
     */
   public void setProjEmp(String projEmp);

   /**
    * @return Returns the projStaff.
    */
  public double getProjStaff();
  /**
    * @param projStaff
    *        
    */
  public void setProjStaff(double projStaff);

  /**
   * @return Returns the StartDate.
   */
 public Date getProjStartDate();
 /**
   * @param StartDate
   *        
   */
 public void setProjStartDate(Date projStartDate);

 /**
  * @return Returns the projEndDate
  */
public Date getProjEndDate();
/**
  * @param ProjEndDate
  *        
  */
public void setProjEndDate(Date projEndDate);

/**
 * @return Returns the ctrlProject
 */
public String getCtrlProject();
/**
 * @param ctrlProject
 *        
 */
public void setCtrlProject(String ctrlProject);
   /**
      * @return get project number
      */
    public String getProjNo();
}

