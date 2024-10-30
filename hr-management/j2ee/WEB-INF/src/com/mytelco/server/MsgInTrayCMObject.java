package com.mytelco.server;

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;


public interface MsgInTrayCMObject extends EJBObject {

    // getter and setter methods for Entity fields   

	/**
	  * @return Returns the Received Date.
	  */
	public Date getReceivedDate() throws RemoteException;
    /**
      * @param ReceivedDate
	  *        
	  */
	public void setReceivedDate(Date receivedDate) throws RemoteException;
	/**
	  * @return Returns the source.
	  */
	public String getSource() throws RemoteException;
	/**
	  * @param source
	  *        
	  */
	public void setSource(String source) throws RemoteException;
	/**
	  * @return Returns the subject.
	  */
	public String getSubject() throws RemoteException;
	/**
	  * @param subject
	  *       
      */
    public void setSubject(String subject) throws RemoteException;
    /**
      * @return get NoteText.
	  */
	public String getNoteText() throws RemoteException;
	/**
	  * @param NoteText
	  *      
      */
    public void setNoteText(String noteText) throws RemoteException;
    /**
      * @return get Emp No.
	  */
	public String getEmpNo() throws RemoteException;
}
