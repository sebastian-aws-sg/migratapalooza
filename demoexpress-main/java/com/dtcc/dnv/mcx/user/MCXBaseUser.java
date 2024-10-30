package com.dtcc.dnv.mcx.user;

import com.dtcc.dnv.otc.common.security.model.DSCUOUser;
import com.dtcc.dnv.otc.common.security.model.ExUser;

/*
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
 * 
 * @author Kevin Lake
 * @version 1.0
 * Date: September 05, 2007
 */
abstract class MCXBaseUser extends DSCUOUser {
	
	/**
	 * Default constructor
	 */
	MCXBaseUser(ExUser user){
		super(user);
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.AbstractDSCUOUser#setProdIndicator(java.lang.String)
	 */
	protected void setProdIndicator(String prodIndicator){
		super.setProdIndicator(prodIndicator);
	}
	
	/**
	 * @see com.dtcc.dnv.otc.common.security.model.AbstractDSCUOUser#setCurrentSystem(java.lang.String)
	 */
	protected void setCurrentSystem(String currentSystem){
		super.setCurrentSystem(currentSystem);
	}
	
	/**
	 * @param isSuperUser The isSuperUser to set.
	 */
	protected void setSuperUser(boolean isSuperUser) {
		super.setSuperUser(isSuperUser);
	}
	
	/**
	 * @param hasInquiry The hasInquiry to set.
	 */
	protected void setHasInquiry(boolean hasInquiry) {
		super.setHasInquiry(hasInquiry);
	}
	
	/**
	 * @param hasUpdate The hasUpdate to set.
	 */
	protected void setHasUpdate(boolean hasUpdate) {
		super.setHasUpdate(hasUpdate);
	}
}
