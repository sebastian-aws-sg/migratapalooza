
package com.dtcc.dnv.otc.common.security.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.dtcc.dnv.otc.common.exception.UserException;
/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation 55 Water Street,
 * New York, New York, 10041 All Rights Reserved
 * 
 * @author Rajeev
 * @date Jul 25, 2007
 * @version 1.0
 * 
 * Initial revision of EntitlementFactory
 * 
 * This class creates the entitlement request to create the entitlement for the logged in user 
 */
public class ResourceEntitlementRequest implements IEntitlementRequest {

	private List entitlementSystem;

	private List entitlementValue; // "D" or "I"

	/**
	 * Create a ResourceEntitlementRequest  to create an entitlement for the logged user
	 * call setEntitlements to set the access systems and entitlement type(D/I)
	 * @param accessSystems
	 *  	Vector - Holds the entSystems
	 * @param entType
	 * 	 	Vector - Holds the entValue
	 * @throws UserException
	 */

	public ResourceEntitlementRequest(Vector accessSystems, Vector entValue)
			throws UserException {
		setEntitlements(convertToArray(accessSystems), convertToArray(entValue));
	}

	/**
	 * @return Returns the entitlementRoleList.
	 */
	public List getEntitlementValue() {
		return entitlementValue;
	}

	/**
	 * @return Returns the entitlementSystem.
	 */
	public List getEntitlementSystem() {
		return entitlementSystem;
	}

	/**
	 * @param entitlementSystem The entitlementSystem to set.
	 */
	private void setEntitlements(String[] accessSystems, String[] entType) {
		entitlementSystem = new ArrayList(accessSystems.length);
		entitlementValue = new ArrayList(entType.length);
		for (int index = 0; index < accessSystems.length; index++)
			entitlementSystem.add(new EntitlementSystem(accessSystems[index]));
		for (int index = 0; index < entType.length; index++)
			entitlementValue.add(new ResourceEntitlementType(entType[index]));
	}

	private String[] convertToArray(Object object) throws UserException {
		Class clazz = object.getClass();
		String clazzName = clazz.getName();
		if (clazz.isArray())
			return (String[]) object;
		if (clazzName.equals("java.util.Vector")) {
			return copyToStrArray(((Vector) object).toArray());
		} else if (clazzName.equals("java.util.List")
				|| clazzName.equals("java.util.ArrayList")) {
			return copyToStrArray(((List) object).toArray());
		}
		else
			throw UserException.createFatal("1001", "Could not parse the vector");
		
	}

	private String[] copyToStrArray(Object[] object) {
		String[] values = new String[object.length];
		for (int index = 0; index < object.length; index++) {
			values[index] = (String) object[index];
		}
		return values;
	}

}
