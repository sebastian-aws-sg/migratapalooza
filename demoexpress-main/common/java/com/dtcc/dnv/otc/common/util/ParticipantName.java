package com.dtcc.dnv.otc.common.util;

import com.dtcc.dnv.otc.common.security.model.IUser;
import com.dtcc.dnv.otc.legacy.CounterpartyIdName;

/**
 * Copyright © 2004 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55 Water Street,
 * New York, NY 10041
 * 
 * 
 * @author sbrown
 * @date Sep 24, 2004
 * @version 1.0
 * 
 * class to get the name of a participant or a counterparty given the Id and user objects
 */
public class ParticipantName
{

	/**
	 * Method getName.
	 * @param id
	 * @param user
	 * @return String
	 */
	public static String getName(String id, IUser user)
	{
		if (id.equals(""))
			return id;
		CounterpartyIdName[] ids = user.getCounterPartyList();
		for (int i = 0; i < ids.length; i++)
		{
			if (ids[i].getCounterpartyId().equals(id))
			{
				return ids[i].getCounterpartyName();
			}
		}
		//if not found, return empty string
		return "";
	}
}
