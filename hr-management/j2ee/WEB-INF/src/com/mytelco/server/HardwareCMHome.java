package com.mytelco.server;

import javax.ejb.EJBHome;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.Date;



public interface HardwareCMHome extends EJBHome {
    //  
	// @param  
	// @
    public HardwareCMObject create(String hardwareId, String KeyboardId, String CpuId, String MouseId,
    		String MonitorId,Integer mouseInsValue,Integer  monitorInsValue, Integer cpuInsValue,
    		Integer keyboardInsValue) throws CreateException, RemoteException; 
		    

    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public HardwareCMObject findByPrimaryKey(String hardwareId)
	                           throws FinderException, RemoteException; 

}
