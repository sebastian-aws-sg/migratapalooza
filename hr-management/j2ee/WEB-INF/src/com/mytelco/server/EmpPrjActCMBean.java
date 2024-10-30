package com.mytelco.server;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

public abstract class EmpPrjActCMBean implements EntityBean {
    EntityContext entityContext;
    // Default Constructor
    public EmpPrjActCMBean()
    {
    }
    
    public abstract String getEmpNo();
    public abstract void setEmpNo(String empNo);  

    public abstract String getProjNo();
    public abstract void setProjNo(String projNo);  

    public abstract String getActNo();
    public abstract void setActNo(String actNo);  

    public abstract double getEmpTime();
    public abstract void setEmpTime(double empTime);  

    public abstract Date getEStartTime();
    public abstract void setEStartTime(Date eStartTime);
    
    public abstract double getEEndTime();
    public abstract void setEEndTime(Date eEndTime);
    
    public void setEntityContext(EntityContext ctx) throws EJBException,
			RemoteException {
		// TODO Auto-generated method stub
		this.entityContext = ctx;
	}

	public void unsetEntityContext() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
        this.entityContext = null;
	}

	public void ejbRemove() throws RemoveException, EJBException,
			RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbLoad() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbStore() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}
	/** 
     * Called after ejbCreate(). Now, the Bean can retrieve
     * its EJBObject from its context, and pass it as a 'this' argument.
     */   
   public void ejbPostCreate(String empNo, String projNo, String actNo, double empTime, 
		   Date EStartDate, Date EEndDate) {
       System.out.println("ejpPostCreate() called");
   }

   /**
     * This is the initialization method that corresponds to the
     * create() method in the Home interface.
     *
     * when the client calls the Home object's create() method,
     * the Home object then calls this ejbCreate() method.
     *
     * We need to initialize our Bean's fields with the
     * parameters passed from the client, so that the container
     * can create the corresponding database entries in the
     * subclass after this method completes.
     */

   public String ejbCreate(String empNo, String projNo, String actNo, double empTime, 
		   Date EStartDate, Date EEndDate) throws CreateException {
	    
	    setEmpNo(empNo); 
	    setProjNo(projNo); 
	    setActNo(actNo); 
	    setEmpTime(empTime); 
	    setEStartTime(EStartDate); 
	    setEEndTime(EEndDate); 
	   
       return null; 
  }
}    
