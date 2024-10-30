package com.dtcc.dnv.otc.common.resourcelocator;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.dtcc.dnv.otc.common.resourcelocator.exception.LocatorException;

/**
 * @author Cognizant
 * @version 1.0
 * 08/01/2007
 * 
 * Interface for locating resources using a preconfigured stratergy
 * 
 * @version	1.1				September 18, 2007			sv
 * Checked in for Cognizant.  Added remove methods.
 */
public interface IResourceLocator {

	/**
	 * Looks up a resource in the path specified, the path is mentioned in LocatorConstants as 
	 * EAR_FILE, FILE_SYSTEM , CLASSPATH
	 * @return URL object matching the name, or <code>null</code> if <code>URL</code>
	 *         can not be found is not readable.
	 */			
	public URL findResource(String resourceName, int path)
			throws LocatorException;

	/**
	 * Looks up a resource in the path specified, the path is mentioned in LocatorConstants as 
	 * EAR_FILE, FILE_SYSTEM , CLASSPATH
	 * @return InputStream object matching the name, or <code>null</code> if <code>InputStream</code>
	 *         can not be found is not readable.
	 */
	public InputStream findResourceAsStream(String resourceName, int path)
			throws LocatorException;

	/**
	 * Looks up a resource in the path specified, the path is mentioned in LocatorConstants as 
	 * EAR_FILE, FILE_SYSTEM , CLASSPATH
	 * @return Properties object matching the name, or <code>null</code> if <code>Properties</code>
	 *         can not be found is not readable.
	 */
	public Properties findResourceAsProperties(String resourceName, int path)
			throws LocatorException;

	/**
	 * Looks up a resource in the path specified, the path is mentioned in LocatorConstants as 
	 * EAR_FILE, FILE_SYSTEM , CLASSPATH
	 * @return File object matching the name, or <code>null</code> if <code>File</code>
	 *         can not be found is not readable.
	 */
	public File findResourceAsFile(String resourceName, int path)
			throws LocatorException;
	/**
	 * Looks up a resource in the path specified, the path is mentioned in LocatorConstants as 
	 * EAR_FILE, FILE_SYSTEM , CLASSPATH
	 * @return String object matching the name, or <code>null</code> if <code>String</code>
	 *         can not be found is not readable.
	 */
	
	public String findResourceAsString(String resourceName, int path)
			throws LocatorException;

	/**
	 * Additional paths to be added for locating apart from locating in EAR_FILE, FILE_SYSTEM , CLASSPATH
	 * @return void 
	 */
	public void addFileSystemPath( String additionalPath );

	/**
	 * Clears the system paths 
	 * @return void 
	 */
	public void removeAllFileSystemPath();

	/**
	 * Additional paths to be added for locating in web apart from locating in EAR_FILE
	 * @return void 
	 */
	public void addWebSystemPath( String additionalPath );
	
	/**
	 * Clears the web system paths 
	 * @return void 
	 */
	public void removeAllWebSystemPath();
	
	
}
