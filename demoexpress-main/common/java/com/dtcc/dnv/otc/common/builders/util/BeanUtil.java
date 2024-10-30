package com.dtcc.dnv.otc.common.builders.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jun 15, 2004
 * @version 1.0
 *
 * Utility class used manipulate objects.
 * 
 * Rev 1.1      June 25, 2004       sav
 * Updated comments and changed references to getDeclaredMethod() and getDeclaredFields() to
 * getMethod() and getFields().  Added private method to format "get", so that
 * it can support future method implemenations (i.e. boolean = isField()).  Modified to look
 * at available setter method names in the destination object and looking up a 
 * translating getter method in the source object.
 * 
 * Rev 1.2		February 25, 2005	sav
 * Added constructObject() to construct object based on Array of objects.
 */
public class BeanUtil {

	/**
	 * Method copyBeanProperties.  Copies properties from the sourceObject to the
     * destinationObject.  This method looks up available setter methods in the destination
     * object and then calls the appropriate getter method in the source object to 
     * obtain the value.  Then it usees this value to call the setter method on the 
     * destination object.
     * 
	 * @param sourceObject Object to copy from
	 * @param destinationObject Object to copy to
	 * @throws IllegalAccessException Reflection exception
	 * @throws InvocationTargetException Reflection exception
	 */
	public void copyBeanProperties(Object sourceObject, Object destinationObject) 
        throws IllegalAccessException, InvocationTargetException {

		Class destinationObjectClass = destinationObject.getClass();       
//        try{ BeanUtilDebug bud = new BeanUtilDebug(); bud.displayMethods(destinationObjectClass);}catch(Exception e){e.printStackTrace();}
        
        // Get all the available methods in the destination object
        Method[] destinationMethods = destinationObjectClass.getMethods();


        for (int i = 0; i < destinationMethods.length; i++) {
            Method destinationObjMethods = destinationMethods[i];

            // Look for setter methods that can be implemented and therefore be used
            // to copy the property. (i.e. setField(Object field))
            if (destinationObjMethods.getName().toUpperCase().startsWith("SET")){

                String setMethodName = destinationObjMethods.getName();
                String getMethodName = buildGetMethodName(destinationObjMethods);
                try {
                    // Get the getter method of the source object
                    Method sourceGetMethod = sourceObject.getClass().getMethod(getMethodName, null);
    
                    // Invoke the getter method on the source object and retrieve the value.
                    Class parmType = sourceGetMethod.getReturnType();
                    Object value = sourceGetMethod.invoke(sourceObject, null);

                    // Invoke the settter method on the destination object and set the value(s)
                    Method destSetMethod = destinationObjectClass.getMethod(setMethodName, new Class[] { parmType });
                    destSetMethod.invoke(destinationObject, new Object[] { value });
                }
                catch (NoSuchMethodException nsme) {
                    // Ignore.  Not an error if sourceObject does not have property
    //              System.out.println("No such method: " + nsme.getMessage());
                }
            }
        }

	}
 

	/**
	 * Method buildGetMethodName.  Build a getter method name based on the 
     * setting method name.
     * 
	 * @param destinationObjMethod Method used to set on the destination object
	 * @return String value of getter method name.
	 */
    private String buildGetMethodName(Method destinationObjMethod){
        
        String destinationObjMethodName = destinationObjMethod.getName();
        String methodName = "g" + destinationObjMethodName.substring(1);
        return methodName;
    }
    
    
	/**
	 * Constructs object base on Class and constructor parameters.  First builds Class array
     * of the parameter types based on the passed in Object array.  Instantiates a constructor
     * obejcts and creates a new instance on the request object.
     * 
	 * @param destinationObjectClass Class object of the object to be constructed.
	 * @param constructorParms Object [] of parameters to the constructor.
	 * @return Object Constructed object
	 * @throws NoSuchMethodException Exception thrown if constructor is not found.
	 */
    public Object constructObject(Class destinationObjectClass, Object [] constructorParms) 
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException{

            Class [] constructorParmTypes = new Class[constructorParms.length];
            
            for (int i =0; i < constructorParmTypes.length; i++){
                constructorParmTypes[i] = constructorParms[i].getClass();
            }
            
            Constructor constructor = destinationObjectClass.getConstructor(constructorParmTypes);
            Object constructedObject = constructor.newInstance(constructorParms);
            return constructedObject;
    }
}
