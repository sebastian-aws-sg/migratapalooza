package com.mytelco.server;

import javax.ejb.EJBLocalObject;

public interface ActivityCMLocalObject extends EJBLocalObject {
	
    // getter and setter methods for Entity fields   
	/**
	  * @return Returns the ActivityDescription.
	  */
	public String getActivityKeyword();
   /**
     * @param ActivityKeyword
	  *        
	  */
	public void setActivityKeyword(String ActivityKeyword);
	/**
	  * @return Returns the Activity Description.
	  */
	public String getActivityDescription();
	/**
	  * @param ActivityDescription
	  *        
	  */
	public void setActivityDescription(String ActivityDescription);
	/**
     * @return get activityNo.
	  */
	public String getActivityNo();
}

