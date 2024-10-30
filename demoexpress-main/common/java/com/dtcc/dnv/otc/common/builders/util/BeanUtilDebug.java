package com.dtcc.dnv.otc.common.builders.util;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

/**
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jun 18, 2004
 * @version 1.0
 *
 * Debug utility class to debug bean refelction by display various properties of the 
 * object (i.e. parameters, methods, etc);
 * 
 * Rev 1.1.     June 25, 2004       sav
 * Updated comments and javadoc.  Changed references to getDeclaredMethods() to 
 * getMethods().
 */
public class BeanUtilDebug {

	/**
	 * Method displayMethodParameterTypes.  Used to display all the parameters types
     * of a method.
     * 
	 * @param method Method object to debug.
	 */
	
	private static final Logger log = Logger.getLogger(BeanUtilDebug.class);
	
	public void displayMethodParameterTypes(Method method) {

		Class[] parmTypes = method.getParameterTypes();

		for (int k = 0; k < parmTypes.length; k++) {
			log.info("Parm types #" + k + parmTypes[k].getName());
		}
	}

	/**
	 * Method displayMethodsAndParameterTypes.  Used to display all the methods and 
     * parameters types of a class
     * 
	 * @param inClass Class object to debug
	 */
	public void displayMethodsAndParameterTypes(Class inClass) {

		Method[] methods = inClass.getMethods();
        
		for (int j = 0; j < methods.length; j++) {
			log.info("Method #" + j + methods[j].getName());
            displayMethodParameterTypes(methods[j]);
		}
	}
    
    
	/**
	 * Method displayMethods.  Used to display all the methods of a class
     * 
     * @param inClass Class object to debug
	 */
    public void displayMethods(Class inClass){
        
        Method[] methods = inClass.getMethods();
        
        for (int j = 0; j < methods.length; j++) {
            log.info("Method #" + j + methods[j].getName());
        }
    }
}
