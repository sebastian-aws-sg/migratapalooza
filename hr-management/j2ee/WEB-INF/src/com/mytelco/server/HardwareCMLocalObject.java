package com.mytelco.server;

import javax.ejb.EJBLocalObject;

public interface HardwareCMLocalObject extends EJBLocalObject {
	
    // getter and setter methods for Entity fields   
    /**
      * @return Returns the HardwareId.
      */
    public String getMouseId();
    /**
      * @param MouseId
      *        
      */
    public void setMouseId(String mouseId);
    /**
     * @return Returns the keyboard
     */
   public String getKeyboardId();
   /**
     * @param Keyboard
     *        
     */
   public void setKeyboardId(String keyboardId);
    /**
     * @return Returns the CPU
     */
   public String getCpuId();
   /**
     * @param Cpu
     *        
     */
   public void setCpuId(String cpuId);

   /**
    * @return Returns the monitor.
    */
  public String getMonitorId();
  /**
    * @param monitor
    *        
    */
  public void setMonitor(String Monitor);
  /**
   * @return Returns the mouse ins value
   */
 public Integer getMouseInsValue();
 /**
   * @param mouse ins value
   *        
   */
 public void setMouseInsValue(Integer mouseInsValue);
 /**
  * @return Returns the keyboard ins value 
  */
public Integer getKeyboardInsValue();
/**
  * @param mouse ins value
  *        
  */
public void setKeyboardInsValue(Integer keyboardInsValue);
/**
 * @return Returns the cpu ins value 
 */
public Integer getCpuInsValue();
/**
 * @param cpu ins value
 *        
 */
public void setCpuInsValue(Integer cpuInsValue);
/**
 * @return Returns the Monitor ins value 
 */
public Integer getMonitorInsValue();
/**
 * @param Monitor ins value
 *        
 */
public void setMonitorInsValue(Integer monitorInsValue);

/**
      * @return mouse ins hardware id
      */
    public String getHardwareId();
}

