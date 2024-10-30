package com.dtcc.dnv.otc.common.signon.modules;

import java.util.Hashtable;

import com.dtcc.dnv.otc.common.signon.config.DSSignonConfiguration;
import com.dtcc.dnv.otc.common.signon.config.DSSignonConfigurationException;
import com.dtcc.dnv.otc.common.signon.exception.DSSignonException;

/**
 * Copyright (c) 2007 Depository Trust and Clearing Corporation
 * 55 Water Street, New York, New York, 10041
 * All Rights Reserved
 * 
 * @author Steve Velez
 * @date Jul 24, 2007
 * @version 1.0
 *
 * Service locater class to return the respective sigon module for the
 * the workflow step.
 * 
 * @version	1.1			July 27, 2007				sv
 * Update comments.  Added a singleton static Hashtable to cache the module objects.
 * Added instantiateModule() to actually instantiate the respective modules.
 * Updated locateModule to look for a cahced version of the module first.  If
 * one is not found, then it will attempt to instantiate one.
 * 
 * @version	1.2			August 30, 2007				sv
 * Updated locateModule() to throw DSSignonException.  Updated instantiateModule()
 * to instantiate object fro configuration object.  If it s not found
 * and it is a built in module, then it will call the new 
 * locateBuiltInModuleClassName(String)() to locate the class name for 
 * built in modules.
 * 
 * @version	1.3				September 10, 2007			sv
 * Corrected instantiateModule() to only locateBuiltInModule when a module class name
 * was not found.
 * 
 * @version	1.4				October 11, 2007			sv
 * Added logic to thrown an exception if an invalid name was provided.  Also trimmed the
 * moduleName in case it is padded with spaces.  Added system built in module.
 */
public class SignonModuleLocator {
	// Singleton static member.
	public static final Hashtable moduleList = new Hashtable();
	
	/**
	 * Facotory/service locator method to find correct signon module.
	 * This method will first look in the cache of object.  If it cannot find
	 * it, then it will attempt to instantiate a module.
	 * 
	 * @param name String value of the module name
	 * @return ISignonModule object pertaining to this sigon workflow step.
	 */
	public static ISignonModule locateModule(String name) throws DSSignonException{
		ISignonModule module = null;
		
		// Invalid module name was passed, so throw an exception.
		if (name == null || name.trim().length() == 0){
			throw new DSSignonException("SML005", "Unable to locate module because an invalid module name was provided.");
		}
		
		// Get the module by name
		module = (ISignonModule)moduleList.get(name);
		// Module could not be found, so now instantiate one.
		if (module == null){
			// Instantiate the module
			module = instantiateModule(name);
			// Cache the module 
			moduleList.put(name, module);
		}
		// return the module
		return module;
	}
	
	/**
	 * This method will instantiate the module for the name provided.
	 * 
	 * @param name String value of the name of the module to instantiate
	 * @return ISignonModule module for the name provided.
	 */
	private static ISignonModule instantiateModule(String name) throws DSSignonException{
		ISignonModule module = null;
	
		try {
			// Get configuration instance
			DSSignonConfiguration config = DSSignonConfiguration.getInstance();
			// Locate module name
			String moduleName = config.getWorkFlowModule(name);
			
			// If a module classs name is not found, look it up in built in module list
			if (moduleName == null || moduleName.trim().length() == 0){
				moduleName = locateBuiltInModuleClassName(name);
			}
			
			if (moduleName == null || moduleName.trim().length() == 0){
				throw new DSSignonException("SML005", "Exception occurred locating module class name.");
			}
				
			// Get Class object for module.
			Class moduleClass = Class.forName(moduleName);
			// Instantiate module
			module = (ISignonModule)moduleClass.newInstance();
		}catch (DSSignonConfigurationException e) {
			throw new DSSignonException("SML001", "Exception occurred getting configuration instance.");
		} catch (ClassNotFoundException e) {
			throw new DSSignonException("SML002", "Exception occurred instantiating signon module.");
		} catch (InstantiationException e) {
			throw new DSSignonException("SML003", "Exception occurred instantiating signon module.");
		} catch (IllegalAccessException e) {
			throw new DSSignonException("SML004", "Exception occurred instantiating signon module.");
		}
		return module;
	}
	
	/**
	 * This method will locate the built in modules if it is not indicated in the
	 * configuration file for a default set of mosules.
	 * 
	 * @param name String value of the name of the module.
	 * @return String value fo the class name for the module.
	 */
	private static String locateBuiltInModuleClassName(String name){
		String moduleClassName = "";
		// Check for default module names.
		if (name.equalsIgnoreCase("usertypes")){
			moduleClassName = "com.dtcc.dnv.otc.common.signon.modules.UserTypeSignonModule";
		}
		else if (name.equalsIgnoreCase("entitlement")){
			moduleClassName = "com.dtcc.dnv.otc.common.signon.modules.EntitlementSignonModule";
		}
		else if (name.equalsIgnoreCase("testProdIndicator")){
			moduleClassName = "com.dtcc.dnv.otc.common.signon.modules.AccountTypeSignonModule";
		}
		else if (name.equalsIgnoreCase("superuser")){
			moduleClassName = "com.dtcc.dnv.otc.common.signon.modules.SuperUserSignonModule";
		}
		else if (name.equalsIgnoreCase("system")){
			moduleClassName = "com.dtcc.dnv.otc.common.signon.modules.SystemSignonModule";
		}
		return moduleClassName;
	}
}
