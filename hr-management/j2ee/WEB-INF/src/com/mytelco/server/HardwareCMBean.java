package com.mytelco.server;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

public abstract class HardwareCMBean implements EntityBean {
    EntityContext entityContext;
    // Default Constructor
    public HardwareCMBean()
    {
    }
    
    public abstract String getHardwareId();
    public abstract void setHardwareId(String hardwareId);  

    public abstract String getMouseId();
    public abstract void setMouseId(String mouseId);  

    public abstract String getKeyboardId();
    public abstract void setKeyboardId(String keyboardId);  

    public abstract String getCpuId();
    public abstract void setCpuId(String cpuId);  

    public abstract String getMonitorId();
    public abstract void setMonitorId(String monitorId);  

    public abstract Integer getMouseInsValue();
    public abstract void setMouseInsValue(Integer mouseInsValue);
    
    public abstract Integer getKeyboardInsValue();
    public abstract void setKeyboardInsValue(Integer keyboardInsValue);

    public abstract Integer getCpuInsValue();
    public abstract void setCpuInsValue(Integer cpuInsValue);

    public abstract Integer getMonitorInsValue();
    public abstract void setMonitorInsValue(Integer monitorInsValue);

    public void setEntityContext(EntityContext ctx) throws EJBException,
			RemoteException {
		// TODO Auto-generated method stub
		this.entityContext = ctx;
	}

	public void unsetEntityContext() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
        this.entityContext = null;
	}

	public void ejbRemove() throws RemoveException, EJBException,
			RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbLoad() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbStore() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}
	/** 
     * Called after ejbCreate(). Now, the Bean can retrieve
     * its EJBObject from its context, and pass it as a 'this' argument.
     */   
   public void ejbPostCreate(String hardwareId, String mouseId, String keyboardId, String cpuId, String monitorId, Integer mouseInsValue, 
		   Integer keyboardInsValue, Integer cpuInsValue, Integer monitorInsValue) {
       System.out.println("ejpPostCreate() called");
   }

   /**
     * This is the initialization method that corresponds to the
     * create() method in the Home interface.
     *
     * when the client calls the Home object's create() method,
     * the Home object then calls this ejbCreate() method.
     *
     * We need to initialize our Bean's fields with the
     * parameters passed from the client, so that the container
     * can create the corresponding database entries in the
     * subclass after this method completes.
     */

   public String ejbCreate(String hardwareId, String mouseId, String keyboardId, String cpuId, String monitorId, Integer mouseInsValue, 
		   Integer keyboardInsValue, Integer cpuInsValue, Integer monitorInsValue) throws CreateException {
	    
	    setHardwareId(hardwareId); 
	    setMouseId(mouseId); 
	    setKeyboardId(keyboardId); 
	    setCpuId(cpuId); 
	    setMonitorId(monitorId); 
	    setMouseInsValue(mouseInsValue); 
	    setKeyboardInsValue(keyboardInsValue); 
	    setCpuInsValue(cpuInsValue); 
	    setMonitorInsValue(monitorInsValue); 
	   
       return null; 
  }
}    
