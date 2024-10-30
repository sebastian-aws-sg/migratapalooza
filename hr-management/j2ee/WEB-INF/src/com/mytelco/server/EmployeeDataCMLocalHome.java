package com.mytelco.server;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EmployeeDataCMLocalHome extends EJBLocalHome {

    public EmployeeDataCMLocalObject create(String empNo, String firstName,
                                      String lastName, String job, double salary)
	                                  throws CreateException; 
		    
    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public EmployeeDataCMLocalObject findByPrimaryKey(EmployeeNO empNo)
                                throws FinderException; 
	    
    public String findByFirstName(String firstName)
	                            throws FinderException; 

    public String findByJob(String job)
                                throws FinderException; 

    public String findBySalary(double salary)
	                            throws FinderException;      
}
