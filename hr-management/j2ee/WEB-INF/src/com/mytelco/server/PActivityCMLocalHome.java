package com.mytelco.server;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface PActivityCMLocalHome extends EJBLocalHome {

    public EmployeeDataCMLocalObject create(String projNo, String actNo, double actStaff, 
 		   Date actStartDate, Date actEndDate) throws CreateException; 
		    
    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public PActivityCMLocalObject findByPrimaryKey(String projNo)
                                throws FinderException; 
	    
    public PActivityCMLocalObject findByActNo(String actNo)
    				throws FinderException; 

    public PActivityCMLocalObject findByActStartDate(Date ActStartDate)
    				throws FinderException;  
}
