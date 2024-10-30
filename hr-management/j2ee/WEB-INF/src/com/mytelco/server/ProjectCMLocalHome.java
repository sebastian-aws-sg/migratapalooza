package com.mytelco.server;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface ProjectCMLocalHome extends EJBLocalHome {

    public EmployeeDataCMLocalObject create(String projNo, String projName, String deptName, 
    		String projEmp, double projStaff, Date projStartDate, 
    		Date projEndDate, String ctrlProject) throws CreateException; 
		    
    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public ProjectCMLocalObject findByPrimaryKey(String projNo)
                                throws FinderException; 
	    
    public ProjectCMLocalObject findByProjName(String projName)
    				throws FinderException; 

    public ProjectCMLocalObject findByProjEmp(String projEmp)
    				throws FinderException; 

    public ProjectCMLocalObject findByProjStartDate(Date projStartDate)
    				throws FinderException;  
}
