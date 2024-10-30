package com.dtcc.dnv.otc.common.security.xmldb;

import java.io.File;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.ServletContext;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.log4j.Logger;

import com.dtcc.dnv.otc.common.exception.XMLDatabaseException;


/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55, Water Street,
 * New York, NY 10048
 * U.S.A
 * All Rights Reserved.
 *
 * This software may contain (in part or full) confidential/proprietary information of DTCC.
 * ("Confidential Information"). Disclosure of such Confidential
 * Information is prohibited and should be used only for its intended purpose
 * in accordance with rules and regulations of DTCC.
 * Form bean for a Struts application.
 *
 * @author Dmitriy Larin
 * @version 1.0
 * 
 * @version 1.1
 * @author Cognizant
 * 09/04/2007 modified to read entitlement.xml  and entitlement-rules.xml  
 * using ResourceLocator.
 * 
 * @version	1.2				September 23, 2007				sv
 * Cleaned up code.  Change only applicable methods to be public.  Removed
 * unneccesary methods.  Removed extra parser() and left the one that take 2 
 * File objects as input.
 * 
 * @version	1.3				October 18, 2007				sv
 * Updated logger to be static final.
 * 
 */
public class EntitlementDefinitions implements Serializable {

    /*
     * ServletContext method that allowes us to store object reference
     * to our entitlement run-time repository in an Application "Scope"
     */
     private static ServletContext sc = null;
    /*
     * Static literals to fetch proper values
     * 
     */
    static private String PRODUCT_TYPE     = "ProductType";
    static private String TRANSACTION_TYPE = "TransactionType";
        
    /*
     * Business elements
     */
    
    static private com.dtcc.dnv.otc.common.security.xmldb.EntitlementDefinitions _singleton = null;
    static private Vector privileges;
    
    /**
     * Business method as per spec by TK
     * method: getProductTypes(String entitlement)
     */
     
     public static IPrivilege[] getProductTypes(String entitlement)
         throws XMLDatabaseException
     {
     	getInstance();
     	return  getListOfIPrivileges(entitlement,PRODUCT_TYPE);
     }

    /**
     * Business method as per spec by TK
     * method: getModuleAccess(String entitlements)
     */

     public static IPrivilege[] getModuleAccess(String entitlement) 
         throws XMLDatabaseException     
     {
     	getInstance();
     	return  getListOfIPrivileges(entitlement,TRANSACTION_TYPE);
     }


    /**
     * method: get list of IPrivilege for getProductType and getModuleAccess methods
     * private - for internal use only!
     */
     private static IPrivilege[] getListOfIPrivileges(String entitlement, String type) {
     	Vector prod = new Vector(0,1);
     	Enumeration enum = privileges.elements();
     	while(enum.hasMoreElements()) {
     		Privilege privilege = (Privilege) enum.nextElement();
     		if(privilege.isType(type) && privilege.isAllowed(entitlement)) 
     		{
     			prod.add((IPrivilege)privilege);
     		}
     	}
     	return convertVectorToIPrivileges(prod);
     }

    /**
     * method: convert Vector to Array of the objects
     * private - for internal use only!
     */
     private static IPrivilege[] convertVectorToIPrivileges(Vector prod) {
     	IPrivilege[] array = new IPrivilege[prod.size()];
     	int sz = prod.size();
     	for(int ii=0; ii < sz ; ii++) 
     	{
     	    array[ii] = (IPrivilege)prod.get(ii);
     	}
     	return array ;
     }


    
    /**
     * In order to implement singleton pattern we are adding
     * this method. Done as private to prevent developers
     * from directly using it.
     */
  	private static void getInstance() 
         throws XMLDatabaseException
	{
		if(_singleton == null){
			_singleton = new EntitlementDefinitions();
		}
	}
    
    
	/**
	 * Constructor for EntitlementDefinitions.
	 * we are using singletone pattern.
	 * -- constractor made private to prevent developers from using it!
	 * got to keep public due to limitations on parsing using Digester.
	 */
	public EntitlementDefinitions() 
	     throws XMLDatabaseException
	{
		super();
	    if(privileges == null || privileges.size() == 0) 
	    {
 	        privileges = new Vector(0,1);	    	
	    }
	}
	
	public void addPrivilege(Privilege privilege) {
		privileges.add(privilege);
	}

	/**
	 * Returns the privileges.
	 * @return Vector
	 */
	public Vector getPrivileges() {
		return privileges;
	}
	
    /**
     * This method initialized run-time repository of 
     * entitlements.
     * @see EntitlementDefinitions
     */
    public static void initialize(File entitlements, File rules, ServletContext _sc) 
        throws XMLDatabaseException
    {
        sc = _sc ;
    	parse(entitlements,rules);
    	sc.setAttribute("ET",_singleton);
    }


    private static void parse(File entitlements, File rules) 
        throws XMLDatabaseException
    {
    	try
    	{
            Digester digester = DigesterLoader.createDigester( rules.toURL() );
            digester.setValidating(false);
	        _singleton = (EntitlementDefinitions)digester.parse( entitlements );
            log.debug(_singleton.toString());
    	}
    	catch(Exception e) {
    		throw XMLDatabaseException.createFatal(XMLDatabaseException.SEC_ERROR_CODE_IO_ERROR,e.toString());
    	}    	
    }

	public String toString(){
		String newline = System.getProperty("line.separator");
		StringBuffer sb = new StringBuffer("EntitlementDefinitions {");
		sb.append(newline);
		if(privileges != null ) {
			Enumeration enum = privileges.elements();
			for(int ii=0; enum.hasMoreElements(); ii++) {
				sb.append("\t");
				Object obj = enum.nextElement();
				if(obj == null) 
				   sb.append( "" );
				else
				   sb.append( ((Privilege)obj).toString() );
				sb.append(newline);
			}
		}
		sb.append("}");
		return sb.toString();
	}
	/**
	 * Logger class instance used for logging.  
	 */
	private static final Logger log = Logger.getLogger(EntitlementDefinitions.class);
}
