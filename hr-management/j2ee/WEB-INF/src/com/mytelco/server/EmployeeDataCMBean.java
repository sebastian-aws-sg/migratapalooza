package com.mytelco.server;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

public abstract class EmployeeDataCMBean implements EntityBean {
    EntityContext entityContext;
    // Default Constructor
    public EmployeeDataCMBean()
    {
    }
    
    public abstract String getFirstName();
    public abstract void   setFirstName(String firstName);  

    public abstract String getJob();
    public abstract void setJob(String job);  

    public abstract double getSalary();
    public abstract void setSalary(double salary);
    
    public abstract String getEmpNo();
    public abstract void setEmpNo(String empNo);
    
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
   public void ejbPostCreate(String empNo, String firstName, String job,
                             double salary) {
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

   public String ejbCreate(String empNo, String firstName, String job,
                                double salary) throws CreateException {
   
       setEmpNo(empNo);
       setFirstName(firstName);
       setJob(job);
       setSalary(salary);
       
       return null;//new EmployeeNO(empNo);
  }
}    
