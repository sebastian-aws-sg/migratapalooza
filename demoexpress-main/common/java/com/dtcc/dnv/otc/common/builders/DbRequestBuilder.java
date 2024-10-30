package com.dtcc.dnv.otc.common.builders;

import com.dtcc.dnv.otc.common.builders.exception.BuilderException;
import com.dtcc.dnv.otc.common.builders.util.BuilderUtil;
import com.dtcc.dnv.otc.common.layers.IDbRequest;
import com.dtcc.dnv.otc.common.security.model.AuditInfo;

/**
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jun 6, 2004
 * @version 1.0
 *
 * This class is used to build a DbRequest.
 * 
 * Rev  1.1     June 29, 2004       sav
 * Added validateParameters() to validate that input parameters used to create
 * destination object is not null
 * 
 * Rev  1.2     July 3, 2004        sav
 * Removed constructor where HttpServletRequest was passed, since that object should never
 * really exist in the DB layer.  Removed extraneous imports.  Update comments.  
 * Updated javadoc.
 * 
 * rev 1.3 scb 1/10/2005
 * added newRequest(Object sourceObject, String destinationObjectClassName, String requestId, String serviceCall, AuditInfo ai).
 * also added constructObject(Class destinationObjectClass, String requestId, String serviceCall, AuditInfo ai) method
 * 
 * Rev	1.4		March 7, 2005		sav
 * Updated methods to use the BuilderUtil class and removed extraneous methods that already exist
 * in BuilderUtil.  Deprecated newRequest(Object sourceObject, String destinationObjectClassName, String serviceCall )
 * 
 * ****************************************************************************
 * PROJECT MOVED TO NEW REPOSITORY.
 * @version	1.1					October 18, 2007			sv
 * Removed unused imports.
 */
public class DbRequestBuilder {

	/**
	 * This method creates a DbRequest object by using properties from a source
     * object.
     * 
	 * @param sourceObject Source object for which the destinationObjectClassName is derived from
	 * @param destinationObjectClassName Class name of resulting DbRequest
     * @param serviceCall  String value of the service call.  Used to distinguish appropriate rules
     *                      used to build DbRequest
	 * @return IDbRequest Object representation of DbRequest
	 * @throws BuilderException
	 * @deprecated As of 20050229.  Must use other newRequest().
	 */
	public static IDbRequest newRequest(Object sourceObject, String destinationObjectClassName, String serviceCall ) 
        throws BuilderException{

		BuilderUtil bu = new BuilderUtil();
        bu.validateParameters(sourceObject, destinationObjectClassName);
        
        IDbRequest newObject = null;
        try{
    		Class sourceObjectClass = Class.forName(destinationObjectClassName);
            newObject = (IDbRequest) bu.loadObject(sourceObject, sourceObjectClass);
        }
        catch(Exception e){
            throw new BuilderException(e.getMessage());
        }
        return newObject;
	}


	/**
	 * Creates a new DbRequest based on the source object and the requestId, serviceCall, AuditInfo.
	 * 
	 * Method newRequest.
	 * @param sourceObject Source object for which the destinationObjectClassName is derived from
	 * @param destinationObjectClassName Class name of resulting DbRequest
	 * @param requestId request id for the procedure
	 * @param serviceCall service call
	 * @param ai Audit Info
	 * @return IDbRequest
	 * @throws BuilderException
	 */
	public static IDbRequest newRequest(Object sourceObject, String destinationObjectClassName, String requestId, String serviceCall, AuditInfo ai)
		throws BuilderException{

        IDbRequest destinationObject = null;
        try{
			BuilderUtil bu = new BuilderUtil();
            Class destinationObjectClass = Class.forName(destinationObjectClassName);
            destinationObject = bu.constructDbRequest(destinationObjectClass, requestId, serviceCall, ai);
            bu.copyObject(sourceObject, destinationObject);
        }
        catch(BuilderException srbe){
            throw srbe;
        }
        catch(Exception e){
            throw new BuilderException(e.getMessage());
        }
        return destinationObject;
	} 
	
	
	/**
	 * This method copies the properties of the source object to the destination object
     * @param sourceObject Source object for which the destinationObjectClassName is derived from
     * @param destinationObject Detination object of resulting copy
     * @throws BuilderException Exception occured during building object
	 */
	public static void copyObject(Object sourceObject, Object destinationObject) throws BuilderException{
			new BuilderUtil().copyObject(sourceObject, destinationObject);
	}
}
