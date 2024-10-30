package com.dtcc.dnv.otc.common.builders.util;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;


/**
 * Copyright © 2004 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55 Water Street,
 * New York, NY 10041
 * 
 * 
 * @author Scott Brown
 * @date Aug 9, 2004
 * @version 1.0
 * 
 * 
 */
public class PropertyUtil
{

	/**
	 * Method copyProperty.  Copies a value into a specified propertyName of an object.  
	 * get/set methods for the property must exist in the destination object
	 * @param destinationObject
	 * @param propertyName
	 * @param value
	 */
	
	private static final Logger logger = Logger.getLogger(PropertyUtil.class);
	
	public void copyProperty(Object destinationObject, String propertyName, String value)
	{
		Class beanClass = destinationObject.getClass();

		try
		{
			Method sourceGetMethod = beanClass.getMethod(buildGetMethodName(propertyName), null);
			Class parmType = sourceGetMethod.getReturnType();
			Method destSetMethod = beanClass.getMethod(buildSetMethodName(propertyName), new Class[] { parmType });
			destSetMethod.invoke(destinationObject, new Object[] { value });
		}
		catch (Exception e)
		{
			//if we got here, then either the method wasn't found in the object, or we had an error setting it.  

			//for testing purposes, print out that something went wrong
			logger.info("exception when setting property: " + propertyName + " in destination object: " + e);
		}
	}

	/**
	 * Method buildGetMethodName.  Build a getter method name based on the property name
	 * 
	 * @param propertyName property the getter method will get 
	 * @return String getter method name.
	 */
	private String buildGetMethodName(String propertyName)
	{
		String methodName = "get" + propertyName.substring(0,1).toUpperCase() + propertyName.substring(1);
		return methodName;
	}
	
	/**
	 * Method buildSetMethodName.  Build a setter method name based on the property name
	 * 
	 * @param propertyName property the setter method will get 
	 * @return String setter method name.
	 */
	private String buildSetMethodName(String propertyName)
	{
		String methodName = "set" + propertyName.substring(0,1).toUpperCase() + propertyName.substring(1);
		return methodName;
	}



}
