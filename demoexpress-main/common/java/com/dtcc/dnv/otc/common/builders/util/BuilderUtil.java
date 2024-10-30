package com.dtcc.dnv.otc.common.builders.util;

import javax.servlet.http.HttpServletRequest;

import com.dtcc.dnv.otc.common.builders.exception.BuilderException;
import com.dtcc.dnv.otc.common.exception.UserException;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;
import com.dtcc.dnv.otc.common.security.model.IUser;
import com.dtcc.dnv.otc.common.security.model.UserFactory;

/**
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author author
 * @date Feb 25, 2005
 * @version 1.0
 * 
 * This class is used as a utility method for the Builder classes
 * 
 * Rev	1.1			March 7, 2005		sav
 * Added import for IDbRequest.  Updated code and comments to make constructXXX()
 * specific to the builder request, and other methods to be more generic.
 */
public class BuilderUtil {
	
	/**
	 * Constructs Object passing parameters to constructors.
     * 
	 * @param destinationObjectClass Class object of the destination object
	 * @param request HttpServletRequest
     * 
	 * @return IServiceRequest Destination object
	 * @throws Exception Most likely a reflection exception has occurred.
	 */
    public IServiceRequest constructServiceRequest(Class destinationObjectClass, HttpServletRequest request, String serviceCall) 
        throws Exception{
        
        IServiceRequest destinationObject = null;
        
		// Retrieve AuditInfo object from request
        AuditInfo ai = getAuditInfo(request);
        ai.setTransaction(serviceCall);
        
        try{
            BeanUtil bu = new BeanUtil();
            destinationObject = (IServiceRequest) bu.constructObject(destinationObjectClass, new Object[] {ai});
        }
        catch (Exception e){
            throw new BuilderException("Error constructing object because of invalid constructor.  " + e.getMessage());
        }
        
        return destinationObject;
    }
    
    
	/**
	 * Constructs IDbRequest object passing parameters to constructors.
	 * 
	 * Method constructObject.
	 * @param destinationObjectClass
	 * @param requestId
	 * @param serviceCall
	 * @param ai
	 * @return IDbRequest
	 * @throws Exception
	 */
    public IDbRequest constructDbRequest(Class destinationObjectClass, String requestId, String serviceCall, AuditInfo ai) throws Exception
    {
        IDbRequest dbRequest = null;
        
        Class [] constructorParms = new Class[]{requestId.getClass(), serviceCall.getClass(), ai.getClass()};
		BeanUtil bu = new BeanUtil();
        dbRequest = (IDbRequest) bu.constructObject(destinationObjectClass, new Object[]{requestId, serviceCall, ai});
        
        return dbRequest;
    } 
    
    
	/**
	 * Gets AuditInfo object from HttpServletRequest
     * 
	 * @param request HttpServletRequest object
	 * @return AuditInfo 
	 * @throws UserException
	 */
    public AuditInfo getAuditInfo(HttpServletRequest request) throws UserException{

        IUser user = UserFactory.getUser(request);
        return user.getAuditInfo();
    }
    
    
	/**
	 * This methods loads the destination object.  It preforms a copy of the object, but
     * in the future it will implement rules dependent on a serviceCall
     * 
     * @param sourceObject Source object for which the destinationObjectClassName is derived from
     * @param destinationObjectClass Class object of resulting object
     * @return Object Object representation of loaded object
     * @throws BuilderException Exception occured during building DbRequest object
     * @throws IllegalAccessException Reflection exceptions
     * @throws InstantiationException Reflection exceptions
	 */
	public Object loadObject(Object sourceObject, Class destinationObjectClass)
		throws BuilderException, IllegalAccessException, InstantiationException{

		// Instantiate destination object
		Object destinationObject = destinationObjectClass.newInstance();
		copyObject(sourceObject, destinationObject);

		return destinationObject;
	}
	
	
	/**
	 * This method copies the properties of the source object to the destination object
     * @param sourceObject Source object for which the destinationObjectClassName is derived from
     * @param destinationObject Detination object of resulting copy
     * @throws BuilderException Exception occured during building DbRequest object
	 */
	public void copyObject(Object sourceObject, Object destinationObject) 
        throws BuilderException{

		// Validate the objects are valid before processing
        validateParameters(sourceObject, destinationObject);
        try{
            BeanUtil bu = new BeanUtil();
            bu.copyBeanProperties(sourceObject, destinationObject);
        }
        catch(Exception e){
            throw new BuilderException(e.getMessage());
        }
	}
	
	
    /**
     * Method validates that the parameters passed in are not null
     * @param sourceObject Source object for which the destinationObjectClassName is derived from
     * @param destinationObject destination object
     * @throws BuilderException Exception which occurs when source or destination object
     * is null
     */
    public void validateParameters(Object sourceObject, Object destinationObject) throws BuilderException{
        
        if (sourceObject == null)
            throw new BuilderException("Source object is null");
            
        if (destinationObject == null)
            throw new BuilderException("Destination object is null");
    }

}
