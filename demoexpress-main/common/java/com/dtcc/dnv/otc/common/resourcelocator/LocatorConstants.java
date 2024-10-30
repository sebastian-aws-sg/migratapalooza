package com.dtcc.dnv.otc.common.resourcelocator;

/**
 * @author Cognizant
 * @version 1.0 08/01/2007
 * 
 * This class consists of all constants pertaining to ResourceLocator
 * functionality
 * 
 * @version	1.1				October 18, 2007		sv
 * Checked in for Cognizant.  Added constants and updated the environment
 * variable constants.
 */
public final class LocatorConstants {

	/**
	 * Indicates the resource should be searched in all paths
	 */
	public static final int NO_PATH = 0;

	/**
	 * Indicates to find the file in classpath
	 */
	public static final int CLASS_PATH = 1;

	/**
	 * Indicates to find the file in file system
	 */
	public static final int FILE_SYSTEM = 2;

	/**
	 * Indicates to find the file in EAR
	 */
	public static final int EAR_FILE = 3;
	
	/**
	 * Indicates invalid path for obtaining URL object
	 */
	public static final String EC_URL_INVALID_PATH = "100";

	/**
	 * Indicates invalid path for obtaining inputstream object
	 */
	public static final String EC_INPUTSTREAM_INVALID_PATH = "101";

	/**
	 * Indicates invalid path for obtaining properties object
	 */
	public static final String EC_PROPERTIES_INVALID_PATH = "102";

	/**
	 * Indicates invalid path for obtaining file name as string
	 */
	public static final String EC_STRING_INVALID_PATH = "103";

	/**
	 * Indicates error in obtaining the properties for the specified file.
	 */
	public static final String EC_ERROR_PROPERTIES = "104";

	/**
	 * Indicates an IOException while obtaining InputStream
	 */
	public static final String EC_ERROR_IOEXCEP = "105";

	/**
	 * Indicates security exception
	 */
	public static final String EC_SECURITY_EXCEPTION = "106";

	/**
	 * Indicates error while converting to file object
	 */
	public static final String EC_ERROR_CONVERTING_TO_FILE = "107";

	/**
	 * Indicates malformed URL while obtaining URL object
	 */
	public static final String EC_ERROR_MALFORMED_URL = "108";

	/**
	 * ENV Variable prefix
	 */
	public static final String EC_VARIABLE_PREFIX = "[";

	/**
	 * ENV Variable suffix
	 */
	public static final String EC_VARIABLE_SUFFIX = "]";

}
