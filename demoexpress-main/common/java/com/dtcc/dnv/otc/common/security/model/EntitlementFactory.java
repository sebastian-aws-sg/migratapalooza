
package com.dtcc.dnv.otc.common.security.model;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.exception.XMLDatabaseException;
import com.dtcc.dnv.otc.common.util.SystemMappings;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation 55 Water Street,
 * New York, New York, 10041 All Rights Reserved
 * 
 * @author Rajeev
 * @date Jul 17, 2007
 * @version 1.0
 * 
 * Initial revision of EntitlementFactory
 * 
 * This class creates the entitlement for the logged in user and sets in User
 * object
 * 
 * @version	1.1				October 18, 2007			sv
 * Updated logger to be static final.
 */

public class EntitlementFactory {

	private static final Logger logger = Logger.getLogger(EntitlementFactory.class);

	/**
	 * Create an entitlement list for the logged in User call
	 * hasSystemAccessMethod to validate whether the user has access to the
	 * system and then creates the entitlement list
	 * 
	 * @return IEntitlement object for the user, or <code>null</code> if
	 *         <code>IEntitlement</code> can not be found is not readable.
	 * @throws UserException ,
	 *             XMLDatabaseException
	 */

	public static IEntitlement getEntitlements(IEntitlementRequest eRequest)
			throws UserException, XMLDatabaseException {
		IEntitlement entitlements = new EntitlementList();
		String[] sisIds = SystemMappings.getSisIds(); // Get a list of DerivSERV systems (i.e. "dsv","dpr")
		for (int idx = 0; idx < sisIds.length; idx++) {
			String sisId = sisIds[idx];
			if (hasSystemAccess(sisId, eRequest)) {
				//if (user.hasSystemAccess(sisIds[idx])) {
				try {
					entitlements
							.addEntitlement(sisId, getEntitlement(eRequest));
				} catch (XMLDatabaseException e) {
					logger.error(" Error occured in EntitlementFactory :-" + e);
					throw e;
				} catch (UserException exp) {
					logger.error(" Error occured in EntitlementFactory :-"
							+ exp);
					throw exp;
				}
			}
		}
		return entitlements;
	}

	/**
	 * Method validateSystemAccess
	 * 
	 * @param String
	 * @return boolean
	 * 
	 * Used to validate the user's access to the system (i.e. DSV, DPR)
	 */
	private static boolean hasSystemAccess(String sysName,
			IEntitlementRequest eRequest) {
		if (eRequest instanceof ResourceEntitlementRequest) {
			List systemList = eRequest.getEntitlementSystem();
			Iterator iterator = systemList.iterator();
			while (iterator.hasNext()) {
				IEntitlementSystem element = (IEntitlementSystem) iterator
						.next();
				if (element.getSystemName().equalsIgnoreCase(sysName))
					return true;
			}

		}
		return false;
	}

	/**
	 * Method getEntitlement
	 * 
	 * @param DSCUOUser
	 *            user
	 * @return Privilege
	 * 
	 * Returns the user's entitlement for a system.
	 * @throws UserException
	 */
	private static Entitlement getEntitlement(IEntitlementRequest eRequest)
			throws UserException {
		Entitlement ent = null; // default entitlement has no privileges
		//Object[] accessList = user.getRoleList().keySet().toArray();
		if (eRequest instanceof ResourceEntitlementRequest) {
			List resourceEntList = eRequest.getEntitlementValue();
			Iterator iterator = resourceEntList.iterator();
			while (iterator.hasNext()) {
				IEntitlementType element = (ResourceEntitlementType) iterator
						.next();
				ent = new Entitlement(element.getValue());
			}

		}
		return ent; // return the entitlement object
	}

}
