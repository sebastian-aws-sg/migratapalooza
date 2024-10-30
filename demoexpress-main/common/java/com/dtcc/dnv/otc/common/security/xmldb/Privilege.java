package com.dtcc.dnv.otc.common.security.xmldb;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

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
 * Rev  1.2     October 8, 2004     sav
 * Modified  isModuleAccess() to correct bug in IF statment for the product key.  Changed
 * if( this.key.equalsIgnoreCase(key) ) to if( this.key.equalsIgnoreCase(productKey) ).
 */
public class Privilege 
    implements Serializable, IPrivilege
{

    private String type ;
    private String key ;    
    private Vector acls ;     
    private String description ;         
	/**
	 * Constructor for Privilege.
	 */
	public Privilege() 
	{
		super();
	    acls = new Vector(0,1);		
	}

	public void addAcl(Acl acl) 
	{
		this.acls.add(acl);
	}

    /**
     * Required methods as per TK desing
     */
	/**
	 * Method isKey 
	 *  allows to verify a correct version of product
	 * 
	 * @param String permission
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns true if this is a valid type of access
	 */
	protected boolean isType(String type) {
		if(this.type.equalsIgnoreCase(type)) 
			return true;
		else
		    return false;
	}

    /**
     * Required methods as per TK desing
     */
	/**
	 * Method isAllowed 
	 *  to match entitlement vs. existing subset
	 * 
	 * @param String permission
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns true if this is a valid type of access
	 */
	protected boolean isAllowed(String permission) {
	 	
        if(acls.indexOf(new Acl(permission)) >= 0)
            return true;
        else
            return false;
	
	}
	/**
	 * Method isProductTypes
	 * @param String permission
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns true if the user is allowed to read records from a system
	 */
    public boolean isProductKey(String productKey) 
    {
    	if(this.key.equalsIgnoreCase(productKey)) 
    		return true;
    	else
    		return false;
    } 

	/**
	 * Method isProductTypes
	 * @param String permission
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns IPrivilege if the user is allowed to read records from a system
	 */
    public IPrivilege getProductKey(String productKey) 
    {
    	if(isProductKey(productKey))
    	    return (IPrivilege)this;
    	else
    	    return null;
    }

	/**
	 * Method isAllowed
	 * @param String key
	 * @param String permission
	 * @return boolean
	 * @throws nothing
	 * 
	 * Returns true if the user is allowed to update records in a system
	 */
	 public boolean isModuleAccess(String productKey, String permission) 
	 {
	 	if( this.key.equalsIgnoreCase(productKey) ) 
	 	{
            if(acls.indexOf(new Acl(permission)) >= 0)
                return true;
            else
                return false;
	 	}
	 	return false;
	 }

	/**
	 * Method getAllowed
	 * @param String key
	 * @param String permission
	 * @return IPrivilege
	 * @throws nothing
	 * 
	 * Returns true if the user is allowed to update records in a system
	 */
	 public IPrivilege getModuleAccess(String key, String permission) 
	 {
	 	if(isModuleAccess(key, permission))
	 	    return (IPrivilege)this ;
	 	else
	 	    return null;
	 }
	/**
	 * Returns the key.
	 * @return String
	 */
	public String getKey() 
	{
		return key;
	}

	/**
	 * Returns the type.
	 * @return String
	 */
	public String getType() 
	{
		return type;
	}

	/**
	 * Sets the key.
	 * @param key The key to set
	 */
	public void setKey(String key) 
	{
		this.key = key;
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(String type) 
	{
		this.type = type;
	}
		
	/**
	 * Returns the privilege.
	 * @return Vector
	 */
	public Vector getAcls() 
	{
		return acls;
	}

	/**
	 * Sets the privilege.
	 * @param privilege The privilege to set
	 */
	public void setAcls(Vector acls) 
	{
		this.acls = acls;
	}
	
	/**
	 * Returns the description.
	 * @return String
	 */
	public String getDescription() 
	{
		return description;
	}

	/**
	 * Sets the description.
	 * @param description The description to set
	 */
	public void setDescription(String description) 
	{
		this.description = description;
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer("Privilege: {");
		sb.append("  type='");
		sb.append(this.type);
		sb.append("',key='");
		sb.append(this.key);
		sb.append("', acls {");
		if(acls != null ) {
			Enumeration enum = acls.elements();
			for(int ii=0; enum.hasMoreElements(); ii++) {
				if(ii > 0)
				    sb.append("', '");
				else 
				    sb.append("'");
				Object privilege = enum.nextElement();
				
				if(privilege != null)
				    sb.append( ((Acl)privilege).toString() );
				
				sb.append("'");
			}
		}
		sb.append("}");
		sb.append("',description='");
		sb.append(this.description);		
		sb.append("}");		
		return sb.toString();
	}

}
