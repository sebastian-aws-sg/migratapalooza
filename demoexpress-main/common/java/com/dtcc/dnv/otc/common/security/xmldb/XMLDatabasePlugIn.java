package com.dtcc.dnv.otc.common.security.xmldb;

/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55, Water Street,
 * New York, NY 10048
 * U.S.A
 * All Rights Reserved.
 *
 * This software may contain (in part or full) confidential/proprietary information of DTCC.
 * ("Confidential Information"). Disclosure of such Confidential
 * Information is prohibited and should be used only for its intended purpose
 * in accordance with rules and regulations of DTCC.
 * Form bean for a Struts application.
 *
 * @author Dmitriy Larin
 * @version 1.0
 * 
 * @version 1.1
 * @author Cognizant
 * 09/04/2007 Modified to read entitlement.xml  and entitlement-rules.xml using
 * ResourceLocator
 * 
 * @version	1.2					Septemner 19, 2007			sv
 * Updated to use ResourceLocater with EAR setup.  Updated to use parameters passed to plugin
 * rather than MessageResources.  Added constants.  Updated init() to read entitlements
 * and entitlement rules config from the parameters passed to this plugin and to call initialize()
 * on EntitlementDefiniitions object.   Removed unused variables.
 * 
 * @version	1.3					September 23, 2007			sv
 * Added error for config files not being found, instead of relying on NullPointerException.
 */

/**
 * 
 * XMLDatabasePlugin - Struts plugin to initialize the XML
 * Databases. If another DataAccessObject is used (e.g. for DB2)
 * then the plugin could be replaced with a DB2 specific plugin
 * None of the other components of the application would have
 * to be changed
 */
import java.io.File;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.PlugInConfig;

import com.dtcc.dnv.otc.common.resourcelocator.LocatorConstants;
import com.dtcc.dnv.otc.common.resourcelocator.ResourceLocator;

public class XMLDatabasePlugIn implements PlugIn {

	private static final String DEFAULT_ENTITLEMENT_PATH_NAME =
		"/WEB-INF/config/entitlements.xml";
	private static final String DEFAULT_ENTITLEMENT_RULES_PATH_NAME =
		"/WEB-INF/config/entitlements-rules.xml";
		
	private static final String XML_DAO_FACTORY =
		"com.dtcc.dnv.otc.security.xmldb.XMLDAOFactory";
	
	private static final String XML_DAO_RULES = "rules";
	private static final String XML_DAO_PATHNAME = "pathname";

	/**
	 * Action servlet instance
	 */
	private ActionServlet servlet = null;

	/**
	 * Commons Logging instance.
	 */
	private static Log log = LogFactory.getLog(XMLDatabasePlugIn.class);

	/**
	 * @see PlugIn#destroy()
	 */
	public void destroy() {
		if (log.isDebugEnabled()) {
			log.debug("Destroying XMLDatabasePlugIn");
		}

		//servlet.getServletContext().removeAttribute(DAOFactory.class.getName());

	}

	/**
	 * @see PlugIn#init(ActionServlet. ApplicationConfig)
	 */
	public void init(ActionServlet actionServlet, ModuleConfig config)
		throws ServletException {

		servlet = actionServlet;
		ServletContext sc = actionServlet.getServletContext();
		
		// Locate entitlements files
		PlugInConfig[] pluginConfig = config.findPlugInConfigs();
		String entitlementsUri = DEFAULT_ENTITLEMENT_PATH_NAME;
		String rulesUri = DEFAULT_ENTITLEMENT_RULES_PATH_NAME;
		// Iterate until parameters are found.
        for (int i = 0; i < pluginConfig.length; i++) {
			PlugInConfig pconfig = pluginConfig[i];
			Map map = pconfig.getProperties();
			if(map.containsKey(XML_DAO_PATHNAME) && map.containsKey(XML_DAO_RULES)) {
				entitlementsUri = (String)map.get(XML_DAO_PATHNAME);
				rulesUri = (String)map.get(XML_DAO_RULES);				
				break;
			}
		}        
               
		try {

			if (log.isInfoEnabled()){
				log.info("Initializing XML database from '" + DEFAULT_ENTITLEMENT_RULES_PATH_NAME + "'");
			}
			
			// Use resource locator to locate entitlement files
            File entitlements = ResourceLocator.getInstance().findResourceAsFile(entitlementsUri, LocatorConstants.EAR_FILE);
            File rules = ResourceLocator.getInstance().findResourceAsFile(rulesUri, LocatorConstants.EAR_FILE);
            
            if (entitlements == null || rules == null){
            	log.error("Unable to locate entilement configuration files.  Entitlements: " + entitlementsUri +"; Rules: " + rulesUri);
            }
            else{
            	EntitlementDefinitions.initialize(entitlements, rules, sc);
            }
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new ServletException(e.getMessage());
		}

	}
}
