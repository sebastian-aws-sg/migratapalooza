package com.dtcc.dnv.otc.common.security.xmldb;

import java.io.Serializable;

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
 */
public class Acl implements Serializable 
{

    private String acl ; 
	/**
	 * Constructor for acls.
	 */
	public Acl() {
		super();
	}

	/**
	 * Constructor for acls.
	 */
	public Acl(String acl) {
		super();
		this.acl = acl;
	}

	/**
	 * Returns the acl.
	 * @return Vector
	 */
	public String getAcl() {
		return acl;
	}

	/**
	 * Sets the acl.
	 * @param acl The acl to set
	 */
	public void setAcl(String acl) {
		this.acl = acl;
	}
		
	public String toString(){
		return this.acl;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 * Overriden equals method from superclass Object
	 * will allow us to have a fast compare between
	 * objects
	 */
	public boolean equals(Object arg0) 
	{
		if(arg0 instanceof Acl)
 		    return acl.equals(arg0.toString());
 		else
 		    return false;
// 		    throw new Exception("Incompatible object types. Make sure both side are co.dtcc.dnv.otc.security.xmldb.Acl.java types.");
	}

}
