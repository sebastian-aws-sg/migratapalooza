package com.mytelco.server;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface ActivityCMLocalHome extends EJBLocalHome {

    public ActivityCMLocalObject create(String activityNo, String activityDescription,
            String activityKeyword)  throws CreateException; 
		    
    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public ActivityCMLocalObject findByPrimaryKey(float ActivityNo)
    	throws FinderException; 

    public String findByActivityKeyword(String ActivityKeyword)
    	throws FinderException; 

}
