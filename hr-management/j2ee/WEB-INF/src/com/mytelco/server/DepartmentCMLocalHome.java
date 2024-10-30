package com.mytelco.server;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface DepartmentCMLocalHome extends EJBLocalHome {

    public DepartmentCMLocalObject create(String departmentNo, String DepartmentName,
            String ManagerNO, String ADMRDepartment, String Location)
            throws CreateException; 
		    
    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public DepartmentCMLocalObject findByPrimaryKey(String DepartmentNo)
    	throws FinderException; 

    public String findByDepartmentName(String DepartmentName)
    	throws FinderException; 

}
