package com.mytelco.server;


import java.util.Date;

import javax.ejb.EJBLocalObject;

public interface DeveloperCMLocalObject extends EJBLocalObject {
	
    // getter and setter methods for Entity fields   
    /**
      * @return Returns the HardwareId.
      */
    public String getHardwareId();
    /**
      * @param HardwareId
      *        
      */
    public void setHardwareId(String hardwareId);
    /**
     * @return Returns the SkillLevel
     */
   public Integer getSkillLevel();
   /**
     * @param SkillLevel
     *        
     */
   public void setSkillLevel(Integer skillLevel);
    /**
     * @return Returns the salary
     */
   public Integer getSalary();
   /**
     * @param Salary
     *        
     */
   public void setSalary(Integer salary);

   /**
    * @return Returns the experience.
    */
  public Integer getExperience();
  /**
    * @param experience
    *        
    */
  public void setExperience(Integer experience);
  	/**
      * @return get project number
      */
    public String getDeveloperId();
}

