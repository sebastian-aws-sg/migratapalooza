package com.mytelco.server;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EmpResumeCMLocalHome extends EJBLocalHome {

    public EmpResumeCMLocalObject create(String employeeNo, Object employeeResume,
            String resumeFormat) throws CreateException; 
		    
    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public EmpResumeCMLocalObject findByPrimaryKey(String EmployeeNo)
    	throws FinderException; 
}
