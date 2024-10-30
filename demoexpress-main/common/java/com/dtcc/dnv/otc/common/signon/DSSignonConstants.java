package com.dtcc.dnv.otc.common.signon;

/**
 * @author vxsubram
 * @date Aug 14, 2007
 * 
 * This class serves as the constants for the signon framework
 * 
 * @version	1.1					September 2, 2007				sv
 * Cleaned up unnecessary constants.  Checked in for Cognizant.
 * 
 * @version	1.2					September 9, 2007				sv
 * Checked in for Cognizant.  Added static constant rolelabel.
 * 
 * @version	1.3					Septemeber 12, 2007				sv
 * Checked in for Cognizant.
 * 
 * @version	1.4					September 26, 2007				sv
 * Added constants for accountType and mutuallyExclusive properties.
 * 
 * @version	1.5					October 03, 2007				sv
 * Checked in for Cognizant.  Added constants for missing signon 
 * properties.
 */
public class DSSignonConstants {

	// Holds configuration file name 
	public static final String CONFIG_FILENAME = "DSSignonConfig.properties";
	// The prefix keyword (Root element) in the signon configuration  
	public static final String PREFIX = "signon";
	// The workflow keyword (workflow element) in the signon configuration
	public static final String WORKFLOW_KEY = "workflow";
	// The label keyword (workflow step element) in the signon configuration
	public static final String LABEL_KEY = "label";
	// The delimiter string used for separation of values
	public static final String VALUE_DELIMITER = ",";
	// The delimiter string used for separation of attributes (Elements)
	public static final String ATTR_DELIMITER = ".";
	// The value keyword (workflow step element) in the signon configuration
	public static final String VALUE_KEY = "value";
	// The rolevalue keyword in the signon configuration
	public static final String ROLE_VALUE_KEY = "rolevalue";
	// The rolename keyword in the signon configuration
	public static final String ROLE_NAME_KEY="rolename";
	// The rolelabel keyword in the signon configuration
	public static final String ROLE_LABEL_KEY = "rolelabel";
	// The attribute keyword (attribute element) in the signon configuration	
	public static final String ATTR_KEY = "attribute";
	// The type keyword
	public static final String TYPE_KEY = "type";
	// The type "role" keyword 
	public static final String TYPE_ROLE = "roles";
	// The type "STD" keyword
	public static final String TYPE_STD = "STD";
	// The type "labels" keyword
	public static final String TYPE_LABELS = "labels";
	//	 The WORKFLOW_MODULE_KEY keyword
	public static final String WORKFLOW_MODULE_KEY = "module";
	
	//	 Holds the usertype workflow key
	public static final String USERTYPE_WORKFLOW = "usertypes";
	
	// Holds the superuser workflow key
	public static final String SUPERUSER_WORKFLOW = "superuser";
	
	// Error Codes in Signon Module configuration.
	public static final String EX_RROPS_LOAD_FAILED = "DSSCFGL001";
	public static final String EX_RROPS_LOAD_FAILED_STR = "Unable to load signon configuration properties.";
	
	public static final String EX_MISSING_RROPS = "DSSCFGL000";
	public static final String WRKFLW_MISSING_STR = "Workflow(s) configuration is missing.";
	public static final String WRKFLW_TYPE_MISSING_STR = "Workflow property \"Type\" is not configured.";
	public static final String WRKFLW_VALUE_MISSING_STR = "Workflow item value is not configured.";
	
	// Error code in signon module exception that cannot be handled by signon module.
	public static final String EX_MODULE_FAILED = "DSSMDL000";
	public static final String EX_MODULE_FAILED_STR = "Runtime exception occured while processing workflow ";
	
	// Signon module exception that is handled by application
	public static final String EX_ACCTTYPE_INVALID = "DSSMDL001";
	public static final String EX_ACCTTYPE_INVALID_STR = "Unable to determine the Account Type.";
	
	public static final String EX_ENTITLEMENT_NOREC = "DSSMDL002";
	public static final String EX_ENTITLEMENT_NOREC_STR = "Entitlement list is empty.";
	
	public static final String EX_BUSINESS = "DSSMDLBUS";

	public static final String EX_USERTYPE_INVALID = "DSSMDL003";
	public static final String EX_USERTYPE_INVALID_STR = "Invalid User Type.";
	
	public static final String ALERT_SELECT_RECORD = "DSSMDL004";
	public static final String ALERT_SELECT_RECORD_STR = "Please select a value.";
	
	// Holds the Mutually Exclusive property
	public static final String MUTUALLY_EXCLUSIVE = "mutuallyExclusive";
	public static final String ACCOUNT_TYPE_ATTR = "accountType";
}
