package com.dtcc.dnv.otc.common.signon.entitlement;

import com.dtcc.dnv.otc.common.exception.UserException;

/**
 * @author vxsubram
 * @date Aug 13, 2007
 * 
 * This class contains the entitlement details of the non-branch user.
 * 
 * @version	1.1					September 2, 2007			sv
 * Checked in for Cogizant.  Updated commetns and javadoc.  Created
 * private constructor and factory method to instantiate an instance of
 * a UFOOBean based on the string value of it.
 * 
 * @version	1.2					September 16, 2007			sv
 * Checked in for Cognizant.  private constructor and factory method to instantiate an instance of
 * a UFOBean based on the string value of format oCode_entitlementcode is introduced.
 */
public class UFOBean {
	// Holds the originator code
	private String oCode;

	// Holds the entitlement value
	private String entitlement;

	// Holds the description of the oCode and Entitlement value;
	private String description;
	
	/*
	 * Accepts signonEntitlementValue string as oCode_entitlementcode format and sets the appropriate value.
	 * @param signonEntitlementValue the string signonEntitlementValue as oCode_entitlementcode format
	 * @throws UserException
	 */
	private UFOBean(String signonEntitlementValue) throws UserException {
		if(signonEntitlementValue.indexOf("_") > 0){
    		this.setOCode(signonEntitlementValue.substring(0,signonEntitlementValue.indexOf("_")));
    		this.setEntitlement(signonEntitlementValue.substring(signonEntitlementValue.indexOf("_") + 1,signonEntitlementValue.length()));
		}else{
			UserException ue = UserException.createFatal(UserException.SEC_ERROR_CODE_ENTITLEMENT,"Entitlement value is invalid");
			throw ue;
		}
	}
	
	/*
	 * Gets an instance of UFOBean object
	 */
	public static UFOBean createInstance(String signonEntitlementValue) throws UserException {
		UFOBean oUFObean = new UFOBean(signonEntitlementValue);		
		return oUFObean;
	}
	/**
	 * @return the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return the entitlement code.
	 */
	public String getEntitlement() {
		return entitlement;
	}
	/**
	 * @return the originator code.
	 */
	public String getOCode() {
		return oCode;
	}
	/**
	 * @param description The description to set.
	 */
	void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @param entitlement The entitlement to set.
	 */
	void setEntitlement(String entitlement) {
		this.entitlement = entitlement;
	}
	/**
	 * @param code The originator code to set.
	 */
	void setOCode(String code) {
		oCode = code;
	}
}
