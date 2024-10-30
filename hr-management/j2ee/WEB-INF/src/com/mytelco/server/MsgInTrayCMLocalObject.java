package com.mytelco.server;

import java.util.Date;
import javax.ejb.EJBLocalObject;

public interface MsgInTrayCMLocalObject extends EJBLocalObject {
	
    // getter and setter methods for Entity fields   
	/**
	  * @return Returns the ReceivedDate.
	  */
	public Date getReceivedDate();
   /**
     * @param ReceivedDate
	  *         
	  */
	public void setReceivedDate(Date receivedDate);
	/**
	  * @return Returns the Source
	  */
	public String getSource();
	/**
	  * @param Source
	  *         
	  */
	public void setSource(String Source);
	/**
	  * @return Returns the Subject.
	  */
	public String getSubject();
	/**
	  * @param Subject
	  *        
     */
   public void setSubject(String Subject);
   /**
     * @return get NoteText
	  */
	public String getNoteText();
	/**
	  * @param NoteText
	  *         
     */
   public void setNoteText(String noteText);
   /**
     * @return get EmpNo
	  */
	public String getEmpNo();
}

