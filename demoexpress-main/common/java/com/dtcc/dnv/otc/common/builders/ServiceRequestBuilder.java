package com.dtcc.dnv.otc.common.builders;

import javax.servlet.http.HttpServletRequest;

import com.dtcc.dnv.otc.common.builders.exception.BuilderException;
import com.dtcc.dnv.otc.common.builders.util.BuilderUtil;
import com.dtcc.dnv.otc.common.layers.IServiceRequest;

/**
 * Copyright (c) 2004 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jun 6, 2004
 * @version 1.0
 *
 * This class is used to build a ServiceRequest.
 * 
 * Rev  1.1     June 28, 2004       sav
 * Updated imports.  Forced IServiceRequest to be data type instead of generic Object.  
 * Renamed references to ServiceRequestBuilderException to BuilderException.
 * 
 * Rev  1.2     June 29, 2004       sav
 * Added validateParameters() to validate that input parameters used to create
 * destination object is not null
 * 
 * Rev  1.3     July 23, 2004       sav
 * Recovered work file since it was deleted from PVCS.  This implementation includes a
 * service call to be later used to build request and to set AuditInfo.
 * 
 * Rev  1.4     August 4, 2004      sav
 * Removed extraneous commented code.  Modified newRequest() to use constructObject() to 
 * instantiation a IServiceRequest with the AuditInfo.  Added getAuditInfo() to extract
 * AuditInfo object from the request.
 * 
 * Rev  1.5     February 25, 2005   sav
 * Deprecated newRequest(Object sourceObject, String destinationObjectClassName, String serviceCall).  
 * Implementors must use other method.  Refactored static worker methods to BuilderUtil class.  
 * Added a constructObject() to validate that the destination IServiceRequest object has a 
 * valid constructor (i.e. Take an AuditInfo objects), and if so constructs the object.
 * 
 * Rev	1.6		March 7, 2005		sav
 * Modified newRequest(Object sourceObject, String destinationObjectClassName, String serviceCall) 
 * to cast to IServiceRequest.  Modified
 * newRequest(Object sourceObject, String destinationObjectClassName, String serviceCall, HttpServletRequest req)
 * use new constructServiceRequest().
 */
public class ServiceRequestBuilder {

	/**
	 * This method creates a ServiceRequest object by using properties from a source
     * object.
     * 
	 * @param sourceObject Source object for which the destinationObjectClassName is derived frmo
	 * @param destinationObjectClassName Class name of resulting ServiceRequest
     * @param serviceCall  String value of the service call.  Used to distinguish appropriate rules
     *                      used to build ServiceRequest
	 * @return IServiceRequest Object representation of ServiceRequest
     * @throws BuilderException Exception occured during building DbRequest object
     * @deprecated Deprecated 2005-02-25.  Must use other newRequest().
	 */
	public static IServiceRequest newRequest(Object sourceObject, String destinationObjectClassName, String serviceCall) 
        throws BuilderException{

		// Validate parameters before processing
        new BuilderUtil().validateParameters(sourceObject, destinationObjectClassName);
        IServiceRequest newObject = null;
        try{
    		Class sourceObjectClass = Class.forName(destinationObjectClassName);
            newObject = (IServiceRequest) new BuilderUtil().loadObject(sourceObject, sourceObjectClass);
        }
        catch(Exception e){
            throw new BuilderException(e.getMessage());
        }
        return newObject;
	}


	/**
	 * Creates a new SeriviceRequest based on the source object and the HttpServletRequest.
     * 
     * @param sourceObject Source object for which the destinationObjectClassName is derived from
     * @param destinationObjectClass Class object of resulting ServiceRequest
     * @param serviceCall String value of service call.
	 * @param req HttpServletRequest object used to build SeriveRequest
     * @return IServiceRequest Object representation of ServiceRequest
     * @throws BuilderException Exception occured during building DbRequest object
	 */
	public static IServiceRequest newRequest(Object sourceObject, String destinationObjectClassName, String serviceCall, HttpServletRequest req)
		throws BuilderException{

        IServiceRequest destinationObject = null;
        try{
            Class destinationObjectClass = Class.forName(destinationObjectClassName);
            destinationObject = new BuilderUtil().constructServiceRequest(destinationObjectClass, req, serviceCall);
            new BuilderUtil().copyObject(sourceObject, destinationObject);
        }
        catch(BuilderException srbe){
            throw srbe;
        }
        catch(Exception e){
            throw new BuilderException(e.getMessage());
        }
        return destinationObject;
	}
}
