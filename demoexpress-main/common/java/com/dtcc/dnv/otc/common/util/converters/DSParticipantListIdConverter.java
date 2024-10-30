package com.dtcc.dnv.otc.common.util.converters;

import com.dtcc.dnv.otc.legacy.CounterpartyIdName;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jul 13, 2007
 * @version 1.0
 *
 * Converts a participant id list to various converter interface formats.
 */
public class DSParticipantListIdConverter extends DSBaseConverter {

	/**
	 * Converts a participant id list to a String
	 * 
	 * @see com.dtcc.dnv.otc.common.util.converters.IDSConverter#convertToString(java.lang.Object)
	 */
	public String convertToString(Object dataToConvert) {
		// Local instance of StringBuffer
		StringBuffer sb = new StringBuffer();
		// Proces if the instance of the input object is correct.
		if (dataToConvert instanceof CounterpartyIdName[]){
			// Cast input object to accurate instance type.
			CounterpartyIdName[] participantList = (CounterpartyIdName[])dataToConvert;
			// Iterate through the collection of participants.
			for(int ii=0; ii < participantList.length; ii++){
				// Get participant object reference
				CounterpartyIdName cpIdNm = (CounterpartyIdName) participantList[ii];
				// Process if it is not null
				if(cpIdNm != null) {
					// Append a , on every instance after the first one to separate each instance
					if(ii>0){
					    sb.append(",");
					}
					// Generate string for participant id
				    sb.append(cpIdNm.getCounterpartyId());
				}
			}
		}
		// Return string value.
		return (sb.toString());
	}
}
