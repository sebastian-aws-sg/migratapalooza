package com.mytelco.server;

import java.util.Date;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface MsgInTrayCMLocalHome extends EJBLocalHome {

    public MsgInTrayCMLocalObject create(String empNo, Date ReceivedDate,
            String Source, String Subject, String NoteText)
            throws CreateException; 
		    
    // Finder methods. These are implemented by the container.
    // You can customize the functionality of these methods in the deployment 
    // descriptor through EJB-QL and container tools
		   
    public MsgInTrayCMLocalObject findByPrimaryKey(String empNo)
    	throws FinderException; 

    public String findBySource(String source)
    	throws FinderException; 

}
