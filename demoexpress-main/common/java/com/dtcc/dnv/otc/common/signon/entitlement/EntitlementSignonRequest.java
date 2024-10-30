package com.dtcc.dnv.otc.common.signon.entitlement;

import com.dtcc.dnv.otc.common.layers.AbstractServiceRequest;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;
import com.dtcc.dnv.otc.common.security.model.ExUser;


/**
 * @author vxsubram
 * @date Aug 13, 2007
 * @version 1.0
 * 
 *  This class contains the entitlement request parameters necessary to 
 *  process the entitlement workflow
 * 
 * @version	1.1				October 25, 2007			sv
 * Checked in for Cognizant.  Added user and product id property, setter, and getter.
 */
public class EntitlementSignonRequest extends AbstractServiceRequest {

	//private instance of ExUser
	private ExUser user = null;
	
	// Holds the product id 
	private String productId = null;
	
	/**
	 * @param auditInfo
	 */
	public EntitlementSignonRequest(AuditInfo auditInfo) {
		super(auditInfo);
	}
	
	/*
	 * Returns the user object
	 * @return Returns the ExUser instance.
	 */ 
	public ExUser getUser(){
		return user;
	}
	/*
	 * Sets the user object
	 * @param user instance of ExUser
	 */
	public void setUser(ExUser user){
		this.user = user;
	}
	/**
	 * Gets the product id of the application as configured in UMG
	 * @return Returns the productid.
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * Sets the product id of the application as configured in UMG
	 * @param productid The productid to set.
	 */
	public void setProductId(String productid) {
		this.productId = productid;
	}
}
