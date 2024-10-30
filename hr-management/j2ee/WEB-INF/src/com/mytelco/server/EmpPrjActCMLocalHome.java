package com.mytelco.server;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EmpPrjActCMLocalHome extends EJBLocalHome {

    public EmpPrjActCMLocalObject create(String empNo, String projNo, String actNo, double empTime, 
 		   Date EStartDate, Date EEndDate) throws CreateException; 
		    
    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public EmpPrjActCMLocalObject findByPrimaryKey(EmpPrjActPKey epaPK)
                                throws FinderException; 
	    
    public EmpPrjActCMLocalObject findByEmpNo(String empNo)
    				throws FinderException; 

    public EmpPrjActCMLocalObject findByProjNo(String projNo)
    				throws FinderException; 

    public EmpPrjActCMLocalObject findByActNo(String actNo)
    				throws FinderException;  
}
