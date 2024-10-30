package com.dtcc.dnv.otc.common.signon.config;

import java.util.Hashtable;

import org.apache.log4j.Logger;
/**
 * @author vxsubram
 *
 * Created on Aug 9, 2007
 * @version 1.0
 * 
 * This class represent the attributes of a workflow.
 * 
 * @version	1.1					September 2, 2007			sv
 * Checked in for Cognizant.  Updated comments and javadoc.
 * 
 * @version	1.2					October 17, 2007			sv
 * Removed INFO logging.
 * 
 * @version	1.3					October 18, 2007			sv
 * Updated logger to be static final.
 */
public class SignonAttribute {
	
	// Holds the attributes of signon framework
	private Hashtable attributes = new Hashtable();
	// Logger instance
	private static final Logger log = Logger.getLogger(SignonAttribute.class);
	
	/**
	 * Default scope constructor because it should only be created by configuration mangers.
	 */
	SignonAttribute(){
	}
	
	/**
	 * Gets the attribute value of signon
	 * @param key - String name of the attribute
	 * @return the value to which the key is mapped in this hashtable; null if the key is not mapped to any value in this hashtable
	 */
	String getAttribute(String key){
		return (String)attributes.get(key);
	}
	
	/**
	 * Sets the attribute of signon framework
	 * @param key - String name of the attribute; 
	 * @param value - String value of the attribute
	 * 
	 */
	void setAttribute(String key, String value){
		attributes.put(key,value);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return attributes.toString();
	}

}
