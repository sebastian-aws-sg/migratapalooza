package com.mytelco.server;

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;

//TODO: need to complete this CMBean

public interface HardwareCMObject extends EJBObject {

    // getter and setter methods for Entity fields   

	/**
	  * @return Returns the mouse Id.
	  */
	public String getMouseId() throws RemoteException;
    /**
      * @param Mouse Id
	  *        
	  */
	public void setMouseId(String mouseId) throws RemoteException;
	/**
	  * @return Returns the keyboard id.
	  */
	public String getKeyboardId() throws RemoteException;
	/**
	  * @param KeyboardId
	  *        
      */
    public void setKeyboardId(String keyboardId) throws RemoteException;
    /**
      * @return get cpu
	  */
	public String getCpuId() throws RemoteException;
	/**
	  * @param Salary
	  *        
      */
    public void setCpuId(String cpuId) throws RemoteException;
    /**
     * @return get MonitorId
	  */
	public Integer getMonitorId() throws RemoteException;
	/**
	  * @param MonitorId
	  *        
     */
   public void setMonitorId(Integer monitorId) throws RemoteException;
   /**
    * @return get MonitorId
	  */
	public Integer getMouseInsValue() throws RemoteException;
	/**
	  * @param MonitorId
	  *        
    */
  public void setMouseInsValue(Integer mouseInsValue) throws RemoteException;
  /**
   * @return get KeyboardInsValue
	  */
	public Integer getKeyboardInsValue() throws RemoteException;
	/**
	  * @param KeyboardInsValue
	  *        
   */
 public void setKeyboardInsValue(Integer keyboardInsValue) throws RemoteException;
 /**
  * @return get CpuInsValue
	  */
	public Integer getCpuInsValue() throws RemoteException;
	/**
	  * @param CpuInsValue
	  *        
  */
public void setCpuInsValue(Integer IntegercpuInsValue) throws RemoteException;
/**
 * @return get MonitorInsValue
	  */
	public Integer getMonitorInsValue() throws RemoteException;
	/**
	  * @paramMonitorInsValue
	  *        
 */
public void setMonitorInsValue(Integer monitorInsValue) throws RemoteException;
  /**
     * @return get hardware
	  */
	public String getHardwareId() throws RemoteException;
}
