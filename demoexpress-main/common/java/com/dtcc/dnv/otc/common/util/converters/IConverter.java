package com.dtcc.dnv.otc.common.util.converters;

import java.util.Vector;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jul 21, 2007
 * @version 1.0
 *
 * Interface class used by all concrete converter classes
 */
public interface IConverter {
	// Used to convert object to a String
	public String convertToString(Object dataToConvert);
	// Used to convert object to a Vector.
	public Vector convertToLabelValueVector(Object dataToConvert);
	// Used to convert object to a String Array.
	public String[] convertToStringArray(Object dataToConvert);
}
