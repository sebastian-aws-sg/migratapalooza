package com.mytelco.server;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface DeveloperCMLocalHome extends EJBLocalHome {

    public DeveloperCMLocalObject create(String developerId, String hardwareId, Integer skillLevel, 
    		Integer salary, Integer experience) throws CreateException; 
		    
    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public DeveloperCMLocalObject findByPrimaryKey(String developerId)
                                throws FinderException; 
	    
    public DeveloperCMLocalObject findByHardwareId(String hardwareId)
    				throws FinderException; 

}
