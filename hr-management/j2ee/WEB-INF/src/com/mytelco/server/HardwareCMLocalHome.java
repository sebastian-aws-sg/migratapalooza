package com.mytelco.server;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface HardwareCMLocalHome extends EJBLocalHome {

    public HardwareCMLocalObject create(String hardwareId, String KeyboardId, String CpuId, String MouseId,
    		String MonitorId,Integer mouseInsValue,Integer  monitorInsValue, Integer cpuInsValue,
    		Integer keyboardInsValue) throws CreateException; 
		    
    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public HardwareCMLocalObject findByPrimaryKey(String hardwareId)
                                throws FinderException; 
	    }
