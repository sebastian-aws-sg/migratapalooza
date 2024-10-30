package com.dtcc.dnv.mcx.util;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

/*
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights
 * reserved.
 * 
 * Depository Trust & Clearing Corporation (DTCC) 55, Water Street, New York, NY
 * 10048 U.S.A All Rights Reserved.
 * 
 * This software may contain (in part or full) confidential/proprietary
 * information of DTCC. ("Confidential Information"). Disclosure of such
 * Confidential Information is prohibited and should be used only for its
 * intended purpose in accordance with rules and regulations of DTCC.
 * 
 * @author Kevin Lake
 * 
 * @version 1.0 Date: September 05, 2007
 */
public class MessageLoggerFactory implements LoggerFactory {
	public MessageLoggerFactory() {

	}

	public Logger makeNewLoggerInstance(String strClassName) {
		return new MessageLogger(strClassName);
	}

}
