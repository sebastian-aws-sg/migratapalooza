package com.mytelco.server;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EmpPhotoCMLocalHome extends EJBLocalHome {

    public EmpPhotoCMLocalObject create(String employeeNo, Object employeePhoto,
            String photoFormat) throws CreateException; 
		    
    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public EmpPhotoCMLocalObject findByPrimaryKey(String EmployeeNo)
    	throws FinderException; 
}
