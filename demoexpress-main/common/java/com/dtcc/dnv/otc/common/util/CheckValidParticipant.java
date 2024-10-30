package com.dtcc.dnv.otc.common.util;

import com.dtcc.dnv.otc.common.security.model.IUser;

/**
 * Copyright © 2004 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55 Water Street,
 * New York, NY 10041
 * 
 * 
 * @author sbrown
 * @date Jan 24, 2005
 * @version 1.0
 */
public class CheckValidParticipant
{

	/**
	 * Method isValidParticipant.
	 * @param user
	 * @param id
	 * 
	 * Verifies that the id is either the user's originator or is a valid family member.
	 * 
	 * if id = originator, return true.
	 * if id = 4 chars and 0000 + id is in family, return true.
	 * if id is in family, return true
	 * else, return false.
	 * @return boolean
	 */
	public static boolean isValidParticipant(IUser user, String id)
	{
		//is it the originator code?
		if (id.equals(user.getOriginatorCode()))
			return true;
		//not originator.  is it 4 chars?  if so, try padding with 4 zeros
		else if (id.length() == 4 && user.isFamilyMember("0000" + id))
			return true;
		//is id in the family?
		else if (user.isFamilyMember(id))
			return true;
		else
			return false;
	}

}
