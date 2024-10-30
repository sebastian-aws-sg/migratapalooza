package com.dtcc.dnv.otc.common.util.converters;

import java.util.Vector;

import org.apache.struts.util.LabelValueBean;

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
 * Converts a participant list to various converter interface formats.
 */
public class DSParticipantListConverter extends DSBaseConverter {

	/**
	 * Converts a participant list to a String
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
					// Append a ; on every instance after the first one to separate each instance
					if(ii>0){
					    sb.append(";");
					}
					// Generate string for participant.
				    sb.append(cpIdNm.getCounterpartyId());
				    sb.append("|");
				    sb.append(cpIdNm.getCounterpartyName());
				}
			}
		}
		// Return string value.
		return (sb.toString());
	}
	
	/**
	 * Converts a participant list to a Vector
	 * 
	 * @see com.dtcc.dnv.otc.common.util.converters.IDSConverter#convertToLabelValueVector(java.lang.Object)
	 */
	public Vector convertToLabelValueVector(Object dataToConvert){
		// Local reference of vector.
		Vector labelValueVector = null;
		// Proces if the instance of the input object is correct.
		if (dataToConvert instanceof CounterpartyIdName[]){
			// Cast input object to accurate instance type.
			CounterpartyIdName[] cpIdNm = (CounterpartyIdName[])dataToConvert;
			// Create Vector based on input object attributes
			labelValueVector = new Vector(0,cpIdNm.length);
			// Iterate through the collection of participants.
			for(int ii=0; ii<cpIdNm.length; ii++){
				// Process if object is not null
				if(cpIdNm[ii] != null)
					labelValueVector.add(new LabelValueBean(cpIdNm[ii].getCounterpartyId()+"-"+cpIdNm[ii].getCounterpartyName(),cpIdNm[ii].getCounterpartyId()));
			}
		}
		// Return Vector value.
		return labelValueVector;
	}
}
